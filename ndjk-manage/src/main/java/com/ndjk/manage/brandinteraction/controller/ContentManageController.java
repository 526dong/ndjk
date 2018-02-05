package com.ndjk.manage.brandinteraction.controller;

import com.google.gson.reflect.TypeToken;
import com.ndjk.cl.brandinteraction.model.ColumnList;
import com.ndjk.cl.brandinteraction.model.ContentManage;
import com.ndjk.cl.brandinteraction.model.vo.ContentManageVo;
import com.ndjk.cl.brandinteraction.service.ColumnListService;
import com.ndjk.cl.brandinteraction.service.ContentManageService;
import com.ndjk.cl.brandservice.model.Kindergarten;
import com.ndjk.cl.brandservice.model.resp.BaseResponseModel;
import com.ndjk.cl.brandservice.model.resp.JsonResult;
import com.ndjk.cl.sys.model.SysAppConfig;
import com.ndjk.cl.sys.service.SysAppConfigService;
import com.ndjk.cl.utils.GsonUtil;
import com.ndjk.cl.utils.StringUtil;
import com.ndjk.cl.utils.UploadFileUtil;
import com.ndjk.cl.utils.dto.UploadFileRes;
import com.ndjk.manage.brandservice.controller.BrandServiceController;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.*;

/**
 * Created by wl on 2018/1/21.
 */
@Controller
public class ContentManageController {

    private static Logger logger = LogManager.getLogger(ContentManageController.class);
    @Autowired
    private ContentManageService contentManageService;
    @Autowired
    private ColumnListService columnListService;
    @Autowired
    private SysAppConfigService sysAppConfigService;

    //内容列表
    @RequestMapping(value = "/manage/brand/interaction/listContentManage")
    @ResponseBody
    public JsonResult listContentManage(@RequestParam String searchParams, @RequestParam Integer page, @RequestParam Integer size,
                                        HttpServletResponse response) {
        ContentManageVo contentManageVo = GsonUtil.fromJson(ContentManageVo.class, searchParams);
        try {
            if(contentManageVo==null){
                contentManageVo=new ContentManageVo();
            }
            List<ContentManage> contentManages = contentManageService.listContent(contentManageVo, page-1, size);
            if (contentManages != null && contentManages.size() > 0) {
                return JsonResult.ok(contentManages, "内容查询成功");
            } else {
                return JsonResult.error(400, "内容查询出错");
            }
        } catch (Exception e) {
            logger.error("查询内容列表异常", e);
            return JsonResult.error(400, "内容查询出错");
        }
    }

    //内容修改
    @RequestMapping(value = "/manage/brand/interaction/updateContentManage")
    @ResponseBody
    public JsonResult updateContentManage(ContentManage contentManage) {
        contentManage.setUpdateTime(new Date());
        int i = contentManageService.updateContentManage(contentManage);
        if (i > 0) {
            return JsonResult.ok(i, "内容修改成功");
        } else {
            return JsonResult.error(400, "内容修改出错");
        }
    }

    //内容删除
    @RequestMapping(value = "/manage/brand/interaction/deleteContentManage")
    @ResponseBody
    public JsonResult deleteContentManage(Long id) {
        int i = contentManageService.deleteContentManage(id);
        if (i > 0) {
            return JsonResult.ok(i, "内容删除成功");
        } else {
            return JsonResult.error(400, "内容删除出错");
        }
    }

