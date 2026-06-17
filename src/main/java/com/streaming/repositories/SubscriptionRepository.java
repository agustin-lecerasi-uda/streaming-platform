package com.streaming.repositories;

import com.streaming.interfaces.SubscriptionManageable;
import com.streaming.models.Subscription;
import java.util.*;

public class SubscriptionRepository implements SubscriptionManageable {
    private Map<Long, Subscription> subscriptions = new HashMap<>();
    private Long nextId = 1L;

    public SubscriptionRepository() {
        initializeDefaultSubscriptions();
    }

    private void initializeDefaultSubscriptions() {
        create(new Subscription("Free", 0.0, 1, "SD", true));
        create(new Subscription("Basic", 9.99, 2, "HD", false));
        create(new Subscription("Standard", 15.99, 4, "FHD", false));
        create(new Subscription("Premium", 19.99, 6, "4K", false));
    }

    @Override
    public Subscription create(Subscription subscription) {
        subscription.setId(nextId++);
        subscriptions.put(subscription.getId(), subscription);
        System.out.println("[SubscriptionRepository] Subscription created: " + subscription);
        return subscription;
    }

    @Override
    public Optional<Subscription> findById(Long id) {
        System.out.println("[SubscriptionRepository] Finding subscription by ID: " + id);
        return Optional.ofNullable(subscriptions.get(id));
    }

    @Override
    public Optional<Subscription> findByPlan(String plan) {
        System.out.println("[SubscriptionRepository] Finding subscription by plan: " + plan);
        return subscriptions.values().stream()
                .filter(s -> s.getPlan().equalsIgnoreCase(plan))
                .findFirst();
    }

    @Override
    public List<Subscription> findAll() {
        System.out.println("[SubscriptionRepository] Finding all subscriptions");
        return new ArrayList<>(subscriptions.values());
    }

    @Override
    public Subscription update(Subscription subscription) {
        subscriptions.put(subscription.getId(), subscription);
        System.out.println("[SubscriptionRepository] Subscription updated: " + subscription);
        return subscription;
    }

    @Override
    public boolean delete(Long id) {
        boolean deleted = subscriptions.remove(id) != null;
        System.out.println("[SubscriptionRepository] Subscription deleted: " + id + " - Success: " + deleted);
        return deleted;
    }

    @Override
    public boolean validatePlanForQuality(String plan, String quality) {
        System.out.println("[SubscriptionRepository] Validating plan " + plan + " for quality: " + quality);
        return findByPlan(plan)
                .map(s -> isQualityValid(s.getMaxQuality(), quality))
                .orElse(false);
    }

    @Override
    public boolean validatePlanDeviceLimit(String plan, Integer currentDevices) {
        System.out.println("[SubscriptionRepository] Validating device limit for plan: " + plan);
        return findByPlan(plan)
                .map(s -> currentDevices < s.getMaxDevices())
                .orElse(false);
    }

    private boolean isQualityValid(String maxQuality, String requestedQuality) {
        Map<String, Integer> qualityRank = Map.of(
                "SD", 1,
                "HD", 2,
                "FHD", 3,
                "4K", 4
        );
        Integer maxRank = qualityRank.getOrDefault(maxQuality, 0);
        Integer requestedRank = qualityRank.getOrDefault(requestedQuality, 0);
        return requestedRank <= maxRank;
    }
}
