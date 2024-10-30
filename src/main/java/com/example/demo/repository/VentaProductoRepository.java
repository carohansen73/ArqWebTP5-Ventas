package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Venta;
import com.example.demo.model.VentaProducto;
import com.example.demo.model.VentaProductoId;

import dto.VentaProductoDTO;

@Repository
public interface VentaProductoRepository extends JpaRepository<VentaProducto,VentaProductoId>{
	
	@Query("SELECT new dto.VentaProductoDTO(vp.id_producto,vp.cantidad,vp.precio) "
			+ "FROM VentaProducto vp "
			+ "JOIN Venta v "
			+ "ON v.id = vp.venta.id")
	public List<VentaProductoDTO> productosVentas(Integer idVenta);
}
