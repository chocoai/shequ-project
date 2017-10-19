package com.haolinbang.modules.sns.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.haolinbang.common.config.Global;
import com.haolinbang.common.dto.WeJson;
import com.haolinbang.common.persistence.Page;
import com.haolinbang.common.thridwy.haolong.bean.bean.GroupInfo;
import com.haolinbang.common.utils.StringUtils;
import com.haolinbang.common.web.BaseController;
import com.haolinbang.modules.sns.entity.WyClassification;
import com.haolinbang.modules.sns.entity.WyConvenienceService;
import com.haolinbang.modules.sns.entity.WyMemberRefQuestionnaire;
import com.haolinbang.modules.sns.entity.WyOption;
import com.haolinbang.modules.sns.entity.WyQuestionnaire;
import com.haolinbang.modules.sns.entity.WyQuestionnaireRelease;
import com.haolinbang.modules.sns.entity.WySubject;
import com.haolinbang.modules.sns.service.WyClassificationService;
import com.haolinbang.modules.sns.service.WyMemberRefQuestionnaireService;
import com.haolinbang.modules.sns.service.WyOptionService;
import com.haolinbang.modules.sns.service.WyQuestionnaireReleaseService;
import com.haolinbang.modules.sns.service.WyQuestionnaireService;
import com.haolinbang.modules.sns.service.WySubjectService;
import com.haolinbang.modules.sys.utils.UserUtils;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * 问卷发布表Controller
 * @author wxc
 * @version 2017-06-19
 */
@Controller
@RequestMapping(value = "${adminPath}/sns/wyQuestionnaireRelease")
public class WyQuestionnaireReleaseController extends BaseController {

	@Autowired
	private WyQuestionnaireReleaseService wyQuestionnaireReleaseService;
	
	@Autowired
	private WyQuestionnaireService wyQuestionnaireService;
	
	@Autowired
	private WyClassificationService	wyClassificationService;
	
	@Autowired
	private WySubjectService wySubjectService;
	
	@Autowired
	private WyOptionService wyOptionService;
	
	@Autowired
	private WyMemberRefQuestionnaireService wyMemberRefQuestionnaireService;
	
	@RequiresPermissions("sns:wyQuestionnaireRelease:view")
	@RequestMapping("")
	public String init(WyQuestionnaireRelease wyQuestionnaireRelease, HttpServletRequest request, HttpServletResponse response, Model model) {
		request.getSession().setAttribute("wqrgid", "");
		request.getSession().setAttribute("wqrname", "");
		return "modules/sns/wyQuestionnaireReleaseIndex";
	}
	
	@ModelAttribute
	public WyQuestionnaireRelease get(@RequestParam(required=false) String releaseId) {
		WyQuestionnaireRelease entity = null;
		if (StringUtils.isNoneBlank(releaseId)){
			entity = wyQuestionnaireReleaseService.get(releaseId);
		}
		if (entity == null){
			entity = new WyQuestionnaireRelease();
		}
		return entity;
	}
	
