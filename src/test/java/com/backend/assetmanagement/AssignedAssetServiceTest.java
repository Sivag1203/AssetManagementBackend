package com.backend.assetmanagement;

import com.backend.assetmanagement.dto.AssignedAssetDTO;
import com.backend.assetmanagement.model.AssignedAsset;
import com.backend.assetmanagement.model.Asset;
import com.backend.assetmanagement.model.Employee;
import com.backend.assetmanagement.repository.AssignedAssetRepository;
import com.backend.assetmanagement.repository.AssetRepository;
import com.backend.assetmanagement.repository.EmployeeRepository;
import com.backend.assetmanagement.service.AssignedAssetService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.List;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

public class AssignedAssetServiceTest {

    @InjectMocks
    private AssignedAssetService assignedAssetService;

    @Mock
    private AssignedAssetRepository assignedAssetRepository;

    @Mock
    private AssetRepository assetRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    private Asset asset;
    private Employee employee;
    private AssignedAsset assignedAsset;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        asset = new Asset();
        asset.setId(1);

        employee = new Employee();
        employee.setId(1);

        assignedAsset = new AssignedAsset();
        assignedAsset.setId(100);
        assignedAsset.setAsset(asset);
        assignedAsset.setEmployee(employee);
    }

    @Test
    public void testAssignAsset() {
        AssignedAssetDTO dto = new AssignedAssetDTO(0, 1, 1);

        when(assetRepository.findById(1)).thenReturn(Optional.of(asset));
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        when(assignedAssetRepository.save(org.mockito.Mockito.any(AssignedAsset.class)))
                .thenReturn(assignedAsset);

        AssignedAssetDTO result = assignedAssetService.assignAsset(dto);

        assertEquals(100, result.getId());
        assertEquals(1, result.getAssetId());
        assertEquals(1, result.getEmployeeId());
    }

    @Test
    public void testGetAllAssignedAssets() {
        when(assignedAssetRepository.findAll()).thenReturn(List.of(assignedAsset));

        List<AssignedAssetDTO> result = assignedAssetService.getAllAssignedAssets();

        assertEquals(1, result.size());
        assertEquals(100, result.get(0).getId());
    }

    @Test
    public void testGetById() {
        when(assignedAssetRepository.findById(100)).thenReturn(Optional.of(assignedAsset));

        AssignedAssetDTO result = assignedAssetService.getById(100);

        assertEquals(100, result.getId());
    }

    @Test
    public void testGetByEmployeeId() {
        when(assignedAssetRepository.findByEmployeeId(1)).thenReturn(Arrays.asList(assignedAsset));

        List<AssignedAssetDTO> result = assignedAssetService.getByEmployeeId(1);

        assertEquals(1, result.size());
        assertEquals(100, result.get(0).getId());
    }

    @Test
    public void testGetByAssetId() {
        when(assignedAssetRepository.findByAssetId(1)).thenReturn(Arrays.asList(assignedAsset));

        List<AssignedAssetDTO> result = assignedAssetService.getByAssetId(1);

        assertEquals(1, result.size());
        assertEquals(100, result.get(0).getId());
    }

    @Test
    public void testGetByEmployeeAndAsset() {
        when(assignedAssetRepository.findByEmployeeAndAsset(1, 1)).thenReturn(assignedAsset);

        AssignedAssetDTO result = assignedAssetService.getByEmployeeAndAsset(1, 1);

        assertEquals(100, result.getId());
    }

    @Test
    public void testDeleteAssignedAsset() {
        String result = assignedAssetService.deleteAssignedAsset(100);

        verify(assignedAssetRepository).deleteById(100);
        assertEquals("Assigned asset with ID 100 has been deleted.", result);
    }

    @AfterEach
    public void cleanup() {
        asset = null;
        employee = null;
        assignedAsset = null;
    }
}
