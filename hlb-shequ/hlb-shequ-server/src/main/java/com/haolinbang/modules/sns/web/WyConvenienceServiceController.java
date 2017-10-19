package com.haolinbang.modules.sns.web;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.haolinbang.common.dto.WeJson;
import com.haolinbang.common.thridwy.haolong.bean.GetLastGroupInfo;
import com.haolinbang.common.thridwy.haolong.utils.HaolongUtils;
import com.haolinbang.common.utils.Encodes;
import com.haolinbang.common.utils.StringUtils;
import com.haolinbang.common.web.BaseController;
import com.haolinbang.modules.sns.dao.AdDao;
import com.haolinbang.modules.sns.dao.WyConvenienceServiceDao;
import com.haolinbang.modules.sns.entity.Member;
import com.haolinbang.modules.sns.entity.Room;
import com.haolinbang.modules.sns.entity.WyConvenienceService;
import com.haolinbang.modules.sns.service.RoomService;
import com.haolinbang.modules.sns.service.UrlmapService;
import com.haolinbang.modules.sns.service.WyBuildingService;
import com.haolinbang.modules.sns.service.WyConvenienceServiceService;
import com.haolinbang.modules.sns.util.GroupidsUtils;
import com.haolinbang.modules.sns.util.MemberUtils;
import com.haolinbang.modules.sns.util.RoomUtils;
import com.haolinbang.modules.weixin.entity.WxAccount;
import com.haolinbang.modules.weixin.service.WxAccountService;

@Controller
@RequestMapping("/wyConvenienceService")
public class WyConvenienceServiceController extends BaseController {

	@Autowired
	private AdDao adDao;

	@Autowired
	private RoomService roomService;

	@Autowired
	private WyConvenienceServiceDao wyConvenienceServiceDao;

	@Autowired
	private WyConvenienceServiceService wyConvenienceServiceService;
	
	@Autowired
	private UrlmapService urlmapService;
	
	@Autowired
	private WyBuildingService wyBuildingService;
	
	@Autowired
	private WxAccountService wxAccountService;
	

	@RequestMapping("/index")
	public String index(HttpServletRequest request, Model model) {
		/*DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS2);
		Ad ad = new Ad();
		ad.setSource(null);
		ad.setWYID(null);
		ad.setStatus("1"); // 有效状态
		List<Ad> ads = adDao.findList(ad);
		CacheUtils.put("ads", ads);
		DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS1);*/

		String dirType = request.getParameter("dirType");
		String source = (String) request.getSession().getAttribute("app_source");
		String groupid = request.getSession().getAttribute("app_groupid")+"";
		Member member = null;
		
		if(StringUtils.isBlank(dirType)){
			member = MemberUtils.getCurrentMember();
		}else if(!dirType.equals("convenience")){
			member = MemberUtils.getCurrentMember();
		}else{
			model.addAttribute("type", "1");
			String appid = (String) request.getSession().getAttribute("app_appid");
			if(StringUtils.isBlank(appid)){
				WxAccount wxAccount = new WxAccount();
				wxAccount.setSource(source);
				wxAccount.setGroupId(StringUtils.toInteger(groupid));
				appid = wxAccountService.getByWxAccount(wxAccount).get(0).getAppid();
				request.getSession().setAttribute("app_appid", appid);
			}
			List<WyConvenienceService> list = wyConvenienceServiceService.getByAppid_Wymc_Type(appid, "", "1");
			if(list==null || list.size()==0){
				model.addAttribute("flag", false);
			}else if(list.size()==1){
				model.addAttribute("content", Encodes.unescapeHtml(list.get(0).getContent()));
				model.addAttribute("flag", true);
			}else{
				model.addAttribute("list", list);
				return "modules/sns/wyConvenienceServiceSearch";
			}
			return "modules/sns/wyConvenienceService";
		}
		
		if(member.getMemberType() == '0'){
			model.addAttribute("type", "1");
			String appid = (String) request.getSession().getAttribute("app_appid");
			if(StringUtils.isBlank(appid)){
				WxAccount wxAccount = new WxAccount();
				wxAccount.setSource(source);
				wxAccount.setGroupId(StringUtils.toInteger(groupid));
				appid = wxAccountService.getByWxAccount(wxAccount).get(0).getAppid();
				request.getSession().setAttribute("app_appid", appid);
			}
			List<WyConvenienceService> list = wyConvenienceServiceService.getByAppid_Wymc_Type(appid, "", "1");
			if(list==null || list.size()==0){
				model.addAttribute("flag", false);
			}else if(list.size()==1){
				model.addAttribute("content", Encodes.unescapeHtml(list.get(0).getContent()));
				model.addAttribute("flag", true);
			}else{
				model.addAttribute("list", list);
				return "modules/sns/wyConvenienceServiceSearch";
			}
			return "modules/sns/wyConvenienceService";
		}
			
		Room room = null;
		if (StringUtils.isNotBlank(member.getRoomId())) {
			room = roomService.getRoom(member.getRoomId());
		}
		WyConvenienceService wyConvenienceService = null;
		if (room != null) {
			wyConvenienceService = wyConvenienceServiceDao.getbySourceAndGroupIdAndType(room.getSource(), room.getGroupId(), "1");
		}

		//model.addAttribute("ads", ads);
		String content = "";
		if (wyConvenienceService != null) {
			content = Encodes.unescapeHtml(wyConvenienceService.getContent());
			model.addAttribute("flag", true);
		}else{
			model.addAttribute("flag", false);
		}

		model.addAttribute("content", content);

		return "modules/sns/wyConvenienceService";
	}
	
