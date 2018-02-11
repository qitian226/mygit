package com.cos.service.email;

import java.util.List;
import java.util.Map;

import com.cos.model.Fans;
import com.cos.model.FansGroup;
import com.cos.model.SysAccount;
import com.cos.model.SysEmail;

public interface ISysEmailService {

	List<SysEmail> queryFromUserEmails(Long formUserid,Long toUserId);

	SysEmail sendEmail(Long toUserid, Long fromUserId,String tocken, String message);

	Object[] queryHistoryEmailsByPage(Long formUserid,SysAccount toUser,int page);

	List<Fans> queryGroupFans(Long gid,Long accountId);

	List<FansGroup> queryFansAllGroups(Long accountId);

	List<FansGroup> queryFansGroups(Long accountId);

	Map<String,List<SysEmail>> queryHistoryInfos(SysAccount account);

    List<SysEmail> queryHistoryDayInfos(int hid);

    List<SysEmail> queryHistoryWeekInfos(int page);

	String removeFansGroups(long gid);

	String addFansGroups(String gname, Long id,short type);

	String moveToFansGroups(Long fansid, Long gid,Long accountId);

	List<SysEmail> queryHistoryEmailsByPage(SysAccount account, int page);

	SysEmail querySimpleEmail(Long emailId,SysAccount account);

	List<SysEmail> queryReciveEmails(Long accountId);

	List<FansGroup> queryAttentionsAllGroups(Long accountId);

	List<Fans> queryGroupAttentions(Long gid,Long accountId);

	String moveToAttenGroups(Long uid, Long gid, Long id);

	String checkFans(Long uid,Long LoginId);

}
