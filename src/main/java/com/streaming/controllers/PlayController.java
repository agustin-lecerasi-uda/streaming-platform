package com.streaming.controllers;

import com.streaming.interfaces.StreamPlayable;
import com.streaming.interfaces.StreamValidator;
import com.streaming.interfaces.UserManageable;
import com.streaming.interfaces.ContentManageable;
import com.streaming.interfaces.SubscriptionManageable;
import com.streaming.interfaces.NotificationService;
import com.streaming.models.Stream;
import com.streaming.models.User;
import com.streaming.models.Content;
import java.util.List;
import java.util.Optional;

public class PlayController {
    private final StreamPlayable streamService;
    private final StreamValidator streamValidator;
    private final UserManageable userService;
    private final ContentManageable contentService;
    private final SubscriptionManageable subscriptionService;
    private final NotificationService notificationService;

    public PlayController(StreamPlayable streamService, 
                         StreamValidator streamValidator,
                         UserManageable userService,
                         ContentManageable contentService,
                         SubscriptionManageable subscriptionService,
                         NotificationService notificationService) {
        this.streamService = streamService;
        this.streamValidator = streamValidator;
        this.userService = userService;
        this.contentService = contentService;
        this.subscriptionService = subscriptionService;
        this.notificationService = notificationService;
    }

    public Stream playContent(Long userId, Long contentId, String requestedQuality, String deviceType) {
        System.out.println("\n=== [PlayController] Starting playback ===");
        System.out.println("User: " + userId + ", Content: " + contentId + 
                          ", Quality: " + requestedQuality + ", Device: " + deviceType);

        Optional<User> userOpt = userService.findById(userId);
        if (userOpt.isEmpty()) {
            System.out.println("[PlayController] User not found");
            notificationService.notifyError(userId, "User not found");
            throw new IllegalArgumentException("User not found");
        }

        User user = userOpt.get();
        Optional<Content> contentOpt = contentService.findById(contentId);
        if (contentOpt.isEmpty()) {
            System.out.println("[PlayController] Content not found");
            notificationService.notifyError(userId, "Content not found");
            throw new IllegalArgumentException("Content not found");
        }

        Content content = contentOpt.get();
        String userPlan = user.getSubscription();

        try {
            streamValidator.validateStreamRequest(userId, contentId, requestedQuality);
            streamValidator.validateQualityForPlan(requestedQuality, userPlan);
            streamService.validateUserStreamLimit(userId);

            String finalQuality = requestedQuality;
            if (!subscriptionService.validatePlanForQuality(userPlan, requestedQuality)) {
                System.out.println("[PlayController] Quality not available for plan, downgrading...");
                finalQuality = getMaxQualityForPlan(userPlan);
                notificationService.notifyQualityChange(userId, requestedQuality, finalQuality);
            }

            Stream stream = streamService.startStream(userId, contentId, finalQuality, deviceType);
            System.out.println("[PlayController] Playback started successfully");
            System.out.println("[PlayController] Stream ID: " + stream.getId() + 
                              ", Quality: " + finalQuality + ", Duration: " + content.getDuration() + "s");
            
            notificationService.notifyStreamStart(userId, content.getTitle());
            return stream;

        } catch (Exception e) {
            System.out.println("[PlayController] Error during playback: " + e.getMessage());
            notificationService.notifyError(userId, e.getMessage());
            throw e;
        }
    }

    public void pausePlayback(Long streamId) {
        System.out.println("\n=== [PlayController] Pausing stream: " + streamId + " ===");
        streamService.pauseStream(streamId);
        System.out.println("[PlayController] Playback paused");
    }

    public void resumePlayback(Long streamId) {
        System.out.println("\n=== [PlayController] Resuming stream: " + streamId + " ===");
        streamService.resumeStream(streamId);
        System.out.println("[PlayController] Playback resumed");
    }

    public void stopPlayback(Long streamId) {
        System.out.println("\n=== [PlayController] Stopping stream: " + streamId + " ===");
        streamService.stopStream(streamId);
        System.out.println("[PlayController] Playback stopped");
    }

    public Optional<Stream> getActiveStream(Long userId) {
        System.out.println("\n=== [PlayController] Getting active stream for user: " + userId + " ===");
        return streamService.findActiveStream(userId);
    }

    public List<Stream> getWatchHistory(Long userId) {
        System.out.println("\n=== [PlayController] Getting watch history for user: " + userId + " ===");
        return streamService.findStreamHistory(userId);
    }

    public void updateWatchProgress(Long streamId, Long watchedDuration) {
        System.out.println("\n=== [PlayController] Updating watch progress ===");
        System.out.println("Stream: " + streamId + ", Watched: " + watchedDuration + "s");
        streamService.updateWatchedDuration(streamId, watchedDuration);
    }

    private String getMaxQualityForPlan(String plan) {
        return switch (plan.toLowerCase()) {
            case "free" -> "SD";
            case "basic" -> "HD";
            case "standard" -> "FHD";
            case "premium" -> "4K";
            default -> "SD";
        };
    }
}
