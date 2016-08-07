package com.hanksha.ces.data

import com.hanksha.ces.data.models.Member;

public interface MemberRepository {
	
	List<Member> findAll();
	
	Optional<Member> findOne(int id);
	
	List<Member> findNamedLike(String firstName, String lastName);

	void update(Member member);
	
	Member save(Member member);
	
	boolean delete(Member member);

	boolean delete(int id);
	
}
