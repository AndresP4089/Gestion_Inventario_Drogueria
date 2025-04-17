package com.example.INVENTARIO_DROGUERIA.Service;

import com.example.INVENTARIO_DROGUERIA.Model.MovimientoInventario;
import com.example.INVENTARIO_DROGUERIA.Repository.MovimientoInventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovimientoInventarioService {

    @Autowired
    private MovimientoInventarioRepository movimientoInventarioRepository;

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

