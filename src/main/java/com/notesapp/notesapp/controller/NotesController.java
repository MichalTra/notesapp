package com.notesapp.notesapp.controller;

import com.notesapp.notesapp.model.Note;
import com.notesapp.notesapp.repository.NotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NotesController {

    @Autowired
    private NotesRepository notesRepository;

    @RequestMapping(value="/notes", method = RequestMethod.POST)
    public ModelAndView login(Authentication authentication) {

        String user = authentication.getName();

        // TODO login, getdata, etc.
        boolean successfulLogin = authentication.isAuthenticated();

        if (! successfulLogin) {
            ModelAndView modelAndView = new ModelAndView("login.html");
            // TODO modelAndView.addObject("message", "")
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("notes.html");
            // TODO modelAndView.addObject("notesList","");
            return modelAndView;
        }
    }

    @RequestMapping(value="/changeNoteDoneStatus")
    public boolean changeNoteDoneStatus(int id) {
        Note note = notesRepository.findById(id);
        if (note != null) {
            note.setDone(!note.isDone());
            notesRepository.save(note);
            return true;
        } else {
            return false;
        }
    }

    @RequestMapping(value="/editNote")
    public boolean editNote() {
        // TODO
        return true;
    }
}
