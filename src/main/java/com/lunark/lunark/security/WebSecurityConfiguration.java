package com.lunark.lunark.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration {

    private final TokenFilter jwtRequestFilter;
    private final TokenEntryPoint tokenEntryPoint;
    private final MvcRequestMatcher.Builder mvcMatcherBuilder;

    @Autowired
    @Lazy
    public WebSecurityConfiguration(TokenFilter jwtRequestFilter, TokenEntryPoint tokenEntryPoint, HandlerMappingIntrospector introspector) {
        this.jwtRequestFilter = jwtRequestFilter;
        this.tokenEntryPoint = tokenEntryPoint;
        mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);
    }

    @Bean
    public WebMvcConfigurer CORSConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200")
                        .allowedHeaders("*")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS")
                        .allowCredentials(false);
            }
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.securityContext((securityContext) -> securityContext
                .securityContextRepository(new RequestAttributeSecurityContextRepository())
        );

        http.csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(tokenEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers(mvcMatcherBuilder.pattern("/api/auth/**")).permitAll()
                            .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()
                            .requestMatchers(mvcMatcherBuilder.pattern("/api/test/**")).permitAll()
                            .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/properties")).permitAll()
                            .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/properties/{id:\\d+}")).permitAll()
                            .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/amenities")).permitAll()
                            .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/amenities/*")).permitAll()
                            .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/properties/{id:\\d+}/images")).permitAll()
                            .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/properties/{propertyId:\\d+}/images/{imageId:\\d+}")).permitAll()
                            //TODO: Remove
                            .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/properties/{id:\\d+}/images")).permitAll()
                .anyRequest().authenticated()
                );
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        MvcRequestMatcher loginMatcher = mvcMatcherBuilder.pattern("/api/auth/login");
        loginMatcher.setMethod(HttpMethod.POST);
        MvcRequestMatcher signupMatcher = mvcMatcherBuilder.pattern("/api/auth/signup");
        loginMatcher.setMethod(HttpMethod.POST);
    	return (web) -> web.ignoring().requestMatchers(loginMatcher).requestMatchers(signupMatcher)
                .requestMatchers(new AntPathRequestMatcher("/"))
                .requestMatchers(new AntPathRequestMatcher("/webjars/**"))
                .requestMatchers(new AntPathRequestMatcher("/*.html"))
                .requestMatchers(new AntPathRequestMatcher("favicon.ico"))
                .requestMatchers(new AntPathRequestMatcher("/**/*.html"))
                .requestMatchers(new AntPathRequestMatcher("/**/*.css"))
                .requestMatchers(new AntPathRequestMatcher("/**/*.js"));

    }
}