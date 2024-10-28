package dto;

public class FacturacionPorClienteDTO {
	private int cliente;
	private double total;
	
	
	public FacturacionPorClienteDTO() {
		
	}
	
	public FacturacionPorClienteDTO(int cliente, double total) {
		super();
		this.cliente = cliente;
		this.total = total;
	}
	
	public int getCliente() {
		return cliente;
	}
	public void setCliente(int cliente) {
		this.cliente = cliente;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	
	
}
