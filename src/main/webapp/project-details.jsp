<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<!--
This is a starter template page. Use this page to start your new project from
scratch. This page gets rid of all links and provides the needed markup only.
-->
<html lang="en">

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<!-- Tell the browser to be responsive to screen width -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<!-- Favicon icon -->
<link rel="icon" type="image/png" sizes="16x16"
	href="plugins/images/favicon.png">
<title>Pixel Admin</title>
<!-- Bootstrap Core CSS -->
<link href="bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
<!-- Menu CSS -->
<link
	href="plugins/bower_components/sidebar-nav/dist/sidebar-nav.min.css"
	rel="stylesheet">
<!-- Animation CSS -->
<link href="css/animate.css" rel="stylesheet">
<!-- Custom CSS -->
<link href="css/style.css" rel="stylesheet">
<!-- color CSS you can use different color css from css/colors folder -->
<!-- We have chosen the skin-blue (blue.css) for this starter
          page. However, you can choose any other skin from folder css / colors .
-->
<link href="css/colors/blue-dark.css" id="theme" rel="stylesheet">
<link rel="stylesheet" href="./css/custom.css">
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
					<div class="col-md-12">
						<h4 class="page-title">Chi tiết dự án - ${projectDetails.name}</h4>
					</div>
					<!-- /.col-lg-12 -->
				</div>
				<!-- BEGIN THỐNG KÊ -->
				<div class="row">
					<c:forEach items="${projectProgress}" var="entry">
						<c:choose>
							<c:when test="${entry.key == 'CHƯA BẮT ĐẦU' }">
								<c:set var="progressText" value="text-danger"></c:set>
								<c:set var="progressBar" value="progress-bar-danger"></c:set>
							</c:when>
							<c:when test="${entry.key == 'ĐANG THỰC HIỆN' }">
								<c:set var="progressText" value="text-megna"></c:set>
								<c:set var="progressBar" value="progress-bar-megna"></c:set>
							</c:when>
							<c:when test="${entry.key == 'ĐÃ HOÀN THÀNH' }">
								<c:set var="progressText" value="text-primary"></c:set>
								<c:set var="progressBar" value="progress-bar-primary"></c:set>
							</c:when>
						</c:choose>
						<!--col -->
						<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
							<div class="white-box">
								<div class="col-in row">
									<div class="col-md-6 col-sm-6 col-xs-6">
										<i data-icon="E" class="linea-icon linea-basic"></i>
										<h5 class="text-muted vb">${entry.key}</h5>
									</div>
									<div class="col-md-6 col-sm-6 col-xs-6">
										<h3 class="counter text-right m-t-15 ${progressText }">${entry.value[1]}%</h3>
									</div>
									<div class="col-md-12 col-sm-12 col-xs-12">
										<div class="progress">
											<div class="progress-bar ${progressBar }" role="progressbar"
												aria-valuenow="40" aria-valuemin="0" aria-valuemax="100"
												style="width: ${entry.value[1]}%">
												<span class="sr-only">${entry.value[1]}% Complete
													(success)</span>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<!-- /.col -->
					</c:forEach>
				</div>
				<!-- END THỐNG KÊ -->

				<!-- BEGIN DANH SÁCH CÔNG VIỆC -->
				<div class="row">

					<c:forEach items="${projectUsers}" var="projectUser">
						<div class="col-md-12">
							<a href="user-details?id=${projectUser.id}" class="group-title">
								<img width="30" src="plugins/images/users/pawandeep.jpg"
								class="img-circle" /> <span>${projectUser.firstName}
									${projectUser.lastName}</span>
							</a>
						</div>
						<c:forEach items="${projectProgress}" var="entry">
							<div class="col-md-4">
								<div class="white-box">
									<h3 class="box-title">${entry.key }</h3>
									<div class="message-center">
										<c:forEach var="item" items="${projectUser.tasks}"
											varStatus="index">
											<c:if test="${item.status.name == entry.key}">
												<a href="task-edit?id=${item.id}">
													<div class="mail-contnet">
														<h5>${item.name}</h5>
														<span class="mail-desc"></span> <span class="time">Bắt
															đầu: ${item.startDate.toLocalDate()}</span> <span class="time">Kết
															thúc: ${item.endDate.toLocalDate()}</span>
													</div>
												</a>
											</c:if>
										</c:forEach>
									</div>
								</div>
							</div>
						</c:forEach>
					</c:forEach>
					<!-- END DANH SÁCH CÔNG VIỆC -->
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
</body>

</html>