package dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;



public class PostVentaDTO implements Serializable{
	private Integer id;
	private LocalDate fecha;
	private Integer id_cliente;
	
	private List<VentaProductoDTO> ventaProductos;
	
	
	
	public PostVentaDTO(Integer id, LocalDate fecha, Integer id_cliente,List<VentaProductoDTO> ventaProductos) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.id_cliente = id_cliente;
		this.ventaProductos = ventaProductos;
	}
	
	public Iterator<VentaProductoDTO> iteradorVentaProductos(){
		return this.ventaProductos.iterator();
	}
	
	public List<VentaProductoDTO> getVentaProductos() {
		return ventaProductos;
	}

	public void setVentaProductos(List<VentaProductoDTO> ventaProductos) {
		this.ventaProductos = ventaProductos;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public LocalDate getFecha() {
		return fecha;
	}
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	
	public Integer getId_cliente() {
		return id_cliente;
	}

	public void setId_cliente(Integer id_cliente) {
		this.id_cliente = id_cliente;
	}
	
	@Override
	public String toString() {
		return "" + ventaProductos.iterator().next().getCantidad();
	}
}