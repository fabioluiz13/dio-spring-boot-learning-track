package dio.inventory.domain;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Product save(Product product);

    List<Product> findAllByLocation(StorageLocation location);

    Optional<Product> findBySku(String sku);

    List<Product> findAll();
}
