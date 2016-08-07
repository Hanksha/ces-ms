package com.hanksha.ces.controller

import com.hanksha.ces.data.ActivityRepository
import com.hanksha.ces.data.models.Activity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

/**
 * Created by vivien on 7/10/16.
 */

@RestController
@RequestMapping('/api/activities')
class ActivityController {

    @Autowired
    ActivityRepository activityRepo

    @RequestMapping(value = '/{id}', method = RequestMethod.GET)
    ResponseEntity getActivity(@PathVariable int id) {

        def activity = null

        activityRepo.findOne(id).ifPresent({a -> activity = a})

        if(activity)
            new ResponseEntity(activity, HttpStatus.OK)
        else
            new ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity listActivities(@RequestParam(name = 'q', required = false) String query) {

        def activities = null

        if(!query) {

            activities = activityRepo.findAll()

        }
        else {
            def split = query.split(':')

            if(split.size() < 2)
                return new ResponseEntity(HttpStatus.BAD_REQUEST)

            if(split[0] == 'title') {
                activities = activityRepo.findTitledLike(split[1])
            }
            else if(split[0] == 'date') {
                activities = activityRepo.findByDate(split[1])
            }
            else
                return new ResponseEntity(HttpStatus.BAD_REQUEST)
        }

        if(activities)
            new ResponseEntity(activities, HttpStatus.OK)
        else
            new ResponseEntity(HttpStatus.NO_CONTENT)
    }

    @RequestMapping(method = RequestMethod.POST)
    Activity createActivity(@RequestBody Activity activity) {
        activityRepo.save(activity)
    }

    @RequestMapping(value = '/{id}', method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    void updateActivity(@RequestBody Activity activity) {
        activityRepo.update(activity)
    }

    @RequestMapping(value = '/{id}', method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    void deleteActivity(@PathVariable int id) {
        activityRepo.delete(id)
    }
}
