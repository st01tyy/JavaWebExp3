<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.List"%>
<%@page import="dataobject.User"%>
<%@page import="dataobject.Paper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<!-- 引入Bootstrap -->
<!-- https://getbootstrap.com/ -->
<link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
<script type="text/javascript" src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
<script type="text/javascript" src="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>

<title>用户主页</title>
</head>
<%	
	//获取session
	User user = (User)session.getAttribute("User");
	@SuppressWarnings("all")
	List<Paper> list = (List<Paper>)session.getAttribute("PaperList");
%>
<body>

	<!-- 头部 -->
	<div class="container page-header" style="margin-top: 15px;">
		<h4 class="text-primary" style="font-size: 36px; font-family: 幼圆;">在线问卷系统</h4>
	</div>

	<!-- 状态栏 -->
	<div class="container">
		<nav class="navbar navbar-inverse" role="navigation">
			<div class="container-fluid;">

				<!-- 用户名显示 -->
				<div class="navbar-header">
						<a class="navbar-brand" style="font-size: 24px;">
							<span class="glyphicon glyphicon-user"></span>
							<%=user.getUsername() %>
						</a>
				</div>

				<!-- 按钮栏 -->
				<div style="float: right;">
					<form name="form_userinfo" method="POST" action="/JavaWebExp3/servlet/logout" class="navbar-form navbar-left">
						<button type="button" class="btn btn-primary" style="font-size: 20px; font-family: 幼圆;"><span class="glyphicon glyphicon-cog"></span>   设置</button>
						<button type="submit" name="btn_logout" class="btn btn-danger" style="font-size: 20px; font-family: 幼圆;"><span class="glyphicon glyphicon-log-out"></span>   登出</button>
					</form>
				</div>

			</div>
		</nav>
	</div>

	<!-- 二级标题 -->
	<div class="container" style="border-bottom: 1px dashed grey;">
		<h3 class="text-default" style="font-family: 幼圆;">未完成问卷</h3>
	</div>

	<!-- 未完成问卷列表 -->
	<div class="container" style="margin-top: 10px;">

		<%
			for(int a = 0; a < list.size(); a++)
			{
				%>
				<!-- 试卷导航栏 -->
				<nav class="navbar navbar-default" role="navigation">
					<div class="container-fluid;">

						<!-- 问卷名显示 -->
						<div class="navbar-header">
								<a class="navbar-brand" style="font-size: 20px;">
									<span class="glyphicon glyphicon-tasks"></span>
									   <%=list.get(a).getPapername() %> 
								</a>
						</div>

						<!-- 时间显示 -->
						<div class="navbar-header">
							<a class="navbar-brand" style="font-size: 20px; color: red;">
								<%
									SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
									String publish = format.format(list.get(a).getPublish_time());
									String end = format.format(list.get(a).getCutoff_time());
								%>
								截止时间： <%=end %>
							</a>
						</div>
						
						<!-- 按钮栏 -->
						<div style="float: right;">
							<form name="form_paper_<%=list.get(a).getPaperid() %>" method="POST" action="/JavaWebExp3/servlet/usermain" class="navbar-form navbar-left">
								<input type="hidden" name="choicesheet">
								<button name="btn_paper" onclick="updateChoice(<%=a %>)" type="submit" class="btn btn-default" style="font-size: 16px; font-family: 幼圆;"><span class="glyphicon glyphicon-pencil"></span>   作答</button>
							</form>
						</div>

					</div>
				</nav>
				<%
			}
		%>
	</div>
</body>
<script>
function updateChoice(number)
{
	var choicesheet = document.getElementsByName("choicesheet")[number];
	choicesheet.value = number;
}
</script>
</html>