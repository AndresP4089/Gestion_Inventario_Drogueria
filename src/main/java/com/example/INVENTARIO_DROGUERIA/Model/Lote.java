package com.example.INVENTARIO_DROGUERIA.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "lote")
public class Lote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "proveedor_id", nullable = false)
    private Proveedor proveedor;

    @Column(name = "numero_lote", length = 50, nullable = false)
    private String numeroLote;

    @Column(name = "fecha_ingreso", nullable = false)
    private LocalDate fechaIngreso;

    @Column(name = "fecha_vencimiento", nullable = false)
    private LocalDate fechaVencimiento;

    @Column(name = "cantidad_inicial", nullable = false)
    private Integer cantidadInicial;

    @Column(name = "cantidad_actual", nullable = false)
    private Integer cantidadActual;

    @JsonIgnore
    @OneToMany(mappedBy = "lote", fetch = FetchType.LAZY)
    private List<MovimientoInventario> movimientos;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoLote estado = EstadoLote.ACTIVO;

    public enum EstadoLote {
        ACTIVO,
        INACTIVO
    }
}
