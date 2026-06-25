package dio.inventory.infrastructure.persistence.repository;

import dio.inventory.domain.StorageLocation;
import dio.inventory.infrastructure.persistence.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductEntityRepository extends JpaRepository<ProductEntity, String> {
    List<ProductEntity> findAllByLocation(StorageLocation location);

    Optional<ProductEntity> findBySku(String sku);
}
