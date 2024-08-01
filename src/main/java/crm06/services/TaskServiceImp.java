package crm06.services;

import java.util.List;

import crm06.entity.ProjectEntiy;
import crm06.entity.TaskEntity;
import crm06.entity.UserEntity;

public interface TaskServiceImp {
	List<TaskEntity> getAllTaskByUserAndProject(int userId, int projectId);
	List<TaskEntity> getAllTaskStatusByUserId(int userId);
	List<TaskEntity> getAllTaskStatus();
	List<TaskEntity> getTaskUserDetail(int userId);
	boolean updateTaskStatus(TaskEntity task);
	TaskEntity getTaskById(int id);
	List<TaskEntity> getAllTasks(UserEntity leader);
	boolean deleteTaskByProjectId(int projectId);
	boolean deleteTask(int id);
	boolean addTask(TaskEntity task);
	boolean updateTask(TaskEntity task);
}
