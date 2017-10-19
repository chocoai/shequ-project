<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<html>

<head>
    <title>问卷报表</title>
    <meta name="decorator" content="default" />
    <script src="${ctxStatic}/js/echarts.min.js"></script>
    <script type="text/javascript" src="${ctxStatic}/js/html2canvas.js"></script>
    <script type="text/javascript" src="${ctxStatic}/js/jsPdf.debug.js"></script>
    <style type="text/css">
		.questionaire{
			width: 100%;
		    text-align: center;
		    font-size: -webkit-xxx-large;
		    height: 80px;
    		line-height: 70px;
		}
		.block{
			width: 50%;
    		float: left;
    		margin: 20px auto;
		}
		.title{
			width: 100%;
		    text-align: left;
		    font-size: 20px;
		    margin-top: 25px;
		}
		.content{
			width: 100%;
			height: 400px;
		}
		.type{
			color: blue;
		}
		.button{
			text-align:center
		}
		.print{

		}
		.point{
			text-align: center;
		    background-color: #3398db;
		    width: 20%;
		    margin: 20px 40%;
		    border-radius: 10px;
		    height: 30px;
		    line-height: 30px;
		    font-size: 20px;
		    color: #FFF;
		}
</style>
</head>


<body>
    <ul class="nav nav-tabs">
        <li><a href="${ctx}/sns/wyQuestionnaireRelease/">问卷报表</a></li>
        <li class="active"><a>统计结果</a></li>
    </ul>
    <br/>
    <div class="print" id="print">
	    <div class="questionaire">${wyQuestionnaire.title}</div>
	   	<div class="button">
	   		<input class="btn btn-primary" type="button" value="双行排列" onclick="single(2)"/>
	   		<input class="btn btn-primary" type="button" value="单行排列" onclick="single(1)"/>
	    	<input class="btn btn-primary" type="button" value="导出" onclick="toexport()"/>
	   	</div>
	   	<div class="point">统计得分：${point}%</div>
   		<form id="formid">
   		<input type="hidden" id="questionnaireid" name="questionnaireid" value="${wyQuestionnaire.questionnaireid}"></input>
	    <c:forEach items="${subjects}" var="subjects">
	    <div class="block">
	   		<!-- <div class="title">
	   			第${subjects.sortval}题：${subjects.title}
	   			<c:if test="${subjects.type == 0}"><span class="type">[单选题]</span></c:if>
	   			<c:if test="${subjects.type == 1}"><span class="type">[多选题]</span></c:if>
	   		</div> -->
	    	<div id="${subjects.subjectid}" class="content"></div>
	    	<input type="hidden" id="url${subjects.subjectid}" name="url${subjects.subjectid}"></input>
	   	</div>
	   	</c:forEach>
	   	</form>
   	</div>
   	<!-- <div class="block">
   		 <div class="title">问卷报表问卷报表问卷报表</div>
    	<div id="main" style="width: 600px;height:400px;" class="content"></div>
   	</div>
    <div class="block">
   		 <div class="title">问卷报表问卷报表问卷报表</div>
    	<div id="main1" style="width: 600px;height:400px;" class="content"></div>
   	</div>
   	<div class="block">
   		 <div class="title">问卷报表问卷报表问卷报表</div>
    	<div id="main2" style="width: 600px;height:400px;" class="content"></div>
   	</div>
   	<div class="block">
   		 <div class="title">问卷报表问卷报表问卷报表</div>
    	<div id="main3" style="width: 600px;height:400px;" class="content"></div>
   	</div> -->
    <script type="text/javascript">
    	function single(num){
    		if(num==2){
    			$(".block").css("text-align", "none");
    			$(".block").css("float", "left");
    			$(".title").css("text-align", "left");
    		}
    		if(num==1){
    			$(".block").css("text-align", "center");
    			$(".block").css("float", "none");
    			$(".title").css("text-align", "none");
    		}
    	}
