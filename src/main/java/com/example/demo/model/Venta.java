package com.example.demo.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Venta implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_venta")
	private Integer id;
	
	@Column    
	private LocalDate fecha;
		
	
	//private Cliente cliente;
	@Column
	private Integer id_cliente;
	
	@OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<VentaProducto> ventaProductos;
	
	public Venta() {
		super();
	}
	
	public Venta(LocalDate fecha, Integer id_cliente) {
		this.fecha = fecha;
		this.id_cliente = id_cliente;
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
