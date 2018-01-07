package com.ndjk.manage.operaterecord.controller;

import com.ndjk.cl.brandservice.model.resp.JsonResult;
import com.ndjk.cl.operaterecord.model.OperateRecord;
import com.ndjk.cl.operaterecord.service.OperateRecordService;
import com.ndjk.manage.aspect.Record;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 日志操作
 * @author ymj
 */
@Controller
@RequestMapping(value="/manage")
public class OperateRecordController {
	private static Logger logger = LogManager.getLogger(OperateRecordController.class);

	@Autowired
	OperateRecordService operateRecordService;

	/**
	 * 查询日志列表
	 * @return
	 */
	@RequestMapping(value = "/record/findAll", method = RequestMethod.GET)
	@Record(operateType="查询操作日志列表")
	@ResponseBody
	public JsonResult findAllRecord(HttpServletRequest request){
		//查询条件
		String name = request.getParameter("name");
		String state = request.getParameter("state");
		List<OperateRecord> operateRecords = new ArrayList<>();
		try {
			operateRecords = operateRecordService.getList();
			return JsonResult.ok(operateRecords, "日志列表查询成功");
		} catch (Exception e) {
			logger.error("操作日志模块报错：日志列表查询异常!", e);
			return JsonResult.error(400, "日志列表查询失败");
		}
	}
	
}
