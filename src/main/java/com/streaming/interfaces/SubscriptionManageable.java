package com.streaming.interfaces;

import com.streaming.models.Subscription;
import java.util.List;
import java.util.Optional;

public interface SubscriptionManageable {
    Subscription create(Subscription subscription);
    Optional<Subscription> findById(Long id);
    Optional<Subscription> findByPlan(String plan);
    List<Subscription> findAll();
    Subscription update(Subscription subscription);
    boolean delete(Long id);
    boolean validatePlanForQuality(String plan, String quality);
    boolean validatePlanDeviceLimit(String plan, Integer currentDevices);
}
