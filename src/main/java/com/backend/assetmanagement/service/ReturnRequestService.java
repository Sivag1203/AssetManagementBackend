package com.backend.assetmanagement.service;

import com.backend.assetmanagement.dto.ReturnRequestDTO;
import com.backend.assetmanagement.enums.ReturnStatus;
import com.backend.assetmanagement.model.Asset;
import com.backend.assetmanagement.model.AssignedAsset;
import com.backend.assetmanagement.model.Employee;
import com.backend.assetmanagement.model.ReturnRequest;
import com.backend.assetmanagement.repository.AssetRepository;
import com.backend.assetmanagement.repository.AssignedAssetRepository;
import com.backend.assetmanagement.repository.EmployeeRepository;
import com.backend.assetmanagement.repository.ReturnRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReturnRequestService {

    @Autowired
    private ReturnRequestRepository returnRequestRepository;

    @Autowired
    private AssignedAssetRepository assignedAssetRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AssetRepository assetRepository;

    public ReturnRequest createRequest(ReturnRequest dto) {
        dto.setStatus(ReturnStatus.pending);
        dto.setRequestDate(LocalDate.now());
        return returnRequestRepository.save(dto);
    }

    public List<ReturnRequest> getAllRequests() {

        return returnRequestRepository.findAll();
    }

    public ReturnRequestDTO approveRequest(int id) {
        ReturnRequest request = returnRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));
        request.setStatus(ReturnStatus.completed);
        returnRequestRepository.save(request);

        AssignedAsset assignedAsset = assignedAssetRepository
                .findByEmployeeAndAsset(request.getEmployee().getId(), request.getAsset().getId());
        if (assignedAsset != null) {
            assignedAssetRepository.delete(assignedAsset);
        }

        return convertToDTO(request);
    }

    public String rejectRequest(int id) {
        ReturnRequest request = returnRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));
        returnRequestRepository.delete(request);
        return "Return request rejected and deleted.";
    }

    public List<ReturnRequest> getRequestsByEmployee(int employeeId) {
        return returnRequestRepository.findByEmployeeId(employeeId);
    }

    private ReturnRequestDTO convertToDTO(ReturnRequest request) {
        ReturnRequestDTO dto = new ReturnRequestDTO();
        dto.setId(request.getId());
        dto.setEmployeeId(request.getEmployee().getId());
        dto.setEmployeeName(request.getEmployee().getName());
        dto.setAssetId(request.getAsset().getId());
        dto.setAssetSpecs(request.getAsset().getSpecs());
        dto.setStatus(request.getStatus());
        dto.setRequestDate(request.getRequestDate());
        return dto;
    }
    
    public Map<String, Long> getStatusCounts() {
        List<Object[]> results = returnRequestRepository.countGroupedByStatus();
        Map<String, Long> map = new HashMap<>();
        for (Object[] obj : results) {
            map.put(obj[0].toString(), (Long) obj[1]);
        }
        return map;
    }
}
