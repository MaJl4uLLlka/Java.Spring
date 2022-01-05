package com.example.mainspingproject;

import com.example.mainspingproject.dto.RegisterRequestDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class IntegrationTests {

    @Autowired
    private MockMvc mvc;

    public MockHttpServletRequestBuilder postJson(String uri, Object body) {
        try {
            String json = new ObjectMapper().writeValueAsString(body);
            return  post(uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public MockHttpServletRequestBuilder deleteJson(String uri, Object body) {
        try {
            String json = new ObjectMapper().writeValueAsString(body);
            return  delete(uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getMeetups() throws Exception {
        this.mvc.perform(get("/meetups/1"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void createMeetup() throws Exception{
        RegisterRequestDTO requestDTO = new RegisterRequestDTO();
        requestDTO.setEmail("user2222@mail.com");
        requestDTO.setFirst_name("Grishin");
        requestDTO.setLast_name("Ivan");
        requestDTO.setPassword("jdjkfjkdj222");

        this.mvc.perform(postJson("/auth/register", requestDTO))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
