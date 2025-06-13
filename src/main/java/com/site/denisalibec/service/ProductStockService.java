package com.site.denisalibec.service;

import com.site.denisalibec.dto.ProductStockDTO;
import com.site.denisalibec.dto.ProductStockCreateUpdateDTO;
import com.site.denisalibec.model.Product;
import com.site.denisalibec.model.ProductStock;
import com.site.denisalibec.repository.ProductRepository;
import com.site.denisalibec.repository.ProductStockRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductStockService {

    // ------------- Variabile -------------------
    private final ProductStockRepository productStockRepo;
    private final ProductRepository productRepo;

    // ------------- Constructor ------------------
    public ProductStockService(ProductStockRepository productStockRepo, ProductRepository productRepo) {
        this.productStockRepo = productStockRepo;
        this.productRepo = productRepo;
    }

    // ------------- Metode -----------------------

    // Creeaza o varianta de stoc pentru un produs
    public ProductStockDTO addStock(Long productId, ProductStockCreateUpdateDTO dto) {
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Produsul nu a fost gasit cu ID-ul " + productId));
        ProductStock stock = fromDTO(dto, product);
        return toDTO(productStockRepo.save(stock));
    }

    // Returneaza o varianta de stoc dupa ID
    public ProductStockDTO getStockById(Long id) {
        ProductStock stock = productStockRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Varianta de stoc nu a fost gasita cu ID-ul " + id));
        return toDTO(stock);
    }

    // Returneaza toate variantele de stoc pentru un produs
    public List<ProductStock> getStocksForProduct(Long productId) {
        return productStockRepo.findByProductId(productId);
    }

    // Sterge o varianta de stoc dupa ID
    public void deleteStock(Long stockId) {
        productStockRepo.deleteById(stockId);
    }

    // Converteste entitatea ProductStock in DTO
    public ProductStockDTO toDTO(ProductStock stock) {
        return new ProductStockDTO(
                stock.getId(),
                stock.getSize(),
                stock.getStock(),
                stock.getProduct().getId()
        );
    }

    // Creeaza un obiect ProductStock din DTO si produs
    public ProductStock fromDTO(ProductStockCreateUpdateDTO dto, Product product) {
        return new ProductStock(product, dto.getSize(), dto.getStock());
    }
}