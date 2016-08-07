package com.hanksha.ces.controller

import com.hanksha.ces.data.InvolvementTypeRepository
import com.hanksha.ces.data.models.InvolvementType
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
@RequestMapping('/api/involvement-types')
class InvolvementTypeController {

    @Autowired
    InvolvementTypeRepository involvementTypeRepo

    @RequestMapping(value = '/{name}', method = RequestMethod.GET)
    ResponseEntity getInvolvementTypes(@PathVariable String name) {
        def type = involvementTypeRepo.findOne(name)

        if(type)
            new ResponseEntity(type, HttpStatus.OK)
        else
            new ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @RequestMapping(method = RequestMethod.GET)
    List<InvolvementType> listInvolvementTypes() {
        involvementTypeRepo.findAll()
    }

    @RequestMapping(value = '/{name}', method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    void deleteInvolvementType(@PathVariable String name) {
        involvementTypeRepo.delete(name)
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    void createInvolvementType(@RequestBody InvolvementType involvementType) {
        involvementTypeRepo.save(involvementType)
    }

    @RequestMapping(value = '/{name}', method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    void updateInvolvementType(@RequestBody InvolvementType involvementType) {
        involvementTypeRepo.update(involvementType)
    }

}
