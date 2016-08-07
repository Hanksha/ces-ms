package com.hanksha.ces.data

import com.hanksha.ces.data.models.Department

interface DepartmentRepository {

	Department findOne(String id)

	List<Department> findAll()
	
	void delete(String dptId)

	void save(Department department)

	void update(Department department)

}
