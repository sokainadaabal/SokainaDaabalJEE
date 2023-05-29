package ma.enset.ebankingbackend.security.config;


import lombok.AllArgsConstructor;
import ma.enset.ebankingbackend.security.filters.JwtAuthenticationFilter;
import ma.enset.ebankingbackend.security.filters.JwtAuthorizationFilter;
import ma.enset.ebankingbackend.security.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@AllArgsConstructor

public class SecurityConfig  extends WebSecurityConfigurerAdapter {

    private UserDetailsServiceImpl detailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(detailsService);
    }

    // bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();// csrf protection disabled
        http.formLogin().disable().cors(httpSecurityCorsConfigurer -> {
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues(); // allow all
            corsConfiguration.addAllowedMethod("GET"); // add other methods as per your requirement
            corsConfiguration.addAllowedMethod("POST");
            corsConfiguration.addAllowedMethod("PUT");
            corsConfiguration.addAllowedMethod("DELETE");
            source.registerCorsConfiguration("/**",  corsConfiguration);
            httpSecurityCorsConfigurer.configurationSource(source);
        });
        // disabled cors issues
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);// session management disabled
        http.authorizeHttpRequests().antMatchers("/login/**",
                "/swagger-ui**",
                "/bankAccount/**",
                "/customer/**",
                JwtConfig.REFRESH_PATH).permitAll();
        http.addFilter( new JwtAuthenticationFilter(authenticationManagerBean() ) );
        http.addFilterBefore( new JwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);//@formatter:on

    }
}
