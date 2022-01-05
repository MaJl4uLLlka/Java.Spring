package com.example.mainspingproject;

import com.example.mainspingproject.dto.AuthenticationRequestDTO;
import com.example.mainspingproject.dto.RegisterRequestDTO;
import com.example.mainspingproject.entity.User;
import com.example.mainspingproject.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class JUnitControllerUserTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired ObjectMapper objectMapper;

    @Test
    public void checkStatusForUnknowUser() throws Exception{
        AuthenticationRequestDTO requestDTO = new AuthenticationRequestDTO();
        requestDTO.setEmail("user222@mail.com");
        requestDTO.setPassword("2234455");
        mockMvc.perform(
                        post("/auth/login")
                                .content(objectMapper.writeValueAsString(requestDTO))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isForbidden());
    }

    @Test
    public void tryRegisterExistUser() throws Exception{
        RegisterRequestDTO requestDTO = new RegisterRequestDTO();
        requestDTO.setEmail("user@mail.com");
        requestDTO.setPassword("skdsdl23");
        requestDTO.setFirst_name("Ivan");
        requestDTO.setLast_name("Grishin");

        mockMvc.perform(
                        post("/auth/register")
                                .content(objectMapper.writeValueAsString(requestDTO))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    public void checkLogout() throws Exception {
        mockMvc.perform(
                        get("/auth/logout")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void checkValidation() throws Exception{
        RegisterRequestDTO requestDTO = new RegisterRequestDTO();
        requestDTO.setEmail("111user@mail.com");
        requestDTO.setPassword("skdsdl23");
        requestDTO.setFirst_name("Ivan");
        requestDTO.setLast_name("Grishin");

        mockMvc.perform(
                        post("/auth/register")
                                .content(objectMapper.writeValueAsString(requestDTO))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    public void checkNotFoundException() throws Exception{
        RegisterRequestDTO requestDTO = new RegisterRequestDTO();
        requestDTO.setEmail("111user@mail.com");
        requestDTO.setPassword("skdsdl23");
        requestDTO.setFirst_name("Ivan");
        requestDTO.setLast_name("Grishin");

        mockMvc.perform(
                        post("/auth/registereee")
                                .content(objectMapper.writeValueAsString(requestDTO))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound());
    }
}
