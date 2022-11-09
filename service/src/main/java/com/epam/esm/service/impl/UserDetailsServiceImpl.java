package com.epam.esm.service.impl;

import com.epam.esm.dao.jpaRepository.UsersRepository;
import com.epam.esm.entity.User;
import com.epam.esm.security.UsersDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsersRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UsersRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Optional<User> user = Optional.ofNullable(userRepository.readByName(name).orElseThrow(() -> new UsernameNotFoundException("User not found ")));
        return new UsersDetails(user.get());
    }
}