package sem2.logistics.services;

import org.springframework.stereotype.Service;
import sem2.logistics.dtos.request.ProductRequestDto;
import sem2.logistics.dtos.response.ProductResponseDto;
import sem2.logistics.models.Product;
import sem2.logistics.repositories.ProductRepository;
import sem2.logistics.util.State;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponseDto createProduct(ProductRequestDto productRequestDto) {

        if (productRequestDto.getState() == null) {
            productRequestDto.setState("CREATED");
        }

        Product product = new Product(productRequestDto.getProducerId(), productRequestDto.getDescription(), State.valueOf(productRequestDto.getState()));

        product = productRepository.save(product);

        return new ProductResponseDto(product.getProductId(), product.getProducerId(), product.getDescription(), product.getState());
    }

    public ProductResponseDto getProduct(Integer productId) {
        Product product = productRepository.getReferenceById(productId);
        return new ProductResponseDto(product.getProductId(), product.getProducerId(), product.getDescription(), product.getState());
    }

    public List<ProductResponseDto> getProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(product -> new ProductResponseDto(product.getProductId(), product.getProducerId(), product.getDescription(), product.getState()))
                .collect(Collectors.toList());
    }

}