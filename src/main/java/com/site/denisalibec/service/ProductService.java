package com.site.denisalibec.service;

import com.site.denisalibec.dto.ProductDTO;
import com.site.denisalibec.dto.ProductCreateUpdateDTO;
import com.site.denisalibec.model.Product;
import com.site.denisalibec.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    // ----------- Variabile ------------------
    private final ProductRepository productRepository;

    // ----------- Constructor ----------------
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // ----------- Metode ---------------------

    // Creaza un produs nou in baza de date
    public ProductDTO createProduct(ProductCreateUpdateDTO dto) {
        Product saved = productRepository.save(fromCreateDTO(dto));
        return toDTO(saved);
    }

    // Returneaza toate produsele din baza de date
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Returneaza un produs dupa ID
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produsul nu a fost gasit"));
        return toDTO(product);
    }

    // Actualizeaza un produs existent
    public ProductDTO updateProduct(Long id, ProductCreateUpdateDTO dto) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produsul nu a fost gasit"));

        existing.setName(dto.getName());
        existing.setDescription(dto.getDescription());
        existing.setPrice(dto.getPrice());
        existing.setStock(dto.getStock());
        existing.setCategory(dto.getCategory());

        return toDTO(productRepository.save(existing));
    }

    // Sterge un produs dupa ID
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    // Converteste entitatea Product in DTO
    public ProductDTO toDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getCategory()
        );
    }

    // Creeaza un obiect Product din DTO de creare
    public Product fromCreateDTO(ProductCreateUpdateDTO dto) {
        return new Product(
                dto.getName(),
                dto.getDescription(),
                dto.getPrice(),
                dto.getStock(),
                dto.getCategory()
        );
    }

    // Returneaza produsele dintr-o categorie (fara paginare)
    public List<ProductDTO> getProductsByCategory(String category) {
        return productRepository.findByCategory(category, PageRequest.of(0, Integer.MAX_VALUE))
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Cauta produse dupa nume sau descriere (paginat)
    public Page<ProductDTO> searchProducts(String search, int page, int size) {
        return productRepository.searchProducts(search, PageRequest.of(page, size))
                .map(this::toDTO);
    }

    // Returneaza produse dintr-o categorie (paginat)
    public Page<ProductDTO> getProductsByCategoryPaginated(String category, int page, int size) {
        return productRepository.findByCategory(category, PageRequest.of(page, size))
                .map(this::toDTO);
    }

    // Returneaza produse paginat si sortat
    public Page<ProductDTO> getPaginatedProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(this::toDTO);
    }
}