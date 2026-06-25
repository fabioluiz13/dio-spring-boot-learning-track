package dio.inventory.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Product {
    private ProductId id;
    private String name;
    private String sku;
    private long quantity;
    private StorageLocation location;

    public Product(String name, String sku, long quantity, StorageLocation location) {
        this.id = new ProductId();
        this.name = name;
        this.sku = sku;
        this.quantity = quantity;
        this.location = location;
    }
}
