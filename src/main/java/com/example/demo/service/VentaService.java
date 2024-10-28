package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.model.Venta;
import com.example.demo.repository.VentaRepository;

import dto.FacturacionPorClienteDTO;
import dto.ReporteVentaDiaDTO;

@Service
public class VentaService {
	
	public static final String clienteApiUri = "http://localhost:8080/clientes/";
	@Autowired
	private final VentaRepository ventaRepository;
	@Autowired
	private final RestTemplate restTemplate; 
	
	public VentaService(VentaRepository ventaRepository,RestTemplate restTemplate) {
		this.ventaRepository = ventaRepository;
		this.restTemplate = restTemplate;
	}

	public Iterable<ReporteVentaDiaDTO> getReporteVentas(){
		return null;
	}

	public Iterable<Venta> getAll(){
		return ventaRepository.findAll();
	}
	
	public ResponseEntity<String> save(Venta venta) {
		if(this.checkClienteValido(venta.getId_cliente())){
			ventaRepository.save(venta);
			return new ResponseEntity<String>(HttpStatus.CREATED);
		}else{
			return new ResponseEntity<String>("El cliente no existe",HttpStatus.CONFLICT);
		}
			
	}
	
	private boolean checkClienteValido(int clienteId) {
		return this.restTemplate.getForEntity(clienteApiUri + "clienteId",Object.class).getStatusCode().is2xxSuccessful();
	}
	
	
	public ResponseEntity<Iterable<FacturacionPorClienteDTO>> facturacionPorCliente() {
		// TODO Auto-generated method stub
		return ResponseEntity.ok(ventaRepository.facturacionPorCliente());
	}
	
}
