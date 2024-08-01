package crm06.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import crm06.entity.StatusEntity;
import crm06.entity.TaskEntity;
import crm06.repository.StatusRepository;

public class StatusService implements StatusServiceImp {
	private StatusRepository repository = new StatusRepository();
	
	public List<StatusEntity> getAllStatus() {
		return repository.getAll();
	}
	
	public LinkedHashMap<String,Integer[]> calculateProgress(List<TaskEntity> taskList) {
		LinkedHashMap<String,Integer[]> statusPercent = new LinkedHashMap<String,Integer[]>();
		
		int totalTask = taskList.size();
	    List<StatusEntity> statusList = repository.getAll();
	    
		for (StatusEntity status : statusList) {
			int countTask = 0;

			for (TaskEntity task : taskList) {
				if (task.getStatus().getId() == status.getId()) {
					countTask++;
				}
			}
			
			int percent = (int) Math.round(countTask * 100.0/totalTask);
			statusPercent.put(status.getName(), new Integer[]{countTask, percent});
		}
		
		return statusPercent;
	}
}
