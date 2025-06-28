package com.backend.assetmanagement;

import com.backend.assetmanagement.exception.ResourceNotFoundException;
import com.backend.assetmanagement.model.AssetCategory;
import com.backend.assetmanagement.repository.AssetCategoryRepository;
import com.backend.assetmanagement.service.AssetCategoryService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AssetCategoryServiceTest {

    @Mock
    private AssetCategoryRepository repo;

    @InjectMocks
    private AssetCategoryService service;

    private AutoCloseable closeable;
    private AssetCategory category;        // existing row
    private AssetCategory updatePayload;   // object used for update

    @BeforeEach
    void init() {
        closeable = MockitoAnnotations.openMocks(this);

        category = new AssetCategory();
        category.setId(1);
        category.setName("Laptop");

        updatePayload = new AssetCategory();
        updatePayload.setName("Desktop");
    }

    @AfterEach
    void cleanup() throws Exception {
        closeable.close();
    }
    @Test
    void addCategory_success() {
        when(repo.findByName("Laptop")).thenReturn(null);       
        when(repo.save(category)).thenReturn(category);

        AssetCategory saved = service.addCategory(category);

        assertEquals("Laptop", saved.getName());
        verify(repo).save(category);
    }

    @Test
    void addCategory_duplicateName_throws() {
        when(repo.findByName("Laptop")).thenReturn(new AssetCategory());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.addCategory(category));

        assertTrue(ex.getMessage().contains("Category already exists"));
    }

    @Test
    void getAllCategories_returnsList() {
        when(repo.findAll()).thenReturn(List.of(category));

        List<AssetCategory> list = service.getAllCategories();

        assertEquals(1, list.size());
        assertEquals("Laptop", list.get(0).getName());
    }

    @Test
    void getById_found() {
        when(repo.findById(1)).thenReturn(Optional.of(category));

        AssetCategory result = service.getCategoryById(1);

        assertEquals("Laptop", result.getName());
    }

    @Test
    void getById_notFound() {
        when(repo.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.getCategoryById(99));
    }

    @Test
    void updateCategory_success() {
        when(repo.findById(1)).thenReturn(Optional.of(category));
        when(repo.save(any())).thenAnswer(i -> i.getArgument(0));   // echo back

        AssetCategory updated = service.updateCategory(1, updatePayload);

        assertEquals("Desktop", updated.getName());
        verify(repo).save(category);                                // same instance updated
    }

    @Test
    void updateCategory_notFound() {
        when(repo.findById(42)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.updateCategory(42, updatePayload));
    }

    @Test
    void deleteCategory_success() {
        when(repo.findById(1)).thenReturn(Optional.of(category));

        service.deleteCategory(1);

        verify(repo).delete(category);
    }

    @Test
    void deleteCategory_notFound() {
        when(repo.findById(77)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.deleteCategory(77));
    }
}
