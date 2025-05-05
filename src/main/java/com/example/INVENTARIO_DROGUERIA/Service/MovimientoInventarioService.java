package com.example.INVENTARIO_DROGUERIA.Service;

import com.example.INVENTARIO_DROGUERIA.Exceptions.BadRequestException;
import com.example.INVENTARIO_DROGUERIA.Exceptions.NoContentData;
import com.example.INVENTARIO_DROGUERIA.Model.*;
import com.example.INVENTARIO_DROGUERIA.Repository.LoteRepository;
import com.example.INVENTARIO_DROGUERIA.Repository.MovimientoInventarioRepository;
import com.example.INVENTARIO_DROGUERIA.Repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class MovimientoInventarioService {

    @Autowired
    private MovimientoInventarioRepository movimientoInventarioRepository;

    @Autowired
    private LoteRepository loteRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private LoteService loteService;

    @Autowired
    private ProductoService productoService;

    // CONSULTAS

    // Listar todos por fecha orden desc
    public Page<MovimientoInventario> listarPorFecha(Pageable pageable) {
        Page<MovimientoInventario> movimientoInventarios = movimientoInventarioRepository.findAllOrderDescByFecha(pageable);

        if(!movimientoInventarios.hasContent()) {
            throw new NoContentData("No hay contenido.");
        }

        return movimientoInventarios;
    }

    // Listar todos por tipo (orden fecha desc)
    public Page<MovimientoInventario> listarPorTipo(MovimientoInventario.TipoMovimiento tipo, Pageable pageable) {
        Page<MovimientoInventario> movimientoInventarios = movimientoInventarioRepository.findAllOrderDescByTipo(tipo.name(), pageable);

        if(!movimientoInventarios.hasContent()) {
            throw new NoContentData("No hay contenido.");
        }

        return movimientoInventarios;
    }

    // Listar por lote (orden fecha desc)
    public Page<MovimientoInventario> listarPorLote(String numeroLote, Pageable pageable) {
        Page<MovimientoInventario> movimientoInventarios = movimientoInventarioRepository.findAllByLote(numeroLote, pageable);

        if(!movimientoInventarios.hasContent()){
            throw new NoContentData("No hay contenido.");
        }

        return movimientoInventarios;
    }

    // Listar por producto (orden fecha desc)
    public Page<MovimientoInventario> listarPorProducto(String codigoProducto, Pageable pageable) {
        Page<MovimientoInventario> movimientoInventarios = movimientoInventarioRepository.findAllByProducto(codigoProducto, pageable);

        if(!movimientoInventarios.hasContent()){
            throw new NoContentData("No hay contenido");
        }

        return movimientoInventarios;
    }

    // Buscar por id
    public MovimientoInventario obtenerPorId(Long idMovimiento) {

        return movimientoInventarioRepository.findById(idMovimiento)
                .orElseThrow(() -> new BadRequestException("El movimiento con id {"+idMovimiento+"} no existe."));
    }

    public MovimientoInventario guardar(DTOMovimientoRequest request) {
        MovimientoInventario movimientoInventario = new MovimientoInventario();

        // Validar que tenga el codigo de producto
        if (request.getCodigoProducto() == null || request.getCodigoProducto().isBlank()) {
            throw new BadRequestException("El código de producto es obligatorio.");
        }

        // Validar que el producto existe
        Producto producto = productoService.obtenerPorCodigo(request.getCodigoProducto());

        // Validar que tenga el tipo
        if (request.getTipo() == null) {
            throw new BadRequestException("El movimiento requiere un tipo {ENTRADA, SALIDA}.");
        }

        // Validar que tenga la cantidad, mayor a cero
        if (request.getCantidad() == null || request.getCantidad() <= 0) {
            throw new BadRequestException("El movimiento requiere una cantidad mayor a cero.");
        }

        // Validar que tenga el precio, mayor a cero
        if (request.getPrecioCompraVenta() == null || request.getPrecioCompraVenta().compareTo(BigDecimal.ZERO) <= 0){
            throw new BadRequestException("El movimiento requiere un precio de compra o de venta mayor a cero.");
        }

        // Si es un movimiento de entrada
        if (request.getTipo() == MovimientoInventario.TipoMovimiento.ENTRADA) {

            // Si el producto es controlado por lote
            if (producto.getControladoPorLote()) {

                DTOLoteRequest loteRequest = request.getLote();

                // Validar que se proporcionó la información del lote (Para crearlo)
                if (loteRequest == null) {
                    throw new BadRequestException("Se requiere información para crear el lote que se asociará al producto que ingresó.");
                }

                // Asegurar que el codigo del producto ingresado coincida con el del lote por crear
                loteRequest.setCodigoProducto(producto.getCodigo());

                // Crear y guardar el lote en el movimientoInventario
                movimientoInventario.setLote(loteService.crearNuevoLote(loteRequest));

            }
                // Si el producto no es controlado por lote

                // Se asigna directamente el producto al movimientoInventario

        } else {
            // Si es un movimiento de SALIDA

            // Si es controlado por lote
            if (producto.getControladoPorLote()) {

                // Validar que tenga el numeroLote
                if (request.getNumeroLote() == null || request.getNumeroLote().isEmpty()) {
                    throw new BadRequestException("Se requiere un número de lote para la salida.");
                }

                // Validar y obtener el lote existente
                Lote loteExistente = loteService.obtenerPorNumeroLote(request.getNumeroLote());

                // Validar que el producto y el producto asociado al lote sean el mismo
                if (!producto.getId().equals(loteExistente.getProducto().getId())) {
                    throw new BadRequestException("El producto que ingresó no coincide con el lote.");
                }

                // Validar que la cantidad en stock no sea menor a la solicitada
                Integer stock = movimientoInventarioRepository.calcularStockPorLote(loteExistente.getId());
                if (stock == null) {
                    stock = 0;
                }
                if (stock < request.getCantidad()) {
                    throw new BadRequestException("No hay suficiente stock en el lote. Verifique la cantidad que quiere sacar.");
                }

                // Asociar el lote al movimiento
                movimientoInventario.setLote(loteExistente);
            } else {
                // Si el producto no es controlado por lote

                // Validar que la cantidad en stock no sea menor a la solicitada
                Integer stock = movimientoInventarioRepository.calcularStockPorProducto(producto.getId());
                if (stock == null) {
                    stock = 0;
                }
                if (stock < request.getCantidad()) {
                    throw new BadRequestException("No hay suficiente stock en el lote. Verifique la cantidad que quiere sacar.");
                }
            }
        }

        // En todos los casos se asigna el producto
        movimientoInventario.setProducto(producto);

        // Si observaciones y motivo vienen null, pasarlos a string vacio
        if (request.getMotivo() == null) {
            request.setMotivo("");
        }
        if (request.getObservaciones() == null) {
            request.setObservaciones("");
        }

        // Establecer si es entrada o salida
        movimientoInventario.setTipo(request.getTipo());

        // Establecer el motivo
        movimientoInventario.setMotivo(request.getMotivo());

        // Establecer las observaciones
        movimientoInventario.setObservaciones(request.getObservaciones());

        // Establecer la cantidad
        movimientoInventario.setCantidad(request.getCantidad());

        // Establecer la fecha actual
        movimientoInventario.setFecha(LocalDate.now());

        // Establecer precio
        movimientoInventario.setPrecioCompraVenta(request.getPrecioCompraVenta());

        return movimientoInventarioRepository.save(movimientoInventario);
    }

    public void eliminar(Long id) {
        movimientoInventarioRepository.deleteById(id);
    }
}

