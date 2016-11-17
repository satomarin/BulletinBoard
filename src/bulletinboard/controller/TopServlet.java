package bulletinboard.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bulletinboard.beans.Message;
import bulletinboard.beans.UserComment;
import bulletinboard.beans.UserMessage;
import bulletinboard.service.CommentService;
import bulletinboard.service.MessageService;



@WebServlet (urlPatterns = { "/index.jsp" })
public class TopServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet (HttpServletRequest request, HttpServletResponse response) throws IOException,ServletException {


		//人が選択
		String category =request.getParameter("category");
		String firstTime =request.getParameter("firstTime");
		String lastTime =request.getParameter("lastTime");


		//date→String
		//nullだったら最古・最新の日数を格納
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (firstTime != null){
			firstTime = sdf1.format(new MessageService().getOldestDate());
		}
		if (lastTime != null){
			lastTime = sdf1.format(new MessageService().getLatestDate());
		}


		//絞込み
		List<UserMessage> messages = new MessageService().getMessage(category,firstTime,lastTime);
		request.setAttribute("messages", messages);

		//カテゴリーごとのメッセージ
		List<Message> messageCatalogs = new MessageService().getMessageCatalog();
		request.setAttribute("messageCatalogs", messageCatalogs);

		//コメント取得
		List<UserComment> comments = new CommentService().getComment();
		request.setAttribute("comments", comments);


		request.getRequestDispatcher("/top.jsp").forward(request, response);

	}

	@Override
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {



		//メッセージのIDを取得　（削除用）
		//int messageId = (Integer.parseInt(request.getParameter("id")));
		String messagesId = request.getParameter("id");
		//コメントのIDを取得　（削除用）
		String commentId = request.getParameter("commentId");


		if ( !messagesId.isEmpty() ){
			int messageId = (Integer.parseInt(request.getParameter("id")));
			new MessageService().delete(messageId);
		}

		if ( !commentId.isEmpty() ){
			int commentsId = (Integer.parseInt(request.getParameter("commentId")));
			new CommentService().delete(commentsId);
		}



	}

}