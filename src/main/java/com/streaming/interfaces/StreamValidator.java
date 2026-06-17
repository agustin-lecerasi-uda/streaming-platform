package com.streaming.interfaces;

import com.streaming.models.Stream;

public interface StreamValidator {
    boolean isQualityAvailable(String quality);
    boolean canStream(Long userId, String subscriptionPlan);
    boolean validateQualityForPlan(String quality, String subscriptionPlan);
    void validateStreamRequest(Long userId, Long contentId, String quality);
    Stream validateActiveStream(Long streamId);
}
