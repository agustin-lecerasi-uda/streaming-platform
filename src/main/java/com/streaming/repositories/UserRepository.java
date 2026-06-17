package com.streaming.repositories;

import com.streaming.interfaces.UserManageable;
import com.streaming.models.User;
import java.util.*;

public class UserRepository implements UserManageable {
    private Map<Long, User> users = new HashMap<>();
    private Long nextId = 1L;

    @Override
    public User create(User user) {
        user.setId(nextId++);
        users.put(user.getId(), user);
        System.out.println("[UserRepository] User created: " + user);
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        System.out.println("[UserRepository] Finding user by ID: " + id);
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public Optional<User> findByUsername(String username) {
        System.out.println("[UserRepository] Finding user by username: " + username);
        return users.values().stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        System.out.println("[UserRepository] Finding user by email: " + email);
        return users.values().stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst();
    }

    @Override
    public List<User> findAll() {
        System.out.println("[UserRepository] Finding all users");
        return new ArrayList<>(users.values());
    }

    @Override
    public User update(User user) {
        users.put(user.getId(), user);
        System.out.println("[UserRepository] User updated: " + user);
        return user;
    }

    @Override
    public boolean delete(Long id) {
        boolean deleted = users.remove(id) != null;
        System.out.println("[UserRepository] User deleted: " + id + " - Success: " + deleted);
        return deleted;
    }

    @Override
    public boolean verifyPassword(String email, String password) {
        System.out.println("[UserRepository] Verifying password for: " + email);
        return findByEmail(email)
                .map(u -> u.getPassword().equals(password))
                .orElse(false);
    }

    @Override
    public void updateSubscription(Long userId, String subscriptionPlan) {
        System.out.println("[UserRepository] Updating subscription for user " + userId + " to " + subscriptionPlan);
        findById(userId).ifPresent(u -> {
            u.setSubscription(subscriptionPlan);
            update(u);
        });
    }
}
