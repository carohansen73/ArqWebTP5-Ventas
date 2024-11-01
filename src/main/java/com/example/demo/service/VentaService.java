package com.example.demo.service;

import java.net.ConnectException;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

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
import com.example.demo.model.VentaProducto;
import com.example.demo.model.VentaProductoId;
import com.example.demo.repository.VentaProductoRepository;
import com.example.demo.repository.VentaRepository;
import com.example.demo.utils.GenericObjectPatcher;
import com.example.demo.utils.PatcherVenta;
import com.example.demo.utils.TokenClienteHandler;

import dto.FacturacionPorClienteDTO;
import dto.PostVentaDTO;
import dto.ReporteVentasPorDiaDTO;
import dto.VentaDTO;
import dto.VentaProductoDTO;
import dto.VentaProductoSinIdDTO;

@Service
public class VentaService {
	@Autowired
	private final TokenClienteHandler tokenClienteHandler;
	public static final String clienteApiUri = "http://localhost:8081/clientes/";
	public static final String productoApiUri = "http://localhost:8089/productos/";
	@Autowired
	private final VentaRepository ventaRepository;
	@Autowired
	private final RestTemplate restTemplate; 
	@Autowired
	private final VentaProductoRepository ventaProductoRepository;
	
	public VentaService(VentaRepository ventaRepository,RestTemplate restTemplate,TokenClienteHandler tokenClienteHandler,VentaProductoRepository  ventaProductoRepository) {
		this.ventaRepository = ventaRepository;
		this.ventaProductoRepository = ventaProductoRepository;
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
	
	public ResponseEntity<String> save(PostVentaDTO ventaDTO) {
		
		if(this.checkClienteValido(ventaDTO.getId_cliente())){
			Venta venta = new Venta(ventaDTO);
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

	public ResponseEntity<?> patchVentaProducto(Integer idVenta, Integer idProducto, VentaProductoSinIdDTO ventaProductoDTO) {
		
		if(this.checkProductoValido(idProducto)) {
				return new ResponseEntity<String>("idProducto Invalido",HttpStatus.BAD_REQUEST);
		}
		try {
			VentaProducto vpIncompleto = new VentaProducto(null,idProducto,ventaProductoDTO);
			VentaProducto vp = ventaProductoRepository.findById(new VentaProductoId(idVenta,idProducto)).orElseThrow();
			GenericObjectPatcher.patch(vpIncompleto, vp);
			ventaProductoRepository.save(vp);
			return new ResponseEntity<String>(HttpStatus.OK);
		}catch(NoSuchElementException e) {
			return new ResponseEntity<String>("id Invalido",HttpStatus.NOT_FOUND);
		}
	}

	private boolean checkProductoValido(Integer id_producto) {
		/*HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(this.tokenClienteHandler.getToken());
		HttpEntity<String> httpEntity = new HttpEntity<>(headers);*/
		try {
			this.restTemplate.getForEntity(productoApiUri, Object.class).getStatusCode().is2xxSuccessful();
			//this.restTemplate.exchange(productoApiUri + id_producto,HttpMethod.GET, httpEntity,Object.class).getStatusCode().is2xxSuccessful();
			return true;
		}catch(HttpClientErrorException e) {
			return false;
		}
	}

	public ResponseEntity<?> addVentaProducto(Integer idVenta, VentaProductoDTO ventaProducto) {
		if(!this.checkProductoValido(ventaProducto.getId_producto())) {
			return new ResponseEntity<String>("idProducto Invalido",HttpStatus.BAD_REQUEST);
		}
		Optional<Venta> venta = ventaRepository.findById(idVenta);
		try{
			VentaProducto vp = new VentaProducto(venta.orElseThrow(),ventaProducto);
			ventaProductoRepository.save(vp);
			return new ResponseEntity<String>(HttpStatus.CREATED);
			
		}catch(NoSuchElementException e) {
			return new ResponseEntity<String>("La venta no existe",HttpStatus.NOT_FOUND);
		}
		
	}

	public ResponseEntity<?> deleteVentaProducto(Integer idVenta, Integer idProducto) {
		VentaProductoId id = new VentaProductoId(idVenta,idProducto);
		ventaProductoRepository.deleteById(id);
		return ResponseEntity.ok(null);
	}

	public ResponseEntity<Integer> masVendido() {
;		return new ResponseEntity<Integer>(ventaRepository.masVendido().get(0),HttpStatus.OK);
	}

	public ResponseEntity<?> getProductosVenta(Integer idVenta) {
		// TODO Auto-generated method stub
		return ResponseEntity.ok(ventaProductoRepository.productosVentas(idVenta));
	}
		
}
