package com.streaming.repositories;

import com.streaming.interfaces.ContentManageable;
import com.streaming.models.Content;
import java.util.*;

public class ContentRepository implements ContentManageable {
    private Map<Long, Content> contents = new HashMap<>();
    private Long nextId = 1L;

    @Override
    public Content create(Content content) {
        content.setId(nextId++);
        contents.put(content.getId(), content);
        System.out.println("[ContentRepository] Content created: " + content);
        return content;
    }

    @Override
    public Optional<Content> findById(Long id) {
        System.out.println("[ContentRepository] Finding content by ID: " + id);
        return Optional.ofNullable(contents.get(id));
    }

    @Override
    public List<Content> findByGenre(String genre) {
        System.out.println("[ContentRepository] Finding content by genre: " + genre);
        return contents.values().stream()
                .filter(c -> c.getGenre().equalsIgnoreCase(genre))
                .toList();
    }

    @Override
    public List<Content> findByType(String type) {
        System.out.println("[ContentRepository] Finding content by type: " + type);
        return contents.values().stream()
                .filter(c -> c.getType().equalsIgnoreCase(type))
                .toList();
    }

    @Override
    public List<Content> findAll() {
        System.out.println("[ContentRepository] Finding all content");
        return new ArrayList<>(contents.values());
    }

    @Override
    public Content update(Content content) {
        contents.put(content.getId(), content);
        System.out.println("[ContentRepository] Content updated: " + content);
        return content;
    }

    @Override
    public boolean delete(Long id) {
        boolean deleted = contents.remove(id) != null;
        System.out.println("[ContentRepository] Content deleted: " + id + " - Success: " + deleted);
        return deleted;
    }

    @Override
    public List<Content> search(String query) {
        System.out.println("[ContentRepository] Searching content for: " + query);
        return contents.values().stream()
                .filter(c -> c.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                           c.getDescription().toLowerCase().contains(query.toLowerCase()))
                .toList();
    }

    @Override
    public void rateContent(Long contentId, Integer rating) {
        System.out.println("[ContentRepository] Rating content " + contentId + " with rating: " + rating);
        findById(contentId).ifPresent(c -> {
            c.setRating(rating);
            update(c);
        });
    }
}
