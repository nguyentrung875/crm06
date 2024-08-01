package crm06.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import crm06.entity.UserEntity;

//urlPattern: Đường dẫn client gọi sẽ kích hoạt filter
@WebFilter(filterName = "authenFilter", urlPatterns = "/*")
public class AuthenticationFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// Lấy link servlet mà client đang gọi
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		String contextPath = req.getContextPath();
		String path = req.getServletPath();

		// Những file .js không cần chạy qua filter
		if (path.startsWith("/js/") || path.startsWith("/css") || path.startsWith("/bootstrap/")
				|| path.startsWith("/plugins/") || path.startsWith("/less/")) {
			chain.doFilter(request, response);
			return;
		}
		
		HttpSession session = req.getSession();
		UserEntity loginedInfo = (UserEntity) session.getAttribute("loginedInfo");
		
		//CHƯA ĐĂNG NHẬP
		if (loginedInfo == null) {
			if (path.equals("/login")) {
				chain.doFilter(request, response);
			} else {
				resp.sendRedirect(contextPath + "/login");
			}
			return;
		}

		//ĐÃ ĐĂNG NHẬP
		String loginedRole = loginedInfo.getRole().getName();
		
		//--TH lại vào trang login khi đã đăng nhập
		if (path.equals("/login")) {
			resp.sendRedirect(contextPath);
			return;
		}
		
		//PHÂN QUYỀN
		//Các trang ROLE nào cũng có thể vào
		if (path.startsWith("/profile") || path.startsWith("/index") || 
				path.equals("")|| path.equals("/404") || 
				path.equals("/logout")) {
			chain.doFilter(request, response);
			return;
		} 
		
		//Phân quyền cho ROLE_USER
		if (loginedRole.equals("USER")) {
			resp.sendRedirect(contextPath + "/404");
			return;
		}
		
		//Phân quyền cho ROLE_LEADER
		if (loginedRole.equals("LEADER")) {
			if (path.startsWith("/task") || path.startsWith("/project")) {
				chain.doFilter(request, response);
			} else {
				resp.sendRedirect(contextPath + "/404");
			}
			return;
		}
		
		//Phân quyền cho ROLE_ADMIN
		if (loginedRole.equals("ADMIN")) {
			chain.doFilter(request, response);
		}
		
		
//		if (path.equals("/login")) {
//			resp.sendRedirect(contextPath);
//		} else {
//			if (path.startsWith("/profile") || path.startsWith("/index") || path.equals("")|| path.equals("/404") || path.equals("/logout")) {
//				chain.doFilter(request, response);
//			} else {
//				if (loginedRole.equals("USER")) {
//					resp.sendRedirect(contextPath + "/404");
//				} else if (loginedRole.equals("ADMIN")) {
//					chain.doFilter(request, response);
//				} else if (loginedRole.equals("LEADER")) {
//					chain.doFilter(request, response);
//				} else {
//					chain.doFilter(request, response);
//				}
//			}
//		}

//		if (path.equals("/login")) {
//			if (isExist) {
//				// Cho phép đi tiếp (nều sau đó filter thì sẽ đi filter kế tiếp)
//				resp.sendRedirect(contextPath);
//			} else {
//				chain.doFilter(request, response);
//			}
//		} else {
//			if (isExist) {
//				// Cho phép đi tiếp (nều sau đó filter thì sẽ đi filter kế tiếp)
//				chain.doFilter(request, response);
//			} else {
//				contextPath = req.getContextPath();
//				resp.sendRedirect(contextPath + "/login");
//			}
//		}

	}

}
