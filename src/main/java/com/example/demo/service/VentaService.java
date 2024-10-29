package com.example.demo.service;

import java.net.ConnectException;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.example.demo.model.Venta;
import com.example.demo.repository.VentaRepository;
import com.example.demo.utils.GenericObjectPatcher;
import com.example.demo.utils.PatcherVenta;
import com.example.demo.utils.TokenClienteHandler;

import dto.FacturacionPorClienteDTO;
import dto.ReporteVentasPorDiaDTO;
import dto.VentaDTO;

@Service
public class VentaService {
	@Autowired
	private final TokenClienteHandler tokenClienteHandler;
	public static final String clienteApiUri = "http://localhost:8081/clientes/";
	@Autowired
	private final VentaRepository ventaRepository;
	@Autowired
	private final RestTemplate restTemplate; 
	
	public VentaService(VentaRepository ventaRepository,RestTemplate restTemplate,TokenClienteHandler tokenClienteHandler) {
		this.ventaRepository = ventaRepository;
		this.restTemplate = restTemplate;
		this.tokenClienteHandler = tokenClienteHandler;
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
	
	private boolean checkClienteValido(int clienteId){
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(this.tokenClienteHandler.getToken());
		HttpEntity<String> httpEntity = new HttpEntity<>(headers);
		System.out.println(clienteApiUri + clienteId);
		try {
			this.restTemplate.exchange(clienteApiUri + clienteId,HttpMethod.GET, httpEntity,Object.class).getStatusCode().is2xxSuccessful();;
			return true;
		}catch(HttpClientErrorException e) {
			return false;
		}
		//return this.restTemplate.exchange(clienteApiUri + clienteId,HttpMethod.GET, httpEntity,Object.class).getStatusCode().is2xxSuccessful();
		//return this.restTemplate.getForEntity(clienteApiUri + "clienteId",Object.class).getStatusCode().is2xxSuccessful();
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
		
		if((ventaIncompleta.getId_cliente() !=null) && !this.checkClienteValido(ventaIncompleta.getId_cliente())) {
				return new ResponseEntity<String>("idCliente Invalido",HttpStatus.BAD_REQUEST);
		}
		
		try {
			Venta venta = ventaRepository.findById(id).orElseThrow();
			GenericObjectPatcher.patch(ventaIncompleta, venta);
			ventaRepository.save(venta);
			return new ResponseEntity<String>(HttpStatus.OK);
		}catch(NoSuchElementException e) {
			return new ResponseEntity<String>("id Invalido",HttpStatus.NOT_FOUND);
		}
	}
	
}
