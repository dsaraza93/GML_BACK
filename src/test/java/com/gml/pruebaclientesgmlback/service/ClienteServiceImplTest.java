package com.gml.pruebaclientesgmlback.service;

import com.gml.pruebaclientesgmlback.entity.Cliente;
import com.gml.pruebaclientesgmlback.exception.ResourceNotFoundException;
import com.gml.pruebaclientesgmlback.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ClienteServiceImplTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllClientes() {
        Cliente c1 = new Cliente();
        c1.setId(1L);
        c1.setSharedKey("key1");
        Cliente c2 = new Cliente();
        c2.setId(2L);
        c2.setSharedKey("key2");

        when(clienteRepository.findAll()).thenReturn(Arrays.asList(c1, c2));

        List<Cliente> clientes = clienteService.getAllClientes();
        assertEquals(2, clientes.size());
        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    void testCreateCliente() {
        Cliente c = new Cliente();
        c.setSharedKey("key1");

        // Simula que al guardar se asigna un ID
        when(clienteRepository.save(any(Cliente.class))).thenAnswer(invocation -> {
            Cliente saved = invocation.getArgument(0);
            saved.setId(1L);
            return saved;
        });

        Cliente created = clienteService.createCliente(c);
        assertNotNull(created.getId());
        assertEquals("key1", created.getSharedKey());
        verify(clienteRepository, times(1)).save(c);
    }

    @Test
    void testGetClienteByIdFound() {
        Cliente c = new Cliente();
        c.setId(1L);
        c.setSharedKey("key1");

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(c));

        Cliente found = clienteService.getClienteById(1L);
        assertNotNull(found);
        assertEquals("key1", found.getSharedKey());
    }

    @Test
    void testGetClienteByIdNotFound() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> clienteService.getClienteById(1L));
    }

    @Test
    void testUpdateCliente() {
        Cliente existing = new Cliente();
        existing.setId(1L);
        existing.setSharedKey("oldKey");
        existing.setBusinessId("oldBusiness");
        existing.setEmail("old@example.com");
        existing.setPhone("123456");
        existing.setDateAdded(LocalDate.of(2020, 1, 1));

        Cliente updates = new Cliente();
        updates.setSharedKey("newKey");
        updates.setBusinessId("newBusiness");
        updates.setEmail("new@example.com");
        updates.setPhone("654321");
        updates.setDateAdded(LocalDate.of(2021, 2, 2));

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(clienteRepository.save(existing)).thenReturn(existing);

        Cliente updated = clienteService.updateCliente(1L, updates);
        assertEquals("newKey", updated.getSharedKey());
        assertEquals("newBusiness", updated.getBusinessId());
        assertEquals("new@example.com", updated.getEmail());
        assertEquals("654321", updated.getPhone());
        assertEquals(LocalDate.of(2021, 2, 2), updated.getDateAdded());
    }

    @Test
    void testDeleteCliente() {
        Cliente c = new Cliente();
        c.setId(1L);

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(c));
        doNothing().when(clienteRepository).delete(c);

        clienteService.deleteCliente(1L);
        verify(clienteRepository, times(1)).delete(c);
    }

    @Test
    void testSearchClientes() {
        Cliente c = new Cliente();
        c.setId(1L);
        c.setSharedKey("testKey");

        when(clienteRepository.findBySharedKeyContainingIgnoreCase("testKey"))
                .thenReturn(Arrays.asList(c));

        List<Cliente> result = clienteService.searchClientes("testKey");
        assertEquals(1, result.size());
        verify(clienteRepository, times(1)).findBySharedKeyContainingIgnoreCase("testKey");
    }

    @Test
    void testSearchAdvanced() {
        Cliente c = new Cliente();
        c.setId(1L);
        c.setSharedKey("testKey");
        c.setBusinessId("business");
        c.setEmail("test@example.com");
        c.setPhone("1234567890");
        c.setDateAdded(LocalDate.of(2022, 1, 1));

        // Suponemos que cualquier Specification devuelve el cliente
        when(clienteRepository.findAll(any(Specification.class))).thenReturn(Arrays.asList(c));

        List<Cliente> result = clienteService.searchAdvanced(
                "testKey", "business", "test@example.com", "1234567890",
                LocalDate.of(2021, 1, 1), LocalDate.of(2023, 1, 1)
        );
        assertEquals(1, result.size());
        verify(clienteRepository, times(1)).findAll(any(Specification.class));
    }
}
