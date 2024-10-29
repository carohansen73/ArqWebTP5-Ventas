package com.example.demo.service;

import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.model.Venta;
import com.example.demo.repository.VentaRepository;
import com.example.demo.utils.PatcherVenta;

import dto.FacturacionPorClienteDTO;
import dto.ReporteVentasPorDiaDTO;
import dto.VentaDTO;

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

	public ResponseEntity<Iterable<VentaDTO>> getReporteVentas(){
		return ResponseEntity.ok(ventaRepository.getReporteVentas());
	}
	/*
	public ResponseEntity<Iterable<ReporteVentasPorDiaDTO>> getReporteVentasDetallado(){
		restTemplate.getForEntity(null, ClienteDTO.class)
		return null;
	}*/

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

	public ResponseEntity<String> delete(Integer id) {
		ventaRepository.deleteById(id);
		return ResponseEntity.ok(null);
	}

	public ResponseEntity<String> patch(Integer id, Venta ventaIncompleta) {
		//tries to edit id
		if((ventaIncompleta.getId()!=null)) {
			return new ResponseEntity<String>("No se puede editar id",HttpStatus.BAD_REQUEST);
		}
		if((ventaIncompleta.getId_cliente() !=null) && !this.checkClienteValido(id)) {
				return new ResponseEntity<String>("idCliente Invalido",HttpStatus.BAD_REQUEST);
			}
		Venta venta;
		try {
			venta = ventaRepository.findById(id).orElseThrow();
		}catch(NoSuchElementException e) {
			return new ResponseEntity<String>("id Invalido",HttpStatus.NOT_FOUND);
		}
		PatcherVenta.patchVenta(ventaIncompleta, venta);
		ventaRepository.save(venta);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
}
