<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<frameset rows="*" cols="30%,70%">
      <frame id="wxAccount" name="wxAccount" src="${ctx}/weixin/wxAccount/listSelect"/>
      <frame id="msgTpl" name="msgTpl" src="${ctx}/weixin/wxMsgTpl/list" frameborder="0"/>
</frameset>
</html>