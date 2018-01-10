package com.ndjk.manage.system;

import com.ndjk.cl.brandservice.model.BrandService;
import com.ndjk.cl.brandservice.model.resp.JsonResult;
import com.ndjk.cl.brandservice.service.BrandServiceService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by zfwlz on 2018/1/10.
 */
@Controller
@RequestMapping(value="/manage")
public class SystemSettingController {

    @Autowired
    private BrandServiceService brandServiceService;

    /**
     * 新增服务
     * @param name
     * @param type
     * @param sort
     * @return
     */
    @RequestMapping("/sysconfig/addservice")
    @ResponseBody
    public JsonResult addBrandService(String name,Integer type,Integer sort){
        if(StringUtils.isBlank(name) || type== null || type ==0 || sort ==null || sort == 0){
            return new JsonResult(400,"新增失败，请输入正确参数");
        }
        BrandService brandService = new BrandService();
        brandService.setName(name);
        brandService.setSort(sort);
        brandService.setType(type);
        int id = this.brandServiceService.insertSelective(brandService);
        return new JsonResult(200,id,"操作成功");
    }

    /**
     * 修改服务
     * @param name
     * @param type
     * @param brId
     * @return
     */
    @RequestMapping("/sysconfig/updateservice")
    @ResponseBody
    public JsonResult updateBrandService(String name,Integer type,Integer brId){
        if(brId ==null || brId == 0){
            return new JsonResult(400,"修改失败，服务不存在");
        }
        BrandService brandService = new BrandService();
        brandService.setName(name);
        brandService.setId(brId);
        brandService.setType(type);
        this.brandServiceService.updateSelective(brandService);
        return new JsonResult(200,"操作成功");
    }

    /**
     * 删除服务
     * @param brId
     * @return
     */
    @RequestMapping("/sysconfig/delservice")
    @ResponseBody
    public JsonResult delservice(Integer brId){
        if(brId ==null || brId == 0){
            return new JsonResult(400,"删除失败，服务不存在");
        }
        this.brandServiceService.deleteSelective(brId);
        return new JsonResult(200,"操作成功");
    }

    @RequestMapping("/sysconfig/sortservice")
    @ResponseBody
    public JsonResult sortservice(String idStr){
        if(StringUtils.isBlank(idStr)){
            return new JsonResult(400,"操作失败，请输入正确参数");
        }
        this.brandServiceService.sortBrandServcie(idStr);
        return new JsonResult(200,"操作成功");
    }
}
