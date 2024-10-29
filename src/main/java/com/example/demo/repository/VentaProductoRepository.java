package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Venta;
import com.example.demo.model.VentaProducto;
import com.example.demo.model.VentaProductoId;

import dto.VentaProductoDTO;

@Repository
public interface VentaProductoRepository extends JpaRepository<VentaProducto,VentaProductoId>{
	
	@Query("INSERT INTO VentaProducto (venta, id_producto, cantidad,precio) "
			+ "VALUES (:idVenta, :id_producto, :cantidad, :precio)")
	void addVentaProducto(Integer idVenta, Integer id_producto, Integer cantidad, double precio);

}
