package com.ndjk.api.brandinteraction.controller;

import com.ndjk.cl.brandinteraction.model.ThumbsViewDetail;
import com.ndjk.cl.brandinteraction.model.ThumbsViewList;
import com.ndjk.cl.brandinteraction.service.ThumbsViewListService;
import com.ndjk.cl.brandinteraction.service.ThumbsViewService;
import com.ndjk.cl.brandservice.model.OrderService;
import com.ndjk.cl.brandservice.model.ServiceOrder;
import com.ndjk.cl.brandservice.model.req.ApplyServiceReq;
import com.ndjk.cl.brandservice.model.resp.BaseResponseModel;
import com.ndjk.cl.brandservice.model.resp.JsonResult;
import com.ndjk.cl.sys.model.SysAppConfig;
import com.ndjk.cl.sys.service.SysAppConfigService;
import com.ndjk.cl.utils.GsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by wl on 2018/1/12.
 */
@Controller
public class BrandInteractionController {
    @Autowired
    private SysAppConfigService SysAppConfigService;
    @Autowired
    private ThumbsViewService thumbsViewService;
    @Autowired
    private ThumbsViewListService thumbsViewListService;

    @RequestMapping(value = "/sysAppConfigService/matherClass")
    @ResponseBody
    public Object matherClass(@RequestParam(value = "code", required = false) String code) {
        List<SysAppConfig> sysAppConfigs = SysAppConfigService.listByCode(code);
        if (sysAppConfigs == null || sysAppConfigs.size() <= 0) {
            return new BaseResponseModel(400, "操作失败");
        }
        return JsonResult.ok(sysAppConfigs, "查询成功");
    }

    /**
     * @param
     * @Author: wl
     * @Description: 插入点赞详细记录
     * @Date: 2018/1/12  16:22
     * @Version: 2.0
     */
    @RequestMapping(value = "/thumbsViewService/insertThumbsView")
    @ResponseBody
    public Object insertThumbsView(@RequestParam String searchParams) {
        ThumbsViewDetail thumbsViewDetail = GsonUtil.fromJson(ThumbsViewDetail.class, searchParams);

        int insert = thumbsViewService.insert(thumbsViewDetail);
        if (insert > 0) {
            //点赞总表点赞数加1
            thumbsViewListService.findSelective()
            thumbsViewListService.updateByPrimaryKeySelective()
            return JsonResult.ok(insert, "插入成功");
        }
        return JsonResult.error(400, "插入失败");
    }
}
