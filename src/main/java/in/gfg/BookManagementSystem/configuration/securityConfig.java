package in.gfg.BookManagementSystem.configuration;

import in.gfg.BookManagementSystem.service.StudentService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class securityConfig {

    @Autowired
    private StudentService studentService;

    @Bean
    public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
    daoAuthenticationProvider.setUserDetailsService(studentService);
    daoAuthenticationProvider.setPasswordEncoder(getPasswordEncoder());
    return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.authorizeHttpRequests(authorize->authorize
                .requestMatchers("/txn/create/**").hasAnyAuthority("ADMIN","USER")
                .requestMatchers("/txn/return/**").hasAuthority("ADMIN")
                .requestMatchers("/book/create/**").hasAuthority("ADMIN")
                .anyRequest().permitAll())
                .formLogin(Customizer.withDefaults()).httpBasic(Customizer.withDefaults()).csrf(csrf->csrf.disable());
        return httpSecurity.build();
    }


    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }


}


