<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<!-- jsp文件头和头部 -->
<link rel="stylesheet" href="plugins/nivo-slider/nivo-slider.css" type="text/css" media="screen" />
<link href="static/css/bootstrap.min.css" rel="stylesheet" />
<link rel="stylesheet" href="static/css/ace.min.css" />
<link rel="stylesheet" href="static/css/ace-skins.min.css" />
<link rel="stylesheet" href="static/assets/css/font-awesome.css" />

<link rel="stylesheet" type="text/css" href="plugins/imguploadify/base.css">
<link rel="stylesheet" type="text/css" href="plugins/imguploadify/home.css">
<!-- ace styles -->
<link rel="stylesheet" href="static/assets/css/ace.css" class="ace-main-stylesheet" id="main-ace-style" />
<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script> 
<script type="text/javascript" src="plugins/nivo-slider/jquery.nivo.slider.pack.js"></script>
<script type="text/javascript" src="static/js/jquery.tips.js"></script>
<style type="text/css">

</style>
</head>
<body>
	<div class="container-fluid" id="main-container">
		<div id="page-content" class="clearfix">
			<div class="row-fluid">
				<div class="row-fluid">
					<form action="bg/images/upload.do" name="Form" id="Form" method="post" enctype="multipart/form-data">

						<div class="aui-form-group clear">
							<div class="aui-form-input">
								<div class="aui-content-img-box aui-content-full">
									<div class="aui-photo aui-up-img clear">

										<c:choose>
											<c:when test="${not empty images}">
												<c:forEach items="${images}" var="var" varStatus="vs">
													<section class="aui-up-section fl">
														<span class="aui-up-span"></span>
														<img class="aui-close-up-img" src="plugins/imguploadify/close.png">
														<img class="aui-to-up-img" src="${var.src}">
														<p class="img-aui-img-name-p"></p>
														<input type="hidden" name="imageId" value="${var.id}">
													</section>
												</c:forEach>
											</c:when>
										</c:choose>

										<section class="aui-file-up fl" <c:if test="${fn:length(images)>=20}" > style="display: none" </c:if> >
											<img src="plugins/imguploadify/up.png" class="add-img">
											<input type="file" name="imageUp" id="imageUp" class="file" value="" accept="image/jpg,image/jpeg,image/png,image/bmp" multiple="">
										</section>
									</div>
								</div>
							</div>
						</div>
						
					</form>
				</div>
				<!-- PAGE CONTENT ENDS HERE -->
			</div>
			<!--/row-->

		</div>
		<!--/#page-content-->
	</div>
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

	<script type="text/javascript">
		var imageCount = 20;

		$(top.hangge());

		$(function() {
			var delParent;
			var defaults = {
				fileType: ["jpg", "png", "bmp", "jpeg"],// 上传图片支持的格式
				fileSize: 1024 * 1024 * 10 // 上传的图片大小不得超过 10M
			};
			/*点击图片*/
			$(".file").change(function() {
				var idFile = $(this).attr("id");
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
						var $input = $("<input id='actionId' name='actionId' value='' type='hidden'>");
						$input.appendTo($section);
						var $input2 = $("<input id='tagId' name='tagId' value='' type='hidden'/>");
						$input2.appendTo($section);

					}
				}
				setTimeout(function() {
					$(".aui-up-section").removeClass("loading");
					$(".aui-to-up-img").removeClass("aui-up-clarity");
				}, 100);


				uploadImage('imageUp');


				numUp = imgContainer.find(".aui-up-section").length;
				if (numUp >= 5) {
					$(this).parent().hide();
				}

				$(this).val("");
			});

			$(".aui-photo").delegate(".aui-close-up-img", "click", function() {
				$(".aui-works-mask").show();
				delParent = $(this).parent();
			});

			$(".aui-accept-ok").click(function() {
				//$(".aui-works-mask").hide();
				//var numUp = delParent.siblings().length;
				//if (numUp < imageCount) {
				//	delParent.parent().find(".aui-file-up").show();
				//}
				//delParent.remove();

				var imageId = delParent.find("input[name='imageId']").val();
				deleteImage(imageId,delParent)

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

		function uploadImage() {

			var formData = new FormData();
			formData.append("image", document.getElementById("imageUp").files[0]);
			formData.append("rid", "0");
			$.ajax({
				url: "bg/images/upload.do",
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

		function deleteImage(id,delParent){
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

	</script>
</body>
</html>

