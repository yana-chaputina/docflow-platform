package com.ipr.userservice.service;

import com.ipr.userservice.dto.user.CreateUserRequestDto;
import com.ipr.userservice.dto.user.UpdateUserRequestDto;
import com.ipr.userservice.dto.user.UserResponseDto;

import java.util.List;

public interface UserService {

    List<UserResponseDto> getUsers();

    UserResponseDto getUserById(Long id);

    UserResponseDto createUser(CreateUserRequestDto createUserRequestDto);

    UserResponseDto updateUser(UpdateUserRequestDto updateUserRequestDto, Long id);

    void deleteUser(Long id);

    List<UserResponseDto> getUsersWithRoleManager();
}
