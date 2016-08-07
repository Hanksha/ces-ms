package com.hanksha.ces.data

import com.hanksha.ces.data.models.InvolvementType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataAccessException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.JdbcOperations
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository

import java.sql.ResultSet
import java.sql.SQLException

/**
 * Created by vivien on 7/10/16.
 */

@Repository
class JdbcInvolvementTypeRepository implements InvolvementTypeRepository {

    JdbcOperations jdbc

    @Autowired
    JdbcInvolvementTypeRepository(JdbcOperations jdbc) {
        this.jdbc = jdbc
    }

    InvolvementType findOne(String name) {
        def type = null

        try {
            type = jdbc.queryForObject(
                    'SELECT name, desc FROM involvement_types WHERE name = ?', new InvolvementTypeRowMapper(),
                    name)
        } catch(EmptyResultDataAccessException e) {
            e.printStackTrace()
        }

        type
    }

    List<InvolvementType> findAll() {
        jdbc.query('SELECT name, desc FROM involvement_types', new InvolvementTypeRowMapper());
    }

    void delete(String name) {
        jdbc.update('DELETE FROM involvement_types WHERE name = ?', name);
    }

    void save(InvolvementType involvementType) {
        jdbc.update(
                'INSERT INTO involvement_types (name, desc) VALUES (?, ?)',
                involvementType.name, involvementType.desc)
    }

    void update(InvolvementType involvementType) {
        jdbc.update(
                'UPDATE involvement_types SET desc = ? WHERE name = ?',
                involvementType.desc, involvementType.name)
    }

    static class InvolvementTypeRowMapper implements RowMapper<InvolvementType> {

        public InvolvementType mapRow(ResultSet rs, int rowNum) throws SQLException {
            new InvolvementType(name: rs.getString('name'), desc: rs.getString('desc'))
        }

    }

}
