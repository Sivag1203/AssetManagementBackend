//package com.backend.assetmanagement.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration 
//public class SecurityConfig {
//	
//	@Bean
//	UserDetailsService users() {
//		UserDetails employee = User.builder()
//			.username("employee")
//			.password("{noop}employee123")
//			.roles("EMPLOYEE")
//			.build();
//		UserDetails admin = User.builder()
//			.username("admin")
//			.password("{noop}admin123")
//			.roles("ADMIN")
//			.build();
//		return new InMemoryUserDetailsManager(employee, admin);
//	}
//	
//	@Bean
//	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//		http
//			.authorizeHttpRequests(authorize -> authorize
//				.anyRequest().authenticated()  
//			)
//		 .httpBasic(Customizer.withDefaults());
//		return http.build();
//	}
//}