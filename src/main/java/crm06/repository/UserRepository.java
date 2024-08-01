package crm06.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.internal.compiler.apt.dispatch.RoundDispatcher;

import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import com.mysql.cj.protocol.a.MysqlBinaryValueDecoder;

import crm06.config.MySQLConfig;
import crm06.entity.RoleEntity;
import crm06.entity.UserEntity;

public class UserRepository {
	public List<UserEntity> getByRoleId(int roleId) {
		List<UserEntity> users = new ArrayList<UserEntity>();
		
		String sql = "SELECT u.id, u.first_name , u.last_name \r\n"
				+ "FROM users u \r\n"
				+ "WHERE id_role = ?";
		
		try {
			
			Connection connection = MySQLConfig.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, roleId);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				UserEntity user = new UserEntity();
				user.setId(resultSet.getInt("u.id"));
				user.setFirstName(resultSet.getString("u.first_name"));
				user.setLastName(resultSet.getString("u.last_name"));
				users.add(user);
			}
			connection.close();

		} catch (Exception e) {
			System.out.println("Error UserRepository - getByRoleId: " + e.getMessage());
		}

		return users;
	}
	public int updatePassword(UserEntity user) {
		int rowCount = 0;
		String sql = "UPDATE users u\r\n"
				+ "SET u.password = ?\r\n"
				+ "WHERE u.id = ?";
		try {
			Connection connection = MySQLConfig.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, user.getPassword());
			statement.setInt(2, user.getId());
			rowCount = statement.executeUpdate();
		} catch (Exception e) {
			System.out.println("Error UserRepository - updatePassword: " + e.getMessage());
		}
		return rowCount;
	} 
	public List<UserEntity> getUserByProject(int projectId) {
		List<UserEntity> users = new ArrayList<UserEntity>();
		String sql = "SELECT DISTINCT (u.id), u.first_name , u.last_name \r\n"
				+ "FROM users u\r\n"
				+ "JOIN task t ON u.id = t.id_user \r\n"
				+ "WHERE t.id_project = " + projectId;
		
		try {
			Connection connection = MySQLConfig.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				UserEntity user = new UserEntity();
				user.setId(resultSet.getInt("u.id"));
				user.setFirstName(resultSet.getString("u.first_name"));
				user.setLastName(resultSet.getString("u.last_name"));
				users.add(user);
			}
		} catch (Exception e) {
			System.out.println("Error UserRepository - getUserByProject: " + e.getMessage());
		}
		
		return users;
	}
	public UserEntity getUserByUserNameAndPassword(String username, String password) {
		UserEntity user = null;

		String sql = "SELECT u.id, u.username, u.first_name, u.last_name, r.name\r\n" + "FROM users u \r\n"
				+ "JOIN roles r ON u.id_role = r.id\r\n" + "WHERE u.username = ? AND u.password = ? ";

		try {
			Connection connection = MySQLConfig.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, username);
			statement.setString(2, password);

			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				user = new UserEntity();
				user.setId(resultSet.getInt("id"));
				user.setUsername(resultSet.getString("username"));
				user.setFirstName(resultSet.getString("first_name"));
				user.setLastName(resultSet.getString("last_name"));

				RoleEntity role = new RoleEntity();
				role.setName(resultSet.getString("name"));
				user.setRole(role);
			}

			connection.close();
		} catch (Exception e) {
			System.out.println("Error UserRepository - getUserByUserNameAndPassword: " + e.getMessage());
		}

		return user;
	}

	public UserEntity getUserById(int id) {
		UserEntity user = null;

		String sql = "SELECT u.id, u.username, u.password, u.first_name , u.last_name , u.phone , r.id as id_role , r.name\r\n"
				+ "FROM users u \r\n" + "JOIN roles r ON u.id_role = r.id\r\n" + "WHERE u.id =" + id;

		try {

			Connection connection = MySQLConfig.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				user = new UserEntity();
				user.setId(resultSet.getInt("id"));
				user.setUsername(resultSet.getString("username"));
				user.setPassword(resultSet.getString("password"));
				user.setFirstName(resultSet.getString("first_name"));
				user.setLastName(resultSet.getString("last_name"));
				user.setPhone(resultSet.getString("phone"));

				RoleEntity role = new RoleEntity();
				role.setId(resultSet.getInt("id_role"));
				role.setName(resultSet.getString("name"));
				user.setRole(role);
			}
		} catch (Exception e) {
			System.out.println("Error UserRepository - getById: " + e.getMessage());
		}

		return user;
	}

	public int update(UserEntity user) {
		int rowCount = 0;

		String sql = "UPDATE users u\r\n" + "SET u.username = ?,\r\n" + "	u.first_name = ?,\r\n"
				+ "	u.last_name = ?,\r\n" + "	u.phone = ?,\r\n" + "	u.id_role = ?\r\n" + "WHERE u.id = ?";

		try {
			Connection connection = MySQLConfig.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, user.getUsername());
			statement.setString(2, user.getFirstName());
			statement.setString(3, user.getLastName());
			statement.setString(4, user.getPhone());
			statement.setInt(5, user.getRole().getId());
			statement.setInt(6, user.getId());
			rowCount = statement.executeUpdate();
			connection.close();
		} catch (Exception e) {
			System.out.println("Error UserRepository - update: " + e.getMessage());
		}
		return rowCount;
	}

	public int insert(UserEntity user) {
		int rowCount = 0;

		try {
			String sql = "INSERT INTO users (first_name, last_name, username, password, phone, id_role)\r\n"
					+ "VALUES (?,?,?,?,?,?)";
			Connection connection = MySQLConfig.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, user.getFirstName());
			statement.setString(2, user.getLastName());
			statement.setString(3, user.getUsername());
			statement.setString(4, user.getPassword());
			statement.setString(5, user.getPhone());
			statement.setInt(6, user.getRole().getId());
			rowCount = statement.executeUpdate();
			connection.close();
		} catch (Exception e) {
			System.out.println("Error UserRepository - insert: " + e.getMessage());
		}

		return rowCount;
	}

	public int deleteById(int id) {
		// repository chỉ trả về kqua, ko thực hiện logic code
		int rowCount = 0;

		try {
			String sql = "DELETE FROM users u WHERE u.id = ?";
			Connection connection = MySQLConfig.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			rowCount = statement.executeUpdate();

		} catch (Exception e) {
			System.out.println("Error UserRepository - deleteById: " + e.getMessage());
		}

		return rowCount;
	}

	public List<UserEntity> getAll() {
		List<UserEntity> users = new ArrayList<UserEntity>();

		try {
			String sql = "SELECT u.id, u.first_name , u.last_name , u.username , r.name \r\n" + "FROM users u \r\n"
					+ "JOIN roles r ON u.id_role = r.id ";
			Connection connection = MySQLConfig.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				UserEntity user = new UserEntity();
				user.setId(resultSet.getInt("id"));
				user.setUsername(resultSet.getString("username"));
				user.setFirstName(resultSet.getString("first_name"));
				user.setLastName(resultSet.getString("last_name"));

				RoleEntity role = new RoleEntity();
				role.setName(resultSet.getString("name"));
				user.setRole(role);

				users.add(user);
			}
			connection.close();

		} catch (Exception e) {
			System.out.println("Error UserRepository - getAll: " + e.getMessage());
		}

		return users;
	}

}
