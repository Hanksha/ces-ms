package com.hanksha.ces.data

import com.hanksha.ces.data.models.Involvement;
import com.hanksha.ces.data.models.Participation

interface ParticipationRepository {

	List<Participation> findAll()

	List<Participation> findAllFor(int memberId)
	
	Optional<Participation> findOne(int id)
	
	void update(Participation participation)
	
	Participation save(Participation participation)
	
	void delete(int id)

	List<Involvement> findInvolvements(int id)

	void saveInvolvement(Involvement involvement)

	void deleteInvolvement(Involvement involvement)
}
