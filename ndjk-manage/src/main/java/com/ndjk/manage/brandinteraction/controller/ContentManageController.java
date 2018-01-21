package com.ndjk.manage.brandinteraction.controller;

import com.ndjk.cl.brandinteraction.model.ContentManage;
import com.ndjk.cl.brandinteraction.service.ContentManageService;
import com.ndjk.cl.brandservice.model.Kindergarten;
import com.ndjk.cl.brandservice.model.resp.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * Created by wl on 2018/1/21.
 */
@Controller
public class ContentManageController {
    @Autowired
    private ContentManageService contentManageService;
    /**
     * 发布内容接口
     * @param contentManage
     * @return
     */
    @RequestMapping(value = "/brand/interaction/addContentManage")
    @ResponseBody
    public JsonResult addContentManage(ContentManage contentManage) {
        int i = contentManageService.insertContentManage(contentManage);
        if(i>0){
            return JsonResult.ok(i,"发布内容成功");
        }else {
            return JsonResult.error(400,"发布内容出错");
        }
    }
    //内容列表
    @RequestMapping(value = "/brand/interaction/listContentManage")
    @ResponseBody
    public JsonResult listContentManage(ContentManage contentManage,int page,int size) {
        List<ContentManage> contentManages = contentManageService.listContent(contentManage, page, size);
        if(contentManages!=null&&contentManages.size()>0){
            return JsonResult.ok(contentManages,"内容查询成功");
        }else {
            return JsonResult.error(400,"内容查询出错");
        }
    }
    //内容修改
    @RequestMapping(value = "/brand/interaction/updateContentManage")
    @ResponseBody
    public JsonResult updateContentManage(ContentManage contentManage) {
        int i = contentManageService.updateContentManage(contentManage);
        if(i>0){
            return JsonResult.ok(i,"内容修改成功");
        }else {
            return JsonResult.error(400,"内容修改出错");
        }
    }
}
