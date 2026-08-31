package com.ipr.userservice.service;

import com.ipr.userservice.dto.CreateUserRequestDto;
import com.ipr.userservice.dto.UpdateUserRequestDto;
import com.ipr.userservice.dto.UserResponseDto;

import java.util.List;

public interface UserService {

    public List<UserResponseDto> getUsers();

    public UserResponseDto getUserById(Long id);

    public UserResponseDto createUser(CreateUserRequestDto createUserRequestDto);

    public UserResponseDto updateUser(UpdateUserRequestDto updateUserRequestDto, Long id);

    public void deleteUser(Long id);
}
