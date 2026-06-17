package com.streaming.controllers;

import com.streaming.interfaces.ContentManageable;
import com.streaming.models.Content;
import java.util.List;
import java.util.Optional;

public class ContentController {
    private final ContentManageable contentService;

    public ContentController(ContentManageable contentService) {
        this.contentService = contentService;
    }

    public Content addContent(String title, String description, String genre, String type, Long duration, String url) {
        System.out.println("\n=== [ContentController] Adding content: " + title + " ===");
        Content content = new Content(title, description, genre, type, duration, url);
        Content createdContent = contentService.create(content);
        System.out.println("[ContentController] Content successfully added with ID: " + createdContent.getId());
        return createdContent;
    }

    public Optional<Content> getContentById(Long id) {
        System.out.println("\n=== [ContentController] Getting content by ID: " + id + " ===");
        return contentService.findById(id);
    }

    public List<Content> getContentByGenre(String genre) {
        System.out.println("\n=== [ContentController] Getting content by genre: " + genre + " ===");
        return contentService.findByGenre(genre);
    }

    public List<Content> getContentByType(String type) {
        System.out.println("\n=== [ContentController] Getting content by type: " + type + " ===");
        return contentService.findByType(type);
    }

    public List<Content> searchContent(String query) {
        System.out.println("\n=== [ContentController] Searching for: " + query + " ===");
        return contentService.search(query);
    }

    public void rateContent(Long contentId, Integer rating) {
        System.out.println("\n=== [ContentController] Rating content " + contentId + " with rating: " + rating + " ===");
        if (rating >= 1 && rating <= 5) {
            contentService.rateContent(contentId, rating);
            System.out.println("[ContentController] Content rated successfully");
        } else {
            System.out.println("[ContentController] Invalid rating. Must be between 1-5");
        }
    }

    public void listAllContent() {
        System.out.println("\n=== [ContentController] Listing all content ===");
        contentService.findAll().forEach(c -> System.out.println(c));
    }

    public boolean deleteContent(Long id) {
        System.out.println("\n=== [ContentController] Deleting content: " + id + " ===");
        return contentService.delete(id);
    }
}
