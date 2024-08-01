package crm06.services;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import crm06.entity.StatusEntity;
import crm06.entity.TaskEntity;

public interface StatusServiceImp {
	public List<StatusEntity> getAllStatus();
	public LinkedHashMap<String,Integer[]> calculateProgress(List<TaskEntity> taskList);

}
