package com.dream.restapi.middleware;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.dream.restapi.model.User;
import com.dream.restapi.repository.UserRepository;
import com.dream.restapi.services.AuthService;
import com.dream.restapi.utils.RequestContext;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request, 
        @NonNull HttpServletResponse response, 
        @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String requestPath = request.getRequestURI();
        System.out.println("Request path: " + requestPath);
        
        if (requestPath.startsWith("/api/auth")) {
            System.out.println("Skipping JWT verification for auth endpoint");
            filterChain.doFilter(request, response);
            return;
        }
        
        String token = this.getJwtFromCookies(request);
        if (token == null) {
            System.out.println("No token found in cookies");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
            return;
        }
        
        String jwtSecret = this.authService.getSecret();
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
            JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("the_dream_cr")
                .build();
            DecodedJWT decodedJwt = verifier.verify(token);
            String userId = decodedJwt.getSubject();
            
            Optional<User> targetUser = this.userRepository.findById(Integer.parseInt(userId));
            if (!targetUser.isPresent()) {
                System.out.println("User not found");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                return;
            }
            
            System.out.println("User found: " + targetUser.get().getEmail());
            this.setAuthentication(request, decodedJwt, targetUser.get());
            RequestContext.setCurrentUser(targetUser.get());
            
            filterChain.doFilter(request, response);
        } catch (JWTVerificationException err) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        }
    }
    
    private String getJwtFromCookies(HttpServletRequest request) {
        if (request.getCookies() != null) {
            return Arrays.stream(request.getCookies())
                .filter(cookie -> "token".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);
        }
        return null;
    }
    
    private void setAuthentication(HttpServletRequest request, DecodedJWT decodedJwt, User targetUser) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("alg", decodedJwt.getAlgorithm());

        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", decodedJwt.getSubject());

        if (decodedJwt.getIssuedAt() != null) {
            claims.put("iat", decodedJwt.getIssuedAt().toInstant());
        }
        if (decodedJwt.getExpiresAt() != null) {
            claims.put("exp", decodedJwt.getExpiresAt().toInstant());
        }
        Jwt jwt = Jwt.withTokenValue(decodedJwt.getToken())
            .headers(h -> h.putAll(headers))
            .claims(c -> c.putAll(claims))
            .build();

        JwtAuthenticationToken authentication = new JwtAuthenticationToken(jwt, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
