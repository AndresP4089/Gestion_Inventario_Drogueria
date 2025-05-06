package com.example.INVENTARIO_DROGUERIA.Repository;

import com.example.INVENTARIO_DROGUERIA.Model.MovimientoInventario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MovimientoInventarioRepository extends JpaRepository<MovimientoInventario, Long> {
    // Encontrar y paginar todos los movimiento en orden desc por fecha
    @Query(value = "SELECT * FROM movimiento_inventario ORDER BY fecha DESC",
            countQuery = "SELECT count(*) FROM movimiento_inventario",
            nativeQuery = true)
    Page<MovimientoInventario> findAllOrderDescByFecha(Pageable pageable);

    // Encontrar todos por tipo
    @Query(value = "SELECT * FROM movimiento_inventario WHERE tipo = :tipo ORDER BY fecha DESC",
            countQuery = "SELECT count(*) FROM movimiento_inventario WHERE tipo = :tipo",
            nativeQuery = true)
    Page<MovimientoInventario> findAllOrderDescByTipo(@Param("tipo") String tipo, Pageable pageable);

    @Query(
            value = "SELECT mi.* FROM movimiento_inventario mi " +
                    "JOIN lote l ON mi.lote_id = l.id " +
                    "WHERE l.numero_lote = :numeroLote AND l.estado = 'ACTIVO' " +
                    "ORDER BY mi.fecha",
            countQuery = "SELECT COUNT(*) FROM movimiento_inventario mi " +
                    "JOIN lote l ON mi.lote_id = l.id " +
                    "WHERE l.numero_lote = :numeroLote AND l.estado = 'ACTIVO'",
            nativeQuery = true
    )
    Page<MovimientoInventario> findAllByLote(@Param("numeroLote") String numeroLote, Pageable pageable);

    @Query(
            value = "SELECT mi.* FROM movimiento_inventario mi " +
                    "JOIN producto p ON mi.producto_id = p.id " +
                    "WHERE p.codigo = :codigoProducto AND p.estado = 'ACTIVO' " +
                    "ORDER BY mi.fecha",
            countQuery = "SELECT COUNT(*) FROM movimiento_inventario mi " +
                    "JOIN producto p ON mi.producto_id = p.id " +
                    "WHERE p.codigo = :codigoProducto AND p.estado = 'ACTIVO'",
            nativeQuery = true
    )
    Page<MovimientoInventario> findAllByProducto(@Param("codigoProducto") String codigoProducto, Pageable pageable);

    //
    @Query("SELECT SUM(CASE WHEN m.tipo = 'ENTRADA' THEN m.cantidad ELSE -m.cantidad END) " +
            "FROM MovimientoInventario m WHERE m.lote.id = :loteId")
    Integer calcularStockPorLote(@Param("loteId") Long loteId);

    @Query("SELECT SUM(CASE WHEN m.tipo = 'ENTRADA' THEN m.cantidad ELSE -m.cantidad END) " +
            "FROM MovimientoInventario m WHERE m.producto.id = :productoId AND m.lote IS NULL")
    Integer calcularStockPorProducto(@Param("productoId") Long productoId);

    // Reporte por rango de fechas
    @Query("SELECT m FROM MovimientoInventario m " +
            "WHERE (:codigoProducto IS NULL OR m.producto.codigo = :codigoProducto) " +
            "AND (m.fecha >= :fechaDesde) " +
            "AND (m.fecha <= :fechaHasta) " +
            "AND (:tipoMovimiento IS NULL OR m.tipo = :tipoMovimiento)")
    List<MovimientoInventario> buscarPorFiltros(
            @Param("codigoProducto") String codigoProducto,
            @Param("fechaDesde") LocalDate fechaDesde,
            @Param("fechaHasta") LocalDate fechaHasta,
            @Param("tipoMovimiento") MovimientoInventario.TipoMovimiento tipoMovimiento);

    // Reporte por fecha exacta
    @Query("SELECT m FROM MovimientoInventario m " +
            "WHERE (:codigoProducto IS NULL OR m.producto.codigo = :codigoProducto) " +
            "AND m.fecha = :fechaExacta "+
            "AND (:tipoMovimiento IS NULL OR m.tipo = :tipoMovimiento)")
    List<MovimientoInventario> buscarPorFechaExacta(
            @Param("codigoProducto") String codigoProducto,
            @Param("fechaExacta") LocalDate fechaExacta,
            @Param("tipoMovimiento") MovimientoInventario.TipoMovimiento tipoMovimiento);
}
