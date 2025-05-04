package com.example.INVENTARIO_DROGUERIA.Service;

import com.example.INVENTARIO_DROGUERIA.Exceptions.NoContentData;
import com.example.INVENTARIO_DROGUERIA.Model.MovimientoInventario;
import com.example.INVENTARIO_DROGUERIA.Repository.MovimientoInventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovimientoInventarioService {

    @Autowired
    private MovimientoInventarioRepository movimientoInventarioRepository;

    // Listar todos por fecha orden desc
    public Page<MovimientoInventario> listarPorFecha(Pageable pageable) {
        Page<MovimientoInventario> movimientoInventarios = movimientoInventarioRepository.findAllOrderByFechaDesc(pageable);

        if(!movimientoInventarios.hasContent()) {
            throw new NoContentData("No hay contenido.");
        }

        return movimientoInventarios;
    }

    // Listar todos por tipo (orden fecha desc)

    // Listar por lote (orden fecha desc)

    // Listar por producto (orden fecha desc)

    // Buscar por id

    public List<MovimientoInventario> obtenerTodos() {
        return movimientoInventarioRepository.findAll();
    }

    public Optional<MovimientoInventario> obtenerPorId(Long id) {
        return movimientoInventarioRepository.findById(id);
    }

    public MovimientoInventario guardar(MovimientoInventario movimiento) {
        return movimientoInventarioRepository.save(movimiento);
    }

    public void eliminar(Long id) {
        movimientoInventarioRepository.deleteById(id);
    }
}

