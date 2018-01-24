package com.ndjk.cl.wechat.service;

import com.ndjk.cl.wechat.encryption.AesException;
import com.ndjk.cl.wechat.model.UserWechat;
import com.ndjk.cl.wechat.paramesapi.WechatUserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by wky77 on 2017/6/15.
 */

public interface WechatService {
    /**
     * 处理微信请求
     *
     * @return
     * @date: 2017/6/15 9:33
     * @author keyuwang
     */
    void handleMsg();

    Map<String, Object> getTicket(String url) throws AesException;

    //存入openId
    int insertUserWechat(UserWechat userWechat);

    /**
     * @Author: wl
     * @Description: code换微信用户基本信息
     * @Date: 2018/1/19  10:49
     * @Version: 2.0
     */
    WechatUserInfo getUserWechat(String sessionId, String code) throws Exception;

}
