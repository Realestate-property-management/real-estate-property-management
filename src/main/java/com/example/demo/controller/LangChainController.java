package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.LangChainRequest;
import com.example.demo.dto.LangChainResponse;
import com.example.demo.integration.LangChainClient;

@RestController
@RequestMapping("/api/langchain")
@CrossOrigin(origins = "*")
public class LangChainController {

    @Autowired
    private LangChainClient langChainService;

    @PostMapping("/extract")
    public LangChainResponse extractLeaseText(
            @RequestBody LangChainRequest request) {

        return langChainService.extractText(request);
    }
}