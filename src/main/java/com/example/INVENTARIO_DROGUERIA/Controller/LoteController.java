package com.example.INVENTARIO_DROGUERIA.Controller;

import com.example.INVENTARIO_DROGUERIA.Model.Lote;
import com.example.INVENTARIO_DROGUERIA.Service.LoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/lotes")
public class LoteController {

    @Autowired
    private LoteService loteService;

    @GetMapping("/listar")
    public List<Lote> obtenerTodos() {
        return loteService.obtenerTodos();
    }

    @GetMapping("/listar/{id}")
    public Optional<Lote> obtenerPorId(@PathVariable Long id) {
        return loteService.obtenerPorId(id);
    }

    @PostMapping("/crear")
    public Lote guardar(@RequestBody Lote lote) {
        return loteService.guardar(lote);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminar(@PathVariable Long id) {
        loteService.eliminar(id);
    }
}

