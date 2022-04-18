package com.wisdom.springsecurityjwtdemo;

import com.wisdom.springsecurityjwtdemo.models.AuthenticateRequest;
import com.wisdom.springsecurityjwtdemo.models.AuthenticateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
public class AppController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

    @RequestMapping("/home")
    public String home(){
        return "Welcome";
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticateRequest authenticateRequest)
            throws Exception {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticateRequest.getUsername(), authenticateRequest.getPassword())
            );
        }catch (BadCredentialsException e){
            throw new Exception("Incorrect Username and Password", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticateRequest.getUsername());

        String jwt = jwtUtils.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticateResponse(jwt));
    }
}
