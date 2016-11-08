package bulletinboard.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bulletinboard.beans.User;
import bulletinboard.beans.UserComment;
import bulletinboard.beans.UserMessage;
import bulletinboard.service.CommentService;
import bulletinboard.service.MessageService;



@WebServlet (urlPatterns = { "/index.jsp" })
public class TopServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet (HttpServletRequest request, HttpServletResponse response) throws IOException,ServletException {

		User user = (User) request.getSession().getAttribute("loginUser");

		boolean isShowMessageForm;
		if (user != null) {
			isShowMessageForm = true;
		} else {
			isShowMessageForm = false;
		}

		request.setAttribute("isShowMessageForm", isShowMessageForm);


		List<UserMessage> messages = new MessageService().getMessage();
		request.setAttribute("messages", messages);

		List<UserComment> comments = new CommentService().getComment();
		request.setAttribute("comments", comments);


		request.getRequestDispatcher("/top.jsp").forward(request, response);

	}

}