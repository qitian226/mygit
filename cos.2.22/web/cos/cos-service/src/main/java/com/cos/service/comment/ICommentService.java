package com.cos.service.comment;


import com.cos.model.Reply;
import com.cos.model.SysAccount;

public interface ICommentService {


	String praiseComment(String commentId, Long accountId);

	Reply submitReply(Long foucsuser, String c, Long id, SysAccount account, String tocken);

	int submitComment(String content, Long topicId, SysAccount account, String grade,String tocken);

	String removeComment(Long commentId, Long loginId);
}
