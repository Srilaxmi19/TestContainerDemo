package com.practice.events.controller;

import com.practice.events.model.Event;
import com.practice.events.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/event")
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class EventController {

    @Autowired
    private EventService eventService;
    @PostMapping("/add")
    public ResponseEntity<Event> addEvent(@RequestBody Event event) {
        log.info("Event Controller::addEvent() method executed");
        return new ResponseEntity<Event>( eventService.saveEvent(event),HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Event>>getAllEvents(){
        log.info("Event Controller::getAllEvents() method executed");
        return new ResponseEntity<>(eventService.getAllEvents(),HttpStatus.OK);

    }

    @GetMapping("/{eventId}")
    public ResponseEntity<Event> getEvent(@PathVariable Integer eventId){
        return new ResponseEntity<>(eventService.getEventById(eventId),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable("id") Integer eventId){
        eventService.deleteEvent(eventId);
        return new ResponseEntity<String>("Record Deleted",HttpStatus.OK);
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<Event> updateEvent(@PathVariable Integer eventId,@RequestBody Event event){
       return new ResponseEntity<Event>(eventService.updateEvent(eventId,event),HttpStatus.OK);

    }

    @PatchMapping("/updateFields/{eventId}")
    public ResponseEntity<Event> updateEventByFields(@PathVariable Integer eventId,@RequestBody Map<String,Object> fields){
        return new ResponseEntity<Event>(eventService.updateEventByFields(eventId,fields),HttpStatus.OK);
    }

}
