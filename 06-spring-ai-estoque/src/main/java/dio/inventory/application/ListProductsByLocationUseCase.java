package dio.inventory.application;

import dio.inventory.application.output.StockOutput;
import dio.inventory.domain.StorageLocation;
import dio.inventory.domain.ProductRepository;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListProductsByLocationUseCase {
    private final ProductRepository productRepository;

    public ListProductsByLocationUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Tool(name = "list-by-location", description = "Lista todos os produtos em uma localização de armazenamento")
    public List<StockOutput> execute(@ToolParam(description = "Localização do estoque") StorageLocation location) {
        return productRepository.findAllByLocation(location).stream().map(StockOutput::from).toList();
    }
}
