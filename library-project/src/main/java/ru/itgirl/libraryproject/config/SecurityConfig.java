package ru.itgirl.libraryproject.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import ru.itgirl.libraryproject.dto.UsersDto;
import ru.itgirl.libraryproject.service.UsersService;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UsersService usersService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) ->
                        authorize.requestMatchers("/book").hasRole("USER")
                                .requestMatchers("/book/v2").hasRole("ADMIN")
                                .requestMatchers("/books").hasRole("ADMIN")
                                .anyRequest().authenticated()
                )
                .httpBasic();

        return http.build();
    }

    @Bean
    public UserDetailsService users() {
        User.UserBuilder users = User.withDefaultPasswordEncoder();
        UsersDto userDB = usersService.getUserRoles("user", "password");
        UsersDto adminDB = usersService.getUserRoles("admin", "password");
        UserDetails user = users
                .username(userDB.getUser_name())
                .password(userDB.getUser_password())
                .roles(userDB.getUser_roles().toArray(String[]::new))
                .build();
        UserDetails admin = users
                .username(adminDB.getUser_name())
                .password(adminDB.getUser_password())
                .roles(adminDB.getUser_roles().toArray(String[]::new))
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }
}

