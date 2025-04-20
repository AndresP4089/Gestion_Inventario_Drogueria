package com.example.INVENTARIO_DROGUERIA.Repository;

import com.example.INVENTARIO_DROGUERIA.Model.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    @Query(value = "SELECT * FROM producto WHERE estado = 'ACTIVO' ORDER BY nombre ASC",
            countQuery = "SELECT count(*) FROM producto WHERE estado = 'ACTIVO'",
            nativeQuery = true)
    Page<Producto> findAllOrderByAsc(Pageable pageable);

    @Query(value = "SELECT * FROM producto\n" +
            "WHERE estado = 'ACTIVO'\n" +
            "AND codigo = :codigoProducto;", nativeQuery = true)
    Optional<Producto> findByCodigo(@Param("codigoProducto") String codigoProducto);

    @Query(value = "SELECT * FROM producto " +
            "WHERE estado = 'ACTIVO' AND LOWER(nombre) LIKE LOWER(CONCAT(:letra, '%')) " +
            "ORDER BY nombre ASC", nativeQuery = true)
    List<Producto> findByName(@Param("letra") String letra);

}

