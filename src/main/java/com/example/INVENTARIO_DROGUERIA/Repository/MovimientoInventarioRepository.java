package com.example.INVENTARIO_DROGUERIA.Repository;

import com.example.INVENTARIO_DROGUERIA.Model.MovimientoInventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovimientoInventarioRepository extends JpaRepository<MovimientoInventario, Long> {
}
