package com.backend.assetmanagement;

import com.backend.assetmanagement.enums.AssetStatus;
import com.backend.assetmanagement.enums.Level;
import com.backend.assetmanagement.exception.ResourceNotFoundException;
import com.backend.assetmanagement.model.Asset;
import com.backend.assetmanagement.model.AssetCategory;
import com.backend.assetmanagement.repository.AssetCategoryRepository;
import com.backend.assetmanagement.repository.AssetRepository;
import com.backend.assetmanagement.service.AssetService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AssetServiceTest {

    @Mock
    private AssetRepository assetRepo;

    @Mock
    private AssetCategoryRepository categoryRepo;

    @InjectMocks
    private AssetService service;

    private AutoCloseable closeable;

    private AssetCategory category;   // reused across tests
    private Asset sample;             // existing asset
    private Asset updatePayload;      // asset used for update

    @BeforeEach
    void init() {
        closeable = MockitoAnnotations.openMocks(this);

        category = new AssetCategory();
        category.setId(1);

        sample = new Asset();
        sample.setId(1);
        sample.setSerialNumber("SN123");
        sample.setSpecs("8 GB RAM");
        sample.setEligibilityLevel(Level.L1);
        sample.setStatus(AssetStatus.available);

        updatePayload = new Asset();
        updatePayload.setSerialNumber("NewSN");
        updatePayload.setSpecs("New Specs");
        updatePayload.setEligibilityLevel(Level.L2);
        updatePayload.setStatus(AssetStatus.not_available);
    }

    @AfterEach
    void cleanup() throws Exception {
        closeable.close();
    }


    @Test
    void addAsset_success() {
        when(categoryRepo.findById(1)).thenReturn(Optional.of(category));
        when(assetRepo.save(sample)).thenReturn(sample);

        Asset saved = service.addAsset(1, sample);

        assertEquals("SN123", saved.getSerialNumber());
        assertEquals("8 GB RAM", saved.getSpecs());
        assertEquals("L1", saved.getEligibilityLevel());
    }

    @Test
    void addAsset_categoryNotFound() {
        when(categoryRepo.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.addAsset(99, sample));
    }

    @Test
    void updateAsset_success() {
        when(assetRepo.findById(1)).thenReturn(Optional.of(sample));
        when(assetRepo.save(sample)).thenReturn(sample); // service updates the same instance

        Asset updated = service.updateAsset(1, updatePayload);

        assertEquals("NewSN",          updated.getSerialNumber());
        assertEquals("New Specs",      updated.getSpecs());
        assertEquals(AssetStatus.not_available, updated.getStatus());
        assertEquals("L2",             updated.getEligibilityLevel());
    }

    @Test
    void updateAsset_notFound() {
        when(assetRepo.findById(55)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.updateAsset(55, updatePayload));
    }

    @Test
    void deleteAsset_success() {
        when(assetRepo.findById(5)).thenReturn(Optional.of(sample));

        service.deleteAsset(5);

        verify(assetRepo).delete(sample);
    }

    @Test
    void deleteAsset_notFound() {
        when(assetRepo.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.deleteAsset(99));
    }
}
