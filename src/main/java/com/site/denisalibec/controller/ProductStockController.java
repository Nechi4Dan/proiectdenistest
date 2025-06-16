package com.site.denisalibec.controller;

import com.site.denisalibec.dto.ProductStockDTO;
import com.site.denisalibec.service.ProductStockService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-stock")
@CrossOrigin(origins = "*")
public class ProductStockController {

    private final ProductStockService productStockService;

    public ProductStockController(ProductStockService productStockService) {
        this.productStockService = productStockService;
    }

    // GET /api/product-stock → toate variantele de stoc (marimi)
    @GetMapping
    public List<ProductStockDTO> getAllStockVariants() {
        return productStockService.getAllStockVariants();
    }

    // GET /api/product-stock/{id} → varianta de stoc dupa ID
    @GetMapping("/{id}")
    public ProductStockDTO getStockById(@PathVariable Long id) {
        return productStockService.getStockById(id)
                .orElseThrow(() -> new RuntimeException("Varianta de stoc nu a fost gasita"));
    }

    // TODO: Vom adauga metode POST, PUT si DELETE cand facem partea de administrare a stocurilor:
    // - @PostMapping pentru a adauga o varianta noua
    // - @PutMapping pentru a edita o varianta existenta
    // - @DeleteMapping pentru a sterge o varianta
}
