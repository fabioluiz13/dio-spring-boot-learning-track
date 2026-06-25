package dio.inventory.infrastructure.http.response;

import dio.inventory.application.output.StockOutput;

public record StockResponse(String id, String name, String sku, long quantity, String location) {
    public static StockResponse from(StockOutput output) {
        return new StockResponse(output.id(), output.name(), output.sku(), output.quantity(), output.location());
    }
}
