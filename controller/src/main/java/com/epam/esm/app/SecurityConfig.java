package com.epam.esm.app;

import com.epam.esm.security.JWTFilter;
import com.epam.esm.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@SpringBootApplication(scanBasePackages = "com.epam.esm")
@EntityScan(basePackages = "com.epam.esm.entity")
@EnableWebSecurity
@EnableJpaRepositories(basePackages = "com.epam.esm.dao.jpaRepository")
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE + 10)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String HTTP_FOR_TAG = "/tag/**";
    private static final String HTTP_FOR_GC = "/certificates/**";
    private static final String HTTP_FOR_USERS = "/users/**";
    private static final String HTTP_FOR_ORDERS = "/orders/**";
    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private static final String ROLE_USER = "ROLE_USER";
    private static final String HTTP_TOKEN = "/token";
    private static final String HTTP_AUTH_LOGIN = "/auth/login";
    private static final String HTTP_AUTH_REGISTRATION = "/auth/registration";
    private static final String HTTP_ERROR = "/error";
    private static final String HTTP_LOGOUT = "/logout";
    private static final String HTTP_PROCESS_LOGIN = "/process_login";
    private static final String HTTP_AUTH_LOGIN_ERROR = "/auth/login?error";

    @Autowired
    private JWTFilter jwtFilter;

    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    public SecurityConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * Method for password encoder
     *
     * @param authenticationManagerBuilder
     * @throws Exception
     */
    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService)
                .passwordEncoder(getPasswordEncoder());
    }

    /**
     * Method for configuration spring security
     *
     * @param httpSecurity
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.DELETE, HTTP_FOR_TAG, HTTP_FOR_ORDERS, HTTP_FOR_GC, HTTP_FOR_USERS).hasAuthority(ROLE_ADMIN)
                .antMatchers(HttpMethod.PATCH, HTTP_FOR_TAG, HTTP_FOR_ORDERS, HTTP_FOR_GC).hasAuthority(ROLE_ADMIN)
                .antMatchers(HttpMethod.GET, HTTP_FOR_TAG, HTTP_FOR_ORDERS, HTTP_FOR_GC).hasAnyAuthority(ROLE_ADMIN, ROLE_USER)
                .antMatchers(HttpMethod.POST, HTTP_FOR_GC, HTTP_FOR_TAG).hasAnyAuthority(ROLE_ADMIN, ROLE_USER)
                .antMatchers(HTTP_AUTH_LOGIN, HTTP_AUTH_REGISTRATION, HTTP_ERROR, HTTP_TOKEN)
                .permitAll().anyRequest().authenticated()
                .and()
                .formLogin().loginPage(HTTP_AUTH_LOGIN)
                .loginProcessingUrl(HTTP_PROCESS_LOGIN)
                .failureUrl(HTTP_AUTH_LOGIN_ERROR)
                .and()
                .logout()
                .logoutUrl(HTTP_LOGOUT)
                .logoutSuccessUrl(HTTP_AUTH_LOGIN);
        httpSecurity.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
