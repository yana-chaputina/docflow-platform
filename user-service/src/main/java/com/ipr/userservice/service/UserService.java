package com.ipr.userservice.service;

import com.ipr.userservice.dto.CreateUserRequestDto;
import com.ipr.userservice.dto.UpdateUserRequestDto;
import com.ipr.userservice.dto.UserResponseDto;
import com.ipr.userservice.entity.Role;
import com.ipr.userservice.entity.User;
import com.ipr.userservice.entity.UserStatus;
import com.ipr.userservice.mapper.UserDTOEntityMapper;
import com.ipr.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserDTOEntityMapper userDTOEntityMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, UserDTOEntityMapper userDTOEntityMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userDTOEntityMapper = userDTOEntityMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserResponseDto> getUsers() {
        List<User> users = userRepository.findAll();
        return userDTOEntityMapper.userToUserResponseDtoAsList(users);
    }

    public UserResponseDto createUser(CreateUserRequestDto createUserRequestDto) {
        if (userRepository.existsByEmail(createUserRequestDto.email())) {
            throw new RuntimeException("Email already exists");
        }
        User user = userDTOEntityMapper.createUserRequestDtoToUser(createUserRequestDto);
        user.setPasswordHash(passwordEncoder.encode(createUserRequestDto.password()));
        user.setRole(Role.USER);
        user.setStatus(UserStatus.ACTIVE);
        user = userRepository.save(user);
        return userDTOEntityMapper.userToUserResponseDto(user);
    }

    public UserResponseDto updateUser(UpdateUserRequestDto updateUserRequestDto, Long id) {
        User user=userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.save(userDTOEntityMapper
                .updateUserRequestDtoToUser(updateUserRequestDto,user));
        return userDTOEntityMapper.userToUserResponseDto(user);
    }
}
