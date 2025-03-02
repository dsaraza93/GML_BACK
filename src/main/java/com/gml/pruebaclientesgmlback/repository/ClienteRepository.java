package com.gml.pruebaclientesgmlback.repository;

import com.gml.pruebaclientesgmlback.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>, JpaSpecificationExecutor<Cliente> {
    List<Cliente> findBySharedKeyContainingIgnoreCase(String sharedKey);
}
