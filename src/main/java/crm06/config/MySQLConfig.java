package crm06.config;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySQLConfig {
	private static String url = "jdbc:mysql://localhost:3307/crmapp";
	private static String username = "root";
	private static String password = "875879";

	// Mở kết nối tới CSDL
	public static Connection getConnection() {
		Connection connection = null;
		
		// Khai báo driver sử dụng cho JDBC (lên mạng search)
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			System.out.println("Lỗi không thể kết nối CSDL " + e.getMessage());
		}

		return connection;
	}
}
