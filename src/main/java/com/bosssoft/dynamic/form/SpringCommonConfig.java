package com.bosssoft.dynamic.form;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Configuration
public class SpringCommonConfig {
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        /*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        objectMapper.setDateFormat(sdf);*/

        // 属性为null时不转为json
        // objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // 属性为空对象以 {} 返回
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 以json请求后台，多字段不会报错
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }
}
