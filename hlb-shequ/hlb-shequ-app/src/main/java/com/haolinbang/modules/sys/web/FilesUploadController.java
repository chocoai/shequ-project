package com.haolinbang.modules.sys.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.haolinbang.common.config.Global;
import com.haolinbang.common.utils.FileUtils;
import com.haolinbang.common.web.BaseController;

/**
 * uedit文件上传实现类
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping("${adminPath}/fileUpload")
public class FilesUploadController extends BaseController {

	/**
	 * 
	 * SpringMVC 用的是 的MultipartFile来进行文件上传
	 * 
	 * 这里用@RequestParam()来指定上传文件为MultipartFile
	 * 
	 */
	@ResponseBody
	@RequestMapping("/uploadImage")
	public Map<String, String> uploadImage(@RequestParam("upfile") MultipartFile upfile, HttpServletRequest request) {
		// 文件原名称
		String fileName = upfile.getOriginalFilename();
		// 返回结果信息(UEditor需要)
		Map<String, String> respMap = new HashMap<String, String>();
		// 是否上传成功
		respMap.put("state", "FAIL");
		try {
			// 为了避免重复简单处理
			if (upfile.isEmpty()) {
				logger.info("上传的文件流为空");
				return respMap;
			}
			// 获取图片文件保存规则
			Map<String, String> map = Global.getLocalPath();
			// 保存文件流到本地
			boolean b = FileUtils.saveFile(upfile.getInputStream(), map.get("amrPath"), map.get("imageName"));
			// 获取服务器访问地址
			String url = map.get("imgUrl");
			if (b) {
				// 是否上传成功
				respMap.put("state", "SUCCESS");
			}
			// 现在文件名称
			respMap.put("title", map.get("imageName"));
			// 文件原名称
			respMap.put("original", fileName);
			// 文件类型 .+后缀名
			respMap.put("type", fileName.substring(upfile.getOriginalFilename().lastIndexOf(".")));
			// 文件路径
			respMap.put("url", url);
			// 文件大小（字节数）
			respMap.put("size", upfile.getSize() + "");

			logger.info("文件上传信息:respMap={}", respMap);

			return respMap;
		} catch (Exception e) {
			logger.error("上传文件出错:{}", e);
			return respMap;
		}
	}

}
