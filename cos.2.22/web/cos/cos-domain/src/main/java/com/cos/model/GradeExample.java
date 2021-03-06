package com.cos.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GradeExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table grade
	 * @mbg.generated  Fri Mar 31 19:32:28 CST 2017
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table grade
	 * @mbg.generated  Fri Mar 31 19:32:28 CST 2017
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table grade
	 * @mbg.generated  Fri Mar 31 19:32:28 CST 2017
	 */protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table grade
	 * @mbg.generated  Fri Mar 31 19:32:28 CST 2017
	 */public GradeExample(){oredCriteria=new ArrayList<Criteria>();}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table grade
	 * @mbg.generated  Fri Mar 31 19:32:28 CST 2017
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table grade
	 * @mbg.generated  Fri Mar 31 19:32:28 CST 2017
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table grade
	 * @mbg.generated  Fri Mar 31 19:32:28 CST 2017
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table grade
	 * @mbg.generated  Fri Mar 31 19:32:28 CST 2017
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table grade
	 * @mbg.generated  Fri Mar 31 19:32:28 CST 2017
	 */public List<Criteria> getOredCriteria(){return oredCriteria;}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table grade
	 * @mbg.generated  Fri Mar 31 19:32:28 CST 2017
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table grade
	 * @mbg.generated  Fri Mar 31 19:32:28 CST 2017
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table grade
	 * @mbg.generated  Fri Mar 31 19:32:28 CST 2017
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table grade
	 * @mbg.generated  Fri Mar 31 19:32:28 CST 2017
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table grade
	 * @mbg.generated  Fri Mar 31 19:32:28 CST 2017
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table grade
	 * @mbg.generated  Fri Mar 31 19:32:28 CST 2017
	 */protected abstract static class GeneratedCriteria {protected List<Criterion> criteria;protected GeneratedCriteria(){super();criteria=new ArrayList<Criterion>();}public boolean isValid(){return criteria.size() > 0;}public List<Criterion> getAllCriteria(){return criteria;}public List<Criterion> getCriteria(){return criteria;}protected void addCriterion(String condition){if (condition == null){throw new RuntimeException("Value for condition cannot be null");}criteria.add(new Criterion(condition));}protected void addCriterion(String condition,Object value,String property){if (value == null){throw new RuntimeException("Value for " + property + " cannot be null");}criteria.add(new Criterion(condition,value));}protected void addCriterion(String condition,Object value1,Object value2,String property){if (value1 == null || value2 == null){throw new RuntimeException("Between values for " + property + " cannot be null");}criteria.add(new Criterion(condition,value1,value2));}public Criteria andIdIsNull(){addCriterion("id_ is null");return (Criteria)this;}public Criteria andIdIsNotNull(){addCriterion("id_ is not null");return (Criteria)this;}public Criteria andIdEqualTo(Long value){addCriterion("id_ =",value,"id");return (Criteria)this;}public Criteria andIdNotEqualTo(Long value){addCriterion("id_ <>",value,"id");return (Criteria)this;}public Criteria andIdGreaterThan(Long value){addCriterion("id_ >",value,"id");return (Criteria)this;}public Criteria andIdGreaterThanOrEqualTo(Long value){addCriterion("id_ >=",value,"id");return (Criteria)this;}public Criteria andIdLessThan(Long value){addCriterion("id_ <",value,"id");return (Criteria)this;}public Criteria andIdLessThanOrEqualTo(Long value){addCriterion("id_ <=",value,"id");return (Criteria)this;}public Criteria andIdIn(List<Long> values){addCriterion("id_ in",values,"id");return (Criteria)this;}public Criteria andIdNotIn(List<Long> values){addCriterion("id_ not in",values,"id");return (Criteria)this;}public Criteria andIdBetween(Long value1,Long value2){addCriterion("id_ between",value1,value2,"id");return (Criteria)this;}public Criteria andIdNotBetween(Long value1,Long value2){addCriterion("id_ not between",value1,value2,"id");return (Criteria)this;}public Criteria andGradeIsNull(){addCriterion("grade_ is null");return (Criteria)this;}public Criteria andGradeIsNotNull(){addCriterion("grade_ is not null");return (Criteria)this;}public Criteria andGradeEqualTo(Integer value){addCriterion("grade_ =",value,"grade");return (Criteria)this;}public Criteria andGradeNotEqualTo(Integer value){addCriterion("grade_ <>",value,"grade");return (Criteria)this;}public Criteria andGradeGreaterThan(Integer value){addCriterion("grade_ >",value,"grade");return (Criteria)this;}public Criteria andGradeGreaterThanOrEqualTo(Integer value){addCriterion("grade_ >=",value,"grade");return (Criteria)this;}public Criteria andGradeLessThan(Integer value){addCriterion("grade_ <",value,"grade");return (Criteria)this;}public Criteria andGradeLessThanOrEqualTo(Integer value){addCriterion("grade_ <=",value,"grade");return (Criteria)this;}public Criteria andGradeIn(List<Integer> values){addCriterion("grade_ in",values,"grade");return (Criteria)this;}public Criteria andGradeNotIn(List<Integer> values){addCriterion("grade_ not in",values,"grade");return (Criteria)this;}public Criteria andGradeBetween(Integer value1,Integer value2){addCriterion("grade_ between",value1,value2,"grade");return (Criteria)this;}public Criteria andGradeNotBetween(Integer value1,Integer value2){addCriterion("grade_ not between",value1,value2,"grade");return (Criteria)this;}public Criteria andTypeIsNull(){addCriterion("type_ is null");return (Criteria)this;}public Criteria andTypeIsNotNull(){addCriterion("type_ is not null");return (Criteria)this;}public Criteria andTypeEqualTo(Short value){addCriterion("type_ =",value,"type");return (Criteria)this;}public Criteria andTypeNotEqualTo(Short value){addCriterion("type_ <>",value,"type");return (Criteria)this;}public Criteria andTypeGreaterThan(Short value){addCriterion("type_ >",value,"type");return (Criteria)this;}public Criteria andTypeGreaterThanOrEqualTo(Short value){addCriterion("type_ >=",value,"type");return (Criteria)this;}public Criteria andTypeLessThan(Short value){addCriterion("type_ <",value,"type");return (Criteria)this;}public Criteria andTypeLessThanOrEqualTo(Short value){addCriterion("type_ <=",value,"type");return (Criteria)this;}public Criteria andTypeIn(List<Short> values){addCriterion("type_ in",values,"type");return (Criteria)this;}public Criteria andTypeNotIn(List<Short> values){addCriterion("type_ not in",values,"type");return (Criteria)this;}public Criteria andTypeBetween(Short value1,Short value2){addCriterion("type_ between",value1,value2,"type");return (Criteria)this;}public Criteria andTypeNotBetween(Short value1,Short value2){addCriterion("type_ not between",value1,value2,"type");return (Criteria)this;}public Criteria andTagetIdIsNull(){addCriterion("taget_id_ is null");return (Criteria)this;}public Criteria andTagetIdIsNotNull(){addCriterion("taget_id_ is not null");return (Criteria)this;}public Criteria andTagetIdEqualTo(Long value){addCriterion("taget_id_ =",value,"tagetId");return (Criteria)this;}public Criteria andTagetIdNotEqualTo(Long value){addCriterion("taget_id_ <>",value,"tagetId");return (Criteria)this;}public Criteria andTagetIdGreaterThan(Long value){addCriterion("taget_id_ >",value,"tagetId");return (Criteria)this;}public Criteria andTagetIdGreaterThanOrEqualTo(Long value){addCriterion("taget_id_ >=",value,"tagetId");return (Criteria)this;}public Criteria andTagetIdLessThan(Long value){addCriterion("taget_id_ <",value,"tagetId");return (Criteria)this;}public Criteria andTagetIdLessThanOrEqualTo(Long value){addCriterion("taget_id_ <=",value,"tagetId");return (Criteria)this;}public Criteria andTagetIdIn(List<Long> values){addCriterion("taget_id_ in",values,"tagetId");return (Criteria)this;}public Criteria andTagetIdNotIn(List<Long> values){addCriterion("taget_id_ not in",values,"tagetId");return (Criteria)this;}public Criteria andTagetIdBetween(Long value1,Long value2){addCriterion("taget_id_ between",value1,value2,"tagetId");return (Criteria)this;}public Criteria andTagetIdNotBetween(Long value1,Long value2){addCriterion("taget_id_ not between",value1,value2,"tagetId");return (Criteria)this;}public Criteria andCreateByIsNull(){addCriterion("create_by_ is null");return (Criteria)this;}public Criteria andCreateByIsNotNull(){addCriterion("create_by_ is not null");return (Criteria)this;}public Criteria andCreateByEqualTo(Long value){addCriterion("create_by_ =",value,"createBy");return (Criteria)this;}public Criteria andCreateByNotEqualTo(Long value){addCriterion("create_by_ <>",value,"createBy");return (Criteria)this;}public Criteria andCreateByGreaterThan(Long value){addCriterion("create_by_ >",value,"createBy");return (Criteria)this;}public Criteria andCreateByGreaterThanOrEqualTo(Long value){addCriterion("create_by_ >=",value,"createBy");return (Criteria)this;}public Criteria andCreateByLessThan(Long value){addCriterion("create_by_ <",value,"createBy");return (Criteria)this;}public Criteria andCreateByLessThanOrEqualTo(Long value){addCriterion("create_by_ <=",value,"createBy");return (Criteria)this;}public Criteria andCreateByIn(List<Long> values){addCriterion("create_by_ in",values,"createBy");return (Criteria)this;}public Criteria andCreateByNotIn(List<Long> values){addCriterion("create_by_ not in",values,"createBy");return (Criteria)this;}public Criteria andCreateByBetween(Long value1,Long value2){addCriterion("create_by_ between",value1,value2,"createBy");return (Criteria)this;}public Criteria andCreateByNotBetween(Long value1,Long value2){addCriterion("create_by_ not between",value1,value2,"createBy");return (Criteria)this;}public Criteria andCreateTimeIsNull(){addCriterion("create_time_ is null");return (Criteria)this;}public Criteria andCreateTimeIsNotNull(){addCriterion("create_time_ is not null");return (Criteria)this;}public Criteria andCreateTimeEqualTo(Date value){addCriterion("create_time_ =",value,"createTime");return (Criteria)this;}public Criteria andCreateTimeNotEqualTo(Date value){addCriterion("create_time_ <>",value,"createTime");return (Criteria)this;}public Criteria andCreateTimeGreaterThan(Date value){addCriterion("create_time_ >",value,"createTime");return (Criteria)this;}public Criteria andCreateTimeGreaterThanOrEqualTo(Date value){addCriterion("create_time_ >=",value,"createTime");return (Criteria)this;}public Criteria andCreateTimeLessThan(Date value){addCriterion("create_time_ <",value,"createTime");return (Criteria)this;}public Criteria andCreateTimeLessThanOrEqualTo(Date value){addCriterion("create_time_ <=",value,"createTime");return (Criteria)this;}public Criteria andCreateTimeIn(List<Date> values){addCriterion("create_time_ in",values,"createTime");return (Criteria)this;}public Criteria andCreateTimeNotIn(List<Date> values){addCriterion("create_time_ not in",values,"createTime");return (Criteria)this;}public Criteria andCreateTimeBetween(Date value1,Date value2){addCriterion("create_time_ between",value1,value2,"createTime");return (Criteria)this;}public Criteria andCreateTimeNotBetween(Date value1,Date value2){addCriterion("create_time_ not between",value1,value2,"createTime");return (Criteria)this;}}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table grade
	 * @mbg.generated  Fri Mar 31 19:32:28 CST 2017
	 */public static class Criterion {private String condition;private Object value;private Object secondValue;private boolean noValue;private boolean singleValue;private boolean betweenValue;private boolean listValue;private String typeHandler;public String getCondition(){return condition;}public Object getValue(){return value;}public Object getSecondValue(){return secondValue;}public boolean isNoValue(){return noValue;}public boolean isSingleValue(){return singleValue;}public boolean isBetweenValue(){return betweenValue;}public boolean isListValue(){return listValue;}public String getTypeHandler(){return typeHandler;}protected Criterion(String condition){super();this.condition=condition;this.typeHandler=null;this.noValue=true;}protected Criterion(String condition,Object value,String typeHandler){super();this.condition=condition;this.value=value;this.typeHandler=typeHandler;if (value instanceof List<?>){this.listValue=true;} else {this.singleValue=true;}}protected Criterion(String condition,Object value){this(condition,value,null);}protected Criterion(String condition,Object value,Object secondValue,String typeHandler){super();this.condition=condition;this.value=value;this.secondValue=secondValue;this.typeHandler=typeHandler;this.betweenValue=true;}protected Criterion(String condition,Object value,Object secondValue){this(condition,value,secondValue,null);}}

	/**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table grade
     *
     * @mbg.generated do_not_delete_during_merge Fri Mar 31 19:09:46 CST 2017
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}