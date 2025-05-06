package com.example.INVENTARIO_DROGUERIA.Service;

import com.example.INVENTARIO_DROGUERIA.DTO.Lote.DTOLoteRequest;
import com.example.INVENTARIO_DROGUERIA.DTO.MovimientoInventario.*;
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
import java.util.ArrayList;
import java.util.List;

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

    // Generar reporte
    public List<MovimientoReporteDTO> generarReporte (MovimientoFiltroDTO filtro) {

        // Si no tiene tipoMovimiento
        if (filtro.getTipoMovimiento() != null && filtro.getTipoMovimiento().toString().isBlank()) {
            filtro.setTipoMovimiento(null);
        }

        // Si tiene codigo
        if (filtro.getCodigoProducto() != null && !filtro.getCodigoProducto().isBlank()) {
            // Validar el codigo
            Producto producto = productoService.obtenerPorCodigo(filtro.getCodigoProducto());
        }

        List<MovimientoInventario> movimientos;

        // Validar si es fecha exacta o rango de fechas
        // Si es fecha exacta
        if (isFechaExacta(filtro)) {
            // Usar consulta fecha exacta
            movimientos = movimientoInventarioRepository.buscarPorFechaExacta(
                    filtro.getCodigoProducto(),
                    filtro.getFechaExacta(),
                    filtro.getTipoMovimiento()
            );

            /* Existe una funcion de mapeo

             - Esta funcion toma la lista de movimientos y los convierte en Stream.
             - Aplica la funcion mapearADTO para convertir los MovimientosInventario a MovimientoReporteDTO.
             - Luego, toma todos los MovimientoReporteDTO y los pasa a una lista.

             movimientos.stream().map(this::mapearADTO).collect(Collectors.toList());*/

        } else {
            // Usar consulta rango de fechas

            movimientos = movimientoInventarioRepository.buscarPorFiltros(
                    filtro.getCodigoProducto(),
                    filtro.getFechaDesde(),
                    filtro.getFechaHasta(),
                    filtro.getTipoMovimiento()
            );
        }

        // Convertirlos a MovimientoReporteDTO y retornarlos
        return mapearADTOListado(movimientos);
    }

    // Generar resumen del reporte
    public ResumenMovimientoInventarioDTO generarResumenReporte (MovimientoFiltroDTO filtro) {

        List<MovimientoReporteDTO> movimientos = generarReporte(filtro);

        ResumenMovimientoInventarioDTO resumen = new ResumenMovimientoInventarioDTO();

        resumen.setMovimientos(movimientos);
        resumen.setTotalesMovimientos(TotalesMovimientoDTO.calcularTotalesMovimientos(movimientos));
        resumen.setTotalesValores(TotalesValoresDTO.calcularTotalesValores(movimientos));

        return resumen;
    }

    // ACTUALIZACIONES

    //  Crear
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

    // FUNCIONES PRIVADAS

    // Convertir el movimientoInventario en el DTORespuesta
    private MovimientoReporteDTO mapearADTO(MovimientoInventario movimiento) {
        MovimientoReporteDTO dto = new MovimientoReporteDTO();

        dto.setId(movimiento.getId());
        dto.setTipo(movimiento.getTipo());
        dto.setCantidad(movimiento.getCantidad());
        dto.setPrecioCompraVenta(movimiento.getPrecioCompraVenta());
        dto.setFecha(movimiento.getFecha());
        dto.setMotivo(movimiento.getMotivo());
        dto.setObservaciones(movimiento.getObservaciones());

        Producto producto = movimiento.getProducto();
        dto.setCodigoProducto(producto.getCodigo());
        dto.setNombreProducto(producto.getNombre());

        if (movimiento.getLote() != null) {
            dto.setNumeroLote(movimiento.getLote().getNumeroLote());
            dto.setFechaVencimientoLote(movimiento.getLote().getFechaVencimiento());

            Proveedor proveedor = movimiento.getLote().getProveedor();
            dto.setNombreProveedor(proveedor.getNombre());
        }

        return dto;
    }

    // Converitr una lista de movimientos inventario a un listado de DTORespuesta
    private List<MovimientoReporteDTO> mapearADTOListado(List<MovimientoInventario> movimientos) {
        List<MovimientoReporteDTO> listaDTO = new ArrayList<>();

        for (MovimientoInventario movimiento: movimientos) {
            MovimientoReporteDTO dto = mapearADTO(movimiento);
            listaDTO.add(dto);
        }

        return listaDTO;
    }

    // Validar filtro fechas
    private boolean isFechaExacta(MovimientoFiltroDTO filtro) {
        // Definir si es por rango de fechas o por fecha exacta
        // Si tiene fecha exacta
        if (filtro.getFechaExacta() != null) {
            // Validar que no tenga fecha desde y hasta
            if(filtro.getFechaDesde() != null || filtro.getFechaHasta() != null) {
                throw new BadRequestException("Si proporciona fecha exacta, no puede proporcionar el rango de fechasDesde o fechaHasta.");
            }

            return true;

        } else if (filtro.getFechaDesde() != null || filtro.getFechaHasta() != null) {
            // Se valida que al menos una fecha no sea nula

            if (filtro.getFechaDesde() == null || filtro.getFechaHasta() == null) {
                // Se valida que la otra fecha no sea nula
                throw new BadRequestException("Se debe proporcionar tanto fechaDesde como fechaHasta.");
            }

            // Validar que la fechaDesde sea anterior a la fechaHasta
            if (filtro.getFechaDesde().isAfter(filtro.getFechaHasta())) {
                throw new BadRequestException("La fechaDesde no puede ser igual o estar despues de la fechaHasta");
            }

            return false;

        } else {
            throw new BadRequestException("Debe proporcionar la fecha exacta o un rango de fechas");
        }
    }

}

