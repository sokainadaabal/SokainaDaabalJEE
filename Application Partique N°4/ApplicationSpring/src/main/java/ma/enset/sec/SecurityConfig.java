package ma.enset.sec;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;
import java.util.ArrayList;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private DataSource dataSource;

    // pycrypte un algo de cryptage de password ;
/*    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        PasswordEncoder passwordEncoder=passwordEncoder();
        ArrayList<UserDetails> users= new ArrayList<>();
        UserDetails user = User.withUsername("user1")
                .username("user1")
                .password(passwordEncoder.encode("1234"))
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
        String s=passwordEncoder.encode("1234");
        System.out.println(s);
        users.add(user);
        users.add(user2);
        users.add(admin);
        return new InMemoryUserDetailsManager(users);
    }*/
    @Bean
    public UserDetailsManager users() {
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
        users.setUsersByUsernameQuery("select username as principal,password as credentials,active from users where username=?");
        users.setAuthoritiesByUsernameQuery("select username as principal,role as role from users_roles where username=?");
        users.setRolePrefix("ROLE_");
        return users;
    }

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(@NotNull HttpSecurity http) throws Exception {
        http.formLogin();
        http.authorizeRequests().requestMatchers("/").permitAll();
        http.authorizeRequests().requestMatchers("/admin/**").hasRole("ADMIN");
        http.authorizeRequests().requestMatchers("/user/**").hasRole("USER");
        http.authorizeRequests().requestMatchers("/webjars/***").permitAll();
        http.authorizeRequests().anyRequest().authenticated();
        http.exceptionHandling().accessDeniedPage("/403");
        return http.build();
    }

 @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
 }
}
