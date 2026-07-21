package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.models.AuditLog;
import com.example.demo.service.AuditLogService;

@RestController
@RequestMapping("/api/audit-logs")
@CrossOrigin(origins = "*")
public class AuditLogController {

    @Autowired
    private AuditLogService auditLogService;

    @PostMapping
    public AuditLog saveAuditLog(@RequestBody AuditLog auditLog) {
        return auditLogService.saveAuditLog(auditLog);
    }

    @GetMapping
    public List<AuditLog> getAllAuditLogs() {
        return auditLogService.getAllAuditLogs();
    }

    @GetMapping("/{id}")
    public AuditLog getAuditLogById(@PathVariable Long id) {
        return auditLogService.getAuditLogById(id);
    }

    @PutMapping("/{id}")
    public AuditLog updateAuditLog(@PathVariable Long id,
                                   @RequestBody AuditLog auditLog) {
        return auditLogService.updateAuditLog(id, auditLog);
    }

    @DeleteMapping("/{id}")
    public String deleteAuditLog(@PathVariable Long id) {
        auditLogService.deleteAuditLog(id);
        return "Audit Log deleted successfully.";
    }
}