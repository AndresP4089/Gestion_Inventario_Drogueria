package com.example.INVENTARIO_DROGUERIA.Model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "movimiento_inventario")
public class MovimientoInventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "lote_id")
    private Lote lote;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precioCompraVenta;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(length = 100)
    private String motivo;

    @Column(columnDefinition = "TEXT")
    private String observaciones;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoMovimiento tipo;

    public enum TipoMovimiento {
        ENTRADA,
        SALIDA
    }
}
