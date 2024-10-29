package com.example.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Venta;
import com.example.demo.model.VentaProducto;
import com.example.demo.service.VentaService;

import dto.FacturacionPorClienteDTO;
import dto.PostVentaDTO;
import dto.VentaDTO;
import dto.VentaProductoDTO;

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
	public ResponseEntity<Iterable<Venta>> getAll() {
		
		Iterable<Venta> ventas = ventaService.getAll();
		return new ResponseEntity<Iterable<Venta>>(ventas,HttpStatus.OK);
	}
	
	@PostMapping("/")
	public ResponseEntity<String> save(@RequestBody PostVentaDTO venta) {
		return ventaService.save(venta);
	}
	
	@GetMapping("/clientes")
	public ResponseEntity<Iterable<FacturacionPorClienteDTO>> facturacionPorCliente(){
		return ventaService.facturacionPorCliente();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable Integer id){
		return ventaService.delete(id);
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<String> patch(@RequestBody Venta venta,@PathVariable Integer id){
		return ventaService.patch(id,venta);
	}
	
	@GetMapping("/reporte")
	public ResponseEntity<Iterable<VentaDTO>> getReporte(){
		return ventaService.getReporteVentas();
	}
	@PostMapping("/{idVenta}/productos/")
	public ResponseEntity<?> addVentaProducto(@RequestBody VentaProductoDTO ventaProducto,@PathVariable Integer idVenta){
		return ventaService.addVentaProducto(idVenta,ventaProducto);
	}
	
	@PatchMapping("/{idVenta}/productos/{idProducto}")
	public ResponseEntity<?> patchVentaProducto(@PathVariable Integer idVenta, @PathVariable Integer idProducto,@RequestBody VentaProducto ventaProducto){
		return ventaService.patchVentaProducto(idVenta,idProducto,ventaProducto);
	}
	
	@DeleteMapping("/{idVenta}/productos/{idProducto}")
	public ResponseEntity<?> deleteVentaProducto(@PathVariable Integer idVenta,@PathVariable Integer idProducto){
		return ventaService.deleteVentaProducto(idVenta,idProducto);
	}
	
	@GetMapping("/masVendido")
	public ResponseEntity<Integer> masVendido(){
		return ventaService.masVendido();
	}
}
