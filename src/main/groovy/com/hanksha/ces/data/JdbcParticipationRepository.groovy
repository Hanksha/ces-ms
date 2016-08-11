package com.hanksha.ces.data

import com.hanksha.ces.data.models.Involvement
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.jdbc.support.KeyHolder

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataAccessException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.JdbcOperations
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository

import com.hanksha.ces.data.models.Participation

import java.sql.Statement

@Repository
class JdbcParticipationRepository implements ParticipationRepository {

	private JdbcOperations jdbc
	
	@Autowired
	public JdbcParticipationRepository(JdbcOperations jdbc) {
		this.jdbc = jdbc
	}

	List<Participation> findAll() throws DataAccessException {
		jdbc.query(
				'SELECT id, member_id, activity_id, date, remarks FROM participations',
				new ParticipationRowMapper())
	}
	
	List<Participation> findAllFor(int memberId) throws DataAccessException {
		jdbc.query(
				'SELECT id, member_id, activity_id, date, remarks ' +
				'FROM participations WHERE member_id = ?',
				new ParticipationRowMapper(), 
				memberId)
	}
	
	Optional<Participation> findOne(int id) throws DataAccessException {
		Participation participation = null
		
		try {
			participation = jdbc.queryForObject(
					'SELECT id, member_id, activity_id, date, remarks ' +
					'FROM participations WHERE id = ?',
					new ParticipationRowMapper(), 
					id)
		} catch(EmptyResultDataAccessException e) {
			e.printStackTrace()
		}
		
		Optional.ofNullable(participation)
	}

	void update(Participation participation) throws DataAccessException {
		jdbc.update(
				'UPDATE participations ' +
				'SET date = ?, remarks = ? ' +
				'WHERE id = ?',
				new java.sql.Date(participation.date.time),
				participation.getRemarks(),
				participation.getId())
	}


	Participation save(Participation participation) throws DataAccessException {
		KeyHolder keyHolder = new GeneratedKeyHolder();


		jdbc.update({Connection con ->
			PreparedStatement ps = con.prepareStatement(
				'INSERT INTO participations (member_id, activity_id, date, remarks) ' +
				'VALUES (?, ?, ?, ?)',
					Statement.RETURN_GENERATED_KEYS)

			ps.setInt(1, participation.getMemberId())
			ps.setInt(2, participation.getActivityId())
			ps.setDate(3, new java.sql.Date(participation.date.time))
			ps.setString(4, participation.getRemarks())

			ps
		},
		keyHolder)

		participation.setId(keyHolder.key.intValue())

		participation
	}


	void delete(int id) throws DataAccessException {
		jdbc.update(
				'DELETE FROM participations ' +
				'WHERE id = ?',
				id)
	}

	List<Involvement> findInvolvements(int id) {
		jdbc.query(
				'SELECT participation_id, type ' +
				'FROM participation_involvements ' +
				'WHERE participation_id = ?',
				new InvolvementRowMapper(), id)
	}

	void saveInvolvement(Involvement involvement) {
		jdbc.update('INSERT INTO participation_involvements VALUES (?,?)',
				involvement.participationId, involvement.type)
	}

	void deleteInvolvement(Involvement involvement) {
		jdbc.update('DELETE FROM participation_involvements WHERE participation_id = ? AND type = ?',
				involvement.participationId, involvement.type)
	}

	static class ParticipationRowMapper implements RowMapper<Participation> {
		Participation mapRow(ResultSet rs, int rowNum) throws SQLException {
			new Participation(
					id: rs.getInt('id'),
					memberId: rs.getInt('member_id'),
					activityId: rs.getInt('activity_id'),
					date: new Date(rs.getDate('date').time),
					remarks: rs.getString('remarks'))
		}
	}

	static class InvolvementRowMapper implements RowMapper<Involvement> {
		Involvement mapRow(ResultSet rs, int rowNum) throws SQLException {
			new Involvement(
					participationId: rs.getInt('participation_id'),
					type: rs.getString('type'))
		}
	}
}
