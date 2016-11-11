package bulletinboard.service;

import static bulletinboard.utils.CloseableUtil.*;
import static bulletinboard.utils.DBUtils.*;

import java.sql.Connection;
import java.util.List;

import bulletinboard.beans.Message;
import bulletinboard.beans.UserMessage;
import bulletinboard.dao.MessageDao;
import bulletinboard.dao.UserMessageDao;

public class MessageService {

	public void register(Message message) {

		Connection connection = null;
		try {
			connection = getConnection();

			MessageDao messageDao = new MessageDao();

			messageDao.insert(connection, message);


			commit(connection);
		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}

	private static final int LIMIT_NUM = 1000;

	public List<UserMessage> getMessage(String category, String time) {

		Connection connection = null;
		try {
			connection = getConnection();

			UserMessageDao messageDao = new UserMessageDao();
			List<UserMessage> ret = messageDao.getUserMessages(connection, LIMIT_NUM, category, time);

			commit(connection);

			return ret;
		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}

	public List<Message> getMessagecatalog() {

		Connection connection = null;
		try {
			connection = getConnection();

			MessageDao messageDao = new MessageDao();

			List<Message> ret = messageDao.getMessages(connection, LIMIT_NUM);

			commit(connection);

			return ret;

		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}


}