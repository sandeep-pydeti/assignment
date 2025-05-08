package com.assignment.apica.service.Implementation;

import com.assignment.apica.dto.UserDto;
import com.assignment.apica.entity.Users;
import com.assignment.apica.mappers.UserMapper;
import com.assignment.apica.repository.UsersRepo;
import com.assignment.apica.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Override
    public UserDto create(UserDto userDto) {
        Users Req = UserMapper.mapToUsers(userDto);
        Users response = usersRepo.save(Req);
        kafkaTemplate.send("user-events",Constants.created+","+response.getId());
        return UserMapper.mapToUserDto(response);
    }

    @Override
    public UserDto getUser(String UserId){
        Users user = usersRepo.findById(UserId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        kafkaTemplate.send("user-events",Constants.getData+","+user.getId());
        return  UserMapper.mapToUserDto(user);
    }
    @Override
    public List<UserDto> getAllUsers(){
        List<Users> user = usersRepo.findAll();
        return user.stream().map(UserMapper::mapToUserDto).toList();
    }

    @Override
    public UserDto updateUsers(String userId,UserDto userDto){
        Users existingUser = usersRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User ID not found"));
        existingUser.setUsername(userDto.getUsername());
        existingUser.setEmail(userDto.getEmail());
        existingUser.setRole(userDto.getRole());
        existingUser.setPassword(userDto.getPassword());
        existingUser.setPhoneNumber(userDto.getPhoneNumber());
        Users updatedUser = usersRepo.save(existingUser);
        kafkaTemplate.send("user-events",Constants.updated+","+existingUser.getId());
        return UserMapper.mapToUserDto(updatedUser);
    }

    @Override
    public UserDto deleteUser(String userId){
        Users existingUser = usersRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User ID not found"));
        usersRepo.delete(existingUser);
        kafkaTemplate.send("user-events",Constants.deleted+","+existingUser.getId());
        return UserMapper.mapToUserDto(existingUser);
    }
}
