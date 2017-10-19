package com.haolinbang.modules.sys.security;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

import com.haolinbang.common.config.Global;
import com.haolinbang.common.utils.DateUtils;
import com.haolinbang.common.utils.MD5Helper;
import com.haolinbang.modules.sys.service.SystemService;

/**
 * 自定义密码验证,根据不同的密码类型,调用具体的验证方式
 * 
 * 支持直接后台登录和,sso第三方统一认证
 * 
 * @author Administrator
 * 
 */
public class CustomCredentialsMatcher extends SimpleCredentialsMatcher {

	@Override
	public boolean doCredentialsMatch(AuthenticationToken authcToken, AuthenticationInfo info) {

		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		String pwdType = String.valueOf(token.getPassword());// 判断一下密码是否是用户输入的，还是JCIS传过来的
		if (pwdType.length() == 32) {// 密码长度=32位，说明是md5加密过，是从sso传进来的
			String secretKey = Global.getConfig("shiro.sso.secretKey");
			// 生成token,一分钟内验证认为有效
			String tokenstr = MD5Helper.encrypt32(secretKey + token.getStaffId() + DateUtils.getDate("yyyyMMdd"));
			if (tokenstr.equals(pwdType)) {
				// 将密码加密与系统加密后的密码校验，内容一致就返回true,不一致就返回false				
				return true;
			}
		} else {// 不是32位,则进行普通验证
			return SystemService.validatePassword(pwdType, info.getCredentials().toString());
		}
		return false;
	}
}
