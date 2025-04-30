package com.example.INVENTARIO_DROGUERIA.Service;

import com.example.INVENTARIO_DROGUERIA.Exceptions.NoContentData;
import com.example.INVENTARIO_DROGUERIA.Exceptions.NotFoundDataException;
import com.example.INVENTARIO_DROGUERIA.Model.Lote;
import com.example.INVENTARIO_DROGUERIA.Model.Proveedor;
import com.example.INVENTARIO_DROGUERIA.Repository.LoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoteService {

    @Autowired
    private LoteRepository loteRepository;

    // CONSULTAS

    // Paginar todos en orden en desc por fecha ingreso
    public Page<Lote> obtenerPorFechaIngreso(Pageable pageable) {
        Page<Lote> lotes = loteRepository.findAllOrderByFechaIngresoAsc(pageable);

        if(!lotes.hasContent()) {
            throw new NoContentData("No hay contenido.");
        }

        return lotes;
    }

    // Paginar todos en orden asc por fecha vencimiento
    public Page<Lote> obtenerPorFechaVencimiento(Pageable pageable) {
        Page<Lote> lotes = loteRepository.findAllOrderByFechaVencimientoDesc(pageable);

        if(!lotes.hasContent()) {
            throw new NoContentData("No hay contenido.");
        }

        return lotes;
    }

    public Optional<Lote> obtenerPorId(Long id) {
        return loteRepository.findById(id); // <- aquí también
    }

    public Lote guardar(Lote lote) {
        return loteRepository.save(lote); // <- y aquí
    }

    public void eliminar(Long id) {
        loteRepository.deleteById(id); // <- y finalmente aquí
    }
}

