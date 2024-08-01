package crm06.entity;

import java.time.LocalDateTime;

public class TaskEntity {
	private int id;
	private UserEntity user;
	private ProjectEntiy project;
	private StatusEntity status;
	private String name;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	
	
	public TaskEntity(UserEntity user, ProjectEntiy project, StatusEntity status, String name, LocalDateTime startDate,
			LocalDateTime endDate) {
		this.user = user;
		this.project = project;
		this.status = status;
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	public TaskEntity() {
	}
	public TaskEntity(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public UserEntity getUser() {
		return user;
	}
	public void setUser(UserEntity user) {
		this.user = user;
	}
	public ProjectEntiy getProject() {
		return project;
	}
	public void setProject(ProjectEntiy project) {
		this.project = project;
	}
	public StatusEntity getStatus() {
		return status;
	}
	public void setStatus(StatusEntity status) {
		this.status = status;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDateTime getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}
	public LocalDateTime getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	
}
