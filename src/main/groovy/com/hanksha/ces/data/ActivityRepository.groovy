package com.hanksha.ces.data;

import com.hanksha.ces.data.models.Activity;

public interface ActivityRepository {
	
	List<Activity> findAll();
	
	Optional<Activity> findOne(int id);
	
	List<Activity> findTitledLike(String title);
	
	List<Activity> findByDate(String date);
	
	Activity save(Activity activity);
	
	boolean delete(int id);
	
	void update(Activity activity);
}
