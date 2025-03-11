package ru.akhilko.coworkingtest.controller;

import org.json.JSONException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.akhilko.coworkingtest.CoworkingTestApplication;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@Testcontainers
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = CoworkingTestApplication.class)
@AutoConfigureMockMvc
@ContextConfiguration(initializers = {ReservationControllerITest.Initializer.class})
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
@Disabled
class ReservationControllerITest {
    private static final String REQUEST = """ 
              {
                "roomId": 10,
                "from": "2025-02-11T11:40:17.785Z",
                "to": "2025-02-11T11:40:17.785Z"
              }""";

    @Autowired
    private MockMvc mvc;

    @Container
    private static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
            .withReuse(true)
            .withDatabaseName("mydatabase");

    @Test
    void create() throws Exception {
        mvc.perform(post("/api/reservation").contentType(MediaType.APPLICATION_JSON).content(REQUEST))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "SPRING_DATASOURCE_USERNAME=" + postgreSQLContainer.getUsername(),
                    "SPRING_DATASOURCE_PASSWORD=" + postgreSQLContainer.getPassword(),
                    "SPRING_DATASOURCE_URL=" + postgreSQLContainer.getJdbcUrl()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }
}