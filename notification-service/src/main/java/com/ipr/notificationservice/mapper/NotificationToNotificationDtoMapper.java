package com.ipr.notificationservice.mapper;

import com.ipr.notificationservice.dto.NotificationDto;
import com.ipr.notificationservice.entity.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface NotificationToNotificationDtoMapper {

    NotificationDto notificationToNotificationDto(Notification notification);

    List<NotificationDto> notificationToNotificationDtoAsList(List<Notification> notifications);

}
