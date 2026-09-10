package com.ipr.userservice.controller;

import com.ipr.userservice.dto.user.CreateUserRequestDto;
import com.ipr.userservice.dto.user.UpdateUserRequestDto;
import com.ipr.userservice.dto.user.UserResponseDto;
import com.ipr.userservice.security.CustomUserDetails;
import com.ipr.userservice.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getUsers(){
        return ResponseEntity.ok(userService.getUsers());
    }


    @GetMapping("/managers")
    public ResponseEntity<List<UserResponseDto>> getUsersWithRoleManager(){
        return ResponseEntity.ok(userService.getUsersWithRoleManager());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody CreateUserRequestDto createUserRequestDto,
                                      BindingResult result){
        if(result.hasErrors()){
            throw new ValidationException(result.getAllErrors().getFirst().getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.createUser(createUserRequestDto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUserById(@Valid @RequestBody UpdateUserRequestDto updateUserRequestDto,
                                      @PathVariable Long id,
                                      BindingResult result){
        if(result.hasErrors()){
            throw new ValidationException(result.getAllErrors().getFirst().getDefaultMessage());
        }
        return ResponseEntity.ok(userService.updateUser(updateUserRequestDto,id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> me(Authentication authentication) {
        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(userService.getUserById(userDetails.getUserId()));
    }

}
