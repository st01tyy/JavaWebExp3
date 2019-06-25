package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreatePaperServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public CreatePaperServlet() 
    {
        super(); 
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.getRequestDispatcher("/WEB-INF/editor.html").forward(request, response);
	}

}
