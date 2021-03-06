package com.cos.dao;

 
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.cos.model.BaseDictionaryitem;
import com.cos.model.BaseDictionaryitemExample;

public interface BaseDictionaryitemMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database service base_dictionaryitem
	 * @mbggenerated  Thu Aug 11 11:56:40 CST 2016
	 */
	int countByExample(BaseDictionaryitemExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database service base_dictionaryitem
	 * @mbggenerated  Thu Aug 11 11:56:40 CST 2016
	 */
	int deleteByExample(BaseDictionaryitemExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database service base_dictionaryitem
	 * @mbggenerated  Thu Aug 11 11:56:40 CST 2016
	 */
	int deleteByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database service base_dictionaryitem
	 * @mbggenerated  Thu Aug 11 11:56:40 CST 2016
	 */
	int insert(BaseDictionaryitem record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database service base_dictionaryitem
	 * @mbggenerated  Thu Aug 11 11:56:40 CST 2016
	 */
	int insertSelective(BaseDictionaryitem record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database service base_dictionaryitem
	 * @mbggenerated  Thu Aug 11 11:56:40 CST 2016
	 */
	List<BaseDictionaryitem> selectByExample(BaseDictionaryitemExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database service base_dictionaryitem
	 * @mbggenerated  Thu Aug 11 11:56:40 CST 2016
	 */
	BaseDictionaryitem selectByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database service base_dictionaryitem
	 * @mbggenerated  Thu Aug 11 11:56:40 CST 2016
	 */
	int updateByExampleSelective(@Param("record") BaseDictionaryitem record,
			@Param("example") BaseDictionaryitemExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database service base_dictionaryitem
	 * @mbggenerated  Thu Aug 11 11:56:40 CST 2016
	 */
	int updateByExample(@Param("record") BaseDictionaryitem record,
			@Param("example") BaseDictionaryitemExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database service base_dictionaryitem
	 * @mbggenerated  Thu Aug 11 11:56:40 CST 2016
	 */
	int updateByPrimaryKeySelective(BaseDictionaryitem record);

	 
	
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database service base_dictionaryitem
	 * @mbggenerated  Thu Aug 11 11:56:40 CST 2016
	 */
	int updateByPrimaryKey(BaseDictionaryitem record);
    
	List<BaseDictionaryitem> selectSelectiveByExample(BaseDictionaryitemExample example);
	
	List<BaseDictionaryitem> selectSelectiveByPrimaryKey(BaseDictionaryitemExample example);

	List<BaseDictionaryitem> selectPageData(BaseDictionaryitem item);

	int querySortId();

	List<Map<String, Object>> queryDictionaryItem(BaseDictionaryitemExample example);
}