package com.streaming.controllers;

import com.streaming.interfaces.SubscriptionManageable;
import com.streaming.models.Subscription;
import java.util.List;
import java.util.Optional;

public class SubscriptionController {
    private final SubscriptionManageable subscriptionService;

    public SubscriptionController(SubscriptionManageable subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    public Optional<Subscription> getPlanByName(String plan) {
        System.out.println("\n=== [SubscriptionController] Getting subscription plan: " + plan + " ===");
        return subscriptionService.findByPlan(plan);
    }

    public List<Subscription> listAllPlans() {
        System.out.println("\n=== [SubscriptionController] Listing all subscription plans ===");
        List<Subscription> plans = subscriptionService.findAll();
        plans.forEach(p -> System.out.println("  - " + p));
        return plans;
    }

    public void displayPlanDetails(String plan) {
        System.out.println("\n=== [SubscriptionController] Plan Details: " + plan + " ===");
        subscriptionService.findByPlan(plan).ifPresentOrElse(
            p -> {
                System.out.println("Plan: " + p.getPlan());
                System.out.println("Price: $" + p.getPrice() + "/month");
                System.out.println("Max Devices: " + p.getMaxDevices());
                System.out.println("Max Quality: " + p.getMaxQuality());
                System.out.println("Ad Supported: " + p.getAdSupported());
            },
            () -> System.out.println("Plan not found")
        );
    }

    public boolean validateQualityForPlan(String plan, String quality) {
        System.out.println("\n=== [SubscriptionController] Validating quality ===");
        System.out.println("Plan: " + plan + ", Quality: " + quality);
        boolean isValid = subscriptionService.validatePlanForQuality(plan, quality);
        System.out.println("Valid: " + isValid);
        return isValid;
    }

    public boolean validateDeviceLimitForPlan(String plan, Integer currentDevices) {
        System.out.println("\n=== [SubscriptionController] Validating device limit ===");
        System.out.println("Plan: " + plan + ", Current Devices: " + currentDevices);
        boolean isValid = subscriptionService.validatePlanDeviceLimit(plan, currentDevices);
        System.out.println("Valid: " + isValid);
        return isValid;
    }
}
