package com.apica_2.assignment.service;

import com.apica_2.assignment.dto.EventDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EventService {

    List<EventDto>getEventsUser(String UserId);
    List<EventDto>getEventsUserByEventType(String UserId,String eventName);
    List<EventDto>getAllEvents();
    EventDto updateEvents(String userId,EventDto eventDto);
    EventDto deleteEvents(String userId);


}
