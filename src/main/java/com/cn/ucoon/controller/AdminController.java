package com.cn.ucoon.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cn.ucoon.pojo.Admin;
import com.cn.ucoon.pojo.AdminGroup;
import com.cn.ucoon.pojo.Feedback;
import com.cn.ucoon.pojo.Menu;
import com.cn.ucoon.pojo.Mission;
import com.cn.ucoon.pojo.Mobile;
import com.cn.ucoon.pojo.Pemission;
import com.cn.ucoon.pojo.PhotoTurn;
import com.cn.ucoon.pojo.Report;
import com.cn.ucoon.pojo.ServerWeixin;
import com.cn.ucoon.pojo.ServiceMessage;
import com.cn.ucoon.pojo.User;
import com.cn.ucoon.pojo.wx.AccessToken;
import com.cn.ucoon.pojo.wx.Template;
import com.cn.ucoon.pojo.wx.TemplateParam;
import com.cn.ucoon.service.AdminService;
import com.cn.ucoon.service.MenuService;
import com.cn.ucoon.service.MissionService;
import com.cn.ucoon.service.MobileService;
import com.cn.ucoon.service.PhotoTurnService;
import com.cn.ucoon.service.ServerWeixinService;
import com.cn.ucoon.service.ServiceMessageService;
import com.cn.ucoon.util.WeixinUtil;
import com.cn.ucoon.util.sendThread;

@Controller
@RequestMapping("/admin")
public class AdminController {
	public static final String MISSION_IMAGE_LOCATION = "D:/ucoonFileUpload/";
	@Resource
	private AdminService adminService;

	@Resource
	private MenuService menuService;

	@Resource
	private MissionService missionService;

	@Autowired
	private PhotoTurnService photoTurnServiceImpl;

	@Resource
	private ServiceMessageService serviceMessageService;

	@Resource
	private ServerWeixinService serverWeixinService;

	@Autowired
	private MobileService mobileService;

	// 登陆界面
	@RequestMapping("/login")
	public String toLogin() {
		return "/admin/login";
	}

	@RequestMapping("/photo")
	public String setPhoto() {
		List<PhotoTurn> photoList = photoTurnServiceImpl.getPhoto();
		System.out.println(JSON.toJSONString(photoList));
		PhotoTurn photo = new PhotoTurn();
		photo.setPhotoId(11);
		photo.setPhotoGoUrl("change");
		photoTurnServiceImpl.changePhoto(photo);
		return "admin/login";
	}

	// 退出登陆
	@RequestMapping("/logout")
	public String toLogout(HttpSession session) {
		session.invalidate();
		return "/admin/login";
	}

	// 删除单个图片
	@ResponseBody
	@RequestMapping("/photoDelete")
	public String photoDelete(Integer opid, String pname) {
		int i = photoTurnServiceImpl.deletePhoto(opid);
		System.out.println(pname);
		pname = pname.replace("/", ".");
		File file = new File(MISSION_IMAGE_LOCATION + pname);
		boolean a = file.delete();
		System.out.println("aaa===" + a);
		return "success";
	}

	// 批量删除图片
	@ResponseBody
	@RequestMapping("/deletephotogroup")
	public String deletephotogroup(
			@RequestParam(value = "photoIds[]") Integer[] photoIds,
			@RequestParam(value = "pnames[]") String[] pnames) {
		System.out.println(photoIds.length);
		System.out.println("pname长度" + pnames.length);
		File file = null;
		String pname = null;
		for (int i = 0; i < pnames.length; i++) {
			pname = pnames[i].replace("/", ".");
			file = new File(MISSION_IMAGE_LOCATION + pname);
			file.delete();
		}
		int i = photoTurnServiceImpl.deleteGroupPhoto(photoIds);
		return "success";
	}

	// 登陆成功后跳转界面
	@RequestMapping("/index")
	public String toIndex(HttpServletRequest request) {
		return "/admin/index";
	}

	// 登陆成功后跳转界面
	@ResponseBody
	@RequestMapping("/getPermission")
	public List<Menu> getPermission(HttpSession session) {
		// 到后面添加拦截器上去 一些代码可以去掉
		int adminId = 0;
		if (session.getAttribute("admin_id") != null)
			adminId = (int) session.getAttribute("admin_id");
		System.out
				.println("public List<HashMap<String, Object>> getPermission(HttpSession session):adminId="
						+ adminId);
		List<Menu> menus = menuService.getMenu(adminId);
		return menus;
	}

