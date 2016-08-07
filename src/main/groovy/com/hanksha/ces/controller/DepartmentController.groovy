package com.hanksha.ces.controller

import com.hanksha.ces.data.DepartmentRepository
import com.hanksha.ces.data.models.Department
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

/**
 * Created by vivien on 7/10/16.
 */

@RestController
@RequestMapping('/api/departments')
class DepartmentController {

    @Autowired
    DepartmentRepository dptRepo

    @RequestMapping(value = '/{id}', method = RequestMethod.GET)
    ResponseEntity getDepartment(@PathVariable String id) {
        def dpt = dptRepo.findOne(id)

        if(dpt)
            new ResponseEntity(dpt, HttpStatus.OK)
        else
            new ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @RequestMapping(method = RequestMethod.GET)
    List<Department> listDepartments() {
        dptRepo.findAll()
    }

    @RequestMapping(value = '/{id}', method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    void deleteDepartment(@PathVariable String id) {
        dptRepo.delete(id)
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    void createDepartment(@RequestBody Department department) {
        dptRepo.save(department)
    }

    @RequestMapping(value = '/{id}', method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    void updateDepartment(@RequestBody Department department) {
        dptRepo.update(department)
    }

}
