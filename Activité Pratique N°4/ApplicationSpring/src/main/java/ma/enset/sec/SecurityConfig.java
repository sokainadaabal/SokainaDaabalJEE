package ma.enset.sec;
import ma.enset.sec.services.UserDetailsServiceImpl;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
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
    /**@Bean
    public UserDetailsManager users() {
       JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
        users.setUsersByUsernameQuery("select username as principal,password as credentials,active from users where username=?");
        users.setAuthoritiesByUsernameQuery("select username as principal,role as role from users_roles where username=?");
        users.setRolePrefix("ROLE_");
        return users;
        return  null;
    }**/

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }


    @Bean
    SecurityFilterChain defaultSecurityFilterChain(@NotNull HttpSecurity http) throws Exception {
        http.formLogin();
        http.authorizeRequests().requestMatchers("/").permitAll();
        http.authorizeRequests().requestMatchers("/admin/**").hasAuthority("ADMIN");
        http.authorizeRequests().requestMatchers("/user/**").hasAuthority("USER");
        http.authorizeRequests().requestMatchers("/webjars/***").permitAll();
        http.authorizeRequests().anyRequest().authenticated();
        http.exceptionHandling().accessDeniedPage("/403");
        return http.build();
    }

 }
