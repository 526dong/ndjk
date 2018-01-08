package com.ndjk.cl.operaterecord.service.impl;
	
import com.ndjk.cl.operaterecord.model.OperateRecord;
import com.ndjk.cl.operaterecord.service.OperateRecordService;
import com.ndjk.cl.operaterecord.dao.OperateRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;	
import java.util.*;	
	
/**
 * @author xzd
 */
@Service
public class OperateRecordServiceImpl implements OperateRecordService {	
	@Autowired
	private OperateRecordMapper operateRecordMapper;

	//主键获取	
	@Override	
	public OperateRecord getById(Long id) {	
		return operateRecordMapper.selectByPrimaryKey(id);	
	}

	@Override
	public List<OperateRecord> getList() {
		return null;
	}

	@Override
	public List<OperateRecord> getList(OperateRecord model) {
		return null;
	}

	@Override
	public OperateRecord getByModel(OperateRecord model) {
		return null;
	}

	//保存对象
	@Override	
	public int save(OperateRecord model) {	
		return operateRecordMapper.insert(model);	
	}	
	
	//更新对象	
	@Override	
	public int update(OperateRecord model) {	
		return operateRecordMapper.updateByPrimaryKey(model);	
	}	
		
	//删除对象	
	@Override	
	public int deleteById(Long id) {	
		return operateRecordMapper.deleteByPrimaryKey(id);	
	}

	@Override
	public Map<String, Object> getOther() {
		return null;
	}
}
