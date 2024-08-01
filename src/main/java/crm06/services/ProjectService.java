package crm06.services;

import java.util.List;

import javax.servlet.http.HttpSession;

import crm06.entity.ProjectEntiy;
import crm06.entity.RoleEntity;
import crm06.entity.UserEntity;
import crm06.repository.ProjectRepository;
import crm06.repository.RoleRepository;
import crm06.repository.TaskRepository;

public class ProjectService implements ProjectServiceImp{
	private ProjectRepository projectRepository = new ProjectRepository();
	private TaskServiceImp taskService = new TaskService();
	@Override
	public List<ProjectEntiy> getAllProjects(UserEntity currentUser) {
		
		List<ProjectEntiy> projects = null;
		if (currentUser.getRole().getName().equals("LEADER")) {
			projects = projectRepository.getByLeader(currentUser);
		} else if (currentUser.getRole().getName().equals("ADMIN")){
			projects = projectRepository.getAll();
		}
		
		return projects;
	}

	@Override
	public boolean deleteProject(int id) {
		taskService.deleteTaskByProjectId(id);
		return projectRepository.deleteById(id) > 0;
	}

	@Override
	public boolean addProject(ProjectEntiy project) {
		return projectRepository.insert(project) > 0;
	}

	@Override
	public boolean updateProject(ProjectEntiy project) {
		return projectRepository.update(project) > 0;
	}

	@Override
	public ProjectEntiy getProjectById(int id) {
		return projectRepository.getById(id);
	}
	
}
