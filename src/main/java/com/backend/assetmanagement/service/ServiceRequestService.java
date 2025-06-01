package com.backend.assetmanagement.service;

import com.backend.assetmanagement.enums.ServiceStatus;
import com.backend.assetmanagement.model.ServiceRequest;
import com.backend.assetmanagement.repository.ServiceRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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

    public ServiceRequest approveRequest(int requestId) {
        ServiceRequest request = serviceRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Service request not found"));
        request.setStatus(ServiceStatus.completed);
        return serviceRequestRepository.save(request);
    }

    public String rejectRequest(int requestId) {
        ServiceRequest request = serviceRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Service request not found"));
        serviceRequestRepository.delete(request);
        return "Service request rejected and deleted";
    }
}
