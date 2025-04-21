package com.example.INVENTARIO_DROGUERIA.Repository;

import com.example.INVENTARIO_DROGUERIA.Model.Producto;
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
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // Encontrar y paginar todos los productos activos en orden alfabetico
    @Query(value = "SELECT * FROM producto WHERE estado = 'ACTIVO' ORDER BY nombre ASC",
            countQuery = "SELECT count(*) FROM producto WHERE estado = 'ACTIVO'",
            nativeQuery = true)
    Page<Producto> findAllOrderByAsc(Pageable pageable);

    // Encontrar los productos activos por su codigo
    @Query(value = "SELECT * FROM producto\n" +
            "WHERE estado = 'ACTIVO'\n" +
            "AND codigo = :codigoProducto;", nativeQuery = true)
    Optional<Producto> findByCodigo(@Param("codigoProducto") String codigoProducto);

    // Encontrar y paginar los productos activos por el nombre
    @Query(value = "SELECT * FROM producto " +
            "WHERE estado = 'ACTIVO' AND LOWER(nombre) LIKE LOWER(CONCAT(:nombre, '%')) " +
            "ORDER BY nombre ASC",
            countQuery = "SELECT count(*) FROM producto WHERE estado = 'ACTIVO' AND LOWER(nombre) LIKE LOWER(CONCAT(:nombre, '%')) ",
            nativeQuery = true)
    Page<Producto> findByNombre(@Param("nombre") String nombre, Pageable pageable);

    // Encontrar un profucto por nombre
    Optional<Producto> findByNombre(String nombre);

    // Decir si existe un producto por su nombre
    boolean existsByNombre(String nombre);

    // Decir si existe un producto por su codigo
    boolean existsByCodigo(String codigo);

    // Decir si existe un producto con codigo e id
    boolean existsByCodigoAndIdNot(String codigo, Long id);

    // Inactivar un producto
    @Modifying
    @Transactional
    @Query(value = "UPDATE producto SET estado = :estado WHERE id = :idProducto", nativeQuery = true)
    void actualizarEstadoProducto(@Param("idProducto") Long idProducto, @Param("estado") String estado);
}

