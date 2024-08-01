package crm06.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crm06.entity.TaskEntity;
import crm06.services.StatusService;
import crm06.services.StatusServiceImp;
import crm06.services.TaskService;
import crm06.services.TaskServiceImp;

@WebServlet(name = "dashboardController" , urlPatterns = {"", "/index"})
public class DashboardController extends HttpServlet{
	private TaskServiceImp taskService = new TaskService();
	private StatusServiceImp statusService = new StatusService(); 
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();
		switch (path) {
		case "":
			List<TaskEntity> tasks = taskService.getAllTaskStatus();
			LinkedHashMap<String, Integer[]> taskProgess = statusService.calculateProgress(tasks);
			
			req.setAttribute("taskProgess", taskProgess);
			
			req.getRequestDispatcher("index.jsp").forward(req, resp);
			break;
		case "/index":
			resp.sendRedirect(req.getContextPath());
			break;

		default:
			throw new IllegalArgumentException("Unexpected value: " + path);
		}
	}
}
