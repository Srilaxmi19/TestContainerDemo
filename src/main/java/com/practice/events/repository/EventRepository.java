package com.practice.events.repository;

import com.practice.events.model.Event;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EventRepository extends JpaRepository<Event,Integer>{
}
