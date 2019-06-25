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

public class AdminMainServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
    public AdminMainServlet() 
    {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		String str = request.getParameter("choicesheet");
		System.out.println("choicesheet: "+str);
		String[] arr = str.split(" ");
		int index = new Integer(arr[0]).intValue();
		
		
		@SuppressWarnings("unchecked")
		List<Paper> list = (List<Paper>)request.getSession().getAttribute("PaperList");
		request.getSession().setAttribute("Paper", list.get(new Integer(index)));
		
		Paper paper = list.get(new Integer(index));
		PaperDao paperDao = new PaperDao(paper);
		
		List<Question> questionList = paperDao.getQuestion();
		request.getSession().setAttribute("QuestionList", questionList);
		request.getRequestDispatcher("/WEB-INF/admin_paperview.jsp").forward(request, response);
	}

}
