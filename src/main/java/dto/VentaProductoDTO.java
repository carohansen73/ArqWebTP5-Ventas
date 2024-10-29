package dto;

import java.io.Serializable;

public class VentaProductoDTO implements Serializable{
	private Integer id_roducto;
	private Integer cantidad;
	private double precio;
	
	public VentaProductoDTO() {
	}
	
	public VentaProductoDTO(Integer id_roducto, Integer cantidad, double precio) {
		super();
		this.id_roducto = id_roducto;
		this.cantidad = cantidad;
		this.precio = precio;
	}

	public Integer getId_roducto() {
		return id_roducto;
	}

	public void setId_roducto(Integer id_roducto) {
		this.id_roducto = id_roducto;
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
