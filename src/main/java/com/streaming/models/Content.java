package com.streaming.models;

import java.time.LocalDateTime;
import java.util.Objects;

public class Content {
    private Long id;
    private String title;
    private String description;
    private String genre;
    private String type;
    private Long duration;
    private String url;
    private Integer rating;
    private LocalDateTime releaseDate;
    private LocalDateTime createdAt;

    public Content() {}

    public Content(String title, String description, String genre, String type, Long duration, String url) {
        this.title = title;
        this.description = description;
        this.genre = genre;
        this.type = type;
        this.duration = duration;
        this.url = url;
        this.rating = 0;
        this.releaseDate = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Long getDuration() { return duration; }
    public void setDuration(Long duration) { this.duration = duration; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }

    public LocalDateTime getReleaseDate() { return releaseDate; }
    public void setReleaseDate(LocalDateTime releaseDate) { this.releaseDate = releaseDate; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Content content = (Content) o;
        return Objects.equals(id, content.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Content{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", type='" + type + '\'' +
                ", duration=" + duration +
                ", rating=" + rating +
                '}';
    }
}
