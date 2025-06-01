package com.backend.assetmanagement.service;

import com.backend.assetmanagement.enums.RequestStatus;
import com.backend.assetmanagement.model.AssetRequest;
import com.backend.assetmanagement.model.AssignedAsset;
import com.backend.assetmanagement.repository.AssetRequestRepository;
import com.backend.assetmanagement.repository.AssignedAssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AssetRequestService {

    @Autowired
    private AssetRequestRepository requestRepository;

    @Autowired
    private AssignedAssetRepository assignedAssetRepository;

    public AssetRequest createRequest(AssetRequest request) {
        request.setRequestDate(LocalDate.now());
        request.setStatus(RequestStatus.pending);
        return requestRepository.save(request);
    }

    public List<AssetRequest> getAllRequests() {
        return requestRepository.findAll();
    }

    public AssetRequest approveRequest(int requestId) {
        AssetRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        request.setStatus(RequestStatus.approved);
        requestRepository.save(request);

        AssignedAsset assignedAsset = new AssignedAsset();
        assignedAsset.setAsset(request.getAsset());
        assignedAsset.setEmployee(request.getEmployee());
        assignedAssetRepository.save(assignedAsset);

        return request;
    }

    public String rejectRequest(int requestId) {
        AssetRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        requestRepository.delete(request);
        return "Request rejected and deleted";
    }

    public List<AssetRequest> getRequestsByEmployee(int employeeId) {
        return requestRepository.findByEmployeeId(employeeId);
    }
}
