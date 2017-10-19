package com.haolinbang.modules.sns.service;

/**
 * 物业处理类
 * 
 * @author Administrator
 * 
 */
public interface WuyeService {

	boolean fulfilTask(String bizId, String procInsId, String vars);

	boolean backTask(String bizId, String procInsId) throws Exception;

	String delegateTask(String bizId, String procInsId, String memberid);

	String resolveTask(String bizId, String procInsId);
	
	
	
}
