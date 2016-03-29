package pe.crvm.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Matematica
 */
@WebServlet({ "/Sumar", "/Restar", "/Multiplicar", "/Dividir" })
public class Matematica extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void service(HttpServletRequest request, 
						   HttpServletResponse response) throws ServletException, IOException {
		String path = request.getServletPath();
		if(path.equals("/Sumar")){
			sumar(request,response);
		} else if (path.equals("/Restar")){
			restar(request,response);
		}
	}
	
	
	protected void sumar(HttpServletRequest request, 
						   HttpServletResponse response) throws ServletException, IOException {
		
		int n1,n2, suma;
		
		// Cabecera
		PrintWriter out = response.getWriter();
	       out.print("<!DOCTYPE html>");
	       out.print("<html>");
	       out.print("<head>");
	       out.print("<title>Servlet_Demo01</title>");
	       out.print("</head>");
	       out.print("<body>");
	       out.print("<h1>SUMAR</h1>");
	    // Datos
	       n1 = Integer.parseInt(request.getParameter("n1"));
	       n2 = Integer.parseInt(request.getParameter("n2"));
	    // Mostrando Datos
	       out.println("<p>NUMERO 1: "+n1+"</p>");
	       out.println("<p>NUMERO 2: "+n2+"</p>");
	    // Proceso
	       suma = n1 + n2;
	    // Reporte
	       out.println("<p>SUMA: "+suma+"</p>");
	       
	       
	       out.print("</body>");
	       out.print("</html>");
     
	}
	
	protected void restar(HttpServletRequest request, 
			   HttpServletResponse response) throws ServletException, IOException {

int n1,n2, restar;

// Cabecera
PrintWriter out = response.getWriter();
out.print("<!DOCTYPE html>");
out.print("<html>");
out.print("<head>");
out.print("<title>Servlet_Demo01</title>");
out.print("</head>");
out.print("<body>");
out.print("<h1>RESTA</h1>");
// Datos
n1 = Integer.parseInt(request.getParameter("n1"));
n2 = Integer.parseInt(request.getParameter("n2"));
// Mostrando Datos
out.println("<p>NUMERO 1: "+n1+"</p>");
out.println("<p>NUMERO 2: "+n2+"</p>");
// Proceso
restar = n1 - n2;
// Reporte
out.println("<p>RESTA: "+restar+"</p>");


out.print("</body>");
out.print("</html>");

}

}
