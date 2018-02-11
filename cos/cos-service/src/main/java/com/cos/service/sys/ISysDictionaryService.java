package com.cos.service.sys;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.cos.model.BaseDictionary;
import com.cos.model.BaseDictionaryitem;
 
 

/**
 * TODO What the class does
 * @author qikunlun
 * @date   2016年1月15日-下午2:49:17
 *
 */
public interface ISysDictionaryService {

	/**
	 * @return
	 */
	int getItemTotalCountNum();

	int getDicTotalCountNum();
	/**
	 * @param id
	 * @return
	 */
	BaseDictionaryitem getSysDictionaryItem(String id);

	/**
	 * @param id
	 * @param dictcode
	 * @param dictitemname
	 * @return
	 */
	String checkUpdateRule(Long id, String dictcode, String dictitemname);

	 
	/**
	 * @param dictcode
	 * @param dictitemname
	 * @return
	 */
	String checkRule(String dictcode, String dictitemname);

 
	/**
	 * @param where
	 * @return
	 */
	int queryExistDicItem(String where,String diccode);

	/**
	 * @param curentid
	 * @param forwordid
	 */
	void moveSortId(String curentid, String forwordid);

	/**
	 * @return
	 */
	String loadSysDic();

	/**
	 * @param id
	 * @param dictcode
	 * @param dictitemname
	 * @return
	 */
	String checkDicUpdateRule(Long id,String dictcode, String dictitemname);

	/**
	 * @param dictcode
	 * @param dictitemname
	 * @return
	 */
	String checkDicRule(String dictcode, String dictitemname);

	/**
	 * @param dic
	 * @return
	 */
	String addDic(BaseDictionary dic);

	/**
	 * @param dic
	 * @return
	 */
	String updateDic(BaseDictionary dic);

	/**
	 * @param dictcode
	 * @return
	 */
	String delDic(String dictcode);


	/**
	 * @param item
	 * @return
	 */
	String addDictionary(BaseDictionaryitem item);

	/**
	 * @param item
	 * @return
	 */
	String updateDictionary(BaseDictionaryitem item);

	/**
	 * @param id
	 * @return
	 */
	String delDictionary(String id);

	Integer querySortId();
    
	List<BaseDictionaryitem> queryDictionaryitemByCode(String code);

    BaseDictionaryitem queryParamTypeBySortId(int dataType);
    
    BaseDictionaryitem queryDicValueByName(String dicName);

	List<Map<String, Object>> queryDictionary(String word, int page, int limit);

	List<Map<String, Object>> queryDictionaryItem(String code);

	BaseDictionaryitem queryDicValueByTypeAndName(String code, String type);
	
	List<BaseDictionaryitem> queryDictionaryitemByCodes(String code,List<String> names);

	BaseDictionary queryDictionaryById(Long id);

	BaseDictionary queryDictionaryByCode(String code);

	String virtualrRemoveDic(String code);

	BaseDictionaryitem queryDictionaryitemById(Long id);

	String virtualrRemoveDicitem(Long id);
}
