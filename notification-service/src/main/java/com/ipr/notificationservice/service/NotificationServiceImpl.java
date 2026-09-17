package com.ipr.notificationservice.service;

import com.ipr.notificationservice.dto.NotificationDto;
import com.ipr.notificationservice.entity.Notification;
import com.ipr.notificationservice.entity.NotificationStatus;
import com.ipr.notificationservice.entity.NotificationType;
import com.ipr.notificationservice.event.OrderEvent;
import com.ipr.notificationservice.mapper.NotificationToNotificationDtoMapper;
import com.ipr.notificationservice.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationToNotificationDtoMapper notificationToNotificationDtoMapper;

    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository, NotificationToNotificationDtoMapper notificationToNotificationDtoMapper) {
        this.notificationRepository = notificationRepository;
        this.notificationToNotificationDtoMapper = notificationToNotificationDtoMapper;
    }

    @Override
    public List<NotificationDto> getAllNotifications() {
        return notificationToNotificationDtoMapper.notificationToNotificationDtoAsList(
                notificationRepository.findAll()
        );
    }

    @Override
    public List<NotificationDto> getAllNotificationsByUserId(Long userId) {
        return notificationToNotificationDtoMapper.notificationToNotificationDtoAsList(
                notificationRepository.findByUserId(userId)
        );
    }

    @Override
    public void saveNotificationFromEvent(OrderEvent orderEvent) {
        //send notification in-app
        notificationRepository.save(
                Notification.builder()
                        .userId(orderEvent.userId())
                        .orderId(orderEvent.orderId())
                        .type(NotificationType.IN_APP)
                        .content(orderEvent.message())
                        .status(NotificationStatus.SENT)
                        .sentAt(LocalDateTime.now())
                        .build());
    }
}