	// 修改密码
	@RequestMapping("/changepassword")
	public String changePW(HttpSession session) {
		session.removeAttribute("adminId");
		return "/admin/index";
	}

	// 验证
	@RequestMapping(value = "/checkuser", method = RequestMethod.GET)
	public @ResponseBody Map<String, String> check(
			@RequestParam("username") String username,
			@RequestParam("password") String password,
			@RequestParam("code") String code, HttpSession session) {

		Map<String, String> json = new HashMap<String, String>();
		String imgCode = (String) session.getAttribute("code") == null ? ""
				: (String) session.getAttribute("code");

		if (code.length() < 4 || !imgCode.equals(code.toUpperCase())) {
			json.put("result", "error");
			json.put("msg", "请输入正确的验证码");
			return json;
		}
		Admin admin = adminService.checkUser(username, password);
		if (admin != null) {
			session.setAttribute("admin_id", admin.getAdminId());
			json.put("result", "success");
			json.put("msg", "ok");
			// json.put("right",admin.getGroupName());
		} else {
			json.put("result", "error");
			json.put("msg", "用户名或密码错误");
		}
		return json;
	}

	// 网站信息设置
	@RequestMapping("/main_info")
	public String mainInfo() {

		return "/admin/web_install/main_info";
	}

	// 广告位设置
	@RequestMapping("/adv_list")
	public String advList() {

		return "/admin/web_install/adv_list";
	}

	// 轮播图
	@RequestMapping("/adv_photo")
	public String advPhoto(HttpServletRequest request) {
		List<PhotoTurn> photoList = photoTurnServiceImpl.getPhoto();
		request.setAttribute("photoList", photoList);
		return "/admin/web_install/photoTurn";
	}

	// 轮播图上传
	@ResponseBody
	@RequestMapping("/PhotofileUp")
	public String PhotofileUp(@RequestParam MultipartFile photoTurn,
			PhotoTurn photo, HttpServletRequest request,
			HttpServletResponse response) {

		System.out.println("进来了");
		System.out.println(JSON.toJSON(photo));

		String localPath = "D:\\ucoonFileUpload";// request.getSession().getServletContext().getRealPath("/upload");
		System.out.println(localPath);
		if (photoTurn.isEmpty()) {
			System.out.println("上传空文件，请重新上传");
		} else {
			String originalFilename = photoTurn.getOriginalFilename();/*
																	 * System.out
																	 * .println(
																	 * "文件原名: "
																	 * +
																	 * originalFilename
																	 * );
																	 * System.
																	 * out
																	 * .println
																	 * ("文件名称: "
																	 * +
																	 * photoTurn
																	 * .
																	 * getName()
																	 * );
																	 * System.
																	 * out
																	 * .println
																	 * ("文件长度: "
																	 * +
																	 * photoTurn
																	 * .
																	 * getSize()
																	 * );
																	 * System.
																	 * out
																	 * .println
																	 * ("文件类型: "
																	 * +
																	 * photoTurn
																	 * .
																	 * getContentType
																	 * ());
																	 */
			// 让图片名称唯一，产生随机数字
			double randNum = Math.random() * 100000;
			String randString = String.valueOf(randNum);
			String nameUp = randString + originalFilename;
			String aa = nameUp.replace(".", "/");
			String photoUrl = aa.replaceFirst("/", ".");
			System.out.println(photoUrl);
			photo.setPhotoUrl(photoUrl);
			int i = photoTurnServiceImpl.addPhoto(photo);
			try {
				if (i == 1)
					FileUtils.copyInputStreamToFile(photoTurn.getInputStream(),
							new File(localPath, nameUp));
				else {
					System.out.println("文件[" + originalFilename
							+ "]数据库插入失败,堆栈轨迹如下");
					return "1`文件上传失败，请重试！！";
				}
			} catch (IOException e) {
				System.out.println("文件[" + originalFilename + "]上传失败,堆栈轨迹如下");
				return "1`文件上传失败，请重试！！";
			}

		}

		return "/admin/web_install/photoTurn";
	}