    //发布内容接口
    @RequestMapping(value = "/manage/brand/interaction/sendContentManage")
    @ResponseBody
    public JsonResult sendContentManage(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "files", required = false) List<MultipartFile> files) {
        response.setContentType("text/html;charset=utf-8");
        //创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //判断 request 是否有文件上传,即多部分请求
        if (multipartResolver.isMultipart(request)) {
            //转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            ContentManage cm = new ContentManage();
            //获得formdata对象中自定义的一些属性,是枚举类型
            String title = multiRequest.getParameter("title");
            cm.setTitle(title);
            String columnId = multiRequest.getParameter("columnId");
            cm.setColumnId(Long.valueOf(columnId));
            String columnType = multiRequest.getParameter("columnType");
            cm.setColumnType(columnType);
            String author = multiRequest.getParameter("author");
            cm.setAuthor(author);
            String pictureUrl = multiRequest.getParameter("pictureUrl");
            cm.setPictureUrl(pictureUrl);
            String otherUrl = multiRequest.getParameter("otherUrl");
            cm.setOtherUrl(otherUrl);
            String contentes = multiRequest.getParameter("contentes");
            cm.setContentes(contentes);
            String thumbsNum = multiRequest.getParameter("thumbsNum");
            cm.setThumbsNum(Integer.valueOf(thumbsNum));
            String viewsNum = multiRequest.getParameter("viewsNum");
            cm.setViewsNum(Integer.valueOf(viewsNum));
            String collectionNum = multiRequest.getParameter("collectionNum");
            cm.setCollectionNum(Integer.valueOf(collectionNum));
            String contentType = multiRequest.getParameter("contentType");
            cm.setContentType(contentType);

            MultiValueMap<String, MultipartFile> multiValueMap = multiRequest.getMultiFileMap();
            String filePathes = "";
            for (MultipartFile file : files) {
                if (file == null) {
                    return JsonResult.error(400, "上传参数不能为空");
                }
                //这里上传多张图片
                StringBuffer url = new StringBuffer("");
                Map<String, String> map1 = new HashMap<>();
                if (file != null) {
//                    //取得当前上传文件的文件名称
                    String picName = file.getOriginalFilename();
                    List<UploadFileRes> listModel = new ArrayList<>();
                    String s = File.separator;
                    String filePath = s + "data" + s + "image" + s + System.currentTimeMillis() + s + picName;
                    UploadFileUtil.saveMultipartFile(listModel, file, filePath);
                    if (s.equals("\\")) {
                        filePath = "D:" + filePath;
                    }
                    filePathes+=filePath+";";
                }
            }
            cm.setPictureUrl(filePathes);
            cm.setCreateTime(new Date());
            cm.setUpdateTime(new Date());
            int i = contentManageService.insertContentManage(cm);
            if (i > 0) {
                return JsonResult.ok(i, "发布内容成功");
            } else {
                return JsonResult.error(400, "发布内容出错");
            }

        }
        return JsonResult.ok(200, "发布内容成功");
    }

    //查询栏目
    @RequestMapping(value = "/manage/brand/columnList/listColumnList")
    @ResponseBody
    public JsonResult listColumnList() {
        List<ColumnList> columnLists = columnListService.listAll();
        if (columnLists != null && columnLists.size() > 0) {
            return JsonResult.ok(columnLists, "栏目查询成功");
        } else {
            return JsonResult.error(400, "栏目查询出错");
        }
    }

    //查询栏目类型
    @RequestMapping(value = "/manage/brand/columnType/listColumnType")
    @ResponseBody
    public JsonResult listColumnType(Long id) {
        String type = "";
        if (String.valueOf(id).equals("1")) {
            type = "class_";
        } else if ("2".equals(String.valueOf(id))) {
            type = "health_";
        }
        List<SysAppConfig> sysAppConfigs = sysAppConfigService.listByCode(type);
        if (sysAppConfigs != null || sysAppConfigs.size() > 0) {
            return JsonResult.ok(sysAppConfigs, "栏目类型查询成功");
        } else {
            return JsonResult.error(400, "栏目类型查询出错");
        }
    }


    @RequestMapping(value = "/manage/brand/interaction/selectContentManage")
    @ResponseBody
    public JsonResult selectContentManage(Long id) {
        try {
            ContentManage contentManage = contentManageService.selectContentManage(id);
            if (contentManage != null) {
                String pictureUrl = contentManage.getPictureUrl();
                String[] split = pictureUrl.split(";");
                contentManage.setPictureUrlList(split);
                return JsonResult.ok(contentManage, "内容查询成功");
            } else {
                return JsonResult.error(400, "内容查询出错");
            }
        } catch (Exception e) {
            logger.error("查询内容列表异常", e);
            return JsonResult.error(400, "内容查询出错");
        }
    }
}
