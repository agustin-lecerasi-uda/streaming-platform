package com.streaming.controllers;

import com.streaming.interfaces.UserManageable;
import com.streaming.interfaces.NotificationService;
import com.streaming.models.User;
import java.util.Optional;

public class UserController {
    private final UserManageable userService;
    private final NotificationService notificationService;

    public UserController(UserManageable userService, NotificationService notificationService) {
        this.userService = userService;
        this.notificationService = notificationService;
    }

    public User registerUser(String username, String email, String password) {
        System.out.println("\n=== [UserController] Registering user: " + username + " ===");
        User newUser = new User(username, email, password, "Free");
        User createdUser = userService.create(newUser);
        System.out.println("[UserController] User successfully registered with ID: " + createdUser.getId());
        return createdUser;
    }

    public Optional<User> getUserById(Long id) {
        System.out.println("\n=== [UserController] Getting user by ID: " + id + " ===");
        return userService.findById(id);
    }

    public Optional<User> authenticateUser(String email, String password) {
        System.out.println("\n=== [UserController] Authenticating user: " + email + " ===");
        if (userService.verifyPassword(email, password)) {
            System.out.println("[UserController] Authentication successful");
            return userService.findByEmail(email);
        }
        System.out.println("[UserController] Authentication failed");
        return Optional.empty();
    }

    public void upgradeSubscription(Long userId, String newPlan) {
        System.out.println("\n=== [UserController] Upgrading subscription for user: " + userId + " ===");
        userService.updateSubscription(userId, newPlan);
        userService.findById(userId).ifPresent(u ->
            notificationService.notifySubscriptionChange(userId, newPlan)
        );
        System.out.println("[UserController] Subscription upgraded to: " + newPlan);
    }

    public void listAllUsers() {
        System.out.println("\n=== [UserController] Listing all users ===");
        userService.findAll().forEach(u -> System.out.println(u));
    }

    public boolean deleteUser(Long id) {
        System.out.println("\n=== [UserController] Deleting user: " + id + " ===");
        return userService.delete(id);
    }
}
