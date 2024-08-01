package crm06.services;

import java.util.List;

import crm06.entity.TaskEntity;
import crm06.entity.UserEntity;
import crm06.repository.TaskRepository;

public class TaskService implements TaskServiceImp{
	private TaskRepository repository = new TaskRepository();

	@Override
	public List<TaskEntity> getAllTasks(UserEntity user) {
		List<TaskEntity> tasks = null;
		if (user.getRole().getName().equals("ADMIN")) {
			tasks = repository.getAll();
		} else if (user.getRole().getName().equals("LEADER")){
			tasks = repository.getByLeader(user);
		}
		return tasks;
	}

	@Override
	public boolean deleteTask(int id) {
		return repository.deleteById(id) > 0;
	}
	
	@Override
	public boolean deleteTaskByProjectId(int projectId) {
		return repository.deleteByProjectId(projectId) >0;
	}
	
	@Override
	public boolean addTask(TaskEntity task) {
		return repository.insert(task) > 0;
	}

	@Override
	public boolean updateTask(TaskEntity task) {
		return repository.update(task) > 0;
	}

	@Override
	public TaskEntity getTaskById(int id) {
		return repository.getById(id);
	}

	@Override
	public boolean updateTaskStatus(TaskEntity task) {
		return repository.updateStatus(task) > 0;
	}

	@Override
	public List<TaskEntity> getAllTaskStatus() {
		return repository.getAllTaskStatus();
	}

	@Override
	public List<TaskEntity> getAllTaskStatusByUserId(int userId) {
		return repository.getAllTaskStatusByUserId(userId);

	}

	@Override
	public List<TaskEntity> getTaskUserDetail(int userId) {
		return repository.getByUserId(userId);
	}

	@Override
	public List<TaskEntity> getAllTaskByUserAndProject(int userId, int projectId) {
		return repository.getByUserAndProject(userId, projectId);
	}



}
