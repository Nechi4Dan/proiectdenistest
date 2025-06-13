package com.site.denisalibec.controller;

import com.site.denisalibec.dto.ProductStockDTO;
import com.site.denisalibec.dto.ProductStockCreateUpdateDTO;
import com.site.denisalibec.service.ProductStockService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock")
@CrossOrigin
public class ProductStockController {

    // ----------- Variabile ------------------
    private final ProductStockService productStockService;

    // ----------- Constructor ------------------
    public ProductStockController(ProductStockService service) {
        this.productStockService = service;
    }

    // ----------- Metode ------------------

    // ------- GET: toate variantele de stoc pentru un produs -------
    @GetMapping("/product/{productId}")
    public List<ProductStockDTO> getStocksByProduct(@PathVariable Long productId) {
        return productStockService.getStocksForProduct(productId)
                .stream()
                .map(productStockService::toDTO)
                .toList();
    }

    // ------- GET: o varianta de stoc dupa ID -------
    @GetMapping("/{id}")
    public ResponseEntity<?> getStockById(@PathVariable Long id) {
        try {
            ProductStockDTO dto = productStockService.getStockById(id);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Eroare: " + e.getMessage());
        }
    }

    // ------- POST: adaugare stoc pentru un produs (doar ADMIN) -------
    @PostMapping("/product/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addStock(@PathVariable Long productId,
                                      @RequestBody @Valid ProductStockCreateUpdateDTO dto) {
        try {
            return ResponseEntity.ok(productStockService.addStock(productId, dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Eroare la adaugare stoc: " + e.getMessage());
        }
    }

    // ------- DELETE: stergere varianta de stoc (doar ADMIN) -------
    @DeleteMapping("/{stockId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteStock(@PathVariable Long stockId) {
        try {
            productStockService.deleteStock(stockId);
            return ResponseEntity.ok("Varianta de stoc a fost stearsa cu succes");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Eroare la stergere: " + e.getMessage());
        }
    }
}