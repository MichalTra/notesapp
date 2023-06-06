package com.notesapp.notesapp.controller;

import com.notesapp.notesapp.model.User;
import com.notesapp.notesapp.repository.UsersRepository;
import com.notesapp.notesapp.security.AuthenticationHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationHelper authenticationHelper;

    @RequestMapping(value={"","/","/login"}, method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView displayLoginPage(@RequestParam(value = "logout", required = false) String logout,
                                   @RequestParam(value = "error", required = false) String error,
                                         Model model) {
        model.addAttribute("user", new User()); // send an empty user object to be filled by /submitLoginInformation action

        String errors = null;
        String message = null;
        if (error != null) {
            errors = "Error";
        } else if (logout != null && logout.equals("true")) {
            message = "Logout was successful";
        }
        ModelAndView modelAndView = new ModelAndView("login.html");
        modelAndView.addObject("message", message);
        modelAndView.addObject("error", errors);
        return modelAndView;
    }

    // handle login/register
    @RequestMapping(value="/submitLoginInformation", method = RequestMethod.POST)
    public ModelAndView attemptLogin(@Valid @ModelAttribute("user") User user, Errors errors, Authentication authentication) {

        // Invalid data from user
        if (errors.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("login.html");
            modelAndView.addObject("errors", "Invalid data");
            return modelAndView;
        }

        User cmpUser = usersRepository.getByUsername(user.getUsername());
        // Create a new user
        if (user.isCreateNewUser()) {
            if (cmpUser != null) {
                ModelAndView modelAndView = new ModelAndView("login.html");
                modelAndView.addObject("errors", "The user already exists");
                return modelAndView;
            } else {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                User savedUser = usersRepository.save(user);
                if (savedUser == null || savedUser.getUserId() <= 0) {
                    ModelAndView modelAndView = new ModelAndView("login.html");
                    modelAndView.addObject("errors", "Saving the user failed");
                    return modelAndView;
                }
                ModelAndView modelAndView = new ModelAndView("notes.html");
                authenticationHelper.manualAuthenticate(authentication, user.getUsername(), user.getPassword());
                modelAndView.addObject("loggedUser", authenticationHelper.getLoggedUser(authentication));
                return modelAndView;
            }
        } else {
            if (cmpUser == null || !passwordEncoder.matches(user.getPassword(), cmpUser.getPassword())) {
                ModelAndView modelAndView = new ModelAndView("login.html");
                modelAndView.addObject("errors", "Invalid credentials");
                return modelAndView;
            } else {
                ModelAndView modelAndView = new ModelAndView("notes.html");
                authenticationHelper.manualAuthenticate(authentication, user.getUsername(), user.getPassword());
                modelAndView.addObject("loggedUser", authenticationHelper.getLoggedUser(authentication));
                return modelAndView;
            }
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/login?logout=true";
    }

}
