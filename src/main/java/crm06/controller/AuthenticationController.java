package crm06.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.constant.Constable;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.cj.Constants;

import crm06.entity.UserEntity;
import crm06.services.AuhenticationService;
import crm06.services.AuthenticaionServiceImp;
import crm06.services.UserService;
import crm06.utils.PasswordEncoder;

@WebServlet(name = "authenController", urlPatterns = { "/login", "/logout", "/404" })
public class AuthenticationController extends HttpServlet {
	private AuthenticaionServiceImp authenService = new AuhenticationService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");

		String path = req.getServletPath();
		switch (path) {
		case "/login":
			req.getRequestDispatcher("login.jsp").forward(req, resp);
			break;
		case "/logout":
			this.doGetLogout(req, resp);
			break;
		case "/404":
			req.getRequestDispatcher("404.jsp").forward(req, resp);
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + path);
		}
	}
	
	private void doGetLogout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		session.invalidate();
		resp.sendRedirect(req.getContextPath() + "/login");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");

		String path = req.getServletPath();
		switch (path) {
		case "/login":
			this.doPostLogin(req, resp);
			break;

		default:
			throw new IllegalArgumentException("Unexpected value: " + path);
		}

	}

	private void doPostLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json");
		PrintWriter writer = resp.getWriter();
		String username = req.getParameter("email");
		String password = PasswordEncoder.encode(req.getParameter("password"));

		UserEntity loginedInfo = authenService.login(username, password);
		HttpSession session = req.getSession();
		if (loginedInfo != null) {
			session.setAttribute("loginedInfo", loginedInfo);
			session.setMaxInactiveInterval(3600);
			writer.println(true);
		} else {
			writer.println(false);
		}
		writer.close();
	}



}
