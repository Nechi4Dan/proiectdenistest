package com.site.denisalibec.dto;

public class BlogPostDTO {
    private Long id;
    private String title;
    private String summary;
    private String text;
    private String image;

    // Constructori
    public BlogPostDTO() {}

    public BlogPostDTO(Long id, String title, String summary, String text, String image) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.text = text;
        this.image = image;
    }

    // Getteri și Setteri
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
}
