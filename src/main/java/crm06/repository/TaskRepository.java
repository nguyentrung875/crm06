package crm06.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.StatementEvent;

import crm06.config.MySQLConfig;
import crm06.entity.ProjectEntiy;
import crm06.entity.RoleEntity;
import crm06.entity.StatusEntity;
import crm06.entity.TaskEntity;
import crm06.entity.UserEntity;
import crm06.utils.DateConvertor;

public class TaskRepository {
	public List<TaskEntity> getByUserAndProject(int userId, int projectId) {
		List<TaskEntity> taskList = new ArrayList<TaskEntity>();
		String sql = "SELECT t.id , t.name , t.start_date , t.end_date , s.id, s.name \r\n"
				+ "FROM task t\r\n"
				+ "JOIN users u ON u.id = t.id_user \r\n"
				+ "JOIN status s ON s.id = t.id_status\r\n"
				+ "WHERE t.id_user = ? AND t.id_project = ? ";
		try {
			Connection connection = MySQLConfig.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, userId);
			statement.setInt(2, projectId);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				TaskEntity task = new TaskEntity();
				task.setId(resultSet.getInt("t.id"));
				task.setName(resultSet.getString("t.name"));
				task.setStartDate(resultSet.getTimestamp("t.start_date").toLocalDateTime());
				task.setEndDate(resultSet.getTimestamp("t.end_date").toLocalDateTime());
				
				StatusEntity statusEntity = new StatusEntity();
				statusEntity.setId(resultSet.getInt("s.id"));
				statusEntity.setName(resultSet.getString("s.name"));
				task.setStatus(statusEntity);
				taskList.add(task);
			}
		} catch (Exception e) {
			System.out.println("Error TaskRepository - getByUserAndProject: " + e.getMessage());
		}
		return taskList;
	}
	
	public List<TaskEntity> getAllTaskStatusByUserId(int userId) {
		List<TaskEntity> taskList = new ArrayList<TaskEntity>();
		
		String sql = "SELECT t.id_status \r\n"
				+ "FROM task t \r\n"
				+ "WHERE t.id_user = ?";
		
		try {
			Connection connection = MySQLConfig.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, userId);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				TaskEntity task = new TaskEntity();
				task.setStatus(new StatusEntity(resultSet.getInt("id_status")));
				taskList.add(task);
			}
		} catch (Exception e) {
			System.out.println("Error TaskRepository - getAllTaskStatus: " + e.getMessage());
		}
		return taskList;
	}
	
	public List<TaskEntity> getAllTaskStatus() {
		List<TaskEntity> taskList = new ArrayList<TaskEntity>();
		
		String sql = "SELECT id_status FROM task t";
		
		try {
			Connection connection = MySQLConfig.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				TaskEntity task = new TaskEntity();
				task.setStatus(new StatusEntity(resultSet.getInt("id_status")));
				taskList.add(task);
			}
		} catch (Exception e) {
			System.out.println("Error TaskRepository - getAllTaskStatus: " + e.getMessage());
		}
		return taskList;
	}
	public int updateStatus(TaskEntity task) {
		int rowCount = 0;

		String sql = "UPDATE task t\r\n"
				+ "SET t.id_status = ?\r\n"
				+ "WHERE t.id = ? ";
		
		try {
			Connection connection = MySQLConfig.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, task.getStatus().getId());
			statement.setInt(2, task.getId());
			rowCount = statement.executeUpdate();
			connection.close();
		} catch (Exception e) {
			System.out.println("Error TaskRepository - updateStatus: " + e.getMessage());
		}
		return rowCount;
	}
	
	public List<TaskEntity> getByUserId(int userId) {
		List<TaskEntity> taskList = new ArrayList<TaskEntity>();
		String sql = "SELECT t.id , t.name , p.name, t.start_date , t.end_date , s.id, s.name\r\n"
				+ "FROM task t\r\n"
				+ "LEFT JOIN project p ON p.id = t.id_project \r\n"
				+ "LEFT JOIN users u ON u.id = t.id_user \r\n"
				+ "LEFT JOIN status s ON s.id = t.id_status \r\n"
				+ "WHERE u.id =" + userId;
		
		try {
			Connection connection = MySQLConfig.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				TaskEntity task = new TaskEntity();
				task.setId(resultSet.getInt("id"));
				task.setName(resultSet.getString("t.name"));
				
				ProjectEntiy project = new ProjectEntiy();
				project.setName(resultSet.getString("p.name"));
				task.setProject(project);
				
				StatusEntity status = new StatusEntity();
				status.setId(resultSet.getInt("s.id"));
				status.setName(resultSet.getString("s.name"));
				task.setStatus(status);
				
				task.setStartDate(resultSet.getTimestamp("start_date").toLocalDateTime());;
				task.setEndDate(resultSet.getTimestamp("end_date").toLocalDateTime());;
				taskList.add(task);
			}
			connection.close();
		} catch (Exception e) {
			System.out.println("Error TaskRepository - getById: " + e.getMessage());
		}
		return taskList;
	}
	
	public TaskEntity getById(int id) {
		TaskEntity task = new TaskEntity();
		String sql = "SELECT t.id , t.name , p.name , u.last_name , t.start_date , t.end_date , s.id, s.name \r\n"
				+ "FROM task t\r\n"
				+ "LEFT JOIN project p ON p.id = t.id_project \r\n"
				+ "LEFT JOIN users u ON u.id = t.id_user \r\n"
				+ "LEFT JOIN status s ON s.id = t.id_status \r\n"
				+ "WHERE t.id =" + id;
		
		try {
			Connection connection = MySQLConfig.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				task.setId(resultSet.getInt("id"));
				task.setName(resultSet.getString("t.name"));
				
				ProjectEntiy project = new ProjectEntiy();
				project.setName(resultSet.getString("p.name"));
				task.setProject(project);
				
				UserEntity user = new UserEntity();
				user.setLastName(resultSet.getString("last_name"));
				task.setUser(user);
				
				StatusEntity status = new StatusEntity();
				status.setId(resultSet.getInt("s.id"));
				status.setName(resultSet.getString("s.name"));
				task.setStatus(status);
				
				task.setStartDate(resultSet.getTimestamp("start_date").toLocalDateTime());;
				task.setEndDate(resultSet.getTimestamp("end_date").toLocalDateTime());;
			}
			connection.close();
		} catch (Exception e) {
			System.out.println("Error TaskRepository - getById: " + e.getMessage());
		}
		return task;
	}
	public int insert(TaskEntity task) {
		int rowCount = 0;

		try {
			String sql = "INSERT INTO task (id_user, id_project, id_status, name, start_date, end_date)\r\n"
					+ "VALUES (? , ?, ?, ?, ?, ?)";
			Connection connection = MySQLConfig.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, task.getUser().getId());
			statement.setInt(2, task.getProject().getId());
			statement.setInt(3, task.getStatus().getId());
			statement.setString(4, task.getName());
			statement.setString(5, task.getStartDate().toString());
			statement.setString(6, task.getEndDate().toString());
			rowCount = statement.executeUpdate();
			connection.close();
		} catch (Exception e) {
			System.out.println("Error TaskRepository - insert: " + e.getMessage());
		}
		return rowCount;
	}
	
	public int deleteByProjectId(int projectId) {
		int rowCount = 0;
		String sql = "DELETE FROM task t WHERE id_project = ?";

		try {
			Connection connection = MySQLConfig.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, projectId);
			rowCount = statement.executeUpdate();
			connection.close();
		} catch (Exception e) {
			System.out.println("Error TaskRepository - deleteByProjectId: " + e.getMessage());
		}

		return rowCount;
	}

	public int deleteById(int id) {
		int rowCount = 0;

		try {
			String sql = "DELETE FROM task WHERE id = ?";
			Connection connection = MySQLConfig.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			rowCount = statement.executeUpdate();
			connection.close();
		} catch (Exception e) {
			System.out.println("Error TaskRepository - delete: " + e.getMessage());
		}

		return rowCount;
	}

	public int update(TaskEntity task) {
		int rowCount = 0;

		String sql = "UPDATE task t\r\n"
				+ "SET t.id_user = ?,\r\n"
				+ "	t.name = ?,\r\n"
				+ "	t.start_date = ?,\r\n"
				+ "	t.end_date = ?\r\n"
				+ "WHERE t.id = ?";

		try {
			Connection connection = MySQLConfig.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, task.getUser().getId());
			statement.setString(2, task.getName());
			statement.setString(3, task.getStartDate().toString());
			statement.setString(4, task.getEndDate().toString());
			statement.setInt(5, task.getId());
			rowCount = statement.executeUpdate();
			connection.close();
		} catch (Exception e) {
			System.out.println("Error TaskRepository - update: " + e.getMessage());
		}
		return rowCount;
	}
	
	public List<TaskEntity> getByLeader(UserEntity leader) {
		List<TaskEntity> tasks = new ArrayList<TaskEntity>();

		try {
			String sql = "SELECT t.id , t.name , p.name, u.last_name , t.start_date , t.end_date , s.id, s.name \r\n"
					+ "FROM task t\r\n"
					+ "LEFT JOIN project p ON p.id = t.id_project \r\n"
					+ "LEFT JOIN users u ON u.id = t.id_user \r\n"
					+ "LEFT JOIN status s ON s.id = t.id_status\r\n"
					+ "WHERE p.id_leader = ?;";
			Connection connection = MySQLConfig.getConnection();
			PreparedStatement preparedStatement;
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, leader.getId());
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				TaskEntity task = new TaskEntity();
				task.setId(resultSet.getInt("id"));
				task.setName(resultSet.getString("t.name"));
				
				ProjectEntiy project = new ProjectEntiy();
				project.setName(resultSet.getString("p.name"));
				task.setProject(project);
				
				UserEntity user = new UserEntity();
				user.setLastName(resultSet.getString("u.last_name"));
				task.setUser(user);
				
				StatusEntity status = new StatusEntity();
				status.setName(resultSet.getString("s.name"));
				task.setStatus(status);
				
				task.setStartDate(resultSet.getTimestamp("start_date").toLocalDateTime());;
				task.setEndDate(resultSet.getTimestamp("end_date").toLocalDateTime());;
				tasks.add(task);
			}
			connection.close();
		} catch (SQLException e) {
			System.out.println("Error TaskRepository - getAll: "+ e.getMessage());
		}

		return tasks;
	}

	public List<TaskEntity> getAll() {
		List<TaskEntity> tasks = new ArrayList<TaskEntity>();

		try {
			String sql = "SELECT t.id , t.name , p.name , u.last_name , t.start_date , t.end_date , s.name\r\n"
					+ "FROM task t\r\n"
					+ "LEFT JOIN project p ON p.id = t.id_project \r\n"
					+ "LEFT JOIN users u ON u.id = t.id_user \r\n"
					+ "LEFT JOIN status s ON s.id = t.id_status ;";
			Connection connection = MySQLConfig.getConnection();
			PreparedStatement preparedStatement;
			preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				TaskEntity task = new TaskEntity();
				task.setId(resultSet.getInt("id"));
				task.setName(resultSet.getString("t.name"));
				
				ProjectEntiy project = new ProjectEntiy();
				project.setName(resultSet.getString("p.name"));
				task.setProject(project);
				
				UserEntity user = new UserEntity();
				user.setLastName(resultSet.getString("u.last_name"));
				task.setUser(user);
				
				StatusEntity status = new StatusEntity();
				status.setName(resultSet.getString("s.name"));
				task.setStatus(status);
				
				task.setStartDate(resultSet.getTimestamp("start_date").toLocalDateTime());;
				task.setEndDate(resultSet.getTimestamp("end_date").toLocalDateTime());;
				tasks.add(task);
			}
			connection.close();
		} catch (SQLException e) {
			System.out.println("Error TaskRepository - getAll: "+ e.getMessage());
		}

		return tasks;
	}
}
