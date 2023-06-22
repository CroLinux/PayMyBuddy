package com.paymybuddy.configuration;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

 	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
         http.csrf(withDefaults())
                 .authorizeHttpRequests((requests) -> requests
                                 .requestMatchers("/login").permitAll()
                                 .requestMatchers("/register").permitAll()
                                 .requestMatchers("/process_register").permitAll()
                                 .anyRequest().authenticated()
                 )
                 .formLogin((form) -> form
                                 .loginPage("/login")
                                 .loginProcessingUrl("/login")
                                 .defaultSuccessUrl("/home")
                                 .permitAll()
                 )
                 .logout((logout) -> logout
                		    .logoutUrl("/logout")
                		    .deleteCookies("JSESSIONID", "remember-me") // Optionally, specify additional cookies you want to delete
                		    .deleteCookies()
                		    .clearAuthentication(true)
                		    .invalidateHttpSession(true)
                		    .permitAll())
                 .exceptionHandling(handling -> handling.accessDeniedPage("/access-denied"));
         return http.build();
     }
    
}
