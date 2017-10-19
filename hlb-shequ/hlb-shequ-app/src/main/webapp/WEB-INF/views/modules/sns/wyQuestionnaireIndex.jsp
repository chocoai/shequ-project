<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>公司简介</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>
	<style type="text/css">
		.ztree {overflow:auto;margin:0;_margin-top:10px;padding:10px 0 0 10px;}
	</style>
</head>
<body>
	<sys:message content="${message}"/>
	<div id="content" class="row-fluid">
		<div id="left" class="accordion-group">
			<div class="accordion-heading">
		    	<a class="accordion-toggle">组织机构<i class="icon-refresh pull-right" onclick="refreshTree();"></i></a>
		    </div>
			<div id="ztree" class="ztree"></div>
		</div>
		<div id="openClose" class="close">&nbsp;</div>
		<div id="right">
			<iframe id="officeContent" src="${ctx}/sns/wyQuestionnaire/list?refresh=refresh" width="100%" height="91%" frameborder="0"></iframe>
		</div>
	</div>
	<script type="text/javascript">
		var setting = {
			data:{simpleData:{enable:true,idKey:"id",pIdKey:"pId",rootPId:'-1'}},
			callback:{onClick:function(event, treeId, treeNode){			
					/*if(treeNode.isParent){
						//top.$.jBox.alert("该节点还有子节点，请选择子节点。");
						return;
					}*/
					var rootNode=getRoot();
					var source=rootNode.urlkey;

					//点击的时候,设置iframe中,请求的地址,加上参数,查询对应的组织结构的用户
					var id = treeNode.id == '0' ? '' :treeNode.id;
					$('#officeContent').attr("src","${ctx}/sns/wyQuestionnaire/list?gid="+id+"&name="+treeNode.name+"&refresh=refresh");
				}
			},
			//获取json数据  
	        async : {    
	            enable : true,   
	            url : "${ctx}/sys/office/subTree1", // Ajax 获取数据的 URL 地址    
	            autoParam : [ "id", "name", "pId","type","urlkey"], //ajax提交的时候，传的是id值  
	            /* otherParam: ["pId","type",function(){ 
	             		return "1";
	                    // return window.opener.document.getElementById("contactid").value;  
	           	}]  */ 
	        }	        
		};
		
		
		
		var ztree;
		function refreshTree(){
			$.getJSON("${ctx}/sys/office/treeData32",function(data){
				ztree = $.fn.zTree.init($("#ztree"), setting, data);
			});
		}
		refreshTree();
		//获取根节点
		function getRoot() {		    
		    //返回一个根节点
		   var node = ztree.getNodesByFilter(function (node) { return node.level == 0; }, true);		   
		   return node;
		}
		
		
		 
		var leftWidth = 280; // 左侧窗口大小
		var htmlObj = $("html"), mainObj = $("#main");
		var frameObj = $("#left, #openClose, #right, #right iframe");
		function wSize(){
			var strs = getWindowSize().toString().split(",");
			htmlObj.css({"overflow-x":"hidden", "overflow-y":"hidden"});
			mainObj.css("width","auto");
			frameObj.height(strs[0] - 5);
			var leftWidth = ($("#left").width() < 0 ? 0 : $("#left").width());
			$("#right").width($("#content").width()- leftWidth - $("#openClose").width() -5);
			$(".ztree").width(leftWidth - 10).height(frameObj.height() - 46);
		}
	</script>
	<script src="${ctxStatic}/common/wsize.min.js" type="text/javascript"></script>
</body>
</html>