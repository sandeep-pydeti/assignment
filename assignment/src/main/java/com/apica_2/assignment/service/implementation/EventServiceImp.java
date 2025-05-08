package com.apica_2.assignment.service.implementation;

import com.apica_2.assignment.dto.EventDto;
import com.apica_2.assignment.entity.Events;
import com.apica_2.assignment.mapper.EventMapper;
import com.apica_2.assignment.repository.EventRepo;
import com.apica_2.assignment.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImp implements EventService {

    @Autowired
    private EventRepo eventRepo;


    @Override
    public List<EventDto> getAllEvents(){
        List<Events> event = eventRepo.findAll();
        return event.stream().map(EventMapper::mapToEventDto).toList();
    }

    @Override
    public List<EventDto> getEventsUser(String userId){
        List<Events> event = eventRepo.findAllByUserId(userId);
        return event.stream().map(EventMapper::mapToEventDto).toList();
    }

    @Override
    public List<EventDto> getEventsUserByEventType(String userId,String eventName){
        List<Events> event = eventRepo.findAllByUserIdAndEventName(userId,eventName);
        return event.stream().map(EventMapper::mapToEventDto).toList();
    }

    @Override
    public EventDto updateEvents(String userId, EventDto eventDto) {
        Events existingEvent = eventRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User ID not found"));
        existingEvent.setEventName(eventDto.getEventName());
        existingEvent.setUserId(eventDto.getUserId());
        Events updatedEvent = eventRepo.save(existingEvent);
        return EventMapper.mapToEventDto(updatedEvent);
    }

    @Override
    public EventDto deleteEvents(String userId) {
        Events existingEvent = eventRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User ID not found"));
        eventRepo.delete(existingEvent);
        return EventMapper.mapToEventDto(existingEvent);
    }
}
