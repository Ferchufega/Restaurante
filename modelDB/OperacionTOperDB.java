package modelDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OperacionTOperDB implements TipoOperacionDAO {

	public ConexionDB cDB = null;
	public Connection db = null;

	// Constructor
	public OperacionTOperDB() throws SQLException {
		cDB = new ConexionDB();
	}

	// Implementaciones
	@Override
	public boolean CreateRow(TipoOperacion tipoOperacion) throws SQLException {
		// TODO Auto-generated method stub
		db = cDB.conectarBD();
		String sql = "INSERT INTO public.\"TipoOperacion\"(\"Descripcion\") VALUES (?)";
		PreparedStatement st = db.prepareStatement(sql);
		st.setString(1, tipoOperacion.getDescripcion());

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
	public List<TipoOperacion> ReadRow() throws SQLException {
		// TODO Auto-generated method stub
		db = cDB.conectarBD();
		TipoOperacion tipoOperRow = null;
		List<TipoOperacion> listaTipoOper = new ArrayList<TipoOperacion>();
		String sql = "SELECT * FROM public.\"TipoOperacion\"";
		PreparedStatement st = db.prepareStatement(sql);
		ResultSet rs = st.executeQuery();

//		if (rs.getFetchSize() > 0) {
		while (rs.next()) {
			tipoOperRow = new TipoOperacion();
			tipoOperRow.setTipoOperacionID(rs.getInt("TipoOperacionID"));
			tipoOperRow.setDescripcion(rs.getString("Descripcion"));
			listaTipoOper.add(tipoOperRow);
		}
//		} else {
		// Si deseamos, podemos cargar datos ficticios...
		// Ej.: INSTANCIAR ROLES Y AÑADIR A LA LISTA, para no devolver una lista vacía.

		/* OTRA OPCIÓN ES: Controlar en el método main (ConexionDB) */
//		}

		st.close();
		db.close();

		return listaTipoOper;
	}

	@Override
	public TipoOperacion ReadRow(int id) throws SQLException {
		// TODO Auto-generated method stub
		db = cDB.conectarBD();
		TipoOperacion tipoOperRow = null;
		String sql = "SELECT * FROM public.\"TipoOperacion\" WHERE \"TipoOperacionID\"=" + id;
		PreparedStatement st = db.prepareStatement(sql);
		ResultSet rs = st.executeQuery();

//		if (rs.getFetchSize() > 0) {
		while (rs.next()) {
			tipoOperRow = new TipoOperacion();
			tipoOperRow.setTipoOperacionID(rs.getInt("TipoOperacionID"));
			tipoOperRow.setDescripcion(rs.getString("Descripcion"));
		}
//		} else {
		// Si deseamos, podemos cargar datos ficticios...
		// Ej.: INSTANCIAR ROLES Y AÑADIR A LA LISTA, para no devolver una lista vacía.

		/* OTRA OPCIÓN ES: Controlar en el método main (ConexionDB) */
//		}
		
		st.close();
		db.close();

		return tipoOperRow;
	}

	@Override
	public boolean UpdateRow(TipoOperacion tipoOperacion) throws SQLException {
		// TODO Auto-generated method stub
		boolean resultado = false;
		db = cDB.conectarBD();
		String sql = "UPDATE public.\"TipoOperacion\" SET \"Descripcion\"=? WHERE \"TipoOperacionID\"=?";
		PreparedStatement st = db.prepareStatement(sql);
		st.setString(1, tipoOperacion.getDescripcion());
		st.setInt(2, tipoOperacion.getTipoOperacionID());
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
	public boolean DeleteRow(TipoOperacion tipoOperacion) throws SQLException {
		// TODO Auto-generated method stub

		boolean resultado = false;
		db = cDB.conectarBD();
		String sql = "DELETE FROM public.\"TipoOperacion\" WHERE \"TipoOperacionID\"=?";
		PreparedStatement st = db.prepareStatement(sql);
		st.setInt(1, tipoOperacion.getTipoOperacionID());
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
