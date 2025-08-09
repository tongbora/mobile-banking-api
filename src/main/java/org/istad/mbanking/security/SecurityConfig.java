package org.istad.mbanking.security;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.KeySourceException;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSelector;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.util.List;
import java.util.UUID;

@Configuration
// EnableWebSecurity use to customize security from spring
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;



    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }





    // Customize security
    // This is 1 step.
    /** To customize it, we need to create a firewall [SecurityFilterChain]
     * and we need to inject bean call HttpSecurity and also return httpSecurity.build() [to build everything]
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        /**
         * this will config all end point for authorization process
         * Example : Admin can access end point delete user
         * .authenticated mean all anyRequest will have security
         * if .permitAll will not have security
         */
        httpSecurity
                .authorizeHttpRequests(request ->
                        request
                                // this method get end point
                                .requestMatchers("/api/v1/auth/**")
                                .permitAll()
//                                .requestMatchers(HttpMethod.POST,"/api/v1/users/**")
//                                .permitAll()
//                                .requestMatchers(HttpMethod.GET, "/api/v1/users")
//                                .hasRole("ADMIN")
//                                .requestMatchers(HttpMethod.DELETE, "/api/v1/users/**")
//                                .hasRole("ADMIN")
//                                .requestMatchers(HttpMethod.PUT, "/api/v1/users/**")
//                                .hasRole("ADMIN")
//                                .requestMatchers("/api/v1/users/**")
//                                .permitAll()
                                .requestMatchers("api/v1/media/**")
                                .permitAll()
                                .requestMatchers("/api/v1/mail")
                                .hasAnyRole("ADMIN")
                                .anyRequest()
                                .authenticated());


        // because we use @EnableWebSecurity , so default form login in spring boot cannot know
        // just config it to http basic form login with defaults
//        httpSecurity.httpBasic(Customizer.withDefaults());

        // change mechanism to use jwt mechanism
        httpSecurity.oauth2ResourceServer(jwt -> jwt
                .jwt(Customizer.withDefaults()));

        // Disable CSRF
        httpSecurity.csrf(token -> token.disable());

        // When we change csrf to disable we need to make this REST to STATELESS
        httpSecurity.sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return httpSecurity.build();
    }

//    @Bean
//    InMemoryUserDetailsManager inMemoryUserDetailsManager(){
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//
//        // when build using User from security and it return UserDetails
//        UserDetails admin = User.builder()
//                                .username("admin")
//                                // cannot use raw password, if use , it will error
//                                // need to encode {passwordEndcoder is a bean from SecurityBean}
//                                .password(passwordEncoder.encode("qwer"))
//                                .roles("USRE","ADMIN")
//                                .build();
//
//        UserDetails editor = User.builder()
//                .username("editor")
//                .password(passwordEncoder.encode("qwer"))
//                .roles("USER", "EDITOR")
//                .build();
//
//        // add user to InMemoryUserDetailsManager
//        manager.createUser(admin);
//        manager.createUser(editor);
//        return manager;
//    }


    @Bean
    KeyPair keyPair(){
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            return keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    RSAKey rsaKey(KeyPair keyPair){
        return new RSAKey.Builder((RSAPublicKey) keyPair.getPublic())
                .privateKey(keyPair.getPrivate())
                .keyID(UUID.randomUUID().toString())
                .build();
    }
    @Bean
    JWKSource<SecurityContext> jwkSource(RSAKey rsaKey){
        JWKSet jwkSet = new JWKSet(rsaKey);
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }

    @Bean
    JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource){
        return new NimbusJwtEncoder(jwkSource);
    }

    @Bean
    JwtDecoder jwtDecoder(RSAKey rsaKey) throws JOSEException {
        return NimbusJwtDecoder.withPublicKey(rsaKey.toRSAPublicKey()).build();
    }
}
