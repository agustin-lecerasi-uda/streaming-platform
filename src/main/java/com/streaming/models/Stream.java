package com.streaming.models;

import java.time.LocalDateTime;
import java.util.Objects;

public class Stream {
    private Long id;
    private Long userId;
    private Long contentId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long watchedDuration;
    private String quality;
    private String deviceType;
    private String location;

    public Stream() {}

    public Stream(Long userId, Long contentId, String quality, String deviceType) {
        this.userId = userId;
        this.contentId = contentId;
        this.startTime = LocalDateTime.now();
        this.quality = quality;
        this.deviceType = deviceType;
        this.watchedDuration = 0L;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getContentId() { return contentId; }
    public void setContentId(Long contentId) { this.contentId = contentId; }

    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }

    public Long getWatchedDuration() { return watchedDuration; }
    public void setWatchedDuration(Long watchedDuration) { this.watchedDuration = watchedDuration; }

    public String getQuality() { return quality; }
    public void setQuality(String quality) { this.quality = quality; }

    public String getDeviceType() { return deviceType; }
    public void setDeviceType(String deviceType) { this.deviceType = deviceType; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stream stream = (Stream) o;
        return Objects.equals(id, stream.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Stream{" +
                "id=" + id +
                ", userId=" + userId +
                ", contentId=" + contentId +
                ", quality='" + quality + '\'' +
                ", watchedDuration=" + watchedDuration +
                '}';
    }
}
