package com.notesapp.notesapp.security;

import com.notesapp.notesapp.model.User;
import com.notesapp.notesapp.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationHelper {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PasswordAuthenticationProvider passwordAuthenticationProvider;



    public Authentication manualAuthenticate(Authentication authentication, String username, String password) {
        User user = usersRepository.getByUsername(username);

        if (user != null && user.getUserId() > 0 && passwordEncoder.matches(password, user.getPassword())) {
            UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(user.getUsername(), null, passwordAuthenticationProvider.getGrantedAuthorities(user.getUsername()));
            //Authentication auth = authManager.authenticate(authReq);
            //SecurityContext sc = SecurityContextHolder.getContext();
            //sc.setAuthentication(auth);
            //HttpSession session = req.getSession(true);
            //session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, sc);
            return authReq;
        } else {
            throw new BadCredentialsException("Invalid credentials");
        }
    }

    public String getLoggedUser(Authentication authentication) {
        String username = null;

        if ((authentication != null) && (authentication.isAuthenticated())) {
            username = authentication.getName();
        }

        return username;
    }
}
