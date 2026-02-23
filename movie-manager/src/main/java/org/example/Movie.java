package org.example;

import jakarta.persistence.*;

@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private double rating;

    @Column(length = 2000)
    private String description;

    @Column(length = 2000)
    private String review;


    public Movie() {}

    public Movie(String title, double rating, String description) {
        this.title = title;
        this.rating = rating;
        this.description = description;
    }
    public String getReview() { return review; }
    public void setReview(String review) { this.review = review; }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public double getRating() { return rating; }
    public String getDescription() { return description; }

    public void setTitle(String title) { this.title = title; }
    public void setRating(double rating) { this.rating = rating; }
    public void setDescription(String description) { this.description = description; }
}