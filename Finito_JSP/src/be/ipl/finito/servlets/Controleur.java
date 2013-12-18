package be.ipl.finito.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns={"*.html", "/index.html"})
public class Controleur extends javax.servlet.http.HttpServlet implements
		javax.servlet.Servlet {
	static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String nom = request.getServletPath().substring(1);
		if (nom.equals(""))
			nom = "index.html";
		RequestDispatcher rd = getServletContext().getNamedDispatcher(nom);
		if (rd == null)
			rd = getServletContext().getNamedDispatcher("index.html");
		rd.forward(request, response);
	}
}