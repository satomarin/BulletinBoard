package bulletinboard.dao;

import static bulletinboard.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import bulletinboard.beans.UserMessage;
import bulletinboard.exception.SQLRuntimeException;

public class UserMessageDao {

	public List<UserMessage> getUserMessages(Connection connection, int num, String category, String time) {

		PreparedStatement ps = null;

		try {
			StringBuilder sql = new StringBuilder();

			sql.append("SELECT * FROM user_message ");
			if(category != null){
				sql.append("WHERE category = ?");
			}

			sql.append("ORDER BY insert_date DESC limit " + num );

			ps = connection.prepareStatement(sql.toString());


			if(category != null){
				ps.setString(1, category);
			}

			System.out.println(ps);

			ResultSet rs = ps.executeQuery();
			List<UserMessage> ret = toUserMessageList(rs);
			return ret;

		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}


	private List<UserMessage> toUserMessageList(ResultSet rs)throws SQLException {

		List<UserMessage> ret = new ArrayList<UserMessage>();
		try {
			while (rs.next()) {

				String account = rs.getString("account");
				String name = rs.getString("name");
				int messageId = rs.getInt("message_id");
				int userId = rs.getInt("user_id");
				String title = rs.getString("title");
				String category = rs.getString("category");
				String text = rs.getString("text");
				Timestamp insertDate = rs.getTimestamp("insert_date");

				UserMessage message = new UserMessage();
				message.setAccount(account);
				message.setName(name);
				message.setMessageId(messageId);
				message.setUserId(userId);
				message.setTitle(title);
				message.setCategory(category);
				message.setText(text);
				message.setInsertDate(insertDate);

				ret.add(message);
			}
			return ret;
		} finally {
			close(rs);
		}
	}


}
