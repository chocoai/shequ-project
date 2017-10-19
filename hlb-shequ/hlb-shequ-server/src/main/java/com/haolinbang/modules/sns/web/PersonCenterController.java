package com.haolinbang.modules.sns.web;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.haolinbang.common.config.Global;
import com.haolinbang.common.dto.WeJson;
import com.haolinbang.common.persistence.datasource.DataSourceType;
import com.haolinbang.common.persistence.datasource.DynamicDataSourceHolder;
import com.haolinbang.common.thridwy.haolong.bean.GetRights;
import com.haolinbang.common.thridwy.haolong.bean.GetTimeStamp;
import com.haolinbang.common.thridwy.haolong.utils.HaolongUtils;
import com.haolinbang.common.utils.AES;
import com.haolinbang.common.utils.CacheUtils;
import com.haolinbang.common.utils.StringUtils;
import com.haolinbang.common.web.BaseController;
import com.haolinbang.modules.sns.dao.AdDao;
import com.haolinbang.modules.sns.entity.Ad;
import com.haolinbang.modules.sns.entity.Member;
import com.haolinbang.modules.sns.entity.Menus;
import com.haolinbang.modules.sns.service.MemberService;
import com.haolinbang.modules.sns.service.MenusService;
import com.haolinbang.modules.sns.util.MemberUtils;

@Controller
@RequestMapping("/personcenter")
public class PersonCenterController extends BaseController{
	
	@Autowired
	private AdDao adDao;
	
	@Autowired
	private MenusService menusService;
	
	@Autowired
	private MemberService memberService;
	
	@RequestMapping("/index")
	public String index(Model model, HttpServletRequest request){
		DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS2);
		Ad ad = new Ad();
		ad.setSource(null);
		ad.setWYID(null);
		ad.setStatus("1"); // 有效状态
		List<Ad> ads = adDao.findList(ad);
		CacheUtils.put("ads", ads);
		DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS1);
		
		List<Menus> menusList = menusService.getMenusList(1);
		
		String unStr = (String) request.getSession().getAttribute("screenCodes");
		
		if(StringUtils.isBlank(unStr)){
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
				
				if(StringUtils.isNotBlank(unStr)){
					unStr = unStr.split("\\^")[0];
				}
				request.getSession().setAttribute("screenCodes", unStr);
			} catch (Exception e) {
				logger.error("获取时间戳:{}", e);
			}
		}

		if(StringUtils.isNotBlank(unStr)){
			for(int i=0; i<menusList.size(); ){
				int screenCode = StringUtils.toInteger(menusList.get(i).getScreenCode());
				if(screenCode == 0){
					menusList.get(i).setScreenCode("true");
					i++;
				}else if(screenCode <= unStr.length()){
					String flag = unStr.substring(screenCode-1, screenCode);
					if(flag.equals("Y")){
						menusList.get(i).setScreenCode("true");
						i++;
					}else{
						menusList.remove(i);
					}
				}else{
					menusList.remove(i);
				}
			}
		}

		model.addAttribute("ads", ads);
		model.addAttribute("menuList", menusList);
		
		Member member = MemberUtils.getCurrentMember();
		if (member.getMemberType() == '0') {
			model.addAttribute("membertype", 0);
		} else {
			model.addAttribute("membertype", 1);
		}
		model.addAttribute("admintype", member.getAdmintype());
		
		return "modules/sns/personcenter";
	}
	
	@RequestMapping("myRegister")
	public String myRegister(Model model){
		Member member = MemberUtils.getCurrentMember();
		
		DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS2);
		Ad ad = new Ad();
		ad.setSource(null);
		ad.setWYID(null);
		ad.setStatus("1"); // 有效状态
		List<Ad> ads = adDao.findList(ad);
		CacheUtils.put("ads", ads);
		DynamicDataSourceHolder.setDataSource(DataSourceType.SOURCE_DS1);
		
		model.addAttribute("ads", ads);
		model.addAttribute("member", member);
		
		return "modules/sns/myRegister";
	}
	
	/*
	 * ajax上传文件
	 */
	@ResponseBody
	@RequestMapping("/uploadfile")
	public WeJson uploadfile(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request, String foldername) {
		String fileName = file.getOriginalFilename(); 
		String slash = File.separator;
		SimpleDateFormat year = new SimpleDateFormat("yyyy");
		SimpleDateFormat month = new SimpleDateFormat("MM");
		SimpleDateFormat day = new SimpleDateFormat("dd");
		Date date = new Date();
		String urlPath = foldername + slash + year.format(date) + slash + month.format(date) + slash + day.format(date) + slash;
		String fileUrl = Global.getUserfilesBaseDir() + urlPath;
		String returnUrl = (Global.getConfig("server.project.images") + slash + urlPath).replaceAll("\\\\", "/") + fileName;
		File targetFile = new File(fileUrl, fileName);  
		if(!targetFile.exists()){  
		    targetFile.mkdirs();  
		}
		
		//保存  
        try {  
            file.transferTo(targetFile);  
            Member member = MemberUtils.getCurrentMember();
            member.setAvatarurl(returnUrl);
            memberService.updateMember(member);
        } catch (Exception e) {  
            e.printStackTrace();  
        }  

        return WeJson.success(returnUrl);
	}
	
	/*
	 * ajax上传文件
	 */
	@ResponseBody
	@RequestMapping("/saveInfo")
	public WeJson saveInfo(HttpServletRequest request) {
		Member member = MemberUtils.getCurrentMember();
		 
		String nickname = request.getParameter("nickname");
		String sex = request.getParameter("sex");
		
		member.setNickname(nickname);
		member.setSex(StringUtils.toInteger(sex));
		
		memberService.updateMember(member);
		
        return WeJson.success("保存成功");
	}
	
	/*
	 * 我的收货地址
	 */
	@RequestMapping("myaddress")
	public String myaddress(Model model){
		
		return "";
	}
}
