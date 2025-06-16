package com.site.denisalibec.service;

import com.site.denisalibec.dto.BlogPostDTO;
import com.site.denisalibec.model.BlogPost;
import com.site.denisalibec.repository.BlogPostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BlogPostService {

    private final BlogPostRepository blogPostRepository;

    public BlogPostService(BlogPostRepository blogPostRepository) {
        this.blogPostRepository = blogPostRepository;
    }

    // TODO: Momentan doar GET. Se va extinde cu POST/PUT/DELETE (admin)

    // Returnează toate postările (ca DTO)
    public List<BlogPostDTO> getAllPosts() {
        return blogPostRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Returneaza o postare după ID (ca DTO)
    public Optional<BlogPostDTO> getPostById(Long id) {
        return blogPostRepository.findById(id)
                .map(this::convertToDTO);
    }

    // Conversie BlogPost → DTO
    private BlogPostDTO convertToDTO(BlogPost post) {
        return new BlogPostDTO(
                post.getId(),
                post.getTitle(),
                post.getSummary(),
                post.getText(),
                post.getImage()
        );
    }

    // TODO: va fi folosit cand adaugam create/update/delete din frontend
    public BlogPost convertToEntity(BlogPostDTO dto) {
        BlogPost post = new BlogPost();
        post.setId(dto.getId());
        post.setTitle(dto.getTitle());
        post.setSummary(dto.getSummary());
        post.setText(dto.getText());
        post.setImage(dto.getImage());
        return post;
    }
}
