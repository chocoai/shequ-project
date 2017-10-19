package com.haolinbang.modules.sns.dto;

import java.io.Serializable;

public class WyActFormDto implements Serializable {

	private static final long serialVersionUID = -477825602128491396L;

	private String activitiId;// 活动节点id

	private String procDefinitionId;// 流程定义id

	private String formId; // formid自定义，有实际含义

	private String claimUrl;// 认领任务显示url

	private String claimUrlFormid;// url表单id

	private String claimHtml;// 认领任务显示url

	private String claimHtmlFormid;// 认领任务显示url

	private String completeUrl;// 完成任务显示的url

	private String completeUrlFormid;// 完成任务显示的url

	private String completeHtml;// 完成任务显示的url

	private String completeHtmlFormid;// 完成任务显示的url

	private String completedRedirectUrl;// 完成后跳转url

	private String completedRedirectUrlFormid;// 完成后跳转url

	private String completedRedirectHtml;// 完成后跳转url

	private String completedRedirectHtmlFormid;// 完成后跳转url

	private String selectHandlerUrl;// 选择处理人url

	private String selectHandlerUrlFormid;// 选择处理人表单id

	private String selectHandlerHtml;// 选择处理人url

	private String selectHandlerHtmlFormid;// 选择处理人url

	private String startUrl;// 开始url

	private String startUrlFormid;// 开始url

	private String endUrl;// 结束url

	private String endUrlFormid;// 结束url

	private boolean selected;// 是否已选择

	private String delegateHandlerHmltFormid;// 委托他人处理任务节点

	private String delegateHandlerButtonFormid;// 委托他人处理任务节点按钮

	private String activitiType;// 节点类型

	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public String getActivitiType() {
		return activitiType;
	}

	public void setActivitiType(String activitiType) {
		this.activitiType = activitiType;
	}

	public String getDelegateHandlerButtonFormid() {
		return delegateHandlerButtonFormid;
	}

	public void setDelegateHandlerButtonFormid(String delegateHandlerButtonFormid) {
		this.delegateHandlerButtonFormid = delegateHandlerButtonFormid;
	}

	public String getDelegateHandlerHmltFormid() {
		return delegateHandlerHmltFormid;
	}

	public void setDelegateHandlerHmltFormid(String delegateHandlerHmltFormid) {
		this.delegateHandlerHmltFormid = delegateHandlerHmltFormid;
	}

	public String getClaimHtml() {
		return claimHtml;
	}

	public void setClaimHtml(String claimHtml) {
		this.claimHtml = claimHtml;
	}

	public String getClaimHtmlFormid() {
		return claimHtmlFormid;
	}

	public void setClaimHtmlFormid(String claimHtmlFormid) {
		this.claimHtmlFormid = claimHtmlFormid;
	}

	public String getCompleteHtml() {
		return completeHtml;
	}

	public void setCompleteHtml(String completeHtml) {
		this.completeHtml = completeHtml;
	}

	public String getCompleteHtmlFormid() {
		return completeHtmlFormid;
	}

	public void setCompleteHtmlFormid(String completeHtmlFormid) {
		this.completeHtmlFormid = completeHtmlFormid;
	}

	public String getCompletedRedirectHtml() {
		return completedRedirectHtml;
	}

	public void setCompletedRedirectHtml(String completedRedirectHtml) {
		this.completedRedirectHtml = completedRedirectHtml;
	}

	public String getCompletedRedirectHtmlFormid() {
		return completedRedirectHtmlFormid;
	}

	public void setCompletedRedirectHtmlFormid(String completedRedirectHtmlFormid) {
		this.completedRedirectHtmlFormid = completedRedirectHtmlFormid;
	}

	public String getSelectHandlerHtml() {
		return selectHandlerHtml;
	}

	public void setSelectHandlerHtml(String selectHandlerHtml) {
		this.selectHandlerHtml = selectHandlerHtml;
	}

	public String getSelectHandlerHtmlFormid() {
		return selectHandlerHtmlFormid;
	}

	public void setSelectHandlerHtmlFormid(String selectHandlerHtmlFormid) {
		this.selectHandlerHtmlFormid = selectHandlerHtmlFormid;
	}

	public String getSelectHandlerUrl() {
		return selectHandlerUrl;
	}

	public void setSelectHandlerUrl(String selectHandlerUrl) {
		this.selectHandlerUrl = selectHandlerUrl;
	}

	public String getSelectHandlerUrlFormid() {
		return selectHandlerUrlFormid;
	}

	public void setSelectHandlerUrlFormid(String selectHandlerUrlFormid) {
		this.selectHandlerUrlFormid = selectHandlerUrlFormid;
	}

	public String getStartUrlFormid() {
		return startUrlFormid;
	}

	public void setStartUrlFormid(String startUrlFormid) {
		this.startUrlFormid = startUrlFormid;
	}

	public String getEndUrlFormid() {
		return endUrlFormid;
	}

	public void setEndUrlFormid(String endUrlFormid) {
		this.endUrlFormid = endUrlFormid;
	}

	public String getClaimUrlFormid() {
		return claimUrlFormid;
	}

	public void setClaimUrlFormid(String claimUrlFormid) {
		this.claimUrlFormid = claimUrlFormid;
	}

	public String getCompleteUrlFormid() {
		return completeUrlFormid;
	}

	public void setCompleteUrlFormid(String completeUrlFormid) {
		this.completeUrlFormid = completeUrlFormid;
	}

	public String getCompletedRedirectUrlFormid() {
		return completedRedirectUrlFormid;
	}

	public void setCompletedRedirectUrlFormid(String completedRedirectUrlFormid) {
		this.completedRedirectUrlFormid = completedRedirectUrlFormid;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public String getActivitiId() {
		return activitiId;
	}

	public void setActivitiId(String activitiId) {
		this.activitiId = activitiId;
	}

	public String getProcDefinitionId() {
		return procDefinitionId;
	}

	public void setProcDefinitionId(String procDefinitionId) {
		this.procDefinitionId = procDefinitionId;
	}

	public String getClaimUrl() {
		return claimUrl;
	}

	public void setClaimUrl(String claimUrl) {
		this.claimUrl = claimUrl;
	}

	public String getCompleteUrl() {
		return completeUrl;
	}

	public void setCompleteUrl(String completeUrl) {
		this.completeUrl = completeUrl;
	}

	public String getCompletedRedirectUrl() {
		return completedRedirectUrl;
	}

	public void setCompletedRedirectUrl(String completedRedirectUrl) {
		this.completedRedirectUrl = completedRedirectUrl;
	}

	public String getStartUrl() {
		return startUrl;
	}

	public void setStartUrl(String startUrl) {
		this.startUrl = startUrl;
	}

	public String getEndUrl() {
		return endUrl;
	}

	public void setEndUrl(String endUrl) {
		this.endUrl = endUrl;
	}

}
