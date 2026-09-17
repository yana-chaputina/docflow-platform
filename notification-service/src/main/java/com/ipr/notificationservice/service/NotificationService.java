package com.ipr.notificationservice.service;


import com.ipr.notificationservice.dto.NotificationDto;
import com.ipr.notificationservice.event.OrderEvent;

import java.util.List;

public interface NotificationService {
    public List<NotificationDto> getAllNotifications ();
    public List<NotificationDto> getAllNotificationsByUserId (Long userId);
    public void saveNotificationFromEvent (OrderEvent orderEvent);
}
