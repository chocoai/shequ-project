<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">  
  <link rel="stylesheet" href="${ctxRoot}/act/rest/diagram-viewer/style.css" type="text/css" media="screen">
  <script src="${ctxRoot}/act/rest/diagram-viewer/js/jstools.js" type="text/javascript" charset="utf-8"></script>
  <script src="${ctxRoot}/act/rest/diagram-viewer/js/raphael.js" type="text/javascript" charset="utf-8"></script>
  
  <script src="${ctxRoot}/act/rest/diagram-viewer/js/jquery/jquery.js" type="text/javascript" charset="utf-8"></script>
  <script src="${ctxRoot}/act/rest/diagram-viewer/js/jquery/jquery.progressbar.js" type="text/javascript" charset="utf-8"></script>
  <script src="${ctxRoot}/act/rest/diagram-viewer/js/jquery/jquery.asyncqueue.js" type="text/javascript" charset="utf-8"></script>
  
  <script src="${ctxRoot}/act/rest/diagram-viewer/js/Color.js" type="text/javascript" charset="utf-8"></script>
  <script src="${ctxRoot}/act/rest/diagram-viewer/js/Polyline.js" type="text/javascript" charset="utf-8"></script>
  <script src="${ctxRoot}/act/rest/diagram-viewer/js/ActivityImpl.js" type="text/javascript" charset="utf-8"></script>
  <script src="${ctxRoot}/act/rest/diagram-viewer/js/ActivitiRest.js" type="text/javascript" charset="utf-8"></script>
  <script src="${ctxRoot}/act/rest/diagram-viewer/js/LineBreakMeasurer.js" type="text/javascript" charset="utf-8"></script>
  <script src="${ctxRoot}/act/rest/diagram-viewer/js/ProcessDiagramGenerator.js" type="text/javascript" charset="utf-8"></script>
  <script src="${ctxRoot}/act/rest/diagram-viewer/js/ProcessDiagramCanvas.js" type="text/javascript" charset="utf-8"></script>
  
  <script src="${ctxStatic}/layer/layer-v1.8.5/layer.min.js" type="text/javascript"></script>
  
  <style type="text/css" media="screen">
    
  </style>
</head>
<body>
<div class="wrapper">
  <div id="overlayBox" >    
    <div id="diagramHolder" class="diagramHolder"></div>
  </div>
</div>
<script type="text/javascript">
var DiagramGenerator = {};
var pb1;
$(document).ready(function(){
  var query_string = {};
  var query = window.location.search.substring(1);
  var vars = query.split("&");
  for (var i=0;i<vars.length;i++) {
    var pair = vars[i].split("=");
    query_string[pair[0]] = pair[1];
  } 
  
  var processDefinitionId = query_string["processDefinitionId"];
  var processInstanceId = query_string["processInstanceId"];
  var titleName = query_string["titleName"];
 
  console.log("Initialize progress bar");
  console.info("processDefinitionId="+processDefinitionId+";processInstanceId="+processInstanceId+";titleName="+titleName);
  
  pb1 = new $.ProgressBar({
    boundingBox: '#pb1',
    label: '加载进度条!',
    on: {
      complete: function() {
        console.log("Progress Bar COMPLETE");
        this.set('label', '完成!');
        if (processInstanceId) {
          ProcessDiagramGenerator.drawHighLights(processInstanceId);
        }
      },
      valueChange: function(e) {
        this.set('label', e.newVal + '%');
      }
    },
    value: 0
  });
  console.log("Progress bar inited");
  
  ProcessDiagramGenerator.options = {
    diagramBreadCrumbsId: "diagramBreadCrumbs",
    diagramHolderId: "diagramHolder",
    diagramInfoId: "diagramInfo",
    on: {
      click: function(canvas, element, contextObject){
        var mouseEvent = this;

		console.info("id=%o,name=%o,type=%o",contextObject.id,contextObject.getProperty("name"),contextObject.getProperty("type"));
		
		if(contextObject.getProperty("type")=="userTask"){
			var titleName2 = "【<font color='red'>"+contextObject.getProperty("name") +"</font>】"+ decodeURIComponent(titleName);
			var activitiId = contextObject.id;
		 	var src =  '${ctx}/sns/wyActCandidate/selectHandler?taskId=' + activitiId + '&procDefId=' + processDefinitionId;				
			
			$.layer({
			  type: 2,
			  shade: [0.9, '#393D49'],
			  fix: false,
			  title: titleName2,
			  maxmin: true,
			  iframe: {src : src},
			  area: [document.documentElement.clientWidth * 0.75 , document.documentElement.clientHeight * 0.75],
			  close: function(index){
			   	// layer.msg('您获得了子窗口标记：' + layer.getChildFrame('#name', index).val(),3,1);
			  }
			});
		}		
		
        if (contextObject.getProperty("type") == "callActivity") {
          var processDefinitonKey = contextObject.getProperty("processDefinitonKey");
          var processDefinitons = contextObject.getProperty("processDefinitons");
          var processDefiniton = processDefinitons[0];
          console.log("Load callActivity '" + processDefiniton.processDefinitionKey + "', contextObject: ", contextObject);

          ProcessDiagramGenerator.drawDiagram(processDefiniton.processDefinitionId);
        }
      },
      rightClick: function(canvas, element, contextObject){
     	 alert("rightClick");
        var mouseEvent = this;
        console.log("[RIGHTCLICK] mouseEvent: %o, canvas: %o, clicked element: %o, contextObject: %o", mouseEvent, canvas, element, contextObject);
      },
      over: function(canvas, element, contextObject){
        var mouseEvent = this;       
      },
      out: function(canvas, element, contextObject){
        var mouseEvent = this;     
        ProcessDiagramGenerator.hideInfo();
      }
    }
  };
  
  
  var baseUrl = window.document.location.protocol + "//" + window.document.location.host + "/";
  var shortenedUrl = window.document.location.href.replace(baseUrl, "");
  baseUrl = baseUrl + shortenedUrl.substring(0, shortenedUrl.indexOf("/"));
  
  ActivitiRest.options = {
    processInstanceHighLightsUrl: baseUrl + "/act/rest/service/process-instance/{processInstanceId}/highlights?callback=?",
    processDefinitionUrl: baseUrl + "/act/rest/service/process-definition/{processDefinitionId}/diagram-layout?callback=?",
    processDefinitionByKeyUrl: baseUrl + "/act/rest/service/process-definition/{processDefinitionKey}/diagram-layout?callback=?",
    processInstanceUrl: baseUrl + "/act/rest/service/process-instance/{processInstanceId}/diagram-layout?callback=?"
  };
  
  if (processDefinitionId) {
    ProcessDiagramGenerator.drawDiagram(processDefinitionId);
    
  } else {
    alert("processDefinitionId parameter is required");
  }
  
  //绑定整个页面的esc时间,按esc键,进行退出操作
  $(document).keyup(function(event){
  	 if(event.keyCode==27){
  	 	closeLayer();
  	 }	 
  });
  
});

	//关闭弹出层
	function closeLayer(){		
		 var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		 parent.layer.close(index);		
	}


</script>
</body>
</html>