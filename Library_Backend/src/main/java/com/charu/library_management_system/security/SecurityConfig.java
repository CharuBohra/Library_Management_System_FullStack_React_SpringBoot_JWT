package com.charu.library_management_system.security;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
    {
        http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .headers(headers -> headers.frameOptions(frame-> frame.sameOrigin()))
                .exceptionHandling(exception -> exception
                        // Nobody logged in (no token, or no valid authentication) → 401
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.setContentType("application/json");
                            response.setCharacterEncoding("UTF-8");
                            response.getWriter().write("""
                         {"message": "Authentication required. Please log in.",
                          "status": false}
                            """);
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            response.setContentType("application/json");
                            response.setCharacterEncoding("UTF-8");

                            response.getWriter().write("""
                {
                    "message": "You do not have permission to access this resource",
                    "status": false
                }
                """);
                        })
                )
                .authorizeHttpRequests(auth -> auth.
                        requestMatchers(
                                "/auth/login/**",
                                "/auth/signup/**",
                                "/error",
                                "/auth/forgot-password/**",
                                "/auth/reset-password/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**"
                        ).permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/payments/payment_success/**").permitAll()

                        // ---------- Admin only ----------
                        // Books (search and reading stay open to all logged-in users)
                        .requestMatchers(HttpMethod.POST, "/api/books", "/api/books/create/bulk").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/books/*/damaged-copies/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/books/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/books/**").hasRole("ADMIN")

                        // Genres
                        .requestMatchers(HttpMethod.POST, "/api/genres/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/genres/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/genres/**").hasRole("ADMIN")

                        // Subscription plans (viewing plans stays open)
                        .requestMatchers(HttpMethod.POST, "/api/subscription-plans/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/subscription-plans/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/subscription-plans/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/subscriptions/deactivate-expired").hasRole("ADMIN")

                        // Fines (customers keep /my and /{id}/pay)
                        .requestMatchers(HttpMethod.POST, "/api/fines", "/api/fines/waive").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/fines").hasRole("ADMIN")

                        // Book loans (customers keep checkout, checkin, renew, /my)
                        .requestMatchers(HttpMethod.POST,
                                "/api/book-loans/checkout/user/**",
                                "/api/book-loans/search",
                                "/api/book-loans/update-overdue").hasRole("ADMIN")

                        // Reservations (customers keep create, cancel, /my)
                        .requestMatchers(HttpMethod.POST, "/api/reservations/create/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/reservations/*/fulfill").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/reservations").hasRole("ADMIN")

                        // Users, payments, subscriptions (admin lists)
                        .requestMatchers(HttpMethod.GET,
                                "/api/user/list",
                                "/api/payments",
                                "/api/subscriptions").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin((form->form.disable()))
                .addFilterBefore(jwtFilter , UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config){
        return config.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(@Value("${cors.allowed-origins}") List<String> allowedOrigins){
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.setAllowCredentials(true);

        corsConfiguration.setAllowedHeaders(List.of("*"));

        corsConfiguration.setAllowedOrigins(allowedOrigins);

        corsConfiguration.setAllowedMethods(List.of("GET","POST","PUT","PATCH","DELETE","OPTIONS"));

        corsConfiguration.setExposedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**",corsConfiguration);

        return source;
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
}
