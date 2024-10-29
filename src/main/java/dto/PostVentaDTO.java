package dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class PostVentaDTO implements Serializable{
	private Integer id;
	private LocalDate fecha;
	private double total;
	private List<VentaProductoDTO> ventaProductos;
	
	
	
	public PostVentaDTO(Integer id, LocalDate fecha, double total) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.total = total;
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
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	
	
}