package com.hanksha.ces.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

/**
 * Created by vivien on 7/9/16.
 */

@Controller
class HomeController {

    @RequestMapping(value = '/', method = RequestMethod.GET)
    def home() {
        'index'
    }

}
