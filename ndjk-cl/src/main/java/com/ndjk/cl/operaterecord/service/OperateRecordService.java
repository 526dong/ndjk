package com.ndjk.cl.operaterecord.service;
	
import com.ndjk.cl.operaterecord.model.OperateRecord;
import java.util.*;	
	
public interface OperateRecordService {	
	//主键获取
	OperateRecord getById(Long id);	
		
	//获取无参list	
	List<OperateRecord> getList();	
		
	//获取有参数list	
	List<OperateRecord> getList(OperateRecord model);	
		
	//通过条件获取
	OperateRecord getByModel(OperateRecord model);	
	
	//保存对象	
	int save(OperateRecord model);	
	
	//更新对象	
	int update(OperateRecord model);	
		
	//删除对象	
	int deleteById(Long id);	
		
	//其他查询	
	Map<String, Object> getOther();	
}	
