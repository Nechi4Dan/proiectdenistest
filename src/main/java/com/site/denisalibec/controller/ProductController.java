package com.site.denisalibec.controller;

import com.site.denisalibec.dto.ProductDTO;
import com.site.denisalibec.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // GET /api/products → toate produsele (ca DTO)
    @GetMapping
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    // GET /api/products/{id} → produs după ID (ca DTO)
    @GetMapping("/{id}")
    public ProductDTO getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .orElseThrow(() -> new RuntimeException("Produsul nu a fost gasit"));
    }

    // TODO: Adaugare metode POST, PUT si DELETE pentru administrarea produselor:
    // - @PostMapping pentru a adauga produse noi
    // - @PutMapping pentru a edita produse existente
    // - DeleteMapping pentru a sterge produse
    // !!! aceste lucruri doar contul de admin va putea face
}