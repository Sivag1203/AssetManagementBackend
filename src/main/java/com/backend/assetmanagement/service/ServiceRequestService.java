package com.backend.assetmanagement.service;

import com.backend.assetmanagement.dto.ServiceRequestDTO;
import com.backend.assetmanagement.enums.ServiceStatus;
import com.backend.assetmanagement.model.Asset;
import com.backend.assetmanagement.model.Employee;
import com.backend.assetmanagement.model.ServiceRequest;
import com.backend.assetmanagement.repository.AssetRepository;
import com.backend.assetmanagement.repository.EmployeeRepository;
import com.backend.assetmanagement.repository.ServiceRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceRequestService {

    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AssetRepository assetRepository;

    public ServiceRequestDTO createRequest(ServiceRequestDTO dto) {
        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        Asset asset = assetRepository.findById(dto.getAssetId())
                .orElseThrow(() -> new RuntimeException("Asset not found"));

        ServiceRequest request = new ServiceRequest();
        request.setEmployee(employee);
        request.setAsset(asset);
        request.setDescription(dto.getDescription());
        request.setStatus(ServiceStatus.pending);
        request.setRequestDate(LocalDate.now());

        ServiceRequest saved = serviceRequestRepository.save(request);
        return convertToDTO(saved);
    }

    public List<ServiceRequestDTO> getAllRequests() {
        List<ServiceRequest> requests = serviceRequestRepository.findAll();
        List<ServiceRequestDTO> dtos = new ArrayList<>();
        for (ServiceRequest request : requests) {
            dtos.add(convertToDTO(request));
        }
        return dtos;
    }

    public List<ServiceRequestDTO> getRequestsByEmployee(int employeeId) {
        List<ServiceRequest> requests = serviceRequestRepository.findByEmployeeId(employeeId);
        List<ServiceRequestDTO> dtos = new ArrayList<>();
        for (ServiceRequest request : requests) {
            dtos.add(convertToDTO(request));
        }
        return dtos;
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
