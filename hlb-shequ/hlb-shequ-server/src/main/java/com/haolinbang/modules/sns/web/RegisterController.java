package com.haolinbang.modules.sns.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.haolinbang.common.config.Global;
import com.haolinbang.common.dto.WeJson;
import com.haolinbang.common.persistence.datasource.DataSourceType;
import com.haolinbang.common.persistence.datasource.DynamicDataSourceHolder;
import com.haolinbang.common.thridwy.haolong.bean.GetContractA;
import com.haolinbang.common.thridwy.haolong.bean.GetEmployeeInfo;
import com.haolinbang.common.thridwy.haolong.bean.GetRights;
import com.haolinbang.common.thridwy.haolong.bean.GetTimeStamp;
import com.haolinbang.common.thridwy.haolong.bean.RegisteVerify;
import com.haolinbang.common.thridwy.haolong.bean.bean.Contract;
import com.haolinbang.common.thridwy.haolong.bean.bean.Urlmap;
import com.haolinbang.common.thridwy.haolong.bean.bean.UserInfo;
import com.haolinbang.common.thridwy.haolong.utils.EncodesUtils;
import com.haolinbang.common.thridwy.haolong.utils.HaolongUtils;
import com.haolinbang.common.utils.AES;
import com.haolinbang.common.utils.CacheUtils;
import com.haolinbang.common.utils.Exceptions;
import com.haolinbang.common.utils.HttpUtils;
import com.haolinbang.common.utils.StringUtils;
import com.haolinbang.common.web.BaseController;
import com.haolinbang.common.web.ServletUtil;
import com.haolinbang.modules.sns.dao.AdDao;
import com.haolinbang.modules.sns.dto.UserRegDto;
import com.haolinbang.modules.sns.entity.Ad;
import com.haolinbang.modules.sns.entity.Member;
import com.haolinbang.modules.sns.entity.Menus;
import com.haolinbang.modules.sns.entity.Room;
import com.haolinbang.modules.sns.entity.WyBuilding;
import com.haolinbang.modules.sns.service.MemberService;
import com.haolinbang.modules.sns.service.MenusService;
import com.haolinbang.modules.sns.service.RegisterService;
import com.haolinbang.modules.sns.service.RoomService;
import com.haolinbang.modules.sns.service.UrlmapService;
import com.haolinbang.modules.sns.service.WyBuildingService;
import com.haolinbang.modules.sns.util.MemberUtils;
import com.haolinbang.modules.weixin.entity.WxAccount;
import com.haolinbang.modules.weixin.service.WxAccountService;

/**
 * 会员注册
 * 
 * @author Administrator
 * 
 */
@Controller
public class RegisterController extends BaseController {

	@Autowired
	private RegisterService registerService;

	@Autowired
	private RoomService roomService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private UrlmapService urlmapService;

	@Autowired
	private MenusService menusService;

	@Autowired
	private WyBuildingService wyBuildingService;

	@Autowired
	private AdDao adDao;
	
	@Autowired
	private WxAccountService wxAccountService;

	/**
	 * 用户注册 UserRegDto
	 * 
	 * @return
	 */
	@RequestMapping("/reg")
	public String reg(Boolean isAssociate, Model model, HttpServletRequest request) {
		String index = (String) request.getSession().getAttribute("index");
		if (StringUtils.isNotBlank(index) && index.equals("managerIndex")) {
			return "modules/sns/managerReg";
		}
		try {
			//
			// String openid = (String)
			// ServletUtil.getSession().getAttribute("openid");
			model.addAttribute("isAssociate", isAssociate);
			String source = (String) request.getSession().getAttribute("source");
			String wyid = (String) request.getSession().getAttribute("wyid");
			WyBuilding wyBuilding = new WyBuilding();
			wyBuilding.setSource(source);
			wyBuilding.setWyid(wyid);
			if (source != null) {
				List<WyBuilding> wyBuildings = wyBuildingService.findWyBuilding(wyBuilding);
				if (wyBuildings != null && wyBuildings.size() > 0) {
					model.addAttribute("WyBuilding", wyBuildings.get(0));
				}
			}

			return "modules/sns/reg";
		} catch (Exception e) {
			logger.error("注册出现错误");
			return Exceptions.deal(e);
		}
	}

	/*
	 * 管理员注册
	 */
	@RequestMapping("/managerReg")
	public String managerReg() {
		return "modules/sns/managerReg";
	}

	/**
	 * 用户注册信息保存 UserRegDto
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/toReg")
	public WeJson toReg(UserRegDto user, HttpServletRequest request, HttpServletResponse response) {
		/**
		 * 一般会员注册手机和身份证验证通过 先根据用户名和手机判断是否已经存在 将会员信息存数据库，默认为游客身份
		 * 调用wuye数据库，查询account中所有不同的source
		 * 遍历每一个source,同时存source的总个数和当前个数到session中，便于进度条异步读取
		 * 根据source查找用户的合同信息，如果找到，加入数据库并存进List<room>中
		 */
		user.setAdmintype(0);
		Member chemember = new Member();
		if (user.getIsAssociate().equals("true")) {// 关联会员
			chemember.setMobile(user.getMainMemberPhone());

			chemember = memberService.findList(chemember).size() > 0 ? memberService.findList(chemember).get(0) : null;
			if (chemember == null || chemember.getMemberType() != '1') {
				return WeJson.success("noexits");
			}
		}

		// chemember.setMemberName(user.getMemberName());
		/*
		 * chemember.setMobile(user.getMobile());
		 * DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS1);
		 * chemember =
		 * memberService.findList(chemember).size()>0?memberService.findList
		 * (chemember).get(0):null; if(chemember != null){ return
		 * WeJson.success("exits"); }
		 */

