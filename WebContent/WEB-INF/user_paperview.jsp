<%@page import="dataobject.Paper"%>
<%@page import="dataobject.Question"%>
<%@page import="dataobject.Selection"%>
<%@page import="dao.QuestionDao"%>
<%@page import="java.util.List"%>
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

<title>问卷用户视角</title>
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
		<h2 class="text-primary" style="color: black; font-family: 幼圆;"><%=paper.getPapername() %></h2>
	</div>
	<br>
	<%
		for(int a = 0; a < questionList.size(); a++)
		{
			Question temp = questionList.get(a);
			QuestionDao questionDao = new QuestionDao(temp);
			List<Selection> selectionList = questionDao.getSelection();
			
			if(temp.getType().equals("radio"))
			{
				%>
				<!-- 单选题 -->
				<div class="container">
					<div class="row">
						<div class="col-lg-1" style="text-align:right;">
							<p class="text-info" style="color:black; font-size: 20px;"><%=(a+1) %>.</p>
						</div>
						<div class="col-lg-11">
							<p class="text-info" style="color:black; font-size: 20px;"><%=temp.getQuestion() %></p>
						</div>
					</div>
					<div class="text-info" style="margin-left: 95px; font-size: 18px; color:black;">
						<form>
						<%
							for(int b = 0; b < selectionList.size(); b++)
							{
								%>
								<div class="radio">
									<label>
										<input type="radio" name=<%=a %> onclick="updateAnswer(<%=a%>)">
										<%=selectionList.get(b).getSelection_describe() %>
									</label>
								</div>
								<%
							}
						%>
						</form>
					</div>
				</div>
				<br>
			<%	
			}
			else
			{
				%>
				<!-- 多选题 -->
				<div class="container">
					<div class="row">
						<div class="col-lg-1" style="text-align:right;">
							<p class="text-info" style="color:black; font-size: 20px;"><%=(a+1) %>.</p>
						</div>
						<div class="col-lg-11">
							<p class="text-info" style="color:black; font-size: 20px;"><%=temp.getQuestion() %></p>
						</div>
					</div>
				<div class="text-info" style="margin-left: 95px; font-size: 18px; color:black;">
					<form>
					<%
						for(int b = 0; b < selectionList.size(); b++)
						{
							%>
							<div class="checkbox">
								<label>
									<input type="checkbox" name=<%=a %> onclick="updateAnswer(<%=a%>)">
									<%=selectionList.get(b).getSelection_describe() %>
								</label>
							</div>
							<%
						}
					%>
					</form>
				</div>
			</div>
			<br>
			<%
			}
		}
	%>
	
	<div align="center">
		<p id="errormsg" class="text-primary" style="font-size: 18px; color:red;"></p>
		<form method="POST" action="/JavaWebExp3/servlet/submitanswer" onsubmit="return isFinished()">
			<%
				for(int a = 0; a < questionList.size(); a++)
				{
					%>
						<input type="hidden" class="answersheet" name=<%=a %> id=<%=a %>>
					<%
				}
			%>
			<button type="submit" class="btn btn-primary" style="font-size: 18px;">提交</button>
		</form>
	</div>
</body>
<script>
function updateAnswer(number)
{
	var buttonList = document.getElementsByName(number);
	var answersheet = document.getElementById(number);
	answersheet.value="";
	for(var a = 0; a < buttonList.length; a++)
	{
		if(buttonList[a].checked == true)
			answersheet.value += a;
	}
}
function isFinished()
{
	var answerList = document.getElementsByClassName("answersheet");
	var errormsg = document.getElementById("errormsg");
	for(var a = 0; a < answerList.length; a++)
	{
		if(answerList[a].value == "")
		{
			errormsg.innerHTML = "问卷未完成"			
			return false;
		}
	}
	errormsg.innerHTML = "";
	return true;
}
</script>
</html>