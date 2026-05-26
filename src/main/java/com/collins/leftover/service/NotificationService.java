package com.collins.leftover.service;


import com.collins.leftover.model.Notification;
import com.collins.leftover.model.User;
import com.collins.leftover.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public void createNotification(User user, String title, String message, boolean isRead){

        Notification notification = new Notification();
        notification.setUser(user);
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setRead(isRead);
        notification.setCreatedAt(LocalDateTime.now());

        notificationRepository.save(notification);
    }

    public List<Notification> getLatestNotifications(String email) {
        return notificationRepository.findTop20ByUser_EmailOrderByCreatedAtDesc(email);
    }
}
