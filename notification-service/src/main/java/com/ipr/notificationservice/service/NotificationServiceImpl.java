package com.ipr.notificationservice.service;

import com.ipr.notificationservice.dto.NotificationDto;
import com.ipr.notificationservice.mapper.NotificationToNotificationDtoMapper;
import com.ipr.notificationservice.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<NotificationDto> getAllNotifications (){
        return notificationToNotificationDtoMapper.notificationToNotificationDtoAsList(
                notificationRepository.findAll()
        );
    }

    @Override
    public List<NotificationDto> getAllNotificationsByUserId (Long userId){
        return notificationToNotificationDtoMapper.notificationToNotificationDtoAsList(
                notificationRepository.findByUserId(userId)
        );
    }
}
