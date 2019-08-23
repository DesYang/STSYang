<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">

  <head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="howsun">

    <title>CMS后台管理系统</title>

    <!-- Bootstrap core CSS-->
    <link href="/libs/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom fonts for this template-->
    <link href="/libs/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">

    <!-- Custom styles for this template-->
    <link href="/libs/sb-admin/sb-admin.css" rel="stylesheet">

  </head>

  <body id="page-top">

	<!-- 后台管理系统顶部 -->
 	<jsp:include page="_inc_top.jsp"/>

    <div id="wrapper">

 		<!-- 后台管理系统左部菜单 -->
 		<jsp:include page="_inc_left.jsp"/>

      <div id="content-wrapper">

        <div class="container-fluid">

          <!-- Breadcrumbs-->
          <ol class="breadcrumb">
            <li class="breadcrumb-item">
              <a href="#">后台首页</a>
            </li>
            <li class="breadcrumb-item active">文章管理</li>
          </ol>
          	<form action="/admin/articles" method="post">
          		<input type="hidden" name="currentPage">
          		标题:<input type="text" name="title" value="${title }">
          		<input type="submit" value="查询">
          		<input type="button" value="发布文章" onclick="add()">
          	</form>
			<!-- 表格 -->
			<table class="table table-striped table-bordered table-hover">
				<tr>
					<td><input type="checkbox" value="全选"></td>
					<td>文章编号</td>
					<td>文章标题</td>
					<td>点击量</td>
					<td>是否审核</td>
					<td>操作</td>
				</tr>
				<c:forEach items="${articles }" var="l">
				<tr>
					<td><input type="checkbox" value="${l.id }" name="checkid"></td>
					<td>${l.id }</td>
					<td>${l.title }</td>
					<td>${l.hits }</td>
					<td>
						<c:if test="${l.status == 1 }">已审核</c:if>
						<c:if test="${l.status != 1 }">未审核</c:if>
					</td>
					<td>
						<input type="button" value="审核" onclick="shenhe(${l.id })">
						<input type="button" value="收藏" onclick="shoucang(${l.id})">
						<input type="button" value="删除" onclick="del(${l.id})">
					</td>
				</tr>
				</c:forEach>
			</table>
			${page }
			共耗时${time }毫秒
        </div>
        <!-- /.container-fluid -->

        <!-- Sticky Footer -->
        <footer class="sticky-footer">
          <div class="container my-auto">
            <div class="copyright text-center my-auto">
              <span>Copyright Â© Your Website 2019</span>
            </div>
          </div>
        </footer>

      </div>
      <!-- /.content-wrapper -->

    </div>
    <!-- /#wrapper -->

    <!-- Scroll to Top Button-->
    <a class="scroll-to-top rounded" href="#page-top">
      <i class="fas fa-angle-up"></i>
    </a>

    <!-- Bootstrap core JavaScript-->
    <script src="/libs/jquery/jquery.min.js"></script>
    <script src="/libs/bootstrap/js/bootstrap.bundle.min.js"></script>

    <!-- Custom scripts for all pages-->
    <script src="/libs/sb-admin/sb-admin.min.js"></script>
    <script type="text/javascript">
    	function page(i){
    		$("input[name=currentPage]").val(i);
    		$("form").submit();
    	}
    	
    	function shenhe(id){
    		$.post(
    			"/admin/shenhe",
    			{id:id},
    			function(msg){
    				if(msg){
    					alert("审核成功");
    					location.href = "/admin/articles"
    				}else{
    					alert("审核失败");
    				}
    				
    			},
    			"json"
    		)
    	}
    	
    	function add(){
    		location.href = "/admin/toadd";
    	}
    	
    	function shoucang(id){
    		$.post(
    			"/admin/shenheshoucang",
    			{id:id},
    			function(msg){
    				if(msg){
    					$.post(
    			    			"/admin/shoucang",
    			    			{id:id},
    			    			function(msg){
    			    				if(msg){
    			    					alert("收藏成功");
    			    					location.href = "/admin/articles";
    			    				}else{
    			    					alert("收藏失败");
    			    				}
    			    			},
    			    			"json"
    			    		)	
    				}else{
    					alert("该本章已收藏");		
    				}
    			},
    			"json"
    		)
    		
    	}
    	
    	function del(id){
    		
   			
   			$.post(
   				"delarticles",
   				{id:id},
   				function(msg){
   					if(msg){
   						alert("删除成功");
   						location.href = "/admin/articles";
   					}
   				},
   				"json"
   			)
    	}
    </script>
  </body>

</html>
			