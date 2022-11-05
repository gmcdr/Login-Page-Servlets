package com.gmcdr.resgistration;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Servlet implementation class RegistrationServlet
 */
@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String uname = request.getParameter("name");
		String upwd = request.getParameter("pass");
		String uemail = request.getParameter("email");
		String umobile = request.getParameter("contact");
		RequestDispatcher dispatcher = null;
		Connection connection = null;
		
		 try {
			 Class.forName("org.postgresql.Driver");
			 connection  = DriverManager.getConnection("jdbc:postgresql://localhost:5432/company?user=postgres&password=123&ssl=false");
			 PreparedStatement pst = connection.prepareStatement("INSERT INTO users(uname, upwd, uemail, umobile) VALUES (?,?,?,?)");
			 pst.setString(1, uname);
			 pst.setString(2, upwd);
			 pst.setString(3, uemail);
			 pst.setString(4, umobile);
			 
			 int rowCount = pst.executeUpdate();
			 dispatcher = request.getRequestDispatcher("registration.jsp");
			 if (rowCount > 0) {
				request.setAttribute("status", "success");
			}else {
				request.setAttribute("status", "failed");
			}
			 dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	}

}
