package job_portal.security;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;


@Configuration
@EnableMethodSecurity
@Slf4j
@RequiredArgsConstructor
public class SecurityConfig {

    private final SeekerDetailServiceImpl seekerDetailServiceImpl;
    private final CompanyDetailServiceImpl companyDetailServiceImpl;

    private final PasswordEncoder passwordEncoder;

    // ********** Handle Cross-Origin **********
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // --- REACT FRONTEND URLS ---
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("http://localhost:3000");  
        config.addAllowedOriginPattern("http://192.168.18.*");    
        config.addAllowedOriginPattern("*");                      

        // --- ALLOW HEADERS ---
        config.addAllowedHeader("*");

        // --- ALLOW METHODS ---
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");
        config.addAllowedMethod("OPTIONS");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }

    public DaoAuthenticationProvider seekerAuthProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(seekerDetailServiceImpl);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public DaoAuthenticationProvider companyAuthProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(companyDetailServiceImpl);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder =
        http.getSharedObject(AuthenticationManagerBuilder.class);

        builder.authenticationProvider(seekerAuthProvider());
        builder.authenticationProvider(companyAuthProvider());
        return builder.build();
    }

    /**
     * Configure JwtAuthenticationConverter so that the 'scope' claim from the JWT
     * is converted into GrantedAuthority values without the default "SCOPE_" prefix.
     * This allows using @PreAuthorize("hasAuthority('JOB_VIEW')") when the JWT
     * contains scope claim like: "JOB_VIEW ROLE_SEEKER JOB_APPLY".
     */
    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        // Use the claim named "scope" (default supports "scope" and "scp") and remove prefix
        grantedAuthoritiesConverter.setAuthorityPrefix("");
        grantedAuthoritiesConverter.setAuthoritiesClaimName("scope");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

    @Bean
    @Order(1)
    public SecurityFilterChain seekerChain(HttpSecurity http, JwtDecoder jwtDecoder) throws Exception {

        http.securityMatcher("/api/v1/seeker/**");

        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));
        http.csrf(csrf -> csrf.disable());

        http.sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        // Use SEEKER user detail service
        http.userDetailsService(seekerDetailServiceImpl);

        http.oauth2ResourceServer(oauth2 ->
                oauth2.jwt(jwt -> jwt.decoder(jwtDecoder).jwtAuthenticationConverter(jwtAuthenticationConverter()))
        );

        return http.build();
    }

    // ---- COMPANY FILTER CHAIN ----
    @Bean
    @Order(2)
    public SecurityFilterChain companyChain(HttpSecurity http, JwtDecoder jwtDecoder) throws Exception {

        http.securityMatcher("/api/v1/company/**");  // IMPORTANT

        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));
        http.csrf(csrf -> csrf.disable());

        http.sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        // Use COMPANY user detail service
        http.userDetailsService(companyDetailServiceImpl);
        http.oauth2ResourceServer(oauth2 ->
                oauth2.jwt(jwt -> jwt.decoder(jwtDecoder).jwtAuthenticationConverter(jwtAuthenticationConverter()))
        );
        return http.build();
    }


    @Bean
    @Order(1)
    public SecurityFilterChain adminChain(HttpSecurity http, JwtDecoder jwtDecoder) throws Exception {

        http.securityMatcher("/api/v1/admin/**");

        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));
        http.csrf(csrf -> csrf.disable());

        http.sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        // Use SEEKER user detail service
        http.userDetailsService(seekerDetailServiceImpl);

        http.oauth2ResourceServer(oauth2 ->
                oauth2.jwt(jwt -> jwt.decoder(jwtDecoder).jwtAuthenticationConverter(jwtAuthenticationConverter()))
        );

        return http.build();
    }



}

