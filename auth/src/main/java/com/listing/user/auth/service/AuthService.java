package com.listing.user.auth.service;

import com.listing.user.auth.util.JwtUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private static final String BEARER_PREFIX = "Bearer ";

    private final RedisTemplate<String, String> redisTemplate;

    private final JwtUtil jwtUtil;

    private final  UserDetailsService userDetailsService;

    public Optional<Authentication> authenticate(HttpServletRequest request) {
        return extractBearerTokenHeader(request).flatMap(this::lookup);
    }

    private static Optional<String> extractBearerTokenHeader(@NonNull HttpServletRequest request) {
        try {
            String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (nonNull(authorization) && (authorization.startsWith(BEARER_PREFIX))) {
                    String token = authorization.substring(BEARER_PREFIX.length()).trim();
                    if (!token.isBlank()) {
                        return Optional.of(token);
                    }

            }
            return Optional.empty();
        } catch (Exception e) {
            log.error("An unknown error occurred while trying to extract bearer token", e);
            return Optional.empty();
        }
    }

    public Optional<Authentication> lookup(String token) {
        try {
            String userId = redisTemplate.boundSetOps(token).randomMember();
            if (nonNull(userId) && Boolean.TRUE.equals(jwtUtil.validateToken(token, userDetailsService.loadUserByUsername(userId)))) {
                    return Optional.of(createAuthentication(userId, Role.ADMIN));
            }
            return Optional.empty();
        } catch (Exception exception) {
            return Optional.empty();
        }
    }

    private static Authentication createAuthentication(String actor, @NonNull Role... roles) {
        // The difference between `hasAuthority` and `hasRole` is that the latter uses the `ROLE_` prefix
        List<GrantedAuthority> authorities = Stream.of(roles)
                .distinct()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .collect(toList());
        return new UsernamePasswordAuthenticationToken(nonNull(actor) ? actor : "N/A", "N/A", authorities);
    }

    private enum Role {
        USER,
        ADMIN,
        SYSTEM,
    }
}
