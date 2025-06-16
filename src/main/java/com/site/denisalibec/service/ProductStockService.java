package com.site.denisalibec.service;

import com.site.denisalibec.dto.ProductStockDTO;
import com.site.denisalibec.model.Product;
import com.site.denisalibec.model.ProductStock;
import com.site.denisalibec.repository.ProductRepository;
import com.site.denisalibec.repository.ProductStockRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductStockService {

    private final ProductStockRepository stockRepository;
    private final ProductRepository productRepository;

    public ProductStockService(ProductStockRepository stockRepository, ProductRepository productRepository) {
        this.stockRepository = stockRepository;
        this.productRepository = productRepository;
    }

    // TODO: Momentan nu folosim aceste metode din frontend.
    // Vor fi folosite cand adaugam functionalitati de CRUD pentru variantele de stoc (ex: marimi).

    public List<ProductStockDTO> getAllStockVariants() {
        return stockRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<ProductStockDTO> getStockById(Long id) {
        return stockRepository.findById(id).map(this::convertToDTO);
    }

    public ProductStockDTO convertToDTO(ProductStock stock) {
        return new ProductStockDTO(
                stock.getId(),
                stock.getProduct().getId(),
                stock.getSize(),
                stock.getStock()
        );
    }

    public ProductStock convertToEntity(ProductStockDTO dto) {
        ProductStock stock = new ProductStock();
        stock.setId(dto.getId());
        stock.setSize(dto.getSize());
        stock.setStock(dto.getStock());

        // Asociaza produsul pe baza ID-ului
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Produsul nu a fost gasit"));
        stock.setProduct(product);

        return stock;
    }
}
