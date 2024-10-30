package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Venta;
import com.example.demo.model.VentaProducto;

import dto.FacturacionPorClienteDTO;
import dto.VentaDTO;

//import dto.ReporteVentasPorDiaDTO;

@Repository
public interface VentaRepository extends JpaRepository<Venta,Integer>{

	@Query("SELECT new dto.VentaDTO(v.id,v.fecha,SUM(vp.cantidad*vp.precio)) "
			+ "FROM Venta v "
			+ "JOIN VentaProducto vp "
			+ "ON v.id = vp.venta.id "
			+ "GROUP BY v.fecha,vp.id_producto")
	
	public Iterable<VentaDTO> getReporteVentas();
	
	/*
	@Query("SELECT new dto.ReporteVentaDiaDTO(v.fecha,vp.id_producto,SUM(vp.cantidad),SUM(vp.cantidad*vp.precio)) "
			+ "FROM Venta v "
			+ "JOIN VentaProducto vp "
			+ "ON v.id = vp.venta.id "
			+ "GROUP BY v.fecha,vp.id_producto")
	public Iterable<ReporteVentasPorDiaDTO> gerReporteVentasDetallado();
	*/
	
	@Query("SELECT new dto.FacturacionPorClienteDTO(v.id_cliente, SUM(vp.cantidad*vp.precio)) "
			+ "FROM Venta v "
			+ "JOIN VentaProducto vp "
			+ "ON v.id = vp.venta.id "
			+ "GROUP BY v.id_cliente")
	public Iterable<FacturacionPorClienteDTO> facturacionPorCliente();

	@Query("SELECT vp.id_producto "
			+ "FROM VentaProducto vp "
			+ "GROUP BY vp.id_producto "
			+ "ORDER BY SUM(vp.cantidad) DESC")
	public List<Integer> masVendido();
	
}
