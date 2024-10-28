package dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class ReporteVentasPorDiaDTO implements Serializable{
	
	private LocalDate fecha;
	private List<VentaDTO> ventaDTO;
	
	public ReporteVentasPorDiaDTO(Integer id_producto) {
		
	}
	
	public ReporteVentasPorDiaDTO(LocalDate fecha,List<VentaDTO> ventaDTO) {
		super();
		this.fecha = fecha;
		this.ventaDTO = ventaDTO;
		
	}
	
	public LocalDate getFecha() {
		return fecha;
	}
	
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public List<VentaDTO> getVentaDTO() {
		return ventaDTO;
	}

	public void setVentaDTO(List<VentaDTO> ventaDTO) {
		this.ventaDTO = ventaDTO;
	}
	
	
}
