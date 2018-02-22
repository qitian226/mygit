package com.cos.dao;

import com.cos.model.FansGroup;
import com.cos.model.FansGroupExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FansGroupMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fans_group
     *
     * @mbg.generated Sat Jun 03 16:14:36 CST 2017
     */
    long countByExample(FansGroupExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fans_group
     *
     * @mbg.generated Sat Jun 03 16:14:36 CST 2017
     */
    int deleteByExample(FansGroupExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fans_group
     *
     * @mbg.generated Sat Jun 03 16:14:36 CST 2017
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fans_group
     *
     * @mbg.generated Sat Jun 03 16:14:36 CST 2017
     */
    int insert(FansGroup record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fans_group
     *
     * @mbg.generated Sat Jun 03 16:14:36 CST 2017
     */
    int insertSelective(FansGroup record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fans_group
     *
     * @mbg.generated Sat Jun 03 16:14:36 CST 2017
     */
    List<FansGroup> selectByExample(FansGroupExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fans_group
     *
     * @mbg.generated Sat Jun 03 16:14:36 CST 2017
     */
    FansGroup selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fans_group
     *
     * @mbg.generated Sat Jun 03 16:14:36 CST 2017
     */
    int updateByExampleSelective(@Param("record") FansGroup record, @Param("example") FansGroupExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fans_group
     *
     * @mbg.generated Sat Jun 03 16:14:36 CST 2017
     */
    int updateByExample(@Param("record") FansGroup record, @Param("example") FansGroupExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fans_group
     *
     * @mbg.generated Sat Jun 03 16:14:36 CST 2017
     */
    int updateByPrimaryKeySelective(FansGroup record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fans_group
     *
     * @mbg.generated Sat Jun 03 16:14:36 CST 2017
     */
    int updateByPrimaryKey(FansGroup record);
}