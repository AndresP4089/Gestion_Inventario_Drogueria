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

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    // CONSULTAS

    // Encontrar y paginar todo
    public Page<Producto> obtenerTodos(Pageable pageable) {
        Page<Producto> productos = productoRepository.findAllOrderByAsc(pageable);

        if(!productos.hasContent()) {
            throw new NoContentData("No hay contenido");
        }

        return productos;
    }

    // Encontrar uno por codigo
    public Producto obtenerPorCodigo(String codigo) {
        // validar que exista producto con el codigo
        return productoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new NotFoundDataException("No existe un producto con el código: " + codigo));
    }

    // Encontrar y paginar todos por nombre
    public Page<Producto> obtenerPorNombre(String nombre, Pageable pageable) {
        nombre = nombre.trim();
        Page<Producto> productos = productoRepository.findByNombre(nombre, pageable);

        // Validar que existan productos por ese nombre
        if(!productos.hasContent()) {
            throw new NoContentData("No hay contenido");
        }

        return productos;
    }

    // ACTUALIZACIONES

    // Crear un nuevo producto
    public Producto crearNuevoProducto(Producto producto) {
        // Validaciones de campo 'nombre'
        if (producto.getNombre() == null || producto.getNombre().isBlank()) {
            throw new BadRequestException("El producto debe tener nombre");
        }

        // Validación del campo 'codigo'
        if (producto.getCodigo() == null || producto.getCodigo().isBlank()) {
            throw new BadRequestException("El producto debe tener un código");
        }

        // Validación para comprobar si ya existe un producto con el mismo codigo
        if (productoRepository.existsByCodigo(producto.getCodigo())) {
            throw new BadRequestException("Ya existe un producto con ese codigo");
        }

        // Validación para comprobar si ya existe un producto con el mismo nombre
        if (productoRepository.existsByNombre(producto.getNombre())) {
            throw new BadRequestException("Ya existe un producto con ese nombre");
        }

        // Validación de precio unitario
        if (producto.getPrecioUnitario() == null || producto.getPrecioUnitario().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("El precio unitario no debe ser nulo y debe ser mayor que cero");
        }

        // Validación de stock mínimo
        if (producto.getStockMinimo() == null || producto.getStockMinimo() <= 0) {
            throw new BadRequestException("El stock mínimo debe ser mayor que cero");
        }

        // Validación de presentacion
        if (producto.getPresentacion() == null || producto.getPresentacion().isBlank()) {
            throw new BadRequestException("El producto debe tener presentación");
        }

        // Validación de unidad de medida
        if (producto.getUnidadMedida() == null || producto.getUnidadMedida().isBlank()) {
            throw new BadRequestException("El producto debe tener unidad de medida");
        }

        // Establece el estado del producto como 'ACTIVO'
        producto.setEstado(Producto.EstadoProducto.ACTIVO);

        return productoRepository.save(producto);
    }

    // Editar un producto existente
    public Producto editarProducto(Producto productoNuevo, Long id) {

        // Validar que exista el producto
        Producto productoActual = productoRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("El producto con id: " + id + " no existe"));

        // Validar que el producto esté activo, no editar productos inactivos
        if (productoActual.getEstado() == Producto.EstadoProducto.INACTIVO) {
            throw new BadRequestException("Este producto está inactivo, no se puede editar");
        }

        // Validar que no exista otro producto con el mismo nombre (y diferente id)
        Optional<Producto> productoConMismoNombre = productoRepository.findByNombre(productoNuevo.getNombre());
        if (productoConMismoNombre.isPresent() && !productoConMismoNombre.get().getId().equals(id)) {
            throw new BadRequestException("Ya existe otro producto con ese nombre");
        }

        // Validar que no exista otro producto con el mismo código (y diferente id)
        if (productoRepository.existsByCodigoAndIdNot(productoNuevo.getCodigo(), id)) {
            throw new BadRequestException("Ya existe otro producto con ese código");
        }


        // Validar que precio unitario sea mayor a cero
        if (productoNuevo.getPrecioUnitario().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("El precio unitario debe ser mayor que cero");
        }

        // Validación que el stock mínimo sea mayor a cero
        if (productoNuevo.getStockMinimo() <= 0) {
            throw new BadRequestException("El stock mínimo debe ser mayor que cero");
        }

        // Solo actualiza campos no nulos o no vacíos(Actualizacion parcial)
        if (productoNuevo.getNombre() != null && !productoNuevo.getNombre().isBlank()) {
            productoActual.setNombre(productoNuevo.getNombre());
        }

        if (productoNuevo.getCodigo() != null && !productoNuevo.getCodigo().isBlank()) {
            productoActual.setCodigo(productoNuevo.getCodigo());
        }

        if (productoNuevo.getDescripcion() != null) {
            productoActual.setDescripcion(productoNuevo.getDescripcion());
        }

        if (productoNuevo.getPrecioUnitario() != null) {
            productoActual.setPrecioUnitario(productoNuevo.getPrecioUnitario());
        }

        if (productoNuevo.getLaboratorio() != null) {
            productoActual.setLaboratorio(productoNuevo.getLaboratorio());
        }

        if (productoNuevo.getPrincipioActivo() != null) {
            productoActual.setPrincipioActivo(productoNuevo.getPrincipioActivo());
        }

        if (productoNuevo.getPresentacion() != null && !productoNuevo.getPresentacion().isBlank()) {
            productoActual.setPresentacion(productoNuevo.getPresentacion());
        }

        if (productoNuevo.getUnidadMedida() != null && !productoNuevo.getUnidadMedida().isBlank()) {
            productoActual.setUnidadMedida(productoNuevo.getUnidadMedida());
        }

        if (productoNuevo.getStockMinimo() != null) {
            productoActual.setStockMinimo(productoNuevo.getStockMinimo());
        }

        productoActual.setEstado(Producto.EstadoProducto.ACTIVO);

        return productoRepository.save(productoActual);
    }


    public void eliminar(Long id) {
        productoRepository.deleteById(id);
    }
}
