package dio.inventory.application.output;

import dio.inventory.domain.Product;

public record StockOutput(String id, String name, String sku, long quantity, String location) {
    public static StockOutput from(Product product) {
        return new StockOutput(
            product.getId().uuid().toString(),
            product.getName(),
            product.getSku(),
            product.getQuantity(),
            product.getLocation().name()
        );
    }
}
