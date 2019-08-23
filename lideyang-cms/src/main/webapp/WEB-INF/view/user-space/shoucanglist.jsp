<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>我的博客</title>

    <!-- Bootstrap -->
    <link rel="stylesheet" type="text/css" href="/libs/bootstrap/css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="/css/cms.css"/>

    <style type="text/css">
    </style>
  </head>
  <body>
    <jsp:include page="/WEB-INF/inc/top.jsp"></jsp:include>
	<br/>
	<!-- 主体内容区 -->
	<div class="container">
		<div class="container-fluid">

          <!-- Breadcrumbs-->
          <ol class="breadcrumb">
            <li class="breadcrumb-item">
              <a >收藏夹</a>
            </li>
          </ol>
          	<form action="/admin/articles" method="post">
          		<input type="hidden" name="currentPage">
          		标题:<input type="text" name="title" value="${title }">
          		<input type="submit" value="查询">
          	</form>
			<!-- 表格 -->
			<table class="table table-striped table-bordered table-hover">
				<tr>
					<td><input type="checkbox" value="全选"></td>
					<td>文章编号</td>
					<td>文章标题</td>
					<td>点击量</td>
					<td>操作</td>
				</tr>
				<c:forEach items="${list }" var="l">
				<tr>
					<td><input type="checkbox" value="${l.id }" name="checkid"></td>
					<td>${l.id }</td>
					<td>${l.title }</td>
					<td>${l.hits }</td>
					<td>
						<input type="button" value="取消收藏" onclick="del(${l.id})">
					</td>
				</tr>
				</c:forEach>
			</table>
			${page }
			共耗时${time }毫秒
        </div>
	</div>
	
	<jsp:include page="/WEB-INF/inc/footer.jsp"/>
	
	<script type="text/javascript">
		
	</script>
  </body>
</html>