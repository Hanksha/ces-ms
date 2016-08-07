package com.hanksha.ces.data

import com.hanksha.ces.data.models.Department
import org.springframework.dao.EmptyResultDataAccessException

import java.sql.ResultSet;
import java.sql.SQLException

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcDepartmentRepository implements DepartmentRepository {

	JdbcOperations jdbc;
	
	@Autowired
	JdbcDepartmentRepository(JdbcOperations jdbc) {
		this.jdbc = jdbc
	}

	Department findOne(String id) {
		def dpt = null
		try {
			dpt = jdbc.queryForObject(
					'SELECT id, name FROM departments WHERE id = ?',
					new DepartmentRowMapper(), id)
		} catch(EmptyResultDataAccessException e) {
			e.printStackTrace()
		}

		dpt
	}

	List<Department> findAll() throws DataAccessException {
		jdbc.query('SELECT id, name FROM departments', new DepartmentRowMapper())
	}

	void delete(String id) throws DataAccessException {
		jdbc.update('DELETE FROM departments WHERE id = ?', id)
	}

	void save(Department department) throws DataAccessException {
		jdbc.update(
				'INSERT INTO departments (id, name) VALUES (?, ?)', department.id, department.name)
	}

	void update(Department department) {
		jdbc.update(
				'UPDATE departments SET name = ? WHERE id = ?', department.name, department.id)
	}

	static class DepartmentRowMapper implements RowMapper<Department> {

		public Department mapRow(ResultSet rs, int rowNum) throws SQLException {
			new Department(id: rs.getString('id'), name: rs.getString('name'))
		}

	}

}
