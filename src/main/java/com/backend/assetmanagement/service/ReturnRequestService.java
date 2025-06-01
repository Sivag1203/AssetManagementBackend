package com.backend.assetmanagement.service;

import com.backend.assetmanagement.enums.ReturnStatus;
import com.backend.assetmanagement.model.AssignedAsset;
import com.backend.assetmanagement.model.ReturnRequest;
import com.backend.assetmanagement.repository.AssignedAssetRepository;
import com.backend.assetmanagement.repository.ReturnRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReturnRequestService {

    @Autowired
    private ReturnRequestRepository returnRequestRepository;

    @Autowired
    private AssignedAssetRepository assignedAssetRepository;

    public ReturnRequest createRequest(ReturnRequest request) {
        request.setStatus(ReturnStatus.pending);
        request.setRequestDate(LocalDate.now());
        return returnRequestRepository.save(request);
    }

    public List<ReturnRequest> getAllRequests() {
        return returnRequestRepository.findAll();
    }

    public ReturnRequest approveRequest(int requestId) {
        ReturnRequest request = returnRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Return request not found"));

        request.setStatus(ReturnStatus.completed);
        returnRequestRepository.save(request);

        // Remove from assigned_assets
        AssignedAsset assignedAsset = assignedAssetRepository
                .findByEmployeeAndAsset(request.getEmployee().getId(), request.getAsset().getId());
        if (assignedAsset != null) {
            assignedAssetRepository.delete(assignedAsset);
        }

        return request;
    }

    public String rejectRequest(int requestId) {
        ReturnRequest request = returnRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Return request not found"));
        returnRequestRepository.delete(request);
        return "Return request rejected and deleted";
    }

    public List<ReturnRequest> getRequestsByEmployee(int employeeId) {
        return returnRequestRepository.findByEmployeeId(employeeId);
    }
}
