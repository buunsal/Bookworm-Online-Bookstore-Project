package com.bookworm.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.ejb.EJB;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @EJB
    private CustomAuthenticationProvider authProvider;

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Disable CSRF (cross site request forgery)
        http.csrf().disable();

        // form login
        http
                .authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers("/javax.faces.resource/**").permitAll()
                    .antMatchers("/index.xhtml").permitAll()
                    .antMatchers("/signin.xhtml").permitAll()
                    .antMatchers("/registration.xhtml").permitAll()
                    .antMatchers("/passwordrecovery.xhtml").permitAll()
                    .antMatchers("/newreleasespage.xhtml").permitAll()
                    .antMatchers("/template.xhtml").permitAll()
                    .antMatchers("/editprofile.xhtml").permitAll()
                    .antMatchers("/author.xhtml").permitAll()
                    .antMatchers("/publisher.xhtml").permitAll()
                    .antMatchers("/category.xhtml").permitAll()
                    .antMatchers("/bestsellerpage.xhtml").permitAll()
                    .antMatchers("/viewbook.xhtml").permitAll()
                    .antMatchers("/access-denied.xhtml").permitAll()
                    .antMatchers("/admin/**").hasAuthority("ROLE_USER")
                    .antMatchers("/admin/**").hasAuthority("ROLE_ADMIN").anyRequest().fullyAuthenticated()
                    .and()
                .formLogin()
                    .loginPage("/signin.xhtml")
                    .defaultSuccessUrl("/redirect.xhtml")
                    .failureUrl("/signin.xhtml?authfailed=true")
                    .permitAll()
                    .usernameParameter("email").passwordParameter("password")
                    .and()
                .logout()
                    .logoutSuccessUrl("/index.xhtml")
                    .logoutUrl("/j_spring_security_logout")
                    .logoutSuccessUrl("/index.xhtml")
                .deleteCookies("JSESSIONID")
                    .and()
                .exceptionHandling()
                    .accessDeniedPage("/access-denied.xhtml");

        // allow to use resource links like pdf
        http.headers().frameOptions().sameOrigin();

    }

    @Override
    public void configure(WebSecurity web) {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
    }

}
