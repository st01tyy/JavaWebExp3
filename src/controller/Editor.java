package controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.PaperDao;
import dao.QuestionDao;
import dataobject.User;


public class Editor extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public Editor() 
    {
        super();
    }

	@SuppressWarnings("deprecation")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		Timestamp cutoff = new Timestamp(new Date().getTime());
		cutoff.setYear(2019);
		cutoff.setMonth(12);
		cutoff.setDate(31);
		User user = (User)request.getSession().getAttribute("User");
		PaperDao paperDao = new PaperDao();
		QuestionDao questionDao = null;
		
		Enumeration<String> set = request.getParameterNames();
		if(set != null)
		{
			while(set.hasMoreElements())
			{
				String str = set.nextElement();
				System.out.println(str+" "+request.getParameter(str));
				String value;
				if(str.equals("title"))
				{
					value = request.getParameter(str);
					boolean res = paperDao.addPaper(user.getUid(), value, currentTime, cutoff);
					System.out.println("添加试卷结果："+res);
				}
				else if(str.contains("question"))
				{
					value = request.getParameter(str);
					questionDao = new QuestionDao();
					System.out.println("index: "+str.substring(str.length()-1));
					String type = request.getParameter(str.substring(str.length()-1));
					System.out.println("type: "+type);
					System.out.println("添加问题之前："+paperDao.getPaper().getPaperid()+" "+type+" "+value);
					questionDao.addQuestion(paperDao.getPaper().getPaperid(), type, value);
				}
				else if(str.contains("selection"))
				{
					value = request.getParameter(str);
					questionDao.addSelection(value);
				}
				
			}
		}
		else
			System.out.println("set is empty!");
		request.getSession().setAttribute("PassPermission", "true");
		request.getRequestDispatcher("/servlet/adminlogin").forward(request, response);
	}

}
