package dto;

import java.io.Serializable;
import java.time.LocalDate;

public class ReporteVentaDiaDTO implements Serializable{
	
	private LocalDate fecha;
	private Integer id_producto;
	private Integer cantidad;
	private double total;
	
	public ReporteVentaDiaDTO(Integer id_producto) {
		
	}
	
	public ReporteVentaDiaDTO(LocalDate fecha, Integer id_producto, double cantidad, double total) {
		super();
		this.fecha = fecha;
		this.id_producto = id_producto;
		this.cantidad = (int) cantidad;
		this.total = total;
	}
	public LocalDate getFecha() {
		return fecha;
	}
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	public Integer getId_producto() {
		return id_producto;
	}
	public void setId_producto(Integer id_producto) {
		this.id_producto = id_producto;
	}
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	
	
}
