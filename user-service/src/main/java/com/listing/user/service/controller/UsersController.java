package com.listing.user.service.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.listing.user.service.model.dto.UsersEntityDto;
import com.listing.user.service.model.request.UsersRequest;
import com.listing.user.service.entity.UsersEntity;
import com.listing.user.service.service.DatabaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
@Slf4j
public class UsersController {

    DatabaseService databaseService;

    public UsersController(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    ObjectMapper mapper = new ObjectMapper();

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, name = "Create User")
    public ResponseEntity<UsersEntityDto> createUser(@RequestBody UsersRequest usersRequest, HttpServletRequest request) {
        log.info("/create user -- {}", usersRequest);
        UsersEntityDto usersEntityDto;
        try {
            UsersEntity usersEntity = databaseService.createUser(usersRequest);
            usersEntityDto = mapper.convertValue(usersEntity, new TypeReference<>() {});
            log.info("user created -- {}", usersEntityDto);
            return ResponseEntity.ok(usersEntityDto);
        } catch (Exception exception) {
            exception.printStackTrace();
            log.error("failed to create user -- {}", exception.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UsersEntityDto>> listUsers() {
        log.info("/list users");
        List<UsersEntity> usersEntityList = databaseService.findAll();
        if (!usersEntityList.isEmpty()) {
            List<UsersEntityDto> usersEntityDtoList = mapper.convertValue(usersEntityList, new TypeReference<>() {});
            return ResponseEntity.ok(usersEntityDtoList);
        } else return ResponseEntity.noContent().build();
    }

    /**
     * todo
     * - get all users
     * - update user
     * - delete user
     * - find user by email
     * - find usr by id
     */
}
