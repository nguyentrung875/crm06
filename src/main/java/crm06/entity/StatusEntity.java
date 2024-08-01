package crm06.entity;

public class StatusEntity {
	private int id;
	private String name;
	
	
	public StatusEntity() {
	}
	public StatusEntity(int id) {
		this.id = id;
	}
	public StatusEntity(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
