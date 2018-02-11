package com.cos.service.sys.impl;



import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.common.utils.JSONUtil;
import com.cos.common.utils.ReturnJSONUtil;
import com.cos.dao.BaseDictionaryMapper;
import com.cos.dao.BaseDictionaryitemMapper;
import com.cos.exception.BusinessException;
import com.cos.exception.DaoException;
import com.cos.exception.ServiceException;
import com.cos.model.BaseDictionary;
import com.cos.model.BaseDictionaryExample;
import com.cos.model.BaseDictionaryitem;
import com.cos.model.BaseDictionaryitemExample;
import com.cos.service.sys.ISysDictionaryService;
import com.cos.service.topic.ITopicService;
import com.cos.service.topic.impl.TopicService;



/**
 * TODO What the class does
 *
 * @author qikunlun
 * @date 2016年1月15日-下午2:49:31
 */
@Service
@Transactional
public class SysDictionaryService implements ISysDictionaryService {

    @Resource
    private BaseDictionaryitemMapper sysDictionaryItemDao;
    @Resource
    private BaseDictionaryMapper sysDictionaryDao;
    @Resource
    private ITopicService  topicService;
    @Override
    public int getItemTotalCountNum() {
        BaseDictionaryitemExample example = new BaseDictionaryitemExample();
        example.createCriteria().andIdIsNotNull();
        int count = sysDictionaryItemDao.countByExample(example);
        return count;
    }
	@Override
	public int getDicTotalCountNum() {
		    BaseDictionaryExample example = new BaseDictionaryExample();
	        example.createCriteria().andIdIsNotNull();
	        int count = sysDictionaryDao.countByExample(example);
	        return count;
	}
    @Override
    public BaseDictionaryitem getSysDictionaryItem(String id) {
        BaseDictionaryitem item = sysDictionaryItemDao.selectByPrimaryKey(Long.parseLong(id));
        return item;
    }

    @Override
    public String checkUpdateRule(Long id, String dictcode,
                                  String dictitemname) {
        BaseDictionaryitemExample example = new BaseDictionaryitemExample();
        example.createCriteria().andIdNotEqualTo(id).andDictCodeEqualTo(dictcode).andDictitemNameEqualTo(dictitemname);
        int count = sysDictionaryItemDao.countByExample(example);
        if (count > 0) {
        	return "no";
        }
       return "ok";
    }

    @Override
    public String checkRule(String dictcode, String dictitemname) {
        BaseDictionaryitemExample example = new BaseDictionaryitemExample();
        example.createCriteria().andDictCodeEqualTo(dictcode).andDictitemNameEqualTo(dictitemname);
        int count = sysDictionaryItemDao.countByExample(example);
        if (count > 0) {
        	return "no";
        }
        return "ok";
    }


    @Override
    public int queryExistDicItem(String where, String diccode) {
        BaseDictionaryitemExample example = new BaseDictionaryitemExample();

        if (StringUtils.isNotEmpty(where) && StringUtils.isNotBlank(where)) {
            example.createCriteria().andDictitemNameLike("%" + where + "%");
        }
        if (StringUtils.isNotEmpty(diccode) && StringUtils.isNotBlank(diccode)) {
            example.createCriteria().andDictCodeEqualTo(diccode);
        }
        int count = sysDictionaryItemDao.countByExample(example);
        return count;
    }

    @Override
    public void moveSortId(String curentid, String forwordid) {
        BaseDictionaryitem curentDic = sysDictionaryItemDao.selectByPrimaryKey(Long.parseLong(curentid));
        int cid = curentDic.getSortId();
        BaseDictionaryitem forwordDic = sysDictionaryItemDao.selectByPrimaryKey(Long.parseLong(forwordid));
        int fid = forwordDic.getSortId();
        curentDic.setSortId(fid);
        forwordDic.setSortId(cid);
        sysDictionaryItemDao.updateByPrimaryKey(forwordDic);
        sysDictionaryItemDao.updateByPrimaryKey(curentDic);
    }

    @Override
    public String loadSysDic() {
        BaseDictionaryExample example = new BaseDictionaryExample();
        example.setOrderByClause("created_time_ desc");
        List<BaseDictionary> maps = sysDictionaryDao.selectByExample(example);
        return JSONUtil.outPutObjectJSONData(maps);
    }

    @Override
    public String checkDicUpdateRule(Long id, String dictcode, String dictname) {
        BaseDictionaryExample example = new BaseDictionaryExample();
        example.createCriteria().
                andIdNotEqualTo(id).andDictCodeEqualTo(dictcode);
        example.or(example.createCriteria().
                andIdNotEqualTo(id).andDictNameEqualTo(dictname));
        int count = sysDictionaryDao.countByExample(example);
        if (count > 0) {
        	return "no";
        }
        return "ok";
    }

