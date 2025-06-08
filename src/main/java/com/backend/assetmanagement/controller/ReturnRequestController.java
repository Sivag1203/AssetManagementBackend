package com.backend.assetmanagement.controller;

import com.backend.assetmanagement.dto.ReturnRequestDTO;
import com.backend.assetmanagement.service.ReturnRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/return-requests")
public class ReturnRequestController {

    @Autowired
    private ReturnRequestService returnRequestService;

    @PostMapping("/create")
    public ReturnRequestDTO createRequest(@RequestBody ReturnRequestDTO requestDTO) {
        return returnRequestService.createRequest(requestDTO);
    }

    @GetMapping("/all")
    public List<ReturnRequestDTO> getAllRequests() {
        return returnRequestService.getAllRequests();
    }

    @PutMapping("/approve/{id}")
    public ReturnRequestDTO approveRequest(@PathVariable int id) {
        return returnRequestService.approveRequest(id);
    }

    @DeleteMapping("/reject/{id}")
    public String rejectRequest(@PathVariable int id) {
        return returnRequestService.rejectRequest(id);
    }

    @GetMapping("/employee/{employeeId}")
    public List<ReturnRequestDTO> getRequestsByEmployee(@PathVariable int employeeId) {
        return returnRequestService.getRequestsByEmployee(employeeId);
    }
}
