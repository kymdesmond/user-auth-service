package com.listing.user.service.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.listing.user.service.model.dto.EntityTypesEntityDto;
import com.listing.user.service.model.request.EntityTypeRequest;
import com.listing.user.service.service.DatabaseService;
import com.listing.user.service.entity.EntityTypesEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/entity-types")
@Slf4j
public class EntityTypesController {

    DatabaseService databaseService;

    public EntityTypesController(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }
    ObjectMapper mapper = new ObjectMapper();

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EntityTypesEntityDto>> listEntityTypes() {
        log.info("find all entity types");
        List<EntityTypesEntity> entityList = databaseService.listEntityTypes();
        List<EntityTypesEntityDto> entityTypesEntityDto = mapper.convertValue(entityList, new TypeReference<>() {});
        return ResponseEntity.status(HttpStatus.OK).body(entityTypesEntityDto);
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityTypesEntityDto> createEntityType(@RequestBody EntityTypeRequest entityTypeRequest) {
        HttpStatus httpStatus;
        EntityTypesEntityDto entityTypesEntityDto = null;
        try {
            EntityTypesEntity entityTypesEntity = new EntityTypesEntity();
            entityTypesEntity.setName(entityTypeRequest.name());
            entityTypesEntity.setDescription(entityTypeRequest.description());
            databaseService.createEntityType(entityTypesEntity);
            log.info("create entity type request -- {}", entityTypeRequest);
            entityTypesEntityDto = mapper.convertValue(entityTypesEntity, new TypeReference<>() {});
            httpStatus = HttpStatus.CREATED;
        } catch (Exception exception) {
            log.error("failed to created entity type -- {}", exception.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        log.info("create entity type response -- {}", entityTypesEntityDto);
        return new ResponseEntity<>(entityTypesEntityDto, httpStatus);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityTypesEntityDto> updateEntityType(@PathVariable Integer id,
                                                        @RequestBody EntityTypeRequest entityUpdateTypeRequest) {
        HttpStatus httpStatus;
        EntityTypesEntityDto entityTypesEntityDto = null;
        try {
            log.info("find entity type by id -- [{}]", id);
            Optional<EntityTypesEntity> optionalEntityTypesEntity = databaseService.findEntityType(id);

            if (optionalEntityTypesEntity.isPresent()) {
                EntityTypesEntity entityTypesEntity = optionalEntityTypesEntity.get();
                entityTypesEntity.setName(entityUpdateTypeRequest.name());
                entityTypesEntity.setDescription(entityUpdateTypeRequest.description());
                databaseService.updateEntityType(entityTypesEntity);
                log.info("update entity type request -- {}", entityUpdateTypeRequest);
                entityTypesEntityDto = mapper.convertValue(entityTypesEntity, new TypeReference<>() {});
                httpStatus = HttpStatus.CREATED;
            } else {
                log.error("entity type not found -- {}", id);
                httpStatus = HttpStatus.NO_CONTENT;
            }
        } catch (Exception exception) {
            log.error("failed to update entity type -- {}", exception.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        log.info("update entity type response -- {}", entityTypesEntityDto);
        return new ResponseEntity<>(entityTypesEntityDto, httpStatus);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityTypesEntityDto> getEntityType(@PathVariable Integer id) {
        HttpStatus httpStatus;
        log.info("find entity type by id -- [{}]", id);
        Optional<EntityTypesEntity> optionalEntityTypesEntity = databaseService.findEntityType(id);
        EntityTypesEntityDto entityTypesEntityDto = null;
        if (optionalEntityTypesEntity.isPresent()) {
            entityTypesEntityDto = mapper.convertValue(optionalEntityTypesEntity.get(), new TypeReference<>() {});
            httpStatus = HttpStatus.OK;
        } else {
            log.error("entity type not found -- {}", id);
            httpStatus = HttpStatus.NO_CONTENT;
        }

        log.info("find entity type by id response -- {}", entityTypesEntityDto);
        return new ResponseEntity<>(entityTypesEntityDto, httpStatus);
    }
}
