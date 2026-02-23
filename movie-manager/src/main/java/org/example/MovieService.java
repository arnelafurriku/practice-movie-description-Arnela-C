package org.example;


import org.example.MovieRepository;
import org.example.Movie;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final GeminiService geminiService;

    public MovieService(MovieRepository movieRepository, GeminiService geminiService) {
        this.movieRepository = movieRepository;
        this.geminiService = geminiService;
    }

    public Movie addMovie(String title, double rating) {
        String description = geminiService.generateMovieDescription(title);
        Movie movie = new Movie(title, rating, description);
        return movieRepository.save(movie);
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }
}