package com.ndjk.cl.wechat.service.impl;

import com.ndjk.cl.redis.service.RedisService;
import com.ndjk.cl.utils.StringUtil;
import com.ndjk.cl.wechat.dao.UserWechatMapper;
import com.ndjk.cl.wechat.encryption.AesException;
import com.ndjk.cl.wechat.encryption.SHA1;
import com.ndjk.cl.wechat.model.UserWechat;
import com.ndjk.cl.wechat.paramesapi.*;
import com.ndjk.cl.wechat.service.WechatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by wl on 2018/1/24.
 */
@Service
public class WechatServiceImpl implements WechatService {
    private static final Logger logger = LoggerFactory.getLogger(WechatServiceImpl.class);
    @Autowired
    private RedisService redisService;
    @Autowired
    private UserWechatMapper userWechatMapper;

    /**
     * 处理微信请求
     *
     * @return
     * @date: 2017/6/15 9:33
     * @author keyuwang
     */
    @Override
    public void handleMsg() {
        boolean flag = false;// 是否需要回复发送消息
        String respXml = "";
        try {
//            Map<String, String> reqMap = XmlUtil.paseXml2Map(req);
            Map<String, String> reqMap = new HashMap<>();
            Set<String> keys = reqMap.keySet();
            for (String key : keys) {
                System.err.println("the key and value is: " + key + " = " + reqMap.get(key));
            }
            String fromUserName = reqMap.get("FromUserName");
            String toUserName = reqMap.get("ToUserName");
            String createTime = reqMap.get("CreateTime");
            String msgType = reqMap.get("MsgType");
            String msgID = reqMap.get("MsgId");

        /*    TextRespMessage textRespMessage = new TextRespMessage();
            textRespMessage.setFromUserName(toUserName);
            textRespMessage.setToUserName(fromUserName);
            textRespMessage.setCreateTime(new Date().getTime());
            textRespMessage.setMsgType(WechatConstants.REQ_MESSAGE_TYPE_TEXT);*/

            if (msgType.equals("text")) {// 文本消息
                logger.error("文本消息");
            } else if (msgType.equals("image")) {// 图片消息
                logger.error("图片消息");
            } else if (msgType.equals("voice")) {// 声音消息
                logger.error("声音消息");
            } else if (msgType.equals("video")) {// 视频消息
                logger.error("视频消息");
            } else if (msgType.equals("location")) {// 地理位置消息
                logger.error("地理位置消息");
            } else if (msgType.equals("link")) {// 链接消息
                logger.error("链接消息");
            } /*else if (msgType.equals(WechatConstants.REQ_MESSAGE_TYPE_EVENT)) {
                String eventType = reqMap.get("Event").toLowerCase();
                if (eventType.equals(WechatConstants.EVENT_TYPE_SUBSCRIBE.toLowerCase())) {// 关注
                    System.err.println("关注微信");
                } else if (eventType.equals(WechatConstants.EVENT_TYPE_UNSUBSCRIBE.toLowerCase())) {// 取消关注
                    logger.error("取消关注");
                } else if (eventType.equals(WechatConstants.EVENT_TYPE_SCAN.toLowerCase())) {// 扫描
                    logger.error("扫描");
                } else if (eventType.equals(WechatConstants.EVENT_TYPE_LOCATION.toLowerCase())) {// 地址
                    logger.error("地址");
                } else if (eventType.equals(WechatConstants.EVENT_TYPE_CLICK.toLowerCase())) {// 点击
                    logger.error("点击");
                } else if (eventType.equals(WechatConstants.EVENT_TYPE_VIEW)) {// 视图
                    logger.error("视图");
                }
            }*/
            if (flag) {
                System.err.println("微信发送响应消息" + respXml);
            }
        } catch (Exception e) {
        }
    }
    @Override
    public Map<String, Object> getTicket(String url) throws AesException {
        Map<String, Object> result = new HashMap<>();
        Map h5_access_token = redisService.entries("wechat_access_token");
        String access_token = "";
        if (null != h5_access_token && StringUtil.isNotBlank(h5_access_token.get("access_token"))) {
            access_token = StringUtil.isNull(h5_access_token.get("access_token"));
        } else {
            //获取access_token
            access_token = WeixinUtil.getAccessToken(ParamesAPI.corpId, ParamesAPI.secret).getToken();
            Map<String, String> tokenMap = new HashMap<>();
            tokenMap.put("access_token", access_token);
            redisService.putAll("wechat_access_token", tokenMap, 1);
        }
        logger.info("=====access_token=====:" + access_token);
        //获取jsapi_ticket
        String jsapiTicket = "";
        Map wechatJsapiTicket = redisService.entries("wechat_jsapi_ticket");
        if (null != wechatJsapiTicket && StringUtil.isNotBlank(wechatJsapiTicket.get("jsapiTicket"))) {
            jsapiTicket = StringUtil.isNull(wechatJsapiTicket.get("jsapiTicket"));
        } else {
            //获取jsapi_ticket
            JsapiTicket jsapiTicketOb = WeixinUtil.getJsapiTicket(access_token);
            if (StringUtil.isNotBlank(jsapiTicketOb)) {
                jsapiTicket = jsapiTicketOb.getJsapiTicket();
                Map<String, String> ticketMap = new HashMap<>();
                ticketMap.put("jsapiTicket", jsapiTicketOb.getJsapiTicket());
                redisService.putAll("wechat_jsapi_ticket", ticketMap, 1);
            }
        }
        //时间戳和随机字符串
        String noncestr = UUID.randomUUID().toString().replace("-", "").substring(0, 16);//随机字符串
        result.put("noncestr", noncestr);
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);//时间戳
        result.put("timestamp", timestamp);
        result.put("jsapi_ticket", jsapiTicket);
        String signature = SHA1.getSHA1(jsapiTicket, noncestr, timestamp, url);
        result.put("signature", signature);

