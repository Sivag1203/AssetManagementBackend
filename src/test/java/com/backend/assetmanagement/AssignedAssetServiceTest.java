package com.backend.assetmanagement;

import com.backend.assetmanagement.model.AssignedAsset;
import com.backend.assetmanagement.model.Asset;
import com.backend.assetmanagement.model.Employee;
import com.backend.assetmanagement.repository.AssignedAssetRepository;
import com.backend.assetmanagement.repository.AssetRepository;
import com.backend.assetmanagement.repository.EmployeeRepository;
import com.backend.assetmanagement.service.AssignedAssetService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AssignedAssetServiceTest {

    @Mock  private AssignedAssetRepository assignedRepo;
    @Mock  private AssetRepository         assetRepo;
    @Mock  private EmployeeRepository      employeeRepo;

    @InjectMocks
    private AssignedAssetService service;

    private AutoCloseable closeable;
    private Asset asset;
    private Employee emp;
    private AssignedAsset aa;

    @BeforeEach
    void init() {
        closeable = MockitoAnnotations.openMocks(this);

        asset = new Asset();
        asset.setId(1);

        emp = new Employee();
        emp.setId(1);

        aa = new AssignedAsset();
        aa.setId(100);
        aa.setAsset(asset);
        aa.setEmployee(emp);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void assignAsset_success() {
        when(assetRepo.findById(1)).thenReturn(Optional.of(asset));
        when(employeeRepo.findById(1)).thenReturn(Optional.of(emp));
        when(assignedRepo.save(any(AssignedAsset.class))).thenReturn(aa);

        AssignedAsset saved = service.assignAsset(aa);

        assertEquals(100, saved.getId());
        assertEquals(1,   saved.getAsset().getId());
        assertEquals(1,   saved.getEmployee().getId());
    }

    @Test
    void getAllAssignedAssets() {
        when(assignedRepo.findAll()).thenReturn(List.of(aa));

        List<AssignedAsset> list = service.getAllAssignedAssets();

        assertEquals(1, list.size());
        assertEquals(100, list.get(0).getId());
    }

    @Test
    void getById() {
        when(assignedRepo.findById(100)).thenReturn(Optional.of(aa));

        AssignedAsset result = service.getById(100);

        assertEquals(100, result.getId());
    }

    @Test
    void getByEmployeeId() {
        when(assignedRepo.findByEmployeeId(1)).thenReturn(Arrays.asList(aa));

        List<AssignedAsset> list = service.getByEmployeeId(1);

        assertEquals(1, list.size());
        assertEquals(100, list.get(0).getId());
    }

    @Test
    void getByAssetId() {
        when(assignedRepo.findByAssetId(1)).thenReturn(Arrays.asList(aa));

        List<AssignedAsset> list = service.getByAssetId(1);

        assertEquals(1, list.size());
        assertEquals(100, list.get(0).getId());
    }

    @Test
    void getByEmployeeAndAsset() {
        when(assignedRepo.findByEmployeeAndAsset(1, 1)).thenReturn(aa);

        AssignedAsset result = service.getByEmployeeAndAsset(1, 1);

        assertEquals(100, result.getId());
    }

    @Test
    void deleteAssignedAsset() {
        when(assignedRepo.findById(100)).thenReturn(Optional.of(aa));

        String msg = service.deleteAssignedAsset(100);

        verify(assignedRepo).delete(aa);
        assertEquals("Assigned asset 100 deleted.", msg);
    }
}
