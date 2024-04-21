package com.listing.user.service.controller;

import com.listing.user.service.mapper.user.UserMapper;
import com.listing.user.service.model.dto.ChangePasswordResponseDto;
import com.listing.user.service.model.dto.UsersEntityDto;
import com.listing.user.service.model.request.ChangePasswordRequest;
import com.listing.user.service.model.request.UsersRequest;
import com.listing.user.service.entity.UsersEntity;
import com.listing.user.service.service.DatabaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/users")
@Slf4j
public class UsersController {

    DatabaseService databaseService;

    public UsersController(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    UserMapper mapper = UserMapper.INSTANCE;

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, name = "Create User")
    public ResponseEntity<UsersEntityDto> createUser(@RequestBody UsersRequest usersRequest, HttpServletRequest request) {
        log.info("/create user -- {}", usersRequest);
        UsersEntityDto usersEntityDto;
        try {
            UsersEntity usersEntity = databaseService.createUser(usersRequest);
            usersEntityDto = mapper.convertValue(usersEntity);
            log.info("user created -- {}", usersEntityDto);
            return ResponseEntity.ok(usersEntityDto);
        } catch (Exception exception) {
            log.error("failed to create user -- {}", exception.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UsersEntityDto>> listUsers() {
        log.info("/list users");
        List<UsersEntity> usersEntityList = databaseService.findAll();
        if (!usersEntityList.isEmpty()) {
            List<UsersEntityDto> usersEntityDtoList = usersEntityList
                    .stream()
                    .map(usersEntity -> mapper.convertValue(usersEntity))
                    .toList();
            return ResponseEntity.ok(usersEntityDtoList);
        } else return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UsersEntityDto> updateUser(@PathVariable Integer id, @RequestBody UsersRequest usersRequest) {
        log.info("/update user -- id[{}] {}",id, usersRequest);
        return ResponseEntity.ok(mapper.convertValue(databaseService.updateUser(usersRequest, id)));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UsersEntityDto> findUserById(@PathVariable int id) {
        log.info("find user by id -- {}", id);
        Optional<UsersEntity> optionalUsersEntity = databaseService.findById(id);
        return optionalUsersEntity
                .map(usersEntity -> ResponseEntity.ok(mapper.convertValue(usersEntity)))
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @GetMapping(value = "/find/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UsersEntityDto> findUserByEmail(@PathVariable String email) {
        log.info("find user by email -- {}", email);
        Optional<UsersEntity> optionalUsersEntity = databaseService.findUserByEmail(email);
        return optionalUsersEntity
                .map(usersEntity -> ResponseEntity.ok(mapper.convertValue(usersEntity)))
                .orElseGet(() -> ResponseEntity.ok().build());
    }

    @PutMapping(value = "/change-password/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ChangePasswordResponseDto> changePassword(@PathVariable Integer id, @RequestBody ChangePasswordRequest changePasswordRequest) {
        log.info("change password request -- id[{}]", id);
        return ResponseEntity.ok(databaseService.updatePassword(changePasswordRequest, id));
    }
}
