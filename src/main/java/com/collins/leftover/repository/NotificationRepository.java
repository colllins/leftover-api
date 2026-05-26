package com.collins.leftover.repository;

import com.collins.leftover.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findTop20ByUser_EmailOrderByCreatedAtDesc(String email);
}
