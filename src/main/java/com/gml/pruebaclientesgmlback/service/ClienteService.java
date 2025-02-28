package com.gml.pruebaclientesgmlback.service;

import com.gml.pruebaclientesgmlback.entity.Cliente;
import java.time.LocalDate;
import java.util.List;

public interface ClienteService {
    List<Cliente> getAllClientes();
    Cliente createCliente(Cliente cliente);
    Cliente getClienteById(Long id);
    Cliente updateCliente(Long id, Cliente cliente);
    void deleteCliente(Long id);

    // Método para búsqueda simple por sharedKey
    List<Cliente> searchClientes(String sharedKey);
    // Método para búsqueda avanzada
    List<Cliente> searchAdvanced(String sharedKey, String businessId, String email, String phone, LocalDate dateFrom, LocalDate dateTo);
}
