package com.backend.assetmanagement.controller;

import com.backend.assetmanagement.dto.ServiceRequestDTO;
import com.backend.assetmanagement.model.ServiceRequest;
import com.backend.assetmanagement.service.ServiceRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/service-requests")
public class ServiceRequestController {

    @Autowired
    private ServiceRequestService serviceRequestService;

    @PostMapping("/create")
    public ServiceRequest createRequest(@RequestBody ServiceRequest request) {
        return serviceRequestService.createRequest(request);
    }

    @GetMapping("/all")
    public List<ServiceRequest> getAllRequests() {
        return serviceRequestService.getAllRequests();
    }

    @GetMapping("/employee/{employeeId}")
    public List<ServiceRequest> getRequestsByEmployee(@PathVariable int employeeId) {
        return serviceRequestService.getRequestsByEmployee(employeeId);
    }

    @PutMapping("/approve/{id}")
    public ServiceRequestDTO approveRequest(@PathVariable int id) {
        return serviceRequestService.approveRequest(id);
    }

    @DeleteMapping("/reject/{id}")
    public String rejectRequest(@PathVariable int id) {
        return serviceRequestService.rejectRequest(id);
    }
    
    @PutMapping("/in-progress/{id}")
    public ServiceRequestDTO markInProgress(@PathVariable int id) {
        return serviceRequestService.markInProgress(id);
    }

    
    @GetMapping("/status-counts")
    public ResponseEntity<Map<String, Long>> getStatusCounts() {
        return ResponseEntity.ok(serviceRequestService.getStatusCounts());
    }
}
