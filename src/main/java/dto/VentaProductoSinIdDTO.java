package dto;

import java.io.Serializable;

public class VentaProductoSinIdDTO implements Serializable{
	private Integer cantidad;
	private double precio;
	
	public VentaProductoSinIdDTO() {
	}
	
	public VentaProductoSinIdDTO(Integer cantidad, double precio) {
		super();
		this.cantidad = cantidad;
		this.precio = precio;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}
	
	
}
