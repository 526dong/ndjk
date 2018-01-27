package com.ndjk.manage.brandinteraction.controller;

import com.ndjk.cl.brandinteraction.model.ColumnList;
import com.ndjk.cl.brandinteraction.model.ContentManage;
import com.ndjk.cl.brandinteraction.service.ColumnListService;
import com.ndjk.cl.brandinteraction.service.ContentManageService;
import com.ndjk.cl.brandservice.model.Kindergarten;
import com.ndjk.cl.brandservice.model.resp.BaseResponseModel;
import com.ndjk.cl.brandservice.model.resp.JsonResult;
import com.ndjk.cl.sys.model.SysAppConfig;
import com.ndjk.cl.sys.service.SysAppConfigService;
import com.ndjk.cl.utils.StringUtil;
import com.ndjk.cl.utils.UploadFileUtil;
import com.ndjk.cl.utils.dto.UploadFileRes;
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
import java.io.File;
import java.util.*;

/**
 * Created by wl on 2018/1/21.
 */
@Controller
public class ContentManageController {
    @Autowired
    private ContentManageService contentManageService;
    @Autowired
    private ColumnListService columnListService;
    @Autowired
    private SysAppConfigService sysAppConfigService;

    /**
     * 发布内容接口
     *
     * @param contentManage
     * @return
     */
    @RequestMapping(value = "/mange/brand/interaction/addContentManage")
    @ResponseBody
    public JsonResult addContentManage(ContentManage contentManage) {
        int i = contentManageService.insertContentManage(contentManage);
        if (i > 0) {
            return JsonResult.ok(i, "发布内容成功");
        } else {
            return JsonResult.error(400, "发布内容出错");
        }
    }

    //内容列表
    @RequestMapping(value = "/manage/brand/interaction/listContentManage")
    @ResponseBody
    public JsonResult listContentManage(ContentManage contentManage, int page, int size) {
        List<ContentManage> contentManages = contentManageService.listContent(contentManage, page, size);
        if (contentManages != null && contentManages.size() > 0) {
            return JsonResult.ok(contentManages, "内容查询成功");
        } else {
            return JsonResult.error(400, "内容查询出错");
        }
    }

    //内容修改
    @RequestMapping(value = "/manage/brand/interaction/updateContentManage")
    @ResponseBody
    public JsonResult updateContentManage(ContentManage contentManage) {
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
            return JsonResult.ok(i, "内容修改成功");
        } else {
            return JsonResult.error(400, "内容修改出错");
        }
    }


    //发布内容接口
    @RequestMapping(value = "/manage/brand/interaction/sendContentManage")
    @ResponseBody
    public JsonResult sendContentManage(HttpServletRequest request, List<MultipartFile> files) {
        for (MultipartFile file : files) {
            if (file == null) {
                return JsonResult.error(400, "上传参数不能为空");
            }
        }

        //创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //判断 request 是否有文件上传,即多部分请求
        if (multipartResolver.isMultipart(request)) {
            //转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            Enumeration params = multiRequest.getParameterNames();
//            String upDir = null;
            ContentManage cm = new ContentManage();
            //获得formdata对象中自定义的一些属性,是枚举类型
            while (params.hasMoreElements()) {
//                String name = (String)params.nextElement();
//                upDir = multiRequest.getParameter("upDir");
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

            }

           /* Enumeration<String> parameterNames = request.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String s = parameterNames.nextElement();
                String contentType = request.getParameter("contentType");

            }*/
            //取得request中的所有文件名
//            Iterator<String> iter = multiRequest.getFileNames();
//            List<Map> list = new ArrayList<>();
            MultiValueMap<String, MultipartFile> multiValueMap = multiRequest.getMultiFileMap();
            List<MultipartFile> fileList = multiValueMap.get("file");
            String filePathes = "";
            for (MultipartFile file : fileList) {
                //这里上传多张图片
                StringBuffer url = new StringBuffer("");
                Map<String, String> map1 = new HashMap<>();
                if (file != null) {
//                    //取得当前上传文件的文件名称
                    String picName = file.getOriginalFilename();
//                    //如果名称不为“”,说明该文件存在，否则说明该文件不存在
//                    if (originalFilename != null && originalFilename.trim() != "") {
//                        String suffix = "";
//
//                        if (originalFilename.indexOf(".") > -1) {
//                            suffix = originalFilename.substring(originalFilename.lastIndexOf("."), originalFilename.length());
//                        }
//
//                        if (notImageFormat(suffix)) {
//                            map.put("statusInfo",imageFomat);
//                            return map;
//                        }
//                        if (isPassSize(file.getSize())) {
//                            map.put("stateInfo",imageSize);
//                            return map;
//                        }
//                        //重命名上传后的文件名
//                        String fileName = idWorker.nextId() + suffix;
//                        String dir = DateTimeUtil.formatDateTime("yy/MM/dd");
//
//                        if (!StringUtil.isBlank(upDir)){
//                            url.append("/").append( upDir);
//                        }
//                        url.append("/").append(dir).append("/");
//
//                        //定义上传路径
//                        String path = ParamUtil.imageServiceRealPath + url;
//                        //上传图片
//                        imageUploadService.uploadImage(path, fileName, file.getInputStream());
//                        String allUrl = url + fileName;
//                        map1.put("imagePath",url.toString());
//                        map1.put("imageName",fileName);
//                        map1.put("imageUrl",ParamUtil.imageServicePath + allUrl);
//                        list.add(map1);
//                    }
//                }
                    List<UploadFileRes> listModel = new ArrayList<>();
                    String s = File.separator;
                    String filePath = s + "data" + s + "image" + s + System.currentTimeMillis() + s + picName;
                    filePathes += filePath;
                    UploadFileUtil.saveMultipartFile(listModel, file, filePath);
                }
            }
            cm.setPictureUrl(filePathes);
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
    @RequestMapping(value = "/mange/brand/columnList/listColumnList")
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
    @RequestMapping(value = "/mange/brand/columnType/listColumnType")
    @ResponseBody
    public JsonResult listColumnType() {
        List<SysAppConfig> sysAppConfigs = sysAppConfigService.listByCode("class_");
        if (sysAppConfigs != null || sysAppConfigs.size() > 0) {
            return JsonResult.ok(sysAppConfigs, "栏目类型查询成功");
        } else {
            return JsonResult.error(400, "栏目类型查询出错");
        }
    }
}
