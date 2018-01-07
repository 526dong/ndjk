package com.ndjk.manage.aspect;

import com.ndjk.cl.operaterecord.model.OperateRecord;
import org.aspectj.lang.JoinPoint;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 操作日志记录 工具类
 * @author Created by xzd on 2018/1/7
 */
public class OperateRecordUtils {
    private static OperateRecord operateRecord = null;

    /**
     * 获取日志对象/同步方法，安全
     * @return
     */
    public static synchronized OperateRecord getRecord(JoinPoint joinPoint) {
        if (operateRecord == null) {
            operateRecord = new OperateRecord();
        }

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();

        try {
            String targetName = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            Object[] arguments = joinPoint.getArgs();
            Class targetClass = Class.forName(targetName);
            Method[] methods = targetClass.getMethods();
            //操作内容
            String operateType = "";
            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    Class[] clazz = method.getParameterTypes();
                    if (clazz.length == arguments.length) {
                        operateType = method.getAnnotation(Record.class).operateType();
                        break;
                    }
                }
            }
            //null deal
            if (operateType == null || "".equals(operateType.trim())) {
                return null;
            }
            //操作人
            //operateRecord.setOperator(user.getLoginName());
            //操作类型
            operateRecord.setOperateContent(operateType);
            //操作人ip
            //operateRecord.setOperateIp(user.getLoginIp());
            //操作请求路径
            operateRecord.setOperateUrlPath(request.getRequestURI());
            //操作时间
            operateRecord.setOperateTime(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
            return operateRecord;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
