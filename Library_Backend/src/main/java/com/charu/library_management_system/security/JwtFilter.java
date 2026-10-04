package com.charu.library_management_system.security;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

            String authHeader = request.getHeader("Authorization");

            if(authHeader==null || !authHeader.startsWith("Bearer "))
            {
                filterChain.doFilter(request,response);
                return;
            }

            try{
                String jwt = authHeader.substring(7);

                String email = jwtService.extractEmail(jwt);

                UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

                if(jwtService.isTokenValid(jwt,userDetails)
                    && SecurityContextHolder.getContext().getAuthentication()==null)
                {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );
                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(authenticationToken);
                }
            }catch (JwtException | IllegalArgumentException | UsernameNotFoundException e){
                log.warn("JWT rejected: {}", e.getMessage());

                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.setContentType("application/json");

                response.getWriter().write("""
                    {
                      "message": "Invalid or expired JWT",
                      "status": false
                    }
                    """);

                return;
            }

            filterChain.doFilter(request,response);
    }
}
