package com;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class BaseTest<T> {

    private T service;

    private ObjectMapper mapper = new ObjectMapper();

    public T getService() {
        return this.service;
    }

    @Before
    public void init() {

        ApplicationContext aCtx = new FileSystemXmlApplicationContext(
                "classpath:spring.xml", "classpath:spring-mybatis.xml", "classpath:spring-redis.xml", "classpath:spring-freemarker.xml");
        T service = (T) aCtx.getBean(this.getBeanName());
        assertNotNull(service);
        this.service = service;

    }

    public abstract String getBeanName();

    public String objToStr(Object obj) {
        try {
            return this.mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
