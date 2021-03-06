package com.cos.dao;

 
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.cos.model.BaseDictionary;
import com.cos.model.BaseDictionaryExample;

public interface BaseDictionaryMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table base_dictionary
     *
     * @mbggenerated Fri Sep 23 09:51:51 CST 2016
     */
    int countByExample(BaseDictionaryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table base_dictionary
     *
     * @mbggenerated Fri Sep 23 09:51:51 CST 2016
     */
    int deleteByExample(BaseDictionaryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table base_dictionary
     *
     * @mbggenerated Fri Sep 23 09:51:51 CST 2016
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table base_dictionary
     *
     * @mbggenerated Fri Sep 23 09:51:51 CST 2016
     */
    int insert(BaseDictionary record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table base_dictionary
     *
     * @mbggenerated Fri Sep 23 09:51:51 CST 2016
     */
    int insertSelective(BaseDictionary record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table base_dictionary
     *
     * @mbggenerated Fri Sep 23 09:51:51 CST 2016
     */
    List<BaseDictionary> selectByExample(BaseDictionaryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table base_dictionary
     *
     * @mbggenerated Fri Sep 23 09:51:51 CST 2016
     */
    BaseDictionary selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table base_dictionary
     *
     * @mbggenerated Fri Sep 23 09:51:51 CST 2016
     */
    int updateByExampleSelective(@Param("record") BaseDictionary record, @Param("example") BaseDictionaryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table base_dictionary
     *
     * @mbggenerated Fri Sep 23 09:51:51 CST 2016
     */
    int updateByExample(@Param("record") BaseDictionary record, @Param("example") BaseDictionaryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table base_dictionary
     *
     * @mbggenerated Fri Sep 23 09:51:51 CST 2016
     */
    int updateByPrimaryKeySelective(BaseDictionary record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table base_dictionary
     *
     * @mbggenerated Fri Sep 23 09:51:51 CST 2016
     */
    int updateByPrimaryKey(BaseDictionary record);

    
	List<Map<String, Object>> selectPageByExample(BaseDictionaryExample example);
}