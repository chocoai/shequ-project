<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>${wyQuestionnaire.title}</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="keywords" content="">
		<meta name="description" content="">
		<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no">
		<link rel="stylesheet" href="${ctxStatic}/css/weui.min.css">
		<link rel="stylesheet" href="${ctxStatic}/css/myweui.css">
		<script type="text/javascript" src="${ctxStatic}/js/jquery.js"></script>
		<style type="text/css">
			body{margin: 0 auto;}
			.top{
				width: 100%;
			    height: 40px;
			    background-color: #09bb07;
			    text-align: center;
			    line-height: 40px;
			    font-size: 20px;
			    color: #FFF;
			    z-index: 99;
			}
			.back{
				width: 10%;
				height: 40px;
				z-index: 100;
				position: absolute;
				background-image: url(${ctxStatic}/image/back2.png);
				background-repeat: no-repeat;
				background-size: 100% 100%;
			}
			.explain{
				width: 90%;
			    margin: 20px 5% 20px 5%;
			    padding-bottom: 12px;
			    font-size: 14px;
			    border-bottom: 1px solid #e2e2e2;
			}
			.subject{
				width: 90%;
			    margin: 0 0 7px 5%;
			    font-size: 15px;
			    font-weight: 600;
			}
			.options{
			    margin-bottom: 25px;
			}
			.fit{
				width: 90%;
			    margin-left: 5%;
				border-bottom: 1px solid #e2e2e2;
			}
			.option{
				width: 90%;
			    margin-left: 5%;
			    line-height: 30px;
    			margin-left: 5%;
			    border-left: 1px solid #e2e2e2;
			    border-right: 1px solid #e2e2e2;
			    border-bottom: 1px solid #e2e2e2;
			}
			.fillblank{
				width: 90%;
			    margin-left: 5%;
			    font-size: 15px
			}
			.submit{
				height: 43px;
			    line-height: 43px;
			    width: 90%;
			    margin-left: 5%;
			    background-color: #09bb07;
			    text-align: center;
			    font-size: 1.2em;
			    color: #FFF;
			    border-radius: .2em;
			    margin-bottom: 60px;
			}
		</style>
	</head>

	<body>

		<!-- <div class="back" onClick="window.location.href='javascript:history.back()'"></div>
		<div class="top">${wyQuestionnaire.title}</div> -->
		<div class="weui-cells weui-title weui-title-green" onclick="javascript:history.go(-1)">
            <a class="weui-cell weui-cell_access" href="javascript:;">
                <div class="weui-cell__ft1">
                </div>
                <div class="weui-cell__bd">
                    <p>${wyQuestionnaire.title}</p>
                </div>
            </a>
        </div>
		<div class="explain">${wyQuestionnaire.explain}</div>
		<form action="${ctx}/questionary/questionarySubmit" method="post" name="form1" id="form1">
			<input type="hidden" name="wyQuestionnaireid" value="${wyQuestionnaire.questionnaireid}">
			<c:forEach items="${list}" var="list">
			<div class="subject id${list.subject.subjectid}">${list.subject.sortval}.${list.subject.title}
				<c:if test="${list.subject.notnull == 1}">
				<span style="color: red">*</span>
				<span style="display: none" class="btx${list.subject.subjectid}" >(必填项)</span>
				</c:if>
			</div>

			<c:if test="${list.subject.notnull == 1}">
			<div class="options request" id="${list.subject.subjectid}">
			</c:if>
			<c:if test="${list.subject.notnull == 0}">
			<div class="options">
			</c:if>
			<c:if test="${list.subject.type == 0}">
				<div class="fit"></div>
				<c:forEach items="${list.optionList}" var="optionList">
				<div class="option danx">
					<input type="radio" name="subject${list.subject.subjectid}" value="${optionList.optionid}">${optionList.content}</input>
				</div>
				</c:forEach>
			</div>
			</c:if>
			<c:if test="${list.subject.type == 1}">
				<div class="fit"></div>
				<c:forEach items="${list.optionList}" var="optionList">
				<div class="option duox">
					<input type="checkbox" name="subject${list.subject.subjectid}" value="${optionList.optionid}">${optionList.content}</input>
				</div>
				</c:forEach>
			</div>
			</c:if>
			<c:if test="${list.subject.type == 2}">
				<textarea rows="2" class="fillblank" name="subject${list.subject.subjectid}"></textarea>
			</div>
			</c:if>
			</c:forEach>
		</form>

		<div type="button" class="submit" onclick="submit()">提交</div>
	</body>

	<script type="text/javascript">
		$(document).ready(function(){
			var status = "${status}";
			if(status=="0"){
				$(":input").attr("disabled", "");
				$(".fillblank").attr("readonly", "readonly");
				$(".submit").css("display", "none");
			}
		});
		
		function submit(){ 
			var id = "";
			var flag = 0;
			$(".request").each(function(){
				if($(this).children(".danx").length>0 && $(this).find('input[type="radio"]:checked').val()==undefined){
					id = $(this).attr("id");
				}else if($(this).children(".duox").length>0 && $(this).find('input[type="checkbox"]:checked').val()==undefined){
					id = $(this).attr("id");
					/*$(this).find('input[type="checkbox"]:checked').each(function(){
						alert($(this).val());
					});*/
				}else if($(this).children(".fillblank").length>0 && $(this).find("textarea").val()==""){
					id = $(this).attr("id");
				}
				if(id != ""){
					flag = 1;
					$("html, body").animate({scrollTop: $("#"+id).offset().top-$("#"+id).height() }, {duration: 500,easing: "swing"});
					$(".id"+id).css("color", "red");
					$(".btx"+id).css("display", "");
					return false;
				}	
			});
			if(flag == 0){
				$("#form1").submit();
			}
		}
	</script>