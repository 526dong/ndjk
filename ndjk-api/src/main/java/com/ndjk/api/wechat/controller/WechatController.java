package com.ndjk.api.wechat.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ndjk.cl.utils.GsonUtil;
import com.ndjk.cl.wechat.paramesapi.WechatUserInfo;
import com.ndjk.cl.wechat.service.WechatService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * Created by wky77 on 2017/6/15.
 */
@Controller
public class WechatController {
    private Logger logger = Logger.getLogger(WechatController.class);

    @Autowired
    private WechatService wechatService;

    /**
     * 校验请求是否合法，仅接受微信请求
     *
     * @param wechat
     */
    /*@RequestMapping(method = {RequestMethod.GET}, produces = "application/json;charset=UTF-8")
    public void valid(Wechat wechat) {
        String signature = wechat.getSignature(); // 微信加密签名
        String timestamp = wechat.getTimestamp(); // 时间戳
        String nonce = wechat.getNonce();// 随机数
        String echostr = wechat.getEchostr();// 随机字符串

        logger.error("WechatController.valid : signature" + signature + "timestamp: " + timestamp + "nonce: " + nonce + "echostr: " + echostr);
        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        if (SignUtil.checkSignature(signature, timestamp, nonce)) {
            PrintWriter out;
            try {
                out = response.get().getWriter();
                out.print(echostr);
                out.flush();
                out.close();
            } catch (IOException e) {
                showError(e);
            }
        } else {
            logger.error("不是微信服务器发来的请求,拒绝认证请求！");
        }

    }

    *//**
     * 消息的接收、处理、响应f
     *
     * @throws IOException
     *//*
    @RequestMapping(method = {RequestMethod.POST}, produces = "application/xml;charset=UTF-8")
    public void dispose() throws IOException {
        // 将请求、响应的编码均设置为UTF-8（防止中文乱码）
        HttpServletRequest req = request.get();
        req.setCharacterEncoding("UTF-8");
        HttpServletResponse resp = response.get();
        resp.setCharacterEncoding("UTF-8");
        service.handleMsg(req, resp);
    }*/

    @RequestMapping(value = "/api/actzm/mine/wechat/getTicket")
    @ResponseBody
    public void getTicket(String url, HttpServletResponse response) throws Exception {

        Map<String, Object> ticket = wechatService.getTicket(url);
/*        ApiResult ticket = wechatApi.getTicket(url);
        Gson gson = new GsonBuilder().create();*/
        this.writeJsonToResponse(ticket, response, response.getWriter());
    }
    public static void writeJsonToResponse(Object obj, HttpServletResponse response, PrintWriter pw) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json;charset=utf-8");
        String json = GsonUtil.toJson(obj);
        pw.write(json);
    }
    //获取微信用户信息
    @RequestMapping(value = "/api/actzm/mine/wechat/getWechatUserInfo")
    @ResponseBody
    public void getWechatUserInfo(String code, HttpServletResponse response, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(30*60*4);
        String sessionId = session.getId();
//        ApiResult wechatUserInfo = wechatApi.getWechatUserInfo(sessionId, code);
        WechatUserInfo userWechat = wechatService.getUserWechat(sessionId, code);
        Gson gson = new GsonBuilder().create();
        this.writeJsonToResponse(userWechat, response, response.getWriter());
    }
}
