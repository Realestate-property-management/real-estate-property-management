package com.example.demo.service;

import java.util.List;
import com.example.demo.models.AuditLog;

public interface AuditLogService {

    AuditLog saveAuditLog(AuditLog auditLog);

    AuditLog updateAuditLog(Long id, AuditLog auditLog);

    void deleteAuditLog(Long id);

    AuditLog getAuditLogById(Long id);

    List<AuditLog> getAllAuditLogs();

}