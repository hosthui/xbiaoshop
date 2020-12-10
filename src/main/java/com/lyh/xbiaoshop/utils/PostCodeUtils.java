package com.lyh.xbiaoshop.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

public class PostCodeUtils {
	private static final String URL_PREFIX = "http://cpdc.chinapost.com.cn/web/index.php?m=postsearch&c=index&a=ajax_addr&searchkey=";
	
	
	/**
	 * 通过地址获取邮编信息
	 * @param addr -> 地址
	 * @return postcode -> 邮编
	 */
	public static String getPostCodeByAddr(String addr) {
		
		RestTemplate restTemplate = new RestTemplate();
		String response = restTemplate.getForObject(URL_PREFIX + addr, String.class);
		
		JSONObject jsonResp = JSON.parseObject(response);
		
		return Optional.ofNullable(jsonResp)
				.map(jsonObject -> jsonObject.getJSONArray("rs"))
				.filter(jsonArray -> jsonArray.size() > 0)
				// 地址不精确导致找到多个默认取第一个
				.map(jsonArray -> jsonArray.getJSONObject(0))
				.map(jsonObject -> jsonObject.getString("POSTCODE"))
				.orElse(StringUtils.EMPTY);
	}
}
