package com.notesapp.notesapp.controller;

import com.notesapp.notesapp.model.Note;
import com.notesapp.notesapp.repository.NotesRepository;
import com.notesapp.notesapp.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class NotesController {

    @Autowired
    private NotesRepository notesRepository;

    @Autowired
    private UsersRepository usersRepository;

    @RequestMapping(value="/notes", method = RequestMethod.POST)
    public ModelAndView login(Authentication authentication) {

        boolean successfulLogin = authentication.isAuthenticated();

        if (! successfulLogin) {
            ModelAndView modelAndView = new ModelAndView("login.html");
            modelAndView.addObject("message", "Unauthorized request");
            return modelAndView;
        } else {
            String user = authentication.getName();
            ModelAndView modelAndView = new ModelAndView("notes.html");
            String name = authentication.getName();
            long userId = usersRepository.getByUsername(name).getUserId();
            List<Note> notesList = notesRepository.findByUserId(userId);
            modelAndView.addObject("notesList",notesList);
            modelAndView.addObject("loggedUser", user);
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
    public ModelAndView editNote() {
        ModelAndView modelAndView = new ModelAndView("notedetail.html");
        Note note = notesRepository.findById(0); // TODO fill the id based on the edited note
        modelAndView.addObject("description", note.getDescription());
        modelAndView.addObject("deadline", note.getDeadline());
        return modelAndView;
    }

    @RequestMapping(value="/newNote")
    public ModelAndView newNote() {
        ModelAndView modelAndView = new ModelAndView("notedetail.html");
        modelAndView.addObject("description", "");
        modelAndView.addObject("deadline", "");
        return modelAndView;
    }
}
