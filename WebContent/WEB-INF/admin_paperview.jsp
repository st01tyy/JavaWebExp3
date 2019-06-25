<%@page import="dataobject.Paper"%>
<%@page import="dataobject.Question"%>
<%@page import="dataobject.Selection"%>
<%@page import="dao.QuestionDao"%>
<%@page import="java.util.List"%>
<%@page import="java.util.TreeMap"%>
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

<title>问卷管理员视角</title>
</head>
<%
	@SuppressWarnings("all")
	List<Question> questionList = (List<Question>)session.getAttribute("QuestionList");
	Paper paper = (Paper)session.getAttribute("Paper");
%>
<body>

	<!-- 头部 -->
	<div class="container page-header" style="margin-top: 15px;">
		<h4 class="text-primary" style="font-size: 36px; font-family: 幼圆;">在线问卷系统</h4>
	</div>

	<!-- 试卷标题 -->
	<div class="container" style="text-align: center">
		<h2 class="text-primary" style="color: black; font-family: 幼圆;"><%=paper.getPapername()%></h2>
	</div>
	<br>
	<%
		for(int a = 0; a < questionList.size(); a++)
		{
			Question temp = questionList.get(a);
			QuestionDao questionDao = new QuestionDao(temp);
			List<Selection> selectionList = questionDao.getSelection();
			TreeMap<Selection, Integer> map = questionDao.analyzeSelection();
			%>
			<div class="container">
				<div class="row">
					<div class="col-lg-1" style="text-align:right;">
						<p class="text-info" style="color:black; font-size: 20px;"><%=(a+1) %>.</p>
					</div>
					<div class="col-lg-11">
						<p class="text-info" style="color:black; font-size: 20px;"><%=temp.getQuestion() %></p>
					</div>
				</div>
				<div class="container" style="font-size: 20px; margin-left: 80px;">
				<%
					for(int b = 0; b < selectionList.size(); b++)
					{
						%>
						<p><%=selectionList.get(b).getSelection_describe() %> <span class="label label-primary"><%=map.get(selectionList.get(b)) %></span></p>
						<%
					}
				%>
				</div>
			</div>
			<br>
			<%
		}
	%>
	<div align="center">
		<form method="POST" action="/JavaWebExp3/servlet/adminpaperview" onsubmit="">
			<button type="submit" class="btn btn-default" style="font-size: 18px;">返回</button>
		</form>
	</div>
</body>
</html>