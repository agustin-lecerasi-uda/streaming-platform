package com.streaming.utils;

import com.streaming.repositories.*;
import com.streaming.interfaces.*;

public class DependencyInjector {
    private static final UserManageable userService = new UserRepository();
    private static final ContentManageable contentService = new ContentRepository();
    private static final StreamPlayable streamService = new StreamRepository();
    private static final SubscriptionManageable subscriptionService = new SubscriptionRepository();
    private static final StreamValidator streamValidator = new StreamValidatorImpl();
    private static final NotificationService notificationService = new ConsoleNotificationService();

    public static UserManageable getUserService() {
        return userService;
    }

    public static ContentManageable getContentService() {
        return contentService;
    }

    public static StreamPlayable getStreamService() {
        return streamService;
    }

    public static SubscriptionManageable getSubscriptionService() {
        return subscriptionService;
    }

    public static StreamValidator getStreamValidator() {
        return streamValidator;
    }

    public static NotificationService getNotificationService() {
        return notificationService;
    }
}
