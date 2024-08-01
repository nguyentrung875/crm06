package crm06.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crm06.config.MySQLConfig;
import crm06.entity.RoleEntity;
import crm06.entity.UserEntity;
import crm06.services.RoleService;
import crm06.services.RoleServiceImp;
import crm06.utils.Validator;

@WebServlet(name = "roleController", urlPatterns = { "/role-add", "/role-edit", "/role-delete", "/role",
		"/role-detail" })
public class RoleController extends HttpServlet {
	private RoleServiceImp roleService = new RoleService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();
		switch (path) {
		case "/role-add":
			this.doGetRoleAdd(req, resp);
			break;
		case "/role-edit":
			this.doGetRoleEdit(req, resp);
			break;
		case "/role":
			this.doGetRole(req, resp);
			break;
		case "/role-detail":
			this.doGetRoleDetail(req, resp);
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + path);
		}
	}

	protected void doGetRoleAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("role-add.jsp").forward(req, resp);
	}

	protected void doGetRoleEdit(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		int id = Validator.parseWithDefault(req.getParameter("id"), -1);

		RoleEntity editRole = roleService.getRoleById(id);
		req.setAttribute("editRole", editRole);

		req.getRequestDispatcher("role-edit.jsp").forward(req, resp);

	}

	protected void doGetRole(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<RoleEntity> roles = roleService.getAllRoles();
		req.setAttribute("roles", roles);
		req.getRequestDispatcher("role-table.jsp").forward(req, resp);
	}

	protected void doGetRoleDetail(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getRequestDispatcher("role-table.jsp").forward(req, resp);

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String path = req.getServletPath();
		switch (path) {
		case "/role-add":
			this.doPostRoleAdd(req, resp);
			break;
		case "/role-edit":
			this.doPostRoleEdit(req, resp);
			break;
		case "/role-delete":
			this.doPostRoleDelete(req, resp);
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + path);
		}

	}

	protected void doPostRoleAdd(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		RoleEntity role = new RoleEntity();
		role.setName(req.getParameter("roleName"));
		role.setDescription(req.getParameter("description"));

		boolean isSuccess = roleService.addRole(role);

		resp.setContentType("application/json");
		PrintWriter writer = resp.getWriter();
		writer.println(isSuccess);
		writer.close();

	}

	protected void doPostRoleEdit(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		RoleEntity updateRole = new RoleEntity();

		int id = Validator.parseWithDefault(req.getParameter("id"), -1);

		updateRole.setId(id);
		updateRole.setName(req.getParameter("roleName"));
		updateRole.setDescription(req.getParameter("description"));

		boolean isSuccess = roleService.updateRole(updateRole);

		resp.setContentType("application/json");
		PrintWriter writer = resp.getWriter();
		writer.println(isSuccess);
		writer.close();
	}

	protected void doPostRoleDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		int idDeleteRole = Validator.parseWithDefault(req.getParameter("id"), -1);
		boolean isSuccess = roleService.deleteRole(idDeleteRole);

		resp.setContentType("application/json");
		PrintWriter writer = resp.getWriter();
		writer.println(isSuccess);
		writer.close();
	}
}
