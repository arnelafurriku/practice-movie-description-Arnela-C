package org.example;


import jakarta.validation.constraints.*;

public class CreateMovieRequest {

    @NotBlank
    private String title;

    @Min(0)
    @Max(10)
    private double rating;

    public String getTitle() { return title; }
    public double getRating() { return rating; }

    public void setTitle(String title) { this.title = title; }
    public void setRating(double rating) { this.rating = rating; }
}
