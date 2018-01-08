package com.ndjk.manage.aspect;

import com.ndjk.cl.operaterecord.model.OperateRecord;
import com.ndjk.cl.operaterecord.service.OperateRecordService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


/**
 * 用于记录所有系统操作的切面类
 * @author Created by xzd on 2018/1/7
 */
@Aspect
@Component
public class OperateRecordAspect {
    //注入service保存日志
    @Autowired
    private OperateRecordService operateRecordService;

    //controller切点层-manage所有子包里的Controller类的任意方法的执行
    @Pointcut("execution(* com.ndjk.manage.*.controller.*Controller.*(..))")
    public void controllerAspect() {
    }

    /*** 前置通知 用于拦截Controller层记录用户的操作
     * @param joinPoint 切点
     */
    @Before(value = "com.ndjk.manage.aspect.OperateRecordAspect.controllerAspect()")
    public void doBefore(JoinPoint joinPoint) {
        System.out.println("before==========" + joinPoint);
    }

    /**
     * 后置通知 用于拦截Controller层记录用户的操作
     * @param joinPoint 切点
     */
    @After("controllerAspect()")
    public void after(JoinPoint joinPoint) {
        System.out.println("========后置通知开始=============");
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        if(request.getRequestURI().indexOf("logout") == -1){
            //获取操作记录
            OperateRecord record = OperateRecordUtils.getRecord(joinPoint);
            if (record == null) {
                throw new RuntimeException("系统操作日志模块报错：操作记录为空");
            }
            //获取请求路径
            String urlPath = record.getOperateUrlPath();
            //获取请求参数
            Map<String, String[]> paramMap = request.getParameterMap();
            //操作内容
            String operateContent = "";
            /*try {
                //deal null
                if (paramMap == null || paramMap.isEmpty()) {
                    throw new RuntimeException("系统操作日志模块报错：请求参数为空");
                }
                //基础模块：企业、角色...
                //服务器和本地测试路径不一致，
                String paramStr = urlPath.replace("//", "/");
                String objectAttr1 = urlPath.split("/")[1];
                String objectAttr2 = urlPath.split("/")[2];
                //如果id且不为空，查询id获取相应的名称
                if (paramMap.containsKey("id") && paramMap.get("id")[0] != null && paramMap.get("id")[0].trim() != "") {
                }
            } catch (Exception e) {
                e.printStackTrace();
            }*/
            try {
                //设置对象的属性，并添加日志
                record.setOperateContent(record.getOperateType() + "" + operateContent);
                operateRecordService.save(record);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
