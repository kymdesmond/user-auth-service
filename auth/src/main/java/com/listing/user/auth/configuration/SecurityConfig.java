package com.listing.user.auth.configuration;

import com.listing.user.auth.service.AuthService;
import com.listing.user.auth.service.UserDetailsService;
import com.listing.user.auth.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter.Directive.*;
import static org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter.Directive.EXECUTION_CONTEXTS;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    private final JwtUtil jwtUtil;

    private final AuthService authService;

    private final RedisTemplate<String, String> redisTemplate;

    private static final ClearSiteDataHeaderWriter.Directive[] SOURCE = {CACHE, COOKIES, STORAGE, EXECUTION_CONTEXTS};

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .authorizeRequests()
                .antMatchers("/auth/authenticate").permitAll()
                .antMatchers("/users/create").permitAll()
                .antMatchers("/users/reset-password").permitAll()
                .antMatchers(
                        HttpMethod.GET,
                        "/",
                        "/swagger-ui",
                        "/v3/api-docs/swagger-config",           // swagger
                        "/v3/api-docs",           // swagger
                        "/webjars/**",            // swagger-ui webjars
                        "/swagger-resources/**",  // swagger-ui resources
                        "/configuration/**",      // swagger configuration
                        "/*.html",
                        "/favicon.ico",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js"
                ).permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling()
                .and()
                .cors()
                .and()
                .sessionManagement(conf -> conf.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        httpSecurity.logout(logout -> logout
                        .logoutUrl("/auth/logout")
                        .addLogoutHandler((request, response, auth) -> {
                            for (Cookie cookie : request.getCookies()) {
                                String cookieName = cookie.getName();
                                Cookie cookieToDelete = new Cookie(cookieName, null);
                                cookieToDelete.setMaxAge(0);
                                response.addCookie(cookieToDelete);
                            }
                            HttpSession session = request.getSession();
                            String token = (String) session.getAttribute("token");
                            jwtUtil.invalidateToken(token);
                            session.invalidate();
                        })
//                      .addLogoutHandler(new HeaderWriterLogoutHandler(new ClearSiteDataHeaderWriter(SOURCE)))
        );
        httpSecurity.addFilterBefore(this::doFilterInternal, UsernamePasswordAuthenticationFilter.class);

    }

    private void doFilterInternal(
            ServletRequest request,
            ServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        authService.authenticate((HttpServletRequest) request).ifPresent(SecurityContextHolder.getContext()::setAuthentication);
        filterChain.doFilter(request, response);
    }
}
