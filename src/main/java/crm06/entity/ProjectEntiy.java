package crm06.entity;

import java.time.LocalDateTime;

public class ProjectEntiy {
	private int id;
	private String name;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private UserEntity leader;
	
	public ProjectEntiy() {
	}
	public ProjectEntiy(int id) {
		this.id = id;
	}
	
	public ProjectEntiy(String name, LocalDateTime startDate, LocalDateTime endDate) {
		super();
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public UserEntity getLeader() {
		return leader;
	}
	public void setLeader(UserEntity leader) {
		this.leader = leader;
	}
	
	
}