        return result;
    }

    //存入openId
    @Override
    public int insertUserWechat(UserWechat userWechat) {
        return userWechatMapper.insertSelective(userWechat);
    }

    /**
     * @Author: wl
     * @Description: code换微信用户基本信息
     * @Date: 2018/1/19  10:49
     * @Version: 2.0
     */
    @Override
    public WechatUserInfo getUserWechat(String sessionId, String code) throws Exception{
        WechatUserInfo wechatUserInfo = null;
        AccessTokenCode accessTokenByCode = WeixinUtil.getAccessTokenByCode(ParamesAPI.corpId, ParamesAPI.secret, code);//code 换accessToken
        if (accessTokenByCode != null) {
            wechatUserInfo = WeixinUtil.getWechatUserInfoByOpenId(accessTokenByCode.getAccessToken(), accessTokenByCode.getOpenid());//用openId和accessToken获取微信用户基本信息

            Map<String,Object> param=new HashMap<>();
            param.put("openId",accessTokenByCode.getOpenid());
            UserWechat userWechat = userWechatMapper.findSelective(param);
            if(userWechat!=null){
                //微信基本信息存到表里
                userWechat=new UserWechat();
                userWechat.setNickname(wechatUserInfo.getNickname());
                userWechat.setCity(wechatUserInfo.getCity());
                userWechat.setCountry(wechatUserInfo.getCountry());
                userWechat.setProvince(wechatUserInfo.getProvince());
                userWechat.setCreateTime(new Date());
                userWechat.setHeadimgurl(wechatUserInfo.getHeadimgurl());
                userWechat.setOpenId(wechatUserInfo.getNickname());
                userWechat.setPrivilege(wechatUserInfo.getPrivilege().toString());
                userWechat.setSex(wechatUserInfo.getSex());
                userWechat.setUnionid(wechatUserInfo.getUnionid());
                userWechatMapper.updateByPrimaryKeySelective(userWechat);
            }else{
                userWechat=new UserWechat();
                userWechat.setNickname(wechatUserInfo.getNickname());
                userWechat.setCity(wechatUserInfo.getCity());
                userWechat.setCountry(wechatUserInfo.getCountry());
                userWechat.setProvince(wechatUserInfo.getProvince());
                userWechat.setCreateTime(new Date());
                userWechat.setHeadimgurl(wechatUserInfo.getHeadimgurl());
                userWechat.setOpenId(wechatUserInfo.getNickname());
                userWechat.setPrivilege(StringUtil.isNull(wechatUserInfo.getPrivilege()));
                userWechat.setSex(wechatUserInfo.getSex());
                userWechat.setUnionid(wechatUserInfo.getUnionid());
                userWechatMapper.insertSelective(userWechat);
            }

            Map<String,String> sessionOpenId=new HashMap<>();
            sessionOpenId.put("openId",accessTokenByCode.getOpenid());
            redisService.putAll(sessionId,sessionOpenId,2);

            if(userWechat!=null&&StringUtil.isNotBlank(userWechat.getPhone())){
                wechatUserInfo.setRelation(true);
            }else{
                wechatUserInfo.setRelation(false);
            }
        } else {
            wechatUserInfo=new WechatUserInfo();
            //从session中获取openId查询用户表
            Object openIdOb = redisService.getMap(sessionId, "openId");
            String openId = StringUtil.isNull(openIdOb);
            Map<String,Object> param=new HashMap<>();
            param.put("openId",openId);
            UserWechat userWechat = userWechatMapper.findSelective(param);
            if(userWechat!=null){
                wechatUserInfo.setOpenid(userWechat.getOpenId());
                wechatUserInfo.setNickname(userWechat.getNickname());
                wechatUserInfo.setSex(userWechat.getSex());
                wechatUserInfo.setCountry(userWechat.getCountry());
                wechatUserInfo.setProvince(userWechat.getProvince());
                wechatUserInfo.setCity(userWechat.getCity());
                wechatUserInfo.setHeadimgurl(userWechat.getHeadimgurl());
                if(StringUtil.isNotBlank(userWechat.getPhone())){
                    wechatUserInfo.setRelation(true);
                }else{
                    wechatUserInfo.setRelation(false);
                }
            }

        }
        return wechatUserInfo;
    }

}
