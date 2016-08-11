package com.hanksha.ces.data

import com.hanksha.ces.data.models.Member

import java.sql.Connection;
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

@Repository
class JdbcMemberRepository implements MemberRepository {

	private JdbcOperations jdbc;
	
	@Autowired
	public JdbcMemberRepository(JdbcOperations jdbc) {
		this.jdbc = jdbc;
	}
	
	@Override
	public List<Member> findAll() throws DataAccessException {
		return jdbc.query(
				"SELECT id, first_name, last_name, dpt_id "
				+ "FROM members", new MemberRowMapper());
	}

	@Override
	public Optional<Member> findOne(int id) throws DataAccessException {
		Member member = null;
		
		try {
			member = jdbc.queryForObject(
					"SELECT id, first_name, last_name, dpt_id "
					+ "FROM members "
					+ "WHERE id = ?",
					new MemberRowMapper(), id);
		} catch(EmptyResultDataAccessException e) {
			e.printStackTrace();
		}
		
		return Optional.ofNullable(member);
	}

	@Override
	public List<Member> findNamedLike(String firstName, String lastName) throws DataAccessException {
		return jdbc.query(
				"SELECT id, first_name, last_name, dpt_id "
				+ "FROM members "
				+ "WHERE first_name LIKE ? OR last_name LIKE ?",
				new MemberRowMapper(),
				firstName, lastName);
	}

	@Override
	public void update(Member member) throws DataAccessException {
		jdbc.update(
				"UPDATE members "
				+ "SET first_name = ?, last_name = ?, dpt_id = ? "
				+ "WHERE id = ?",
				member.getFirstName(), member.getLastName(), 
				member.getDptId(), member.getId());
	}

	@Override
	public Member save(Member member) throws DataAccessException {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		jdbc.update(
				{ Connection con ->
					PreparedStatement ps = con.prepareStatement(
							"INSERT INTO members (first_name, last_name, dpt_id) "
							+ "VALUES (?, ?, ?)", 
							Statement.RETURN_GENERATED_KEYS);
					
					ps.setString(1, member.getFirstName());
					ps.setString(2, member.getLastName());
					ps.setString(3, member.getDptId());
					
					return ps;
				},
				keyHolder
		);
		
		member.setId(keyHolder.getKey().intValue());
		
		return member;
	}

	@Override
	boolean delete(Member member) throws DataAccessException {
		delete(member.getId())
	}

	@Override
	boolean delete(int id) throws DataAccessException {
		jdbc.update(
				'DELETE FROM members WHERE id = ?', id)

		true;
	}
	
	private static class MemberRowMapper implements RowMapper<Member> {
		@Override
	    public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
	    	return new Member(
	    			rs.getInt("id"),
					rs.getString("first_name"),
					rs.getString("last_name"), 
					rs.getString("dpt_id"));
		}
	}
}
