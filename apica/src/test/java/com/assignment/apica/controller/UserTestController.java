package com.assignment.apica.controller;

import com.assignment.apica.controllers.usercontroller;
import com.assignment.apica.dto.UserDto;
import com.assignment.apica.exception.CustomException;
import com.assignment.apica.service.Implementation.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(usercontroller.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateUser() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setFullName("user1");
        userDto.setEmail("user1@email.com");
        userDto.setRole("USER");
        userDto.setUsername("user11");
        userDto.setPhoneNumber(1234567890);
        String json = objectMapper.writeValueAsString(userDto);
        mockMvc.perform(MockMvcRequestBuilders.post("/apica/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.message").value("registered successfully"))
                .andExpect(jsonPath("$.data.fullName").value("user1"));
    }

    @Test
    void testGetUser_Success() throws Exception {
        String userId = "123";
        UserDto userDto = new UserDto();
        userDto.setFullName("user1");
        userDto.setEmail("user1@email.com");
        userDto.setRole("USER");
        userDto.setUsername("user11");
        userDto.setPhoneNumber(1234567890);
        when(userService.getUser(userId)).thenReturn(userDto);
        mockMvc.perform(MockMvcRequestBuilders.get("/apica/user/getUser/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.message").value("registered successfully"))
                .andExpect(jsonPath("$.data.fullName").value("user1"))
                .andExpect(jsonPath("$.data.email").value("user1@email.com"))
                .andExpect(jsonPath("$.data.username").value("user11"))
                .andExpect(jsonPath("$.data.phoneNumber").value("1234567890"));

    }

    @Test
    void testGetUser_UserNotFound() throws Exception {
        String userId = "999";
        when(userService.getUser(userId)).thenThrow(new RuntimeException("User not found"));
        mockMvc.perform(get("/apica/user/getUser/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .andExpect(jsonPath("$.message").value("User not found"));
    }

    @Test
    void testGetAllUsers_Success() throws Exception {
        UserDto user1 = new UserDto();
        user1.setFullName("user1");
        user1.setEmail("user1@email.com");
        user1.setRole("USER");
        user1.setUsername("user11");
        user1.setPhoneNumber(1234567890);

        UserDto user2 = new UserDto();
        user2.setFullName("user2");
        user2.setEmail("user2@email.com");
        user2.setUsername("user22");
        user2.setRole("ADMIN");
        user2.setPhoneNumber(1234567800);

        List<UserDto> users = Arrays.asList(user1, user2);
        when(userService.getAllUsers()).thenReturn(users);
        mockMvc.perform(get("/apica/user/getAllUsers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data[0].fullName").value("user1"))
                .andExpect(jsonPath("$.data[0].email").value("user1@email.com"))
                .andExpect(jsonPath("$.data[1].fullName").value("user2"))
                .andExpect(jsonPath("$.data[1].email").value("user2@email.com"));
    }

    @Test
    void testGetAllUsers_Error() throws Exception {
        when(userService.getAllUsers()).thenThrow(new RuntimeException("Database error"));
        mockMvc.perform(get("/apica/user/getAllUsers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }

    @Test
    void testUpdateUser() throws Exception {
        String userId = "123";
        UserDto existiing = new UserDto();
        existiing.setFullName("user1");
        UserDto updated = new UserDto();
        updated.setFullName("user2");
        when(userService.updateUsers(eq(userId), any(UserDto.class))).thenReturn(updated);
        String jsonPayload = new ObjectMapper().writeValueAsString(existiing);
        mockMvc.perform(put("/apica/update/Users")
                        .param("userId", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("updated successfully"))
                .andExpect(jsonPath("$.data.fullName").value("updated"));
    }


    @Test
    void testUpdateUser_UserNotFound() throws Exception {
        String userId = "9999";
        UserDto existiing = new UserDto();
        existiing.setFullName("user1");
        UserDto updated = new UserDto();
        updated.setFullName("user2");
        String jsonPayload = new ObjectMapper().writeValueAsString(existiing);
        when(userService.updateUsers(eq(userId), any(UserDto.class)))
                .thenThrow(new CustomException(HttpStatus.NOT_FOUND, "User not found"));

        mockMvc.perform(put("/apica/update/Users")
                        .param("userId", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("UserId not found"));
    }
}
