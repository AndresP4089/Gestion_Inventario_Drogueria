package com.example.INVENTARIO_DROGUERIA.Repository;

import com.example.INVENTARIO_DROGUERIA.Model.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {
}

