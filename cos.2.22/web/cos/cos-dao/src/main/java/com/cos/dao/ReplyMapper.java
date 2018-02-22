package com.cos.dao;

import com.cos.model.Reply;
import com.cos.model.ReplyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ReplyMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table reply
     *
     * @mbg.generated Mon Mar 13 11:07:38 CST 2017
     */
    long countByExample(ReplyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table reply
     *
     * @mbg.generated Mon Mar 13 11:07:38 CST 2017
     */
    int deleteByExample(ReplyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table reply
     *
     * @mbg.generated Mon Mar 13 11:07:38 CST 2017
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table reply
     *
     * @mbg.generated Mon Mar 13 11:07:38 CST 2017
     */
    int insert(Reply record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table reply
     *
     * @mbg.generated Mon Mar 13 11:07:38 CST 2017
     */
    int insertSelective(Reply record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table reply
     *
     * @mbg.generated Mon Mar 13 11:07:38 CST 2017
     */
    List<Reply> selectByExample(ReplyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table reply
     *
     * @mbg.generated Mon Mar 13 11:07:38 CST 2017
     */
    Reply selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table reply
     *
     * @mbg.generated Mon Mar 13 11:07:38 CST 2017
     */
    int updateByExampleSelective(@Param("record") Reply record, @Param("example") ReplyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table reply
     *
     * @mbg.generated Mon Mar 13 11:07:38 CST 2017
     */
    int updateByExample(@Param("record") Reply record, @Param("example") ReplyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table reply
     *
     * @mbg.generated Mon Mar 13 11:07:38 CST 2017
     */
    int updateByPrimaryKeySelective(Reply record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table reply
     *
     * @mbg.generated Mon Mar 13 11:07:38 CST 2017
     */
    int updateByPrimaryKey(Reply record);
}