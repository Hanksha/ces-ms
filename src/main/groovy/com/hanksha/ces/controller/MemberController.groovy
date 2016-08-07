package com.hanksha.ces.controller;

import com.hanksha.ces.data.MemberRepository;
import com.hanksha.ces.data.ParticipationRepository;
import com.hanksha.ces.data.models.Member;
import com.hanksha.ces.data.models.Participation;
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * Created by vivien on 7/2/16.
 */

@RestController
@RequestMapping('/api/members')
class MemberController {

    @Autowired
    MemberRepository memberRepo;

    @Autowired
    ParticipationRepository participationRepo;

    @RequestMapping(method = RequestMethod.GET)
    List<Member> listMember() {
        memberRepo.findAll()
    }

    @RequestMapping(value = '/{id}', method = RequestMethod.GET)
    ResponseEntity<Member> getMember(@PathVariable int id) {
        def member = null;

        memberRepo.findOne(id).ifPresent({Member m -> member = m})

        if(member)
            new ResponseEntity(member, HttpStatus.OK)
        else
            new ResponseEntity(HttpStatus.NOT_FOUND)

    }

    @RequestMapping(value = '/{id}', method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    void deleteMember(@PathVariable int id) {
        memberRepo.delete(id)
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity createMember(@RequestBody Member member) {
         new ResponseEntity(memberRepo.save(member), HttpStatus.OK)
    }

    @RequestMapping(value = '/{id}', method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    void updateMember(@RequestBody Member member) {
        memberRepo.update(member)
    }

}
