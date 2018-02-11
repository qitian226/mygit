package com.cos.service.comment.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.common.utils.JsonModel;
import com.cos.common.utils.PrimaryKeyGenerator;
import com.cos.common.utils.ReturnJSONUtil;
import com.cos.dao.CommentMapper;
import com.cos.dao.GradeMapper;
import com.cos.dao.ReplyMapper;
import com.cos.exception.BusinessException;
import com.cos.model.Comment;
import com.cos.model.CommentExample;
import com.cos.model.Entry;
import com.cos.model.EntryExample;
import com.cos.model.Grade;
import com.cos.model.GradeExample;
import com.cos.model.Reply;
import com.cos.model.ReplyExample;
import com.cos.model.SysAccount;
import com.cos.model.Topic;
import com.cos.service.comment.ICommentService;
import com.cos.service.sys.ISysAccountService;
import com.cos.service.topic.ITopicService;

@Service @Transactional
public class CommentService implements ICommentService {


	@Resource
	private GradeMapper gradeDao;
	@Resource
	private CommentMapper commentDao;
	@Resource
	private ReplyMapper replyDao;
	@Resource
	private ISysAccountService sysAccountService;
	@Resource
	private ITopicService topicService;

	@Override
	public String praiseComment(String commentId, Long accountId) {
		GradeExample example=new GradeExample();
		example.createCriteria().andTagetIdEqualTo(Long.parseLong(commentId)).andCreateByEqualTo(accountId);
		Long count=gradeDao.countByExample(example);
		Comment comment=commentDao.selectByPrimaryKey(Long.parseLong(commentId));
		if(null==count || count.compareTo(0L)==0){
			Grade ng=new Grade();
			Long gid=PrimaryKeyGenerator.getPrimaryKey("grade");
			ng.setId(gid);
			ng.setCreateBy(accountId);
			ng.setTagetId(Long.parseLong(commentId));
			ng.setType((short) 2);
			ng.setGrade(1);
			gradeDao.insert(ng);
			comment.setEndorse((comment.getEndorse()==null?0:comment.getEndorse())+1);
			commentDao.updateByPrimaryKey(comment);
		}
		return ReturnJSONUtil.getSuccessExtendInfo("评论成功", new JsonModel("data",comment.getEndorse()));
	}

