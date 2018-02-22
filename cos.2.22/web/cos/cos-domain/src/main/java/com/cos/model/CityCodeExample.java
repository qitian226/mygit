package com.cos.model;

import java.util.ArrayList;
import java.util.List;

public class CityCodeExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table city_code
	 * @mbg.generated  Tue Nov 07 16:22:34 CST 2017
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table city_code
	 * @mbg.generated  Tue Nov 07 16:22:34 CST 2017
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table city_code
	 * @mbg.generated  Tue Nov 07 16:22:34 CST 2017
	 */protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table city_code
	 * @mbg.generated  Tue Nov 07 16:22:34 CST 2017
	 */public CityCodeExample(){oredCriteria=new ArrayList<Criteria>();}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table city_code
	 * @mbg.generated  Tue Nov 07 16:22:34 CST 2017
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table city_code
	 * @mbg.generated  Tue Nov 07 16:22:34 CST 2017
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table city_code
	 * @mbg.generated  Tue Nov 07 16:22:34 CST 2017
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table city_code
	 * @mbg.generated  Tue Nov 07 16:22:34 CST 2017
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table city_code
	 * @mbg.generated  Tue Nov 07 16:22:34 CST 2017
	 */public List<Criteria> getOredCriteria(){return oredCriteria;}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table city_code
	 * @mbg.generated  Tue Nov 07 16:22:34 CST 2017
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table city_code
	 * @mbg.generated  Tue Nov 07 16:22:34 CST 2017
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table city_code
	 * @mbg.generated  Tue Nov 07 16:22:34 CST 2017
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table city_code
	 * @mbg.generated  Tue Nov 07 16:22:34 CST 2017
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table city_code
	 * @mbg.generated  Tue Nov 07 16:22:34 CST 2017
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table city_code
	 * @mbg.generated  Tue Nov 07 16:22:34 CST 2017
	 */protected abstract static class GeneratedCriteria {protected List<Criterion> criteria;protected GeneratedCriteria(){super();criteria=new ArrayList<Criterion>();}public boolean isValid(){return criteria.size() > 0;}public List<Criterion> getAllCriteria(){return criteria;}public List<Criterion> getCriteria(){return criteria;}protected void addCriterion(String condition){if (condition == null){throw new RuntimeException("Value for condition cannot be null");}criteria.add(new Criterion(condition));}protected void addCriterion(String condition,Object value,String property){if (value == null){throw new RuntimeException("Value for " + property + " cannot be null");}criteria.add(new Criterion(condition,value));}protected void addCriterion(String condition,Object value1,Object value2,String property){if (value1 == null || value2 == null){throw new RuntimeException("Between values for " + property + " cannot be null");}criteria.add(new Criterion(condition,value1,value2));}public Criteria andIdIsNull(){addCriterion("id_ is null");return (Criteria)this;}public Criteria andIdIsNotNull(){addCriterion("id_ is not null");return (Criteria)this;}public Criteria andIdEqualTo(Integer value){addCriterion("id_ =",value,"id");return (Criteria)this;}public Criteria andIdNotEqualTo(Integer value){addCriterion("id_ <>",value,"id");return (Criteria)this;}public Criteria andIdGreaterThan(Integer value){addCriterion("id_ >",value,"id");return (Criteria)this;}public Criteria andIdGreaterThanOrEqualTo(Integer value){addCriterion("id_ >=",value,"id");return (Criteria)this;}public Criteria andIdLessThan(Integer value){addCriterion("id_ <",value,"id");return (Criteria)this;}public Criteria andIdLessThanOrEqualTo(Integer value){addCriterion("id_ <=",value,"id");return (Criteria)this;}public Criteria andIdIn(List<Integer> values){addCriterion("id_ in",values,"id");return (Criteria)this;}public Criteria andIdNotIn(List<Integer> values){addCriterion("id_ not in",values,"id");return (Criteria)this;}public Criteria andIdBetween(Integer value1,Integer value2){addCriterion("id_ between",value1,value2,"id");return (Criteria)this;}public Criteria andIdNotBetween(Integer value1,Integer value2){addCriterion("id_ not between",value1,value2,"id");return (Criteria)this;}public Criteria andAreaCodeIsNull(){addCriterion("area_code_ is null");return (Criteria)this;}public Criteria andAreaCodeIsNotNull(){addCriterion("area_code_ is not null");return (Criteria)this;}public Criteria andAreaCodeEqualTo(Integer value){addCriterion("area_code_ =",value,"areaCode");return (Criteria)this;}public Criteria andAreaCodeNotEqualTo(Integer value){addCriterion("area_code_ <>",value,"areaCode");return (Criteria)this;}public Criteria andAreaCodeGreaterThan(Integer value){addCriterion("area_code_ >",value,"areaCode");return (Criteria)this;}public Criteria andAreaCodeGreaterThanOrEqualTo(Integer value){addCriterion("area_code_ >=",value,"areaCode");return (Criteria)this;}public Criteria andAreaCodeLessThan(Integer value){addCriterion("area_code_ <",value,"areaCode");return (Criteria)this;}public Criteria andAreaCodeLessThanOrEqualTo(Integer value){addCriterion("area_code_ <=",value,"areaCode");return (Criteria)this;}public Criteria andAreaCodeIn(List<Integer> values){addCriterion("area_code_ in",values,"areaCode");return (Criteria)this;}public Criteria andAreaCodeNotIn(List<Integer> values){addCriterion("area_code_ not in",values,"areaCode");return (Criteria)this;}public Criteria andAreaCodeBetween(Integer value1,Integer value2){addCriterion("area_code_ between",value1,value2,"areaCode");return (Criteria)this;}public Criteria andAreaCodeNotBetween(Integer value1,Integer value2){addCriterion("area_code_ not between",value1,value2,"areaCode");return (Criteria)this;}public Criteria andPAreaCodeIsNull(){addCriterion("p_area_code_ is null");return (Criteria)this;}public Criteria andPAreaCodeIsNotNull(){addCriterion("p_area_code_ is not null");return (Criteria)this;}public Criteria andPAreaCodeEqualTo(Integer value){addCriterion("p_area_code_ =",value,"pAreaCode");return (Criteria)this;}public Criteria andPAreaCodeNotEqualTo(Integer value){addCriterion("p_area_code_ <>",value,"pAreaCode");return (Criteria)this;}public Criteria andPAreaCodeGreaterThan(Integer value){addCriterion("p_area_code_ >",value,"pAreaCode");return (Criteria)this;}public Criteria andPAreaCodeGreaterThanOrEqualTo(Integer value){addCriterion("p_area_code_ >=",value,"pAreaCode");return (Criteria)this;}public Criteria andPAreaCodeLessThan(Integer value){addCriterion("p_area_code_ <",value,"pAreaCode");return (Criteria)this;}public Criteria andPAreaCodeLessThanOrEqualTo(Integer value){addCriterion("p_area_code_ <=",value,"pAreaCode");return (Criteria)this;}public Criteria andPAreaCodeIn(List<Integer> values){addCriterion("p_area_code_ in",values,"pAreaCode");return (Criteria)this;}public Criteria andPAreaCodeNotIn(List<Integer> values){addCriterion("p_area_code_ not in",values,"pAreaCode");return (Criteria)this;}public Criteria andPAreaCodeBetween(Integer value1,Integer value2){addCriterion("p_area_code_ between",value1,value2,"pAreaCode");return (Criteria)this;}public Criteria andPAreaCodeNotBetween(Integer value1,Integer value2){addCriterion("p_area_code_ not between",value1,value2,"pAreaCode");return (Criteria)this;}public Criteria andAreaNameIsNull(){addCriterion("area_name_ is null");return (Criteria)this;}public Criteria andAreaNameIsNotNull(){addCriterion("area_name_ is not null");return (Criteria)this;}public Criteria andAreaNameEqualTo(String value){addCriterion("area_name_ =",value,"areaName");return (Criteria)this;}public Criteria andAreaNameNotEqualTo(String value){addCriterion("area_name_ <>",value,"areaName");return (Criteria)this;}public Criteria andAreaNameGreaterThan(String value){addCriterion("area_name_ >",value,"areaName");return (Criteria)this;}public Criteria andAreaNameGreaterThanOrEqualTo(String value){addCriterion("area_name_ >=",value,"areaName");return (Criteria)this;}public Criteria andAreaNameLessThan(String value){addCriterion("area_name_ <",value,"areaName");return (Criteria)this;}public Criteria andAreaNameLessThanOrEqualTo(String value){addCriterion("area_name_ <=",value,"areaName");return (Criteria)this;}public Criteria andAreaNameLike(String value){addCriterion("area_name_ like",value,"areaName");return (Criteria)this;}public Criteria andAreaNameNotLike(String value){addCriterion("area_name_ not like",value,"areaName");return (Criteria)this;}public Criteria andAreaNameIn(List<String> values){addCriterion("area_name_ in",values,"areaName");return (Criteria)this;}public Criteria andAreaNameNotIn(List<String> values){addCriterion("area_name_ not in",values,"areaName");return (Criteria)this;}public Criteria andAreaNameBetween(String value1,String value2){addCriterion("area_name_ between",value1,value2,"areaName");return (Criteria)this;}public Criteria andAreaNameNotBetween(String value1,String value2){addCriterion("area_name_ not between",value1,value2,"areaName");return (Criteria)this;}public Criteria andProvinceCodeIsNull(){addCriterion("province_code_ is null");return (Criteria)this;}public Criteria andProvinceCodeIsNotNull(){addCriterion("province_code_ is not null");return (Criteria)this;}public Criteria andProvinceCodeEqualTo(String value){addCriterion("province_code_ =",value,"provinceCode");return (Criteria)this;}public Criteria andProvinceCodeNotEqualTo(String value){addCriterion("province_code_ <>",value,"provinceCode");return (Criteria)this;}public Criteria andProvinceCodeGreaterThan(String value){addCriterion("province_code_ >",value,"provinceCode");return (Criteria)this;}public Criteria andProvinceCodeGreaterThanOrEqualTo(String value){addCriterion("province_code_ >=",value,"provinceCode");return (Criteria)this;}public Criteria andProvinceCodeLessThan(String value){addCriterion("province_code_ <",value,"provinceCode");return (Criteria)this;}public Criteria andProvinceCodeLessThanOrEqualTo(String value){addCriterion("province_code_ <=",value,"provinceCode");return (Criteria)this;}public Criteria andProvinceCodeLike(String value){addCriterion("province_code_ like",value,"provinceCode");return (Criteria)this;}public Criteria andProvinceCodeNotLike(String value){addCriterion("province_code_ not like",value,"provinceCode");return (Criteria)this;}public Criteria andProvinceCodeIn(List<String> values){addCriterion("province_code_ in",values,"provinceCode");return (Criteria)this;}public Criteria andProvinceCodeNotIn(List<String> values){addCriterion("province_code_ not in",values,"provinceCode");return (Criteria)this;}public Criteria andProvinceCodeBetween(String value1,String value2){addCriterion("province_code_ between",value1,value2,"provinceCode");return (Criteria)this;}public Criteria andProvinceCodeNotBetween(String value1,String value2){addCriterion("province_code_ not between",value1,value2,"provinceCode");return (Criteria)this;}public Criteria andCityCodeIsNull(){addCriterion("city_code_ is null");return (Criteria)this;}public Criteria andCityCodeIsNotNull(){addCriterion("city_code_ is not null");return (Criteria)this;}public Criteria andCityCodeEqualTo(String value){addCriterion("city_code_ =",value,"cityCode");return (Criteria)this;}public Criteria andCityCodeNotEqualTo(String value){addCriterion("city_code_ <>",value,"cityCode");return (Criteria)this;}public Criteria andCityCodeGreaterThan(String value){addCriterion("city_code_ >",value,"cityCode");return (Criteria)this;}public Criteria andCityCodeGreaterThanOrEqualTo(String value){addCriterion("city_code_ >=",value,"cityCode");return (Criteria)this;}public Criteria andCityCodeLessThan(String value){addCriterion("city_code_ <",value,"cityCode");return (Criteria)this;}public Criteria andCityCodeLessThanOrEqualTo(String value){addCriterion("city_code_ <=",value,"cityCode");return (Criteria)this;}public Criteria andCityCodeLike(String value){addCriterion("city_code_ like",value,"cityCode");return (Criteria)this;}public Criteria andCityCodeNotLike(String value){addCriterion("city_code_ not like",value,"cityCode");return (Criteria)this;}public Criteria andCityCodeIn(List<String> values){addCriterion("city_code_ in",values,"cityCode");return (Criteria)this;}public Criteria andCityCodeNotIn(List<String> values){addCriterion("city_code_ not in",values,"cityCode");return (Criteria)this;}public Criteria andCityCodeBetween(String value1,String value2){addCriterion("city_code_ between",value1,value2,"cityCode");return (Criteria)this;}public Criteria andCityCodeNotBetween(String value1,String value2){addCriterion("city_code_ not between",value1,value2,"cityCode");return (Criteria)this;}public Criteria andZipCodeIsNull(){addCriterion("zip_code_ is null");return (Criteria)this;}public Criteria andZipCodeIsNotNull(){addCriterion("zip_code_ is not null");return (Criteria)this;}public Criteria andZipCodeEqualTo(String value){addCriterion("zip_code_ =",value,"zipCode");return (Criteria)this;}public Criteria andZipCodeNotEqualTo(String value){addCriterion("zip_code_ <>",value,"zipCode");return (Criteria)this;}public Criteria andZipCodeGreaterThan(String value){addCriterion("zip_code_ >",value,"zipCode");return (Criteria)this;}public Criteria andZipCodeGreaterThanOrEqualTo(String value){addCriterion("zip_code_ >=",value,"zipCode");return (Criteria)this;}public Criteria andZipCodeLessThan(String value){addCriterion("zip_code_ <",value,"zipCode");return (Criteria)this;}public Criteria andZipCodeLessThanOrEqualTo(String value){addCriterion("zip_code_ <=",value,"zipCode");return (Criteria)this;}public Criteria andZipCodeLike(String value){addCriterion("zip_code_ like",value,"zipCode");return (Criteria)this;}public Criteria andZipCodeNotLike(String value){addCriterion("zip_code_ not like",value,"zipCode");return (Criteria)this;}public Criteria andZipCodeIn(List<String> values){addCriterion("zip_code_ in",values,"zipCode");return (Criteria)this;}public Criteria andZipCodeNotIn(List<String> values){addCriterion("zip_code_ not in",values,"zipCode");return (Criteria)this;}public Criteria andZipCodeBetween(String value1,String value2){addCriterion("zip_code_ between",value1,value2,"zipCode");return (Criteria)this;}public Criteria andZipCodeNotBetween(String value1,String value2){addCriterion("zip_code_ not between",value1,value2,"zipCode");return (Criteria)this;}public Criteria andDevisionLevelIsNull(){addCriterion("devision_level_ is null");return (Criteria)this;}public Criteria andDevisionLevelIsNotNull(){addCriterion("devision_level_ is not null");return (Criteria)this;}public Criteria andDevisionLevelEqualTo(Integer value){addCriterion("devision_level_ =",value,"devisionLevel");return (Criteria)this;}public Criteria andDevisionLevelNotEqualTo(Integer value){addCriterion("devision_level_ <>",value,"devisionLevel");return (Criteria)this;}public Criteria andDevisionLevelGreaterThan(Integer value){addCriterion("devision_level_ >",value,"devisionLevel");return (Criteria)this;}public Criteria andDevisionLevelGreaterThanOrEqualTo(Integer value){addCriterion("devision_level_ >=",value,"devisionLevel");return (Criteria)this;}public Criteria andDevisionLevelLessThan(Integer value){addCriterion("devision_level_ <",value,"devisionLevel");return (Criteria)this;}public Criteria andDevisionLevelLessThanOrEqualTo(Integer value){addCriterion("devision_level_ <=",value,"devisionLevel");return (Criteria)this;}public Criteria andDevisionLevelIn(List<Integer> values){addCriterion("devision_level_ in",values,"devisionLevel");return (Criteria)this;}public Criteria andDevisionLevelNotIn(List<Integer> values){addCriterion("devision_level_ not in",values,"devisionLevel");return (Criteria)this;}public Criteria andDevisionLevelBetween(Integer value1,Integer value2){addCriterion("devision_level_ between",value1,value2,"devisionLevel");return (Criteria)this;}public Criteria andDevisionLevelNotBetween(Integer value1,Integer value2){addCriterion("devision_level_ not between",value1,value2,"devisionLevel");return (Criteria)this;}}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table city_code
	 * @mbg.generated  Tue Nov 07 16:22:34 CST 2017
	 */public static class Criterion {private String condition;private Object value;private Object secondValue;private boolean noValue;private boolean singleValue;private boolean betweenValue;private boolean listValue;private String typeHandler;public String getCondition(){return condition;}public Object getValue(){return value;}public Object getSecondValue(){return secondValue;}public boolean isNoValue(){return noValue;}public boolean isSingleValue(){return singleValue;}public boolean isBetweenValue(){return betweenValue;}public boolean isListValue(){return listValue;}public String getTypeHandler(){return typeHandler;}protected Criterion(String condition){super();this.condition=condition;this.typeHandler=null;this.noValue=true;}protected Criterion(String condition,Object value,String typeHandler){super();this.condition=condition;this.value=value;this.typeHandler=typeHandler;if (value instanceof List<?>){this.listValue=true;} else {this.singleValue=true;}}protected Criterion(String condition,Object value){this(condition,value,null);}protected Criterion(String condition,Object value,Object secondValue,String typeHandler){super();this.condition=condition;this.value=value;this.secondValue=secondValue;this.typeHandler=typeHandler;this.betweenValue=true;}protected Criterion(String condition,Object value,Object secondValue){this(condition,value,secondValue,null);}}

	/**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table city_code
     *
     * @mbg.generated do_not_delete_during_merge Tue Oct 31 17:01:35 CST 2017
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}