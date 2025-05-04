package com.example.INVENTARIO_DROGUERIA.Repository;

import com.example.INVENTARIO_DROGUERIA.Model.MovimientoInventario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
}
