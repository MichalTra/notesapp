package com.notesapp.notesapp.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @RequestMapping(value={"","/","/login"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String displayLoginPage(@RequestParam(value = "logout", required = false) String logout,
                                   @RequestParam(value = "error", required = false) String error) {
        String messageForUser = null;
        if (error != null) {
            messageForUser = "Error";
        } else if (logout != null && logout.equals("true")) {
            messageForUser = "Logout was successful";
        }
        return "login.html";
    }

    @RequestMapping(value="/submitLoginInformation", method = RequestMethod.POST)
    public ModelAndView attemptLogin(Authentication authentication) {

        if (authentication.isAuthenticated()) {
            // redirect to NotesController
            ModelAndView modelAndView = new ModelAndView("notes.html");
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("login.html");
            modelAndView.addObject("errors","Login was not successful");
            return modelAndView;
        }
    }

}
