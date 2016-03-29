package pe.crvm.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Servlet01
 */
@WebServlet(
		name = "Panchito", 
		urlPatterns = { 
				"/uno", 
				"/dos", 
				"/tres"
		})
public class Servlet01 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, 
						   HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
	       out.print("<!DOCTYPE html>");
	       out.print("<html>");
	       out.print("<head>");
	       out.print("<title>Servlet_Demo01</title>");
	       out.print("</head>");
	       out.print("<body>");
	       out.print("<h1>Bienvenidos al servlet 01</h1>");
	       out.print("</body>");
	       out.print("</html>");
		
	}

}
