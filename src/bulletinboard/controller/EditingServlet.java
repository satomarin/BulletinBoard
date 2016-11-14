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


@WebServlet (urlPatterns = { "/editing" })

public class EditingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {

		//取得
		List<Branch> branches = new BranchService().getBranch();
		List<Department> departments = new DepartmentService().getDepartment();

		//jspで使えるように
		request.setAttribute("branches", branches);
		request.setAttribute("departments", departments);


		//ユーザー情報編集にてどこの編集が押されたのか、ユーザーIDをもらっている
		String editId =request.getParameter("id");

		UserService userService = new UserService();
		User editUser = userService.editing(editId);

		request.setAttribute("editUser", editUser);

		//jspファイルをgetする
		request.getRequestDispatcher("editing.jsp").forward(request, response);




	}

	@Override
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {


		List<String> messages = new ArrayList<String>();
		HttpSession session = request.getSession();


		if (isValid(request, messages) == true) {

			User user = new User();
			user.setId(Integer.parseInt(request.getParameter("id")));
			user.setAccount(request.getParameter("account"));
			user.setName(request.getParameter("name"));
			user.setPassword(request.getParameter("password"));
			System.out.println(request.getParameter("password"));
			user.setBranchID(request.getParameter("branch_id"));
			user.setDepartmentID(request.getParameter("department_id"));
			new UserService().update(user);


			response.sendRedirect("setting");

		} else {
			session.setAttribute("errorMessages", messages);
			response.sendRedirect("./");
		}
	}


	private boolean isValid(HttpServletRequest request, List<String> messages) {
		String account = request.getParameter("account");
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String confirmationPassword = request.getParameter("confirmationPassword");

		if (StringUtils.isEmpty(account) == true) {
			messages.add("ログインIDを入力してください");
		}
		if (StringUtils.isEmpty(name) == true) {
			messages.add("名前を入力してください");
		}
		if (!password.equals(confirmationPassword)){
			messages.add("確認用パスワードが間違っています");
		}


		// TODO アカウントが既に利用されていないか、メールアドレスが既に登録されていないかなどの確認も必要
		if (messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

}
