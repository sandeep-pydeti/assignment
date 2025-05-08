package com.assignment.apica.controllers;

import com.assignment.apica.dto.UserDto;
import com.assignment.apica.exception.CustomException;
import com.assignment.apica.response.ResponseModel;
import com.assignment.apica.service.Implementation.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apica")
public class usercontroller {

    @Autowired
    private UserService userService;

    @PostMapping("/user/register")
    public ResponseModel createLocation(@Valid @RequestBody UserDto userDto){
        try {
            return ResponseModel.success(HttpStatus.OK, "registered successfully", userService.create(userDto));
        } catch (Exception e) {
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
    @GetMapping("/user/getUser/{userId}")
    public ResponseModel getUser(@PathVariable String userId){
        try {
            return ResponseModel.success(HttpStatus.OK, "Success", userService.getUser(userId));
        } catch (Exception e) {
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
    @GetMapping("/getAllUsers")
    public ResponseModel getAllUsers() {
        try {
            List<UserDto> response = userService.getAllUsers();
            return ResponseModel.success(HttpStatus.OK, "Success", response);
        } catch (Exception e) {
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
    @PutMapping("/update/Users")
    public ResponseModel updateUsers(@RequestParam String userId,@RequestBody UserDto userDto){
        try{
            return ResponseModel.success(HttpStatus.OK, "updated successfully", userService.updateUsers(userId,userDto));

        } catch (Exception e) {
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
    @DeleteMapping("/delete/user")
    public ResponseModel deleteUser(@RequestParam String userId){
        try{
            return ResponseModel.success(HttpStatus.OK, "deleted successfully", userService.deleteUser(userId));
        } catch (Exception e) {
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
