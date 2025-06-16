package com.site.denisalibec.service;

import com.site.denisalibec.dto.ProductDTO;
import com.site.denisalibec.model.Product;
import com.site.denisalibec.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    // Constructor
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // TODO: Momentan doar GET. Se va extinde cu POST/PUT/DELETE (admin)

    // Obține toate produsele ca DTO
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Obține un produs după ID (ca DTO)
    public Optional<ProductDTO> getProductById(Long id) {
        return productRepository.findById(id).map(this::convertToDTO);
    }

    // Conversie Product → ProductDTO
    private ProductDTO convertToDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getCategory(),
                product.getImage()
        );
    }

    // TODO: folosit doar cand vom adauga produse din front (create/update/delete)
    public Product convertToEntity(ProductDTO dto) {
        Product p = new Product();
        p.setId(dto.getId());
        p.setName(dto.getName());
        p.setDescription(dto.getDescription());
        p.setPrice(dto.getPrice());
        p.setStock(dto.getStock());
        p.setCategory(dto.getCategory());
        p.setImage(dto.getImage());
        return p;
    }
}
