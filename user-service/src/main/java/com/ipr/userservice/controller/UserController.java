package com.ipr.userservice.controller;

import com.ipr.userservice.dto.CreateUserRequestDto;
import com.ipr.userservice.dto.UpdateUserRequestDto;
import com.ipr.userservice.dto.UserResponseDto;
import com.ipr.userservice.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
    public ResponseEntity<List<UserResponseDto>> getUsers(){
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody CreateUserRequestDto createUserRequestDto,
                                      BindingResult result){
        if(result.hasErrors()){
            throw new ValidationException(result.getAllErrors().getFirst().getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.createUser(createUserRequestDto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUserById(@Valid @RequestBody UpdateUserRequestDto updateUserRequestDto,
                                      @PathVariable Long id,
                                      BindingResult result){
        if(result.hasErrors()){
            throw new ValidationException(result.getAllErrors().getFirst().getDefaultMessage());
        }
        return ResponseEntity.ok(userService.updateUser(updateUserRequestDto,id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
