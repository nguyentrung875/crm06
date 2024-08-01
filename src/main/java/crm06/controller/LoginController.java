package crm06.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crm06.config.MySQLConfig;
import crm06.entity.UserEntity;


@WebServlet(name = "loginController", urlPatterns = "/old_login")
public class LoginController extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("login.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			//Bước 1: Nhận tham số
			String email = req.getParameter("email");
			String password = req.getParameter("password");
			
			//Bước 2: Chuẩn bị câu query tương ứng
			String query = "SELECT *\r\n"
					+ "FROM users u \r\n"
					+ "WHERE u.username = '"+ email +"' AND u.password = '" + password + "'";
			
			//Bước 3: Mở kết nối CSDL và truyền câu truy vấn xuống CSDL để thực hiện
			Connection connection = MySQLConfig.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			
			//Hay dùng 2 loại excute: 
			//executeQuery: Giành cho lấy dữ liệu -> SELECT
			//executeUpdate: khác SELECT
			//resultSet: Đại diện cho kết quả dữ liệu truy vấn được
			ResultSet resultSet = preparedStatement.executeQuery(query);
			
			List<UserEntity> listUsers = new ArrayList<UserEntity>();
			
			//Khi nào resultSet còn next được: chạy từng dòng dữ liệu từ trên xuống dưới
			while (resultSet.next()) {
				UserEntity userEntity = new UserEntity();
				userEntity.setId(resultSet.getInt("id"));
				userEntity.setFirstName(resultSet.getString("first_name"));
				listUsers.add(userEntity);
			}
		
			if (listUsers.size() > 0) {
				Cookie logined = new Cookie("logined", "true");
				resp.addCookie(logined);
				
				String contextPath = req.getContextPath();
				resp.sendRedirect(contextPath + "/user-add");				
			} else {
				System.out.println("Đăng nhập thất bại");
			}
			
			connection.close();
			
		} catch (Exception e) {
			System.out.println("Lỗi login: " + e.getMessage());
		}
	}
}