/*
    	function toexport(){
    		$.ajax({
                cache: true,
                type: "POST",
                url: "${ctx}/sns/wyQuestionnaireRelease/toexport",
                data:$('#formid').serialize(),// 你的formid
                async: false,
                error: function(request) {
                    
                },
                success: function(data) {
                   
                }
            });
    	}*/

    	function toexport(){
    		single(1);
    		$(".nav").css("display", "none");
    		$(".button").css("display", "none");
    		$(".print").css("margin", "0 25%");
    		$(".block").css("margin", "20px 0");
			/*$("#formid").css("text-align", "center");
    		$("#formid").css("margin-left", "20%");
    		$("#formid").css("margin-right", "20%");*/
    		//document.body
    		html2canvas(document.getElementById("print"), {
              onrendered:function(canvas) {

                  var contentWidth = canvas.width;
                  var contentHeight = canvas.height;

                  //一页pdf显示html页面生成的canvas高度;
                  var pageHeight = contentWidth / 592.28 * 841.89;
                  //未生成pdf的html页面高度
                  var leftHeight = contentHeight;
                  //pdf页面偏移
                  var position = 0;
                  //a4纸的尺寸[595.28,841.89]，html页面生成的canvas在pdf中图片的宽高
                  var imgWidth = 595.28;
                  var imgHeight = 592.28/contentWidth * contentHeight;

                  var pageData = canvas.toDataURL('image/jpeg', 1.0);

                  var pdf = new jsPDF('', 'pt', 'a4');

                  //有两个高度需要区分，一个是html页面的实际高度，和生成pdf的页面高度(841.89)
                  //当内容未超过pdf一页显示的范围，无需分页
                  if (leftHeight < pageHeight) {
                      pdf.addImage(pageData, 'JPEG', 0, 0, imgWidth, imgHeight );
                  } else {
                      while(leftHeight > 0) {
                          pdf.addImage(pageData, 'JPEG', 0, position, imgWidth, imgHeight)
                          leftHeight -= pageHeight;
                          position -= 841.89;
                          //避免添加空白页
                          if(leftHeight > 0) {
                              pdf.addPage();
                          }
                      }
                  }

                  pdf.save('content.pdf');
              },
              background:"#fff", 
          	});
          	$(".nav").css("display", "");
    		$(".button").css("display", "");
    		$(".print").css("margin", "");
    		$(".block").css("margin", "20px auto");
    	}

    	function draw(id, data1, data2, data3){
    		//alert(data11);
    		//data1 = ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'];
    		//data2 = [10, 52, 200, 334, 390, 330, 220];
    		//app.title = '坐标轴刻度与标签对齐';
		    // 基于准备好的dom，初始化echarts实例
		    var myChart = echarts.init(document.getElementById(id));
		    // 指定图表的配置项和数据
		    var option = {
		    	title: {  
                    //主标题文本，'\n'指定换行  
                    text: data3,  
                    //主标题文本超链接  
                    //link: 'http://www.tqyb.com.cn/weatherLive/climateForecast/2014-01-26/157.html',  
                    //副标题文本，'\n'指定换行  
                    //subtext: 'www.stepday.com',  
                    //副标题文本超链接  
                    //sublink: 'http://www.stepday.com/myblog/?Echarts',  
                    //水平安放位置，默认为左侧，可选为：'center' | 'left' | 'right' | {number}（x坐标，单位px）  
                    x: 'center',  
                    //垂直安放位置，默认为全图顶端，可选为：'top' | 'bottom' | 'center' | {number}（y坐标，单位px）  
                    y: 'top'  
                },  
		        color: ['#3398DB'],
		        tooltip: {
		            trigger: 'axis',
		            axisPointer: { // 坐标轴指示器，坐标轴触发有效
		                type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
		            }
		        },
		        grid: {
		            left: '3%',
		            right: '4%',
		            bottom: '3%',
		            containLabel: true
		        },
		        xAxis: [{
		            type: 'category',
		            data: data1,
		            axisTick: {
		                alignWithLabel: true
		            },
		        }],
		        yAxis: [{
		            type: 'value'
		        }],
		        series: [{
		            name: '直接访问',
		            type: 'bar',
		            barWidth: 60,
		            data: data2
		        }]
		    };

		    // 使用刚指定的配置项和数据显示图表。
		    myChart.setOption(option);

		    var picInfo = myChart.getDataURL();
		    $("#url"+id).val(picInfo);
    	};

    	var subjectids = ${subjectids};
    	var wototalss = ${wototalss};
    	var wotitless = ${wotitless};
    	var titles = ${titles};
    	for(var i=0; i<subjectids.length; i++){
    		//alert("subjects: "+subjects[i]+" wototalss: "+wototalss+" wotitless: "+wotitless);
    		//alert("subjects:"+subjects[i]);
    		//alert("wototalss:"+wototalss[i]);
    		//alert("wotitless:"+wotitless[i]);
    		draw(subjectids[i], wotitless[i], wototalss[i], titles[i]);
    	}
    </script>
</body>

</html>