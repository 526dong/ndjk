package com.ndjk.cl.wechat.oauth2;

import com.ndjk.cl.wechat.paramesapi.ParamesAPI;
import com.ndjk.cl.wechat.paramesapi.WeixinUtil;

/**
 * OAuth2 servlet类
 *
 * @author ivhhs
 * @date 2014.10.16
 */



public class OAuth2Servlet {

    private static final long serialVersionUID = 1L;

    public void doGet() throws Exception {

        String access_token = WeixinUtil.getAccessToken(ParamesAPI.corpId, ParamesAPI.secret).getToken();
        // agentid 跳转链接时所在的企业应用ID
        // 管理员须拥有agent的使用权限；agentid必须和跳转链接时所在的企业应用ID相同


    }

 /*   public static void main(String[] args) {
//        String access_token = "5_m5JVvp1q1on1Quw6HKyN-8ih-R_4nvWN5BlYHD_07yny0cAr0k0UckyhB9tLVg14MmBqB0zO2_23wziLZstwHMR2ZJfNiryqZfJmsLOSuRqK4u0bNyv1iHJwGbANQDdAEAYBO";
		String access_token = WeixinUtil.getAccessToken(ParamesAPI.corpId, ParamesAPI.secret).getToken();
        System.out.println(access_token);
        JsapiTicket jsapiTicket = WeixinUtil.getJsapiTicket(access_token);
//        String jsapiTicket = "HoagFKDcsGMVCIY2vOjf9jdR7_C1VSflnnzHHmYTTXQi5U8uw3cmPle7rNSGq-gKWMJ6NIKI6Syra8VTq9Gm8w";
        System.out.println(jsapiTicket.getJsapiTicket());
    }*/
 public static void main(String[] args) {
//     WeixinUtil.getAccessTokenByCode(ParamesAPI.corpId, ParamesAPI.secret,"0619LMwW1IMLOS0IiTxW1bkDwW19LMw2");
     WeixinUtil.getWechatUserInfoByOpenId("6_QOsYA97Bwq39H6wXSy--RYcTWFSWv3zvsWGECdx0M3ApDY9dEx5uPHCedIAN9PQHslOorXwhVwFtVE4A4vK_KA","oSxB71qXB7OgxQS4dqV_OumDRV7I");
 }

}