	@RequiresPermissions("sns:wyQuestionnaireRelease:view")
	@RequestMapping(value = {"list"})
	public String list(WyQuestionnaireRelease wyQuestionnaireRelease, HttpServletRequest request, HttpServletResponse response, Model model) {
		String releaseId = request.getParameter("releaseId");
		String runstatus = request.getParameter("runstatus");
		if(StringUtils.isNotBlank(releaseId) && StringUtils.isNotBlank(runstatus)){
			WyQuestionnaireRelease questionnaireRelease = new WyQuestionnaireRelease();
			questionnaireRelease.setReleaseId(StringUtils.toInteger(releaseId));
			questionnaireRelease.setRunstatus(StringUtils.toInteger(runstatus));
			wyQuestionnaireReleaseService.update(questionnaireRelease);
			wyQuestionnaireRelease.setQuestionnaireid(null);
		}
		
		String gid = request.getParameter("gid");
		String name = request.getParameter("name");
		if(StringUtils.isBlank(gid)){
			gid = (String) request.getSession().getAttribute("wqrgid");
		}
		if(StringUtils.isBlank(name)){
			name = (String) request.getSession().getAttribute("wqrname");
		}
		if(StringUtils.isBlank(gid)){
			String urlkey = UserUtils.getUser().getSource();
			List<GroupInfo> list = UserUtils.getUserGroupInfo(urlkey);
			GroupInfo groupInfo = list.get(list.size()-1);
			List<String> groupids = UserUtils.getEmployeeOfGroups();
			boolean flag = false;
			for(String ss : groupids){
				if(groupInfo.getGroupId().equals(ss)){
					flag = true;
				}
			}
			if(flag){
				wyQuestionnaireRelease.setSource(urlkey);
				wyQuestionnaireRelease.setGroupid(StringUtils.toInteger(groupInfo.getGroupId()));
			}else{
				return "modules/sns/wyCompanyProfileNoForm";
			}
		}else if(StringUtils.isBlank(name)){
			return "modules/sns/wyCompanyProfileNoForm";
		}else{
			if(name.contains("可操作")){
				String[] gids = gid.split("___");
				wyQuestionnaireRelease.setSource(gids[1]);
				wyQuestionnaireRelease.setGroupid(StringUtils.toInteger(gids[2]));
			}else{
				return "modules/sns/wyCompanyProfileNoForm";
			}
		}
		
		Page<WyQuestionnaireRelease> page = wyQuestionnaireReleaseService.findPage(new Page<WyQuestionnaireRelease>(request, response), wyQuestionnaireRelease); 
		model.addAttribute("page", page);
		request.getSession().setAttribute("wqrgid", gid);
		request.getSession().setAttribute("wqrname", name);
		return "modules/sns/wyQuestionnaireReleaseList";
	}

	@RequiresPermissions("sns:wyQuestionnaireRelease:view")
	@RequestMapping(value = "form")
	public String form(WyQuestionnaireRelease wyQuestionnaireRelease, Model model, String type) {
		model.addAttribute("wyQuestionnaireRelease", wyQuestionnaireRelease);
		model.addAttribute("type", type);
		return "modules/sns/wyQuestionnaireReleaseForm";
	}

	@RequiresPermissions("sns:wyQuestionnaireRelease:edit")
	@RequestMapping(value = "save")
	public String save(WyQuestionnaireRelease wyQuestionnaireRelease, Model model, RedirectAttributes redirectAttributes, String type) {
		if(type!=null && type.equals("edit")){
			wyQuestionnaireRelease.setId(wyQuestionnaireRelease.getReleaseId().toString());
		}
			
		if (!beanValidator(model, wyQuestionnaireRelease)){
			return form(wyQuestionnaireRelease, model, type);
		}
		wyQuestionnaireReleaseService.save(wyQuestionnaireRelease);
		addMessage(redirectAttributes, "保存问卷发布表成功");
		return "redirect:"+Global.getAdminPath()+"/sns/wyQuestionnaireRelease/list?repage";
	}
	
	@RequiresPermissions("sns:wyQuestionnaireRelease:edit")
	@RequestMapping(value = "delete")
	public String delete(WyQuestionnaireRelease wyQuestionnaireRelease, RedirectAttributes redirectAttributes) {
		/*if(wyQuestionnaireRelease.getRunstatus() == 1){
			addMessage(redirectAttributes, "问卷运行中，不可删除");
		}else if(wyQuestionnaireRelease.getNum() > 0){
			addMessage(redirectAttributes, "该问卷已有用户参与，不可删除");
		}else{
			wyQuestionnaireReleaseService.delete(wyQuestionnaireRelease);
			addMessage(redirectAttributes, "删除问卷发布表成功");
		}*/
		
		if(wyQuestionnaireRelease.getNum() > 0){
			addMessage(redirectAttributes, "该问卷已有用户参与，不可删除");
		}else{
			wyQuestionnaireReleaseService.delete(wyQuestionnaireRelease);
			addMessage(redirectAttributes, "删除问卷发布表成功");
		}
		
		return "redirect:"+Global.getAdminPath()+"/sns/wyQuestionnaireRelease/list?repage";
	}
	
