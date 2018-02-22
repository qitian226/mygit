package com.cos.model;


import java.util.Date;


public class BaseDictionaryitem {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column base_dictionaryitem.id_
	 * @mbggenerated  Fri Aug 19 15:57:33 CST 2016
	 */
	private Long id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column base_dictionaryitem.dict_code_
	 * @mbggenerated  Fri Aug 19 15:57:33 CST 2016
	 */
	private String dictCode;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column base_dictionaryitem.dictitem_name_
	 * @mbggenerated  Fri Aug 19 15:57:33 CST 2016
	 */
	private String dictitemName;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column base_dictionaryitem.dictitem_value_
	 * @mbggenerated  Fri Aug 19 15:57:33 CST 2016
	 */
	private String dictitemValue;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column base_dictionaryitem.dictitem_desc_
	 * @mbggenerated  Fri Aug 19 15:57:33 CST 2016
	 */
	private String dictitemDesc;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column base_dictionaryitem.sort_id_
	 * @mbggenerated  Fri Aug 19 15:57:33 CST 2016
	 */
	private Integer sortId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column base_dictionaryitem.created_by_
	 * @mbggenerated  Fri Aug 19 15:57:33 CST 2016
	 */
	private Long createdBy;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column base_dictionaryitem.created_time_
	 * @mbggenerated  Fri Aug 19 15:57:33 CST 2016
	 */
	private Date createdTime;
	
	
	private Short status;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column base_dictionaryitem.curr_version_
	 * @mbggenerated  Fri Aug 19 15:57:33 CST 2016
	 */
	private Short currVersion;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column base_dictionaryitem.id_
	 * @return  the value of base_dictionaryitem.id_
	 * @mbggenerated  Fri Aug 19 15:57:33 CST 2016
	 */
	public Long getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column base_dictionaryitem.id_
	 * @param id  the value for base_dictionaryitem.id_
	 * @mbggenerated  Fri Aug 19 15:57:33 CST 2016
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column base_dictionaryitem.dict_code_
	 * @return  the value of base_dictionaryitem.dict_code_
	 * @mbggenerated  Fri Aug 19 15:57:33 CST 2016
	 */
	public String getDictCode() {
		return dictCode;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column base_dictionaryitem.dict_code_
	 * @param dictCode  the value for base_dictionaryitem.dict_code_
	 * @mbggenerated  Fri Aug 19 15:57:33 CST 2016
	 */
	public void setDictCode(String dictCode) {
		this.dictCode = dictCode == null ? null : dictCode.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column base_dictionaryitem.dictitem_name_
	 * @return  the value of base_dictionaryitem.dictitem_name_
	 * @mbggenerated  Fri Aug 19 15:57:33 CST 2016
	 */
	public String getDictitemName() {
		return dictitemName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column base_dictionaryitem.dictitem_name_
	 * @param dictitemName  the value for base_dictionaryitem.dictitem_name_
	 * @mbggenerated  Fri Aug 19 15:57:33 CST 2016
	 */
	public void setDictitemName(String dictitemName) {
		this.dictitemName = dictitemName == null ? null : dictitemName.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column base_dictionaryitem.dictitem_value_
	 * @return  the value of base_dictionaryitem.dictitem_value_
	 * @mbggenerated  Fri Aug 19 15:57:33 CST 2016
	 */
	public String getDictitemValue() {
		return dictitemValue;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column base_dictionaryitem.dictitem_value_
	 * @param dictitemValue  the value for base_dictionaryitem.dictitem_value_
	 * @mbggenerated  Fri Aug 19 15:57:33 CST 2016
	 */
	public void setDictitemValue(String dictitemValue) {
		this.dictitemValue = dictitemValue == null ? null : dictitemValue.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column base_dictionaryitem.dictitem_desc_
	 * @return  the value of base_dictionaryitem.dictitem_desc_
	 * @mbggenerated  Fri Aug 19 15:57:33 CST 2016
	 */
	public String getDictitemDesc() {
		return dictitemDesc;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column base_dictionaryitem.dictitem_desc_
	 * @param dictitemDesc  the value for base_dictionaryitem.dictitem_desc_
	 * @mbggenerated  Fri Aug 19 15:57:33 CST 2016
	 */
	public void setDictitemDesc(String dictitemDesc) {
		this.dictitemDesc = dictitemDesc == null ? null : dictitemDesc.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column base_dictionaryitem.sort_id_
	 * @return  the value of base_dictionaryitem.sort_id_
	 * @mbggenerated  Fri Aug 19 15:57:33 CST 2016
	 */
	public Integer getSortId() {
		return sortId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column base_dictionaryitem.sort_id_
	 * @param sortId  the value for base_dictionaryitem.sort_id_
	 * @mbggenerated  Fri Aug 19 15:57:33 CST 2016
	 */
	public void setSortId(Integer sortId) {
		this.sortId = sortId;
	}

	
	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column base_dictionaryitem.created_by_
	 * @return  the value of base_dictionaryitem.created_by_
	 * @mbggenerated  Fri Aug 19 15:57:33 CST 2016
	 */
	public Long getCreatedBy() {
		return createdBy;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column base_dictionaryitem.created_by_
	 * @param createdBy  the value for base_dictionaryitem.created_by_
	 * @mbggenerated  Fri Aug 19 15:57:33 CST 2016
	 */
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column base_dictionaryitem.created_time_
	 * @return  the value of base_dictionaryitem.created_time_
	 * @mbggenerated  Fri Aug 19 15:57:33 CST 2016
	 */
	public Date getCreatedTime() {
		return createdTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column base_dictionaryitem.created_time_
	 * @param createdTime  the value for base_dictionaryitem.created_time_
	 * @mbggenerated  Fri Aug 19 15:57:33 CST 2016
	 */
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column base_dictionaryitem.status_
	 * @return  the value of base_dictionaryitem.status_
	 * @mbggenerated  Fri Aug 19 15:57:33 CST 2016
	 */
	public Short getStatus() {
		return status;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column base_dictionaryitem.status_
	 * @param status  the value for base_dictionaryitem.status_
	 * @mbggenerated  Fri Aug 19 15:57:33 CST 2016
	 */
	public void setStatus(Short status) {
		this.status = status;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column base_dictionaryitem.curr_version_
	 * @return  the value of base_dictionaryitem.curr_version_
	 * @mbggenerated  Fri Aug 19 15:57:33 CST 2016
	 */
	public Short getCurrVersion() {
		return currVersion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column base_dictionaryitem.curr_version_
	 * @param currVersion  the value for base_dictionaryitem.curr_version_
	 * @mbggenerated  Fri Aug 19 15:57:33 CST 2016
	 */
	public void setCurrVersion(Short currVersion) {
		this.currVersion = currVersion;
	}
}