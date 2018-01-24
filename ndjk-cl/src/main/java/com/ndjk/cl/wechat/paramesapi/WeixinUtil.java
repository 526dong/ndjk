package com.ndjk.cl.wechat.paramesapi;

import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.ndjk.cl.http.HttpUrlUtil;
import com.ndjk.cl.http.entity.HttpUrlParam;
import com.ndjk.cl.http.entity.HttpUrlParamFactory;
import com.ndjk.cl.wechat.menu.Menu;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * 请求数据通用类
 *
 * @author wl
 * @Date: 2018/1/16  9:53
 */
public class WeixinUtil {
    private static Logger logger = LoggerFactory.getLogger(WeixinUtil.class);

    /**
     * 发起https请求并获取结果
     *
     * @param request 请求地址
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public static JSONObject httpRequest(String request) {
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        try {

            HttpUrlParam defaultGetRequest = HttpUrlParamFactory.createDefaultGetRequest(request);
            String s = HttpUrlUtil.httpRequest(defaultGetRequest);

            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(Date.class, new DateTypeAdapter());
            jsonObject = builder.create().fromJson(s, JSONObject.class);

        } catch (Exception e) {
        }
        return jsonObject;
    }

    public static JSONObject httpRequestPost(String request, Object RequestMethod) {
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        try {

            HttpUrlParam defaultPostRequestWithJson = HttpUrlParamFactory.createDefaultPostRequestWithJson(request);
            String s = HttpUrlUtil.httpRequestWithJson(defaultPostRequestWithJson, RequestMethod);
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(Date.class, new DateTypeAdapter());
            jsonObject = builder.create().fromJson(s, JSONObject.class);

        } catch (Exception e) {
        }
        return jsonObject;
    }


    // 获取access_token的接口地址（GET）
    public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";


    /**
     * 获取access_token
     *
     * @param corpID 企业Id
     * @param secret 管理组的凭证密钥，每个secret代表了对应用、通讯录、接口的不同权限；不同的管理组拥有不同的secret
     * @return
     */
    public static AccessToken getAccessToken(String corpID, String secret) {
        AccessToken accessToken = null;

        String requestUrl = access_token_url.replace("APPID", corpID).replace("APPSECRET", secret);
        JSONObject jsonObject = httpRequest(requestUrl);
        // 如果请求成功
        if (null != jsonObject) {
            try {
                accessToken = new AccessToken();
                accessToken.setToken(jsonObject.getString("access_token"));
                accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
                System.out.println("获取token成功:" + jsonObject.getString("access_token") + "————" + jsonObject.getInt("expires_in"));
            } catch (Exception e) {
                accessToken = null;
                // 获取token失败
                String error = String.format("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
                System.out.println(error);
            }
        }
        return accessToken;
    }

    // 菜单创建（POST）
    public static String menu_create_url = "https://qyapi.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN&agentid=1";

    /**
     * 创建菜单
     *
     * @param menu        菜单实例
     * @param accessToken 有效的access_token
     * @return 0表示成功，其他值表示失败
     */
    public static int createMenu(Menu menu, String accessToken) {
        int result = 0;

        // 拼装创建菜单的url
        String url = menu_create_url.replace("ACCESS_TOKEN", accessToken);
        // 将菜单对象转换成json字符串
        String jsonMenu = JSONObject.fromObject(menu).toString();
        // 调用接口创建菜单
        JSONObject jsonObject = httpRequestPost(url, menu);

        if (null != jsonObject) {
            if (0 != jsonObject.getInt("errcode")) {
                result = jsonObject.getInt("errcode");
                String error = String.format("创建菜单失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
                System.out.println(error);
            }
        }

        return result;
    }

    public static String URLEncoder(String str) {
        String result = str;
        try {
            result = java.net.URLEncoder.encode(result, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 根据内容类型判断文件扩展名
     *
     * @param contentType 内容类型
     * @return
     */
    public static String getFileEndWitsh(String contentType) {
        String fileEndWitsh = "";
        if ("image/jpeg".equals(contentType))
            fileEndWitsh = ".jpg";
        else if ("audio/mpeg".equals(contentType))
            fileEndWitsh = ".mp3";
        else if ("audio/amr".equals(contentType))
            fileEndWitsh = ".amr";
        else if ("video/mp4".equals(contentType))
            fileEndWitsh = ".mp4";
        else if ("video/mpeg4".equals(contentType))
            fileEndWitsh = ".mp4";
        return fileEndWitsh;
    }

    /**
     * 数据提交与请求通用方法
     *
     * @param access_token 凭证
     * @param RequestMt    请求方式
     * @param RequestURL   请求地址
     * @param outstr       提交json数据
     */
    public static int PostMessage(String access_token, String RequestMt, String RequestURL, String outstr) {
        int result = 0;
        RequestURL = RequestURL.replace("ACCESS_TOKEN", access_token);
        JSONObject jsonobject = WeixinUtil.httpRequestPost(RequestURL, RequestMt);
        if (null != jsonobject) {
            if (0 != jsonobject.getInt("errcode")) {
                result = jsonobject.getInt("errcode");
                String error = String.format("操作失败 errcode:{} errmsg:{}", jsonobject.getInt("errcode"), jsonobject.getString("errmsg"));
                System.out.println(error);
            }
        }
        return result;
    }

    // 获取jsapi_ticket的接口地址（GET）
    public final static String jsapi_ticket_url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";

    /**
     * @param access_token
     * @Author: wl
     * @Description: 通过access_token获取jsapi_ticket（get）
     * @Date: 2018/1/17  10:36
     * @Version: 2.0
     */
    public static JsapiTicket getJsapiTicket(String access_token) {
        JsapiTicket jsapiTicket = null;
        String jsapiTicketUrl = jsapi_ticket_url.replace("ACCESS_TOKEN", access_token);
        JSONObject jsonObject = httpRequest(jsapiTicketUrl);
        if (null != jsonObject) {
            if (0 == jsonObject.getInt("errcode")) {
                jsapiTicket = new JsapiTicket();
                jsapiTicket.setJsapiTicket(jsonObject.getString("ticket"));
                jsapiTicket.setExpiresIn(jsonObject.getInt("expires_in"));
                System.out.println("获取jsapi_ticket成功:" + jsonObject.getString("ticket") + "————" + jsonObject.getInt("expires_in"));
            } else {
                String error = String.format("获取失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
                System.out.println(error);
            }
        }
        return jsapiTicket;
    }

    // code换accesstoken的接口地址（GET）
    public final static String access_token_by_code_url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";

    /**
     * @Author: wl
     * @Description: code 换accessToken
     * @Date: 2018/1/18  18:25
     * @Version: 2.0
     */
    public static AccessTokenCode getAccessTokenByCode(String corpID, String secret, String code) {
        AccessTokenCode accessTokenCode = new AccessTokenCode();
        String accessTokenByCodeUrl = access_token_by_code_url.replace("APPID", corpID).replace("SECRET", secret).replace("CODE", code);
        JSONObject jsonObject = httpRequest(accessTokenByCodeUrl);
        if (null != jsonObject) {
            logger.info("=====accessTokenCode=====:" + jsonObject.toString());
            try {
                accessTokenCode.setAccessToken(jsonObject.getString("access_token"));
                accessTokenCode.setExpiresIn(jsonObject.getInt("expires_in"));
                accessTokenCode.setRefreshToken(jsonObject.getString("refresh_token"));
                accessTokenCode.setOpenid(jsonObject.getString("openid"));
                accessTokenCode.setScope(jsonObject.getString("scope"));
            } catch (Exception e) {
                logger.error("微信通过code获取accessToken出错：",e);
            }
        }
        return accessTokenCode;
    }

    // 用openId和accessToken获取微信用户基本信息（GET）
    public final static String wechat_user_info_url = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

    /**
     * @Author: wl
     * @Description: 用openId和accessToken获取微信用户基本信息
     * @Date: 2018/1/19  9:57
     * @Version: 2.0
     */
    public static WechatUserInfo getWechatUserInfoByOpenId(String accessToken, String openId) {
        WechatUserInfo userInfo = new WechatUserInfo();
        String wechatUserInfoUrl = wechat_user_info_url.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
        JSONObject jsonObject = httpRequest(wechatUserInfoUrl);
        if (null != jsonObject) {
            try {
                userInfo.setOpenid(jsonObject.getString("openid"));
                userInfo.setNickname(jsonObject.getString("nickname"));
                userInfo.setSex(jsonObject.getString("sex"));
                userInfo.setProvince(jsonObject.getString("province"));
                userInfo.setCity(jsonObject.getString("city"));
                userInfo.setCountry(jsonObject.getString("country"));
                userInfo.setHeadimgurl(jsonObject.getString("headimgurl"));
            } catch (Exception e) {
                logger.error("获取微信用户基本信息出错：", e);
            }
            System.out.println(jsonObject.toString());
        }
        return userInfo;
    }
}
