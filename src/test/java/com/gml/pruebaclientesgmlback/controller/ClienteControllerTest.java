package com.gml.pruebaclientesgmlback.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gml.pruebaclientesgmlback.entity.Cliente;
import com.gml.pruebaclientesgmlback.exception.ResourceNotFoundException;
import com.gml.pruebaclientesgmlback.service.ClienteService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClienteController.class)
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("GET /api/clientes - Listar todos los clientes")
    void testGetAllClientes() throws Exception {
        Cliente cliente1 = new Cliente();
        cliente1.setId(1L);
        cliente1.setSharedKey("key1");
        cliente1.setBusinessId("business1");
        cliente1.setEmail("email1@example.com");
        cliente1.setPhone("1234567890");
        cliente1.setDateAdded(LocalDate.now());

        Cliente cliente2 = new Cliente();
        cliente2.setId(2L);
        cliente2.setSharedKey("key2");
        cliente2.setBusinessId("business2");
        cliente2.setEmail("email2@example.com");
        cliente2.setPhone("0987654321");
        cliente2.setDateAdded(LocalDate.now());

        when(clienteService.getAllClientes()).thenReturn(Arrays.asList(cliente1, cliente2));

        mockMvc.perform(get("/api/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].sharedKey", is("key1")))
                .andExpect(jsonPath("$[1].sharedKey", is("key2")));
    }

    @Test
    @DisplayName("POST /api/clientes - Crear un cliente")
    void testCreateCliente() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setSharedKey("newKey");
        cliente.setBusinessId("newBusiness");
        cliente.setEmail("new@example.com");
        cliente.setPhone("1234567890");
        cliente.setDateAdded(LocalDate.now());

        Cliente savedCliente = new Cliente();
        savedCliente.setId(1L);
        savedCliente.setSharedKey("newKey");
        savedCliente.setBusinessId("newBusiness");
        savedCliente.setEmail("new@example.com");
        savedCliente.setPhone("1234567890");
        savedCliente.setDateAdded(LocalDate.now());

        when(clienteService.createCliente(any(Cliente.class))).thenReturn(savedCliente);

        mockMvc.perform(post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cliente)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.sharedKey", is("newKey")));
    }

    @Test
    @DisplayName("GET /api/clientes/{id} - Cliente no encontrado")
    void testGetClienteById_NotFound() throws Exception {
        when(clienteService.getClienteById(anyLong())).thenThrow(new ResourceNotFoundException("Cliente not found with id: 999"));

        mockMvc.perform(get("/api/clientes/{id}", 999))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error", containsString("Cliente not found with id: 999")));
    }

    @Test
    @DisplayName("DELETE /api/clientes/{id} - Eliminar cliente")
    void testDeleteCliente() throws Exception {
        // Simula que se elimina sin problemas (no retorna contenido)
        mockMvc.perform(delete("/api/clientes/{id}", 1))
                .andExpect(status().isNoContent());
    }
}