	// 轮播图编辑 映射
	@RequestMapping("/edit_phototurn")
	public String editPhototurn(Integer photoId, HttpServletRequest request) {
		// System.out.println("photoId===="+photoId);
		PhotoTurn photo = photoTurnServiceImpl.getOnePhoto(photoId);
		request.setAttribute("photo", photo);
		return "/admin/web_install/edit_phototurn";
	}

	// 轮播图编辑 提交
	@RequestMapping("/update_phototurn")
	public String updatePhototurn(PhotoTurn photo) {
		System.out.println("更新的数据提交了吗？" + JSON.toJSON(photo));
		photoTurnServiceImpl.changePhoto(photo);
		return "/admin/web_install/photoTurn";
	}

	// 轮播图添加
	@RequestMapping("/add_phototurn")
	public String addPhototurn(Integer photoId, HttpServletRequest request) {
		System.out.println("photoId====" + photoId);
		PhotoTurn photo = photoTurnServiceImpl.getOnePhoto(photoId);
		request.setAttribute("photo", photo);
		return "/admin/web_install/add_phototurn";
	}

	// 广告编辑
	@RequestMapping("/edit_adv")
	public String editAdv() {
		return "/admin/web_install/edit_adv";
	}

	// 按状态查看订单
/*	@RequestMapping(value = "/adv_missionOrder/{status}/{pages}")
	public String advMissionOrder(@PathVariable("status") Integer status,
			@PathVariable("pages") Integer pages, HttpServletRequest request) {
		Mission mission = new Mission();
		// 默认是一页10条 需要可以通过 setter更改
		Integer pagesize = 2;
		Integer countAll = missionService.getAllCountByStatus(status);
		Integer pageAll = countAll / pagesize
				+ ((countAll % pagesize) == 0 ? 0 : 1);
		if (pageAll < pages) {
			pages = pageAll;
		}
		if (pages < 1 || pages == null) {
			pages = 1;
		}
		Integer from = (pages - 1) * pagesize;
		List<Mission> missions = missionService.selectByStatus(status, from,
				pagesize);
		request.setAttribute("missions", missions);
		request.setAttribute("pageAll", pageAll);
		request.setAttribute("page2", pages);
		request.setAttribute("missionStatus", status);
		return "/admin/web_install/adv_missionOrder";
	}

	// 编辑订单 修改状态
	@ResponseBody
	@RequestMapping("/edit_missionOrder")
	public String editMissionOrder(Integer missionId, Integer missionStatus,
			HttpServletRequest request) {
		Integer status = missionService.updateByStatusAndMissionId(missionId,
				missionStatus);
		if (status != 0)
			return "success";
		else
			return "false";
	}*/

	// 批量删除
//	@ResponseBody
//	@RequestMapping("/delall_mission")
//	// @RequestParam(value = "missionIds[]"),有没有这个影响着参数能否传进来，为什么不是自动注入
//	public String delallMission(
//			@RequestParam(value = "missionIds[]") Integer[] missionIds,
//			HttpServletRequest request) {
//		// 返回的是删除的数目
//		Integer status = missionService.delallMissionsByIds(missionIds);
//		;
//		if (status != 0)
//			return "success";
//		else
//			return "false";
//	}

	// 单个删除
/*	@ResponseBody
	@RequestMapping("/delone_mission")
	public String deloneMission(Integer missionId) {
		System.out.println("missionId=====" + missionId);
		// 返回的是删除的数目
		Integer status = missionService.deleteByPrimaryKey(missionId);
		System.out.println("删除状态状态" + status);
		if (status != 0)
			return "success";
		else
			return "false";
	}*/

	// 查看一订单的更多信息
	@RequestMapping("/adv_mission")
	public String advMission(Integer missionId, HttpServletRequest request) {
		Mission mission = missionService.selectByPrimaryKey(missionId);
		request.setAttribute("mission", mission);
		return "/admin/web_install/adv_mission";
	}

	// 消息管理映射
	@RequestMapping("/adv_message")
	public String advMessage() {
		return "/admin/web_install/adv_message";
	}

