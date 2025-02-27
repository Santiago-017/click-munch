package com.bestellen.click_munch.store;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class StoreControllerTest {

    @Mock
    private StoreService storeService;

    @InjectMocks
    private StoreController storeController;

    private Store store1, store2;

    @BeforeEach
    void setUp() {
        store1 = new Store(
                1, "Restaurant A", "Alias A",
                "restA@email.com", "Password123", "Calle 45 #8-74",
                5.658465, -74.213654,
                List.of(), List.of(), List.of() // Inicializando listas vacías correctamente
        );

        store2 = new Store(
                2, "Restaurant B", "Alias B",
                "restB@email.com", "Password123", "Calle 53 #13-70",
                5.654651, -74.876548,
                List.of(), List.of(), List.of()
        );

    }

    @Test
    void findAll_ShouldReturnListOfStores() {
        when(storeService.findAll()).thenReturn(Arrays.asList(store1, store2));
        List<Store> result = storeController.findAll();
        assertEquals(2, result.size());
        extracted(result);
    }

    private static void extracted(List<Store> result) {
        assertFalse(result.isEmpty(), "El resultado no debería estar vacío");
        assertEquals("Restaurant A", result.getFirst().name());

    }

    @Test
    void findAll_ShouldThrowException_WhenNoStoresFound() {
        when(storeService.findAll()).thenReturn(List.of());
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, storeController::findAll);
        assertEquals("404 NOT_FOUND \"No stores found\"", exception.getMessage());
    }

    @Test
    void findById_ShouldReturnStore_WhenExists() {
        when(storeService.findById(1)).thenReturn(store1);
        Store result = storeController.findById(1);
        assertEquals("Restaurant A", result.name());
    }

    @Test
    void findById_ShouldThrowException_WhenNotFound() {
        when(storeService.findById(3)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Store not found"));
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> storeController.findById(3));
        assertEquals("404 NOT_FOUND \"Store not found\"", exception.getMessage());
    }

    @Test
    void findByName_ShouldReturnListOfStores() {
        when(storeService.findByName("Restaurant A")).thenReturn(Collections.singletonList(store1));
        List<Store> result = storeController.findByName("Restaurant A");
        assertEquals(1, result.size());
        assertEquals("Restaurant A", result.getFirst().name());
    }

    @Test
    void create_ShouldThrowException_WhenStoreAlreadyExists() {
        when(storeService.findByName("Restaurant A")).thenReturn(Collections.singletonList(store1));
        Store newStore = new Store(
                3, "Restaurant A", "Alias A",
                "test@email.com", "Password123", "Calle 123",
                5.123456, -74.654321,
                List.of(), List.of(), List.of()
        );

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> storeController.create(newStore));
        assertEquals("403 FORBIDDEN \"Store already exists\"", exception.getMessage());
    }
}
