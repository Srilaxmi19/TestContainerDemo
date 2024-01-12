package com.practice.events.model;




import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer eventId;
    private String eventName;
    private String eventDetails;
    @JsonFormat(pattern = "MM-dd-yyyy HH:mm:ss")
    private Date eventDate;
    private double ticketPrice;
    
    
    

}
