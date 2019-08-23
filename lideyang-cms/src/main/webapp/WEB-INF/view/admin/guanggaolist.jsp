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
            <li class="breadcrumb-item active">广告管理</li>
          </ol>
          <input type="button" value="添加广告" onclick="add()">
			<!-- 表格 -->
			<table class="table table-striped table-bordered table-hover">
				<tr>
					<td>编号</td>
					<td>名称</td>
					<td>地址</td>
					<td>操作</td>
				</tr>
				<c:forEach items="${guanggaolist }" var="g">
				<tr>
					<td>${g.id }</td>
					<td>${g.name }</td>
					<td>
						<img src="/upload/${g.url }" style="height: 100px;width: 100px;">
					</td>
					<td>
						<input type="button" value="删除" onclick="del(${g.id})">
						<input type="button" value="修改" onclick="update(${g.id})">
					</td>
					
				</tr>
				</c:forEach>    	
			</table>
         
         

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
    	function add(){
    		location.href = "/admin/toaddguanggao";
    	}
    	
    	function del(id){
    		$.post(
    			"/admin/delguanggao",
    			{id:id},
    			function(msg){
    				if(msg){
    					alert("删除成功");
    					location.href = "/admin/guanggaolist";
    				}else{
    					alert("删除失败");
    				}
    			},
    			"json"
    		)
    	}
    	
    	function update(id){
    		location.href = "/admin/toupdateguanggao?id="+id;
    	}
    
    </script>
  </body>

</html>
