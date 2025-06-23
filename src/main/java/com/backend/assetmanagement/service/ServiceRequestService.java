package com.backend.assetmanagement.service;

import com.backend.assetmanagement.dto.ServiceRequestDTO;
import com.backend.assetmanagement.enums.ServiceStatus;
import com.backend.assetmanagement.model.ServiceRequest;

import com.backend.assetmanagement.repository.ServiceRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ServiceRequestService {

    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    public ServiceRequest createRequest(ServiceRequest request) {
        request.setStatus(ServiceStatus.pending);
        request.setRequestDate(LocalDate.now());
        return serviceRequestRepository.save(request);
    }

    public List<ServiceRequest> getAllRequests() {
        return serviceRequestRepository.findAll();
    }

    public List<ServiceRequest> getRequestsByEmployee(int employeeId) {
        return serviceRequestRepository.findByEmployeeId(employeeId);
    }

    public ServiceRequestDTO approveRequest(int requestId) {
        ServiceRequest request = serviceRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Service request not found"));
        request.setStatus(ServiceStatus.completed);
        return convertToDTO(serviceRequestRepository.save(request));
    }

    public String rejectRequest(int requestId) {
        ServiceRequest request = serviceRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Service request not found"));
        serviceRequestRepository.delete(request);
        return "Service request rejected and deleted";
    }
    
    public ServiceRequestDTO markInProgress(int requestId) {
        ServiceRequest request = serviceRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Service request not found"));
        request.setStatus(ServiceStatus.in_progress);
        return convertToDTO(serviceRequestRepository.save(request));
    }

    
    public Map<String, Long> getStatusCounts() {
        List<Object[]> results = serviceRequestRepository.countGroupedByStatus();
        Map<String, Long> map = new HashMap<>();
        for (Object[] obj : results) {
            map.put(obj[0].toString(), (Long) obj[1]);
        }
        return map;
    }

    private ServiceRequestDTO convertToDTO(ServiceRequest request) {
        ServiceRequestDTO dto = new ServiceRequestDTO();
        dto.setId(request.getId());
        dto.setAssetId(request.getAsset().getId());
        dto.setAssetSpecs(request.getAsset().getSpecs());
        dto.setEmployeeId(request.getEmployee().getId());
        dto.setEmployeeName(request.getEmployee().getName());
        dto.setDescription(request.getDescription());
        dto.setStatus(request.getStatus());
        dto.setRequestDate(request.getRequestDate());
        return dto;
    }
}
