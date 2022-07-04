package modelDB;

public class Marca {
	
	private Integer marcaID;
	private String nombre;

	// Constructores
		public Marca() {
			super();
		}

		public Marca(Integer marcaID, String nombre) {
			super();
			this.marcaID = marcaID;
			this.nombre = nombre;
		}

		public Marca(Integer marcaID) {
			super();
			this.marcaID = marcaID;
		}

		// Getters - Setters
		public Integer getMarcaID() {
			return marcaID;
		}

		public void setMarcaID(Integer marcaID) {
			this.marcaID = marcaID;
		}

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

		@Override
		public String toString() {
			String resu= "Marca: " + getMarcaID() + "\nNombre: " + getNombre() + "\n";
			
			if(this.getMarcaID()==null)
				resu= "\nNombre de la marca: " + getNombre()+"\n";
			
			return resu; 
		}
		
		
		
}
