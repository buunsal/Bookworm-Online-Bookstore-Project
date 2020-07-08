package com.bookworm.demo;

import com.bookworm.demo.model.User;
import com.bookworm.demo.repository.RoleRepository;
import com.bookworm.demo.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.ejb.EJB;
import java.util.ArrayList;
import java.util.List;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @EJB
    private UserRepository userRepository;

    @EJB
    private RoleRepository roleRepository;

    @EJB
    private BCryptPasswordEncoder encoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        List<GrantedAuthority> grantedAuths = new ArrayList<>();

        if (email.equalsIgnoreCase("admin1") && password.equalsIgnoreCase("admin1")) {
            grantedAuths.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else if (email.equalsIgnoreCase("user1") && password.equalsIgnoreCase("user1")) {
            grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
        } else {
            final User user = userRepository.findByEmail(email);

            if (user == null) {
                throw new UsernameNotFoundException("Authentication failed");
            } else if (!encoder.matches(password, user.getPassword())) {
                throw new UsernameNotFoundException("Passwords does not match !..");
            } else {
                if (user.getRoles().contains(roleRepository.findByRole("ROLE_ADMIN"))) {
                    grantedAuths.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                } else if (user.getRoles().contains(roleRepository.findByRole("ROLE_USER"))) {
                    grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
                }
            }
        }

        if (grantedAuths.size() > 0) {
            return new UsernamePasswordAuthenticationToken(email, password, grantedAuths);
        }

        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
