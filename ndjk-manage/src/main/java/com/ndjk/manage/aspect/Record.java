package com.ndjk.manage.aspect;
import java.lang.annotation.*;

/**
 * 自定义注解 for OperateRecord
 * @author Created by xzd on 2018/1/7
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Record {
    /** 要执行的操作类型比如：add操作 **/
    public String operateType() default "";
}
