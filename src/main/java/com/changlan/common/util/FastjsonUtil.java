package com.changlan.common.util;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

import com.fasterxml.jackson.databind.DeserializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.changlan.common.service.JsonSerializer;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class FastjsonUtil implements JsonSerializer {
    private static final Logger logger = LoggerFactory.getLogger(FastjsonUtil.class);

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String parse(Object object) {
        Writer write = new StringWriter();
        try {
            objectMapper.writeValue(write, object);
        } catch (JsonGenerationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return write.toString();
    }

    public static <T> T toObject(String json, TypeReference<?> type) {
        T object = null;
        try {
            object = (T) objectMapper.readValue(json, type);
        } catch (JsonParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return object;
    }

    public static <T> T irregularJsonToObject(String json, Class<T> clazz) {
        T object = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            object = (T) mapper.readValue(json, TypeFactory.rawClass(clazz));
        } catch (JsonParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return object;
    }

    public static <T> T toObject(String json, Class<?> clazz) {
        T object = null;
        try {
            object = (T) objectMapper.readValue(json, TypeFactory.rawClass(clazz));
        } catch (JsonParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return object;
    }

    public static <T> String beanToJson(T bean) throws JsonProcessingException {
        return objectMapper.writeValueAsString(bean);
    }

    /**
     * bean to map
     *
     * @param <T>
     * @param bean
     * @return
     */
    public static <T> Map<String, String> beanToMap(T bean) {
        Map<String, String> map = new HashMap<>();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                if (key == null || beanMap.get(key) == null) {
                    continue;
                }
                map.put(key.toString(), beanMap.get(key).toString());
            }
        }
        return map;
    }

    /**
     * bean to map
     *
     * @param <T>
     * @param bean
     * @return
     */
    public static <T> Map<String, String> beanToMapKeepNull(T bean) {
        Map<String, String> map = new HashMap<>();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                map.put(key == null ? null : key.toString(), beanMap.get(key) == null ? null : beanMap.get(key).toString());
            }
        }
        return map;
    }

    /**
     * 将对象序列化为JSON字符串
     *
     * @param object
     * @return JSON字符串
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonGenerationException
     */
    public String serialize(Object object) {
        Writer write = new StringWriter();
        try {
            objectMapper.writeValue(write, object);
        } catch (JsonGenerationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return write.toString();
    }

    /**
     * 将JSON字符串反序列化为对象
     *
     * @param json
     * @return JSON字符串
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     */
    public <T> T deserialize(String json, Class<T> clazz) {
        Object object = null;
        try {
            object = objectMapper.readValue(json, TypeFactory.rawClass(clazz));
        } catch (JsonParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return (T) object;
    }
}
