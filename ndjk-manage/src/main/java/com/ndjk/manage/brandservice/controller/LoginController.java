package com.ndjk.manage.brandservice.controller;

import com.ndjk.cl.brandservice.dao.SysUserMapper;
import com.ndjk.cl.brandservice.model.Kindergarten;
import com.ndjk.cl.brandservice.model.SysUser;
import com.ndjk.cl.brandservice.model.resp.BaseResponseModel;
import com.ndjk.cl.brandservice.model.resp.LoginRespModel;
import com.ndjk.cl.brandservice.model.resp.UpdatePwReq;
import com.ndjk.cl.brandservice.service.KindergartenService;
import com.ndjk.cl.utils.ValidateCode;
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
@RequestMapping(value = "/manage")
public class LoginController {

    @Autowired
    private KindergartenService kindergartenService;
    @Autowired
    private SysUserMapper sysUserMapper;
    /**
     * 登录
     * @param account 账号
     * @param password 密码
     * @param code 验证码
     * @return
     */
    @RequestMapping(value = "/login")
    @ResponseBody
    public Object login(HttpSession session,String account,String password,String code){
        SysUser sysUser = this.sysUserMapper.selectByLoginName(account);
        if(sysUser == null){
            return new BaseResponseModel(400,"账号或密码错误");
        }
        if(!sysUser.getStatus().equals("1")){
            return new BaseResponseModel(400,"该账号已冻结");
        }
        if(!password.equals(sysUser.getPassword())){
            return new BaseResponseModel(400,"账号或密码错误");
        }
        String imgcode = String.valueOf(session.getAttribute("imgcode"));
        if(!imgcode.equals(code)){
            return new BaseResponseModel(400,"输入验证码错误");
        }
        session.setAttribute("sysUser",sysUser);
        return new LoginRespModel(200,"登录成功",sysUser.getId());
    }

    /**
     * 修改密码
     * @param updatePwReq
     * @return
     */
    @RequestMapping(value = "/brandservice/updatepassword")
    @ResponseBody
    public Object updatepassword(UpdatePwReq updatePwReq) {
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

    /**
     * @description 退出登录
     * @author wdl
     * @date 2017/12/7 21:23
     */
    @RequestMapping("/user/exitlogin")
    @ResponseBody
    public Object exitLogin(HttpServletResponse response,
                         HttpSession session) throws Exception {

        session.removeAttribute("sysUser");
        return new BaseResponseModel(200,"操作成功");
    }
}
