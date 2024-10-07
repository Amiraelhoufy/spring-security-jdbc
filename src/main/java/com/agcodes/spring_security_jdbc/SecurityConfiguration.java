package com.agcodes.spring_security_jdbc;

import static org.springframework.security.config.Customizer.withDefaults;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

  // You can access h2 db through: http://localhost:8080/h2-console/
  @Autowired
  private DataSource dataSource;
  @Autowired
  public void configure(AuthenticationManagerBuilder auth) throws Exception {

    // Having an external databases and datasource are configured in application.properties
    auth.jdbcAuthentication()
        .dataSource(dataSource);

    // By default, spring security expects certain table and column names
    // But we can override them using the below queries

//        .usersByUsernameQuery("SELECT username, password, enabled FROM users WHERE username = ?")
//        .authoritiesByUsernameQuery("SELECT username, authority FROM authorities WHERE username = ?");

  }

  @Bean
  public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
//    return NoOpPasswordEncoder.getInstance();

  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf
            .ignoringRequestMatchers("/h2-console/**"))  // Ignore CSRF for H2 console
        .authorizeRequests(authorize -> authorize
            .requestMatchers("/h2-console/**").permitAll() // Allow access to H2 console
            .requestMatchers("/admin").hasRole("ADMIN")
            .requestMatchers("/user").hasAnyRole("USER", "ADMIN")
            .requestMatchers("/").permitAll()
        )
        .formLogin(withDefaults())  // Default form login configuration
        .headers(headers -> headers
            .frameOptions().sameOrigin()); // Allow H2 console to be displayed in a frame

// Default form login configuration
    return http.build();

  }


  // To get the encrypted password to be inserted in data.sql

 /* @Bean
  public CommandLineRunner encodePassword() {
    return args -> {
      PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
      String userPassword = "blah";
      String adminPassword = "admin123";
      String encodedUserPassword = passwordEncoder.encode(userPassword);
      String encodedAdminPassword = passwordEncoder.encode(adminPassword);
      System.out.println("Encoded password for 'blah': " + encodedUserPassword);
      System.out.println("Encoded password for 'admin': " + encodedAdminPassword);
    };
  }
*/

  @Bean
  public CommandLineRunner checkDatabaseConnection(DataSource dataSource) {
    return args -> {
      try (Connection conn = dataSource.getConnection()) {
        System.out.println("Connected to the H2 database successfully!");
      } catch (SQLException e) {
        System.out.println("Failed to connect to the H2 database: " + e.getMessage());
      }
    };
  }


}
