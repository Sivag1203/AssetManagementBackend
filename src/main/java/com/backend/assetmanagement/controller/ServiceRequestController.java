package com.backend.assetmanagement.controller;

import com.backend.assetmanagement.dto.ServiceRequestDTO;
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
    public ServiceRequestDTO createRequest(@RequestBody ServiceRequestDTO dto) {
        return serviceRequestService.createRequest(dto);
    }

    @GetMapping("/all")
    public List<ServiceRequestDTO> getAllRequests() {
        return serviceRequestService.getAllRequests();
    }

    @GetMapping("/employee/{employeeId}")
    public List<ServiceRequestDTO> getRequestsByEmployee(@PathVariable int employeeId) {
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
}
