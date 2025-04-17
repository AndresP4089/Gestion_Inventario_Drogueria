package com.example.INVENTARIO_DROGUERIA.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "proveedor")
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(unique = true)
    private String ruc;

    private String direccion;

    @Column(nullable = false)
    private String telefono;

    @Column(nullable = false)
    private String email;

    @JsonIgnore
    @OneToMany(mappedBy = "proveedor", cascade = CascadeType.ALL)
    private List<Lote> lotes;

    @Enumerated(EnumType.STRING)
    private EstadoProveedor estado = EstadoProveedor.ACTIVO;

    public enum EstadoProveedor {
        ACTIVO,
        INACTIVO
    }
}
