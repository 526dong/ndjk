package com.ndjk.cl.brandservice.dao;

import com.ndjk.cl.brandservice.model.Kindergarten;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface KindergartenMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Kindergarten record);

    int insertSelective(Kindergarten record);

    Kindergarten selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Kindergarten record);

    int updateByPrimaryKey(Kindergarten record);

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
    List<Kindergarten> selectListByNameAndState(@Param("kgName") String kgName, @Param("status") String state);

    Kindergarten selectByLoginName(String loginName);
}