	// 管理员分组列表
	@RequestMapping("/admingroup_list")
	public String admingroupList(HttpServletRequest req) {
		List<AdminGroup> admingroup = null;
		admingroup = adminService.getAllAdminGroups();
		req.setAttribute("admingroup", admingroup);
		for (AdminGroup a : admingroup) {
			String[] s = adminService.findPemissionBygroupId(a.getGroupId());
			StringBuffer str = new StringBuffer();
			for (String string : s) {
				str.append(string + "   ");
			}
			a.setGroupRights(str.toString());
		}
		return "/admin/web_install/admingroup_list";
	}

	// 获取管理员分组列表
	@RequestMapping("/getadmingroup_list")
	@ResponseBody
	public List<AdminGroup> getadmingroup_list() {
		List<AdminGroup> admingroup = null;
		admingroup = adminService.getAllAdminGroups();
		return admingroup;
	}

	// 添加管理员分组界面
	@RequestMapping("/add_admingroup_list")
	public String add_admingroup_list() {

		return "/admin/web_install/add_admingroup";
	}

	// 添加管理员分组
	@RequestMapping("/getadd_admingroup_list")
	@ResponseBody
	public String getadd_admingroup_list(String ab, String groupName) {
		ab = ab.replace("power=", "");
		String[] abc = ab.split("&");
		AdminGroup admingroup = new AdminGroup();
		admingroup.setGroupName(groupName);
		if (adminService.insertAdminGroups(admingroup) == 0) {
			return "false";
		}
		admingroup = adminService.findgroupByName(groupName);
		int id = admingroup.getGroupId();
		for (String a : abc) {
			int i = Integer.parseInt(a);
			if (adminService.insertGroup_Pemission(id, i) == 0) {
				return "false";
			}
		}
		return "true";
	}

	// 删除管理员分组信息
	@RequestMapping("/deladmingroup_list")
	@ResponseBody
	public String deladmingroup_list(int id) {
		if (adminService.deleteAdminGroups(id) == 0) {
			return "false";
		}
		if (adminService.deleteGroup_Pemission(id) == 0) {
			return "false";
		}
		return "true";
	}

	// 批量删除管理员分组
	@RequestMapping("/delAlladmingroup_list")
	@ResponseBody
	public String delAlladmingroupList(int[] a) {
		for (int i : a) {
			if (adminService.deleteAdminGroups(i) == 0) {
				return "false";
			}
			if (adminService.deleteGroup_Pemission(i) == 0) {
				return "false";
			}
		}
		return "true";
	}

