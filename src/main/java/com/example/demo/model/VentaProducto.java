package com.example.demo.model;


import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonBackReference;

import dto.VentaProductoDTO;
import dto.VentaProductoSinIdDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@IdClass(VentaProductoId.class)
public class VentaProducto implements Serializable{
	
	@Id
	@ManyToOne
	@JoinColumn(name = "id_venta", nullable = false)
	@JsonBackReference
	private Venta venta;
	
	@Id
	@Column
	private Integer id_producto;
	
	@Column
	private Integer cantidad;
	
	@Column
	private double precio;
	
	
	public VentaProducto() {
		super();
	}
	
	public VentaProducto(Venta venta, Integer id_producto, Integer cantidad, double precio) {
		this.venta =venta;
		this.id_producto=id_producto;
		this.cantidad=cantidad;
		this.precio=precio;
	}
	
	public VentaProducto(Venta venta, VentaProductoDTO vpDTO) {
		this.venta = venta;
		this.id_producto=vpDTO.getId_producto();
		this.cantidad=vpDTO.getCantidad();
		this.precio=vpDTO.getPrecio();
	}
	
	public VentaProducto(Venta venta,Integer idProducto, VentaProductoSinIdDTO vpDTO) {
		this.venta = venta;
		this.id_producto=idProducto;
		this.cantidad=vpDTO.getCantidad();
		this.precio=vpDTO.getPrecio();
	}
	
	public double getTotal() {
		return this.getPrecio()*this.getCantidad();
	}
	public Venta getVenta() {
		return venta;
	}
	public void setVenta(Venta venta) {
		this.venta = venta;
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
