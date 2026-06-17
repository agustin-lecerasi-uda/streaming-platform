package com.streaming.interfaces;

import com.streaming.models.Stream;
import java.util.List;
import java.util.Optional;

public interface StreamPlayable {
    Stream startStream(Long userId, Long contentId, String quality, String deviceType);
    void pauseStream(Long streamId);
    void resumeStream(Long streamId);
    void stopStream(Long streamId);
    Optional<Stream> findActiveStream(Long userId);
    List<Stream> findStreamHistory(Long userId);
    Stream updateWatchedDuration(Long streamId, Long duration);
    List<Stream> findStreamsByContent(Long contentId);
    void validateUserStreamLimit(Long userId);
}