		Member member = registerService.saveMember(user);
		ServletUtil.getSession().setAttribute("memberid", member.getMemberId());
		if (member.getMemberType() == '2') {

			// ServletUtil.getSession().setAttribute("memberid",
			// member.getParentMemberId());
			Member mainMember = memberService.getMember(member.getParentMemberId());
			// ServletUtil.getSession().setAttribute("member", mainMember);
			// ServletUtil.getSession().setAttribute("connectmember", member);
			return WeJson.success(mainMember.getRoomId());
		}

		/*
		 * WyBuilding wyBuilding =
		 * wyBuildingService.getByBuildingId(user.getBuildingId());
		 * if(wyBuilding==null){ return WeJson.fail("fail"); }else{ String
		 * source = wyBuilding.getSource(); String wyid = wyBuilding.getWyid();
		 * request.getSession().setAttribute("source", source);
		 * request.getSession().setAttribute("wyid", wyid); }
		 * 
		 * List<Room> roomList = searchRoom(member.getMemberId(),
		 * user.getMemberName(), user.getMobile(), request);
		 * 
		 * if (roomList.size() > 0) { member.setMemberType('1');
		 * memberService.updateMember(member); return WeJson.success("success");
		 * } else { return WeJson.success("nomember"); }
		 */
		return WeJson.success("success");
	}

	/**
	 * 用户注册信息保存 UserRegDto
	 * 
	 * @return
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping("/toManagerReg")
	public WeJson toManagerReg(UserRegDto user, HttpServletRequest request, HttpServletResponse response) throws IOException {
		// Member chemember = new Member();
		user.setAdmintype(0);
		user.setRegType("manager");
		/*GetEmployeeInfo getEmployeeInfo = new GetEmployeeInfo();
		Urlmap urlmap = MemberUtils.getUrlmap((String)request.getSession().getAttribute("app_source"));

		getEmployeeInfo.setSecretkey(urlmap.getSecretkey());
		getEmployeeInfo.setSoapActionString(urlmap.getSoapactionstring());
		getEmployeeInfo.setUrlString(urlmap.getUrlstring());
		getEmployeeInfo.setGroupID((String)request.getSession().getAttribute("app_groupid"));
		List<UserInfo> userInfo = HaolongUtils.getEmployeeInfo(getEmployeeInfo);
		
		for(UserInfo ui : userInfo){
			if(ui.getStaffName().equals(user.getMemberName())){
				user.setYgID(ui.getStaffId().toString());
			}
		}*/
		
		Member member = registerService.saveMember(user);
		/*
		 * member.setAdmintype(1); memberService.updateMember(member);
		 */

		ServletUtil.getSession().setAttribute("memberid", member.getMemberId());

		return WeJson.success("success");
	}

	/**
	 * 添加楼盘
	 */
	@RequestMapping("/addRoom")
	public String addRoom(Model model) {
		Member member = MemberUtils.getCurrentMember();
		List<Room> rooms = roomService.getRoomListByMemberID(member.getMemberId());
		// HashSet<String> sources = new HashSet<String>();
		HashSet<WyBuilding> buildings = new HashSet<WyBuilding>();
		if (rooms != null && rooms.size() > 0) {
			for (Room room : rooms) {
				WyBuilding wyBuilding = new WyBuilding();
				wyBuilding.setSource(room.getSource());
				wyBuilding.setWyid(room.getWYID().toString());
				List<WyBuilding> wyBuildings = wyBuildingService.findWyBuilding(wyBuilding);
				wyBuilding = (wyBuildings == null || wyBuildings.size() <= 0) ? null : wyBuildings.get(0);
				if (wyBuilding != null) {
					Boolean flag = true;
					for (WyBuilding wb : buildings) {
						if (wb.getBuildingId().equals(wyBuilding.getBuildingId())) {
							flag = false;
						}
					}
					if (flag) {
						buildings.add(wyBuilding);
					}
				}

			}
		}
		model.addAttribute("buildings", buildings);
		return "modules/sns/addRoom";
	}

	/**
	 * 添加source
	 */
	@RequestMapping("/selectSource")
	public String selectSource(Model model) {
		DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS2);
		List<Urlmap> urlmapList = new ArrayList<Urlmap>();
		/*
		 * Urlmap urlmaptemp = new Urlmap(); urlmaptemp.setStatus("1");
		 * 
		 * urlmapList = urlmapService.getUrlmap1(urlmaptemp);
		 */
		DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS1);
		model.addAttribute("urlmapList", urlmapList);
		return "modules/sns/selectSource";
	}

	/**
	 * 楼盘选择
	 * 
	 * @return
	 */
	@RequestMapping("/selectRoom")
	public String selectRoom(Integer roomId, Model model, HttpServletRequest request) {
		try {
			Member member = MemberUtils.getCurrentMember();
			DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS1);
			List<Room> roomList = null;
			if (member.getMemberType() == '2') {
				roomList = roomService.getRoomListByMemberID(member.getParentMemberId());
			} else {
				roomList = roomService.getRoomListByMemberID(member.getMemberId());
			}

			if (roomId == null && roomList != null) {
				roomId = roomList.get(0).getRoomId();
			}

			model.addAttribute("roomList", roomList);
			// model.addAttribute("member", member);
			model.addAttribute("roomId", roomId);

			ServletUtil.getSession().setAttribute("roomId", roomId);
			member.setRoomId(StringUtils.obj2String(roomId));
			if (member.getMemberType() == '0') {
				member.setMemberType('1');
			}
			memberService.updateMember(member);

			return "modules/sns/selectRoom";
		} catch (Exception e) {
			e.printStackTrace();
			return Exceptions.deal(e);
		}
	}

	/**
	 * 首页
	 * 
	 * @return
	 */
	@RequestMapping("/index")
	public String index(String roomId, Model model, HttpServletRequest request) {
		Member member = null;
		Member connectmember = null;

		String source = request.getParameter("app_source");
		String groupid = request.getParameter("app_groupid");
		String jumpType = request.getParameter("jumpType");
		
		if(StringUtils.isNotBlank(source)){
			request.getSession().setAttribute("app_source", source);
		}else{
			source = (String) request.getSession().getAttribute("app_source");
			if(StringUtils.isBlank(source)){
				source = Global.getConfig("app_source");
				request.getSession().setAttribute("app_source", source);
			}
		}
		if(StringUtils.isNotBlank(groupid)){
			request.getSession().setAttribute("app_groupid", groupid);
		}else{
			groupid = request.getSession().getAttribute("app_groupid") + "";
			if(StringUtils.isBlank(groupid)){
				groupid = Global.getConfig("app_groupid");
			}
			request.getSession().setAttribute("app_groupid", groupid); 
		}
		
		
		request.getSession().setAttribute("index", "index");
		request.getSession().setAttribute("dirUrl", "");
		request.getSession().setAttribute("dirType", "");
		

		try {
			//member = memberService.getMember(37);
			member = MemberUtils.getCurrentMember();
			if (member.getMemberType() == '2') {
				connectmember = MemberUtils.getCurrentMember();
				member = memberService.getMember(connectmember.getParentMemberId());

				if (CacheUtils.get(member.getMemberId().toString(), "member") != null) {
					CacheUtils.remove(member.getMemberId().toString(), "member");
				}
				CacheUtils.put(member.getMemberId().toString(), "member", member);
				request.getSession().setAttribute("memberid", member.getMemberId());

				request.getSession().setAttribute("connectmemberid", connectmember.getMemberId());
			}
		} catch (Exception e) {
			logger.error("无用户缓存:{}", e);
			String ex = Exceptions.deal(e);
			return ex;
		}
		DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS1);
		if (roomId != null) {
			member.setRoomId(roomId);
			memberService.updateMember(member);
		} else {
			if (member.getMemberType() == '1' && StringUtils.isEmpty(member.getRoomId())) {
				List<Room> rooms = roomService.getRoomListByMemberID(member.getMemberId());
				if (rooms != null && rooms.size() > 0) {
					member.setRoomId(rooms.get(0).getRoomId().toString());
					memberService.updateMember(member);
				}
			}
		}
		Room room = roomService.getRoom(member.getRoomId());
		if (room != null) {
			request.getSession().setAttribute("usersource", room.getSource());
			request.getSession().setAttribute("usergroupid", room.getGroupId());
			model.addAttribute("title", room.getWYName());
		}else{
			WxAccount wxAccount = new WxAccount();
			wxAccount.setSource(source);
			wxAccount.setGroupId(StringUtils.toInteger(groupid));
			List<WxAccount> wxAccounts = wxAccountService.getByWxAccount(wxAccount);
			model.addAttribute("title", wxAccounts.get(0).getWxname());
		}
		
		//-------------------此处添加是否跳转方法-------------------------
			if(StringUtils.isNotBlank(jumpType)){
				if(jumpType.equals("houseRent")){
					return "forward:/houseRent/list";
				}
			}
		//---------------------------------------------------------
		
		List<Menus> menusList = menusService.getMenusList(0);

		String unStr = (String) request.getSession().getAttribute("screenCodes");

		if (StringUtils.isBlank(unStr)) {
			String secretkey = Global.getConfig("okbang.secretkey");
			String soapactionstring = Global.getConfig("okbang.soapactionstring");
			String urlstring = Global.getConfig("okbang.urlstring");

			GetTimeStamp bean = new GetTimeStamp();
			bean.setSecretkey(secretkey);
			bean.setSoapActionString(soapactionstring);
			bean.setUrlString(urlstring);

			try {
				String timestampStr = HaolongUtils.getTimeStamp(bean);
				String app_source = (String) request.getSession().getAttribute("app_source");

				GetRights getRights = new GetRights();
				getRights.setSecretkey(secretkey);
				getRights.setSoapActionString(soapactionstring);
				getRights.setUrlString(urlstring);
				getRights.setSource(AES.getAesPwd(app_source, secretkey));
				getRights.setTimeStampT(AES.getAesPwd(timestampStr, secretkey));
				String str = HaolongUtils.getRights(getRights);
				unStr = AES.Decrypt(str, secretkey);

				if (StringUtils.isNotBlank(unStr)) {
					unStr = unStr.split("\\^")[0];
				}
				request.getSession().setAttribute("screenCodes", unStr);
			} catch (Exception e) {
				logger.error("获取时间戳:{}", e);
			}
		}

		if (StringUtils.isNotBlank(unStr)) {
			for (int i = 0; i < menusList.size();) {
				int screenCode = StringUtils.toInteger(menusList.get(i).getScreenCode());
				if (screenCode == 0) {
					i++;
				} else if (screenCode <= unStr.length()) {
					String flag = unStr.substring(screenCode - 1, screenCode);
					if (flag.equals("Y")) {
						i++;
					} else {
						menusList.remove(i);
					}
				} else {
					menusList.remove(i);
				}
			}
		}

		model.addAttribute("menuList", menusList);
		model.addAttribute("room", room);
		ServletUtil.getSession().setAttribute("roomId", member.getRoomId());

		DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS2);
		Ad ad = new Ad();
		ad.setSource(null);
		ad.setWYID(null);
		ad.setStatus("1"); // 有效状态
		List<Ad> ads = adDao.findList(ad);
		CacheUtils.put("ads", ads);
		DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS1);

		model.addAttribute("ads", ads);
		model.addAttribute("memberType", member.getMemberType());
		if (member.getMemberType() == '0') {
			model.addAttribute("membertype", 0);
		} else {
			model.addAttribute("membertype", 1);
		}
		model.addAttribute("admintype", member.getAdmintype());

		return "modules/sns/index";
	}

	/**
	 * 首页
	 * 
	 * @return
	 */
	@RequestMapping("/managerIndex")
	public String managerIndex(String roomId, Model model, HttpServletRequest request) {
		Member member = null;
		request.getSession().setAttribute("index", "managerIndex");
		
		String source = request.getParameter("app_source");
		String groupid = request.getParameter("app_groupid");
		
		if(StringUtils.isNotBlank(source)){
			request.getSession().setAttribute("app_source", source);
		}else{
			source = (String) request.getSession().getAttribute("app_source");
			if(StringUtils.isBlank(source)){
				source = Global.getConfig("app_source");
				request.getSession().setAttribute("app_source", source);
			}
		}
		if(StringUtils.isNotBlank(groupid)){
			request.getSession().setAttribute("app_groupid", groupid);
		}else{
			groupid = (String) request.getSession().getAttribute("app_groupid");
			if(StringUtils.isBlank(groupid)){
				groupid = Global.getConfig("app_groupid");
				request.getSession().setAttribute("app_groupid", groupid);
			}
		}
		
		try {
			member = MemberUtils.getCurrentMember();
			if (member.getAdmintype()==null || member.getAdmintype() != 1) {
				return "redirect:/selectSource";
			}
		} catch (Exception e) {
			logger.error("无管理员缓存");
			String ex = Exceptions.deal(e);
			return ex;
		}
		DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS1);

		List<Menus> menusList = menusService.getMenusList(0);
		model.addAttribute("menuList", menusList);

		DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS2);
		Ad ad = new Ad();
		ad.setSource(null);
		ad.setWYID(null);
		ad.setStatus("1"); // 有效状态
		List<Ad> ads = adDao.findList(ad);
		CacheUtils.put("ads", ads);
		DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS1);

		model.addAttribute("ads", ads);
		model.addAttribute("memberType", member.getMemberType());
		if (member.getMemberType() == '0') {
			model.addAttribute("membertype", 0);
		} else if (member.getMemberType() == '1') {
			model.addAttribute("membertype", 1);
		}

		return "modules/sns/index";
	}

	/**
	 * 发送手机验证码
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/sendsms")
	public WeJson sendsms(String mobile, String type) {
		try {
			if (StringUtils.isBlank(mobile)) {
				return WeJson.fail("手机号码不能为空");
			}

			boolean b = registerService.sendsms(mobile, type);
			if (b) {
				return WeJson.success("发送成功");
			}
			return WeJson.fail("发送失败");
		} catch (Exception e) {
			logger.error("程序出现错误，{}", e);
			return WeJson.fail("程序出现错误");
		}
	}

	/**
	 * 验证手机验证码
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkSmsCode")
	public WeJson checkSmsCode(String smscode) {
		try {
			if (StringUtils.isBlank(smscode)) {
				return WeJson.fail("手机验证码不能为空");
			}

			boolean b = registerService.checkSmsCode(smscode);
			if (b) {
				return WeJson.success("验证成功");
			}
			return WeJson.fail("验证失败，请重试");
		} catch (Exception e) {
			logger.error("程序出现错误，{}", e);
			return WeJson.fail("程序出现错误");
		}
	}

	/**
	 * 验证图形验证码验证码
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkPiccode")
	public WeJson checkPiccode(String piccode) {
		try {
			if (StringUtils.isBlank(piccode)) {
				return WeJson.fail("图形验证码不能为空");
			}

			boolean b = registerService.checkPiccode(piccode);
			if (b) {
				return WeJson.success("验证成功");
			}
			return WeJson.fail("验证失败，请重试");
		} catch (Exception e) {
			logger.error("程序出现错误，{}", e);
			return WeJson.fail("程序出现错误");
		}
	}

	/**
	 * 验证主会员是否存在
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkParentMember")
	public WeJson checkParentMember(String mobile) {
		DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS1);
		try {
			Member member = new Member();
			member.setMobile(mobile);
			List<Member> memberList = memberService.findList(member);
			if (memberList != null && memberList.size() > 0) {
				return WeJson.success(memberList.get(0));
			} else {
				return WeJson.fail("查不到此主会员");
			}

		} catch (Exception e) {
			logger.error("程序出现错误，{}", e);
			return WeJson.fail("程序出现错误");
		}
	}

	/**
	 * 商品选择
	 * 
	 * @throws Exception
	 */
	@RequestMapping("/chooseGoods")
	public String chooseGoods(Model model) throws Exception {
		Member member = MemberUtils.getCurrentMember();
		if (StringUtils.isBlank(member.getRoomId())) {
			if (member.getMemberType() == 1) {
				List<Room> rooms = roomService.getRoomListByMemberID(member.getMemberId());
				if (rooms != null && rooms.size() > 0) {
					member.setRoomId(rooms.get(0).getRoomId().toString());
					memberService.updateMember(member);
				}
			}
		}
		String roomId = member.getRoomId();
		// String roomId = (String)
		// ServletUtil.getSession().getAttribute("roomId");
		Room room = new Room();
		DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS1);

		// roomId="240";
		room = roomService.getRoom(roomId);

		DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS2);
		model.addAttribute("WYID", room.getWYID());
		model.addAttribute("HTID", room.getHTID());
		model.addAttribute("JFYF", roomService.getLastMonth(room.getWYID().toString(), room.getSource()));
		model.addAttribute("SOURCE", room.getSource());
		DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS1);
		return "modules/sns/chooseGoods";
	}

	/**
	 * 获取缴费接口参数
	 * 
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/getSign")
	public WeJson getSign(HttpServletRequest request) throws Exception {
		String feetype = StringUtils.obj2String(request.getParameter("feetype"));
		String WYID = StringUtils.obj2String(request.getParameter("WYID"));
		String HTID = StringUtils.obj2String(request.getParameter("HTID"));
		String JFYF = StringUtils.obj2String(request.getParameter("JFYF"));
		String SOURCE = StringUtils.obj2String(request.getParameter("SOURCE"));
		String[] arr_data = { feetype, WYID, HTID, JFYF };
		String sign = EncodesUtils.encrypt2(arr_data, SOURCE);
		HttpSession session = request.getSession();
		session.setAttribute("SIGN", sign);

		return WeJson.success(sign);
	}

	@RequestMapping("/photo")
	public String photo(Model model) throws Exception {

		return "modules/sns/photo";
	}

	@ResponseBody
	@RequestMapping("/getLocation")
	public WeJson getLocation(HttpServletRequest request) {
		String latitude = StringUtils.obj2String(request.getParameter("latitude"));
		String longitude = StringUtils.obj2String(request.getParameter("longitude"));
		String speed = StringUtils.obj2String(request.getParameter("speed"));
		String accuracy = StringUtils.obj2String(request.getParameter("accuracy"));
		System.out.println("latitude:" + latitude + " longitude:" + longitude + " speed:" + speed + " accuracy:" + accuracy);

		return WeJson.success("success");
	}

	// 根据用户id, 用户名和手机查找房屋信息并更新信息
	/*
	 * public List<Room> searchRoom(String memberid, String name, String
	 * mobile){
	 * DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS2);
	 * List<String> sourceList = accountService.getSources(); List<Room>
	 * roomList = new ArrayList<Room>();
	 * 
	 * if(sourceList != null){ ServletUtil.getSession().setAttribute("total",
	 * sourceList.size()); for(int i=0; i<sourceList.size(); i++){
	 * ServletUtil.getSession().setAttribute("current", i); String source =
	 * sourceList.get(i); Urlmap urlmap = new Urlmap();
	 * urlmap.setUrlkey(source); urlmap.setStatus("1");
	 * DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS2); urlmap
	 * = urlmapService.getUrlmap(urlmap); if(urlmap == null){ return null; }
	 * 
	 * GetContractA getContractA = new GetContractA();
	 * getContractA.setCliName(name); getContractA.setMobile(mobile);
	 * getContractA.setWYID("");
	 * getContractA.setUrlString(urlmap.getUrlstring());
	 * getContractA.setSoapActionString(urlmap.getSoapactionstring());
	 * getContractA.setSecretkey(urlmap.getSecretkey());
	 * 
	 * //对调用合同接口进行异常处理 List<Map<String, String>> contractInfoList = null; try {
	 * contractInfoList = mHaolongService.getContractA(getContractA);
	 * if(contractInfoList!=null &&
	 * !contractInfoList.toString().toUpperCase().contains("ERROR")){
	 * DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS1);
	 * for(Map<String,String> contract:contractInfoList){ Room room = new
	 * Room(); room.setSource(source);
	 * room.setWYID(Integer.valueOf(contract.get("WYID")));
	 * room.setHTID(Integer.valueOf(contract.get("HTID")));
	 * room.setKHID(Integer.valueOf(contract.get("KHID")));
	 * room.setWYNo(contract.get("物业编号")); room.setWYName(contract.get("物业名称"));
	 * room.setLYID(Integer.valueOf(contract.get("LYID")));
	 * room.setLYNo(contract.get("大楼编号")); room.setLYName(contract.get("大楼名称"));
	 * room.setRoomNo(contract.get("内部房号"));
	 * room.setTerminationStatus(Boolean.valueOf(contract.get("止约状态")));
	 * SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	 * room.setTerminationDate
	 * (StringUtils.isBlank(contract.get("止约日期"))?null:sdf
	 * .parse(contract.get("止约日期"))); room.setMemberId(memberid);
	 * room.setCreatetime(new Date()); room.setUpdatetime(new Date());
	 * 
	 * roomService.insert(room); roomList.add(room); } if(roomList!=null &&
	 * roomList.size()>0){ roomList =
	 * roomService.getRoomListByMemberID(memberid); } } } catch (Exception e) {
	 * logger.error("getContractA() : " + e.getMessage().toString());
	 * logger.error("ContractInfo : " + contractInfoList.toString());
	 * e.printStackTrace(); } } } return roomList; }
	 */

	// 根据用户id, 用户名和手机查找房屋信息并更新信息
	/*
	 * 新增思路：获取session
	 */
	@RequestMapping("/searchRoom")
	public List<Room> searchRoom(Integer memberid, String name, String mobile, HttpServletRequest request) {
		// String source = (String) request.getSession().getAttribute("source");
		String source = (String) request.getSession().getAttribute("source");
		String wyid = (String) request.getSession().getAttribute("wyid");

		DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS2);
		List<Urlmap> urlmapList = new ArrayList<Urlmap>();
		Urlmap urlmaptemp = new Urlmap();
		urlmaptemp.setStatus("1");
		if (StringUtils.isNotBlank(source)) {
			urlmaptemp.setUrlkey(source);
		}
		urlmapList = urlmapService.getUrlmap1(urlmaptemp);
		List<Room> roomList = new ArrayList<Room>();

		if (urlmapList != null) {
			ServletUtil.getSession().setAttribute("total", urlmapList.size());
			/*
			 * HSSFWorkbook wb = new HSSFWorkbook(); //创建HSSFSheet对象 HSSFSheet
			 * sheet = wb.createSheet("sheet0"); //创建HSSFRow对象 HSSFRow row =
			 * sheet.createRow(0); //创建HSSFCell对象 HSSFCell
			 * cell=row.createCell(0); //设置单元格的值
			 * 
			 * row.createCell(0).setCellValue("序号");
			 * row.createCell(1).setCellValue("source");
			 * row.createCell(2).setCellValue("开始时间");
			 * row.createCell(3).setCellValue("结束时间");
			 * row.createCell(4).setCellValue("耗时（秒）");
			 */

			for (int i = 0; i < urlmapList.size(); i++) {
				/* HSSFRow rowi = sheet.createRow(i+1); */
				Date beginTime = new Date();
				Urlmap urlmap = urlmapList.get(i);

				if (urlmap == null) {
					return null;
				}

				if (!HttpUtils.isAvailableUrl(urlmap.getUrlstring())) {
					continue;
				}

				GetContractA getContractA = new GetContractA();
				getContractA.setCliName(name);
				getContractA.setMobile(mobile);
				getContractA.setWYID("");
				getContractA.setUrlString(urlmap.getUrlstring());
				getContractA.setSoapActionString(urlmap.getSoapactionstring());
				getContractA.setSecretkey(urlmap.getSecretkey());

				// 对调用合同接口进行异常处理
				List<Contract> contractInfoList = null;
				try {
					logger.info(getContractA.getUrlString());
					contractInfoList = HaolongUtils.getContractA(getContractA);
					if (contractInfoList != null && !contractInfoList.toString().toUpperCase().contains("ERROR")) {
						DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS1);
						for (Contract contract : contractInfoList) {
							Room room = new Room();
							room.setSource(urlmap.getUrlkey());
							room.setWYID(contract.getWYID());
							room.setGroupId(contract.getGroupID());
							room.setHTID(contract.getHTID());
							room.setKHID(contract.getKHID());
							room.setWYNo(contract.getWYNo());
							room.setWYName(contract.getWYNo());
							room.setLYID(contract.getLYID());
							room.setLYNo(contract.getLYNo());
							room.setLYName(contract.getLYName());
							room.setRoomNo(contract.getRoomNo());
							room.setTerminationStatus(contract.getTerminationStatus());
							room.setTerminationDate(contract.getTerminationDate());
							room.setMemberId(memberid);
							room.setCreatetime(new Date());
							room.setUpdatetime(new Date());

							roomService.insert(room);
							roomList.add(room);
						}
						if (roomList != null && roomList.size() > 0) {
							roomList = roomService.getRoomListByMemberID(memberid);
						}
					}
				} catch (Exception e) {
					logger.error("getContractA() : {}", e);
					logger.error("ContractInfo : {}", contractInfoList);
					e.printStackTrace();
				}

				/*
				 * Date endTime = new Date(); String slash = File.separator;
				 * File file = new
				 * File(request.getSession().getServletContext().
				 * getRealPath(slash)+"selectRoomTime"); if(!file.exists()){
				 * file.mkdir(); } File file1 = new
				 * File(request.getSession().getServletContext
				 * ().getRealPath(slash
				 * )+"selectRoomTime"+slash+DateUtils.getDate()+".txt");
				 * if(!file1.exists()){ try { file1.createNewFile(); } catch
				 * (IOException e) { e.printStackTrace(); } } FileWriter
				 * fileWriter; try { fileWriter = new FileWriter(file1, true);
				 * String s1 = "第"+i+"次: "+urlmap.getUrlkey(); String s2 =
				 * urlmap
				 * .getUrlkey()+" beginTime:"+DateUtils.formatDateTime(beginTime
				 * ); String s3 =
				 * urlmap.getUrlkey()+" endTime:"+DateUtils.formatDateTime
				 * (endTime); String s4 =
				 * urlmap.getUrlkey()+" total:"+(endTime.getTime() -
				 * beginTime.getTime()); String s = new String(s1+"\r\n" +
				 * s2+"\r\n" + s3+"\r\n" + s4+"\r\n\r\n"); fileWriter.write(s);
				 * fileWriter.close(); // 关闭数据流 } catch (IOException e) { //
				 * TODO Auto-generated catch block e.printStackTrace(); }
				 */
			}
		}
		return roomList;
	}

	@RequestMapping("/searchRoomWithSource")
	public List<Room> searchRoomWithSource(Integer memberid, String name, String mobile, String source, String wyid, HttpServletRequest request) {
		DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS2);
		Urlmap urlmap = new Urlmap();
		urlmap.setStatus("1");
		urlmap.setUrlkey(source);
		urlmap = urlmapService.getUrlmap(urlmap);
		List<Room> roomList = new ArrayList<Room>();

		if (urlmap == null) {
			return null;
		}

		GetContractA getContractA = new GetContractA();
		getContractA.setCliName(name);
		getContractA.setMobile(mobile);
		getContractA.setWYID("");
		getContractA.setUrlString(urlmap.getUrlstring());
		getContractA.setSoapActionString(urlmap.getSoapactionstring());
		getContractA.setSecretkey(urlmap.getSecretkey());

		// 对调用合同接口进行异常处理
		List<Contract> contractInfoList = null;
		try {
			logger.info(getContractA.getUrlString());
			contractInfoList = HaolongUtils.getContractA(getContractA);
			if (contractInfoList != null && !contractInfoList.toString().toUpperCase().contains("ERROR")) {
				DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS1);
				for (Contract contract : contractInfoList) {
					Room room = new Room();
					room.setSource(urlmap.getUrlkey());
					room.setWYID(contract.getWYID());
					room.setGroupId(contract.getGroupID());
					room.setHTID(contract.getHTID());
					room.setKHID(contract.getKHID());
					room.setWYNo(contract.getWYNo());
					room.setWYName(contract.getWYMC());
					room.setLYID(contract.getLYID());
					room.setLYNo(contract.getLYNo());
					room.setLYName(contract.getLYName());
					room.setRoomNo(contract.getRoomNo());
					room.setTerminationStatus(contract.getTerminationStatus());
					room.setTerminationDate(contract.getTerminationDate());
					room.setMemberId(memberid);
					room.setCreatetime(new Date());
					room.setUpdatetime(new Date());

					roomService.insert(room);
					roomList.add(room);
				}
				if (roomList != null && roomList.size() > 0) {
					roomList = roomService.getRoomListByMemberID(memberid);
				}
			}
		} catch (Exception e) {
			logger.error("getContractA() : {}", e);
			logger.error("ContractInfo : {}", contractInfoList);
			e.printStackTrace();
		}

		return roomList;
	}

	@ResponseBody
	@RequestMapping("/searchRoomNew")
	public WeJson searchRoomNew(HttpServletRequest request) {
		Member member = MemberUtils.getCurrentMember();

		String[] buildingIds = request.getParameterValues("buildingId");
		List<String> sources = new ArrayList<String>();
		List<Urlmap> urlmaps = new ArrayList<Urlmap>();
		for (String ss : buildingIds) {
			WyBuilding wyBuilding = wyBuildingService.getByBuildingId(ss);
			if (wyBuilding != null) {
				sources.add(wyBuilding.getSource());
			}
		}
		HashSet<String> hsources = new HashSet<String>(sources);
		if (hsources != null && hsources.size() > 0) {
			DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS2);
			for (String ss : hsources) {
				Urlmap urlmap = new Urlmap();
				urlmap.setStatus("1");
				urlmap.setUrlkey(ss);
				urlmap = urlmapService.getUrlmap(urlmap);
				urlmaps.add(urlmap);
			}
		}

		List<Room> roomList = new ArrayList<Room>();

		for (Urlmap urlmap : urlmaps) {
			GetContractA getContractA = new GetContractA();
			getContractA.setCliName(member.getMemberName());
			getContractA.setMobile(member.getMobile());
			getContractA.setWYID("");
			getContractA.setUrlString(urlmap.getUrlstring());
			getContractA.setSoapActionString(urlmap.getSoapactionstring());
			getContractA.setSecretkey(urlmap.getSecretkey());

			// 对调用合同接口进行异常处理
			List<Contract> contractInfoList = null;
			try {
				logger.info(getContractA.getUrlString());
				contractInfoList = HaolongUtils.getContractA(getContractA);
				if (contractInfoList != null && !contractInfoList.toString().toUpperCase().contains("ERROR")) {
					DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS1);
					for (Contract contract : contractInfoList) {
						Room room = new Room();
						room.setSource(urlmap.getUrlkey());
						room.setWYID(Integer.valueOf(contract.getWYID()));
						room.setGroupId(Integer.valueOf(contract.getGroupID()));
						room.setHTID(Integer.valueOf(contract.getHTID()));
						room.setKHID(Integer.valueOf(contract.getKHID()));
						room.setWYNo(contract.getWYNo());
						room.setWYName(contract.getWYMC());
						room.setLYID(Integer.valueOf(contract.getLYID()));
						room.setLYNo(contract.getLYNo());
						room.setLYName(contract.getLYName());
						room.setRoomNo(contract.getRoomNo());
						room.setTerminationStatus(contract.getTerminationStatus());
						room.setTerminationDate(contract.getTerminationDate());
						room.setMemberId(member.getMemberId());
						room.setCreatetime(new Date());
						room.setUpdatetime(new Date());

						if (member.getMemberType().equals('1')) {
							List<Room> temps = roomService.getByMemberId_Source_WyId(room);
							if (temps != null && temps.size() > 0) {
								room.setRoomId(temps.get(0).getRoomId());
								roomService.update(room);
								roomList.add(temps.get(0));
							}else {
								roomService.insert(room);
								roomList.add(room);
							}
						} else{
							roomService.insert(room);
							roomList.add(room);
						}
					}

				}
			} catch (Exception e) {
				logger.error("getContractA() : {}", e);
				logger.error("ContractInfo : {}", contractInfoList);
				e.printStackTrace();
			}
		}
		if (roomList != null && roomList.size() > 0) {
			roomList = roomService.getRoomListByMemberID(member.getMemberId());
		}

		return WeJson.success("success");
	}

	@ResponseBody
	@RequestMapping("/updateRoomInfo")
	public WeJson updateRoomInfo(HttpServletRequest request, HttpServletResponse response) {
		Member member = MemberUtils.getCurrentMember();
		List<Room> roomList = null;
		try {
			roomList = searchRoom(member.getMemberId(), member.getMemberName(), member.getMobile(), request);
		} catch (Exception e) {
			logger.error("updateRoomInfo: " + e.getMessage());
			return WeJson.success("fails");
		}
		if (roomList != null && roomList.size() > 0) {
			member.setRoomId(roomList.get(0).getRoomId().toString());
			member.setMemberType('1');
			try {
				memberService.updateMember(member);
			} catch (Exception e) {
				logger.error("updateRoomInfo: " + e.getMessage());
				return WeJson.success("fails");
			}
			request.getSession().setAttribute("memberid", member.getMemberId());
			return WeJson.success("success");
		} else {
			return WeJson.success("fails");
		}

	}

	@ResponseBody
	@RequestMapping("/searchBuilding")
	public WeJson searchBuilding(HttpServletRequest request, HttpServletResponse response, String searchName) {
		List<WyBuilding> wyBuildings = wyBuildingService.getBySearchName(searchName);
		if (wyBuildings != null && wyBuildings.size() > 0) {
			return WeJson.success(wyBuildings);
		} else {
			return WeJson.fail("查找不到！");
		}
	}

	@ResponseBody
	@RequestMapping("/searchSource")
	public WeJson searchSource(HttpServletRequest request, HttpServletResponse response, String searchName) {
		Urlmap urlmap = new Urlmap();
		urlmap.setUrlname("%" + searchName + "%");
		DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS2);
		List<Urlmap> urlmaps = urlmapService.getUrlmap1(urlmap);
		DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS1);
		if (urlmaps != null && urlmaps.size() > 0) {
			return WeJson.success(urlmaps);
		} else {
			return WeJson.fail("查找不到！");
		}
	}

	@ResponseBody
	@RequestMapping("/searchManagerID")
	public WeJson searchManagerID(HttpServletRequest request) {
		Member member = MemberUtils.getCurrentMember();

		String id = request.getParameter("id");
		DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS2);
		Urlmap urlmap = new Urlmap();
		urlmap.setId(StringUtils.toInteger(id));
		urlmap = urlmapService.getUrlmap(urlmap);
		DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS1);

		RegisteVerify registeVerify = new RegisteVerify();
		List<Map<String, String>> registeVerifyList = null;
		try {
			registeVerify.setName(AES.getAesPwd(member.getMemberName(), urlmap.getSecretkey()));
			registeVerify.setMobile(AES.getAesPwd(member.getMobile(), urlmap.getSecretkey()));
			registeVerify.setSoapActionString(urlmap.getSoapactionstring());
			registeVerify.setSecretkey(urlmap.getSecretkey());
			registeVerify.setUrlString(urlmap.getUrlstring());

			registeVerifyList = HaolongUtils.RegisteVerify(registeVerify);

		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		member.setAdmintype(1);
		member.setSource(urlmap.getUrlkey());
		member.setGroupID(registeVerifyList.get(0).get("GroupID"));
		member.setYgID(registeVerifyList.get(0).get("YGID"));
		memberService.updateMember(member);

		return WeJson.success("success");
	}

	@RequestMapping("/changePhone")
	public String changePhone() {
		return "modules/sns/changePhone";
	}

	@ResponseBody
	@RequestMapping("/changePhoneDetail")
	public WeJson changePhoneDetail(String mobile) {
		Member member = MemberUtils.getCurrentMember();
		if (mobile != null) {
			Member member1 = new Member();
			member1.setMobile(mobile);
			member1 = memberService.findList(member1) != null ? memberService.findList(member1).get(0) : null;
			if (member1 != null) {
				return WeJson.fail("该手机号码已存在");
			}
			member.setMobile(mobile);
			memberService.updateMember(member);
			CacheUtils.put(member.getMemberId().toString(), "member", member);
		} else {
			return WeJson.fail("系统错误！获取不到手机号");
		}
		return WeJson.success("修改成功");
	}
	
	@RequestMapping("/dirUrl")
	public String dirUrl(String roomId, Model model, HttpServletRequest request) {
		Member member = null;
		Member connectmember = null;

		String source = request.getParameter("app_source");
		String groupid = request.getParameter("app_groupid");
		String dirType = request.getParameter("dirType");
		
		if(StringUtils.isNotBlank(source)){
			request.getSession().setAttribute("app_source", source);
		}else{
			source = (String) request.getSession().getAttribute("app_source");
			if(StringUtils.isBlank(source)){
				source = Global.getConfig("app_source");
				request.getSession().setAttribute("app_source", source);
			}
		}
		if(StringUtils.isNotBlank(groupid)){
			request.getSession().setAttribute("app_groupid", groupid);
		}else{
			groupid = request.getSession().getAttribute("app_groupid") + "";
			if(StringUtils.isBlank(groupid)){
				groupid = Global.getConfig("app_groupid");
			}
			request.getSession().setAttribute("app_groupid", groupid); 
		}
		
		if(StringUtils.isNotBlank(dirType)){
			request.getSession().setAttribute("dirType", dirType); 
		}

		try {
			//member = memberService.getMember(37);
			member = MemberUtils.getCurrentMember();
			if (member.getMemberType() == '2') {
				connectmember = MemberUtils.getCurrentMember();
				member = memberService.getMember(connectmember.getParentMemberId());

				if (CacheUtils.get(member.getMemberId().toString(), "member") != null) {
					CacheUtils.remove(member.getMemberId().toString(), "member");
				}
				CacheUtils.put(member.getMemberId().toString(), "member", member);
				request.getSession().setAttribute("memberid", member.getMemberId());

				request.getSession().setAttribute("connectmemberid", connectmember.getMemberId());
			}
			request.getSession().setAttribute("isexist", "true");
		} catch (Exception e) {
			logger.error("无用户缓存:{}", e);
			String ex = Exceptions.deal(e);
			return ex;
		}
		String url = ServletUtil.getRequestUrlAndParas();
		url = Exceptions.changeUrl(url);
		return "redirect:"+url;
	}
	
	@RequestMapping("/cleanSession")
	public void cleanSession(HttpServletRequest request) {
		request.getSession().setAttribute("memberid", 0);
	}

	@RequestMapping("/test")
	public String test() {
		return "modules/sns/test";
	}

	public static void main(String[] args) throws Exception {
		/*
		 * RegisteVerify registeVerify = new RegisteVerify();
		 * registeVerify.setName(AES.getAesPwd("吴欣超", "oklong_iapppay"));
		 * registeVerify.setMobile(AES.getAesPwd("13580750428",
		 * "oklong_iapppay"));
		 * registeVerify.setSoapActionString("http://tempuri.org/");
		 * registeVerify.setSecretkey("oklong_iapppay");
		 * registeVerify.setUrlString("http://sdfee.oklong.com/service.asmx");
		 * 
		 * List<Map<String, String>> registeVerifyList = null; HaolongService
		 * mHaolongService = new HaolongServiceImpl(); registeVerifyList =
		 * mHaolongService.RegisteVerify(registeVerify);
		 * System.out.println(registeVerifyList);
		 */
	}
}
