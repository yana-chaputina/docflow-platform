package com.ipr.userservice.mapper;

import com.ipr.userservice.dto.CreateUserRequestDto;
import com.ipr.userservice.dto.UpdateUserRequestDto;
import com.ipr.userservice.dto.UserResponseDto;
import com.ipr.userservice.entity.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserDTOEntityMapper {

    User createUserRequestDtoToUser(CreateUserRequestDto userDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User updateUserRequestDtoToUser(UpdateUserRequestDto userDTO,@MappingTarget User user);

    UserResponseDto userToUserResponseDto(User user);

    List<UserResponseDto> userToUserResponseDtoAsList(List<User> users);

}
