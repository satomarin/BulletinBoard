package bulletinboard.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import bulletinboard.beans.Branch;
import bulletinboard.beans.Department;
import bulletinboard.beans.User;
import bulletinboard.service.BranchService;
import bulletinboard.service.DepartmentService;
import bulletinboard.service.UserService;


@WebServlet (urlPatterns = { "/signup" })

public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {

		//取得
		List<Branch> branches = new BranchService().getBranch();
		List<Department> departments = new DepartmentService().getDepartment();
		//jspで使えるように
		request.setAttribute("branches", branches);
		request.setAttribute("departments", departments);

		//jspファイルをgetする
		request.getRequestDispatcher("signup.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {


		List<String> messages = new ArrayList<String>();
		HttpSession session = request.getSession();

		if (isValid(request, messages) == true) {

			User user = new User();
			user.setAccount(request.getParameter("account"));
			user.setName(request.getParameter("name"));
			user.setPassword(request.getParameter("password"));
			user.setBranchID(request.getParameter("branch_id"));
			user.setDepartmentID(request.getParameter("department_id"));



			new UserService().register(user);

			response.sendRedirect("./");

		} else {
			session.setAttribute("errorMessages", messages);
			response.sendRedirect("signup");
		}
	}

	private boolean isValid(HttpServletRequest request, List<String> messages) {
		String account = request.getParameter("account");
		String password = request.getParameter("password");

		if (StringUtils.isEmpty(account) == true) {
			messages.add("アカウント名を入力してください");
		}
		if (StringUtils.isEmpty(password) == true) {
			messages.add("パスワードを入力してください");
		}
		// TODO アカウントが既に利用されていないか、メールアドレスが既に登録されていないかなどの確認も必要
		if (messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

}

