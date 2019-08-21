<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>个人设置</title>
	<script type="text/javascript" src="/libs/jquery/jquery.min.js"></script>
    <!-- Bootstrap -->
    <link rel="stylesheet" type="text/css" href="/libs/bootstrap/css/bootstrap.min.css"/>
    <link href="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.9/summernote.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="/css/cms.css"/>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <style type="text/css">
    </style>
  </head>
  <body>
    <jsp:include page="/WEB-INF/inc/top.jsp"></jsp:include>
	
	<!-- 横幅 -->
	<div class="container">
		<div class="row">
			<div class="col-xs-12 my_banner">
			</div>
		</div>
	</div>
	<br/>
	<!-- 主体内容区 -->
	<div class="container">
		<div class="row">
			<div class="col-md-3">
				<jsp:include page="/WEB-INF/inc/my_left.jsp"><jsp:param value="user" name="module"/></jsp:include>
			</div>
			<div class="col-md-9">
				<div class="panel panel-default">
				  <div class="panel-body">
				    	<h1>个人设置</h1>
				    	<hr/>
				    	
				    	<form action="/my/user/save" enctype="multipart/form-data" method="post" >
				    	<input type="hidden" value="${user.id}" name="id">
				    	<p align="center" class="red"> </p>
				    	
				    	<p>
				    		<input name="username" value="${user.username}" class="form-control" placeholder="用户名"/>
				    		<span class="red"></span>
				    	</p>
				    	<p>
				    		<input name="nickname" value="${user.nickname}" class="form-control" placeholder="昵称"/>
				    		<span class="red"></span>
				    	</p>
				    	<p>
				    		<input type="radio" value="FAMALE" name="sex" <c:if test="${user.gender.name()=='FAMALE' }">checked="checked"</c:if>>女士
				    		<input type="radio" value="MALE" name="sex" <c:if test="${user.gender.name()=='MALE' }">checked="checked"</c:if>>先生
				    	</p>
				    	<p>
				    		<input name="phone" value="${user.phone}" class="form-control" placeholder="电话"/>
				    		<span class="red"></span>
				    	</p>
				    	<p>
				    		<input name="email" value="${user.email}" class="form-control" placeholder="邮箱"/>
				    		<span class="red"></span>
				    	</p>
				    	<p>
				    		<input name="address" value="${user.address}" class="form-control" placeholder="地址"/>
				    		<span class="red"></span>
				    	</p>
				    	<p>
				    		<input name="star" value="${user.star}" class="form-control" placeholder="星座"/>
				    		<span class="red"></span>
				    	</p>
				    	<p>
				    		<input name="motto" value="${user.motto}" class="form-control" placeholder="座右铭"/>
				    		<span class="red"></span>
				    	</p>
				    	<p>
			    	
				    	
				    	<p>
				    		<button type="submit" class="btn btn-info btn-block">保存</button> 
				    	</p>
				    	
				    	</form>
				  </div>
				</div>
				
			</div>
		</div>
	</div>
	
	<jsp:include page="/WEB-INF/inc/footer.jsp"/>
	
	<script src="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.9/summernote.js"></script>
	<script type="text/javascript">
		/* $(document).ready(function(){
			$("#content").summernote({
				placeholder:'博客内容',
				height:300
			});
		}); */
		//加载所有频道
		$.ajax({
			url :'/queryAllChannel',
			dataType : 'json',
			type : 'post',
			success : function(data){
				var content = "";
				for(var i = 0 ; i < data.length; i++){
					content += "<option value="+data[i].id+">"+data[i].name+"</option>";
				}
				$('#channel').html(content);
				var channel_id = $('#channel').find('option')[0].value;
				//默认加载第一个频道下的所有种类
				 $.ajax({
					url :'/queryCategoryByChannelId?channel=' + channel_id,
					dataType : 'json',
					type : 'post',
					success : function(data){
						var content = "";
						for(var i = 0 ; i < data.length; i++){
							content += "<option value="+data[i].id+">"+data[i].name+"</option>";
						}
						$('#category').html(content);
					}
				}); 
			}
		});
		
		$('#channel').change(function(){
			var channel_id = $(this).find('option:selected').val();
			$.ajax({
				url :'/queryCategoryByChannelId?channel='+channel_id,
				dataType : 'json',
				type : 'post',
				success : function(data){
					var content = "";
					for(var i = 0 ; i < data.length; i++){
						content += "<option value="+data[i].id+">"+data[i].name+"</option>";
					}
					$('#category').html(content);
				}
			});
		});
	</script>
  </body>
</html>