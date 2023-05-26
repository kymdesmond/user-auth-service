package com.listing.user.service.service;

import com.listing.userservice.AuthenticateServiceGrpc;
import com.listing.userservice.UserDetails;
import com.listing.userservice.UserDetailsRequest;
import com.listing.userservice.UserDetailsResponse;
import com.listing.user.service.entity.UsersEntity;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.Optional;

@GrpcService
@Slf4j
public class UserServiceImpl extends AuthenticateServiceGrpc.AuthenticateServiceImplBase {

    private final UsersEntityServiceImpl usersEntityService;

    public UserServiceImpl(UsersEntityServiceImpl usersEntityService) {
        this.usersEntityService = usersEntityService;
    }

    @Override
    public void authenticate(UserDetailsRequest request, StreamObserver<UserDetailsResponse> responseObserver) {
        log.info("verify user request -- {}", request.getEmail());

        Optional<UsersEntity> optionalUsersEntity = usersEntityService.findUserByEmail(request.getEmail());
        UserDetailsResponse userDetailsResponse;
        if (optionalUsersEntity.isPresent()) {
            UsersEntity usersEntity = optionalUsersEntity.get();
            UserDetails userDetails = UserDetails.newBuilder()
                    .setId((int) usersEntity.getId())
                    .setEmail(usersEntity.getEmail())
                    .setFirstName(usersEntity.getFirstName())
                    .setLastName(usersEntity.getLastName())
                    .setPhone(usersEntity.getPhone())
                    .setPassword(usersEntity.getPassword())
                    .build();
            userDetailsResponse = UserDetailsResponse.newBuilder()
                    .setResponseCode("00")
                    .setResponseMessage("User details found")
                    .setData(userDetails).build();
        } else {
            userDetailsResponse = UserDetailsResponse.newBuilder()
                    .setResponseCode("01")
                    .setResponseMessage("User Not found").build();
        }
        responseObserver.onNext(userDetailsResponse);
        responseObserver.onCompleted();
    }
}
