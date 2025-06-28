package com.backend.assetmanagement.controller;

import com.backend.assetmanagement.model.AssetRequest;
import com.backend.assetmanagement.service.AssetRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/asset-requests")
@CrossOrigin(origins = "http://localhost:5173")
public class AssetRequestController {

    @Autowired
    private AssetRequestService requestService;

    @PostMapping("/create")
    public AssetRequest createRequest(@RequestBody AssetRequest request) {
        return requestService.createRequest(request);
    }

    @GetMapping("/all")
    public List<AssetRequest> getAllRequests() {
        return requestService.getAllRequests();
    }

    @PutMapping("/approve/{id}")
    public AssetRequest approveRequest(@PathVariable int id) {
        return requestService.approveRequest(id);
    }

    @DeleteMapping("/reject/{id}")
    public String rejectRequest(@PathVariable int id) {
        return requestService.rejectRequest(id);
    }

    @GetMapping("/employee/{employeeId}")
    public List<AssetRequest> getRequestsByEmployee(@PathVariable int employeeId) {
        return requestService.getRequestsByEmployee(employeeId);
    }
    
    @GetMapping("/status-counts")
    public ResponseEntity<Map<String, Long>> getStatusCounts() {
        return ResponseEntity.ok(requestService.getStatusCounts());
    }
}
