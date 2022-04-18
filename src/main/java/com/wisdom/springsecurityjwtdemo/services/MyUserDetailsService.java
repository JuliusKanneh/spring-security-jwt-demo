package com.wisdom.springsecurityjwtdemo.services;

import com.wisdom.springsecurityjwtdemo.models.MyUserDetails;
import com.wisdom.springsecurityjwtdemo.models.User;
import com.wisdom.springsecurityjwtdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipal;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return new User("user", "pass", new ArrayList<>());
        Optional<User> user = userRepository.findByUsername(username);

        user.orElseThrow(()-> new UsernameNotFoundException("Username not found"));

        return user.map(MyUserDetails::new).get();
    }
}