    @Override
    public String checkDicRule(String dictcode, String dictname) {
        BaseDictionaryExample example = new BaseDictionaryExample();
        example.createCriteria().andDictCodeEqualTo(dictcode);
        example.or( example.createCriteria().andDictNameEqualTo(dictname));
        int count = sysDictionaryDao.countByExample(example);
        if (count > 0) {
        	return "no";
        }
        return "ok";
    }

    @Override
    public String addDic(BaseDictionary dic) {
        try {
        	if(checkDicRule(dic.getDictCode(),dic.getDictName()).equals("no")) {
        		return ReturnJSONUtil.getWarnInfo("此字典CODE或NAME已经存在!");
        	}
            sysDictionaryDao.insert(dic);
        } catch (DaoException e) {
            throw new ServiceException("插入字典错误!", e);
        }
        return ReturnJSONUtil.getSuccessInfo("添加字典成功!");
    }

    @Override
    public String updateDic(BaseDictionary dic) {
        try {
        	if(checkDicUpdateRule(dic.getId(),dic.getDictCode(),dic.getDictName()).equals("no")) {
        		return ReturnJSONUtil.getWarnInfo("此字典CODE或NAME已经存在!");
        	}
            sysDictionaryDao.updateByPrimaryKey(dic);
        } catch (DaoException e) {
            throw new ServiceException("修改字典失败!", e);
        }
        return ReturnJSONUtil.getSuccessInfo("修改字典成功!");
    }

    @Override
    public String delDic(String dictcode) {
        BaseDictionaryitemExample example = new BaseDictionaryitemExample();
        example.createCriteria().andDictCodeEqualTo(dictcode);
        int count = sysDictionaryItemDao.countByExample(example);
        if (count > 0) {
        	return ReturnJSONUtil.getWarnInfo("此字典存在字典项!");
        }
        try {
            BaseDictionaryExample example1 = new BaseDictionaryExample();
            example1.createCriteria().andDictCodeEqualTo(dictcode);
            sysDictionaryDao.deleteByExample(example1);
        } catch (DaoException e) {
            throw new ServiceException("删除字典失败!", e);
        }
        return ReturnJSONUtil.getSuccessInfo("删除字典成功!");
    }

    @Override
    public Integer querySortId() {
        BaseDictionaryitemExample example = new BaseDictionaryitemExample();
        example.createCriteria().andIdIsNotNull();
        int count = sysDictionaryItemDao.countByExample(example);
        if (count == 0) {
            return 0;
        }
        int sortId = sysDictionaryItemDao.querySortId();
        return sortId;
    }

    @Override
    public String addDictionary(BaseDictionaryitem item) {
        try {
        	String out=checkRule(item.getDictCode(),item.getDictitemName());
        	if(out.equals("no")) {
        		return ReturnJSONUtil.getWarnInfo("此字典项已经存在!");
        	}        	
            sysDictionaryItemDao.insert(item);
        } catch (DaoException e) {
            throw new ServiceException("插入字典项失败!", e);
        }
        return ReturnJSONUtil.getSuccessInfo("插入字典项成功!");
    }

    @Override
    public String updateDictionary(BaseDictionaryitem item) {
        try {
        	if(checkUpdateRule(item.getId(),item.getDictCode(),item.getDictitemName()).equals("no")) {
        		return ReturnJSONUtil.getWarnInfo("此字典项已经存在!");
        	}
            sysDictionaryItemDao.updateByPrimaryKeySelective(item);
        } catch (DaoException e) {
            throw new ServiceException("修改字典项失败!", e);
        }
        return ReturnJSONUtil.getSuccessInfo("修改字典项成功!");
    }

    @Override
    public String delDictionary(String id) {
        try {
            sysDictionaryItemDao.deleteByPrimaryKey(Long.parseLong(id));
        } catch (DaoException e) {
            throw new BusinessException("del dic error", e);
        }
        return ReturnJSONUtil.getSuccessInfo("删除字典项成功!");
    }

    @Override
    public List<BaseDictionaryitem> queryDictionaryitemByCode(String code) {
        BaseDictionaryitemExample example = new BaseDictionaryitemExample();
        example.createCriteria().andDictCodeEqualTo(code);
        List<BaseDictionaryitem> items = sysDictionaryItemDao.selectByExample(example);
        return items;
    }

    @Override
    public BaseDictionaryitem queryParamTypeBySortId(int dataType) {
        BaseDictionaryitemExample example = new BaseDictionaryitemExample();
        example.createCriteria().andSortIdEqualTo(dataType);
        List<BaseDictionaryitem> list = sysDictionaryItemDao.selectByExample(example);
        if (list == null || list.size() < 1) {
           return null;
        }
        return list.get(0);
    }

