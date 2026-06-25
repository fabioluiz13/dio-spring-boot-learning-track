package dio.inventory.infrastructure.persistence.repository;

import dio.inventory.domain.Product;
import dio.inventory.domain.ProductId;
import dio.inventory.domain.ProductRepository;
import dio.inventory.domain.StorageLocation;
import dio.inventory.infrastructure.persistence.entity.ProductEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JpaProductRepository implements ProductRepository {
    private final ProductEntityRepository productEntityRepository;

    public JpaProductRepository(ProductEntityRepository productEntityRepository) {
        this.productEntityRepository = productEntityRepository;
    }

    @Override
    public Product save(Product product) {
        var entity = new ProductEntity(
            product.getId().uuid().toString(),
            product.getName(),
            product.getSku(),
            product.getQuantity(),
            product.getLocation()
        );
        var saved = productEntityRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public List<Product> findAllByLocation(StorageLocation location) {
        return productEntityRepository.findAllByLocation(location)
            .stream()
            .map(this::toDomain)
            .toList();
    }

    @Override
    public Optional<Product> findBySku(String sku) {
        return productEntityRepository.findBySku(sku).map(this::toDomain);
    }

    @Override
    public List<Product> findAll() {
        return productEntityRepository.findAll().stream().map(this::toDomain).toList();
    }

    private Product toDomain(ProductEntity entity) {
        return new Product(
            new ProductId(java.util.UUID.fromString(entity.getId())),
            entity.getName(),
            entity.getSku(),
            entity.getQuantity(),
            entity.getLocation()
        );
    }
}
