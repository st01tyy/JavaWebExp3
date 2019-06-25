package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.PaperDao;
import dataobject.Paper;
import dataobject.Question;

public class UserMainServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public UserMainServlet() 
    {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		String value = request.getParameter("choicesheet");
		System.out.println("Ö÷Ò³Ñ¡ÔñÊÔ¾íÐòºÅ£º"+value);
		//User user = (User)request.getSession().getAttribute("User");
		@SuppressWarnings("unchecked")
		List<Paper> list = (List<Paper>)request.getSession().getAttribute("PaperList");
		request.getSession().setAttribute("Paper", list.get(new Integer(value)));
		Paper paper = list.get(new Integer(value));
		PaperDao paperDao = new PaperDao(paper);
		List<Question> questionList = paperDao.getQuestion();
		request.getSession().setAttribute("QuestionList", questionList);
		request.getRequestDispatcher("/WEB-INF/user_paperview.jsp").forward(request, response);
	}
}
