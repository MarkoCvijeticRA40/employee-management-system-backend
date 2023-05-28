package com.mcm.EmployeeManagementSystem.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authorization.AuthorityAuthorizationManager;
import org.springframework.security.authorization.AuthorizationManagers;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers(
                        "/auth/**",
                        "/api/permission/**",
                        "/api/user/**",
                        "/api/role/**",
                        "/api/v1/auth/**",
                        "/v2/api-docs",
                        "/v3/api-docs",
                        "/v3/api-docs/**",
                        "/swagger-resources",
                        "/swagger-resources/**",
                        "/configuration/ui",
                        "/configuration/security",
                        "/swagger-ui/**",
                        "/webjars/**",
                        "/swagger-ui.html"
                )
                .permitAll()

//                .requestMatchers("/roles/**").access(AuthorizationManagers.allOf(
//                        AuthorityAuthorizationManager.hasAuthority("admin_update"),
//                        AuthorityAuthorizationManager.hasRole("Administrator")
//                ))
                .requestMatchers("/roles/**").permitAll()

                .requestMatchers("/auth/**").permitAll()

                .requestMatchers("/request/register").permitAll()

//                .requestMatchers("/request/pending").access(AuthorizationManagers.allOf(
//                        AuthorityAuthorizationManager.hasAuthority("admin_read"),
//                        AuthorityAuthorizationManager.hasRole("Administrator")
//                ))
                .requestMatchers("/request/pending").permitAll()


//                .requestMatchers("/request/approve/**").access(AuthorizationManagers.allOf(
//                        AuthorityAuthorizationManager.hasAuthority("admin_update"),
//                        AuthorityAuthorizationManager.hasRole("Administrator")
//                ))
                .requestMatchers("/request/approve/**").permitAll()

//                .requestMatchers("/request/reject/**").access(AuthorizationManagers.allOf(
//                        AuthorityAuthorizationManager.hasAuthority("admin_update"),
//                        AuthorityAuthorizationManager.hasRole("Administrator")
//                ))
                .requestMatchers("/request/reject/**").permitAll()

                .requestMatchers("/users/activate").permitAll()

                .requestMatchers("/users/find/**").permitAll()

//                .requestMatchers("/projects/**").access(AuthorizationManagers.allOf(
//                        AuthorityAuthorizationManager.hasAnyAuthority("admin_update", "admin_read", "admin_create"),
//                        AuthorityAuthorizationManager.hasRole("Administrator")
//                ))
                .requestMatchers("/projects/**").permitAll()

//                .requestMatchers("/assignments/**").access(AuthorizationManagers.allOf(
//                        AuthorityAuthorizationManager.hasAnyAuthority("pm_update", "pm_read"),
//                        AuthorityAuthorizationManager.hasRole("Project manager")
//                ))
                .requestMatchers("/assignments/**").permitAll()

//                .requestMatchers("/skills/**").access(AuthorizationManagers.allOf(
//                        AuthorityAuthorizationManager.hasAnyAuthority("swe_update", "swe_read", "swe_create", "swe_delete"),
//                        AuthorityAuthorizationManager.hasRole("Software engineer")
//                ))
                .requestMatchers("/skills/**").permitAll()

//                .requestMatchers("/users/rolename").access(AuthorizationManagers.allOf(
//                        AuthorityAuthorizationManager.hasAnyAuthority("admin_read"),
//                        AuthorityAuthorizationManager.hasRole("Administrator")
//                ))
                .requestMatchers("/users/rolename").permitAll()

//                .requestMatchers("/users/enabled").access(AuthorizationManagers.allOf(
//                        AuthorityAuthorizationManager.hasAnyAuthority("admin_read"),
//                        AuthorityAuthorizationManager.hasRole("Administrator")
//                ))
                .requestMatchers("/users/enabled").permitAll()

//                .requestMatchers("/users/potential/workers").access(AuthorizationManagers.allOf(
//                        AuthorityAuthorizationManager.hasAnyAuthority("admin_read"),
//                        AuthorityAuthorizationManager.hasRole("Administrator")
//                ))
                .requestMatchers("/users/potential/workers").permitAll()

                .requestMatchers("/users/email/**").permitAll()

//                .requestMatchers("/users/update").access(AuthorizationManagers.allOf(
//                        AuthorityAuthorizationManager.hasAnyAuthority("admin_update"),
//                        AuthorityAuthorizationManager.hasRole("Administrator")
//                ))
                .requestMatchers("/users/update").permitAll()

//                .requestMatchers("/users/register/administrator").access(AuthorizationManagers.allOf(
//                        AuthorityAuthorizationManager.hasAnyAuthority("admin_create"),
//                        AuthorityAuthorizationManager.hasRole("Administrator")
//                ))

                .requestMatchers("/users/register/administrator").permitAll()

//                .requestMatchers("/users/search/engineers/**").access(AuthorizationManagers.allOf(
//                        AuthorityAuthorizationManager.hasAnyAuthority("admin_read"),
//                        AuthorityAuthorizationManager.hasRole("Administrator")
//                ))
                .requestMatchers("/users/search/engineers/**").permitAll()

//                .requestMatchers("/users/**/engineer").access(AuthorizationManagers.allOf(
//                        AuthorityAuthorizationManager.hasAnyAuthority("swe_update", "swe_read", "swe_delete"),
//                        AuthorityAuthorizationManager.hasRole("Software engineer")
//                ))
//
//                .requestMatchers("/users/**/project-manager").access(AuthorizationManagers.allOf(
//                        AuthorityAuthorizationManager.hasAnyAuthority("pm_update"),
//                        AuthorityAuthorizationManager.hasRole("Project manager")
//                ))



                .anyRequest()
                .permitAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();

    }
}