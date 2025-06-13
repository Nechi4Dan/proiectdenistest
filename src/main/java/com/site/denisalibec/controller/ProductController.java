package com.site.denisalibec.controller;

import com.site.denisalibec.dto.ProductDTO;
import com.site.denisalibec.dto.ProductCreateUpdateDTO;
import com.site.denisalibec.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin
public class ProductController {

    // ----------- Variabile ------------------
    private final ProductService productService;

    // ----------- Constructor ------------------
    public ProductController(ProductService service) {
        this.productService = service;
    }

    // ----------- Metode ------------------

    // ------- GET: toate produsele -------
    @GetMapping
    public List<ProductDTO> getAll() {
        return productService.getAllProducts();
    }

    // ------- GET: produs dupa ID -------
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(productService.getProductById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Eroare: " + e.getMessage());
        }
    }

    // ------- GET: produse dupa categorie -------
    @GetMapping("/category/{category}")
    public List<ProductDTO> getByCategory(@PathVariable String category) {
        return productService.getProductsByCategory(category);
    }

    // ------- POST: creare produs (doar ADMIN) -------
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@RequestBody @Valid ProductCreateUpdateDTO dto) {
        try {
            return ResponseEntity.ok(productService.createProduct(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Eroare la creare: " + e.getMessage());
        }
    }

    // ------- PUT: actualizare produs (doar ADMIN) -------
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid ProductCreateUpdateDTO dto) {
        try {
            return ResponseEntity.ok(productService.updateProduct(id, dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Eroare la actualizare: " + e.getMessage());
        }
    }

    // ------- DELETE: stergere produs (doar ADMIN) -------
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok("Produs sters cu succes");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Eroare la stergere: " + e.getMessage());
        }
    }

    // ------- GET: paginare + sortare -------
    @GetMapping("/page")
    public Page<ProductDTO> getPaginatedProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        List<String> allowedFields = List.of("name", "price", "stock", "category");
        if (!allowedFields.contains(sortBy)) {
            sortBy = "name";
        }

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return productService.getPaginatedProducts(pageable);
    }

    // ------- GET: cautare produse cu paginare -------
    @GetMapping("/search")
    public Page<ProductDTO> searchProducts(
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return productService.searchProducts(query, page, size);
    }

    // ------- GET: produse dupa categorie + paginare -------
    @GetMapping("/category-page")
    public Page<ProductDTO> getByCategoryPaginated(
            @RequestParam String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return productService.getProductsByCategoryPaginated(category, page, size);
    }
}