	@Override
	public BaseDictionaryitem queryDicValueByName(String dicName) {
		   BaseDictionaryitemExample example = new BaseDictionaryitemExample();
	        example.createCriteria().andDictitemNameEqualTo(dicName);
	        List<BaseDictionaryitem> list = sysDictionaryItemDao.selectByExample(example);
	        if (list == null || list.size() < 1) {
	             return null;
	        }
	        return list.get(0);
 }

	@Override
	public List<Map<String, Object>> queryDictionary(String word, int page, int limit) {
		 BaseDictionaryExample example = new BaseDictionaryExample();
		 example.setDistinct(true);
		 example.setFirstResult((page-1)*limit);
		 example.setMaxResults(limit);
		 if(StringUtils.isNotBlank(word)){
			example.createCriteria().andDictNameLike("%"+word+"%");
			example.or().andDictCodeLike("%"+word+"%");
			example.or().andDictDescLike("%"+word+"%");
		 }
		 List<Map<String, Object>> maps=sysDictionaryDao.selectPageByExample(example);
		return maps;
	}
	@Override
	public List<Map<String, Object>> queryDictionaryItem(String dicCode) {
		 BaseDictionaryitemExample example = new BaseDictionaryitemExample();
		 example.createCriteria().andDictCodeEqualTo(dicCode);
		 List<Map<String, Object>> maps=sysDictionaryItemDao.queryDictionaryItem(example);
		return maps;
	}
	@Override
	public BaseDictionaryitem queryDicValueByTypeAndName(String code, String name) {
		BaseDictionaryitemExample example = new BaseDictionaryitemExample();
		example.createCriteria().andDictCodeEqualTo(code).andDictitemNameEqualTo(name);
		List<BaseDictionaryitem> items=sysDictionaryItemDao.selectByExample(example);
		if(items.size()>0){
			return items.get(0);
		}
		return null;
	}
	@Override
	public List<BaseDictionaryitem> queryDictionaryitemByCodes(String code,List<String> names) {
		BaseDictionaryitemExample example = new BaseDictionaryitemExample();
		example.createCriteria().andDictCodeEqualTo(code).andDictitemNameIn(names);
		List<BaseDictionaryitem> items=sysDictionaryItemDao.selectByExample(example);
		return items;
	}
	@Override
	public BaseDictionary queryDictionaryById(Long id) {
		BaseDictionary dic=sysDictionaryDao.selectByPrimaryKey(id);
		return dic;
	}
	@Override
	public BaseDictionary queryDictionaryByCode(String code) {
		BaseDictionaryExample example=new BaseDictionaryExample();
		example.createCriteria().andDictCodeEqualTo(code);
		List<BaseDictionary>  dics=sysDictionaryDao.selectByExample(example);
		if(dics.size()>0) {
			return dics.get(0);
		}
		return null;
	}
	@Override
	public String virtualrRemoveDic(String code) {
		BaseDictionaryitemExample exa = new BaseDictionaryitemExample();
		exa.createCriteria().andDictCodeEqualTo(code);
		List<BaseDictionaryitem> items=sysDictionaryItemDao.selectByExample(exa);
		if(items.size()>0) {
			return ReturnJSONUtil.getWarnInfo("此字典存在字典项!不能删除");
		}
		BaseDictionaryExample example=new BaseDictionaryExample();
		example.createCriteria().andDictCodeEqualTo(code);
		List<BaseDictionary>  dics=sysDictionaryDao.selectByExample(example);
		BaseDictionary dic=null;
		if(dics.size()>0) {
			dic= dics.get(0);
		}
		dic.setStatus((short) 0);
		sysDictionaryDao.updateByPrimaryKey(dic);
		return ReturnJSONUtil.getSuccessInfo("删除字典成功!");
	}
	@Override
	public BaseDictionaryitem queryDictionaryitemById(Long id) {
		BaseDictionaryitem item=sysDictionaryItemDao.selectByPrimaryKey(id);
		return item;
	}
	@Override
	public String virtualrRemoveDicitem(Long id) {
		BaseDictionaryitem item=sysDictionaryItemDao.selectByPrimaryKey(id);
		String tag=item.getDictitemName();
		Long count=topicService.queryTotalTopicCountByTag(tag);
		if(count.compareTo(0L)>0) {
			return ReturnJSONUtil.getWarnInfo("此字典存在关联主题!不能删除");	
		}
		item.setStatus((short) 0);
		sysDictionaryItemDao.updateByPrimaryKey(item);
		return ReturnJSONUtil.getSuccessInfo("删除字典项成功!");
	}
}