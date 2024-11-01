package dto;

import java.io.Serializable;

public class VentaProductoDTO implements Serializable{
	private Integer id_producto;
	private Integer cantidad;
	private double precio;
	
	public VentaProductoDTO() {
	}
	
	public VentaProductoDTO(Integer id_producto, Integer cantidad, double precio) {
		super();
		this.id_producto = id_producto;
		this.cantidad = cantidad;
		this.precio = precio;
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

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}
	
	
}
