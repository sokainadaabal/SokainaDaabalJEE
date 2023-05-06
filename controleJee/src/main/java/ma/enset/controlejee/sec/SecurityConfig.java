package ma.enset.controlejee.sec;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // duplicated il faut utilise @AllArgsConstructor
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        PasswordEncoder passwordEncoder=passwordEncoder();
        ArrayList<UserDetails> users= new ArrayList<>();
        UserDetails user = User.withUsername("user1")
                .username("user1")
                .password(passwordEncoder.encode("1234")) // {noop} si en n'utilise pas PasswordEncoder
                .roles("USER")
                .build();
        UserDetails user2 = User.withUsername("user2")
                .username("user2")
                .password(passwordEncoder.encode("1994"))
                .roles("USER")
                .build();
        UserDetails admin = User.withUsername("admin")
                .username("admin")
                .password(passwordEncoder.encode("1999"))
                .roles("ADMIN","USER")
                .build();
        users.add(user);
        users.add(user2);
        users.add(admin);
        return new InMemoryUserDetailsManager(users);
    }

    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    SecurityFilterChain defaultSecurityFilterChain(@NotNull HttpSecurity http) throws Exception {
        http.formLogin().permitAll(); // formulaire authentication contient csrf caché // personnaliser la page
        http.authorizeRequests().requestMatchers("/").permitAll();
        http.authorizeRequests().requestMatchers("/webjars/***").permitAll();
        http.authorizeRequests().requestMatchers("/admin/**").hasRole("ADMIN");
        http.authorizeRequests().requestMatchers("/abonnement/**").hasRole("USER");
        http.authorizeRequests().requestMatchers("/client/**").hasRole("USER");
        http.authorizeRequests().anyRequest().authenticated(); // toute request envoyer doit que l'utilisateur authentifier
        http.exceptionHandling().accessDeniedPage("/403");
        return http.build();
    }
}
