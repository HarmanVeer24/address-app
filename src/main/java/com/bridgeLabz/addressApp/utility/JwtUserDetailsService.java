package com.bridgeLabz.addressApp.utility;

import com.bridgeLabz.addressApp.model.User;
import com.bridgeLabz.addressApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class JwtUserDetailsService implements UserDetailsService{
    @Autowired
    private UserRepository userRepository;
    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException{
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: "+ email));
        return user;
    }
}
