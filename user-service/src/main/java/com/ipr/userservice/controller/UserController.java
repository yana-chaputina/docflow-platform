package com.ipr.userservice.controller;

import com.ipr.userservice.dto.CreateUserRequestDto;
import com.ipr.userservice.dto.UpdateUserRequestDto;
import com.ipr.userservice.dto.UserResponseDto;
import com.ipr.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public List<UserResponseDto> getUsers(){
        return userService.getUsers();
    }

    @PostMapping
    public UserResponseDto createUser(@RequestBody CreateUserRequestDto createUserRequestDto){
        return userService.createUser(createUserRequestDto);
    }

    @PostMapping("/{id}")
    public UserResponseDto updateUser(@RequestBody UpdateUserRequestDto updateUserRequestDto,
                                      @PathVariable Long id){
        return userService.updateUser(updateUserRequestDto,id);
    }
}
