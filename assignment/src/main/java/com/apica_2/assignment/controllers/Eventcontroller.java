package com.apica_2.assignment.controllers;


import com.apica_2.assignment.dto.EventDto;
import com.apica_2.assignment.exception.CustomException;
import com.apica_2.assignment.response.ResponseModel;
import com.apica_2.assignment.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/apica")
public class Eventcontroller {

    @Autowired
    private EventService eventService;

    @GetMapping("/getEvent/{userId}")
    public ResponseModel getEventsUser(@PathVariable String userId, @RequestParam(required = false) String eventName) {
        try {
            List<EventDto> response;
            if (eventName == null) {
                 response = eventService.getEventsUser(userId);
            } else {
                 response = eventService.getEventsUserByEventType(userId, eventName);
            }
            return ResponseModel.success(HttpStatus.OK, "Success", response);
        } catch (Exception e) {
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/getAllEvents")
    public ResponseModel getAllEvents() {
        try {
            List<EventDto> response = eventService.getAllEvents();
            return ResponseModel.success(HttpStatus.OK, "Success", response);
        } catch (Exception e) {
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PutMapping("/update/Events")
    public ResponseModel updateEvents(@RequestParam String userId, @RequestBody EventDto eventDto) {
        try {
            return ResponseModel.success(HttpStatus.OK, "updated successfully", eventService.updateEvents(userId,eventDto));

        } catch (Exception e) {
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @DeleteMapping("/delete/Events")
    public ResponseModel deleteEvents(@RequestParam String userId) {
        try {
            return ResponseModel.success(HttpStatus.OK, "deleted successfully", eventService.deleteEvents(userId));
        } catch (Exception e) {
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
