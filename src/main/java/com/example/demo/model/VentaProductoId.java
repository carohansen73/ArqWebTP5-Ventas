package com.example.demo.model;

import java.io.Serializable;
import java.util.Objects;

public class VentaProductoId implements Serializable{

	private Integer venta;
	private Integer id_producto;
	
	public VentaProductoId() {
	}
	
	public VentaProductoId(Integer venta, Integer producto) {
		super();
		this.venta = venta;
		this.id_producto = producto;
	}
	public Integer getVenta() {
		return venta;
	}
	public void setVenta(Integer venta) {
		this.venta = venta;
	}
	public Integer getProducto() {
		return id_producto;
	}
	public void setProducto(Integer producto) {
		this.id_producto = producto;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VentaProductoId that = (VentaProductoId) o;
        return Objects.equals(venta, that.venta) && 
               Objects.equals(id_producto, that.id_producto);
    }
}
