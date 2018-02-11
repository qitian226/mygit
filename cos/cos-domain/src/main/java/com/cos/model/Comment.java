package com.cos.model;

import java.util.Date;
import java.util.List;

import com.cos.common.utils.DateTimeUtil;

public class Comment {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column comment.id_
     *
     * @mbg.generated Mon Aug 21 17:21:52 CST 2017
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column comment.content_
     *
     * @mbg.generated Mon Aug 21 17:21:52 CST 2017
     */
    private String content;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column comment.target_id_
     *
     * @mbg.generated Mon Aug 21 17:21:52 CST 2017
     */
    private Long targetId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column comment.reply_ids_
     *
     * @mbg.generated Mon Aug 21 17:21:52 CST 2017
     */
    private String replyIds;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column comment.grade_
     *
     * @mbg.generated Mon Aug 21 17:21:52 CST 2017
     */
    private Integer grade;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column comment.endorse_
     *
     * @mbg.generated Mon Aug 21 17:21:52 CST 2017
     */
    private Integer endorse;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column comment.status_
     *
     * @mbg.generated Mon Aug 21 17:21:52 CST 2017
     */
    private Short status;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column comment.tocken_
     *
     * @mbg.generated Mon Aug 21 17:21:52 CST 2017
     */
    private String tocken;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column comment.curr_version_
     *
     * @mbg.generated Mon Aug 21 17:21:52 CST 2017
     */
    private Short currVersion;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column comment.create_by_
     *
     * @mbg.generated Mon Aug 21 17:21:52 CST 2017
     */
    private Long createBy;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column comment.create_time_
     *
     * @mbg.generated Mon Aug 21 17:21:52 CST 2017
     */
    private Date createTime;
    
    private SysAccount author;
    
    private String authorImgUrl;
	
	private List<Reply> replys;
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column comment.id_
     *
     * @return the value of comment.id_
     *
     * @mbg.generated Mon Aug 21 17:21:52 CST 2017
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column comment.id_
     *
     * @param id the value for comment.id_
     *
     * @mbg.generated Mon Aug 21 17:21:52 CST 2017
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column comment.content_
     *
     * @return the value of comment.content_
     *
     * @mbg.generated Mon Aug 21 17:21:52 CST 2017
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column comment.content_
     *
     * @param content the value for comment.content_
     *
     * @mbg.generated Mon Aug 21 17:21:52 CST 2017
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column comment.target_id_
     *
     * @return the value of comment.target_id_
     *
     * @mbg.generated Mon Aug 21 17:21:52 CST 2017
     */
    public Long getTargetId() {
        return targetId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column comment.target_id_
     *
     * @param targetId the value for comment.target_id_
     *
     * @mbg.generated Mon Aug 21 17:21:52 CST 2017
     */
    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column comment.reply_ids_
     *
     * @return the value of comment.reply_ids_
     *
     * @mbg.generated Mon Aug 21 17:21:52 CST 2017
     */
    public String getReplyIds() {
        return replyIds;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column comment.reply_ids_
     *
     * @param replyIds the value for comment.reply_ids_
     *
     * @mbg.generated Mon Aug 21 17:21:52 CST 2017
     */
    public void setReplyIds(String replyIds) {
        this.replyIds = replyIds == null ? null : replyIds.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column comment.grade_
     *
     * @return the value of comment.grade_
     *
     * @mbg.generated Mon Aug 21 17:21:52 CST 2017
     */
    public Integer getGrade() {
        return grade;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column comment.grade_
     *
     * @param grade the value for comment.grade_
     *
     * @mbg.generated Mon Aug 21 17:21:52 CST 2017
     */
    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column comment.endorse_
     *
     * @return the value of comment.endorse_
     *
     * @mbg.generated Mon Aug 21 17:21:52 CST 2017
     */
    public Integer getEndorse() {
        return endorse;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column comment.endorse_
     *
     * @param endorse the value for comment.endorse_
     *
     * @mbg.generated Mon Aug 21 17:21:52 CST 2017
     */
    public void setEndorse(Integer endorse) {
        this.endorse = endorse;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column comment.status_
     *
     * @return the value of comment.status_
     *
     * @mbg.generated Mon Aug 21 17:21:52 CST 2017
     */
    public Short getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column comment.status_
     *
     * @param status the value for comment.status_
     *
     * @mbg.generated Mon Aug 21 17:21:52 CST 2017
     */
    public void setStatus(Short status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column comment.tocken_
     *
     * @return the value of comment.tocken_
     *
     * @mbg.generated Mon Aug 21 17:21:52 CST 2017
     */
    public String getTocken() {
        return tocken;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column comment.tocken_
     *
     * @param tocken the value for comment.tocken_
     *
     * @mbg.generated Mon Aug 21 17:21:52 CST 2017
     */
    public void setTocken(String tocken) {
        this.tocken = tocken == null ? null : tocken.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column comment.curr_version_
     *
     * @return the value of comment.curr_version_
     *
     * @mbg.generated Mon Aug 21 17:21:52 CST 2017
     */
    public Short getCurrVersion() {
        return currVersion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column comment.curr_version_
     *
     * @param currVersion the value for comment.curr_version_
     *
     * @mbg.generated Mon Aug 21 17:21:52 CST 2017
     */
    public void setCurrVersion(Short currVersion) {
        this.currVersion = currVersion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column comment.create_by_
     *
     * @return the value of comment.create_by_
     *
     * @mbg.generated Mon Aug 21 17:21:52 CST 2017
     */
    public Long getCreateBy() {
        return createBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column comment.create_by_
     *
     * @param createBy the value for comment.create_by_
     *
     * @mbg.generated Mon Aug 21 17:21:52 CST 2017
     */
    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column comment.create_time_
     *
     * @return the value of comment.create_time_
     *
     * @mbg.generated Mon Aug 21 17:21:52 CST 2017
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column comment.create_time_
     *
     * @param createTime the value for comment.create_time_
     *
     * @mbg.generated Mon Aug 21 17:21:52 CST 2017
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public SysAccount getAuthor() {
		return author;
	}

	public void setAuthor(SysAccount author) {
		this.author = author;
	}

	public List<Reply> getReplys() {
		return replys;
	}

	public void setReplys(List<Reply> replys) {
		this.replys = replys;
	}
	
	public String getDayNumFromCurrentDay(){
		return DateTimeUtil.getDayNumFromCurrentDay(this.createTime);
	}

	public String getAuthorImgUrl() {
		return authorImgUrl;
	}

	public void setAuthorImgUrl(String authorImgUrl) {
		this.authorImgUrl = authorImgUrl;
	}
}