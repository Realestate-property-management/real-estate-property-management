package com.example.demo.integration;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.demo.dto.LangChainRequest;
import com.example.demo.dto.LangChainResponse;

@Component
public class LangChainClient {

    // FastAPI GenAI Service URL
    private static final String GENAI_URL =
            "http://localhost:8000/process-lease";

    private final RestTemplate restTemplate = new RestTemplate();

    public LangChainResponse extractText(LangChainRequest request) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<LangChainRequest> entity =
                new HttpEntity<>(request, headers);

        ResponseEntity<LangChainResponse> response =
                restTemplate.exchange(
                        GENAI_URL,
                        HttpMethod.POST,
                        entity,
                        LangChainResponse.class
                );

        return response.getBody();
    }
}