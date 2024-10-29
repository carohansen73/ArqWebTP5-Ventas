package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Venta;

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
	
}
