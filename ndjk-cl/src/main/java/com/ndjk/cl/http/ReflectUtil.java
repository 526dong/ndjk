package com.ndjk.cl.http;

import com.ndjk.cl.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.OperationsException;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**反射 域 方法 类*/
public class ReflectUtil {

    private static Logger log = LoggerFactory.getLogger(ReflectUtil.class);

    private ReflectUtil() {
    }

    /**
     * 判断当前object属性全都为空 有一个不为空返回false
     *
     * @param object
     * @return
     */
    public static boolean isAllAttributeEmpty(Object object) {
        Class<? extends Object> clazz=object.getClass();
        Field[] fieldArray=clazz.getDeclaredFields();
        AccessibleObject.setAccessible(fieldArray, true);
        try {
            for (Field field : fieldArray) {
                if (field.getType() == List.class) {
                    if (((List) field.get(object)).size() != 0)
                        return false;
                } else {
                    if (field.get(object) != null)
                        return false;
                }
            }
        } catch (IllegalAccessException e) {
            log.info("isAllAttributeEmpty", e);
        }
        return true;
    }

    /**
     * 判断当前object某一属性为空 全部不为空返回false
     *
     * @param object
     * @return
     */
    public static boolean isAnyAttributeEmpty(Object object) {
        Class<? extends Object> clazz=object.getClass();
        Field[] fieldArray=clazz.getDeclaredFields();
        AccessibleObject.setAccessible(fieldArray, true);
        try {
            for (Field field : fieldArray) {
                if (field.getType() == List.class) {
                    if (((List) field.get(object)).size() == 0)
                        return true;
                } else {
                    if (field.get(object) == null)
                        return true;
                }
            }
        } catch (IllegalAccessException e) {
            log.info("isAllAttributeEmpty", e);
        }
        return false;
    }

    /**
     * 获得所有的field值
     *
     * @param object 具体获取值的实体
     *
     * @return Map集合
     */
    public static Map<String,Object> getFieldValues(Object object){
        Map<String,Object> map=new HashMap<>();
        Class<? extends Object> clazz = object.getClass();
        Field[] fieldArray=clazz.getDeclaredFields();
        AccessibleObject.setAccessible(fieldArray, true);
        try {
            for(Field field:fieldArray){
                if(field.get(object)!=null)
                    map.put(field.getName(),field.get(object));
            }
        }catch(IllegalAccessException e){
            log.info("getFieldValues",e);
        }
        return map;
    }

    /**
     *获得所有的某个field值
     *
     * @param object 具体获取值的实体
     * @param fieldName field名字
     *
     * @return object值
     */
    public static Object getFieldValue(Object object,String fieldName){
        Class<? extends Object> clazz = object.getClass();
        Object objectReturn=null;
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            objectReturn=field.get(object);
        }catch(Exception e){
            log.info("getFieldValue",e);
        }
        return objectReturn;
    }

    /**
     * 反射触发方法
     *
     * @param object 触发方法的具体执行类
     * @param methodName 方法名
     * @param parameterTypes 参数类型
     * @param parameters 参数
     *
     * @return 方法执行结果
     */
    public static Object invokeMethod(Object object,String methodName,Class<?>[] parameterTypes,Object[] parameters)throws OperationsException{
        Class<? extends Object> clazz = object.getClass();
        Object objectReturn=null;
        try {
            Method method = clazz.getMethod(methodName, parameterTypes);
            objectReturn=method.invoke(object,parameters);
            return objectReturn;
        }catch(Exception e){
            log.info("invokeMethod",e);
            throw new OperationsException();
        }
    }

    /**
     * 反射设置字段值
     *
     * @param object 需要设置的实体
     * @param fieldName 设置字段名
     * @param value 设置的值
     */
    public static void setFieldValue(Object object,String fieldName,Object value)throws OperationsException{
        Class<? extends Object> clazz = object.getClass();
        try{
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(object,value);
        }catch(Exception e){
            log.info("setFieldValue",e);
            throw new OperationsException();
        }
    }

    /**
     * 反射设置实体所有字段值
     *
     * @param object 需要设置的实体
     * @param parameterMap 参数集合
     */
    public static void setFieldValues(Object object,Map parameterMap)throws OperationsException{
        Class<? extends Object> clazz = object.getClass();
        Iterator<String> it=parameterMap.keySet().iterator();
        while(it.hasNext()){
            String fieldName=it.next();
            try {
                Field field = clazz.getDeclaredField(fieldName);
                if(field!=null){
                    field.setAccessible(true);
                    field.set(object,parameterMap.get(fieldName));
                }
            }catch(Exception e){
                log.info("setFieldValues",e);
                throw new OperationsException();
            }
        }
    }

    public static Object invokeGetMethod(Class<?> claszz, Object o, String name) {
        Object ret = null;
        try {
            Method method = claszz.getMethod("get"
                    + StringUtil.firstCharUpperCase(name));
            ret = method.invoke(o);
        } catch (NoSuchMethodException | SecurityException
                | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            log.info("claszz:" + claszz + ",name:" + name,e);
        }
        return ret;
    }

    /**
     * object 属性名称及属性值组装为 Map，再用Map转Json字符串。 组装规则： 只组装String类型，且不为常量的字段，
     * 组装时若属性值为空或为null，则不加入Map
     *
     * @param object
     * @return
     */
    public static Map<String, String> fieldValueToMap(Object object, String[] paramNames) {
        return fieldValueToMap(object, paramNames, true);
    }

    /**
     * object 属性名称及属性值组装为 Map，再用Map转Json字符串。 组装规则： 只组装String类型，且不为常量的字段，
     * 组装时若isTrim为true 且 属性值为空或为null，则不加入Map
     *
     * @param object
     * @param paramNames
     * @param is_trim
     * @return
     */
    public static Map<String, String> fieldValueToMap(Object object, String[] paramNames, boolean is_trim) {
        Map<String, String> map = new HashMap<String, String>();
        for (String name : paramNames) {
            Object o = ReflectUtil.invokeGetMethod(object.getClass(), object,
                    name);
            String value = StringUtil.isNull(o);
            // 是否去空
            if (is_trim && "".equals(value)) {
                continue;
            }
            map.put(name, value);
        }
        log.debug("数组反射结果：" + map.toString());
        return map;
    }
}
