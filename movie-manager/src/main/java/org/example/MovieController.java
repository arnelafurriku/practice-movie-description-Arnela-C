package org.example;

import jakarta.validation.Valid;
import org.example.CreateMovieRequest;
import org.example.Movie;
import org.example.MovieService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping
    public Movie addMovie(@RequestBody @Valid CreateMovieRequest request) {
        return movieService.addMovie(request.getTitle(), request.getRating());
    }

    @GetMapping
    public List<Movie> getAllMovies() {
        return movieService.getAllMovies();
    }
    @PostMapping("/{id}/review")
    public Movie generateReview(@PathVariable Long id) {
        return movieService.generateAndSaveReview(id);
    }
}