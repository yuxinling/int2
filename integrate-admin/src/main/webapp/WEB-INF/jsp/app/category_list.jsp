<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:useBean id="dateValue" class="java.util.Date"/>

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
        <div style="width: 100%;text-align: right;margin-bottom: 10px;">
            <a class="btn btn-mini btn-info" onclick="addCategory();" style="margin-right: 50px;"><i>添加分类</i></a>
        </div>
        <table id="table_report" class="table table-striped table-bordered table-hover">
            <thead>
                <tr>
                    <th  class="center" >序号</th>
                    <th  class="center" >分类名称</th>
                    <th class="center">操作</th>
                </tr>
            </thead>
            <tbody>
            <!-- 开始循环 -->
            <c:choose>
                <c:when test="${not empty categorys}">
                    <c:forEach items="${categorys}" var="var" varStatus="vs">
                        <tr>
                            <td class="center" style="width: 20%;">${vs.index + 1}</td>
                            <td class="center" style="width: 50%;" >${var.categoryName}</td>
                            <td class="center" style="width: 30%;" >
                                <a style="cursor: pointer;" title="编辑" onclick="editCategory('${var.id}')"
                                   class="tooltip-success" data-rel="tooltip" title="" data-placement="left">
                                    <span class="green"><i class="icon-edit"></i></span>
                                </a>

                                <a style="cursor:pointer;" title="删除" onclick="deleteCategory('${var.id}');" class="tooltip-success" data-rel="tooltip" title="" data-placement="left"><span class="red"><i class="icon-remove"></i></span></a>
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
		function addCategory(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="添加分类";
			 diag.URL = "<%=basePath%>bg/categoryToEdit.do?tm="+new Date().getTime();
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

        function editCategory(id){
             top.jzts();
             var diag = new top.Dialog();
             diag.Drag=true;
             diag.Title ="添加商品";
             diag.URL = "<%=basePath%>bg/categoryToEdit.do?id="+id+"&tm="+new Date().getTime();
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

		function deleteCategory(id){
			if(confirm("确定要删除？")){
				$.ajax({
					url:"<%=basePath%>bg/categoryDelete.do?id="+id+"&tm="+new Date().getTime(),
					data: {},
					type:'post',
					dataType:'json',
					success:function(data) {
						if(data.code == 200){
							location.reload();
						}
					},
					error : function() {}
				});
			}
		}


		</script>
	</body>
</html>

