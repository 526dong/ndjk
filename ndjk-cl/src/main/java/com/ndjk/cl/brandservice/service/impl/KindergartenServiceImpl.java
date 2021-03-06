package com.ndjk.cl.brandservice.service.impl;

import com.ndjk.cl.brandservice.dao.KindergartenMapper;
import com.ndjk.cl.brandservice.model.Kindergarten;
import com.ndjk.cl.brandservice.model.resp.JsonResult;
import com.ndjk.cl.brandservice.model.searchModel.SearchKindergartensModel;
import com.ndjk.cl.brandservice.service.KindergartenService;
import com.ndjk.cl.utils.RdPage;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by zfwlz on 2017/12/20.
 */
@Service("kindergartenService")
public class KindergartenServiceImpl implements KindergartenService{
    @Autowired
    private KindergartenMapper kindergartenMapper;

    /**
     * 新增
     * @param kindergarten
     */
    public void insertSelective(Kindergarten kindergarten){
        this.kindergartenMapper.insertSelective(kindergarten);
    }


    /**
     * 删除方法
     * @param id
     */
    public void deleteById(int id){
        this.kindergartenMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改
     * @param kindergarten
     */
    public void updateSelective(Kindergarten kindergarten){
        this.kindergartenMapper.updateByPrimaryKeySelective(kindergarten);
    }

    /**
     * 重置密码
     * @param kindergarten
     */
    @Override
    public void updatePwdById(Kindergarten kindergarten) {
        this.kindergartenMapper.updatePwdById(kindergarten);
    }

    /**
     * 根据幼儿园名称和状态查询
     * @param kgName
     * @param state
     * @return
     */
    public JsonResult selectListByNameAndState(String kgName, String state, int page, int size){

        List<Kindergarten> kindergartens= this.kindergartenMapper.selectListByNameAndState(kgName,state);
        RdPage rdPage = new RdPage(kindergartens.size(),page,size);
        SearchKindergartensModel searchKindergartensModel = new SearchKindergartensModel(page, size);
        searchKindergartensModel.setKgName(kgName);
        if(StringUtils.isNotBlank(state)){
            searchKindergartensModel.setStatus(Integer.valueOf(state));
        }
        List<Kindergarten> kindergartenList = this.kindergartenMapper.selectKindergartenList(searchKindergartensModel);
        if(kindergartenList != null && kindergartenList.size() >0){
            for(Kindergarten kindergarten:kindergartenList){
                if(kindergarten.getStatus() == 1){
                    kindergarten.setStatusStr("可用");
                }
                if(kindergarten.getStatus() == 1){
                    kindergarten.setStatusStr("禁用");
                }
            }
        }

        JsonResult jsonResult = new JsonResult(200,"操作成功");
        jsonResult.setData(kindergartenList);
        return jsonResult;
    }

    /**
     * 基本查询
     * @param id
     * @return
     */
    public Kindergarten selectById(int id){
        return this.kindergartenMapper.selectByPrimaryKey(id);
    }

    public Kindergarten selectByLoginName(String loginName){
        return  this.kindergartenMapper.selectByLoginName(loginName);
    }

    /**
     * 修改密码
     * @param kgId
     * @param oldPwd
     * @param newPwd
     */
    public String updatePassWord(int kgId,String oldPwd,String newPwd){
        Kindergarten kindergarten = this.kindergartenMapper.selectByPrimaryKey(kgId);
        if(kindergarten == null){
            return "用户不存在";
        }
        if(oldPwd == null){
            return "旧密码不正确";
        }
        if(newPwd == null){
            return "新密码不能为空";
        }
        if(!oldPwd.equals(kindergarten.getPassword())){
            return "密码错误";
        }
        Kindergarten kindergarten1 = new Kindergarten();
        kindergarten1.setPassword(newPwd);
        kindergarten1.setId(kindergarten.getId());
        this.kindergartenMapper.updateByPrimaryKeySelective(kindergarten1);
        return null;
    }
}
