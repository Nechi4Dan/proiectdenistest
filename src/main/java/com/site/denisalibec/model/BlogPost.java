package com.site.denisalibec.model;

import jakarta.persistence.*;

// ----------- Entitate pentru postare blog ------------------

@Entity
@Table(name = "blog_posts")
public class BlogPost {

    // ----------- Variabile ------------------
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 300)
    private String summary;

    @Lob
    private String text;

    private String image;

    // ----------- Constructori ------------------
    public BlogPost() {}

    //TODO: constructor folosit in viitor pt crearea postare blog (ex din form)
    public BlogPost(String title, String summary, String text, String image) {
        this.title = title;
        this.summary = summary;
        this.text = text;
        this.image = image;
    }

    //TODO: Construcotr complet, folosit la update/transfer obiecte DTO ->entity
    public BlogPost(Long id, String title, String summary, String text, String image) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.text = text;
        this.image = image;
    }

    // ----------- Getteri si Setteri ------------------
    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    // TODO: cand adaugam optiunea de CRUD pt blog
    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}

    // TODO: cand adaugam optiunea de CRUD pt blog
    public String getSummary() {return summary;}
    public void setSummary(String summary) {this.summary = summary;}

    // TODO: cand adaugam optiunea de CRUD pt blog
    public String getText() {return text;}
    public void setText(String text) {this.text = text;}

    // TODO: cand adaugam optiunea de CRUD pt blog
    public String getImage() {return image;}
    public void setImage(String image) {this.image = image;}
}