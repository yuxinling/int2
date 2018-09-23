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

	<link rel="stylesheet" type="text/css" href="plugins/imguploadify/base.css">
	<link rel="stylesheet" type="text/css" href="plugins/imguploadify/home.css">

	<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<script type="text/javascript" src="plugins/ueditor/ueditor.config.js"></script>
	<!-- 编辑器源码文件 -->
	<script type="text/javascript" src="plugins/ueditor/ueditor.all.js"></script>

	<script type="text/javascript">

	</script>
	<style type="text/css">

		.table th, .table td {
			line-height: 20px;
			text-align: left;
			vertical-align: top;
			padding: 8px;
			border-top: 0px solid rgb(221, 221, 221);
		}
		.aui-form-group {
			padding-top: 0px;
		}

		[class*="span"] {
			float: left;
			min-height: 1px;
			margin-left: 0px;
		}

	</style>
</head>

<body>
<form action="bg/productEdit.do" name="Form" id="Form" method="post" enctype="multipart/form-data">
	<input type="hidden" name="id" id="id" value="${product.id}"/>
	<div id="zhongxin">
		<div style="text-align: center;padding: 20px;">
			<span style="font-size: 20px;">商品设置</span>
		</div>

		<table id="table_report" class="table">
			<tr>
				<th nowrap="nowrap" style="text-align: right;width: 15%;">商品名称:</th>
				<td>
					<input id="title" type="text" name="name" value="${product.name}" style="width: 99%;"/>
				</td>
				<th nowrap="nowrap" style="text-align: right;width: 15%;"></th>
			</tr>
			<tr>
				<th nowrap="nowrap" style="text-align: right;width: 15%;">列表图片:</th>
				<td>
					<div class="aui-form-group clear">
						<div class="aui-form-input">
							<div class="aui-content-img-box aui-content-full">
								<div class="aui-photo aui-up-img clear">
									<c:if test="${not empty product.thumbnail}">
										<section class="aui-up-section fl">
											<span class="aui-up-span"></span>
											<img class="aui-close-up-img" src="plugins/imguploadify/close.png">
											<img class="aui-to-up-img" src="${product.thumbnail}">
											<p class="img-aui-img-name-p"></p>
											<input type="hidden" name="thumbnail" value="${product.thumbnail}">
										</section>
									</c:if>


									<section class="aui-file-up fl"  <c:if test="${not empty product.thumbnail}">    style="display: none;" </c:if>   >
										<img src="plugins/imguploadify/up.png" class="add-img">
										<input type="file" name="imageUp" id="thumbnail" class="file" value="" accept="image/jpg,image/jpeg,image/png,image/bmp" multiple="">
									</section>
								</div>
							</div>
						</div>
					</div>
				</td>
				<th nowrap="nowrap" style="text-align: right;width: 15%;"></th>
			</tr>
			<tr>
				<th nowrap="nowrap" style="text-align: right;width: 15%;">兑换积分:</th>
				<td>
					<input id="dateTime" type="text" name="integrate" value="${product.integrate}" style="width: 99%;"/>
				</td>
				<th nowrap="nowrap" style="text-align: right;width: 15%;"></th>
			</tr>
			<tr>
				<th nowrap="nowrap" style="text-align: right;width: 15%;">商品图片:</th>
				<td>
					<div class="aui-form-group clear">
						<div class="aui-form-input">
							<div class="aui-content-img-box aui-content-full">
								<div class="aui-photo aui-up-img clear">
									<c:if test="${not empty product.images}">
										<c:forEach items="${product.images}" var="var" varStatus="vs">
											<section class="aui-up-section fl">
												<span class="aui-up-span"></span>
												<img class="aui-close-up-img" src="plugins/imguploadify/close.png">
												<img class="aui-to-up-img" src="${var.src}">
												<p class="img-aui-img-name-p"></p>
												<input type="hidden" name="imageId" value="${var.id}">
											</section>
										</c:forEach>
									</c:if>

									<section class="aui-file-up fl">
										<img src="plugins/imguploadify/up.png" class="add-img">
										<input type="file" name="imageUp" id="imageUp" class="file" value="" accept="image/jpg,image/jpeg,image/png,image/bmp" multiple="false">
									</section>
								</div>
							</div>
						</div>
					</div>
				</td>
				<th nowrap="nowrap" style="text-align: right;width: 15%;"></th>
			</tr>
			<tr>
				<th nowrap="nowrap" style="text-align: right;width: 15%;">商品详情:</th>
				<td >
					<input type="hidden" name="detail" id="detail" />
					<script id="container" name="content" type="text/plain" >${product.detail}</script>
				</td>
				<th nowrap="nowrap" style="text-align: right;width: 15%;"></th>
			</tr>
			<tr>
				<td style="text-align: center;" colspan="3">
					<a class="btn btn-mini btn-primary" onclick="saveProduct();">保存</a>
					<a class="btn btn-mini btn-danger" onclick="cancelArticle();">取消</a>
				</td>
			</tr>
		</table>
	</div>
	<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>


</form>
<!-- mask begin -->
<div class="aui-mask aui-works-mask">
	<div class="aui-mask-content">
		<p class="aui-delete-text">确定要删除你上传的资料？</p>
		<p class="aui-check-text">
			<span class="aui-delete aui-accept-ok">确定</span>
			<span class="aui-accept-no">取消</span>
		</p>
	</div>
