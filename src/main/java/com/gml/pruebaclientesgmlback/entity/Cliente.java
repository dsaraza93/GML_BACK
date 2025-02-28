package com.gml.pruebaclientesgmlback.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Shared key es obligatorio")
    @Column(name = "shared_key")
    private String sharedKey;

    @NotBlank(message = "Business ID es obligatorio")
    @Column(name = "business_id")
    private String businessId;

    @NotBlank(message = "E-mail es obligatorio")
    @Email(message = "Debe ser un e-mail válido")
    private String email;

    @NotBlank(message = "Phone es obligatorio")
    private String phone;

    @NotNull(message = "Date Added es obligatorio")
    @Column(name = "date_added")
    private LocalDate dateAdded;


    public Cliente() { }

    public Cliente(String sharedKey, String businessId, String email, String phone, LocalDate dateAdded) {
        this.sharedKey = sharedKey;
        this.businessId = businessId;
        this.email = email;
        this.phone = phone;
        this.dateAdded = dateAdded;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSharedKey() {
        return sharedKey;
    }

    public void setSharedKey(String sharedKey) {
        this.sharedKey = sharedKey;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(LocalDate dateAdded) {
        this.dateAdded = dateAdded;
    }
}