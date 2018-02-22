package com.cos.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.cos.common.utils.RegularUtil;
import com.cos.common.utils.ReturnJSONUtil;
import com.cos.controller.base.BaseController;
import com.cos.enums.PageNumEnum;
import com.cos.exception.BusinessException;
import com.cos.model.Fans;
import com.cos.model.FansGroup;
import com.cos.model.SysAccount;
import com.cos.model.SysEmail;
import com.cos.service.email.ISysEmailService;
import com.cos.service.sys.ISysAccountService;

@RestController
@RequestMapping(value="/email")
public class SysEmailController extends BaseController {

	@Resource
	private ISysEmailService sysEmailService;
	@Resource
	private ISysAccountService sysAccountService;

	@RequestMapping(value = "/{id}")
	public ModelAndView toSiteEmail(@PathVariable Long id) throws Exception {
		ModelAndView view = new ModelAndView("email/sysemail");
		SysAccount account = sysAccountService.getAccount(id);
		view.addObject("formUser", account);
		List<SysEmail> emails = sysEmailService.queryFromUserEmails(id,
				null == this.getAccount() ? null : this.getAccount().getId());
		view.addObject("emails", emails);
		return view;
	}

	@RequestMapping(value = "/emailbox")
	public ModelAndView toSiteEmailBox() throws Exception {
		Long accountId = (null == this.getAccount() ? null : this.getAccount().getId());
		if (accountId == null) {
			return null;
		}
		ModelAndView view = new ModelAndView("email/email_box");
		SysAccount account = sysAccountService.getAccount(accountId);
		String desc = (null == account.getDesc() ? "" : account.getDesc());
		account.setDesc(desc.length() > 26 ? desc.substring(0, 26) + "..." : desc);
		view.addObject("fromUser", account);
		List<SysEmail> emails = sysEmailService.queryReciveEmails(accountId);
		view.addObject("emails", emails);

		List<FansGroup> fangroups = sysEmailService.queryFansAllGroups(accountId);
		view.addObject("groups", fangroups);
		if (null != fangroups && fangroups.size() > 0) {
			List<Fans> fans = sysEmailService.queryGroupFans(0L, this.getAccount().getId());//fans默认分组
			view.addObject("fans", fans);
		}
		List<FansGroup> attengroups = sysEmailService.queryAttentionsAllGroups(accountId);
		view.addObject("attengroups", attengroups);
		if (null != attengroups && attengroups.size() > 0) {
			List<Fans> attens = sysEmailService.queryGroupAttentions(0L, this.getAccount().getId());//关注默认分组
			view.addObject("attens", attens);
		}

		Map<String, List<SysEmail>> infos = null;
		infos = sysEmailService.queryHistoryInfos(account);
		view.addObject("isend", true);
		if (null != infos && infos.size() > 0) {
			List<SysEmail> weekbefore = infos.get("weekbefore");
			if (weekbefore.size() >= PageNumEnum.Email.getValue()) {
				view.addObject("isend", false);
			}
		}
		view.addObject("infos", infos);
		return view;
	}

	@RequestMapping(value = "/refreshemail/{id}")
	public ModelAndView refreshEmail(@PathVariable Long id) throws Exception {
		ModelAndView view = new ModelAndView("email/email_receive_frag");
		view.addObject("fromUserId", id);
		List<SysEmail> emails = sysEmailService.queryFromUserEmails(id,
				(null == this.getAccount()) ? null : this.getAccount().getId());
		view.addObject("emails", emails);
		return view;
	}

	@RequestMapping(value = "/sendemail/{toAccountid}", produces = "text/html;charset=UTF-8")
	public ModelAndView sendEmail(@PathVariable Long toAccountid,
			@RequestParam(value = "tocken", required = true) String tocken,
			@RequestParam(value = "message", required = true) String message) throws Exception {
		if (StringUtils.isBlank(message)) {
			throw new BusinessException("总要说点什么哦!");
		}
		if (message.length() > 500) {
			throw new BusinessException("邮件不能超过500字哦!");
		}
		message=RegularUtil.replaceSpecStr(message, RegularUtil.SPECIAL_CODE);
		ModelAndView view = new ModelAndView("email/email_send_frag");
		SysEmail email = sysEmailService.sendEmail(toAccountid, this.getAccount().getId(), tocken, message);
		view.addObject("email", email);
		view.addObject("fromUserId", this.getAccount().getId());
		return view;
	}

	@RequestMapping(value = "/hisemail/{fromAccountId}", produces = "text/html;charset=UTF-8")
	public ModelAndView queryHistoryEmail(@PathVariable Long fromAccountId, String page) throws Exception {
		ModelAndView view = new ModelAndView("email/email_history");
		Object[] emails = sysEmailService.queryHistoryEmailsByPage(fromAccountId, this.getAccount(),
				Integer.parseInt(page));
		view.addObject("emails", emails[0]);
		view.addObject("isend", emails[1]);
		view.addObject("page", emails[2]);
		view.addObject("fromUserId", fromAccountId);
		view.addObject("toUserId", this.getAccount().getId());
		return view;
	}

