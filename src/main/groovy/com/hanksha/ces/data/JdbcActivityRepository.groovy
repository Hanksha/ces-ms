package com.hanksha.ces.data;

import java.sql.Connection
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.hanksha.ces.data.models.Activity;

@Repository
class JdbcActivityRepository implements ActivityRepository {

	private JdbcOperations jdbc

	@Autowired
	JdbcActivityRepository(JdbcOperations jdbc) {
		this.jdbc = jdbc
	}
	
	List<Activity> findAll() throws DataAccessException {
		return jdbc.query(
				"SELECT id, title, date_start, date_end, desc " +
				"FROM activities",
				new ActivityRowMapper())
	}

	Optional<Activity> findOne(int id) throws DataAccessException {
		Activity activity = null
		
		try {
			activity = jdbc.queryForObject(
					"SELECT id, title, date_start, date_end, desc "	+
					"FROM activities " +
					"WHERE id = ?",
					new ActivityRowMapper(),
					id
					)
		} catch(EmptyResultDataAccessException e) {
			e.printStackTrace()
		}
		
		Optional.ofNullable(activity)
	}

	List<Activity> findTitledLike(String title) throws DataAccessException {
		return jdbc.query(
				"SELECT id, title, date_start, date_end, desc "	+
				"FROM activities " +
				"WHERE title LIKE ?",
				new ActivityRowMapper(),
				title)
	}

	List<Activity> findByDate(String date) throws DataAccessException {
		return jdbc.query(
				"SELECT id, title, date_start, date_end, desc "
				+ "FROM activities "
				+ "WHERE date_start LIKE ? OR date_end LIKE ?",
				new ActivityRowMapper(),
				"%" + date + "%",
				"%" + date + "%"
				)
	}

	Activity save(Activity activity) throws DataAccessException {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		jdbc.update( {Connection con ->
				PreparedStatement ps = con.prepareStatement(
						"INSERT INTO activities (title, date_start, date_end, desc) " +
						"VALUES (?, ?, ?, ?)",
						Statement.RETURN_GENERATED_KEYS)

				ps.setString(1, activity.getTitle())
				ps.setDate(2, new java.sql.Date(activity.getDateStart().getTime()))
				ps.setDate(3, new java.sql.Date(activity.getDateEnd().getTime()))
				ps.setString(4, activity.getDesc())

				ps
			},
			keyHolder
		)
		
		activity.setId(keyHolder.getKey().intValue())
		
		activity
	}

	boolean delete(int id) throws DataAccessException {
		jdbc.update("DELETE FROM activities WHERE id = ?", id);
		
		true
	}

	void update(Activity activity) throws DataAccessException {
		jdbc.update(
				"UPDATE activities "
				+ "SET title = ?, date_start = ?, date_end = ?, desc = ? "
				+ "WHERE id = ?",
				activity.getTitle(),
				new java.sql.Date(activity.getDateStart().getTime()),
				new java.sql.Date(activity.getDateEnd().getTime()),
				activity.getDesc(),
				activity.getId());
	}

	static class ActivityRowMapper implements RowMapper<Activity> {
		public Activity mapRow(ResultSet rs, int rowNum) {
			new Activity(
					id: rs.getInt("id"),
					title: rs.getString("title"),
					dateStart: new Date(rs.getDate("date_start").getTime()),
					dateEnd: new Date(rs.getDate("date_end").getTime()),
					desc: rs.getString("desc"))
		}
	}
	
}
