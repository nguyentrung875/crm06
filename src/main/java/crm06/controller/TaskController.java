package crm06.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.JsonObject;

import crm06.entity.ProjectEntiy;
import crm06.entity.StatusEntity;
import crm06.entity.TaskEntity;
import crm06.entity.UserEntity;
import crm06.repository.TaskRepository;
import crm06.services.ProjectService;
import crm06.services.ProjectServiceImp;
import crm06.services.TaskService;
import crm06.services.TaskServiceImp;
import crm06.services.UserService;
import crm06.services.UserServiceImp;
import crm06.utils.DateConvertor;
import crm06.utils.Validator;

@WebServlet(name = "taskController", urlPatterns = { "/task", "/task-add", "/task-edit", "/task-delete",
		"/task-details", })
public class TaskController extends HttpServlet {
	private ProjectServiceImp projectService = new ProjectService();
	private TaskServiceImp taskService = new TaskService();
	private UserServiceImp userService = new UserService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();
		switch (path) {
		case "/task-add":
			this.doGetTaskAdd(req, resp);
			break;
		case "/task-edit":
			this.doGetTaskEdit(req, resp);
			break;
		case "/task":
			this.doGetTask(req, resp);
			break;
		case "/task-details":
			this.doGetTaskDetails(req, resp);
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + path);
		}
	}

	private void doGetTask(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession();
		UserEntity currentUser = (UserEntity) session.getAttribute("loginedInfo");
		List<TaskEntity> tasks = taskService.getAllTasks(currentUser);

		req.setAttribute("tasks", tasks);
		req.getRequestDispatcher("task.jsp").forward(req, resp);
	}

	private void doGetTaskAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		UserEntity currentUser = (UserEntity) session.getAttribute("loginedInfo");

		List<ProjectEntiy> projects = projectService.getAllProjects(currentUser);
		List<UserEntity> users = userService.getAllUsers();

		req.setAttribute("projects", projects);
		req.setAttribute("users", users);

		req.getRequestDispatcher("task-add.jsp").forward(req, resp);
	}

	private void doGetTaskEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		UserEntity currentUser = (UserEntity) session.getAttribute("loginedInfo");
		List<ProjectEntiy> projects = projectService.getAllProjects(currentUser);
		
		int id = Validator.parseWithDefault(req.getParameter("id"), -1);
		TaskEntity task = taskService.getTaskById(id);
		List<UserEntity> users = userService.getAllUsers();

		req.setAttribute("editTask", task);
		req.setAttribute("projects", projects);
		req.setAttribute("users", users);

		req.getRequestDispatcher("task-edit.jsp").forward(req, resp);
	}

	private void doGetTaskDetails(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getRequestDispatcher("task-details.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");

		String path = req.getServletPath();
		switch (path) {
		case "/task-add":
			this.doPostTaskAdd(req, resp);
			break;
		case "/task-edit":
			this.doPostTaskEdit(req, resp);
			break;
		case "/task-delete":
			this.doPostTaskDelete(req, resp);
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + path);
		}

	}

	private void doPostTaskAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String taskName = req.getParameter("taskName");
		int projectId = Validator.parseWithDefault(req.getParameter("projectId"), -1);
		ProjectEntiy project = new ProjectEntiy(projectId);
		int userId = Validator.parseWithDefault(req.getParameter("userId"), -1);
		UserEntity user = new UserEntity(userId);
		LocalDateTime startDate = LocalDate.parse(req.getParameter("startDate")).atStartOfDay();
		LocalDateTime endDate = LocalDate.parse(req.getParameter("endDate")).atStartOfDay();
		TaskEntity newTask = new TaskEntity(user, project, new StatusEntity(1), taskName, startDate, endDate);

		boolean isSuccess = taskService.addTask(newTask);

		resp.setContentType("application/json");
		PrintWriter writer = resp.getWriter();
		writer.println(isSuccess);
		writer.close();
	}

	private void doPostTaskEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int idTask = Validator.parseWithDefault(req.getParameter("id"), -1);
		String taskName = req.getParameter("taskName");
		int userId = Validator.parseWithDefault(req.getParameter("userId"), -1);
		LocalDateTime startDate = LocalDate.parse(req.getParameter("startDate")).atStartOfDay();
		LocalDateTime endDate = LocalDate.parse(req.getParameter("endDate")).atStartOfDay();

		TaskEntity updateTask = new TaskEntity();
		updateTask.setId(idTask);
		updateTask.setName(taskName);
		updateTask.setUser(new UserEntity(userId));
		updateTask.setStartDate(startDate);
		updateTask.setEndDate(endDate);
		updateTask.setId(idTask);

		boolean isSuccess = taskService.updateTask(updateTask);
		resp.setContentType("application/json");
		PrintWriter writer = resp.getWriter();
		writer.println(isSuccess);
		writer.close();
	}

	private void doPostTaskDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		int id = Validator.parseWithDefault(req.getParameter("id"), -1);
		boolean isSuccess = taskService.deleteTask(id);
		resp.setContentType("application/json");
		PrintWriter writer = resp.getWriter();
		writer.println(isSuccess);
		writer.close();

	}
}
