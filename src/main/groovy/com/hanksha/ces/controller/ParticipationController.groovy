package com.hanksha.ces.controller

import com.hanksha.ces.data.ParticipationRepository
import com.hanksha.ces.data.models.Participation
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
@RequestMapping('/api/participations')
class ParticipationController {

    @Autowired
    ParticipationRepository participationRepo

    @RequestMapping(value = '/{id}', method = RequestMethod.GET)
    ResponseEntity getParticipation(@PathVariable int id) {

        def participation = null

        participationRepo.findOne(id).ifPresent({p -> participation = p})

        if(participation)
            new ResponseEntity(participation, HttpStatus.OK)
        else
            new ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @RequestMapping(value = '', method = RequestMethod.GET)
    List<Participation> listParticipations() {
        participationRepo.findAll()
    }

    @RequestMapping(value = '/member/{id}', method = RequestMethod.GET)
    List<Participation> listParticipationForMember(@PathVariable int id) {
        participationRepo.findAllFor(id)
    }

    @RequestMapping(value = '', method = RequestMethod.POST)
    ResponseEntity createParticipation(@RequestBody Participation participation) {
        new ResponseEntity(participationRepo.save(participation), HttpStatus.OK)
    }

    @RequestMapping(value = '/{id}', method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    void updateParticipation(@RequestBody Participation participation) {
        participationRepo.update(participation)
    }


}
