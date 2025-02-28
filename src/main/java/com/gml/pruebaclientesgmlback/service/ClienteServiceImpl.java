package com.gml.pruebaclientesgmlback.service;

import com.gml.pruebaclientesgmlback.entity.Cliente;
import com.gml.pruebaclientesgmlback.exception.ResourceNotFoundException;
import com.gml.pruebaclientesgmlback.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public List<Cliente> getAllClientes() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente createCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public Cliente getClienteById(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente not found with id: " + id));
    }

    @Override
    public Cliente updateCliente(Long id, Cliente clienteDetails) {
        Cliente cliente = getClienteById(id);
        cliente.setSharedKey(clienteDetails.getSharedKey());
        cliente.setBusinessId(clienteDetails.getBusinessId());
        cliente.setEmail(clienteDetails.getEmail());
        cliente.setPhone(clienteDetails.getPhone());
        cliente.setDateAdded(clienteDetails.getDateAdded());
        return clienteRepository.save(cliente);
    }

    @Override
    public void deleteCliente(Long id) {
        Cliente cliente = getClienteById(id);
        clienteRepository.delete(cliente);
    }
    @Override
    public List<Cliente> searchClientes(String sharedKey) {
        return clienteRepository.findBySharedKeyContainingIgnoreCase(sharedKey);
    }
    @Override
    public List<Cliente> searchAdvanced(String sharedKey, String businessId, String email, String phone, LocalDate dateFrom, LocalDate dateTo) {
        Specification<Cliente> spec = Specification.where(null);

        if (sharedKey != null && !sharedKey.isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("sharedKey")), "%" + sharedKey.toLowerCase() + "%")
            );
        }
        if (businessId != null && !businessId.isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("businessId")), "%" + businessId.toLowerCase() + "%")
            );
        }
        if (email != null && !email.isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase() + "%")
            );
        }
        if (phone != null && !phone.isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("phone")), "%" + phone.toLowerCase() + "%")
            );
        }


        if (dateFrom != null) {
            spec = spec.and((root, query, cb) ->
                    cb.greaterThanOrEqualTo(root.get("dateAdded"), dateFrom)
            );
        }
        if (dateTo != null) {
            spec = spec.and((root, query, cb) ->
                    cb.lessThanOrEqualTo(root.get("dateAdded"), dateTo)
            );
        }
        return clienteRepository.findAll(spec);
    }

}
