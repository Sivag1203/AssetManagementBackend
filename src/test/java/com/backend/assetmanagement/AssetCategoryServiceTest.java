package com.backend.assetmanagement;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.backend.assetmanagement.exception.ResourceNotFoundException;
import com.backend.assetmanagement.model.AssetCategory;
import com.backend.assetmanagement.repository.AssetCategoryRepository;
import com.backend.assetmanagement.service.AssetCategoryService;

@SpringBootTest
public class AssetCategoryServiceTest {

    @Mock
    private AssetCategoryRepository assetCategoryRepository;

    @InjectMocks
    private AssetCategoryService assetCategoryService;

    private AutoCloseable closeable;

    private AssetCategory category;
    private AssetCategory updatedCategory;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);

        category = new AssetCategory();
        category.setId(1);
        category.setName("Laptop");

        updatedCategory = new AssetCategory();
        updatedCategory.setName("Updated Laptop");
    }

    @AfterEach
    void tearDown() throws Exception {
        category = null;
        updatedCategory = null;
        closeable.close();
    }

    @Test
    void testAddCategory() {
        when(assetCategoryRepository.save(category)).thenReturn(category);

        AssetCategory result = assetCategoryService.addCategory(category);

        assertEquals("Laptop", result.getName());
    }

    @Test
    void testGetAllCategories() {
        List<AssetCategory> categoryList = Arrays.asList(category);
        when(assetCategoryRepository.findAll()).thenReturn(categoryList);

        List<AssetCategory> result = assetCategoryService.getAllCategories();

        assertEquals(1, result.size());
        assertEquals("Laptop", result.get(0).getName());
    }

    @Test
    void testGetCategoryById_Success() {
        when(assetCategoryRepository.findById(1)).thenReturn(Optional.of(category));

        AssetCategory result = assetCategoryService.getCategoryById(1);

        assertEquals("Laptop", result.getName());
    }

    @Test
    void testGetCategoryById_NotFound() {
        when(assetCategoryRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            assetCategoryService.getCategoryById(99);
        });
    }

    @Test
    void testUpdateCategory_Success() {
        when(assetCategoryRepository.findById(1)).thenReturn(Optional.of(category));
        when(assetCategoryRepository.save(category)).thenReturn(category);

        AssetCategory result = assetCategoryService.updateCategory(1, updatedCategory);

        assertEquals("Updated Laptop", result.getName());
    }

    @Test
    void testUpdateCategory_NotFound() {
        when(assetCategoryRepository.findById(88)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            assetCategoryService.updateCategory(88, updatedCategory);
        });
    }

    @Test
    void testDeleteCategory_Success() {
        when(assetCategoryRepository.findById(1)).thenReturn(Optional.of(category));

        assetCategoryService.deleteCategory(1);

        verify(assetCategoryRepository, times(1)).delete(category);
    }

    @Test
    void testDeleteCategory_NotFound() {
        when(assetCategoryRepository.findById(66)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            assetCategoryService.deleteCategory(66);
        });
    }
}
