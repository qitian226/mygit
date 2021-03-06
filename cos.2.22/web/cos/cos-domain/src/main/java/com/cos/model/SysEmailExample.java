package com.cos.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cos.model.base.BasicExample;

public class SysEmailExample extends BasicExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table sys_email
	 * @mbg.generated  Thu Jun 15 14:32:32 CST 2017
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table sys_email
	 * @mbg.generated  Thu Jun 15 14:32:32 CST 2017
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table sys_email
	 * @mbg.generated  Thu Jun 15 14:32:32 CST 2017
	 */protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_email
	 * @mbg.generated  Thu Jun 15 14:32:32 CST 2017
	 */public SysEmailExample(){oredCriteria=new ArrayList<Criteria>();}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_email
	 * @mbg.generated  Thu Jun 15 14:32:32 CST 2017
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_email
	 * @mbg.generated  Thu Jun 15 14:32:32 CST 2017
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_email
	 * @mbg.generated  Thu Jun 15 14:32:32 CST 2017
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_email
	 * @mbg.generated  Thu Jun 15 14:32:32 CST 2017
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_email
	 * @mbg.generated  Thu Jun 15 14:32:32 CST 2017
	 */public List<Criteria> getOredCriteria(){return oredCriteria;}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_email
	 * @mbg.generated  Thu Jun 15 14:32:32 CST 2017
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_email
	 * @mbg.generated  Thu Jun 15 14:32:32 CST 2017
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_email
	 * @mbg.generated  Thu Jun 15 14:32:32 CST 2017
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_email
	 * @mbg.generated  Thu Jun 15 14:32:32 CST 2017
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_email
	 * @mbg.generated  Thu Jun 15 14:32:32 CST 2017
	 */
	public void clear() {
		super.clear();
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table sys_email
	 * @mbg.generated  Thu Jun 15 14:32:32 CST 2017
	 */protected abstract static class GeneratedCriteria {protected List<Criterion> criteria;protected GeneratedCriteria(){super();criteria=new ArrayList<Criterion>();}public boolean isValid(){return criteria.size() > 0;}public List<Criterion> getAllCriteria(){return criteria;}public List<Criterion> getCriteria(){return criteria;}protected void addCriterion(String condition){if (condition == null){throw new RuntimeException("Value for condition cannot be null");}criteria.add(new Criterion(condition));}protected void addCriterion(String condition,Object value,String property){if (value == null){throw new RuntimeException("Value for " + property + " cannot be null");}criteria.add(new Criterion(condition,value));}protected void addCriterion(String condition,Object value1,Object value2,String property){if (value1 == null || value2 == null){throw new RuntimeException("Between values for " + property + " cannot be null");}criteria.add(new Criterion(condition,value1,value2));}public Criteria andIdIsNull(){addCriterion("id_ is null");return (Criteria)this;}public Criteria andIdIsNotNull(){addCriterion("id_ is not null");return (Criteria)this;}public Criteria andIdEqualTo(Long value){addCriterion("id_ =",value,"id");return (Criteria)this;}public Criteria andIdNotEqualTo(Long value){addCriterion("id_ <>",value,"id");return (Criteria)this;}public Criteria andIdGreaterThan(Long value){addCriterion("id_ >",value,"id");return (Criteria)this;}public Criteria andIdGreaterThanOrEqualTo(Long value){addCriterion("id_ >=",value,"id");return (Criteria)this;}public Criteria andIdLessThan(Long value){addCriterion("id_ <",value,"id");return (Criteria)this;}public Criteria andIdLessThanOrEqualTo(Long value){addCriterion("id_ <=",value,"id");return (Criteria)this;}public Criteria andIdIn(List<Long> values){addCriterion("id_ in",values,"id");return (Criteria)this;}public Criteria andIdNotIn(List<Long> values){addCriterion("id_ not in",values,"id");return (Criteria)this;}public Criteria andIdBetween(Long value1,Long value2){addCriterion("id_ between",value1,value2,"id");return (Criteria)this;}public Criteria andIdNotBetween(Long value1,Long value2){addCriterion("id_ not between",value1,value2,"id");return (Criteria)this;}public Criteria andFromAccountIsNull(){addCriterion("from_account_ is null");return (Criteria)this;}public Criteria andFromAccountIsNotNull(){addCriterion("from_account_ is not null");return (Criteria)this;}public Criteria andFromAccountEqualTo(Long value){addCriterion("from_account_ =",value,"fromAccount");return (Criteria)this;}public Criteria andFromAccountNotEqualTo(Long value){addCriterion("from_account_ <>",value,"fromAccount");return (Criteria)this;}public Criteria andFromAccountGreaterThan(Long value){addCriterion("from_account_ >",value,"fromAccount");return (Criteria)this;}public Criteria andFromAccountGreaterThanOrEqualTo(Long value){addCriterion("from_account_ >=",value,"fromAccount");return (Criteria)this;}public Criteria andFromAccountLessThan(Long value){addCriterion("from_account_ <",value,"fromAccount");return (Criteria)this;}public Criteria andFromAccountLessThanOrEqualTo(Long value){addCriterion("from_account_ <=",value,"fromAccount");return (Criteria)this;}public Criteria andFromAccountIn(List<Long> values){addCriterion("from_account_ in",values,"fromAccount");return (Criteria)this;}public Criteria andFromAccountNotIn(List<Long> values){addCriterion("from_account_ not in",values,"fromAccount");return (Criteria)this;}public Criteria andFromAccountBetween(Long value1,Long value2){addCriterion("from_account_ between",value1,value2,"fromAccount");return (Criteria)this;}public Criteria andFromAccountNotBetween(Long value1,Long value2){addCriterion("from_account_ not between",value1,value2,"fromAccount");return (Criteria)this;}public Criteria andToAccountIsNull(){addCriterion("to_account_ is null");return (Criteria)this;}public Criteria andToAccountIsNotNull(){addCriterion("to_account_ is not null");return (Criteria)this;}public Criteria andToAccountEqualTo(Long value){addCriterion("to_account_ =",value,"toAccount");return (Criteria)this;}public Criteria andToAccountNotEqualTo(Long value){addCriterion("to_account_ <>",value,"toAccount");return (Criteria)this;}public Criteria andToAccountGreaterThan(Long value){addCriterion("to_account_ >",value,"toAccount");return (Criteria)this;}public Criteria andToAccountGreaterThanOrEqualTo(Long value){addCriterion("to_account_ >=",value,"toAccount");return (Criteria)this;}public Criteria andToAccountLessThan(Long value){addCriterion("to_account_ <",value,"toAccount");return (Criteria)this;}public Criteria andToAccountLessThanOrEqualTo(Long value){addCriterion("to_account_ <=",value,"toAccount");return (Criteria)this;}public Criteria andToAccountIn(List<Long> values){addCriterion("to_account_ in",values,"toAccount");return (Criteria)this;}public Criteria andToAccountNotIn(List<Long> values){addCriterion("to_account_ not in",values,"toAccount");return (Criteria)this;}public Criteria andToAccountBetween(Long value1,Long value2){addCriterion("to_account_ between",value1,value2,"toAccount");return (Criteria)this;}public Criteria andToAccountNotBetween(Long value1,Long value2){addCriterion("to_account_ not between",value1,value2,"toAccount");return (Criteria)this;}public Criteria andMessageIsNull(){addCriterion("message_ is null");return (Criteria)this;}public Criteria andMessageIsNotNull(){addCriterion("message_ is not null");return (Criteria)this;}public Criteria andMessageEqualTo(String value){addCriterion("message_ =",value,"message");return (Criteria)this;}public Criteria andMessageNotEqualTo(String value){addCriterion("message_ <>",value,"message");return (Criteria)this;}public Criteria andMessageGreaterThan(String value){addCriterion("message_ >",value,"message");return (Criteria)this;}public Criteria andMessageGreaterThanOrEqualTo(String value){addCriterion("message_ >=",value,"message");return (Criteria)this;}public Criteria andMessageLessThan(String value){addCriterion("message_ <",value,"message");return (Criteria)this;}public Criteria andMessageLessThanOrEqualTo(String value){addCriterion("message_ <=",value,"message");return (Criteria)this;}public Criteria andMessageLike(String value){addCriterion("message_ like",value,"message");return (Criteria)this;}public Criteria andMessageNotLike(String value){addCriterion("message_ not like",value,"message");return (Criteria)this;}public Criteria andMessageIn(List<String> values){addCriterion("message_ in",values,"message");return (Criteria)this;}public Criteria andMessageNotIn(List<String> values){addCriterion("message_ not in",values,"message");return (Criteria)this;}public Criteria andMessageBetween(String value1,String value2){addCriterion("message_ between",value1,value2,"message");return (Criteria)this;}public Criteria andMessageNotBetween(String value1,String value2){addCriterion("message_ not between",value1,value2,"message");return (Criteria)this;}public Criteria andStatusIsNull(){addCriterion("status_ is null");return (Criteria)this;}public Criteria andStatusIsNotNull(){addCriterion("status_ is not null");return (Criteria)this;}public Criteria andStatusEqualTo(Short value){addCriterion("status_ =",value,"status");return (Criteria)this;}public Criteria andStatusNotEqualTo(Short value){addCriterion("status_ <>",value,"status");return (Criteria)this;}public Criteria andStatusGreaterThan(Short value){addCriterion("status_ >",value,"status");return (Criteria)this;}public Criteria andStatusGreaterThanOrEqualTo(Short value){addCriterion("status_ >=",value,"status");return (Criteria)this;}public Criteria andStatusLessThan(Short value){addCriterion("status_ <",value,"status");return (Criteria)this;}public Criteria andStatusLessThanOrEqualTo(Short value){addCriterion("status_ <=",value,"status");return (Criteria)this;}public Criteria andStatusIn(List<Short> values){addCriterion("status_ in",values,"status");return (Criteria)this;}public Criteria andStatusNotIn(List<Short> values){addCriterion("status_ not in",values,"status");return (Criteria)this;}public Criteria andStatusBetween(Short value1,Short value2){addCriterion("status_ between",value1,value2,"status");return (Criteria)this;}public Criteria andStatusNotBetween(Short value1,Short value2){addCriterion("status_ not between",value1,value2,"status");return (Criteria)this;}public Criteria andTockenIsNull(){addCriterion("tocken_ is null");return (Criteria)this;}public Criteria andTockenIsNotNull(){addCriterion("tocken_ is not null");return (Criteria)this;}public Criteria andTockenEqualTo(String value){addCriterion("tocken_ =",value,"tocken");return (Criteria)this;}public Criteria andTockenNotEqualTo(String value){addCriterion("tocken_ <>",value,"tocken");return (Criteria)this;}public Criteria andTockenGreaterThan(String value){addCriterion("tocken_ >",value,"tocken");return (Criteria)this;}public Criteria andTockenGreaterThanOrEqualTo(String value){addCriterion("tocken_ >=",value,"tocken");return (Criteria)this;}public Criteria andTockenLessThan(String value){addCriterion("tocken_ <",value,"tocken");return (Criteria)this;}public Criteria andTockenLessThanOrEqualTo(String value){addCriterion("tocken_ <=",value,"tocken");return (Criteria)this;}public Criteria andTockenLike(String value){addCriterion("tocken_ like",value,"tocken");return (Criteria)this;}public Criteria andTockenNotLike(String value){addCriterion("tocken_ not like",value,"tocken");return (Criteria)this;}public Criteria andTockenIn(List<String> values){addCriterion("tocken_ in",values,"tocken");return (Criteria)this;}public Criteria andTockenNotIn(List<String> values){addCriterion("tocken_ not in",values,"tocken");return (Criteria)this;}public Criteria andTockenBetween(String value1,String value2){addCriterion("tocken_ between",value1,value2,"tocken");return (Criteria)this;}public Criteria andTockenNotBetween(String value1,String value2){addCriterion("tocken_ not between",value1,value2,"tocken");return (Criteria)this;}public Criteria andCreateTimeIsNull(){addCriterion("create_time_ is null");return (Criteria)this;}public Criteria andCreateTimeIsNotNull(){addCriterion("create_time_ is not null");return (Criteria)this;}public Criteria andCreateTimeEqualTo(Date value){addCriterion("create_time_ =",value,"createTime");return (Criteria)this;}public Criteria andCreateTimeNotEqualTo(Date value){addCriterion("create_time_ <>",value,"createTime");return (Criteria)this;}public Criteria andCreateTimeGreaterThan(Date value){addCriterion("create_time_ >",value,"createTime");return (Criteria)this;}public Criteria andCreateTimeGreaterThanOrEqualTo(Date value){addCriterion("create_time_ >=",value,"createTime");return (Criteria)this;}public Criteria andCreateTimeLessThan(Date value){addCriterion("create_time_ <",value,"createTime");return (Criteria)this;}public Criteria andCreateTimeLessThanOrEqualTo(Date value){addCriterion("create_time_ <=",value,"createTime");return (Criteria)this;}public Criteria andCreateTimeIn(List<Date> values){addCriterion("create_time_ in",values,"createTime");return (Criteria)this;}public Criteria andCreateTimeNotIn(List<Date> values){addCriterion("create_time_ not in",values,"createTime");return (Criteria)this;}public Criteria andCreateTimeBetween(Date value1,Date value2){addCriterion("create_time_ between",value1,value2,"createTime");return (Criteria)this;}public Criteria andCreateTimeNotBetween(Date value1,Date value2){addCriterion("create_time_ not between",value1,value2,"createTime");return (Criteria)this;}}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table sys_email
	 * @mbg.generated  Thu Jun 15 14:32:32 CST 2017
	 */public static class Criterion {private String condition;private Object value;private Object secondValue;private boolean noValue;private boolean singleValue;private boolean betweenValue;private boolean listValue;private String typeHandler;public String getCondition(){return condition;}public Object getValue(){return value;}public Object getSecondValue(){return secondValue;}public boolean isNoValue(){return noValue;}public boolean isSingleValue(){return singleValue;}public boolean isBetweenValue(){return betweenValue;}public boolean isListValue(){return listValue;}public String getTypeHandler(){return typeHandler;}protected Criterion(String condition){super();this.condition=condition;this.typeHandler=null;this.noValue=true;}protected Criterion(String condition,Object value,String typeHandler){super();this.condition=condition;this.value=value;this.typeHandler=typeHandler;if (value instanceof List<?>){this.listValue=true;} else {this.singleValue=true;}}protected Criterion(String condition,Object value){this(condition,value,null);}protected Criterion(String condition,Object value,Object secondValue,String typeHandler){super();this.condition=condition;this.value=value;this.secondValue=secondValue;this.typeHandler=typeHandler;this.betweenValue=true;}protected Criterion(String condition,Object value,Object secondValue){this(condition,value,secondValue,null);}}

	/**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table sys_email
     *
     * @mbg.generated do_not_delete_during_merge Tue May 30 11:00:31 CST 2017
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}