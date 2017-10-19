<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <title>流程详情</title>
    <link rel="stylesheet" href="${ctxStatic}/css/processDetail.css">    
</head>
<body>    
    <div data-mohe-type="kuaidi_new" class="g-mohe " id="mohe-kuaidi_new">
        <div id="mohe-kuaidi_new_nucom">
            <div class="mohe-wrap mh-wrap">
                <div class="mh-cont mh-list-wrap mh-unfold">
                    <div class="mh-list">
                        <ul>                        
                        <c:forEach items="${wyApproveDetailList}" var="detail" varStatus="status">
                            <li ${status.index==0?'class="first"':''}>
                            	<p>${detail.taskName}</p>  
                            	<c:choose>
                            	<c:when test="${not empty detail.endTime}">
                            	<p><fmt:formatDate value="${detail.endTime}" pattern="yyyy-MM-dd HH:mm"/></p>
                            	</c:when>
                            	<c:otherwise>
                            	<p><fmt:formatDate value="${detail.startTime}" pattern="yyyy-MM-dd HH:mm"/></p>
                            	</c:otherwise>                            	
                            	</c:choose>
                                <p>${detail.remarks}</p>
                                <span class="before"></span>
                                <span class="after"></span>
                                <c:if test="${status.index==0}">
                                <i class="mh-icon mh-icon-new"></i>
                                </c:if>
                            </li>
                        </c:forEach>                           
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>