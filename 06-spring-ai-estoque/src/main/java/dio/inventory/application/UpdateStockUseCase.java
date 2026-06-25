package dio.inventory.application;

import dio.inventory.application.input.UpdateStockInput;
import dio.inventory.application.output.StockOutput;
import dio.inventory.domain.Product;
import dio.inventory.domain.ProductRepository;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

@Service
public class UpdateStockUseCase {
    private final ProductRepository productRepository;

    public UpdateStockUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Tool(name = "update-stock", description = "Atualiza a quantidade de um produto em estoque")
    public StockOutput execute(UpdateStockInput input) {
        var existingProduct = productRepository.findBySku(input.sku());
        
        Product product;
        if (existingProduct.isPresent()) {
            product = existingProduct.get();
            long newQuantity = product.getQuantity() + input.quantityChange();
            
            if (newQuantity < 0) {
                throw new IllegalArgumentException("Quantidade não pode ser negativa. Estoque atual: " + product.getQuantity());
            }
            
            product = new Product(
                product.getId(),
                product.getName(),
                product.getSku(),
                newQuantity,
                input.location()
            );
        } else {
            if (input.quantityChange() < 0) {
                throw new IllegalArgumentException("Não é possível remover quantidade de um produto inexistente");
            }
            product = new Product(input.sku(), input.sku(), input.quantityChange(), input.location());
        }
        
        var saved = productRepository.save(product);
        return StockOutput.from(saved);
    }
}
