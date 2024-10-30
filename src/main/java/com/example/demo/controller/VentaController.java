package com.example.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Venta;
import com.example.demo.model.VentaProducto;
import com.example.demo.service.VentaService;

import dto.FacturacionPorClienteDTO;
import dto.PostVentaDTO;
import dto.VentaDTO;
import dto.VentaProductoDTO;
import dto.VentaProductoSinIdDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("ventas")
public class VentaController {
    
    @Autowired
    private final VentaService ventaService;
    
    public VentaController(VentaService ventaService) {
        super();
        this.ventaService = ventaService;
    }
    
    @GetMapping("/")
    @Operation(summary = "Obtener todas las ventas", description = "Devuelve una lista de todas las ventas.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ventas obtenidas correctamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Iterable<Venta>> getAll() {
        Iterable<Venta> ventas = ventaService.getAll();
        return new ResponseEntity<>(ventas, HttpStatus.OK);
    }
    
    @PostMapping("/")
    @Operation(summary = "Guardar una nueva venta", description = "Crea una nueva venta.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Venta creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Error en la solicitud")
    })
    public ResponseEntity<String> save(@RequestBody PostVentaDTO venta) {
        return ventaService.save(venta);
    }

    @GetMapping("/clientes")
    @Operation(summary = "Facturación por cliente", description = "Obtiene la facturación agrupada por cliente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Facturación obtenida correctamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Iterable<FacturacionPorClienteDTO>> facturacionPorCliente() {
        return ventaService.facturacionPorCliente();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una venta", description = "Elimina una venta por su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Venta eliminada exitosamente")
    })
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        return ventaService.delete(id);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar una venta", description = "Actualiza los detalles de una venta existente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Venta actualizada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Venta no encontrada"),
        @ApiResponse(responseCode = "400", description = "Error en la solicitud")
    })
    public ResponseEntity<String> patch(@RequestBody Venta venta, @PathVariable Integer id) {
        return ventaService.patch(id, venta);
    }

    @GetMapping("/reporte")
    @Operation(summary = "Obtener reporte de ventas", description = "Devuelve un reporte de ventas.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reporte obtenido correctamente"),
    })
    public ResponseEntity<Iterable<VentaDTO>> getReporte() {
        return ventaService.getReporteVentas();
    }

    @PostMapping("/{idVenta}/productos/")
    @Operation(summary = "Agregar producto a una venta", description = "Agrega un producto a una venta existente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Producto agregado a la venta"),
        @ApiResponse(responseCode = "404", description = "Venta o producto no encontrado")
    })
    public ResponseEntity<?> addVentaProducto(@RequestBody VentaProductoDTO ventaProducto, @PathVariable Integer idVenta) {
        return ventaService.addVentaProducto(idVenta, ventaProducto);
    }

    @PatchMapping("/{idVenta}/productos/{idProducto}")
    @Operation(summary = "Actualizar producto de una venta", description = "Actualiza un producto asociado a una venta.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Venta o producto no encontrado"),
        @ApiResponse(responseCode = "400", description = "Error en la solicitud")
    })
    public ResponseEntity<?> patchVentaProducto( @PathVariable Integer idVenta, @PathVariable Integer idProducto,
    		@io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json",
    			      schema = @Schema(implementation = VentaProductoSinIdDTO.class),
    			      examples = @ExampleObject(value = "{ \"candidad\": \"5\", \"precio\": \"99.9\" }"))) @RequestBody  VentaProductoSinIdDTO ventaProductoSinDTO) {
        return ventaService.patchVentaProducto(idVenta, idProducto, ventaProductoSinDTO);
    }

    @DeleteMapping("/{idVenta}/productos/{idProducto}")
    @Operation(summary = "Eliminar producto de una venta", description = "Elimina un producto asociado a una venta.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto eliminado de la venta"),
        @ApiResponse(responseCode = "404", description = "Venta o producto no encontrado")
    })
    public ResponseEntity<?> deleteVentaProducto(@PathVariable Integer idVenta, @PathVariable Integer idProducto) {
        return ventaService.deleteVentaProducto(idVenta, idProducto);
    }

    @GetMapping("/masVendido")
    @Operation(summary = "Obtener el producto más vendido", description = "Devuelve el ID del producto más vendido.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto más vendido obtenido correctamente"),
    })
    public ResponseEntity<Integer> masVendido() {
        return ventaService.masVendido();
    }

    @GetMapping("/{idVenta}/productos/")
    @Operation(summary = "Obtener productos de una venta", description = "Devuelve la lista de productos asociados a una venta.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Productos obtenidos correctamente"),
        @ApiResponse(responseCode = "404", description = "Venta no encontrada")
    })
    public ResponseEntity<?> getProductosVenta(@PathVariable Integer idVenta) {
        return ventaService.getProductosVenta(idVenta);
    }
}
