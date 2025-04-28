package com.example.INVENTARIO_DROGUERIA.Repository;

import com.example.INVENTARIO_DROGUERIA.Model.Proveedor;
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
public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {
    // Encontrar y paginar todos los proveedores activos en orden alfabetico
    @Query(value = "SELECT * FROM proveedor WHERE estado = 'ACTIVO' ORDER BY nombre ASC",
            countQuery = "SELECT count(*) FROM proveedor WHERE estado = 'ACTIVO'",
            nativeQuery = true)
    Page<Proveedor> findAllOrderByAsc(Pageable pageable);

    // Encontrar los proveedores activos por su NIT
    @Query(value = "SELECT * FROM proveedor\n" +
            "WHERE estado = 'ACTIVO'\n" +
            "AND nit = :NIT;", nativeQuery = true)
    Optional<Proveedor> findByNIT(@Param("NIT") String NIT);

    // Encontrar y paginar los proveedores activos por el nombre
    @Query(value = "SELECT * FROM proveedor " +
            "WHERE estado = 'ACTIVO' AND LOWER(nombre) LIKE LOWER(CONCAT(:nombre, '%')) " +
            "ORDER BY nombre ASC",
            countQuery = "SELECT count(*) FROM proveedor WHERE estado = 'ACTIVO' AND LOWER(nombre) LIKE LOWER(CONCAT(:nombre, '%')) ",
            nativeQuery = true)
    Page<Proveedor> findByNombre(@Param("nombre") String nombre, Pageable pageable);

    // Decir si existe un producto por su nombre
    boolean existsByNombre(String nombre);

    // Decir si existe un proveedor por su NIT
    boolean existsByNit(String NIT);

    // Encontrar un proveedor por nombre
    Optional<Proveedor> findByNombre(String nombre);

    // Decir si existe un proveedor con nit e id
    boolean existsByNitAndIdNot(String nit, Long id);

    // Inactivar un proveedor
    @Modifying
    @Transactional
    @Query(value = "UPDATE proveedor SET estado = :estado WHERE id = :idProveedor", nativeQuery = true)
    void actualizarEstadoProveedor(@Param("idProveedor") Long idProveedor, @Param("estado") String estado);


}

