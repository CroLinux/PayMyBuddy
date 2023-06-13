package com.paymybuddy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.paymybuddy.model.User;
import com.paymybuddy.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	   @Autowired
	    private UserRepository userRepository;

	    @Override
	    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
	    	System.out.println("HELLOOOOOOO");

	        User user = userRepository.findByEmail(usernameOrEmail);
	        if (user != null) {

	            
	            return new org.springframework.security.core.userdetails.User(
	            	    user.getEmail(), user.getPassword(),
	            	    List.of(new SimpleGrantedAuthority(user.getAuthority().strip()))
	            	);
	        } else {
	            throw new UsernameNotFoundException("Invalid email or password");
	        }
	    }

}
