package com.luv2code.springboot.cruddemo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class DemoSecurityConfig {

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource){
        return new JdbcUserDetailsManager(dataSource);
    }
//   @Bean
//   public InMemoryUserDetailsManager userDetailsManager(){
//        UserDetails mahesh= User.builder()
//                .username("mahesh")
//                .password("{noop}dummy")
//                .roles("EMPLOYEE")
//                .build();
//        UserDetails lokesh= User.builder()
//                .username("lokesh")
//                .password("{noop}dummy")
//                .roles("EMPLOYEE","MANAGER")
//                .build();
//        UserDetails lavanya= User.builder()
//                .username("lavanya")
//                .password("{noop}dummy")
//                .roles("EMPLOYEE","MANAGER","ADMIN")
//                .build();
//        return new  InMemoryUserDetailsManager(mahesh,lavanya,lokesh);
//    }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
       http.authorizeHttpRequests(configurer->
               configurer.requestMatchers(HttpMethod.GET,"/api/employees").hasRole("EMPLOYEE")
                       .requestMatchers(HttpMethod.DELETE,"/api/employees/**").hasRole("ADMIN")
                       .requestMatchers(HttpMethod.GET,"/api/employees/**").hasRole("EMPLOYEE")
                .requestMatchers(HttpMethod.POST,"/api/employees/**").hasRole("MANAGER")
                       .requestMatchers(HttpMethod.PUT,"/api/employees").hasRole("MANAGER"));
       http.httpBasic(Customizer.withDefaults());
       http.csrf(csrf->csrf.disable());
       return  http.build();

    }
}
