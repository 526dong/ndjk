package com.ndjk.api.brandservice.controller;

import com.ndjk.cl.brandservice.model.Kindergarten;
import com.ndjk.cl.brandservice.model.resp.BaseResponseModel;
import com.ndjk.cl.brandservice.model.resp.LoginRespModel;
import com.ndjk.cl.brandservice.model.resp.UpdatePwReq;
import com.ndjk.cl.brandservice.service.KindergartenService;
import com.ndjk.cl.utils.ValidateCode;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 关键继承或者实现 需要注释下
 *
 * @author fuwei.zheng
 * @author 修改者
 * @since 9:50  2018/1/8
 */
@Controller
@RequestMapping(value = "/api")
public class LoginController {

    @Autowired
    private KindergartenService kindergartenService;
    /**
     * 登录
     * @param account 账号
     * @param password 密码
     * @param code 验证码
     * @return
     */
    @RequestMapping(value = "/brandservice/login")
    @ResponseBody
    public Object login(HttpResponse response,HttpSession session, String account, String password, String code){
        response.setHeader("Access-Control-Allow-Origin", "*");
        Kindergarten kindergarten = this.kindergartenService.selectByLoginName(account);
        if(kindergarten == null){
            return new BaseResponseModel(400,"账号或密码错误");
        }
        if(kindergarten.getStatus() != 1){
            return new BaseResponseModel(400,"该账号已冻结");
        }
        if(!password.equals(kindergarten.getPassword())){
            return new BaseResponseModel(400,"账号或密码错误");
        }
        String imgcode = String.valueOf(session.getAttribute("imgcode"));
        if(!imgcode.equals(code)){
            return new BaseResponseModel(400,"验证码错误");
        }
        session.setAttribute("kindergarten",kindergarten);
        return new LoginRespModel(200,"登录成功",kindergarten.getId());
    }

    /**
     * 修改密码
     * @param updatePwReq
     * @return
     */
    @RequestMapping(value = "/brandservice/updatepassword")
    @ResponseBody
    public Object updatepassword(HttpResponse response,UpdatePwReq updatePwReq) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        String result = this.kindergartenService.updatePassWord(updatePwReq.getKgId(),
                updatePwReq.getOldPassword(),updatePwReq.getNewPassword());
        if(result == null){
            return  new BaseResponseModel(200,"操作成功");
        }
        return new BaseResponseModel(400,result);
    }

    /**
     * @description 生成图片验证码
     * @author wdl
     * @date 2017/12/7 21:23
     */
    @RequestMapping("/user/imgCode/generate")
    @ResponseBody
    public void generate(HttpServletResponse response,
                         HttpSession session) throws Exception {
        response.setHeader("Access-Control-Allow-Origin", "*");
        // 设置响应的类型格式为图片格式
        response.setContentType("image/jpeg");
        //禁止图像缓存。
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        ValidateCode vCode = new ValidateCode(120,40,4,50);
        session.setAttribute("imgcode", vCode.getCode());
        vCode.write(response.getOutputStream());
        response.getOutputStream().flush();
    }
}
