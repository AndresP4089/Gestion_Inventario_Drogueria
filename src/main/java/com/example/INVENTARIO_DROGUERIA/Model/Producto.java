package com.example.INVENTARIO_DROGUERIA.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Table(name = "producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(unique = true, length = 50, nullable = false)
    private String codigo;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "precio_unitario", precision = 10, scale = 2, nullable = false)
    private BigDecimal precioUnitario;

    @Column(length = 100)
    private String laboratorio;

    @Column(name = "principio_activo", length = 100)
    private String principioActivo;

    @Column(length = 50, nullable = false)
    private String presentacion;

    @Column(name = "unidad_medida", length = 20, nullable = false)
    private String unidadMedida;

    @Column(name = "stock_minimo", nullable = false)
    private Integer stockMinimo = 0;

    @JsonIgnore
    @OneToMany(mappedBy = "producto", fetch = FetchType.LAZY)
    private List<Lote> lotes;

    @JsonIgnore
    @OneToMany(mappedBy = "producto", fetch = FetchType.LAZY)
    private List<MovimientoInventario> movimientos;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoProducto estado = EstadoProducto.ACTIVO;

    public enum EstadoProducto {
        ACTIVO,
        INACTIVO
    }

    // Hay que añadirle un bool para saber si es manejado por lote
    @Column(nullable = false)
    private Boolean controladoPorLote;

}

