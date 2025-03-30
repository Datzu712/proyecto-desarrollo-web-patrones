package com.dream.restapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.dream.restapi.dto.AuthRegisterBody;
import com.dream.restapi.model.User;
import com.dream.restapi.repository.UserRepository;
import com.dream.restapi.utils.AppLogger;

import jakarta.servlet.http.Cookie;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    // Modified configuration load to ignore missing .env file
    private Dotenv config = Dotenv.configure().load();

        /**
     * Authenticates a user based on the provided email and password.
     * If authentication is successful, a JWT token is generated and added as an HTTP-only cookie to the response.
     *
     * @param email the email of the user attempting to log in
     * @param password the password of the user attempting to log in
     * @param res the HttpServletResponse to which the JWT token cookie will be added
     * @return a ResponseEntity containing a success message and user data if authentication is successful,
     *         or an error message if authentication fails
     */
    public ResponseEntity<String> login(String email, String password, HttpServletResponse res) {
        User targetUser = userRepository.findByEmail(email);
        if (targetUser == null || !this.checkPassword(targetUser, password)) {
            return ResponseEntity.status(401).body("{\"message\": \"Invalid email or password\"}");
        }

        String secret = this.getSecret();
        Algorithm algorithm = Algorithm.HMAC256(secret);
        String token = JWT.create()
            .withIssuer("the_dream_cr")
            .withSubject(targetUser.getId().toString())
            .sign(algorithm);

        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");

        res.addCookie(cookie);

        return ResponseEntity.ok("{\"message\": \"Login successful\", \"data\":" + targetUser.toJSON() + "}");
    }

    public ResponseEntity<String> logout(HttpServletResponse res) {
        Cookie cookie = new Cookie("token", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);

        res.addCookie(cookie);

        return ResponseEntity.ok("{\"message\": \"Logout successful\"}");
    }


    public ResponseEntity<String> register(AuthRegisterBody body) {
        if (body.getEmail() == null || body.getPassword() == null || body.getName() == null) {
            return ResponseEntity.badRequest().body("{\"message\": \"Email, password and name are required\"}");
        }

        if (userRepository.findByEmail(body.getEmail()) != null) {
            return ResponseEntity.status(409).body("{\"message\": \"Email already exists\"}");
        }

        User user = new User();
        user.setEmail(body.getEmail());
        user.setName(body.getName());

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(user.getPasswordHash());
        user.setPasswordHash(hashedPassword);
        user.setRole("user");

        User savedUser = userRepository.save(user);

        return ResponseEntity.ok("{\"message\": \"User registered successfully\", \"data\":" + savedUser.toJSON() + "}");
    }

    /**
     * Retrieves the JWT secret key from the configuration.
     * If the secret key is not found and the environment is set to "dev",
     * a default secret key is used and an error is logged.
     * If the secret key is not found and the environment is not "dev",
     * the application will print an error message and terminate.
     *
     * @param forWebsocket a Boolean indicating whether to retrieve the secret key for WebSocket connections.
     * @return the JWT secret key as a String.
     */
    public String getSecret() {
        String secret = this.config.get("JWT_SECRET");
        if (secret == null) {
            if (this.config.get("ENV").equals("dev")) {
                AppLogger.error("JWT_SECRET not found in .env file. Using default secret...");
                secret = "U_SHOULD_DEFINE_JWT_SECRET_IN_ENV_FILE";
            } else {
                System.out.println("JWT_SECRET not found in .env file. Please set JWT_SECRET in .env file, otherwise the server will not start bc that is required for generating JWT secrets.");
                System.exit(1);
            }
        }
        return secret;
    }

    private boolean checkPassword(User user, String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(password, user.getPasswordHash());
    }

}
