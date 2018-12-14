<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<script type="text/javascript" src="plugins/zoomimage/js/jquery.js"></script>
<!-- jsp文件头和头部 -->
<%@ include file="../system/admin/top.jsp"%>

<!--查看图片插件 -->
<link rel="stylesheet" media="screen" type="text/css" href="plugins/zoomimage/css/zoomimage.css" />
<link rel="stylesheet" media="screen" type="text/css" href="plugins/zoomimage/css/custom.css" />
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
					<form action="bg/couponExchangeList.do" method="post" name="Form" id="Form">
						<table>
							<tr>
								<td>
                                    <select name="type" style="width:120px;">
                                        <option  selected="selected" value="1">手机充值</option>
                                        <option  selected="selected" value="2">加油卡充值</option>
                                    </select>
                                        <input class="date-picker" name="begin" id="begin" value="${begin}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width: 75px;" placeholder="开始日期" /> -
                                        <input class="date-picker" name="end" id="end" value="${end}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width: 75px;" placeholder="结束日期" />
								</td>
								<td style="vertical-align: top;">
								    <button class="btn btn-mini btn-light" onclick="search();" title="检索">
										<i id="nav-search-icon" class="icon-search"></i>
									</button>
								</td>
							</tr>
						</table>
						<!-- 检索  -->
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<thead>
								<tr>
									<th>姓名</th>
									<th>充值类型</th>
									<th>手机/加油卡</th>
									<th>充值金额(元)</th>
									<th>兑换积分</th>
									<th>兑换状态</th>
									<th>兑换时间</th>
									<th class="center">操作</th>
								</tr>
							</thead>
							<tbody>

								<!-- 开始循环 -->
								<c:choose>
									<c:when test="${not empty varList}">
										<c:forEach items="${varList}" var="var" varStatus="vs">
											<tr>
												<td style="width: 15%;">${var.nickName}</td>
												<td style="width: 10%;">
                                                    <c:if test="${var.type==1}">
                                                        手机充值
                                                    </c:if>
                                                    <c:if test="${var.type==2}">
                                                        加油卡充值
                                                    </c:if>
												</td>
												<td style="width: 15%;">${var.account}</td>
												<td style="width: 10%;">${var.money}</td>
												<td style="width: 10%;">${var.integrate}</td>
												<td style="width: 10%;">
													<c:if test="${var.status==0}">
														<font color="red"> </font>
													</c:if>
													<c:if test="${var.status==1}">
														<font color="green">处理中</font>
													</c:if>
													<c:if test="${var.status==2}">
														<font color="red">已兑换</font>
													</c:if>
												</td>
												<td style="width: 10%;">
												    <jsp:setProperty name="dateValue" property="time" value="${var.createTime}"/>
                                                    <fmt:formatDate value="${dateValue}" pattern="yyyy-MM-dd HH:mm:ss"/>
												</td>
												<td style="width: 5%;" class="center">
												  <a style="cursor: pointer;" title="编辑-修改状态" onclick="edit('${var.id}');" class="tooltip-success"
												  		data-rel="tooltip" title="" data-placement="left">
														<span class="green"><i class="icon-edit"></i></span>
												  </a>&nbsp;
												</td>
											</tr>
										</c:forEach>
										<c:if test="${QX.cha == 0 }">
											<tr>
												<td colspan="100" class="center">您无权查看</td>
											</tr>
										</c:if>
									</c:when>
									<c:otherwise>
										<tr class="main_info">
											<td colspan="100" class="center">没有相关数据</td>
										</tr>
									</c:otherwise>
								</c:choose>
							</tbody>
						</table>

						<div class="page-header position-relative">
							<table style="width: 100%;">
								<tr>
									<td style="vertical-align: top;"></td>
									<td style="vertical-align: top;"><div class="pagination"
											style="float: right; padding-top: 0px; margin-top: 0px;">${page.pageStr}</div></td>
								</tr>
							</table>
						</div>
					</form>
				</div>
				<!-- PAGE CONTENT ENDS HERE -->
			</div>
			<!--/row-->

		</div>
		<!--/#page-content-->
	</div>
	<!--/.fluid-container#main-container-->


	<!-- 引入 -->
	<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
	<script src="static/js/bootstrap.min.js"></script>
	<script src="static/js/ace-elements.min.js"></script>
	<script src="static/js/ace.min.js"></script>
	<!-- 引入 -->
	<script type="text/javascript"
		src="static/js/bootstrap-datepicker.min.js"></script>
	<!-- 日期框 -->


	<script type="text/javascript">
		
		$(top.hangge());
		//检索
		function search(){
			top.jzts();
			$("#Form").submit();
		}
		
		$(function() { //日期框
			$('.date-picker').datepicker();
		});

		function edit(id){
			//修改
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="编辑";
			 diag.URL = "<%=basePath%>bg/couponExchangeToEdit.do?id="+id+"&tm="+new Date().getTime();
			 diag.Width = 600;
			 diag.Height = 460;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 nextPage(${page.currentPage});
				}
				diag.close();
			 };
			 diag.show();			
		}
		
		</script>
</body>
</html>

