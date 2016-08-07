package com.hanksha.ces.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

/**
 * Created by vivien on 6/30/16.
 */

@EnableWebSecurity
class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource

    @Autowired
    void configureGlobal(AuthenticationManagerBuilder auth) {
        auth
            .jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery('SELECT username, password, enabled FROM users WHERE username = ?')
                .authoritiesByUsernameQuery('SELECT username, role FROM user_roles WHERE username = ?')
    }

    @Override
    protected void configure(HttpSecurity http) {
        http
            .csrf()
                .ignoringAntMatchers('/api/**')
                .and()
            .authorizeRequests()
                .antMatchers("/css/**", "/js/**", "/images/**","/fonts/**").permitAll()
                .antMatchers("/").permitAll()
                .anyRequest().permitAll()
//                .anyRequest().authenticated()
                .and()
            .formLogin()
                .defaultSuccessUrl("/")
                .loginPage("/login")
                .permitAll()
                .and()
            .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
    }

    @Override
    void configure(WebSecurity web) {
    }
}
