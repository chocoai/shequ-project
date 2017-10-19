<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<frameset rows="*" cols="40%,60%">
	<frameset rows="50%,50%" cols="*">
	 	<frame id="wxMassMsgCommonSelect" name="wxMassMsgCommonSelect" src="${ctx}/weixin/wxMassMsgCommon/select"/>      
      	<frame id="tplSelect" name="tplSelect" src="${ctx}/weixin/wxMassMsg/tplSelect" frameborder="0"/>
	</frameset>
		<frame id="tplSelectAccounut" name="tplSelectAccounut" src="${ctx}/weixin/wxMassMsg/tplSelectAccounut" frameborder="0"/>
</frameset>
</html>