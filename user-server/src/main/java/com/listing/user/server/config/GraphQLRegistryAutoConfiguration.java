package com.listing.user.server.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
@ConfigurationProperties(prefix = "graphql.registry")
@ConditionalOnProperty("graphql.registry.uri")
@Slf4j
public class GraphQLRegistryAutoConfiguration implements ApplicationListener<ApplicationReadyEvent> {
    private String uri;
    private final ApplicationContext ctx;
    private final Environment env;

    public GraphQLRegistryAutoConfiguration(ApplicationContext ctx, Environment env) {
        this.ctx = ctx;
        this.env = env;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        final String appName = env.getProperty("spring.application.name");
        try {
            register(appName, uri);
        } catch (JsonProcessingException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Register service on the gateway
     *
     * @param appName
     * @param uri
     */
    private void register(String appName, String uri) throws JsonProcessingException {
        final RestTemplate restTemplate = new RestTemplate();
        ServiceDto serviceDto = new ServiceDto();
        serviceDto.setName(appName);
        String url = "http://" + getServiceHost() + ":" + env.getProperty("local.server.port") + env.getProperty("server.servlet.context-path") + "/v3/api-docs";
        log.info(url);
        serviceDto.setUrl(url);
        ObjectMapper objectMapper = new ObjectMapper();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(objectMapper.writeValueAsString(serviceDto), headers);

        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, String.class);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                log.info("Service successfully registered -- {}", appName);
            } else {
                log.error("Service was not successfully registered -- {}", appName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getServiceHost() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            log.error("Errors getting the host IP", e);
            return null;
        }
    }
}