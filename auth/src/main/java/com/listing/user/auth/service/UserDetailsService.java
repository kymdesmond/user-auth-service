package com.listing.user.auth.service;

import com.listing.userservice.AuthenticateServiceGrpc;
import com.listing.userservice.UserDetailsRequest;
import com.listing.userservice.UserDetailsResponse;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @GrpcClient("authenticate")
    private AuthenticateServiceGrpc.AuthenticateServiceBlockingStub authenticateServiceBlockingStub;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetailsRequest userDetailsRequest = UserDetailsRequest.newBuilder().setEmail(username).build();
        UserDetailsResponse userDetailsResponse = authenticateServiceBlockingStub.authenticate(userDetailsRequest);
        if (userDetailsResponse.hasData()) {
            return new User(userDetailsResponse.getData().getEmail(), userDetailsResponse.getData().getPassword(), new ArrayList<>());
        }
        return null;
    }
}
