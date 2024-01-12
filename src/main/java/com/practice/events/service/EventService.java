package com.practice.events.service;

import com.practice.events.exceptions.ResourceNotFoundException;
import com.practice.events.model.Event;
import com.practice.events.repository.EventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public Event saveEvent(Event event) {
        log.info("Event Service::saveEvent() method executed");
        return eventRepository.save(event);
    }

    public List<Event> getAllEvents() {
        log.info("Event Service::getAllEvents() method executed");
        return eventRepository.findAll();
    }

    public Event getEventById(Integer eventId) {
        Optional<Event> event = eventRepository.findById(eventId);
        if(event.isPresent()){
            return event.get();
        }else
//            throw new ResourceNotFoundException("Event","eventId",eventId);
        return eventRepository.findById(eventId).orElseThrow(()-> new ResourceNotFoundException("Event","eventId",eventId));
    }

    public String deleteEvent(Integer eventId) {
        eventRepository.findById(eventId).orElseThrow(()-> new ResourceNotFoundException("Event","eventId",eventId));
        eventRepository.deleteById(eventId);
        return "Event Deleted with:" + eventId;
    }

    public Event updateEvent(Integer eventId, Event event) {

    Event existingEvent = eventRepository.findById(eventId).orElseThrow(()-> new ResourceNotFoundException("Event","eventId",eventId));

            existingEvent.setEventDate(event.getEventDate());
            existingEvent.setEventName(event.getEventName());
            existingEvent.setEventDetails(event.getEventDetails());
            existingEvent.setTicketPrice(event.getTicketPrice());
            eventRepository.save(existingEvent);
        return existingEvent;
    }


    public Event updateEventByFields(Integer eventId, Map<String, Object> fields) {
        Optional<Event> existingEvent = eventRepository.findById(eventId);
        if (existingEvent.isPresent()) {
            fields.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(Event.class, key);
                field.setAccessible(true);
                ReflectionUtils.setField(field, existingEvent.get(), value);
            });

            return eventRepository.save(existingEvent.get());
        }

        return null;
    }

}
