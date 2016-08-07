package com.hanksha.ces.data;

import com.hanksha.ces.data.models.Participation

interface ParticipationRepository {

	List<Participation> findAll()

	List<Participation> findAllFor(int memberId)
	
	Optional<Participation> findOne(int id);
	
	void update(Participation participation)
	
	Participation save(Participation participation)
	
	boolean delete(Participation participation)
}
