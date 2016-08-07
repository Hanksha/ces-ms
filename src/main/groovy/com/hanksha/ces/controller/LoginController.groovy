package com.hanksha.ces.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by vivien on 6/29/16.
 */

@Controller
public class LoginController {

    Logger log = Logger.getLogger(this.getClass().getName());

    @RequestMapping(value = "/login", method = RequestMethod.GET)
     String login(HttpServletRequest httpServletRequest, Model model) {

        if(httpServletRequest.getRemoteUser() != null)
            return "redirect:/";

        model.addAttribute("login", true);

        return "index";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    String logout(HttpServletRequest httpServletRequest, Model model) throws ServletException {

        log.info("Logging out user " + httpServletRequest.getRemoteUser());

        httpServletRequest.logout();

        model.addAttribute("logout", true);

        return "index";
    }

}