</div>
<!-- mask end -->


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
	var ue = UE.getEditor('container', {
		initialFrameWidth: null,//设置为null即可
		initialFrameHeight: 500
	});
	function saveProduct(){
		var detail = ue.getContent();
		$("#detail").val(detail);
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}




	$(function() {
		var imageCount =5;
		var delParent;
		var defaults = {
			fileType: ["jpg", "png", "bmp", "jpeg"],// 上传图片支持的格式
			fileSize: 1024 * 1024 * 10 // 上传的图片大小不得超过 10M
		};
		/*点击图片*/
		$(".file").change(function() {
			var idFile = $(this).attr("id");
			if(idFile == 'thumbnail') imageCount = 1;

			var file = document.getElementById(idFile);
			var imgContainer = $(this).parents(".aui-photo");
			//存放图片的父元素
			var fileList = file.files;
			//获取的图片文件
			var input = $(this).parent();
			//文本框的父亲元素
			var imgArr = [];
			//遍历得到的图片
			var numUp = imgContainer.find(".aui-up-section").length;
			var totalNum = numUp + fileList.length;
			//图片总的数量可自定义
			if (fileList.length > imageCount || totalNum > imageCount) {
				alert("你好！上传图片不得超过"+imageCount+"张，请重新选择");
				//一次选择上传超过3个  自己定义
			} else if (numUp <= imageCount) {
				fileList = validateUp(fileList);
				for (var i = 0; i < fileList.length; i++) {
					var imgUrl = window.URL.createObjectURL(fileList[i]);
					imgArr.push(imgUrl);
					var $section = $("<section class='aui-up-section fl loading'>");
					imgContainer.prepend($section);
					var $span = $("<span class='aui-up-span'>");
					$span.appendTo($section);

					var $img0 = $("<img class='aui-close-up-img'>").on("click", function(event) {
						event.preventDefault();
						event.stopPropagation();
						$(".aui-works-mask").show();
						delParent = $(this).parent();
					});
					$img0.attr("src", "plugins/imguploadify/close.png").appendTo($section);
					var $img = $("<img class='aui-to-up-img aui-up-clarity'>");
					$img.attr("src", imgArr[i]);
					$img.appendTo($section);
					var $p = $("<p class='img-aui-img-name-p'>");
					$p.html(fileList[i].name).appendTo($section);

				}

				uploadImage(idFile);
			}
			setTimeout(function() {
				$(".aui-up-section").removeClass("loading");
				$(".aui-to-up-img").removeClass("aui-up-clarity");
			}, 100);


			numUp = imgContainer.find(".aui-up-section").length;
			if (numUp >= imageCount) {
				$(this).parent().hide();
			}

			$(this).val("");
		});

		$(".aui-photo").delegate(".aui-close-up-img", "click", function() {
			$(".aui-works-mask").show();
			delParent = $(this).parent();
		});

		$(".aui-accept-ok").click(function() {
			var imageId = delParent.find("input[name='imageId']").val();
			if(imageId){
				deleteImage(imageId,delParent,imageCount)

			}else{
				deleteImageThumbnail(delParent)
			}

		});

		$(".aui-accept-no").click(function() {
			$(".aui-works-mask").hide();
		});

		function validateUp(files) {
			var arrFiles = [];
			//替换的文件数组
			for (var i = 0, file; file = files[i]; i++) {
				//获取图片上传的后缀名
				var newStr = file.name.split("").reverse().join("");
				if (newStr.split(".")[0] != null) {
					var type = newStr.split(".")[0].split("").reverse().join("");
					console.log(type + "===type===");
					if (jQuery.inArray(type, defaults.fileType) > -1) {
						// 符合图片格式，可以上传
						if (file.size >= defaults.fileSize) {
							alert(file.size);
							alert('您这个"' + file.name + '"文件大小过大');
						} else {
							arrFiles.push(file);
						}
					} else {
						alert('您这个"' + file.name + '"上传类型不符合');
					}
				} else {
					alert('您这个"' + file.name + '"没有类型, 无法识别');
				}
			}
			return arrFiles;
		}

	});

	function uploadImage(idFile) {

		var formData = new FormData();
		formData.append("image", document.getElementById(idFile).files[0]);
		formData.append("rid", $("#id").val());
		var url = "bg/images/upload.do";
		if(idFile == 'thumbnail') {
			url = "bg/images/thumbnailUpload.do";
		}
		$.ajax({
			url: url,
			type: "POST",
			data: formData,
			contentType: false,
			processData: false,
			success: function (data) {
				location.reload();
			},
			error: function () {

			}
		});
	};

	function deleteImage(id,delParent,imageCount){
		$(".aui-works-mask").hide();
		$.ajax({
			url:"<%=basePath%>bg/imageDelete.do?id="+id+"&tm="+new Date().getTime(),
			data: {},
			type:'post',
			dataType:'json',
			success:function(data) {
				if(data.code == 200){
					var numUp = delParent.siblings().length;
					if (numUp < imageCount) {
						delParent.parent().find(".aui-file-up").show();
					}
					delParent.remove();

				}
			},
			error : function() {

			}
		});
	}
	function deleteImageThumbnail(delParent){
		$(".aui-works-mask").hide();
		$.ajax({
			url:"<%=basePath%>bg/images/thumbnailDelete.do?rid=2&tm="+new Date().getTime(),
			data: {},
			type:'post',
			dataType:'json',
			success:function(data) {
				if(data.code == 200){

					delParent.parent().find(".aui-file-up").show();
					delParent.remove();

				}
			},
			error : function() {

			}
		});
	}

</script>
</body>
</html>