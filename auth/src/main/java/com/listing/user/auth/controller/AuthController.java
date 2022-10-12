package com.listing.user.auth.controller;

import com.listing.user.auth.model.auth.AuthUser;
import com.listing.user.auth.model.response.ApiResponse;
import com.listing.user.auth.service.UserDetailsService;
import com.listing.user.auth.util.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/auth")
@Slf4j
@AllArgsConstructor
public class AuthController {

    AuthenticationManager authenticationManager;

    UserDetailsService userDetailsService;

    JwtUtil jwtUtil;

    @PostMapping(value = "/authenticate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> authenticateUser(@RequestBody AuthUser userRequest, HttpServletRequest request) {
        ApiResponse apiResponse = new ApiResponse();

        final UserDetails userDetails = userDetailsService.loadUserByUsername(userRequest.getEmail());
        if (userDetails != null) {
            try {
                Authentication authentication = authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(userRequest.getEmail(), userRequest.getPassword()));
                if (authentication.isAuthenticated()) {
                    final String jwt = jwtUtil.generateToken(userDetails);
                    apiResponse.setResponseCode("00");
                    apiResponse.setResponseMessage("Authentication Successful");
                    apiResponse.setData(jwt);
                    return new ResponseEntity<>(apiResponse, HttpStatus.OK);
                } else {
                    apiResponse.setResponseCode("01");
                    apiResponse.setResponseMessage("Authentication Failed. Bad Credentials");
                    return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
                }

            }catch (BadCredentialsException e) {
                apiResponse.setResponseCode("01");
                apiResponse.setResponseMessage("Authentication Failed. Bad Credentials");
                return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
            }
        } else {
            apiResponse.setResponseCode("01");
            apiResponse.setResponseMessage("Authentication Failed. Username not found");
            return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
        }

    }
}
