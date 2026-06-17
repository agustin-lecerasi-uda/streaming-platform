package com.streaming.repositories;

import com.streaming.interfaces.StreamPlayable;
import com.streaming.models.Stream;
import java.time.LocalDateTime;
import java.util.*;

public class StreamRepository implements StreamPlayable {
    private Map<Long, Stream> streams = new HashMap<>();
    private Long nextId = 1L;

    @Override
    public Stream startStream(Long userId, Long contentId, String quality, String deviceType) {
        Stream stream = new Stream(userId, contentId, quality, deviceType);
        stream.setId(nextId++);
        stream.setStartTime(LocalDateTime.now());
        streams.put(stream.getId(), stream);
        System.out.println("[StreamRepository] Stream started: " + stream);
        return stream;
    }

    @Override
    public void pauseStream(Long streamId) {
        System.out.println("[StreamRepository] Stream paused: " + streamId);
    }

    @Override
    public void resumeStream(Long streamId) {
        System.out.println("[StreamRepository] Stream resumed: " + streamId);
    }

    @Override
    public void stopStream(Long streamId) {
        findStreamById(streamId).ifPresent(s -> {
            s.setEndTime(LocalDateTime.now());
            System.out.println("[StreamRepository] Stream stopped: " + streamId);
        });
    }

    @Override
    public Optional<Stream> findActiveStream(Long userId) {
        System.out.println("[StreamRepository] Finding active stream for user: " + userId);
        return streams.values().stream()
                .filter(s -> s.getUserId().equals(userId) && s.getEndTime() == null)
                .findFirst();
    }

    @Override
    public List<Stream> findStreamHistory(Long userId) {
        System.out.println("[StreamRepository] Finding stream history for user: " + userId);
        return streams.values().stream()
                .filter(s -> s.getUserId().equals(userId))
                .toList();
    }

    @Override
    public Stream updateWatchedDuration(Long streamId, Long duration) {
        System.out.println("[StreamRepository] Updating watched duration for stream: " + streamId + " - Duration: " + duration);
        return findStreamById(streamId).map(s -> {
            s.setWatchedDuration(duration);
            return s;
        }).orElse(null);
    }

    @Override
    public List<Stream> findStreamsByContent(Long contentId) {
        System.out.println("[StreamRepository] Finding streams by content: " + contentId);
        return streams.values().stream()
                .filter(s -> s.getContentId().equals(contentId))
                .toList();
    }

    @Override
    public void validateUserStreamLimit(Long userId) {
        System.out.println("[StreamRepository] Validating stream limit for user: " + userId);
    }

    private Optional<Stream> findStreamById(Long streamId) {
        return Optional.ofNullable(streams.get(streamId));
    }
}
