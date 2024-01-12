package com.practice.events;

import com.practice.events.model.Event;
import com.practice.events.repository.EventRepository;
import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.containers.MySQLContainer;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EventManagementApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	EventRepository eventRepository;

	static MySQLContainer mySQLContainer = new MySQLContainer("mysql:latest");

	@DynamicPropertySource
	static void configureProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
		registry.add("spring.datasource.username", mySQLContainer::getUsername);
		registry.add("spring.datasource.password", mySQLContainer::getPassword);
	}


	@BeforeAll
	static void beforeAll() {
		mySQLContainer.start();
	}

	@AfterAll
	static void afterAll() {
		mySQLContainer.stop();
	}


	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders
				.standaloneSetup(Event.class)
				.build();
	}

	@Test
	public void addNewEventTest() throws Exception {
		//build request body
		SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
		String date_string = "01-08-2024 11:58:10";
		Date date = formatter.parse(date_string);

		Event event = Event.builder()
				.eventName("Christmas")
				.eventDetails("Christmas Gift Exchange")
				.eventDate(date)
				.ticketPrice(100).build();
		String eventString = objectMapper.writeValueAsString(event);
		//call controller endpoints
		mockMvc.perform(MockMvcRequestBuilders
						.post("/api/v1/event/add")
						.contentType("application/json")
						.content(eventString)
						.accept("application/json"))
				.andExpect(status().isCreated());
		//.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
		//Assertions.assertEquals(1, eventRepository.findAll().size());

	}

	@Test
	public void getAllTheEventsTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
						.get("/api/v1/event/all")
						.accept("application/json")
						.contentType("application/json"))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.*").exists());
				//.andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").value(1));
	}



}
