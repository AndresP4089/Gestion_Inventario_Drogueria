package com.example.INVENTARIO_DROGUERIA.Service;

import com.example.INVENTARIO_DROGUERIA.Exceptions.BadRequestException;
import com.example.INVENTARIO_DROGUERIA.Exceptions.NoContentData;
import com.example.INVENTARIO_DROGUERIA.Exceptions.NotFoundDataException;
import com.example.INVENTARIO_DROGUERIA.Model.Producto;
import com.example.INVENTARIO_DROGUERIA.Repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    // CONSULTAS

    public Page<Producto> obtenerTodos(Pageable pageable) {

        Page<Producto> productos = productoRepository.findAllOrderByAsc(pageable);

        if(!productos.hasContent()){
            throw new NoContentData("No hay contenido");
        }

        return productos;
    }

    // listar por codigo
    public Producto obtenerPorCodigo(String codigo) {
        // validar que exista producto con el codigo
        return productoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new NotFoundDataException("No existe un producto con el código: " + codigo));
    }

    // listar por nombre
    public List<Producto> obtenerPorNombre(String nombre) {
        // validaciones
        nombre = nombre.trim();
        return productoRepository.findByName(nombre);
    }

    // ACTUALIZACIONES

    public Producto guardar(Producto producto) {
        return productoRepository.save(producto);
    }

    public void eliminar(Long id) {
        productoRepository.deleteById(id);
    }
}
