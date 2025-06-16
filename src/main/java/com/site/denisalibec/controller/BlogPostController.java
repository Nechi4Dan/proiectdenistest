package com.site.denisalibec.controller;

import com.site.denisalibec.dto.BlogPostDTO;
import com.site.denisalibec.service.BlogPostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blog-posts")
@CrossOrigin(origins = "*")
public class BlogPostController {

    private final BlogPostService blogPostService;

    public BlogPostController(BlogPostService blogPostService) {
        this.blogPostService = blogPostService;
    }

    // GET /api/blog-posts → toate postarile ca DTO
    @GetMapping
    public List<BlogPostDTO> getAllPosts() {
        return blogPostService.getAllPosts();
    }

    // GET /api/blog-posts/{id} → o postare după ID ca DTO
    @GetMapping("/{id}")
    public BlogPostDTO getPostById(@PathVariable Long id) {
        return blogPostService.getPostById(id)
                .orElseThrow(() -> new RuntimeException("Postarea nu a fost găsită."));
    }

    // TODO: Adaugare metode POST, PUT si DELETE pentru administrarea articolelor:
    // - @PostMapping pentru a adauga postari noi
    // - @PutMapping pentru a edita postari existente
    // - DeleteMapping pentru a sterge postari
    // !!! aceste lucruri doar contul de admin va putea face
}
