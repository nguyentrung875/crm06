package crm06.services;

import java.util.List;

import crm06.entity.ProjectEntiy;
import crm06.entity.UserEntity;

public interface ProjectServiceImp {
	List<ProjectEntiy> getAllProjects(UserEntity currentUser);
	ProjectEntiy getProjectById(int id);
	boolean deleteProject(int id);
	boolean addProject(ProjectEntiy project);
	boolean updateProject(ProjectEntiy project);
}
