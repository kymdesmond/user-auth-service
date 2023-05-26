package com.listing.user.service.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.listing.user.service.model.dto.EntitiesEntityDto;
import com.listing.user.service.model.request.EntityRequest;
import com.listing.user.service.entity.EntitiesEntity;
import com.listing.user.service.service.DatabaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/entities")
@Slf4j
public class EntitiesController {

    DatabaseService databaseService;

    public EntitiesController(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    ObjectMapper mapper = new ObjectMapper();

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EntitiesEntityDto>> listEntities() {
        log.info("find all entities");
        List<EntitiesEntity> entityList = databaseService.listEntities();
        List<EntitiesEntityDto> entityDtoList = mapper.convertValue(entityList, new TypeReference<>() {});
        return ResponseEntity.status(HttpStatus.OK).body(entityDtoList);
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntitiesEntityDto> createEntity(@RequestBody EntityRequest entityRequest) {
        log.info("create entity request -- {}", entityRequest);
        EntitiesEntity entitiesEntity = new EntitiesEntity();
        HttpStatus httpStatus;
        EntitiesEntityDto entitiesEntityDto = null;
        try {
            entitiesEntity.setName(entityRequest.name());
            entitiesEntity.setEntityTypeId(entityRequest.entityTypeId());
            entitiesEntity.setMsisdn(entityRequest.msisdn());
            entitiesEntity.setEmail(entityRequest.email());
            entitiesEntity.setWebsite(entityRequest.website());
            entitiesEntity.setAddress(entityRequest.address());
            entitiesEntity.setDateCreated(new Timestamp(new Date().getTime()));
            entitiesEntity.setStatus(1);
            entitiesEntity.setCreatedBy(entityRequest.createdBy());

            databaseService.createEntity(entitiesEntity);
            log.info("entity created successfully");
            entitiesEntityDto = mapper.convertValue(entitiesEntity, new TypeReference<>() {});
            httpStatus = HttpStatus.CREATED;
        } catch (Exception e) {
            log.error("error occurred while creating entity -- {}", e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        log.info("create entity response -- {}", entitiesEntityDto);
        return  ResponseEntity.status(httpStatus).body(entitiesEntityDto);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntitiesEntityDto> getEntity(@PathVariable Integer id) {
        log.info("find entity id -- [{}]", id);
        HttpStatus httpStatus;
        Optional<EntitiesEntity> optionalEntitiesEntity = databaseService.findEntity(id);
        EntitiesEntityDto entitiesEntityDto = null;
        if (optionalEntitiesEntity.isPresent()) {
            entitiesEntityDto = mapper.convertValue(optionalEntitiesEntity.get(), new TypeReference<>() {});
            httpStatus = HttpStatus.OK;
        } else {
            httpStatus = HttpStatus.NO_CONTENT;
        }
        log.info("find entity response -- {}", entitiesEntityDto);
        return ResponseEntity.status(httpStatus).body(entitiesEntityDto);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntitiesEntityDto> updateEntity(@PathVariable Integer id, @RequestBody EntityRequest entityRequest) {
        log.info("update entity request -- id [{}] -- {}", id, entityRequest);
        HttpStatus httpStatus;
        EntitiesEntityDto entitiesEntityDto = null;
        try {
            Optional<EntitiesEntity> optionalEntitiesEntity = databaseService.findEntity(id);
            if (optionalEntitiesEntity.isPresent()) {
                EntitiesEntity entitiesEntity = optionalEntitiesEntity.get();
                entitiesEntity.setName(entityRequest.name());
                entitiesEntity.setEntityTypeId(entityRequest.entityTypeId());
                entitiesEntity.setMsisdn(entityRequest.msisdn());
                entitiesEntity.setAddress(entityRequest.address());
                entitiesEntity.setEmail(entityRequest.email());
                entitiesEntity.setWebsite(entityRequest.website());
                entitiesEntity.setCreatedBy(entityRequest.createdBy());

                databaseService.updateEntity(entitiesEntity);
                log.info("entity updated successfully");
                entitiesEntityDto = mapper.convertValue(optionalEntitiesEntity.get(), new TypeReference<>() {});
                httpStatus = HttpStatus.OK;
            } else {
                httpStatus = HttpStatus.NO_CONTENT;
            }

        } catch (Exception e) {
            log.error("error occurred while creating entity -- {}", e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        log.info("create entity response -- {}", entitiesEntityDto);
        return  ResponseEntity.status(httpStatus).body(entitiesEntityDto);
    }
}
