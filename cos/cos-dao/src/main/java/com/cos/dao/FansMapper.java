package com.cos.dao;

import com.cos.model.Fans;
import com.cos.model.FansExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FansMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fans
     *
     * @mbg.generated Sat Jun 03 16:14:36 CST 2017
     */
    long countByExample(FansExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fans
     *
     * @mbg.generated Sat Jun 03 16:14:36 CST 2017
     */
    int deleteByExample(FansExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fans
     *
     * @mbg.generated Sat Jun 03 16:14:36 CST 2017
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fans
     *
     * @mbg.generated Sat Jun 03 16:14:36 CST 2017
     */
    int insert(Fans record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fans
     *
     * @mbg.generated Sat Jun 03 16:14:36 CST 2017
     */
    int insertSelective(Fans record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fans
     *
     * @mbg.generated Sat Jun 03 16:14:36 CST 2017
     */
    List<Fans> selectByExample(FansExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fans
     *
     * @mbg.generated Sat Jun 03 16:14:36 CST 2017
     */
    Fans selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fans
     *
     * @mbg.generated Sat Jun 03 16:14:36 CST 2017
     */
    int updateByExampleSelective(@Param("record") Fans record, @Param("example") FansExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fans
     *
     * @mbg.generated Sat Jun 03 16:14:36 CST 2017
     */
    int updateByExample(@Param("record") Fans record, @Param("example") FansExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fans
     *
     * @mbg.generated Sat Jun 03 16:14:36 CST 2017
     */
    int updateByPrimaryKeySelective(Fans record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fans
     *
     * @mbg.generated Sat Jun 03 16:14:36 CST 2017
     */
    int updateByPrimaryKey(Fans record);
    
    List<Fans> selectPageByExample(FansExample example);
}