package edu.usf.cs.hogwart_artifact_online.Security;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import com.nimbusds.jose.jwk.RSAKey;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Configuration // Means there is customized object, come and take it, and use it, like @Bean
/*
 - Normally spring secure will lock down every thing, so we need to set up it to serve my purpose,
like which endpoint need security, which one public
 /users/*  mean one level of path : /users/1
/users/** mean 2+ level of path : /users/1/profile/settings
    - they are use for path variable

Ones the SecureConfig attend, the log-in turn off ->  implement it
 */



public class SecurityConfiguration {
    @Value("${api.endpoint.base-url}") // Look at application.properties
    private String baseUrl;

    private final CustomBearerTokenAccessDeniedHandler customBearerTokenAccessDeniedHandler;

    private final CustomBasicAuthenticationEntryPoint customBasicAuthenticationEntryPoint;

    private final CustomBearerTokenAuthenticationEntryPoint customBearerTokenAuthenticationEntryPoint;

    private final RSAPublicKey publicKey;

    private final RSAPrivateKey privateKey;

    public SecurityConfiguration(CustomBearerTokenAccessDeniedHandler customBearerTokenAccessDeniedHandler, CustomBasicAuthenticationEntryPoint customBasicAuthenticationEntryPoint,
                                 CustomBearerTokenAuthenticationEntryPoint customBearerTokenAuthenticationEntryPoint) throws NoSuchAlgorithmException {
        this.customBearerTokenAccessDeniedHandler = customBearerTokenAccessDeniedHandler;
        this.customBasicAuthenticationEntryPoint = customBasicAuthenticationEntryPoint;
        this.customBearerTokenAuthenticationEntryPoint = customBearerTokenAuthenticationEntryPoint;
        // Generate a public/private key pair for JWT signing and verification
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        this.publicKey = (RSAPublicKey) keyPair.getPublic();
        this.privateKey = (RSAPrivateKey) keyPair.getPrivate();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers(HttpMethod.GET, baseUrl + "/artifacts/**").permitAll()
                        .requestMatchers(HttpMethod.POST, baseUrl + "/users/login").permitAll()
                        .requestMatchers(HttpMethod.GET, baseUrl + "/users/**").hasAuthority("Role_admin")
                        .requestMatchers(HttpMethod.POST, baseUrl + "/users/").hasAuthority("Role_admin")
                        .requestMatchers(HttpMethod.PUT, baseUrl + "/users/**").hasAuthority("Role_admin")
                        .requestMatchers(HttpMethod.DELETE, baseUrl + "/users/**").hasAuthority("Role_admin")
                        .requestMatchers("/h2-console/**").permitAll()
                        .anyRequest().authenticated()
                )
                .headers(headers -> headers.frameOptions(Customizer.withDefaults()).disable())
                .csrf(csrf -> csrf.disable())
                .httpBasic(httpBasic -> httpBasic.authenticationEntryPoint(this.customBasicAuthenticationEntryPoint)) // Enable HTTP Basic authentication - PART 1
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
                        .authenticationEntryPoint(this.customBearerTokenAuthenticationEntryPoint))
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedHandler(this.customBearerTokenAccessDeniedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Turn off session management, because we are using JWT tokens
                .build();
    }

    @Bean //Only one of this, if 2 PE, the inject will be error as NoUniqueBeanDefinitionException
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
        // Spring let us choose option for encoding, as many choices given.
    }

    @Bean
    public JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(this.publicKey).privateKey(this.privateKey).build();
        JWKSource<SecurityContext> jwkSet = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwkSet);
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(this.publicKey).build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("authorities");// Teach spring security to look for "authorities" claim in JWT for roles, not scope
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");// NO prefix needed, because our roles in default have "Role_" prefix in the JWT

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }
    /*
    Giả sử reques gửi đến server kèm jwt, JwtAuthenticationConverter dùng để đọc jwt, với chỉ dẫn đọc authority
     */
}

/*
 * JWT Configuration Explanation:
 * 
 * 1. JwtEncoder: Creates/signs JWT tokens using RSA private key
 * 2. JwtDecoder: Validates/decodes JWT tokens using RSA public key
 * 3. JwtAuthenticationConverter: Extracts authorities from JWT claims
 *    - setAuthoritiesClaimName("authorities"): Looks for "authorities" claim
 *    - setAuthorityPrefix(""): No prefix needed (already has "Role_")
 * 
 * 4. SecurityFilterChain with OAuth2:
 *    - .oauth2ResourceServer(...): Enables JWT token validation
 *    - .sessionManagement(STATELESS): No session storage (stateless)
 *    - POST /login: Public (no token needed yet)
 *    - Other endpoints: Require JWT token with Bearer prefix
 *
 Quy trình
 * 1 . Gọi login từ  conttroler -> từ controler gọi service(AUTHENTICATION) -
 * TRONG SERVICE: EXTRACT USER TỪ (MYUSERPRINCIPAL)AUTHEN + TẠO TOKEN TỪ JWTPROVIDER
 * TRONG JWTPROVIDER:  TẠO CÁC CLIAM CHO PAYLOAD ĐỂ ENCODE RA TOKEN
 *                      LƯU Ý :EXTRACT AUTHORITY TỪ AUTHEN _> SERILIZE RA STRING ĐỂ ĐINH DẠNG
 *                      Cái claim authorety phải có chỉ dẫn riêng vì nó ko phải default,
 */
