package com.example.block7crudvalidation.infrastructure.security;

import com.example.block7crudvalidation.infrastructure.security.authorities.Role;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@AllArgsConstructor
public class SecurityConfig {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtConfig jwtConfig;

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        var usernameAuthenticationFilter = new CustomAuthenticationFilter(
            authenticationManagerBuilder.getOrBuild(), this.jwtConfig.getKey(), this.jwtConfig.getExpirationDays()
        );
        usernameAuthenticationFilter.setFilterProcessesUrl("/login");

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .csrf().disable()
            .addFilter(usernameAuthenticationFilter)
            .addFilterAfter(new CustomAuthorizationFilter(this.jwtConfig.getKey()), CustomAuthenticationFilter.class)
            .authorizeRequests()
            .antMatchers("/addperson").permitAll()
            .antMatchers(HttpMethod.GET, "**").permitAll()
            .and().authorizeRequests().anyRequest().hasAuthority(Role.ROLE_ADMIN.getAuthority());

        return http.build();
    }
}
