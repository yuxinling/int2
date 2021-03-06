<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<base href="<%=basePath%>">
		
		<meta charset="utf-8" />
		<title></title>
		<meta name="description" content="overview & stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link href="static/css/bootstrap.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="static/css/ace.min.css" />
		<link rel="stylesheet" href="static/css/ace-skins.min.css" />
		<link rel="stylesheet" href="static/assets/css/font-awesome.css" />
		<!-- ace styles -->
		<link rel="stylesheet" href="static/assets/css/ace.css" class="ace-main-stylesheet" id="main-ace-style" />

		<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
		<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		
<script type="text/javascript">
	//保存
	function save(){
		$("#Form").submit();
	}
	
</script>
	</head>
<body>
	<form action="bg/couponExchangeUpdate.do" name="Form" id="Form" method="post" enctype="multipart/form-data">
		<div id="zhongxin">
		<table id="table_report" class="table table-striped table-bordered table-hover">
			<tr>
				<th colspan="2" style="text-align: center;">
                 <c:choose>
                     <c:when test="${couponExchange.type == 1}">
                        手机充值
                     </c:when>
                     <c:when test="${couponExchange.type == 2}">
                        加油卡充值
                     </c:when>
                     <c:otherwise>
                        充值订单
                     </c:otherwise>
                 </c:choose>
				<input type="hidden" name="id" value="${couponExchange.id}">
				</th>
			</tr>
			<tr>
				<th>姓名:</th>
				<td>${couponExchange.nickName}</td>
			</tr>
			<tr>
				<th>
                     <c:choose>
                         <c:when test="${couponExchange.type == 1}">
                            手机号:
                         </c:when>
                         <c:when test="${couponExchange.type == 2}">
                            加油卡号:
                         </c:when>
                         <c:otherwise>
                            充值订单
                         </c:otherwise>
                     </c:choose>
				</th>
				<td>${couponExchange.account}</td>
			</tr>
			<tr>
				<th>充值金额(元):</th>
				<td>${couponExchange.money}</td>
			</tr>
			<tr>
				<th>兑换积分:</th>
				<td>${couponExchange.integrate}</td>
			</tr>
			<tr>
				<th>充值状态:</th>
				<td>
					<select name="status">
				 		<option <c:if test="${status==1}"> selected="selected" </c:if> value="1">处理中</option>
				 		<option <c:if test="${status==2}"> selected="selected" </c:if> value="2">已兑换</option>
					</select>
				</td>
			</tr>
			<tr>
				<td style="text-align: center;" colspan="2">
					<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
					<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
				</td>
			</tr>
		</table>
		</div>
		
		<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
		
	</form>
	
	
		<!-- 引入 -->
		<!--[if !IE]> -->
		<script type="text/javascript">
			window.jQuery || document.write("<script src='static/assets/js/jquery.js'>"+"<"+"/script>");
		</script>
		<!-- <![endif]-->
		<!--[if IE]>
		<script type="text/javascript">
		 	window.jQuery || document.write("<script src='static/assets/js/jquery1x.js'>"+"<"+"/script>");
		</script>
		<![endif]-->
		<script src="static/js/bootstrap.min.js"></script>
		<!-- ace scripts -->
		<script src="static/assets/js/ace/elements.fileinput.js"></script>
		<script src="static/assets/js/ace/ace.js"></script>
		<script type="text/javascript">
		$(top.hangge());
		</script>
</body>
</html>