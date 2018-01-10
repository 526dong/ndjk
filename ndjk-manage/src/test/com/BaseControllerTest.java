package com;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner; 
  
@RunWith(SpringJUnit4ClassRunner.class) 
@ContextHierarchy({ 
    @ContextConfiguration(name = "parent", locations = {"classpath:spring.xml", "classpath:spring-mybatis.xml", "classpath:spring-redis.xml"}), 
    @ContextConfiguration(name = "child", locations = "classpath:spring-mvc.xml") 
}) 
public class BaseControllerTest { 
	
}
