# Spring Security JDBC

This project is a simple Spring Boot application that shows how to use Spring Security with a JDBC authentication method. It uses an H2 in-memory database to store user information.

## What It Does

- **User Authentication:** Users can log in with credentials stored in a database.
- **Role-Based Access:** Different user roles (like ADMIN and USER) control who can access specific parts of the application.
- **H2 Database:** An in-memory H2 database is used for easy setup and testing.
- **H2 Console:** A web interface to view and manage the database.
- **Password Encoding:** For security, consider using password encoding techniques like BCryptPasswordEncoder.
- **Role-Based Access Control:** Customize the access control rules in authorizeRequests to implement fine-grained authorization.

## How It Works

1. **Security Configuration**: 
   - The app uses `@Autowired` to get the database connection.
   - It sets up user authentication with `AuthenticationManagerBuilder`.
   - The security settings are configured in the `securityFilterChain` method, which replaces older methods that are now deprecated.

2. **Access Control**: 
   - Specific URLs (like the H2 console) are allowed without login.
   - Different endpoints are protected based on user roles.

### Important Note

The ```WebSecurityConfigurerAdapter``` is deprecated in Spring Security 5.4. It's recommended to use the ```SecurityFilterChain``` approach as shown in the securityFilterChain method

### Example Security Code

Here’s how the security settings look:

```java
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf
            .ignoringRequestMatchers("/h2-console/**"))  // Ignore CSRF for H2 console
        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers("/h2-console/**").permitAll() // Allow access to H2 console
            .requestMatchers("/admin").hasRole("ADMIN")
            .requestMatchers("/user").hasAnyRole("USER", "ADMIN")
            .requestMatchers("/").permitAll()
        )
        .formLogin(withDefaults())  // Default login form
```

## How to Run the Project

1. **Clone the repository:**
```bash
   git clone https://github.com/Amiraelhoufy/spring-security-jdbc.git
   cd spring-security-jdbc
   ```

2. **Build the project with Maven:**

 ```bash
./mvnw clean install
 ```


3. **Run the application:**

 ```bash
./mvnw spring-boot:run
 ```

4. **Access the H2 console at:**
 ```
bash
http://localhost:8080/h2-console
 ```

Use these settings to log in:

• JDBC URL: ```jdbc:h2:mem:testdb```
• Username: ```sa```
• Password: ```password```

## Database Setup

The app uses two tables, users and authorities, to manage user accounts and their roles. You can add users and roles in the data.sql file located in src/main/resources.
