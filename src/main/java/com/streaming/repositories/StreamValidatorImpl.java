package com.streaming.repositories;

import com.streaming.interfaces.StreamValidator;
import com.streaming.models.Stream;

public class StreamValidatorImpl implements StreamValidator {

    @Override
    public boolean isQualityAvailable(String quality) {
        System.out.println("[StreamValidator] Checking if quality is available: " + quality);
        return quality.matches("SD|HD|FHD|4K");
    }

    @Override
    public boolean canStream(Long userId, String subscriptionPlan) {
        System.out.println("[StreamValidator] Checking if user " + userId + " can stream with plan: " + subscriptionPlan);
        return subscriptionPlan != null && !subscriptionPlan.isEmpty();
    }

    @Override
    public boolean validateQualityForPlan(String quality, String subscriptionPlan) {
        System.out.println("[StreamValidator] Validating quality " + quality + " for plan: " + subscriptionPlan);
        return switch (subscriptionPlan.toLowerCase()) {
            case "free" -> quality.equals("SD");
            case "basic" -> quality.matches("SD|HD");
            case "standard" -> quality.matches("SD|HD|FHD");
            case "premium" -> quality.matches("SD|HD|FHD|4K");
            default -> false;
        };
    }

    @Override
    public void validateStreamRequest(Long userId, Long contentId, String quality) {
        System.out.println("[StreamValidator] Validating stream request for user " + userId + 
                          ", content " + contentId + ", quality " + quality);
        if (!isQualityAvailable(quality)) {
            throw new IllegalArgumentException("Invalid quality: " + quality);
        }
    }

    @Override
    public Stream validateActiveStream(Long streamId) {
        System.out.println("[StreamValidator] Validating active stream: " + streamId);
        return new Stream();
    }
}
