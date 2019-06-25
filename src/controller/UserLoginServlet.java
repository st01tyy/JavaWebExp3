package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDao;
import dataobject.Paper;
import dataobject.User;

public class UserLoginServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public UserLoginServlet()
	{
		super();
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	{
		System.out.println("doGet");
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		request.setCharacterEncoding("UTF-8");
		//检查session
		String pass = (String) request.getSession().getAttribute("PassPermission");
		String sus, spw;
		if(pass == null || !pass.equals("true"))
		{
			sus = null;
			spw = null;
		}
		else
		{
			sus = (String) request.getSession().getAttribute("Username");
			spw = (String) request.getSession().getAttribute("Password");
			request.getSession().setAttribute("PassPermission", "false");
		}
		String username,password;
		if(sus == null || spw == null)
		{
			//获取表单内容
			
			username = request.getParameter("input_username");
			password = request.getParameter("input_password");
		}
		else
		{
			username = sus;
			password = spw;
		}
		System.out.println("username: "+username+"\r\n"+"password "+password);
		
		//检查用户名密码是否正确
		UserDao userDao = new UserDao();
		boolean res = userDao.checkUser(username, password);
		
		if(!res) //不正确则重定向至登录页面
			response.sendRedirect("/JavaWebExp3/userloginfailed.html");
		else //正确则根据数据库数据生成用户主页
		{
			request.getSession().setAttribute("Username", username);
			request.getSession().setAttribute("Password", password);
			User user = userDao.getUser();
			List<Paper> list = userDao.getAllowedPaper();
			System.out.println("试卷列表长度："+list.size());
			request.getSession().setAttribute("User", user);
			request.getSession().setAttribute("PaperList", list);
			request.getRequestDispatcher("/WEB-INF/user_main.jsp").forward(request, response);
		}
	}
}
