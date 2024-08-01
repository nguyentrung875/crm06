package crm06.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import crm06.config.MySQLConfig;
import crm06.entity.RoleEntity;
import crm06.entity.StatusEntity;
import crm06.entity.UserEntity;

public class StatusRepository {

	public List<StatusEntity> getAll() {
		List<StatusEntity> statusList = new ArrayList<StatusEntity>();

		try {
			String sql = "SELECT * FROM status";
			Connection connection = MySQLConfig.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				StatusEntity status = new StatusEntity();
				status.setId(resultSet.getInt("id"));
				status.setName(resultSet.getString("name"));
				statusList.add(status);
			}
			connection.close();

		} catch (Exception e) {
			System.out.println("Error StatusRepository - getAll: " + e.getMessage());
		}
		return statusList;
	}
}
