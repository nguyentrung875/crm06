package crm06.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import crm06.entity.RoleEntity;
import crm06.entity.StatusEntity;
import crm06.entity.TaskEntity;
import crm06.entity.UserEntity;
import crm06.services.RoleService;
import crm06.services.RoleServiceImp;
import crm06.services.StatusService;
import crm06.services.TaskService;
import crm06.services.TaskServiceImp;
import crm06.services.UserService;
import crm06.services.UserServiceImp;
import crm06.utils.PasswordEncoder;
import crm06.utils.Validator;

@WebServlet(name = "userController", urlPatterns = { "/user-delete", "/user-edit", "/user-add", "/user",
		"/user-details", "/profile", "/profile-edit", "/profile-password" })
public class UserController extends HttpServlet {

	private UserServiceImp userService = new UserService();
	private RoleServiceImp roleService = new RoleService();
	private TaskServiceImp taskService = new TaskService();
	private StatusService statusService = new StatusService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String path = req.getServletPath();
		switch (path) {
		case "/user-details":
			this.doGetUserDetail(req, resp);
			break;
		case "/user-edit":
			this.doGetUserEdit(req, resp);
			break;
		case "/user-add":
			this.doGetUserAdd(req, resp);
			break;
		case "/user":
			this.doGetUser(req, resp);
			break;
		case "/profile":
			this.doGetProfile(req, resp);
			break;
		case "/profile-edit":
			this.doGetProfileEdit(req, resp);
			break;
		case "/profile-password":
			this.doGetChangePassword(req, resp);
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + path);
		}

	}
	
	private void doGetChangePassword(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("profile-password.jsp").forward(req, resp);
	}
	
	private void doGetProfileEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		int id = Validator.parseWithDefault(req.getParameter("id"), -1);
		TaskEntity editTask = taskService.getTaskById(id);
		List<StatusEntity> statusList = statusService.getAllStatus();
		
		req.setAttribute("editTask", editTask);
		req.setAttribute("statusList", statusList);
		
		req.getRequestDispatcher("profile-edit.jsp").forward(req, resp);

	}
	private void doGetProfile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		UserEntity loginedInfo = (UserEntity) session.getAttribute("loginedInfo");
		
		UserEntity userDetail = userService.getUserById(loginedInfo.getId());
		List<TaskEntity> taskList = taskService.getTaskUserDetail(loginedInfo.getId());
		userDetail.setTasks(taskList);
		LinkedHashMap<String, Integer[]> taskProgress = statusService.calculateProgress(taskList);

		req.setAttribute("userDetail", userDetail);
		req.setAttribute("taskProgress", taskProgress);
		req.getRequestDispatcher("profile.jsp").forward(req, resp);

	}
	private void doGetUserDetail(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		int userId = Validator.parseWithDefault(req.getParameter("id"), -1);
		UserEntity userDetail = userService.getUserById(userId);
		List<TaskEntity> taskList = taskService.getTaskUserDetail(userId);
		userDetail.setTasks(taskList);
		LinkedHashMap<String, Integer[]> taskProgress = statusService.calculateProgress(taskList);
				
		req.setAttribute("userDetail", userDetail);
		req.setAttribute("taskProgress", taskProgress);
		req.getRequestDispatcher("user-details.jsp").forward(req, resp);
	}
	private void doGetUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<UserEntity> users = userService.getAllUsers();

		req.setAttribute("users", users);
		// Trả ra giao diện
		req.getRequestDispatcher("user-table.jsp").forward(req, resp);
	}
	private void doGetUserAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<RoleEntity> roles = roleService.getAllRoles();

		req.setAttribute("roleList", roles);
		// Lấy thông tin user cần edit hiển thị lên giao diện form

		// Trả ra giao diện
		req.getRequestDispatcher("user-add.jsp").forward(req, resp);
	}
	private void doGetUserEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");

		List<RoleEntity> roles = roleService.getAllRoles();
		req.setAttribute("roleList", roles);
		
		int id = Validator.parseWithDefault(req.getParameter("id"), -1);
		UserEntity user = userService.getUserById(id);
		req.setAttribute("editUser", user);

		req.getRequestDispatcher("user-edit.jsp").forward(req, resp);
	}
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String path = req.getServletPath();
		switch (path) {
		case "/user-edit":
			this.doPostUserEdit(req, resp);
			break;
		case "/user-add":
			this.doPostUserAdd(req, resp);
			break;
		case "/user-delete":
			this.doPostUserDelete(req, resp);
			break;
		case "/profile-edit":
			this.doPostProfileEdit(req, resp);
			break;
		case "/profile-password":
			this.doPostChangePassword(req, resp);
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + path);
		}

	}
	private void doPostChangePassword(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String oldPaswword = PasswordEncoder.encode(req.getParameter("oldPassword"));
		String newPassword = PasswordEncoder.encode(req.getParameter("confirmPassword"));
		
		HttpSession session = req.getSession(); 
		UserEntity loginedUser = (UserEntity) session.getAttribute("loginedInfo");
		loginedUser.setPassword(newPassword);
		
		boolean isSuccess = userService.changeUserPassword(oldPaswword, loginedUser);
		resp.setContentType("application/json");
		PrintWriter writer = resp.getWriter();
		writer.println(isSuccess);
		writer.close();
	}
	
	private void doPostProfileEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int taskId = Validator.parseWithDefault(req.getParameter("id"), -1);	
		int statusId = Validator.parseWithDefault(req.getParameter("taskStatus"), -1);
		
		TaskEntity task = new TaskEntity();
		task.setId(taskId);
		task.setStatus(new StatusEntity(statusId));
		
		boolean isSuccess = taskService.updateTaskStatus(task);
		resp.setContentType("application/json");
		PrintWriter writer = resp.getWriter();
		writer.println(isSuccess);
		writer.close();
	}
	private void doPostUserAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");
		String email = req.getParameter("email");
		String password = PasswordEncoder.encode(req.getParameter("password"));
		String phone = req.getParameter("phone");
		int roleId = Validator.parseWithDefault(req.getParameter("role"), -1);

		RoleEntity role = new RoleEntity(roleId);
		UserEntity newUser = new UserEntity(email, password, firstName, lastName, phone, role);

		boolean isSuccess = userService.addUser(newUser);

		resp.setContentType("application/json");
		PrintWriter writer = resp.getWriter();
		writer.println(isSuccess);
		writer.close();
	}
	private void doPostUserEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		UserEntity updateUser = new UserEntity();
		int id = Validator.parseWithDefault(req.getParameter("id"), -1);

		updateUser.setId(id);
		updateUser.setUsername(req.getParameter("email"));
		updateUser.setFirstName(req.getParameter("firstName"));
		updateUser.setLastName(req.getParameter("lastName"));
		updateUser.setPhone(req.getParameter("phone"));
		updateUser.setRole(new RoleEntity(Integer.parseInt(req.getParameter("role"))));

		boolean isSuccess = userService.updateUser(updateUser);

		resp.setContentType("application/json");
		PrintWriter writer = resp.getWriter();
		writer.println(isSuccess);
		writer.close();
	}
	private void doPostUserDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		int idUser = Validator.parseWithDefault(req.getParameter("id"), -1);
		boolean isSuccess = userService.deleteUser(idUser);
		
		resp.setContentType("application/json");
		PrintWriter writer = resp.getWriter();
		writer.println(isSuccess);
		writer.close();
	}

}

////BUỔI 27: Khai báo cookie
//Cookie cookie = new Cookie("demo", "Hellocookie");
//Cookie cookie2 = new Cookie("demo2", "Hellocookie2");
//
////Gửi cookie vào htpp response
//resp.addCookie(cookie);
//resp.addCookie(cookie2);

//Cookie[] cookies = req.getCookies();
//for (Cookie cookie : cookies) {
//	//Lấy tên cookie
//	String name = cookie.getName();
//	//Lấy giá trị cookie
//	String value = cookie.getValue();
//	
//	if (name.equals("demo")) {
//		System.out.println("Giá trị cookie: " + value);
//		break;
//	}
//	System.out.println("ktra");
//}