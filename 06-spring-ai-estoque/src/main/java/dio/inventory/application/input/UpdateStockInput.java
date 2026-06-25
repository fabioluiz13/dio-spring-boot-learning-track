package dio.inventory.application.input;

import dio.inventory.domain.StorageLocation;
import org.springframework.ai.tool.annotation.ToolParam;

public record UpdateStockInput(
    @ToolParam(description = "SKU (código) do produto") String sku,
    @ToolParam(description = "Quantidade a adicionar ou remover (negativo para remover)") long quantityChange,
    @ToolParam(description = "Localização de armazenamento") StorageLocation location
) {}
