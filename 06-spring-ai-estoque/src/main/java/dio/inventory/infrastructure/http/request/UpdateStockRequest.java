package dio.inventory.infrastructure.http.request;

import dio.inventory.application.input.UpdateStockInput;
import dio.inventory.domain.StorageLocation;

public record UpdateStockRequest(String sku, long quantityChange, StorageLocation location) {
    public UpdateStockInput toInput() {
        return new UpdateStockInput(sku, quantityChange, location);
    }
}
