package modelDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OperacionMarcaDB implements MarcaDAO {

	public ConexionDB cDB = null;
	public Connection db = null;

	// Constructor
	public OperacionMarcaDB() throws SQLException {
		cDB = new ConexionDB();
	}

	// Implementaciones
	@Override
	public boolean CreateRow(Marca marca) throws SQLException {
		db = cDB.conectarBD();
		String sql = "INSERT INTO public.\"Marca\"(\"Nombre\") VALUES (?)";
		PreparedStatement st = db.prepareStatement(sql);
		st.setString(1, marca.getNombre());

		int resultado = st.executeUpdate();
		st.close();
		if (resultado > 0) {
			return true;
		}
		db.close();
		cDB.cerrarBD();
		return false;
	}

	@Override
	public List<Marca> ReadRow() throws SQLException {
		db = cDB.conectarBD();
		Marca marRow = null;
		List<Marca> listaMarca = new ArrayList<Marca>();
		String sql = "SELECT * FROM public.\"Marca\"";
		PreparedStatement st = db.prepareStatement(sql);
		ResultSet rs = st.executeQuery();

		//if (rs.getFetchSize() > 0) {
			while (rs.next()) {
				marRow = new Marca();
				marRow.setMarcaID(rs.getInt("MarcaID"));
				marRow.setNombre(rs.getString("Nombre"));
				listaMarca.add(marRow);
			}
		//} else {
			// Si deseamos, podemos cargar datos ficticios...
			// Ej.: INSTANCIAR ROLES Y AÑADIR A LA LISTA, para no devolver una lista vacía.

			/* OTRA OPCIÓN ES: Controlar en el método main (ConexionDB) */
		//}
		st.close();
        db.close();

		return listaMarca;
	}

	@Override
	public Marca ReadRow(int id) throws SQLException {
		db = cDB.conectarBD();
		Marca marRow = null;
		String sql = "SELECT * FROM public.\"Marca\" WHERE \"MarcaID\"=" + id;
		PreparedStatement st = db.prepareStatement(sql);
		ResultSet rs = st.executeQuery();

		//if (rs.getFetchSize() > 0) {
			while (rs.next()) {
				marRow = new Marca();
				marRow.setMarcaID(rs.getInt("MarcaID"));
				marRow.setNombre(rs.getString("Nombre"));
			}
		//} else {
			// Si deseamos, podemos cargar datos ficticios...
			// Ej.: INSTANCIAR ROLES Y AÑADIR A LA LISTA, para no devolver una lista vacía.

			/* OTRA OPCIÓN ES: Controlar en el método main (ConexionDB) */
		//}
		st.close();
        db.close();

		return marRow;
	}

	@Override
	public boolean UpdateRow(Marca marca) throws SQLException {
		boolean resultado = false;
		db = cDB.conectarBD();
		String sql = "UPDATE public.\"Marca\" SET \"Nombre\"=? WHERE \"MarcaID\"=?";
		PreparedStatement st = db.prepareStatement(sql);
		st.setString(1, marca.getNombre());
		st.setInt(2, marca.getMarcaID());
		int cantidad = st.executeUpdate();
		st.close();

		if (cantidad == 0) {
			resultado = false;
		} else {
			resultado = true;
		}

		db.close();
		cDB.cerrarBD();
		return resultado;
	}

	@Override
	public boolean DeleteRow(Marca marca) throws SQLException {
		boolean resultado = false;
		db = cDB.conectarBD();
		String sql = "DELETE FROM public.\"Marca\" WHERE \"MarcaID\"=?";
		PreparedStatement st = db.prepareStatement(sql);
		st.setInt(1, marca.getMarcaID());
		int cantidad = st.executeUpdate();
		st.close();

		if (cantidad == 0) {
			resultado = false;
		} else {
			resultado = true;
		}

		db.close();
		cDB.cerrarBD();
		return resultado;
	}
	
}
