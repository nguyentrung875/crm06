<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link rel="icon" type="image/png" sizes="16x16"
	href="plugins/images/favicon.png">
<title>Pixel Admin</title>
<!-- Bootstrap Core CSS -->
<link href="bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
<!-- Menu CSS -->
<link
	href="plugins/bower_components/sidebar-nav/dist/sidebar-nav.min.css"
	rel="stylesheet">
<!-- toast CSS -->
<link href="plugins/bower_components/toast-master/css/jquery.toast.css"
	rel="stylesheet">
<!-- animation CSS -->
<link href="css/animate.css" rel="stylesheet">
<!-- Custom CSS -->
<link href="css/style.css" rel="stylesheet">
<!-- color CSS -->
<link href="css/colors/blue-dark.css" id="theme" rel="stylesheet">
<link rel="stylesheet" href="css/custom.css">

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->
</head>

<body>
	<!-- Preloader -->
	<div class="preloader">
		<div class="cssload-speeding-wheel"></div>
	</div>
	<div id="wrapper">
		<!-- Navigation -->
		<nav class="navbar navbar-default navbar-static-top m-b-0">
			<div class="navbar-header">
				<a class="navbar-toggle hidden-sm hidden-md hidden-lg "
					href="javascript:void(0)" data-toggle="collapse"
					data-target=".navbar-collapse"> <i class="fa fa-bars"></i>
				</a>
				<div class="top-left-part">
					<a class="logo" href="index"> <b> <img
							src="plugins/images/pixeladmin-logo.png" alt="home" />
					</b> <span class="hidden-xs"> <img
							src="plugins/images/pixeladmin-text.png" alt="home" />
					</span>
					</a>
				</div>
				<ul class="nav navbar-top-links navbar-left m-l-20 hidden-xs">
					<li>
						<form role="search" class="app-search hidden-xs">
							<input type="text" placeholder="Search..." class="form-control">
							<a href=""> <i class="fa fa-search"></i>
							</a>
						</form>
					</li>
				</ul>
				<ul class="nav navbar-top-links navbar-right pull-right">
					<li>
						<div class="dropdown">
							<a class="profile-pic dropdown-toggle" data-toggle="dropdown"
								href="#"> <img src="plugins/images/users/varun.jpg"
								alt="user-img" width="36" class="img-circle" /> <b
								class="hidden-xs">${loginedInfo.firstName}
									${loginedInfo.lastName}</b>
							</a>
							<ul class="dropdown-menu">
								<li><a href="profile">Thông tin cá nhân</a></li>
								<li><a href="profile-password">Đổi mật khẩu</a></li>
								<li class="divider"></li>
								<li><a href="logout">Đăng xuất</a></li>
							</ul>
						</div>
					</li>
				</ul>
			</div>
			<!-- /.navbar-header -->
			<!-- /.navbar-top-links -->
			<!-- /.navbar-static-side -->
		</nav>
		<!-- Left navbar-header -->
		<div class="navbar-default sidebar" role="navigation">
			<div class="sidebar-nav navbar-collapse slimscrollsidebar">
				<ul class="nav" id="side-menu">
					<li style="padding: 10px 0 0;"><a href="index" class="waves-effect"><i
							class="fa fa-clock-o fa-fw" aria-hidden="true"></i><span
							class="hide-menu">Dashboard</span></a></li>
					<c:if test="${loginedInfo.role.name == 'ADMIN'}">
						<li><a href="user" class="waves-effect"><i
								class="fa fa-user fa-fw" aria-hidden="true"></i><span
								class="hide-menu">Thành viên</span></a></li>
						<li><a href="role" class="waves-effect"><i
								class="fa fa-modx fa-fw" aria-hidden="true"></i><span
								class="hide-menu">Quyền</span></a></li>
						
					</c:if>
					<c:if
						test="${loginedInfo.role.name == 'ADMIN' or loginedInfo.role.name == 'LEADER'}">
						<li><a href="project" class="waves-effect"><i
								class="fa fa-table fa-fw" aria-hidden="true"></i><span
								class="hide-menu">Dự án</span></a></li>
						<li><a href="task" class="waves-effect"><i
								class="fa fa-table fa-fw" aria-hidden="true"></i><span
								class="hide-menu">Công việc</span></a></li>
					</c:if>
				</ul>
			</div>
		</div>
		<!-- Left navbar-header end -->
		<!-- Page Content -->
		<div id="page-wrapper">
			<div class="container-fluid">
				<div class="row bg-title">
					<div class="col-lg-3 col-md-4 col-sm-12 col-xs-12">
						<h4 class="page-title">Cập nhật thành viên</h4>
					</div>

				</div>
				<!-- /.row -->
				<!-- .row -->
				<div class="row">
					<div class="col-md-2 col-12"></div>
					<div class="col-md-8 col-xs-12">
						<div class="white-box">
							<form id="formUser" class="form-horizontal form-material">
								<div class="form-group">
									<label class="col-md-12">Họ</label>
									<div class="col-md-12">
										<input name="firstName" type="text" placeholder="Nguyễn"
											class="form-control form-control-line"
											value="${editUser.firstName}">
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-12">Tên</label>
									<div class="col-md-12">
										<input name="lastName" type="text" placeholder="Văn Tèo"
											class="form-control form-control-line"
											value="${editUser.lastName}">
									</div>
								</div>
								<div class="form-group">
									<label for="example-email" class="col-md-12">Email</label>
									<div class="col-md-12">
										<input name="email" type="email"
											placeholder="nguyenvanteo@admin.com"
											class="form-control form-control-line" name="example-email"
											id="example-email" value="${editUser.username}">
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-12">Số điện thoại</label>
									<div class="col-md-12">
										<input name="phone" type="text" placeholder="099945678"
											class="form-control form-control-line"
											value="${editUser.phone}">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-12">Quyền</label>
									<div class="col-sm-12">
										<select name="role" class="form-control form-control-line">
											<c:forEach var="role" items="${roleList}">
												<c:choose>
													<c:when test="${editUser.role.id eq role.id}">
														<option selected value="${role.id}">${role.name}</option>
													</c:when>
													<c:otherwise>
														<option value="${role.id}">${role.name}</option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="form-group">
									<div class="col-sm-12">
										<button id="btnUserSubmit" type="submit"
											class="btn btn-success">Cập nhật</button>
										<a href="user" class="btn btn-primary">Quay lại</a>
									</div>
								</div>
							</form>
						</div>
					</div>
					<div class="col-md-2 col-12"></div>
				</div>
				<!-- /.row -->
			</div>
			<!-- /.container-fluid -->
			<footer class="footer text-center"> 2018 &copy; myclass.com
			</footer>
		</div>
		<!-- /#page-wrapper -->
	</div>
	<!-- /#wrapper -->
	<!-- jQuery -->
	<script src="plugins/bower_components/jquery/dist/jquery.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.5/jquery.validate.min.js"></script>
	<script src="plugins/bower_components/toast-master/js/jquery.toast.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script src="bootstrap/dist/js/bootstrap.min.js"></script>
	<!-- Menu Plugin JavaScript -->
	<script
		src="plugins/bower_components/sidebar-nav/dist/sidebar-nav.min.js"></script>
	<!--slimscroll JavaScript -->
	<script src="js/jquery.slimscroll.js"></script>
	<!--Wave Effects -->
	<script src="js/waves.js"></script>
	<!-- Custom Theme JavaScript -->
	<script src="js/custom.min.js"></script>
	<script src="js/user.js"></script>

</body>

</html>