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
	<script type="text/javascript" src="plugins/ueditor/ueditor.config.js"></script>
   	<!-- 编辑器源码文件 -->
   	<script type="text/javascript" src="plugins/ueditor/ueditor.all.js"></script>
	
	<script type="text/javascript">
	
	</script>
	<style type="text/css">


	</style>
	</head>
	
<body>
	<form action="bg/productEdit.do" name="Form" id="Form" method="post" enctype="multipart/form-data">
		<div id="zhongxin">
			<div style="text-align: center;padding: 20px;">
				<span style="font-size: 20px;">添加商品</span>
			</div>
			
			<table id="table_report" class="table table-striped table-bordered table-hover">
				<tr>
					<th nowrap="nowrap">商品名称:</th>
					<td>
						<input id="name" type="text" name="name" style="width:420px;"/>
					</td>
				</tr>
				<tr>
					<th nowrap="nowrap">兑换积分:</th>
					<td>
						<input id="integrate" type="text" name="integrate" style="width:420px;" onkeyup="checkValue('integrate');"/>
						<div id="integrate-error" style="color: red;font-size: 8px;margin-top: -10px;"></div>
					</td>
				</tr>

				<tr>
                    <th>商品分类:</th>
                    <td>
                        <select id="category.id" name="category.id" style="width:430px;">
                        <c:choose>
                            <c:when test="${not empty categorys}">
                                <c:forEach items="${categorys}" var="var" varStatus="vs">
                                    <option value="${var.id}">${var.categoryName}</option>
                                </c:forEach>
                            </c:when>
                        </c:choose>
                        </select>
                    </td>
                </tr>



				<tr>
					<td style="text-align: center;" colspan="2">
						<a class="btn btn-mini btn-primary" onclick="saveProduct();">保存</a>
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

	<script type="text/javascript">
	$(top.hangge());

	function checkValue(id){
		var value = $("#"+id).val();
		var regex = /^[0-9]+$/;
		if(!regex.test(value)){
			$("#"+id+"-error").html("请输入正确的数值(大于或等于'0'的整数)");
		}else{
			$("#"+id+"-error").html("");
		}
	}

	function saveProduct(){

		var value = $("#integrate").val();
		var regex = /^[0-9]+$/;
		if(regex.test(value)){

			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}else{
			$("#error").html("请输入正确的数值(大于或等于'0'的整数)");
		}
	}

	</script>
</body>
</html>