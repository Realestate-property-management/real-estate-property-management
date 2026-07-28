package com.example.demo.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.AuditLog;
import com.example.demo.repository.AuditLogRepository;
import com.example.demo.service.AuditLogService;

@Service
public class AuditLogServiceImpl implements AuditLogService {

    private static final String NOT_FOUND_MSG = "Audit Log not found with ID: ";

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Override
    public AuditLog saveAuditLog(AuditLog auditLog) {
        return auditLogRepository.save(auditLog);
    }

    @Override
    public AuditLog updateAuditLog(Long id, AuditLog auditLog) {

        AuditLog existingAuditLog = auditLogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(NOT_FOUND_MSG + id));

        existingAuditLog.setUser(auditLog.getUser());
        existingAuditLog.setAction(auditLog.getAction());
        existingAuditLog.setEntityType(auditLog.getEntityType());
        existingAuditLog.setEntityId(auditLog.getEntityId());
        existingAuditLog.setDescription(auditLog.getDescription());
        existingAuditLog.setIpAddress(auditLog.getIpAddress());
        existingAuditLog.setCreatedAt(auditLog.getCreatedAt());

        return auditLogRepository.save(existingAuditLog);
    }

    @Override
    public void deleteAuditLog(Long id) {

        AuditLog auditLog = auditLogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(NOT_FOUND_MSG + id));

        auditLogRepository.delete(auditLog);
    }

    @Override
    public AuditLog getAuditLogById(Long id) {

        return auditLogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(NOT_FOUND_MSG + id));
    }

    @Override
    public List<AuditLog> getAllAuditLogs() {

        return auditLogRepository.findAll();
    }
}