package com.backend.assetmanagement.controller;

import com.backend.assetmanagement.dto.ReturnRequestDTO;
import com.backend.assetmanagement.model.ReturnRequest;
import com.backend.assetmanagement.service.ReturnRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/return-requests")
@CrossOrigin(origins = "http://localhost:5173")
public class ReturnRequestController {

    @Autowired
    private ReturnRequestService returnRequestService;

    @PostMapping("/create")
    public ReturnRequest createRequest(@RequestBody ReturnRequest request) {
        return returnRequestService.createRequest(request);
    }

    @GetMapping("/all")
    public List<ReturnRequest> getAllRequests() {
        return returnRequestService.getAllRequests();
    }

    @PutMapping("/approve/{id}")
    public ReturnRequest approveRequest(@PathVariable int id) {
        return returnRequestService.approveRequest(id);
    }

    @DeleteMapping("/reject/{id}")
    public String rejectRequest(@PathVariable int id) {
        return returnRequestService.rejectRequest(id);
    }

    @GetMapping("/employee/{employeeId}")
    public List<ReturnRequest> getRequestsByEmployee(@PathVariable int employeeId) {
        return returnRequestService.getRequestsByEmployee(employeeId);
    }
    
    @GetMapping("/status-counts")
    public ResponseEntity<Map<String, Long>> getStatusCounts() {
        return ResponseEntity.ok(returnRequestService.getStatusCounts());
    }
}
