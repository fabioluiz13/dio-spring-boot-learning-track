package dio.inventory.infrastructure.persistence.entity;

import dio.inventory.domain.StorageLocation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity {
    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String sku;

    @Column(nullable = false)
    private long quantity;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StorageLocation location;
}
