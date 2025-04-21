package com.example.INVENTARIO_DROGUERIA.Repository;

import com.example.INVENTARIO_DROGUERIA.Model.Lote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface LoteRepository extends JpaRepository<Lote, Long> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE lote SET estado = :estado WHERE producto_id = :idProducto", nativeQuery = true)
    int actualizarEstadoLotePorProducto(@Param("idProducto") Long idProducto, @Param("estado") String estado);
}