	@RequiresPermissions("sns:wyQuestionnaireRelease:edit")
	@RequestMapping(value = "showResult")
	public String showResult(WyQuestionnaireRelease wyQuestionnaireRelease, RedirectAttributes redirectAttributes, Model model) {
		//获取该问卷的所有题目
		//遍历每道题目,获取该题目的选中数量以及每个选项的数据库中的数量,保留每个选项的数量和百分比
		WyQuestionnaire wyQuestionnaire = wyQuestionnaireService.get(wyQuestionnaireRelease.getQuestionnaireid().toString());
		WySubject wySubject = new WySubject();
		wySubject.setQuestionnaireid(wyQuestionnaireRelease.getQuestionnaireid());
		wySubject.setType(3);
		List<WySubject> subjects = wySubjectService.findList(wySubject);
		List<Object> wototalss = new ArrayList<Object>();
		List<Object> wotitless = new ArrayList<Object>();
		List<Integer> subjectids = new ArrayList<Integer>();
		List<Object> titles = new ArrayList<Object>();
		
		for(WySubject ws : subjects){
			subjectids.add(ws.getSubjectid());
			if(ws.getSortval() == 0){
				titles.add("第"+ws.getSortval()+"题： "+ws.getTitle()+"[单选题]");
			}else{
				titles.add("第"+ws.getSortval()+"题： "+ws.getTitle()+"[多选题]");
			}
			List<WyOption> wyOptions = wyOptionService.getbysubjectid(ws.getSubjectid());
			WyMemberRefQuestionnaire wmrq = new WyMemberRefQuestionnaire();
			wmrq.setSubjectid(ws.getSubjectid());
			//Integer wstotal = wyMemberRefQuestionnaireService.findList(wmrq).size();//题目总数
			Integer wstotal = wyMemberRefQuestionnaireService.getNum(ws.getSubjectid());
			List<Integer> wototals = new ArrayList<Integer>();
			//List<Double> woscales = new ArrayList<Double>();
			List<String> wotitles = new ArrayList<String>();
			
			for(WyOption wo : wyOptions){
				wmrq.setSubjectid(null);
				wmrq.setOptionid(wo.getOptionid());
				Integer wototal = wyMemberRefQuestionnaireService.findList(wmrq).size();//选项总数
				Double woscale = 0.0;
				if(wototal != 0){
					woscale = ((double) (wototal*100))/wstotal;//选项比例
					BigDecimal b = new BigDecimal(woscale);
					woscale = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				}
				wototals.add(wototal);
				//woscales.add(woscale);
				wotitles.add(wo.getContent()+"("+woscale+"%)");
			}
			wototalss.add(wototals);
			//woscaless.add(woscales);
			wotitless.add(wotitles);
		}
		
		model.addAttribute("subjects", subjects);//题目列表
		model.addAttribute("wototalss", JSON.toJSON(wototalss));//选项个数列表
		model.addAttribute("subjectids", JSON.toJSON(subjectids));//选项百分比列表
		model.addAttribute("wotitless", JSON.toJSON(wotitless));//选项题目列表
		model.addAttribute("titles", JSON.toJSON(titles));//题目
		model.addAttribute("wyQuestionnaire", wyQuestionnaire);
		

		/*计算统计得分
		 *（当前分类权值/总分类权值）*（当前题目权值/当前分类总题目权值）
		 *获取问卷，为问卷添加得分的临时变量point
		 *获取分类列表和总分类权值
		 *遍历分类列表,计算出当前的分类获得的权值value1，既（当前分类权值/总分类权值）
		 *根据分类id获取对应的非填空题目列表
		 *遍历题目列表,获取对应的选项列表
		 *计算当前题目总权值a,如果题目是单选题,a=权值最大的选项，如果题目是多选题,a=所有选项的权值之和
		 *根据题目id获取选项集合，  遍历选项,选项的所占的比例b, 选项得分d=b*(选项的权值c/a)，题目得分e=该题目的d相加之和ds
		 *point += e * value1
		 */
		WyClassification wyClassification = new WyClassification();
		wyClassification.setQuestionnaireid(wyQuestionnaireRelease.getQuestionnaireid());
		double point = 0.0;
		List<WyClassification> wyClassifications = wyClassificationService.findList(wyClassification);
		if(wyClassifications!=null && wyClassifications.size()>0){
			double wyClassificationValue = wyClassificationService.getWeight(wyQuestionnaireRelease.getQuestionnaireid());
			for(WyClassification wc : wyClassifications){
				double value1 = wc.getWeight()/wyClassificationValue;
				WySubject wySubject1 = new WySubject();
				wySubject1.setClassificationid(wc.getClassificationid());
				wySubject1.setType(3);
				List<WySubject> wySubjects = wySubjectService.findList(wySubject1);
				if(wySubjects!=null && wySubjects.size()>0){
					for(WySubject ws : wySubjects){
						double a, e=0.0;
						if(ws.getType() == 0){
							a = wyOptionService.getWeight1(ws.getSubjectid());
						}else{
							a = wyOptionService.getWeight(ws.getSubjectid());
						}
						Integer wstotal = wyMemberRefQuestionnaireService.getNum(ws.getSubjectid());//题目总人数
						List<WyOption> wyOptions = wyOptionService.getbysubjectid(ws.getSubjectid());
						for(WyOption wo : wyOptions){
							WyMemberRefQuestionnaire wmrq = new WyMemberRefQuestionnaire();
							wmrq.setOptionid(wo.getOptionid());
							Integer wototal = wyMemberRefQuestionnaireService.findList(wmrq).size();//选项总数
							double b = 0.0;
							if(wototal != 0){
								b = ((double) (wototal*100))/wstotal;//选项比例
								BigDecimal b1 = new BigDecimal(b);
								b = b1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
							}
							Integer c = wo.getWeight();
							double d = b * (c/a);
							BigDecimal b1 = new BigDecimal(d);
							d = b1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
							e += d;
						}
						point += e * value1;
					}
				}
			}
		}
		BigDecimal b1 = new BigDecimal(point);
		point = b1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		model.addAttribute("point", point);
		
		return "modules/sns/wyQuestionnaireReleaseResult";
	}
	
