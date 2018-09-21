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
	<!-- jsp文件头和头部 -->
	<%@ include file="../system/admin/top.jsp"%>
	<!--查看图片插件 -->
	<link rel="stylesheet" media="screen" type="text/css" href="plugins/zoomimage/css/zoomimage.css" />
    <link rel="stylesheet" media="screen" type="text/css" href="plugins/zoomimage/css/custom.css" />
    <script type="text/javascript" src="plugins/zoomimage/js/jquery.js"></script>
    <script type="text/javascript" src="plugins/zoomimage/js/eye.js"></script>
    <script type="text/javascript" src="plugins/zoomimage/js/utils.js"></script>
    <script type="text/javascript" src="plugins/zoomimage/js/zoomimage.js"></script>
    <script type="text/javascript" src="plugins/zoomimage/js/layout.js"></script>
	<!--查看图片插件 -->
	</head>
<body>
<div class="container-fluid" id="main-container">
<div id="page-content" class="clearfix">
  <div class="row-fluid">
	<div class="row-fluid">
			<!-- 检索  -->
			<form action="bg/userList.do" method="post" name="Form" id="Form">
			<!-- 检索  -->
			<div style="width: 100%;text-align: right;margin-bottom: 10px;">
				<a class="btn btn-mini btn-info" onclick="addHotKey();" style="margin-right: 50px;"><i>添加关键字</i></a>
			</div>
			<table id="table_report" class="table table-striped table-bordered table-hover">
				<thead>
					<tr>
						<th>序号</th>
						<th>热门关键字</th>
						<th class="center">操作</th>
					</tr>
				</thead>
				<tbody>
				<!-- 开始循环 -->
				<c:choose>
					<c:when test="${not empty hotKeys}">
						<c:forEach items="${hotKeys}" var="var" varStatus="vs">
							<tr>
								<td class='center' style="width: 20%;">${var.id}</td>
								<td class='center' style="width: 50%;">${var.keyWord}</td>
								<td class='center' style="width: 30%;" >
									<a style="cursor: pointer;" title="编辑" onclick="editHotKey('${var.id}')" class="tooltip-success" data-rel="tooltip" title="" data-placement="left"> <span class="green"><i class="icon-edit"></i></span> </a>
									<a style="cursor: pointer;" title="删除" onclick="deleteHotKey('${var.id}');" class="tooltip-success" data-rel="tooltip" title="" data-placement="left"><span class="red"><i class="icon-remove"></i></span></a>
								</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr class="main_info">
							<td colspan="100" class="center" >没有相关数据</td>
						</tr>
					</c:otherwise>
				</c:choose>
					
				
				</tbody>
			</table>
			
		<div class="page-header position-relative">
		<table style="width:100%;">
			<tr>
				<td style="vertical-align:top;">
				</td>
				<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
			</tr>
		</table>
		</div>
		</form>
	</div>
	<!-- PAGE CONTENT ENDS HERE -->
  </div><!--/row-->
	
</div><!--/#page-content-->
</div><!--/.fluid-container#main-container-->
		<!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
		<!-- 引入 -->
		<script type="text/javascript">
		
		$(top.hangge());
		//检索
		function search(){
			top.jzts();
			$("Form").submit();
		}
		
		function addHotKey(){
			 
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="添加商品";
			 diag.URL = "<%=basePath%>bg/hotKeyToEdit.do?tm="+new Date().getTime();
			 diag.Width = 500;
			 diag.Height = 300;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 location.reload();
				}
				diag.close();
			 };
			 diag.show();			
		}
		function editHotKey(id){

			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="添加商品";
			 diag.URL = "<%=basePath%>bg/hotKeyToEdit.do?id="+id+"&tm="+new Date().getTime();
			 diag.Width = 500;
			 diag.Height = 300;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 location.reload();
				}
				diag.close();
			 };
			 diag.show();
		}

		function deleteHotKey(id) {
			if (confirm("确定要删除？")) {
				top.jzts();
				var url = "<%=basePath%>bg/hotKeyDelete.do?id=" + id + "&tm=" + new Date().getTime();
				$.get(url, function (data) {
					location.reload();
				});
			}
		}



		</script>
	</body>
</html>

