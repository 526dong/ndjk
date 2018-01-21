package com.ndjk.api.brandinteraction.controller;

import com.ndjk.cl.brandinteraction.model.VotePlayer;
import com.ndjk.cl.brandinteraction.service.VotePlayerService;
import com.ndjk.cl.brandservice.model.resp.BaseResponseModel;
import com.ndjk.cl.brandservice.model.resp.JsonResult;
import com.ndjk.cl.sys.model.SysAppConfig;
import com.ndjk.cl.sys.service.SysAppConfigService;
import com.ndjk.cl.utils.GsonUtil;
import com.ndjk.cl.utils.UploadFileUtil;
import com.ndjk.cl.utils.dto.UploadFileRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by wl on 2018/1/20.
 */
@Controller
public class VoteController {
    @Autowired
    private VotePlayerService votePlayerService;

    /**
     * @Author: wl
     * @Description: 插入投票数据
     * @Date: 2018/1/20  10:36
     * @Version: 2.0
     */
    @RequestMapping(value = "/brand/interaction/vote/voteInsert")
    @ResponseBody
    public Object voteInsert(@RequestParam(value = "params", required = true) String params) {
        VotePlayer votePlayer = GsonUtil.fromJson(VotePlayer.class, params);
        int insert = votePlayerService.insert(votePlayer);
        if (insert > 0) {
            return JsonResult.ok(insert, "投票成功");
        }
        return JsonResult.error(400, "投票失败");
    }

    //票数展示
    @RequestMapping(value = "/brand/interaction/vote/votePlayerList")
    @ResponseBody
    public Object votePlayerList(@RequestParam String searchParams,@RequestParam Integer currentPage, @RequestParam Integer pageSize) {
        VotePlayer votePlayer = GsonUtil.fromJson(VotePlayer.class, searchParams);
        if(votePlayer==null){
            votePlayer=new VotePlayer();
        }
        List<VotePlayer> votePlayers = votePlayerService.listAll(votePlayer,currentPage, pageSize);
        if (votePlayers!=null&&votePlayers.size()>0) {
            return JsonResult.ok(votePlayers, "查询成功");
        }
        return JsonResult.error(400, "查询失败");
    }

}
