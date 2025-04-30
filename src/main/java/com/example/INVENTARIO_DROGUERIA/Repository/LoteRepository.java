package com.example.INVENTARIO_DROGUERIA.Repository;

import com.example.INVENTARIO_DROGUERIA.Model.Lote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface LoteRepository extends JpaRepository<Lote, Long> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE lote SET estado = :estado WHERE producto_id = :idProducto", nativeQuery = true)
    int actualizarEstadoLotePorProducto(@Param("idProducto") Long idProducto, @Param("estado") String estado);

    // Encontrar y paginar todos los lotes activos en orden desc por fecha_ingreso
    @Query(value = "SELECT * FROM lote WHERE estado = 'ACTIVO' ORDER BY fecha_ingreso DESC",
            countQuery = "SELECT count(*) FROM lote WHERE estado = 'ACTIVO'",
            nativeQuery = true)
    Page<Lote> findAllOrderByFechaIngresoAsc(Pageable pageable);

    // Encontrar y paginar todos los lotes activos en orden de asc por fecha_vencimiento
    @Query(value = "SELECT * FROM lote WHERE estado = 'ACTIVO' ORDER BY fecha_vencimiento ASC",
            countQuery = "SELECT count(*) FROM lote WHERE estado = 'ACTIVO'",
            nativeQuery = true)
    Page<Lote> findAllOrderByFechaVencimientoDesc(Pageable pageable);

    // Encontrar los lotes activos por su numero_lote
    @Query(value = "SELECT * FROM lote\n" +
            "WHERE estado = 'ACTIVO'\n" +
            "AND numero_lote = :numLote;", nativeQuery = true)
    Optional<Lote> findByNumeroLote(@Param("numLote") String numLote);
}
