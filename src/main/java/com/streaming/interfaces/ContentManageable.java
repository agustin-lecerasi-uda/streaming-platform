package com.streaming.interfaces;

import com.streaming.models.Content;
import java.util.List;
import java.util.Optional;

public interface ContentManageable {
    Content create(Content content);
    Optional<Content> findById(Long id);
    List<Content> findByGenre(String genre);
    List<Content> findByType(String type);
    List<Content> findAll();
    Content update(Content content);
    boolean delete(Long id);
    List<Content> search(String query);
    void rateContent(Long contentId, Integer rating);
}
