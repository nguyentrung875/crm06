package crm06.entity;

import java.util.HashMap;
import java.util.List;

public class UserEntity {
	private int id;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String phone;
	private RoleEntity role;
	private List<TaskEntity> tasks;

	
	public UserEntity() {
	}
	public UserEntity(int id) {
		this.id = id;
	}
	public UserEntity(String username, String password, String firstName, String lastName, String phone,
			RoleEntity role) {
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.role = role;
	}
	
	
	public List<TaskEntity> getTasks() {
		return tasks;
	}
	public void setTasks(List<TaskEntity> tasks) {
		this.tasks = tasks;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public RoleEntity getRole() {
		return role;
	}
	public void setRole(RoleEntity role) {
		this.role = role;
	}
	
}
