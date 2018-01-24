package com.ndjk.cl.wechat.server;

import java.security.MessageDigest;
import java.util.Arrays;


public class SignUtil {


    public static boolean checkSignature(String signature, String timestamp, String nonce) {
        String[] paramArr = new String[]{"kingstardi_dev", timestamp, nonce};
        Arrays.sort(paramArr);
        System.out.println(paramArr);
        String content = paramArr[0].concat(paramArr[1]).concat(paramArr[2]);
        String ciphertext = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] digest = md.digest(content.toString().getBytes());
            ciphertext = byteToStr(digest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ciphertext != null ? ciphertext.equals(signature.toUpperCase())
                : false;
    }

    private static String byteToStr(byte[] byteArray) {
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }

    private static String byteToHexStr(byte mByte) {
        char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
                'B', 'C', 'D', 'E', 'F'};
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];
        return new String(tempArr);
    }



}
