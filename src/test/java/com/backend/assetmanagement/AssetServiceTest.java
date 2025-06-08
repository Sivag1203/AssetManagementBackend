package com.backend.assetmanagement;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.backend.assetmanagement.enums.AssetStatus;
import com.backend.assetmanagement.exception.ResourceNotFoundException;
import com.backend.assetmanagement.model.Asset;
import com.backend.assetmanagement.model.AssetCategory;
import com.backend.assetmanagement.repository.AssetCategoryRepository;
import com.backend.assetmanagement.repository.AssetRepository;
import com.backend.assetmanagement.service.AssetService;

@SpringBootTest
public class AssetServiceTest {

    @Mock
    private AssetRepository assetRepo;

    @Mock
    private AssetCategoryRepository categoryRepo;

    @InjectMocks
    private AssetService assetService;

    private AutoCloseable closeable;

    private Asset sampleAsset;
    private Asset updatedAsset;
    private AssetCategory sampleCategory;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);

        sampleAsset = new Asset();
        sampleAsset.setId(1);
        sampleAsset.setSerialNumber("SN123");
        sampleAsset.setSpecs("8GB RAM");
        sampleAsset.setStatus(AssetStatus.available);

        updatedAsset = new Asset();
        updatedAsset.setSerialNumber("NewSN");
        updatedAsset.setSpecs("New Specs");
        updatedAsset.setStatus(AssetStatus.not_available);

        sampleCategory = new AssetCategory();
        sampleCategory.setId(1);
    }

    @AfterEach
    void tearDown() throws Exception {
        sampleAsset = null;
        updatedAsset = null;
        sampleCategory = null;

        closeable.close();
    }

    @Test
    void testAddAsset_Success() {
        when(categoryRepo.findById(1)).thenReturn(Optional.of(sampleCategory));

        when(assetRepo.save(sampleAsset)).thenReturn(sampleAsset);

        Asset result = assetService.addAsset(1, sampleAsset);

        assertEquals("SN123", result.getSerialNumber());
        assertEquals("8GB RAM", result.getSpecs());
    }

    @Test
    void testAddAsset_CategoryNotFound() {
        when(categoryRepo.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            assetService.addAsset(99, sampleAsset);
        });
    }

    @Test
    void testUpdateAsset_Success() {
        when(assetRepo.findById(1)).thenReturn(Optional.of(sampleAsset));

        when(assetRepo.save(sampleAsset)).thenReturn(sampleAsset);

        Asset result = assetService.updateAsset(1, updatedAsset);

        assertEquals("NewSN", result.getSerialNumber());
        assertEquals("New Specs", result.getSpecs());
        assertEquals(AssetStatus.not_available, result.getStatus());
    }

    @Test
    void testUpdateAsset_NotFound() {
        when(assetRepo.findById(55)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            assetService.updateAsset(55, updatedAsset);
        });
    }

    @Test
    void testDeleteAsset_Success() {
        when(assetRepo.findById(5)).thenReturn(Optional.of(sampleAsset));

        assetService.deleteAsset(5);

        verify(assetRepo, times(1)).delete(sampleAsset);
    }

    @Test
    void testDeleteAsset_NotFound() {
        when(assetRepo.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            assetService.deleteAsset(99);
        });
    }
}
