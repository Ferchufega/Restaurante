package modelDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OperacionRolDB implements RolDAO {

	public ConexionDB cDB = null;
	public Connection db = null;

	// Constructor
	public OperacionRolDB() throws SQLException {
		cDB = new ConexionDB();
	}

	// Implementaciones
	@Override
	public boolean CreateRow(Rol rol) throws SQLException {
		// TODO Auto-generated method stub
		db = cDB.conectarBD();
		String sql = "INSERT INTO public.\"Rol\"(\"Descripcion\") VALUES (?)";
		PreparedStatement st = db.prepareStatement(sql);
		st.setString(1, rol.getDescripcion());

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
	public List<Rol> ReadRow() throws SQLException {
		// TODO Auto-generated method stub
		db = cDB.conectarBD();
		Rol rolRow = null;
		List<Rol> listaRol = new ArrayList<Rol>();
		String sql = "SELECT * FROM public.\"Rol\"";
		PreparedStatement st = db.prepareStatement(sql);
		ResultSet rs = st.executeQuery();

//		if (rs.getFetchSize() > 0) {
		while (rs.next()) {
			rolRow = new Rol();
			rolRow.setRolID(rs.getInt("RolID"));
			rolRow.setDescripcion(rs.getString("Descripcion"));
			listaRol.add(rolRow);
		}
//		} else {
		// Si deseamos, podemos cargar datos ficticios...
		// Ej.: INSTANCIAR ROLES Y AÑADIR A LA LISTA, para no devolver una lista vacía.

		/* OTRA OPCIÓN ES: Controlar en el método main (ConexionDB) */
//		}

		st.close();
		db.close();

		return listaRol;
	}

	@Override
	public Rol ReadRow(int id) throws SQLException {
		// TODO Auto-generated method stub
		db = cDB.conectarBD();
		Rol rolRow = null;
		String sql = "SELECT * FROM public.\"Rol\" WHERE \"RolID\"=" + id;
		PreparedStatement st = db.prepareStatement(sql);
		ResultSet rs = st.executeQuery();

//		if (rs.getFetchSize() > 0) {
		while (rs.next()) {
			rolRow = new Rol();
			rolRow.setRolID(rs.getInt("RolID"));
			rolRow.setDescripcion(rs.getString("Descripcion"));
		}
//		} else {
		// Si deseamos, podemos cargar datos ficticios...
		// Ej.: INSTANCIAR ROLES Y AÑADIR A LA LISTA, para no devolver una lista vacía.

		/* OTRA OPCIÓN ES: Controlar en el método main (ConexionDB) */
//		}
		
		st.close();
		db.close();

		return rolRow;
	}

	@Override
	public boolean UpdateRow(Rol rol) throws SQLException {
		// TODO Auto-generated method stub
		boolean resultado = false;
		db = cDB.conectarBD();
		String sql = "UPDATE public.\"Rol\" SET \"Descripcion\"=? WHERE \"RolID\"=?";
		PreparedStatement st = db.prepareStatement(sql);
		st.setString(1, rol.getDescripcion());
		st.setInt(2, rol.getRolID());
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
	public boolean DeleteRow(Rol rol) throws SQLException {
		// TODO Auto-generated method stub

		boolean resultado = false;
		db = cDB.conectarBD();
		String sql = "DELETE FROM public.\"Rol\" WHERE \"RolID\"=?";
		PreparedStatement st = db.prepareStatement(sql);
		st.setInt(1, rol.getRolID());
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
