package com.example.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.integration.AiAnalysisClient;

@RestController
@RequestMapping("/api/ai/compliance")
@CrossOrigin(origins = "*")
public class ComplianceAIController {

    @Autowired
    private AiAnalysisClient aiAnalysisClient;

    @PostMapping("/analyze")
    public Map<String, Object> analyze(
            @RequestBody Map<String, String> request) {

        String s3Uri = request.get("s3_uri");

        return aiAnalysisClient.analyzeLease(s3Uri);
    }
}