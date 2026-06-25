package dio.inventory.application;

import dio.inventory.application.input.UpdateStockInput;
import dio.inventory.application.output.StockOutput;
import dio.inventory.domain.Product;
import dio.inventory.domain.ProductRepository;
import dio.inventory.domain.StorageLocation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("UpdateStockUseCase Tests")
class UpdateStockUseCaseTest {

    @Mock
    private ProductRepository productRepository;

    private UpdateStockUseCase updateStockUseCase;

    @BeforeEach
    void setup() {
        updateStockUseCase = new UpdateStockUseCase(productRepository);
    }

    @Test
    @DisplayName("Should add quantity to existing product")
    void shouldAddQuantityToExistingProduct() {
        var existingProduct = new Product("Leite", "SKU-MILK-001", 100, StorageLocation.FRIDGE);
        var input = new UpdateStockInput("SKU-MILK-001", 50, StorageLocation.FRIDGE);

        when(productRepository.findBySku("SKU-MILK-001")).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        var result = updateStockUseCase.execute(input);

        assertNotNull(result);
        assertEquals("SKU-MILK-001", result.sku());
        assertEquals(150, result.quantity());
    }

    @Test
    @DisplayName("Should reject negative stock")
    void shouldRejectNegativeStock() {
        var existingProduct = new Product("Leite", "SKU-MILK-001", 10, StorageLocation.FRIDGE);
        var input = new UpdateStockInput("SKU-MILK-001", -20, StorageLocation.FRIDGE);

        when(productRepository.findBySku("SKU-MILK-001")).thenReturn(Optional.of(existingProduct));

        assertThrows(IllegalArgumentException.class, () -> updateStockUseCase.execute(input));
    }

    @Test
    @DisplayName("Should create new product if SKU not found")
    void shouldCreateNewProductIfSkuNotFound() {
        var input = new UpdateStockInput("SKU-NEW-001", 100, StorageLocation.WAREHOUSE_A);

        when(productRepository.findBySku("SKU-NEW-001")).thenReturn(Optional.empty());
        when(productRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        var result = updateStockUseCase.execute(input);

        assertNotNull(result);
        assertEquals(100, result.quantity());
    }

}
