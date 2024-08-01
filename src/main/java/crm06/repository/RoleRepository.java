package crm06.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import crm06.config.MySQLConfig;
import crm06.entity.RoleEntity;

public class RoleRepository {
	public int insert(RoleEntity role) {
		int rowCount = 0;

		try {
			String sql = "INSERT INTO roles (name, description) \r\n"
					+ "VALUES (?,?)";
			Connection connection = MySQLConfig.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, role.getName());
			statement.setString(2, role.getDescription());
			rowCount = statement.executeUpdate();
			connection.close();
		} catch (Exception e) {
			System.out.println("Error RoleRepository - insert: " + e.getMessage());
		}
		return rowCount;
	}

	public int deleteById(int id) {
		int rowCount = 0;

		try {
			String sql = "DELETE FROM roles r WHERE r.id = ?";
			Connection connection = MySQLConfig.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			rowCount = statement.executeUpdate();
			connection.close();
		} catch (Exception e) {
			System.out.println("Error RoleRepository - delete: " + e.getMessage());
		}

		return rowCount;
	}

	public int update(RoleEntity request) {
		int rowCount = 0;

		String sql = "UPDATE roles r\r\n" + "SET r.name  = ? , r.description = ?" + "WHERE r.id = ?";

		try {
			Connection connection = MySQLConfig.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, request.getName());
			statement.setString(2, request.getDescription());
			statement.setInt(3, request.getId());
			rowCount = statement.executeUpdate();
			connection.close();
		} catch (Exception e) {
			System.out.println("Error RoleRepository - update: " + e.getMessage());
		}
		return rowCount;
	}
	
	public RoleEntity getById(int id) {

		RoleEntity role = new RoleEntity();
		String sql = "SELECT * FROM roles WHERE id = " + id;
		
		try {
			Connection connection = MySQLConfig.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				role.setId(resultSet.getInt("id"));
				role.setName(resultSet.getString("name"));
				role.setDescription(resultSet.getString("description"));
			}
			connection.close();
		} catch (Exception e) {
			System.out.println("Error RoleRepository - getById: " + e.getMessage());
		}
		return role;
	}

	public List<RoleEntity> getAll() {
		List<RoleEntity> roles = new ArrayList<RoleEntity>();

		try {
			String sql = "SELECT * FROM roles";
			Connection connection = MySQLConfig.getConnection();
			PreparedStatement preparedStatement;
			preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				RoleEntity role = new RoleEntity();
				role.setId(resultSet.getInt("id"));
				role.setName(resultSet.getString("name"));
				role.setDescription(resultSet.getString("description"));
				roles.add(role);
			}
			connection.close();
		} catch (SQLException e) {
			System.out.println("Error RoleRepository - getAll: " + e.getMessage());
		}

		return roles;
	}
}
