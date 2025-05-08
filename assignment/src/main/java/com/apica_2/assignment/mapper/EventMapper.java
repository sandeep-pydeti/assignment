package com.apica_2.assignment.mapper;


import com.apica_2.assignment.dto.EventDto;
import com.apica_2.assignment.entity.Events;

public class EventMapper {
    public static Events mapToEvents(EventDto eventDto){
        Events events = new Events();
        events.setUserId(eventDto.getUserId());
        events.setEventName(eventDto.getEventName());
        return events;
    }

    public static EventDto mapToEventDto(Events events){
        EventDto eventDto = new EventDto();
        eventDto.setEventName(events.getEventName());
        eventDto.setUserId(events.getUserId());
        return eventDto;
    }
}
