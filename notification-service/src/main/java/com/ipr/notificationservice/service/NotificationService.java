package com.ipr.notificationservice.service;


import com.ipr.notificationservice.dto.NotificationDto;

import java.util.List;

public interface NotificationService {
    public List<NotificationDto> getAllNotifications ();
    public List<NotificationDto> getAllNotificationsByUserId (Long userId);
}
