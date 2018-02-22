package com.cos.dao;

import com.cos.model.SysEmail;
import com.cos.model.SysEmailExample;
import com.cos.model.Topic;
import com.cos.model.TopicExample;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysEmailMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_email
     *
     * @mbg.generated Tue May 30 11:00:31 CST 2017
     */
    long countByExample(SysEmailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_email
     *
     * @mbg.generated Tue May 30 11:00:31 CST 2017
     */
    int deleteByExample(SysEmailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_email
     *
     * @mbg.generated Tue May 30 11:00:31 CST 2017
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_email
     *
     * @mbg.generated Tue May 30 11:00:31 CST 2017
     */
    int insert(SysEmail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_email
     *
     * @mbg.generated Tue May 30 11:00:31 CST 2017
     */
    int insertSelective(SysEmail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_email
     *
     * @mbg.generated Tue May 30 11:00:31 CST 2017
     */
    List<SysEmail> selectByExample(SysEmailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_email
     *
     * @mbg.generated Tue May 30 11:00:31 CST 2017
     */
    SysEmail selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_email
     *
     * @mbg.generated Tue May 30 11:00:31 CST 2017
     */
    int updateByExampleSelective(@Param("record") SysEmail record, @Param("example") SysEmailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_email
     *
     * @mbg.generated Tue May 30 11:00:31 CST 2017
     */
    int updateByExample(@Param("record") SysEmail record, @Param("example") SysEmailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_email
     *
     * @mbg.generated Tue May 30 11:00:31 CST 2017
     */
    int updateByPrimaryKeySelective(SysEmail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_email
     *
     * @mbg.generated Tue May 30 11:00:31 CST 2017
     */
    int updateByPrimaryKey(SysEmail record);
    
    List<SysEmail> selectPageByExample(SysEmailExample example);

	void updateStatusByExample( @Param("example") SysEmailExample example, @Param("status") short status);
}