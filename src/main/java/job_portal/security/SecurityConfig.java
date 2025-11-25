package job_portal.security;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.Collection; 
import java.util.stream.Collectors;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.core.convert.converter.Converter;


@Configuration
@EnableMethodSecurity
@Slf4j
@RequiredArgsConstructor
public class SecurityConfig {

    private final SeekerDetailServiceImpl seekerDetailServiceImpl;

    private final PasswordEncoder passwordEncoder;



    // --------------------- For Handle Cross-Origin ---------------------------
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // --- REACT FRONTEND URLS ---
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("http://localhost:3000");   // React local
        config.addAllowedOriginPattern("http://192.168.18.*");     // LAN devices
        config.addAllowedOriginPattern("*");                       // If needed for testing

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

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {

        Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter = jwt -> {
            String id = jwt.getId();
            log.info("ID: {}", id);
            CustomUserDetails userDetails = (CustomUserDetails) seekerDetailServiceImpl.loadUserByUsername(id);
            log.info("AUTHORITIES: {}", userDetails.getAuthorities());
            return userDetails.getAuthorities()
                    .stream()
                    .map(grantedAuthority -> new SimpleGrantedAuthority(grantedAuthority.getAuthority()))
                    .collect(Collectors.toList());
        };
        var jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

        return jwtAuthenticationConverter;
    }

    public DaoAuthenticationProvider seekerAuthProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(seekerDetailServiceImpl);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    // @Bean
    // public DaoAuthenticationProvider companyAuthProvider() {
    //     DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    //     provider.setUserDetailsService(companyDetailServiceImpl);
    //     provider.setPasswordEncoder(passwordEncoder);
    //     return provider;
    // }

    // @Bean
    // public DaoAuthenticationProvider adminAuthProvider() {
    //     DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    //     provider.setUserDetailsService(adminDetailServiceImpl);
    //     provider.setPasswordEncoder(passwordEncoder);
    //     return provider;
    // }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder =
        http.getSharedObject(AuthenticationManagerBuilder.class);

        builder.authenticationProvider(seekerAuthProvider());
        // builder.authenticationProvider(companyAuthProvider());
        // builder.authenticationProvider(adminAuthProvider());

        return builder.build();
    }

    @Bean
    public SecurityFilterChain seekerChain(HttpSecurity http,JwtDecoder jwtDecoder) throws Exception {

        // http.securityMatcher("/api/v1/seeker/**");

        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));  // <--- IMPORTANT

        http.oauth2ResourceServer(oauth2 ->
            oauth2.jwt(jwt -> jwt.decoder(jwtDecoder))
        );

        http.csrf(csrf -> csrf.disable());
        http.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // http.authorizeHttpRequests(auth -> auth
        //         .requestMatchers("/api/seeker/auth/**").permitAll()
        //         .anyRequest().authenticated()
        // );

        // custom authority for seeker
        // http.oauth2ResourceServer(oauth2 ->
        //     oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(new SeekerJwtConverter(seekerDetailsService)))
        // );
        //  http.oauth2ResourceServer(oauth2 -> oauth2
        //         .jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder))
        // );
        // Make API stateless
        http.sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();

    }


    
}