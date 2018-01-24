/**
 * 对公众平台发送给公众账号的消息加解密示例代码.
 * 
 * @copyright Copyright (c) 1998-2014 Tencent Inc.
 */

// ------------------------------------------------------------------------

package com.ndjk.cl.wechat.encryption;


import com.ndjk.cl.utils.StringUtil;

import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.*;

/**
 * SHA1 class
 *
 * 计算公众平台的消息签名接口.
 */
public class SHA1 {

	/**
	 * 用SHA1算法生成安全签名
	 * 
	 * @param jsapiTicket
	 *            jsapi_ticket
	 * @param timestamp
	 *            时间戳
	 * @param noncestr
	 *            随机字符串
	 * @param url
	 *            url地址
	 * @return 安全签名
	 * @throws AesException
	 */
	public static String getSHA1(String jsapiTicket, String noncestr, String timestamp, String url) throws AesException {
		try {
			Map<String,String> parameters=new HashMap<>();
			parameters.put("jsapi_ticket",jsapiTicket);
			parameters.put("noncestr",noncestr);
			parameters.put("timestamp",timestamp);
			parameters.put("url",url);
			String str = formatUrlMap(parameters, false, true);

			// SHA1签名生成
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.update(str.getBytes());
			byte[] digest = md.digest();

			StringBuffer hexstr = new StringBuffer();
			String shaHex = "";
			for (int i = 0; i < digest.length; i++) {
				shaHex = Integer.toHexString(digest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexstr.append(0);
				}
				hexstr.append(shaHex);
			}
			return hexstr.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new AesException(AesException.ComputeSignatureError);
		}
	}

	/**
	 *
	 * 方法用途: 对所有传入参数按照字段名的ASCII码从小到大排序（字典序），并且生成url参数串<br>
	 * 实现步骤: <br>
	 *
	 * @param paraMap   要排序的Map对象
	 * @param urlEncode   是否需要URLENCODE
	 * @param keyToLower    是否需要将Key转换为全小写
	 *            true:key转化成小写，false:不转化
	 * @return
	 */
	public static String formatUrlMap(Map<String, String> paraMap, boolean urlEncode, boolean keyToLower)
	{
		String buff = "";
		Map<String, String> tmpMap = paraMap;
		try
		{
			List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(tmpMap.entrySet());
			// 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
			Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>()
			{

				@Override
				public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2)
				{
					return (o1.getKey()).toString().compareTo(o2.getKey());
				}
			});
			// 构造URL 键值对的格式
			StringBuilder buf = new StringBuilder();
			for (Map.Entry<String, String> item : infoIds)
			{
				if (StringUtil.isNotBlank(item.getKey()))
				{
					String key = item.getKey();
					String val = item.getValue();
					if (urlEncode)
					{
						val = URLEncoder.encode(val, "utf-8");
					}
					if (keyToLower)
					{
						buf.append(key.toLowerCase() + "=" + val);
					} else
					{
						buf.append(key + "=" + val);
					}
					buf.append("&");
				}

			}
			buff = buf.toString();
			if (buff.isEmpty() == false)
			{
				buff = buff.substring(0, buff.length() - 1);
			}
		} catch (Exception e)
		{
			return null;
		}
		return buff;
	}
}
