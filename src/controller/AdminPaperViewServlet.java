package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AdminPaperViewServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public AdminPaperViewServlet() 
    {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.getRequestDispatcher("/WEB-INF/admin_main.jsp").forward(request, response);
	}

}