	@ResponseBody
	@RequestMapping(value = "toexport")
	public WeJson toexport(Integer questionnaireid, HttpServletRequest request, Model model){
		WySubject wySubject = new WySubject();
		wySubject.setQuestionnaireid(questionnaireid);
		wySubject.setType(3);
		String slash = File.separator;
		String imgFilePath = request.getSession().getServletContext().getRealPath(slash)+"upload"+slash+"report";
		List<WySubject> subjects = wySubjectService.findList(wySubject);
		List<String> imgurls = new ArrayList<String>();
		for(WySubject ws : subjects){
			String imgStr = request.getParameter("url"+ws.getSubjectid());
			String imgurl = imgFilePath+slash+ws.getSubjectid()+".png";
			File file = new File(imgFilePath);
            if(!file.exists()){
            	file.mkdirs();
            }
			GenerateImage(imgStr, imgurl);
			imgurls.add(imgurl);
		}
		createPDF(imgurls);
		return null;
	}
	
	//base64字符串转化成图片  
    public static boolean GenerateImage(String imgStr, String imgFilePath)  
    {   //对字节数组字符串进行Base64解码并生成图片  
		if (imgStr == null) //图像数据为空  
	        return false;  
    	imgStr = imgStr.replaceAll(" ", "+");
    	String[] arr = imgStr.split("base64,"); 
    	System.out.println(arr[1]);
        Base64 decoder = new Base64();  
        try   
        {  
            //Base64解码  
            byte[] b = decoder.decode(arr[1]);  
            for(int i=0;i<b.length;++i)  
            {  
                if(b[i]<0)  
                {//调整异常数据  
                    b[i]+=256;  
                }  
            }  
            
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);  
            out.flush();  
            out.close();  
            return true;  
        }   
        catch (Exception e)   
        {  
            return false;  
        }  
    }  
    
    public static void createPDF(List<String> imgurls){
    	Document document = new Document();
        try
        {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("AddImageExample.pdf"));
            document.open();
            document.add(new Paragraph("Image Example"));
         
            for(int i=0; i<imgurls.size(); i++){
            	Image image = Image.getInstance(imgurls.get(i));
                image = imageScale(image, document, i);
                document.add(image);
            }

           //String imageUrl = "D:\\5.png";
           // Image image2 = Image.getInstance("D:\\5.png");
            //document.add(image2);
            document.newPage();
            document.close();
            writer.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    /*
     * 处理pdf图片大小
     */
    public static Image imageScale(Image image, Document document, int i){
    	float percentage = 1;  
        //这里都是图片最原始的宽度与高度  
        float resizedWidht = image.getWidth();  
        float resizedHeight = image.getHeight();  

        //这时判断图片宽度是否大于页面宽度减去也边距，如果是，那么缩小，如果还大，继续缩小，  
        //这样这个缩小的百分比percentage会越来越小  
        while (resizedWidht > (document.getPageSize().getWidth()) * 0.9)  
        {  
            percentage = percentage * 0.9f;  
            resizedHeight = image.getWidth() * percentage;  
            resizedWidht = image.getWidth() * percentage;  
        }  
        //There is a 0.8 here. If the height of the image is too close to the page size height,  
        //the image will seem so big  
        while (resizedHeight > (document.getPageSize().getHeight()) * 1)  
        {  
            percentage = percentage * 0.9f;  
            resizedHeight = image.getHeight() * percentage;  
            resizedWidht = image.getWidth() * percentage;  
        }  

        //这里用计算出来的百分比来缩小图片  
        image.scalePercent(percentage * 100);
        //让图片的中心点与页面的中心店进行重合  
        image.setAbsolutePosition(document.getPageSize().getWidth()/2 - resizedWidht / 2, document.getPageSize().getHeight() - i*resizedHeight);
        return image;
    }
    
    @RequestMapping(value = "showResultDetail")
	public String showResultDetail(WyQuestionnaireRelease wyQuestionnaireRelease, RedirectAttributes redirectAttributes, Model model) {
		//获取该问卷的所有题目
		//遍历每道题目,获取该题目的选中数量以及每个选项的数据库中的数量,保留每个选项的数量和百分比
    	wyQuestionnaireRelease = wyQuestionnaireReleaseService.get(wyQuestionnaireRelease.getReleaseId().toString());
		WyQuestionnaire wyQuestionnaire = wyQuestionnaireService.get(wyQuestionnaireRelease.getQuestionnaireid().toString());
		WySubject wySubject = new WySubject();
		wySubject.setQuestionnaireid(wyQuestionnaireRelease.getQuestionnaireid());
		wySubject.setType(3);
		List<WySubject> subjects = wySubjectService.findList(wySubject);
		List<Object> wototalss = new ArrayList<Object>();
		List<Object> wotitless = new ArrayList<Object>();
		List<Integer> subjectids = new ArrayList<Integer>();
		List<Object> titles = new ArrayList<Object>();
		
		for(WySubject ws : subjects){
			subjectids.add(ws.getSubjectid());
			if(ws.getSortval() == 0){
				titles.add("第"+ws.getSortval()+"题： "+ws.getTitle()+"[单选题]");
			}else{
				titles.add("第"+ws.getSortval()+"题： "+ws.getTitle()+"[多选题]");
			}
			List<WyOption> wyOptions = wyOptionService.getbysubjectid(ws.getSubjectid());
			WyMemberRefQuestionnaire wmrq = new WyMemberRefQuestionnaire();
			wmrq.setSubjectid(ws.getSubjectid());
			Integer wstotal = wyMemberRefQuestionnaireService.findList(wmrq).size();//题目总数
			List<Integer> wototals = new ArrayList<Integer>();
			//List<Double> woscales = new ArrayList<Double>();
			List<String> wotitles = new ArrayList<String>();
			
			for(WyOption wo : wyOptions){
				wmrq.setSubjectid(null);
				wmrq.setOptionid(wo.getOptionid());
				Integer wototal = wyMemberRefQuestionnaireService.findList(wmrq).size();//选项总数
				Double woscale = 0.0;
				if(wototal != 0){
					woscale = ((double) (wototal*100))/wstotal;//选项比例
					BigDecimal b = new BigDecimal(woscale);
					woscale = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				}
				wototals.add(wototal);
				//woscales.add(woscale);
				wotitles.add(wo.getContent()+"("+woscale+"%)");
			}
			wototalss.add(wototals);
			//woscaless.add(woscales);
			wotitless.add(wotitles);
		}
		
		model.addAttribute("subjects", subjects);//题目列表
		model.addAttribute("wototalss", JSON.toJSON(wototalss));//选项个数列表
		model.addAttribute("subjectids", JSON.toJSON(subjectids));//选项百分比列表
		model.addAttribute("wotitless", JSON.toJSON(wotitless));//选项题目列表
		model.addAttribute("titles", JSON.toJSON(titles));//题目
		model.addAttribute("wyQuestionnaire", wyQuestionnaire);
		
		return "modules/sns/countResultDetail";
	}
}