	@RequestMapping(value = "/hisemailtitle/{page}", produces = "text/html;charset=UTF-8")
	public ModelAndView queryHistoryEmailTitle(@PathVariable String page) throws Exception {
		ModelAndView view = new ModelAndView("email/email_title");
		List<SysEmail> titles = sysEmailService.queryHistoryEmailsByPage(this.getAccount(), Integer.parseInt(page));
		view.addObject("emailtitles", titles);
		if (titles.size() < PageNumEnum.Email.getValue()) {
			view.addObject("isend", true);
		} else {
			view.addObject("isend", false);
		}
		return view;
	}

	@RequestMapping(value = "/showemail/{emailid}", produces = "text/html;charset=UTF-8")
	public ModelAndView showEmail(@PathVariable Long emailid) throws Exception {
		ModelAndView view = new ModelAndView("email/email_frag");
		SysEmail email = sysEmailService.querySimpleEmail(emailid, this.getAccount());
		view.addObject("email", email);
		if (email.getFromAccount().compareTo(this.getAccount().getId()) == 0) {
			view.addObject("isFrom", false);
		} else {
			view.addObject("isFrom", true);
		}
		return view;
	}

	/*@RequestMapping(value = "/reloadfans", produces = "text/html;charset=UTF-8")
	public ModelAndView reloadGroupFans() throws Exception {
		ModelAndView view = new ModelAndView("email/fans_frag");
		List<FansGroup> fangroups = sysEmailService.queryFansAllGroups(this.getAccount().getId());
		view.addObject("groups", fangroups);
		return view;
	}*/

	@RequestMapping(value = "/fans/{gid}", produces = "text/html;charset=UTF-8")
	public ModelAndView getGroupFans(@PathVariable Long gid) throws Exception {
		ModelAndView view = new ModelAndView("email/fans");
		List<Fans> fans = sysEmailService.queryGroupFans(gid, this.getAccount().getId());
		view.addObject("fans", fans);
		//view.addObject("fansgroupid", gid);
		return view;
	}

	/*
	 * @RequestMapping(value="/fansgroup", produces = "text/html;charset=UTF-8")
	 * public ModelAndView queryFansGroups() throws Exception{ ModelAndView
	 * view=new ModelAndView("email/fansgroup"); List<FansGroup>
	 * fangroups=sysEmailService.queryFansGroups(this.getAccount().getId());
	 * view.addObject("fansgroup", fangroups); return view; }
	 */
	@RequestMapping(value = "/addfansgroup/{gname}", produces = "text/html;charset=UTF-8")
	public String addFansGroups(@PathVariable String gname,short t) throws Exception {
		if(t!=1 && t!=2){
			throw new BusinessException("无此分组类型!");
		}
		if(StringUtils.isBlank(gname)){
			return ReturnJSONUtil.getWarnInfo("分组名称不能为空!");
		}
		 if(gname.length()>15){
			 return ReturnJSONUtil.getWarnInfo("分组名称不能大于15字!");
		  }
		String out = sysEmailService.addFansGroups(gname, this.getAccount().getId(),t);
		return out;
	}

	@RequestMapping(value = "/movetogroup/{uid}/{gid}", produces = "text/html;charset=UTF-8")
	public String moveFansGroups(@PathVariable Long uid, @PathVariable Long gid) throws Exception {
		String out = sysEmailService.moveToFansGroups(uid, gid, this.getAccount().getId());
		return out;
	}
	@RequestMapping(value = "/movetoattengroup/{uid}/{gid}", produces = "text/html;charset=UTF-8")
	public String moveAttenGroups(@PathVariable Long uid, @PathVariable Long gid) throws Exception {
		String out = sysEmailService.moveToAttenGroups(uid, gid, this.getAccount().getId());
		return out;
	}
	@RequestMapping(value = "/removegroup/{gid}", produces = "text/html;charset=UTF-8")
	public String removeFansGroups(@PathVariable String gid) throws Exception {
		String out = sysEmailService.removeFansGroups(Long.parseLong(gid));
		return out;
	}
	@RequestMapping(value = "/atten/{gid}", produces = "text/html;charset=UTF-8")
	public ModelAndView queryGroupFans(@PathVariable Long gid) throws Exception {
		ModelAndView view = new ModelAndView("email/attention");
		List<Fans> attens = sysEmailService.queryGroupAttentions(gid, this.getAccount().getId());
		view.addObject("attens", attens);
		return view;
	}

	@RequestMapping(value = "/checkfans/{uid}", produces = "text/html;charset=UTF-8")
	public String checkFans(@PathVariable Long uid) throws Exception {
		String out = sysEmailService.checkFans(uid,this.getAccount().getId());
		return out;
	}
}
