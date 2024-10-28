package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Venta;
import com.example.demo.service.VentaService;

import dto.FacturacionPorClienteDTO;

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
	public ResponseEntity<String> save(@RequestBody Venta venta) {
		return ventaService.save(venta);
	}
	
	@GetMapping("/clientes")
	public ResponseEntity<Iterable<FacturacionPorClienteDTO>> facturacionPorCliente(){
		return ventaService.facturacionPorCliente();
	}
	
}