	// 编辑管理员分组界面
	@RequestMapping("/editadmingroup")
	public String editadmingroup(String groupname, HttpServletRequest req) {
		try {
			groupname = new String(groupname.getBytes("ISO-8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		req.setAttribute("groupname", groupname);
		return "/admin/web_install/edit_admingroup";
	}

	// 编辑管理员分组
	@RequestMapping("/geteditadmingroup")
	@ResponseBody
	public String geteditadmingroup(String ab, String groupname) {
		ab = ab.replace("power=", "");
		String[] abc = ab.split("&");
		AdminGroup admingroup = new AdminGroup();
		admingroup.setGroupName(groupname);
		admingroup = adminService.findgroupByName(groupname);
		int id = admingroup.getGroupId();
		adminService.deleteGroup_Pemission(id);
		for (String a : abc) {
			int i = Integer.parseInt(a);
			if (adminService.insertGroup_Pemission(id, i) == 0) {
				return "false";
			}
		}
		return "true";
	}

	// 管理员列表
	@RequestMapping("/admin_list")
	public String adminList() {

		return "/admin/web_install/admin_list";
	}

	// 获取管理员信息
	@RequestMapping("/getadmin_list")
	@ResponseBody
	public List getadminList() {
		List<Admin> admin = null;
		admin = adminService.getAllAdmins();
		return admin;
	}

	// 增加管理员信息界面
	@RequestMapping("/add_admin")
	public String add_admin() {

		return "/admin/web_install/add_admin";
	}

	// 添加管理员处理
	@RequestMapping("/getadd_admin")
	@ResponseBody
	public String getadd_admin(String username, String password,
			String groupName) {

		if (adminService.findbyName(username) != null) {
			return "has";
		}
		Admin admin = new Admin();
		AdminGroup admingroup = new AdminGroup();
		admin.setAdminName(username);
		admin.setAdminPsw(password);
		admingroup.setGroupName(groupName);
		admin.setAdminGroup(admingroup);
		int ret = adminService.insertAdmin(admin);
		if (ret != 0) {
			return "true";
		} else {
			return "false";
		}
	}

	// 编辑管理员
	@RequestMapping("/editadmin")
	public String editadmin(String username, HttpServletRequest req) {
		try {
			username = new String(username.getBytes("ISO-8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// String username=req.getParameter("username");
		req.setAttribute("username", username);
		return "/admin/web_install/edit_admin";
	}

	// 获取编辑管理员信息
	@ResponseBody
	@RequestMapping("/geteditadmin")
	public String geteditadmin(String username, String password,
			String groupName) {
		AdminGroup admingroup = new AdminGroup();
		admingroup.setGroupName(groupName);
		Admin admin = new Admin();
		admin.setAdminName(username);
		admin.setAdminPsw(password);
		admin.setAdminGroup(admingroup);
		int ret = adminService.updateByname(admin);
		if (ret != 0) {
			return "true";
		} else {
			return "false";
		}
	}

	// 删除管理员信息
	@RequestMapping("/deladmin_list")
	@ResponseBody
	public String deladminList(int id) {
		if (adminService.deleteAdmins(id) != 0) {
			return "true";
		} else {
			return "false";
		}
	}

	// 批量删除管理员信息
	@RequestMapping("/delAlladmin_list")
	@ResponseBody
	public String delAlladminList(int[] a) {
		for (int i : a) {
			if (adminService.deleteAdmins(i) == 0) {
				return "false";
			}
		}
		return "true";
	}

	// 获取所有权限
	@RequestMapping("/getAllPermission")
	@ResponseBody
	public List<Pemission> getAllPermission() {
		List<Pemission> permission = null;
		permission = adminService.getAllPemission();
		return permission;
	}

	// 获取会员信息界面
	@RequestMapping("/user_list")
	public String userList() {

		return "/admin/web_install/user_list";
	}

	// 获取会员信息
	@RequestMapping("/getuser_list")
	@ResponseBody
	public List<User> getuser_list() {
		List<User> list = null;
		list = adminService.getAllUser();
		return list;
	}

	// 反馈信息界面
	@RequestMapping("/feedback_list")
	public String feedback_list(HttpServletRequest req) {
		List<Feedback> feedback = adminService.getAllFeedback();
		req.setAttribute("feedback", feedback);
		return "/admin/web_install/feedback_list";
	}

	// 确定已阅读反馈
	@RequestMapping("/cFeedback")
	@ResponseBody
	public String cFeedback(int feedbackId) {
		Feedback feedback = new Feedback();
		feedback.setFeedbackId(feedbackId);
		//feedback.setStatus(1);
		int ret = adminService.updateFeedback(feedback);
		if (ret == 0) {
			return "false";
		}
		return "true";
	}

	// 举报信息界面
	@RequestMapping("/report_list")
	public String report_list(HttpServletRequest req) {
		List<Report> report = adminService.getAllReports();
		req.setAttribute("report", report);
		return "/admin/web_install/report_list";
	}

	// 删除用户
	@RequestMapping("/delUser")
	@ResponseBody
	public String delUser(int userId, int reportId) {
		User user = adminService.findUserById(userId);
		if (user == null) {
			return "miss";
		}

		int ret = adminService.delUserById(userId);
		if (ret == 0) {
			return "false";
		}
		Report rep = adminService.findReportById(reportId);
//		rep.setStatus("已处理");
		int report_ret = adminService.updateReport(rep);
		return "true";
	}

	// 删除任务
	@RequestMapping("/delMission")
	@ResponseBody
	public String delMission(int missionId, int reportId) {
		Mission mission = adminService.findMissionById(missionId);
		if (mission == null) {
			return "miss";
		}
		int ret = adminService.delMissionById(missionId);
		if (ret == 0) {
			return "false";
		}
		Report rep = adminService.findReportById(reportId);
//		rep.setStatus("已处理");
		int report_ret = adminService.updateReport(rep);
		return "true";
	}

	// 查看客服消息单发
	@RequestMapping("/adv_serviceMessage")
	public String advShortMessage(HttpServletRequest request) {
		List<ServiceMessage> servicemessages = serviceMessageService
				.getAllServiceMessage();
		System.out.println(JSON.toJSONString(servicemessages));
		request.setAttribute("servicemessages", servicemessages);
		return "/admin/web_install/adv_serviceMessage";
	}

	// 发送客服消息
	@ResponseBody
	@RequestMapping("/send_oneserviceMessage")
	public String sendOneshortMessage(String openid, String msgtype,
			String content, Integer serviceId, HttpServletRequest request)
			throws UnsupportedEncodingException {
		ServiceMessage serMsg = new ServiceMessage();
		serMsg.setOpenId(openid);
		serMsg.setType(msgtype);
		serMsg.setServiceMessageContent(content);
		serMsg.setServerId(serviceId);

		int a = WeixinUtil.customSend(openid, msgtype, "空小儿", content,
				"第二个参数干嘛");
		String statu = a == 0 ? "success" : "fail";
		// 发送成功再插入
		if (a == 0) {
			int state = serviceMessageService.sendOneServiceMessage(serMsg);
			System.out.println("插入状态===" + state);
		}
		System.out.println("发送状态===" + a);
		return statu;
	}

	// 编辑短信页面映射
	@RequestMapping("/edit_oneserviceMessageMapping")
	public String editOneshortMessageMapping(HttpServletRequest request) {
		String openid = request.getParameter("openid");
		List<ServerWeixin> servers = serverWeixinService.getServer();
		if (openid != null)
			request.setAttribute("openid", openid);
		request.setAttribute("servers", servers);
		return "/admin/web_install/edit_ServiceMessage";
	}

	// 群发页面映射
	@RequestMapping("/edit_allserviceMessageMapping")
	public String ediAllshortMessageMapping(HttpServletRequest request) {

		List<ServerWeixin> servers = serverWeixinService.getServer();
		request.setAttribute("servers", servers);
		return "/admin/web_install/edit_AllServiceMessage";
	}

	// 发送客服消息
	@ResponseBody
	@RequestMapping("/send_AllserviceMessage")
	public String sendAllshortMessage(String openid, String msgtype,
			String content, String serName, HttpServletRequest request)
			throws UnsupportedEncodingException {
		System.out.println("msgtype======" + msgtype + "serName========"
				+ serName);
		List<String> openIds = serviceMessageService.getAllOpenId();
		ExecutorService pool = Executors.newFixedThreadPool(10);
		for (int i = 0; i < openIds.size(); i++) {
			Thread send = new sendThread(openIds.get(i), msgtype, serName,
					content);
			pool.execute(send);
		}

		return "success";
	}

	// 查看单发短信
	@RequestMapping("/show_singserviceMessageMapping")
	public String showSingshortMessageMapping(HttpServletRequest request) {
		// 把所有单发的短信都查出来
		List<ServiceMessage> serviceMessages = serviceMessageService
				.getAllServiceMessage();
		System.out.println(JSON.toJSONString(serviceMessages));
		request.setAttribute("serviceMessages", serviceMessages);
		return "/admin/web_install/show_singserviceMessage";
	}

	// 删除一条客服
	@ResponseBody
	@RequestMapping("/delete_singserviceMessageMapping")
	public String showSingshortMessageMapping(Integer servicemessageId,
			HttpServletRequest request) {
		System.out.println("单独删除+==" + servicemessageId);
		int status = serviceMessageService
				.delOneServiceMessage(servicemessageId);
		System.out.println("单独删除状态==" + status);
		if (status == 1)
			return "success";
		else
			return "failed";
	}

	// 删除多条客服
	@ResponseBody
	@RequestMapping("/delete_moresingserviceMessageMapping")
	public String deleteMoresingserviceMessageMapping(
			@RequestParam(value = "servicemessageIds[]") Integer[] servicemessageIds,
			HttpServletRequest request) {
		System.out.println(servicemessageIds);
		int status = serviceMessageService
				.delmoreServiceMessage(servicemessageIds);
		System.out.println("删除状态==" + status);
		if (status != 0)
			return "success";
		else
			return "failed";
	}

	// 模板消息页面映射
	@RequestMapping("/adv_singMobile")
	public String advSingMobile(HttpServletRequest request) {
		List<Mobile> mobiles = mobileService.getMobiles();
		String[] con;
		for (int i = 0; i < mobiles.size(); i++) {
			String content = mobiles.get(i).getMobileContents();
			con = content.split(",");
			mobiles.get(i).setContents(con);
		}
		System.out.println(JSON.toJSONString(mobiles));

		request.setAttribute("mobiles", mobiles);
		return "/admin/web_install/adv_singMobile";
	}

	// 模板消息单发页面映射
	@RequestMapping("/edit_singMobile")
	public String editSingMobile(HttpServletRequest request) {
		List<Mobile> mobiles = mobileService.getMobiles();
		String[] con;
		for (int i = 0; i < mobiles.size(); i++) {
			String content = mobiles.get(i).getMobileContents();
			con = content.split(",");
			mobiles.get(i).setContents(con);
		}
		System.out.println(JSON.toJSONString(mobiles));
		request.setAttribute("mobiles", mobiles);
		return "/admin/web_install/edit_singMobile";
	}

	// 模板消息发送
	@ResponseBody
	@RequestMapping("/adv_getOneMobile")
	public Mobile getOneMobile(String mobileid) {
		Mobile mobile = mobileService.getOneMobile(mobileid);
		String mob = JSON.toJSONString(mobile);
		System.out.println(mob);
		return mobile;
	}

	// 模板消息发送
	@ResponseBody
	@RequestMapping("/send_mobile")
	public String advMobile(
			@RequestParam(value = "keywords[]") String[] keywords,
			@RequestParam(value = "colors[]") String[] colors, String openId,
			String mobileFirstParam, String mobileRemark, String mobileId) {
		System.out.println(mobileFirstParam);
		System.out.println(mobileRemark);
		System.out.println(mobileId);
		System.out.println("keyword" + keywords);
		System.out.println("openId" + openId);
		System.out.println("colors" + colors);
		Template tem = new Template();
		tem.setTemplateId(mobileId);
		tem.setTopColor("#00DD00");
		tem.setToUser(openId);
		// "ogF_wvqD9O66lxkZnPlxFcDNOgN4"
		tem.setUrl("http://wx.ucoon.cn/ucoon/"); // 到时候改为任务列表
		List<TemplateParam> paras = new ArrayList<TemplateParam>();
		paras.add(new TemplateParam("first", mobileFirstParam, "#FF3333"));
		
		for (int i = 1; i <= keywords.length; i++) {
			paras.add(new TemplateParam("keyword" + i, keywords[i - 1], "#"
					+ colors[i - 1]));
		}
		paras.add(new TemplateParam("remark", mobileRemark, "#0044BB"));

		System.out.println(JSON.toJSONString(paras));
		tem.setTemplateParamList(paras);

		boolean result = WeixinUtil.sendTemplateMsg(tem);
		System.out.println("模板消息结果：" + result);
		return "aa";
	}

	// 模板消息编辑
	@RequestMapping("/edit_mobile")
	public String editMobile(Mobile mobile) {
		return "/admin/web_install/add_mobile";
	}

	// 模板消息添加
	@ResponseBody
	@RequestMapping("/add_mobile")
	public String addMobile(Mobile mobile) {
		System.out.println("=====\n" + JSON.toJSONString(mobile));
		Integer status = mobileService.addMobile(mobile);
		if (status == 1)
			return "success";
		else
			return "false";
	}

	// 模板消息单独删除
	@ResponseBody
	@RequestMapping("/del_onemobile")
	public String delOnemobile(Integer MobileId) {
		System.out.println("=====川籍哪里elma++++++" + MobileId);
		Integer status = mobileService.delOneMobile(MobileId);
		if (status == 1)
			return "success";
		else
			return "false";
	}

	// 模板消息单独删除
	@ResponseBody
	@RequestMapping("/del_moremobile")
	public String delMoremobile(
			@RequestParam(value = "MobileIds[]") Integer[] MobileIds) {
		System.out.println("=====川籍哪里elma++++++" + MobileIds);
		Integer status = mobileService.delMoreMobile(MobileIds);
		System.out.println("status==" + status);
		if (status != 1)
			return "success";
		else
			return "false";
	}

	// 增加会员界面
	@RequestMapping("/add_User")
	public String add_User() {

		return "/admin/web_install/add_user";
	}

	// 增加会员
	@RequestMapping("/getadd_User")
	@ResponseBody
	public String getadd_User(@RequestParam MultipartFile head, User user,
			String bb, String rr) throws java.text.ParseException {
		// 存储路径
		String localpath = "D:\\ucoonfile";
		// 文件原名
		String originalfilename = head.getOriginalFilename();
		// 文件名称 getName()
		// 文件长度 getSize()
		// 文件类型 getContentType()
		String filename = String.valueOf((new Date()).getTime())
				+ originalfilename;
		String aa = filename.replace(".", "/");
		String photoUrl = aa.replaceFirst("/", ".");
		user.setHeadImgUrl(photoUrl);
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
		Date b = new Date();
		Date r = new Date();
		try {
			if (bb != null && bb != "") {
				b = sim.parse(bb);
			} else {
				b = null;
			}
			if (rr != null && rr != "") {
				r = sim.parse(rr);
			} else {
				r = null;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		user.setBirthday(b);
		user.setRegistTime(r);
		user.setCountry("中国");
		int ret = adminService.addUser(user);
		if (ret == 0) {
			return "false";
		}
		// 文件上传方法
		if (head != null) {
			try {
				FileUtils.copyInputStreamToFile(head.getInputStream(),
						new File(localpath, filename));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return "true";
	}

	// 修改会员信息界面
	@RequestMapping("/editUser")
	public String editUser(HttpServletRequest req, int userId) {
		User user = adminService.findUserById(userId);
		String bb = "";
		String rr = "";
		if (user.getBirthday() != null) {
			bb = new SimpleDateFormat("yyyy-MM-dd").format(user.getBirthday());
		}
		if (user.getRegistTime() != null) {
			rr = new SimpleDateFormat("yyyy-MM-dd")
					.format(user.getRegistTime());
		}
		req.setAttribute("user", user);
		req.setAttribute("bb", bb);
		req.setAttribute("rr", rr);
		return "/admin/web_install/edit_user";
	}

	// 修改会员信息处理
	@RequestMapping("/getedit_User")
	@ResponseBody
	public String geteditUser(@RequestParam MultipartFile head, User user,
			String bb, String rr) throws java.text.ParseException {
		// 存储路径
		String localpath = "D:\\ucoonfile";
		System.out.println("我进来了吗？？？？？");
		// 文件原名
		String originalfilename = head.getOriginalFilename();
		// 文件名称 getName()
		// 文件长度 getSize()
		// 文件类型 getContentType()
		String filename = String.valueOf((new Date()).getTime())
				+ originalfilename;
		String aa = filename.replace(".", "/");
		String photoUrl = aa.replaceFirst("/", ".");
		user.setHeadImgUrl(photoUrl);
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
		Date b = new Date();
		Date r = new Date();
		try {
			if (bb != null || bb != "") {
				b = sim.parse(bb);
			} else {
				b = null;
			}
			if (rr != null || rr != "") {
				r = sim.parse(rr);
			} else {
				r = null;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		user.setBirthday(b);
		user.setRegistTime(r);
		user.setCountry("中国");
		int ret = adminService.updateUserByPrimaryKey(user);
		if (ret == 0) {
			return "false";
		}
		// 文件上传方法
		if (head != null) {
			try {
				FileUtils.copyInputStreamToFile(head.getInputStream(),
						new File(localpath, filename));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return "true";
	}

	// 获取菜单信息
	@RequestMapping("/getMenu")
	public String getMenu(HttpServletRequest req) {
		AccessToken at = WeixinUtil.getAccessToken(WeixinUtil.appid,
				WeixinUtil.appsecret);
		JSONObject jsonObject = WeixinUtil.queryMenu(at.getToken());
		req.setAttribute("data", jsonObject);
		return "/admin/web_install/menu_list";
	}

	// 修改菜单信息
	public String updMenu() {

		return null;
	}
}