	@Override
	public Reply submitReply(Long foucsuser,String content, Long commentId,SysAccount account,String tocken) {
		 Comment c=commentDao.selectByPrimaryKey(commentId);
		 if(null==c){
			 throw new BusinessException("评论不存在!");
		 }
		ReplyExample exa=new  ReplyExample();
		exa.createCriteria().andTockenEqualTo(tocken);
		Long count= replyDao.countByExample(exa);
		if(count.compareTo(1L)>=0){ //防重
			return null;
		}
		Reply r=new Reply();
		 Long id=PrimaryKeyGenerator.getPrimaryKey("reply");
		 r.setId(id);
		 r.setCreateBy(account.getId());
		 r.setContent(content);
		 r.setCurrVersion((short)1);
		 r.setTocken(tocken);
		 r.setCommentId(commentId);
		 if(foucsuser.compareTo(account.getId())!=0){
	     SysAccount caccount= sysAccountService.querySimpleAccount(foucsuser);
		 r.setReplyTo(account.getNickname()+"@"+caccount.getNickname());
		 }else
		 {
			 r.setReplyTo(account.getNickname());
		 }

		 String replyIds=c.getReplyIds();
		 if(StringUtils.isBlank(replyIds)){
			 replyIds=id.toString();
		 }else{
		 replyIds=replyIds+","+id;
		 }
		 c.setReplyIds(replyIds);

		 try{
		 replyDao.insert(r);
		 commentDao.updateByPrimaryKeySelective(c);
		 }catch(Exception e){
			 throw new BusinessException("发表回复错误!", e);
		 }
		 r=replyDao.selectByPrimaryKey(id);
		 r.setAuthor(account);
		return r;
	}
	@Override
	public int submitComment(String content, Long entryId, SysAccount account, String grade,String tocken) {
		CommentExample exa=new CommentExample();
		exa.createCriteria().andTockenEqualTo(tocken);
		Long count=commentDao.countByExample(exa);
		if(count.compareTo(1L)>=0){ //防重
			return 0;
		}
		Entry entry=topicService.querySingleEntry(entryId);
		if(null==entry){
			throw new BusinessException("没有这个单片哦!");
		}
		if(StringUtils.isBlank(content)){
			throw new BusinessException("评论内容不能为空!");
		}
		if(StringUtils.isBlank(grade)){
			grade="0";
		}
		if(StringUtils.isNoneBlank(grade)){
			int g = 0;
			try{
			g=Integer.parseInt(grade);
			}catch(NumberFormatException e){
				grade="0";
			}
			if(g<0||g>100){
				grade="0";
			}
		}

		Long topicId=entry.getTopicId();

		Comment  comment=new Comment();
		Long cid=PrimaryKeyGenerator.getPrimaryKey("comment");
		comment.setId(cid);
		comment.setContent(content);
		comment.setCreateBy(account.getId());
		comment.setTargetId(entryId);
		comment.setTocken(tocken);
		comment.setCurrVersion((short)1);
		int cg=Integer.parseInt(grade);
		comment.setStatus((short) 1);
		GradeExample exa1=new GradeExample();
		exa1.createCriteria().andCreateByEqualTo(account.getId()).andTagetIdEqualTo(topicId);
		List<Grade> g= gradeDao.selectByExample(exa1);//评分表
		try{
			if(g.size()>0 && g.get(0).getGrade()>0){ //评过分
				cg=g.get(0).getGrade();
				comment.setGrade(cg);
				commentDao.insert(comment); //拿到评价分数
			}else{ //未评过分 写入评分表
				if(cg!=0){
				Grade ng=new Grade();
				Long gid=PrimaryKeyGenerator.getPrimaryKey("grade");
				ng.setId(gid);
				ng.setCreateBy(account.getId());
				ng.setTagetId(topicId);
				ng.setType((short) 1);
				ng.setGrade(cg);
				gradeDao.insert(ng);
				}
				comment.setGrade(cg);
				commentDao.insert(comment);

				EntryExample exam=new EntryExample();
				exam.createCriteria().andTopicIdEqualTo(topicId);
				List<Entry> entrys=topicService.queryListEntrys(exam);
				List<Long> entryIds=new ArrayList<Long>();
				for(Entry item:entrys){
					entryIds.add(item.getId());
				}
				CommentExample example=new CommentExample();
				example.createCriteria().andTargetIdIn(entryIds).andCreateByEqualTo(account.getId());
				Comment record=new Comment();
				record.setGrade(cg);
				commentDao.updateByExampleSelective(record, example);//批量修改此主题未打分记录
				exa1.createCriteria().andTagetIdEqualTo(topicId);
				List<Grade> gs= gradeDao.selectByExample(exa1);
				int tg=0;
				if(gs.size()>0){
				for(Grade gr:gs){
					tg=tg+gr.getGrade();
				}
				tg=tg/gs.size();
				}
				Topic topic=topicService.querySingleTopic(topicId);
				topic.setGrade(tg);
				topicService.updateSingleTopic(topic);
			 }
			entry.setCommentNum((entry.getCommentNum()==null?0:entry.getCommentNum())+1);
			topicService.updateSingleEntry(entry);
		}catch(Exception e){
			throw new BusinessException("发表评论错误!", e);
		}
		return entry.getCommentNum();
	}

	@Override
	public String removeComment(Long commentId, Long loginId) {
        Comment comment= commentDao.selectByPrimaryKey(commentId);
        if(comment.getCreateBy().compareTo(loginId)!=0){
        	return ReturnJSONUtil.getWarnInfo("你没有删除这个评论的权限哦!");
        }
        Entry entry=null;
        try{
        	commentDao.deleteByPrimaryKey(commentId);
        	ReplyExample example=new ReplyExample();
        	example.createCriteria().andCommentIdEqualTo(commentId);
			replyDao.deleteByExample(example);
			entry=topicService.querySingleEntry(comment.getTargetId());
			if(null!=entry){
			entry.setCommentNum((entry.getCommentNum()-1<0)?0:entry.getCommentNum()-1);
			topicService.updateSingleEntry(entry);
			}
        }catch(Exception ex){
        	throw new BusinessException("删除评论错误!", ex);
        }
		return  ReturnJSONUtil.getSuccessExtendInfo("删除评论成功!",new JsonModel("entry",entry.getId()),new JsonModel("cnum",entry.getCommentNum()));
	}
}
