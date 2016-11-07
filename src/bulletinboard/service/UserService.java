package bulletinboard.service;

import static bulletinboard.utils.CloseableUtil.*;
import static bulletinboard.utils.DBUtils.*;

import java.sql.Connection;

import bulletinboard.beans.User;
import bulletinboard.dao.UserDao;
import bulletinboard.utils.CipherUtil;

public class UserService {

	public void register(User user){

		Connection connection = null;
		try {
			connection = getConnection();
			System.out.println(user.getPassword());

			String encPassword = CipherUtil.encrypt(user.getPassword());
			user.setPassword(encPassword);

			UserDao userDao = new UserDao();
			userDao.insert(connection, user);

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
}