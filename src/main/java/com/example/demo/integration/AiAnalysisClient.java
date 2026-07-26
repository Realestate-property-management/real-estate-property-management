package com.example.demo.integration;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AiAnalysisClient {

    @Value("${ai.service.url}")
    private String aiServiceUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public Map<String, Object> analyzeLease(String s3Uri) {

        Map<String, String> request = new HashMap<>();
        request.put("s3_uri", s3Uri);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> entity =
                new HttpEntity<>(request, headers);

        ResponseEntity<Map> response =
                restTemplate.exchange(
                        aiServiceUrl + "/process-lease",
                        HttpMethod.POST,
                        entity,
                        Map.class);

        return response.getBody();
    }
}