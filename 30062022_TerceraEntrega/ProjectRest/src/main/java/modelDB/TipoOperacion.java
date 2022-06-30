package modelDB;

public class TipoOperacion {

	private Integer tipoOperacionID;
	private String descripcion;

	// Constructores
	public TipoOperacion() {
		super();
	}

	public TipoOperacion(Integer tipoOperacionID, String descripcion) {
		super();
		this.tipoOperacionID = tipoOperacionID;
		this.descripcion = descripcion;
	}

	public TipoOperacion(Integer tipoOperacionID) {
		super();
		this.tipoOperacionID = tipoOperacionID;
	}

	// Getters - Setters
	public Integer getTipoOperacionID() {
		return tipoOperacionID;
	}

	public void setTipoOperacionID(Integer tipoOperacionID) {
		this.tipoOperacionID = tipoOperacionID;
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
		return "\nTipo Operacion ID: " + getTipoOperacionID() + "\nDescripción: " + getDescripcion();
	}

}
