<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html lang="zh-cn">
<head>
	<title>维修表单</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="keywords" content="">
	<meta name="description" content="">
	<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no">
	<link rel="stylesheet" href="${ctxStatic}/css/repairForm.css">
	<script src="${ctxStatic}/js/repairForm.js"></script>
	<script type="text/javascript">
		var ctx = "${ctx}";
        var ctxStatic = "${ctxStatic}/";
	</script>
</head>
<body>
	<table border = "3px" bordercolor = "0x4C96FF" align = "center">
		<tr>  
			<th align = "center">序号</th> 
			<th align = "center">物料编号</th>  
			<th align = "center">物料名称</th>  
			<th align = "center">规格型号</th>   
		</tr>
		<tr> 
			<td align = "center">1</td>
			<td align = "center">DQ0801</td>  
			<td align = "center">节能灯</td>  
			<td align = "center">15W</td>  
		</tr>
		<tr> 
			<td align = "center">2</td>
			<td align = "center">DQ0801</td>  
			<td align = "center">节能灯</td>  
			<td align = "center">15W</td> 
		</tr>
		<tr> 
			<td align = "center">3</td>
			<td align = "center">DQ0801</td>  
			<td align = "center">节能灯</td>  
			<td align = "center">15W</td>  
		</tr>
		<tr>  
			<th align = "center">序号</th> 
			<th align = "center">数量</th>  
			<th align = "center">单价（元）</th>  
			<th align = "center">合价（元）</th>   
		</tr>
		<tr>  
			<td align = "center">1</td>
			<td align = "center">2</td>  
			<td align = "center">20</td>  
			<td align = "center">40</td>  
		</tr> 
		<tr>  
			<td align = "center">2</td>
			<td align = "center">2</td>  
			<td align = "center">20</td>  
			<td align = "center">40</td>  
		</tr> 
		<tr>  
			<td align = "center">3</td>
			<td align = "center">2</td>  
			<td align = "center">20</td>  
			<td align = "center">40</td>  
		</tr> 
	</table>
</body>