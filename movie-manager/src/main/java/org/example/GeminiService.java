package org.example;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class GeminiService {

    private final RestTemplate restTemplate;

    @Value("${gemini.api.key}")
    private String apiKey;

    public GeminiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    public String generateMovieReview(String title, double rating) {
        String url =
                "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key="
                        + apiKey;

        String prompt = String.format(
                "Write a short movie review (3-5 sentences) for a movie titled '%s' with a rating of %.1f/10. " +
                        "Keep it natural and helpful, like a user review, and avoid spoilers.",
                title, rating
        );

        Map<String, Object> body = Map.of(
                "contents", List.of(
                        Map.of("parts", List.of(
                                Map.of("text", prompt)
                        ))
                )
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);

            List candidates = (List) response.getBody().get("candidates");
            if (candidates == null || candidates.isEmpty()) return "No review returned.";

            Map firstCandidate = (Map) candidates.get(0);
            Map content = (Map) firstCandidate.get("content");
            List parts = (List) content.get("parts");
            Map firstPart = (Map) parts.get(0);

            Object text = firstPart.get("text");
            return text != null ? text.toString() : "No review text returned.";
        } catch (Exception e) {
            return "Review unavailable (Gemini error).";
        }
    }

    public String generateMovieDescription(String title) {
        String url =
                "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key="
                        + apiKey;

        String prompt = "Write a short, fun movie description (2-4 sentences) for a movie titled: " + title;

        Map<String, Object> body = Map.of(
                "contents", List.of(
                        Map.of("parts", List.of(
                                Map.of("text", prompt)
                        ))
                )
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);

            // Extract: candidates[0].content.parts[0].text
            List candidates = (List) response.getBody().get("candidates");
            if (candidates == null || candidates.isEmpty()) return "No description returned.";

            Map firstCandidate = (Map) candidates.get(0);
            Map content = (Map) firstCandidate.get("content");
            List parts = (List) content.get("parts");
            Map firstPart = (Map) parts.get(0);

            Object text = firstPart.get("text");
            return text != null ? text.toString() : "No description text returned.";
        } catch (Exception e) {
            return "Description unavailable (Gemini error).";
        }
    }
}