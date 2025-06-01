package com.backend.assetmanagement.controller;

import com.backend.assetmanagement.model.ServiceRequest;
import com.backend.assetmanagement.service.ServiceRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ServiceRequest approveRequest(@PathVariable int id) {
        return serviceRequestService.approveRequest(id);
    }

    @DeleteMapping("/reject/{id}")
    public String rejectRequest(@PathVariable int id) {
        return serviceRequestService.rejectRequest(id);
    }
}
