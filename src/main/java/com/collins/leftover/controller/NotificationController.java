package com.collins.leftover.controller;

import com.collins.leftover.model.Notification;
import com.collins.leftover.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping
    public List<Notification> getNotifications() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return notificationService.getLatestNotifications(email);
    }
}
