package dio.inventory.application;

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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("ListProductsByLocationUseCase Tests")
class ListProductsByLocationUseCaseTest {

    @Mock
    private ProductRepository productRepository;

    private ListProductsByLocationUseCase listProductsByLocationUseCase;

    @BeforeEach
    void setup() {
        listProductsByLocationUseCase = new ListProductsByLocationUseCase(productRepository);
    }

    @Test
    @DisplayName("Should list all products in a location")
    void shouldListAllProductsInLocation() {
        var products = List.of(
            new Product("Leite", "SKU-MILK-001", 100, StorageLocation.FRIDGE),
            new Product("Queijo", "SKU-CHEESE-001", 50, StorageLocation.FRIDGE)
        );

        when(productRepository.findAllByLocation(StorageLocation.FRIDGE)).thenReturn(products);

        var result = listProductsByLocationUseCase.execute(StorageLocation.FRIDGE);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("Should return empty list if no products in location")
    void shouldReturnEmptyListIfNoProductsInLocation() {
        when(productRepository.findAllByLocation(StorageLocation.WAREHOUSE_B)).thenReturn(List.of());

        var result = listProductsByLocationUseCase.execute(StorageLocation.WAREHOUSE_B);

        assertNotNull(result);
        assertEquals(0, result.size());
    }

}
