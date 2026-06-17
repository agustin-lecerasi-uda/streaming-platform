package com.streaming.interfaces;

public interface NotificationService {
    void notifyStreamStart(Long userId, String contentTitle);
    void notifyStreamEnd(Long userId, String contentTitle);
    void notifyQualityChange(Long userId, String oldQuality, String newQuality);
    void notifySubscriptionChange(Long userId, String newPlan);
    void notifyError(Long userId, String errorMessage);
}
