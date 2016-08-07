package com.hanksha.ces.data;

import java.util.List;
import java.util.Optional;

import com.hanksha.ces.data.models.User;


public interface UserRepository {
	
	List<User> findAll();
	
	Optional<User> findOne(String userName);
	
	Optional<User> findOne(String userName, String password);
	
	boolean delete(User user);
	
	void save(User user);
	
	void update(User user);
}
