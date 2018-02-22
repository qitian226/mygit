package com.cos.dao;

import com.cos.model.CityCode;
import com.cos.model.CityCodeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CityCodeMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table city_code
	 * @mbg.generated  Tue Nov 07 16:22:34 CST 2017
	 */
	long countByExample(CityCodeExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table city_code
	 * @mbg.generated  Tue Nov 07 16:22:34 CST 2017
	 */
	int deleteByExample(CityCodeExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table city_code
	 * @mbg.generated  Tue Nov 07 16:22:34 CST 2017
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table city_code
	 * @mbg.generated  Tue Nov 07 16:22:34 CST 2017
	 */
	int insert(CityCode record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table city_code
	 * @mbg.generated  Tue Nov 07 16:22:34 CST 2017
	 */
	int insertSelective(CityCode record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table city_code
	 * @mbg.generated  Tue Nov 07 16:22:34 CST 2017
	 */List<CityCode> selectByExample(CityCodeExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table city_code
	 * @mbg.generated  Tue Nov 07 16:22:34 CST 2017
	 */
	CityCode selectByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table city_code
	 * @mbg.generated  Tue Nov 07 16:22:34 CST 2017
	 */int updateByExampleSelective(@Param("record") CityCode record,@Param("example") CityCodeExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table city_code
	 * @mbg.generated  Tue Nov 07 16:22:34 CST 2017
	 */int updateByExample(@Param("record") CityCode record,@Param("example") CityCodeExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table city_code
	 * @mbg.generated  Tue Nov 07 16:22:34 CST 2017
	 */
	int updateByPrimaryKeySelective(CityCode record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table city_code
	 * @mbg.generated  Tue Nov 07 16:22:34 CST 2017
	 */
	int updateByPrimaryKey(CityCode record);
}