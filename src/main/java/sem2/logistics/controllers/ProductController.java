package sem2.logistics.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sem2.logistics.dtos.request.ProductRequestDto;
import sem2.logistics.dtos.response.ProductResponseDto;
import sem2.logistics.services.ProductService;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/form")
    public String createProductForm(Model model) {
        model.addAttribute("product", new ProductRequestDto());
        return "create-product";
    }

    @PostMapping("/form")
    public String createProductSubmit(@ModelAttribute ProductRequestDto productRequestDto) {
        try {
            ProductResponseDto productResponseDto = productService.createProduct(productRequestDto);
            return "successfully-created-product";
        } catch (Exception ex) {
            return "error";
        }
    }

    @GetMapping("/{productId}")
    public String getProduct(Model model, @PathVariable Integer productId) {
        try {
            ProductResponseDto productResponseDto = productService.getProduct(productId);
            model.addAttribute("product", productResponseDto);
            return "get-product";
        } catch (Exception ex) {
            return "error";
        }
    }

    @GetMapping()
    public String getProducts(Model model) {
        try {
            List<ProductResponseDto> productResponseDtos = productService.getProducts();
            model.addAttribute("products", productResponseDtos);
            return "get-products";
        } catch (Exception ex) {
            return "error";
        }
    }

}