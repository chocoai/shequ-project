<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<frameset rows="*" cols="40%,60%">
      <frame id="dictType" name="dictType" src="${ctx}/sys/dictType" />
      <frame id="dict" name="dict" src="${ctx}/sys/dict" frameborder="0"/>
</frameset>
</html>