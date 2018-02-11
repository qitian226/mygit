package com.cos.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.cos.model.SysAccount;
import com.cos.model.SysAccountExample;

public interface SysAccountMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database service sys_account
	 * @mbggenerated  Thu Aug 11 11:56:40 CST 2016
	 */
	Long countByExample(SysAccountExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database service sys_account
	 * @mbggenerated  Thu Aug 11 11:56:40 CST 2016
	 */
	int deleteByExample(SysAccountExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database service sys_account
	 * @mbggenerated  Thu Aug 11 11:56:40 CST 2016
	 */
	int deleteByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database service sys_account
	 * @mbggenerated  Thu Aug 11 11:56:40 CST 2016
	 */
	int insert(SysAccount record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database service sys_account
	 * @mbggenerated  Thu Aug 11 11:56:40 CST 2016
	 */
	int insertSelective(SysAccount record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database service sys_account
	 * @mbggenerated  Thu Aug 11 11:56:40 CST 2016
	 */
	List<SysAccount> selectByExample(SysAccountExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database service sys_account
	 * @mbggenerated  Thu Aug 11 11:56:40 CST 2016
	 */
	SysAccount selectByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database service sys_account
	 * @mbggenerated  Thu Aug 11 11:56:40 CST 2016
	 */
	int updateByExampleSelective(@Param("record") SysAccount record, @Param("example") SysAccountExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database service sys_account
	 * @mbggenerated  Thu Aug 11 11:56:40 CST 2016
	 */
	int updateByExample(@Param("record") SysAccount record, @Param("example") SysAccountExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database service sys_account
	 * @mbggenerated  Thu Aug 11 11:56:40 CST 2016
	 */
	int updateByPrimaryKeySelective(SysAccount record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database service sys_account
	 * @mbggenerated  Thu Aug 11 11:56:40 CST 2016
	 */
	int updateByPrimaryKey(SysAccount record);

    public int updateLockByIds(@Param("lock")String lock, @Param("ids") List<Long> ids);

    public List<SysAccount> selectPageByExample(SysAccountExample example);

    public List<Map<String,Object>> searchAccountByPage(@Param("word") String word,@Param("first") Integer first,@Param("limit") Integer limit);

	void addTopicCountByPrimaryKey(@Param("id")Long accountId,@Param("curr_version")int curr_version);

	void delTopicCountByPrimaryKey(@Param("id")Long accountId,@Param("curr_version")int curr_version);

	void addEntryCountByPrimaryKey(@Param("id")Long accountId,@Param("curr_version")int curr_version);

	void delEntryCountByPrimaryKey(@Param("id")Long accountId,@Param("curr_version")int curr_version);

	Long searchAccountTotalCount(String word);

	void updateEntryCount(@Param("count")Long count, @Param("id")Long accountId, @Param("curr_version")int curr_version);
}