	@RequestMapping("/index2")
	public String index2(Model model, HttpServletRequest request) throws Exception {
		/*DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS2);
		Ad ad = new Ad();
		ad.setSource(null);
		ad.setWYID(null);
		ad.setStatus("1"); // 有效状态
		List<Ad> ads = adDao.findList(ad);
		CacheUtils.put("ads", ads);
		DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS1);*/

		String dirType = request.getParameter("dirType");
		String source = (String) request.getSession().getAttribute("app_source");
		String groupid = request.getSession().getAttribute("app_groupid")+"";
		Member member = null;
		
		if(StringUtils.isBlank(dirType)){
			member = MemberUtils.getCurrentMember();
		}else if(!dirType.equals("convenience2")){
			member = MemberUtils.getCurrentMember();
		}else{
			model.addAttribute("type", "3");
			String appid = (String) request.getSession().getAttribute("app_appid");
			if(StringUtils.isBlank(appid)){
				WxAccount wxAccount = new WxAccount();
				wxAccount.setSource(source);
				wxAccount.setGroupId(StringUtils.toInteger(groupid));
				appid = wxAccountService.getByWxAccount(wxAccount).get(0).getAppid();
				request.getSession().setAttribute("app_appid", appid);
			}
			List<WyConvenienceService> list = wyConvenienceServiceService.getByAppid_Wymc_Type(appid, "", "3");
			if(list==null || list.size()==0){
				model.addAttribute("flag", false);
			}else if(list.size()==1){
				model.addAttribute("content", Encodes.unescapeHtml(list.get(0).getContent()));
				model.addAttribute("flag", true);
			}else{
				model.addAttribute("list", list);
				return "modules/sns/wyConvenienceServiceSearch";
			}
			return "modules/sns/wyConvenienceService";
		}
		
		if(member.getMemberType() == '0'){
			model.addAttribute("type", "3");
			String appid = (String) request.getSession().getAttribute("app_appid");
			if(StringUtils.isBlank(appid)){
				WxAccount wxAccount = new WxAccount();
				wxAccount.setSource(source);
				wxAccount.setGroupId(StringUtils.toInteger(groupid));
				appid = wxAccountService.getByWxAccount(wxAccount).get(0).getAppid();
				request.getSession().setAttribute("app_appid", appid);
			}
			List<WyConvenienceService> list = wyConvenienceServiceService.getByAppid_Wymc_Type(appid, "", "3");
			if(list==null || list.size()==0){
				model.addAttribute("flag", false);
			}else if(list.size()==1){
				model.addAttribute("content", Encodes.unescapeHtml(list.get(0).getContent()));
				model.addAttribute("flag", true);
			}else{
				model.addAttribute("list", list);
				return "modules/sns/wyConvenienceServiceSearch";
			}
			return "modules/sns/wyConvenienceService";
		}
		
		Room room = null;
		if (StringUtils.isNotBlank(member.getRoomId())) {
			room = roomService.getRoom(member.getRoomId());
		}
		
		String wxAccountId = (String) request.getSession().getAttribute("wxAccountId");
		
		WyConvenienceService wyConvenienceService = null;
		if (room != null) {
			wyConvenienceService = wyConvenienceServiceDao.getCompanyProfile(room.getSource(), room.getGroupId(), null);
			if(wyConvenienceService == null){
				List<String> groupids = GroupidsUtils.getLastGroupIDs(room.getSource(), room.getGroupId());
				if(groupids!=null && groupids.size()>1){
					int length = groupids.size()-2;
					while(wyConvenienceService==null && length>=0){
						wyConvenienceService = wyConvenienceServiceDao.getCompanyProfile(room.getSource(), StringUtils.toInteger(groupids.get(length)), wxAccountId);
						length--;
					}
				}
			}
			//wyConvenienceService = wyConvenienceServiceDao.getbySourceAndType(room.getSource(), "3");
		}else{
			if(member.getMemberType()=='0'){
				wyConvenienceService = wyConvenienceServiceDao.getCompanyProfile(source, StringUtils.toInteger(groupid), wxAccountId);
			}
		}

		//model.addAttribute("ads", ads);
		String content = "";
		if (wyConvenienceService != null) {
			content = Encodes.unescapeHtml(wyConvenienceService.getContent());
			model.addAttribute("flag", true);
		}else{
			model.addAttribute("flag", false);
		}

		model.addAttribute("content", content);

		return "modules/sns/wyConvenienceService";
	}
	
/*	public List<String> getLastGroupIDs(String source, Integer groupId) throws Exception{
		DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS2);
		Urlmap urlmap = new Urlmap();
		urlmap.setStatus("1");
		urlmap.setUrlkey(source);
		List<Urlmap> urlmaps = urlmapService.getUrlmap1(urlmap);
		urlmap = urlmaps.get(0);
		DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS1);
		GetLastGroupInfo getLastGroupInfo = new GetLastGroupInfo();
		getLastGroupInfo.setGroupId(groupId.toString());
		getLastGroupInfo.setSecretkey(urlmap.getSecretkey());
		getLastGroupInfo.setSoapActionString(urlmap.getSoapactionstring());
		getLastGroupInfo.setUrlString(urlmap.getUrlstring());
		
		List<Map<String, String>> getLastGroupInfos = null;
		try {
			getLastGroupInfos = HaolongUtils.GetLastGroupInfo(getLastGroupInfo);
		} catch (IOException e) {
			//logger.error("调用GetLastGroupInfo接口出错，错误信息："+e.getMessage());
			System.out.println("调用GetLastGroupInfo接口出错，错误信息："+e.getMessage());
			return null;
		}
		
		List<String> groupids = new ArrayList<String>();
		if(getLastGroupInfos!=null && getLastGroupInfos.size()>1){
			for(Map<String, String> maps : getLastGroupInfos){
				groupids.add(maps.get("GroupID"));
			}
		}
		return groupids;
	}*/

	

