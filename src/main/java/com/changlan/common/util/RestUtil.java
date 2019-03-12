package com.changlan.common.util;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.core.type.TypeReference;

import org.springframework.cglib.beans.BeanMap;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class RestUtil {
	private static RestTemplate restTemplate;
	
	static {
		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		requestFactory.setConnectTimeout(30*1000);// 设置超时
		restTemplate = new RestTemplate(requestFactory);
		List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
		//一下操作为了支持restTample 支持更多的返回值类型
		MappingJackson2HttpMessageConverter mappingConverter=null;
		for(HttpMessageConverter converter : messageConverters){
			if(converter instanceof  MappingJackson2HttpMessageConverter){
				mappingConverter = (MappingJackson2HttpMessageConverter) converter;
				break;
			}
		}
		if(mappingConverter == null){
			mappingConverter =new MappingJackson2HttpMessageConverter();
			messageConverters.add(mappingConverter);
		}
		//支持的类型
		MediaType[] mediaTypes = new MediaType[]{
				MediaType.APPLICATION_JSON,
				MediaType.APPLICATION_OCTET_STREAM,
				MediaType.TEXT_HTML,
				MediaType.TEXT_PLAIN,
				MediaType.TEXT_XML,
				MediaType.APPLICATION_JSON_UTF8,
				MediaType.APPLICATION_ATOM_XML,
				MediaType.APPLICATION_FORM_URLENCODED,
				MediaType.APPLICATION_JSON_UTF8,
				MediaType.APPLICATION_PDF,
		};
		mappingConverter.setSupportedMediaTypes(Arrays.asList(mediaTypes));
	}


	public static <E> ResponseEntity<E> postForEntity(String url,Class<E> c){
		return postForEntity(url,null,c);
	}

	public static <E> ResponseEntity<E> postForEntity(String url, Object params, Class<E> c){
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<MultiValueMap<String, String>>(linkedBeanToMap(params),headers);
		return postForEntity(url,httpEntity,c);
	}

	public static ResponseEntity<String> postForString(String url, Object params){
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<MultiValueMap<String, String>>(linkedBeanToMap(params),headers);
		return postForString(url,httpEntity);
	}
	
	public static <E> ResponseEntity<E> postForEntity(String url,HttpEntity<MultiValueMap<String, String>> httpEntity,Class<E> c){
		return restTemplate.postForEntity(url,httpEntity,c);
	}

	public static  <E> ResponseEntity<E> postJsonForEntity(String url,Serializable param,Class<E> c){
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);//APPLICATION_FORM_URLENCODED
		HttpEntity httpEntity = new HttpEntity(FastjsonUtil.parse(param), headers);
		return postForEntity(url,httpEntity,c);
	}
	
	public static ResponseEntity<String> postForString(String url,HttpEntity<MultiValueMap<String, String>> httpEntity){
		return restTemplate.postForEntity(url,httpEntity,String.class);
	}

	
	
	public static ResponseEntity<String> getForString(String url,Map<String, String> params,HttpEntity<MultiValueMap<String, String>> httpEntity){
//		return restTemplate.getForEntity(url, String.class, params);
		return restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class, params);
	}
	
	public static <T> ResponseEntity<T> getForEntity(String url ,Class<T> c){
		return getForEntity(url,c,null);
	}


	public static <T> ResponseEntity<T> getForEntity(String url ,Class<T> c, Object param){
		Map<String,String> map = FastjsonUtil.beanToMap(param);
		return getForEntity(url,map,c);
	}

	public static <T> ResponseEntity<T> getForEntity(String url ,Map<String,String> param,Class<T> c){
		return restTemplate.getForEntity(url+mapToRequestString(param),c,param);
	}


	private static  String mapToRequestString(Map<String,String> param){
		StringBuilder paramStr = new StringBuilder();
			for(String key: param.keySet()){
				if(key==null || param.get(key)==null){
					continue;
				}
				if(paramStr.length()==0){
						paramStr.append("?"+key+"={"+ key +"}");
				}else{
					paramStr.append("&"+key+"={"+ key +"}");
				}
			}
		return paramStr.toString();
	}
	/**
	 * bean to map
	 * @param bean
	 * @return
	 */
	public static MultiValueMap<String,String> linkedBeanToMap(Object bean){
		MultiValueMap<String,String>   map = new LinkedMultiValueMap<>();
		if(bean != null){
			BeanMap beanMap = BeanMap.create(bean);
			for (Object key : beanMap.keySet()) {
				if(key == null || beanMap.get(key) ==null){
					continue;
				}
				map.add(key.toString(),beanMap.get(key).toString() );
			}
		}
		return  map;
	}
}
