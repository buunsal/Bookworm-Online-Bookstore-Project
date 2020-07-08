package com.bookworm.demo.service;

import com.bookworm.demo.exception.ResourceNotFoundException;
import com.bookworm.demo.model.Role;
import com.bookworm.demo.model.User;
import com.bookworm.demo.repository.RoleRepository;
import com.bookworm.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ejb.EJB;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @EJB
    private UserRepository userRepository;
    @EJB
    private RoleRepository roleRepository;
    @EJB
    private BCryptPasswordEncoder encoder;
    @EJB
    private AuthenticationManager authenticationManager;

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void updatePassword(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void saveAdmin(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setActive(true);
        Role userRole = roleRepository.findByRole("ROLE_ADMIN");
        user.setRoles(new HashSet<>(Collections.singletonList(userRole)));
        userRepository.save(user);
    }

    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void registration(User user) {
        String password = user.getPassword();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setActive(true);
        Role userRole = roleRepository.findByRole("ROLE_USER");
        user.setRoles(new HashSet<>(Collections.singletonList(userRole)));
        userRepository.save(user);
        autologin(user, password);
    }

    @Override
    @Transactional
    public void deleteUser(int Id) {
        userRepository.deleteById(Id);
    }

    @Override
    public User getUser(int Id) throws ResourceNotFoundException {
        return userRepository.findById(Id).orElseThrow(
                () -> new ResourceNotFoundException(Id));
    }

    @Override
    @Transactional
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public void autologin(User user, String password) {
        List<GrantedAuthority> grantedAuths = new ArrayList<>();

        if (user.getRoles().contains(roleRepository.findByRole("ROLE_ADMIN"))) {
            grantedAuths.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else if (user.getRoles().contains(roleRepository.findByRole("ROLE_USER"))) {
            grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        if (grantedAuths.size() > 0) {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user.getEmail(), password, grantedAuths);

            authenticationManager.authenticate(usernamePasswordAuthenticationToken);

            if (usernamePasswordAuthenticationToken.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                logger.info(String.format("Auto login successfully! - %s", user.getEmail()));
            }
        } else {
            logger.info(String.format("Auto login error! - %s", user.getEmail()));
        }
    }
}
