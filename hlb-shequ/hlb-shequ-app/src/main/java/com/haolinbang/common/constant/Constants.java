package com.haolinbang.common.constant;

public class Constants {

	/**
	 * 地区国家编码
	 */
	public static final String SYS_AREA_COUNTRY = "1";

	/**
	 * 地区省份、直辖市编码
	 */
	public static final String SYS_AREA_POVINCE = "2";

	/**
	 * 城市编码
	 */
	public static final String SYS_AREA_CITY = "3";

	/**
	 * 区县编码
	 */
	public static final String SYS_AREA_QU = "4";

	/************* 微信公众号类型 *************/
	/**
	 * 微信订阅号
	 */
	public static final String WEIXIN_NUM_SUBSCRIBE = "1";
	/**
	 * 微信服务号
	 */
	public static final String WEIXIN_NUM_SERVICE = "2";
	/**
	 * 微信企业号
	 */
	public static final String WEIXIN_NUM_COMPANY = "3";

	/**
	 * 微信为默认选中账号
	 */
	public static final int WEIXIN_IS_DEFAULT_ACCOUT = 1;

	/**
	 * 微信为非默认选中账号
	 */
	public static final int WEIXIN_NOT_DEFAULT_ACCOUT = 0;

	/**
	 * 微信消息加密密钥由43位字符组成
	 */
	public static final int WEIXIN_ENCODING_AES_KEYS_LENGTH = 43;

	/**
	 * 生成包的路径
	 */
	public static final String GEN_MODULES = "com.haolinbang.modules";
	/**
	 * 微信消息内容，内容输入
	 */
	public static final String WEIXIN_INPUT = "01";
	/**
	 * 微信消息内容，内容输出
	 */
	public static final String WEIXIN_OUTPUT = "01";

}