	/**
	 * 根据id查看公告、通告信息
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/view/{id}")
	public String view(@PathVariable String id, Model model) {
		WyConvenienceService wyConvenienceService = wyConvenienceServiceService.get(id);

		String content = wyConvenienceService.getContent();
		if (StringUtils.isNotBlank(content)) {
			content = Encodes.unescapeHtml(wyConvenienceService.getContent());
			model.addAttribute("content", content);
		}

		return "modules/sns/wyConvenienceService";
	}
	
	@RequestMapping("/search")
	public String search(HttpServletRequest request, Model model, String wymc, String type) {
		String appid = (String) request.getSession().getAttribute("app_appid");
		if(StringUtils.isBlank(appid)){
			WxAccount wxAccount = new WxAccount();
			wxAccount.setSource((String) request.getSession().getAttribute("app_source"));
			wxAccount.setGroupId(StringUtils.toInteger(request.getSession().getAttribute("app_groupid")));
			appid = wxAccountService.getByWxAccount(wxAccount).get(0).getAppid();
			request.getSession().setAttribute("app_appid", appid);
		}
		
		List<WyConvenienceService> convenienceService = wyConvenienceServiceService.getByAppid_Wymc_Type(appid, wymc, type);
		model.addAttribute("list", convenienceService);
		model.addAttribute("type", type);
		if(convenienceService==null || convenienceService.size()<=0){
			model.addAttribute("flag", false);
		}
		return "modules/sns/wyConvenienceServiceSearch";
		
		/*if (convenienceService != null && convenienceService.size()>0) {
			String content = Encodes.unescapeHtml(convenienceService.get(0).getContent());
			return WeJson.success(content);
		}else{
			return WeJson.fail("false");
		}*/
	}
	
	public static void main(String[] args) throws IOException{
		GetLastGroupInfo getLastGroupInfo = new GetLastGroupInfo();
		getLastGroupInfo.setGroupId("5");
		getLastGroupInfo.setSecretkey("oklong_iapppay");
		getLastGroupInfo.setSoapActionString("http://tempuri.org/");
		getLastGroupInfo.setUrlString("http://sdfee.oklong.com/service.asmx");
		List<Map<String, String>> getLastGroupInfos = null;
		getLastGroupInfos = HaolongUtils.GetLastGroupInfo(getLastGroupInfo);
		System.out.println(getLastGroupInfos);
	}
}
