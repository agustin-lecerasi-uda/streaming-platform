package com.streaming.repositories;

import com.streaming.interfaces.NotificationService;

public class ConsoleNotificationService implements NotificationService {

    @Override
    public void notifyStreamStart(Long userId, String contentTitle) {
        System.out.println("[Notification] User " + userId + " started streaming: " + contentTitle);
    }

    @Override
    public void notifyStreamEnd(Long userId, String contentTitle) {
        System.out.println("[Notification] User " + userId + " stopped streaming: " + contentTitle);
    }

    @Override
    public void notifyQualityChange(Long userId, String oldQuality, String newQuality) {
        System.out.println("[Notification] User " + userId + " changed quality from " + oldQuality + " to " + newQuality);
    }

    @Override
    public void notifySubscriptionChange(Long userId, String newPlan) {
        System.out.println("[Notification] User " + userId + " subscribed to plan: " + newPlan);
    }

    @Override
    public void notifyError(Long userId, String errorMessage) {
        System.out.println("[Notification] Error for user " + userId + ": " + errorMessage);
    }
}
