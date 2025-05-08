package com.assignment.apica.service.Implementation;


import com.assignment.apica.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    UserDto create(UserDto userDto);
    UserDto getUser(String userId);
    List<UserDto>getAllUsers();
    UserDto updateUsers(String userId,UserDto userDto);
    UserDto deleteUser(String userId);
}
