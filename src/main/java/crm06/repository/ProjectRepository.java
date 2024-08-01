package crm06.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import crm06.config.MySQLConfig;
import crm06.entity.ProjectEntiy;
import crm06.entity.RoleEntity;
import crm06.entity.UserEntity;
import crm06.utils.DateConvertor;

public class ProjectRepository {
	public int insert(ProjectEntiy project) {
		int rowCount = 0;

		try {
			String sql = "INSERT INTO project (name, start_date, end_date, id_leader)\r\n" + "VALUES (?,?,?,?)";
			Connection connection = MySQLConfig.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, project.getName());
			statement.setString(2, project.getStartDate().toString());
			statement.setString(3, project.getEndDate().toString());
			statement.setInt(4, project.getLeader().getId());
			rowCount = statement.executeUpdate();
			connection.close();
		} catch (Exception e) {
			System.out.println("Error ProjectRepository - insert: " + e.getMessage());
		}
		return rowCount;
	}

	public int deleteById(int id) {
		int rowCount = 0;

		try {
			String sql = "DELETE FROM project p WHERE p.id = ?";
			Connection connection = MySQLConfig.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			rowCount = statement.executeUpdate();
			connection.close();
		} catch (Exception e) {
			System.out.println("Error ProjectRepository - delete: " + e.getMessage());
		}

		return rowCount;
	}

	public int update(ProjectEntiy project) {
		int rowCount = 0;

		String sql = "UPDATE project p\r\n"
				+ "SET p.name = ?,\r\n"
				+ "	p.start_date = ?,\r\n"
				+ "	p.end_date  = ?,\r\n"
				+ "	p.id_leader = ?\r\n"
				+ "WHERE p.id = ?";

		try {
			Connection connection = MySQLConfig.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, project.getName());
			statement.setString(2, project.getStartDate().toString());
			statement.setString(3, project.getEndDate().toString());
			statement.setInt(4, project.getLeader().getId());
			statement.setInt(5, project.getId());
			rowCount = statement.executeUpdate();
			connection.close();
		} catch (Exception e) {
			System.out.println("Error ProjectRepository - update: " + e.getMessage());
		}
		return rowCount;
	}

	public ProjectEntiy getById(int id) {
		ProjectEntiy project = new ProjectEntiy();

		String sql = "SELECT * FROM project p \r\n" + "WHERE p.id = " + id;

		try {

			Connection connection = MySQLConfig.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				project.setId(resultSet.getInt("id"));
				project.setName(resultSet.getString("name"));
				project.setStartDate(resultSet.getTimestamp("start_date").toLocalDateTime());
				project.setEndDate(resultSet.getTimestamp("end_date").toLocalDateTime());
				
				UserEntity leader = new UserEntity(resultSet.getInt("id_leader"));
				project.setLeader(leader);
			}
		} catch (Exception e) {
			System.out.println("Error ProjectRepository - getById: " + e.getMessage());
		}
		return project;
	}

	public List<ProjectEntiy> getByLeader(UserEntity leader) {
		List<ProjectEntiy> projects = new ArrayList<ProjectEntiy>();

		try {
			String sql = "SELECT p.id, p.name, p.start_date, p.end_date, p.id_leader, u.first_name, u.last_name \r\n"
					+ "FROM project p \r\n" + "JOIN users u ON u.id = p.id_leader \r\n WHERE id_leader = ?";
			Connection connection = MySQLConfig.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, leader.getId());
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				ProjectEntiy project = new ProjectEntiy();
				project.setId(resultSet.getInt("id"));
				project.setName(resultSet.getString("name"));
				project.setStartDate(resultSet.getTimestamp("start_date").toLocalDateTime());
				project.setEndDate(resultSet.getTimestamp("end_date").toLocalDateTime());
				
				project.setLeader(leader);
				
				projects.add(project);
			}
			connection.close();
		} catch (SQLException e) {
			System.out.println("Error ProjectRepository - getAll: " + e.getMessage());
		}

		return projects;
	}

	public List<ProjectEntiy> getAll() {
		List<ProjectEntiy> projects = new ArrayList<ProjectEntiy>();

		try {
			String sql = "SELECT p.id, p.name, p.start_date, p.end_date, p.id_leader, u.first_name, u.last_name \r\n"
					+ "FROM project p \r\n" + "JOIN users u ON u.id = p.id_leader \r\n";
			Connection connection = MySQLConfig.getConnection();
			PreparedStatement preparedStatement;
			preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				ProjectEntiy project = new ProjectEntiy();
				project.setId(resultSet.getInt("p.id"));
				project.setName(resultSet.getString("p.name"));
				project.setStartDate(resultSet.getTimestamp("p.start_date").toLocalDateTime());
				project.setEndDate(resultSet.getTimestamp("p.end_date").toLocalDateTime());

				UserEntity leader = new UserEntity();
				leader.setId(resultSet.getInt("p.id_leader"));
				leader.setFirstName(resultSet.getString("u.first_name"));
				leader.setLastName(resultSet.getString("u.last_name"));
				project.setLeader(leader);

				projects.add(project);
			}
			connection.close();
		} catch (SQLException e) {
			System.out.println("Error ProjectRepository - getAll: " + e.getMessage());
		}

		return projects;
	}
}
