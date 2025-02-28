package com.gml.pruebaclientesgmlback.controller;

import com.gml.pruebaclientesgmlback.entity.Cliente;
import com.gml.pruebaclientesgmlback.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Tag(name = "Clientes", description = "Endpoints para administrar clientes")
@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Operation(summary = "Listar todos los clientes", description = "Retorna la lista completa de clientes")
    @GetMapping
    public List<Cliente> getAllClientes() {
        log.info("Llamada a getAllClientes");
        return clienteService.getAllClientes();
    }

    @Operation(summary = "Búsqueda simple de clientes", description = "Retorna la lista de clientes filtrada por sharedKey")
    @GetMapping("/search")
    public List<Cliente> searchClientes(
            @RequestParam(value = "sharedKey", required = true) String sharedKey) {
        log.info("Búsqueda simple con sharedKey: {}", sharedKey);
        return clienteService.searchClientes(sharedKey);
    }

    @Operation(summary = "Búsqueda avanzada de clientes",
            description = "Retorna la lista de clientes filtrada por sharedKey, businessId, email, phone y/o un rango de fechas (dateFrom y dateTo)")
    @GetMapping("/advanced")
    public List<Cliente> searchAdvanced(
            @RequestParam(value = "sharedKey", required = false) String sharedKey,
            @RequestParam(value = "businessId", required = false) String businessId,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "dateFrom", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
            @RequestParam(value = "dateTo", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo) {
        log.info("Búsqueda avanzada con sharedKey: {}, businessId: {}, email: {}, phone: {}, dateFrom: {}, dateTo: {}",
                sharedKey, businessId, email, phone, dateFrom, dateTo);
        return clienteService.searchAdvanced(sharedKey, businessId, email, phone, dateFrom, dateTo);
    }

    @Operation(summary = "Crear un cliente", description = "Crea un nuevo cliente en la base de datos")
    @PostMapping
    public Cliente createCliente(@Valid @RequestBody Cliente cliente) {
        log.info("Creando cliente: {}", cliente);
        cliente.setId(null); // Asegura que se genere el ID
        return clienteService.createCliente(cliente);
    }

    @Operation(summary = "Obtener cliente por ID", description = "Busca un cliente por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable Long id) {
        log.info("Obteniendo cliente con id: {}", id);
        Cliente cliente = clienteService.getClienteById(id);
        return ResponseEntity.ok(cliente);
    }

    @Operation(summary = "Actualizar un cliente", description = "Actualiza los datos de un cliente existente por su ID")
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> updateCliente(@PathVariable Long id, @Valid @RequestBody Cliente clienteDetails) {
        log.info("Actualizando cliente con id: {}", id);
        Cliente updatedCliente = clienteService.updateCliente(id, clienteDetails);
        return ResponseEntity.ok(updatedCliente);
    }

    @Operation(summary = "Eliminar un cliente", description = "Elimina un cliente existente por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        log.info("Eliminando cliente con id: {}", id);
        clienteService.deleteCliente(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Exportar clientes a CSV",
            description = "Genera un archivo CSV con la lista de clientes")
    @GetMapping("/export")
    public ResponseEntity<ByteArrayResource> exportClientesCSV() {
        log.info("Exportando clientes a CSV");
        List<Cliente> clientes = clienteService.getAllClientes();
        String csvContent = convertClientesToCSV(clientes);
        ByteArrayResource resource = new ByteArrayResource(csvContent.getBytes(StandardCharsets.UTF_8));

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=clientes.csv");
        headers.add(HttpHeaders.CONTENT_TYPE, "text/csv; charset=UTF-8");

        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }

    private String convertClientesToCSV(List<Cliente> clientes) {
        StringBuilder sb = new StringBuilder();
        sb.append("Shared Key,Business ID,E-mail,Phone,Date Added\n");
        for (Cliente c : clientes) {
            sb.append(c.getSharedKey()).append(",");
            sb.append(c.getBusinessId()).append(",");
            sb.append(c.getEmail()).append(",");
            sb.append(c.getPhone()).append(",");
            sb.append(c.getDateAdded()).append("\n");
        }
        return sb.toString();
    }
}
