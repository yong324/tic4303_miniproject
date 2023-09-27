package tic4303.miniproject.server.configuration;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{
    
    @Autowired
    private final JwtService jwtService;

    @Autowired
    private final UserDetailsService userDetailsService;

    // filters incoming http request
    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request, 
        @NonNull HttpServletResponse response, 
        @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        // create variable to store the authentication header 
        final String authHeader = request.getHeader("Authorization");
        // create variable to store the extracted JWT from the authentication header
        final String jwt;
        // create variable to store the extracted email
        final String email;

        // implement the check on the token
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return; // terminate
        }

        // extract the token from the authentication header
        // get everything after "Bearer ", as the authentication header starts with "Bearer <<JWT>>"
        jwt = authHeader.substring(7); 

        // extract the email from the jwt using a service class
        email = jwtService.extractUsername(jwt);

        // check if the email is not null and user is not authenticated
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // user is not connected yet
            // check the user against the database
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);
            // check if token is valid
            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }

}
