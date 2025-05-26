package com.vitalconnect.adminpanel.model;

import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "admin")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Admin {

    @Id //Identificador primario
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Valor generado automaticamente
    private int id;

    @NotBlank(message = "Es obligatorio registrar un rut.")
    @Size(max = 13, message = "El rut no debe exceder los 13 caracteres.")
    @Column(name = "rut", unique = true, nullable = false) // unique valida que el campo sea único
    private String rut;

    @NotBlank (message = "Es obligatorio registrar un nombre.")
    @Size (max = 100)
    @Column(name = "nombre", length = 100)
    private String nombre;

    @NotBlank (message = "Es obligatorio registrar un apellido.")
    @Size (max = 100)
    @Column(name = "apellido", length = 100)
    private String apellido;

    @NotBlank
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "admin_rol", joinColumns = @JoinColumn(name = "admin_id"))
    @Column(name = "rol")
    private List<String> rol; // Ej. roles: super_admin (control total del sistema), moderador (puede crear y resolver reportes),auditor (puede ver todos los reportes, pero no modificar)

    @Builder.Default
    @Column(name = "baneado")
    private boolean baneado = false;

}
