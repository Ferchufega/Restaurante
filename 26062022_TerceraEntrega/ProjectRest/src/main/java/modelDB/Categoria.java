package modelDB;

public class Categoria {

	private Integer categoriaID;
	private String descripcion;
	private String activo;
	
	
	public Categoria() {
		
	}


	public Categoria(Integer categoriaID, String descripcion, String activo) {
		this.categoriaID = categoriaID;
		this.descripcion = descripcion;
		this.activo = activo;
	}


	public Integer getCategoriaID() {
		return categoriaID;
	}


	public void setCategoriaID(Integer categoriaID) {
		this.categoriaID = categoriaID;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public String getActivo() {
		return activo;
	}


	public void setActivo(String activo) {
		this.activo = activo;
	}


	@Override
	public String toString() {
		return "CategoriaID: " + getCategoriaID() + "\nDescripcion: " + getDescripcion()
				+ "\nActivo: " + getActivo() + "\n";
	}
	
	
	
}
