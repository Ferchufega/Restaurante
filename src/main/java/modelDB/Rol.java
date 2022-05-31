package modelDB;

public class Rol {

	private Integer rolID;
	private String descripcion;

	// Constructores
	public Rol() {
		super();
	}

	public Rol(Integer rolID, String descripcion) {
		super();
		this.rolID = rolID;
		this.descripcion = descripcion;
	}

	public Rol(Integer rolID) {
		super();
		this.rolID = rolID;
	}

	// Getters - Setters
	public Integer getRolID() {
		return rolID;
	}

	public void setRolID(Integer rolID) {
		this.rolID = rolID;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
