package com.cos.dao;

import com.cos.model.Entry;
import com.cos.model.EntryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EntryMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table entry
     *
     * @mbg.generated Tue Mar 07 11:02:04 CST 2017
     */
    long countByExample(EntryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table entry
     *
     * @mbg.generated Tue Mar 07 11:02:04 CST 2017
     */
    int deleteByExample(EntryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table entry
     *
     * @mbg.generated Tue Mar 07 11:02:04 CST 2017
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table entry
     *
     * @mbg.generated Tue Mar 07 11:02:04 CST 2017
     */
    int insert(Entry record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table entry
     *
     * @mbg.generated Tue Mar 07 11:02:04 CST 2017
     */
    int insertSelective(Entry record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table entry
     *
     * @mbg.generated Tue Mar 07 11:02:04 CST 2017
     */
    List<Entry> selectByExample(EntryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table entry
     *
     * @mbg.generated Tue Mar 07 11:02:04 CST 2017
     */
    Entry selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table entry
     *
     * @mbg.generated Tue Mar 07 11:02:04 CST 2017
     */
    int updateByExampleSelective(@Param("record") Entry record, @Param("example") EntryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table entry
     *
     * @mbg.generated Tue Mar 07 11:02:04 CST 2017
     */
    int updateByExample(@Param("record") Entry record, @Param("example") EntryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table entry
     *
     * @mbg.generated Tue Mar 07 11:02:04 CST 2017
     */
    int updateByPrimaryKeySelective(Entry record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table entry
     *
     * @mbg.generated Tue Mar 07 11:02:04 CST 2017
     */
    int updateByPrimaryKey(Entry record);
    
    Integer selectSortIdByExample(EntryExample example);

	List<Entry> selectPageByExample(EntryExample example);
    /**
     * 
     * @param topicIds
     * @param size 碎片分页数目  默认9个为一行 
     * @param orderBy
     * @return
     */
	List<Entry> selectDynamicEntryByPage(@Param("ids")List<Long> topicIds,@Param("size")int size,@Param("orderBy") String orderBy); 

}