package com.hanksha.ces.data;

import java.sql.ResultSet;
import java.sql.SQLException

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hanksha.ces.data.models.User;

@Repository
public class JdbcUserRepository implements UserRepository {

	private JdbcOperations jdbc;
	
	@Autowired
	public JdbcUserRepository(JdbcOperations jdbc) {
		this.jdbc = jdbc;
	}
	
	@Override
	public List<User> findAll() throws DataAccessException {
		return jdbc.query(
				"SELECT user_name, user_password, user_level, date_created, user_member_id "
				+ "FROM tblusers", 
				new UserRowMapper());
	}
	
	@Override
	public Optional<User> findOne(String userName, String password) throws DataAccessException {
		User user = null;
		try {
		 user = jdbc.queryForObject(
				"SELECT * "
				+ "FROM tblusers "
				+ "WHERE user_name = ? AND user_password = ?", 
				new UserRowMapper(),
				userName, password);
		} catch(EmptyResultDataAccessException e) {
			e.printStackTrace();
		}
		
		return Optional.ofNullable(user);
	}

	@Override
	public Optional<User> findOne(String userName) throws DataAccessException {
		User user = null;
		try {
		user = jdbc.queryForObject(
				"SELECT user_name, user_password, user_level, date_created, user_member_id "
				+ "FROM tblusers "
				+ "WHERE user_name = ?", 
				new UserRowMapper(),
				userName);
		} catch(EmptyResultDataAccessException e) {
			e.printStackTrace();
		}
		
		return Optional.ofNullable(user);
	}

	@Override
	public boolean delete(User user) throws DataAccessException {
		jdbc.update("DELETE FROM tblusers WHERE user_name = ?", user.getUsername());
		
		return true;
	}

	@Override
	public void save(User user) throws DataAccessException {
		jdbc.update(
				"INSERT INTO tblusers (user_name, user_password, user_level, date_created, user_member_id) "
				+ "VALUES (?, ?, ?, ?, ?)",
				user.getUsername(),
				user.getPassword(),
				user.getRole(),
				user.getDateCreated(),
				user.getMemberId());
	}

	@Override
	public void update(User user) throws DataAccessException {
		jdbc.update(
				"UPDATE users "
				+ "SET password = ?, role = ?, member_id = ? "
				+ "WHERE username = ?",
				user.getPassword(),
				user.getRole(),
				user.getMemberId(),
				user.getUsername());
	}

	public static class UserRowMapper implements RowMapper<User> {
		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new User(
					rs.getInt("member_id"),
					rs.getString("username"),
					rs.getString("password"),
					rs.getDate("date_created"));
		}
	}
}
