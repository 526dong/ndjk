package com.ndjk.cl.brandservice.service;

import com.ndjk.cl.brandservice.model.Kindergarten;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zfwlz on 2017/12/20.
 */
public interface KindergartenService {
    /**
     * 新增
     * @param kindergarten
     */
    void insertSelective(Kindergarten kindergarten);

    /**
     * 删除方法
     * @param id
     */
    void deleteById(int id);

    /**
     * 修改
     * @param kindergarten
     */
    void updateSelective(Kindergarten kindergarten);

    /**
     * 重置密码
     * @param kindergarten
     */
    void updatePwdById(Kindergarten kindergarten);

    /**
     * 根据幼儿园名称和状态查询
     * @param kgName
     * @param state
     * @return
     */
    List<Kindergarten> selectListByNameAndState(String kgName,String state);

    /**
     * 基本查询
     * @param id
     * @return
     */
    Kindergarten selectById(int id);

    Kindergarten selectByLoginName(String loginName);

    /**
     * 修改密码
     * @param kgId
     * @param oldPwd
     * @param newPwd
     */
    String updatePassWord(int kgId,String oldPwd,String newPwd);
}
