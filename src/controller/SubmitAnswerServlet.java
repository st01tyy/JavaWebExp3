package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.QuestionDao;
import dataobject.Question;
import dataobject.Selection;
import dataobject.User;


public class SubmitAnswerServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public SubmitAnswerServlet() 
    {
        super();
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		request.setCharacterEncoding("UTF-8");
		User user = (User)session.getAttribute("User");
		@SuppressWarnings("unchecked")
		List<Question> questionList = (List<Question>)session.getAttribute("QuestionList");
		int length = questionList.size();
		System.out.println("questionList's length: "+length);
		for(int a = 0; a < length; a++)
		{
			Question temp = questionList.get(a);
			QuestionDao questionDao = new QuestionDao(temp);
			List<Selection> selectionList = questionDao.getSelection();
			String answer = request.getParameter(new Integer(a).toString());
			System.out.println(answer);
			for(int b = 0; b < answer.length(); b++)
			{
				int index = answer.charAt(b)-'0';
				questionDao.addAnswer(new Long(selectionList.get(index).getSelectionid()).toString(), user.getUid());
			}
		}
		session.setAttribute("PassPermission", "true");
		request.getRequestDispatcher("/servlet/userlogin").forward(request, response);
	}

}
