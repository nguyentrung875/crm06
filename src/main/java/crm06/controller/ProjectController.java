package crm06.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import crm06.entity.ProjectEntiy;
import crm06.entity.RoleEntity;
import crm06.entity.TaskEntity;
import crm06.entity.UserEntity;
import crm06.repository.UserRepository;
import crm06.services.ProjectService;
import crm06.services.ProjectServiceImp;
import crm06.services.StatusService;
import crm06.services.StatusServiceImp;
import crm06.services.TaskService;
import crm06.services.TaskServiceImp;
import crm06.services.UserService;
import crm06.services.UserServiceImp;
import crm06.utils.DateConvertor;
import crm06.utils.Validator;

@WebServlet(name = "projectController", urlPatterns = { "/project", "/project-add", "/project-edit", "/project-delete",
		"/project-details", })
public class ProjectController extends HttpServlet {
	private ProjectServiceImp projectService = new ProjectService();
	private UserServiceImp userService = new UserService();
	private TaskServiceImp taskService = new TaskService();
	private StatusServiceImp statusService = new StatusService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();
		switch (path) {
		case "/project-add":
			this.doGetProjectAdd(req, resp);
			break;
		case "/project-edit":
			this.doGetProjectEdit(req, resp);
			break;
		case "/project":
			this.doGetProject(req, resp);
			break;
		case "/project-details":
			this.doGetProjectDetails(req, resp);
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + path);
		}
	}

	private void doGetProject(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		UserEntity currentUser = (UserEntity) session.getAttribute("loginedInfo");
		List<ProjectEntiy> projects = projectService.getAllProjects(currentUser);
		
		req.setAttribute("projects", projects);
		// Trả ra giao diện
		req.getRequestDispatcher("project.jsp").forward(req, resp);
	}

	private void doGetProjectAdd(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		List<UserEntity> leaders = userService.getUserByRoleId(3);
		
		req.setAttribute("leaders", leaders);
		req.getRequestDispatcher("project-add.jsp").forward(req, resp);
	}

	private void doGetProjectEdit(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");

		int id = Validator.parseWithDefault(req.getParameter("id"), -1);

		ProjectEntiy projectEntiy = projectService.getProjectById(id);
		req.setAttribute("editProject", projectEntiy);
		
		List<UserEntity> leaders = userService.getUserByRoleId(3);
		req.setAttribute("leaders", leaders);

		req.getRequestDispatcher("project-edit.jsp").forward(req, resp);
	}
	
	private void doGetProjectDetails(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		int projectId = Validator.parseWithDefault(req.getParameter("id"), -1);
		
		ProjectEntiy project = projectService.getProjectById(projectId);
		
		List<TaskEntity> projectTasks = new ArrayList<TaskEntity>();
		List<UserEntity> users = userService.getUserByProject(projectId);
		for (UserEntity user : users) {
			List<TaskEntity> taskList = taskService.getAllTaskByUserAndProject(user.getId(), projectId);
			user.setTasks(taskList);
			projectTasks.addAll(taskList);
		}
		
		LinkedHashMap<String, Integer[]> projectProgress = statusService.calculateProgress(projectTasks);

		req.setAttribute("projectDetails", project);
		req.setAttribute("projectUsers", users);
		req.setAttribute("projectProgress", projectProgress);
		req.getRequestDispatcher("project-details.jsp").forward(req, resp);
	}


	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");

		String path = req.getServletPath();
		switch (path) {
		case "/project-add":
			this.doPostProjectAdd(req, resp);
			break;
		case "/project-edit":
			this.doPostProjectEdit(req, resp);
			break;
		case "/project-delete":
			this.doPostProjectDelete(req, resp);
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + path);
		}

	}

	private void doPostProjectAdd(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		ProjectEntiy newProject = new ProjectEntiy();

		String projectName = req.getParameter("projectName");
		String startDateStr = req.getParameter("projectStartDate");
		String endDateStr = req.getParameter("projectEndDate");
		int leaderId = Validator.parseWithDefault(req.getParameter("leader"), 0);

		newProject.setName(projectName);
		newProject.setStartDate(LocalDate.parse(startDateStr).atStartOfDay());
		newProject.setEndDate(LocalDate.parse(endDateStr).atStartOfDay());
		
//		HttpSession session = req.getSession();
//		UserEntity loginedInfo = (UserEntity) session.getAttribute("loginedInfo");
//		if (loginedInfo.getRole().getName().equals("ADMIN")) {
//			leaderId = Validator.parseWithDefault(req.getParameter("leader"), 0);
//		} else {
//			leaderId = loginedInfo.getId();
//		}
		newProject.setLeader(new UserEntity(leaderId));
		
		boolean isSuccess = projectService.addProject(newProject);

		resp.setContentType("application/json");
		PrintWriter writer = resp.getWriter();
		writer.println(isSuccess);
		writer.close();
	}

	private void doPostProjectEdit(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		ProjectEntiy updateProject = new ProjectEntiy();

		int id = Validator.parseWithDefault(req.getParameter("id"), -1);

		String projectName = req.getParameter("projectName");
		String startDateStr = req.getParameter("projectStartDate");
		String endDateStr = req.getParameter("projectEndDate");
		int leaderId = Validator.parseWithDefault(req.getParameter("leader"), 0);

		updateProject.setId(id);
		updateProject.setName(projectName);
		updateProject.setStartDate(LocalDate.parse(startDateStr).atStartOfDay());
		updateProject.setEndDate(LocalDate.parse(endDateStr).atStartOfDay());
		updateProject.setLeader(new UserEntity(leaderId));
		
		boolean isSuccess = projectService.updateProject(updateProject);
		
		resp.setContentType("application/json");
		PrintWriter writer = resp.getWriter();
		writer.println(isSuccess);
		writer.close();

	}

	private void doPostProjectDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		int idProject = Validator.parseWithDefault(req.getParameter("id"), -1);
		boolean isSuccess = projectService.deleteProject(idProject);
		resp.setContentType("application/json");
		PrintWriter writer = resp.getWriter();
		writer.println(isSuccess);

		writer.close();
	}

}
