package com.example.INVENTARIO_DROGUERIA.Service;

import com.example.INVENTARIO_DROGUERIA.Model.Lote;
import com.example.INVENTARIO_DROGUERIA.Repository.LoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoteService {

    @Autowired
    private LoteRepository loteRepository;

    public List<Lote> obtenerTodos() {
        return loteRepository.findAll(); // <- aquí corregido
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

