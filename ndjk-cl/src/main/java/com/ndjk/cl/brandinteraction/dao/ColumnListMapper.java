package com.ndjk.cl.brandinteraction.dao;


import com.ndjk.cl.brandinteraction.model.ColumnList;

public interface ColumnListMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table nd_column_list
     *
     * @mbggenerated Sun Jan 21 14:57:25 CST 2018
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table nd_column_list
     *
     * @mbggenerated Sun Jan 21 14:57:25 CST 2018
     */
    int insert(ColumnList record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table nd_column_list
     *
     * @mbggenerated Sun Jan 21 14:57:25 CST 2018
     */
    int insertSelective(ColumnList record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table nd_column_list
     *
     * @mbggenerated Sun Jan 21 14:57:25 CST 2018
     */
    ColumnList selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table nd_column_list
     *
     * @mbggenerated Sun Jan 21 14:57:25 CST 2018
     */
    int updateByPrimaryKeySelective(ColumnList record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table nd_column_list
     *
     * @mbggenerated Sun Jan 21 14:57:25 CST 2018
     */
    int updateByPrimaryKey(ColumnList record);
}