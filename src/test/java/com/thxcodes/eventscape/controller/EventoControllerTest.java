package com.thxcodes.eventscape.controller;

import com.thxcodes.eventscape.model.Evento;
import com.thxcodes.eventscape.repository.EventoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(EventoController.class)
public class EventoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventoRepository eventoRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllEventos() throws Exception {
        Evento evento1 = new Evento(1L, "Evento 1", LocalDateTime.now(), "Local 1", "Descrição 1");
        Evento evento2 = new Evento(2L, "Evento 2", LocalDateTime.now(), "Local 2", "Descrição 2");
        List<Evento> eventos = Arrays.asList(evento1, evento2);

        when(eventoRepository.findAll()).thenReturn(eventos);

        mockMvc.perform(get("/api/eventos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Evento 1"))
                .andExpect(jsonPath("$[1].nome").value("Evento 2"));
    }

    @Test
    public void testHealthCheck() throws Exception {
        mockMvc.perform(get("/api/eventos/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("API is running!"));
    }

    @Test
    public void testGetEventoById() throws Exception {
        Evento evento = new Evento(1L, "Evento 1", LocalDateTime.now(), "Local 1", "Descrição 1");

        when(eventoRepository.findById(1L)).thenReturn(Optional.of(evento));

        mockMvc.perform(get("/api/eventos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Evento 1"));
    }

    @Test
    public void testGetEventoByIdNotFound() throws Exception {
        when(eventoRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/eventos/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateEvento() throws Exception {
        Evento evento = new Evento(1L, "Evento 1", LocalDateTime.of(2024, 6, 1, 12, 0), "Local 1", "Descrição 1");

        when(eventoRepository.save(any(Evento.class))).thenReturn(evento);

        mockMvc.perform(post("/api/eventos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"Evento 1\", \"dataHoraEvento\":\"2024-06-01T12:00:00\", \"local\":\"Local 1\", \"descricao\":\"Descrição 1\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Evento 1"));
    }

    @Test
    public void testUpdateEvento() throws Exception {
        Evento evento = new Evento(1L, "Evento 1", LocalDateTime.now(), "Local 1", "Descrição 1");

        when(eventoRepository.findById(1L)).thenReturn(Optional.of(evento));
        when(eventoRepository.save(any(Evento.class))).thenReturn(evento);

        mockMvc.perform(put("/api/eventos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"Evento Atualizado\", \"dataHoraEvento\":\"2024-06-01T12:00:00\", \"local\":\"Local 1\", \"descricao\":\"Descrição Atualizada\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Evento Atualizado"));
    }

    @Test
    public void testUpdateEventoNotFound() throws Exception {
        when(eventoRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/eventos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"Evento Atualizado\", \"dataHoraEvento\":\"2024-06-01T12:00:00\", \"local\":\"Local 1\", \"descricao\":\"Descrição Atualizada\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteEvento() throws Exception {
        Evento evento = new Evento(1L, "Evento 1", LocalDateTime.now(), "Local 1", "Descrição 1");

        when(eventoRepository.findById(1L)).thenReturn(Optional.of(evento));

        mockMvc.perform(delete("/api/eventos/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteEventoNotFound() throws Exception {
        when(eventoRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/eventos/1"))
                .andExpect(status().isNotFound());
    }
}
