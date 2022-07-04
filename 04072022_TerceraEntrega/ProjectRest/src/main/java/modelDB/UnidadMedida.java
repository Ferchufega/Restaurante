package modelDB;

public class UnidadMedida {

	private Integer unidadMedidaID;
	private String descripcion;

	// Constructores
	public UnidadMedida() {
		super();
	}

	public UnidadMedida(Integer unidadMedidaID, String descripcion) {
		super();
		this.unidadMedidaID = unidadMedidaID;
		this.descripcion = descripcion;
	}

	public UnidadMedida(Integer unidadMedidaID) {
		super();
		this.unidadMedidaID = unidadMedidaID;
	}

	// Getters - Setters
	public Integer getUnidadMedidaID() {
		return unidadMedidaID;
	}

	public void setUnidadMedidaID(Integer unidadMedidaID) {
		this.unidadMedidaID = unidadMedidaID;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	// toString
	@Override
	public String toString() {
		return "\nUnidad Medida ID: " + getUnidadMedidaID() + "\nDescripción: " + getDescripcion();
	}

}
