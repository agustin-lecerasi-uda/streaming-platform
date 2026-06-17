package com.streaming.interfaces;

import com.streaming.models.User;
import java.util.List;
import java.util.Optional;

public interface UserManageable {
    User create(User user);
    Optional<User> findById(Long id);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    List<User> findAll();
    User update(User user);
    boolean delete(Long id);
    boolean verifyPassword(String email, String password);
    void updateSubscription(Long userId, String subscriptionPlan);
}
