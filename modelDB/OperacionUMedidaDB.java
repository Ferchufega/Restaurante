package modelDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OperacionUMedidaDB implements UMedidaDAO {

	public ConexionDB cDB = null;
	public Connection db = null;

	// Constructor
	public OperacionUMedidaDB() throws SQLException {
		cDB = new ConexionDB();
	}

	// Implementaciones
	@Override
	public boolean CreateRow(UnidadMedida unidadMedida) throws SQLException {
		// TODO Auto-generated method stub
		db = cDB.conectarBD();
		String sql = "INSERT INTO public.\"UnidadMedida\"(\"Descripcion\") VALUES (?)";
		PreparedStatement st = db.prepareStatement(sql);
		st.setString(1, unidadMedida.getDescripcion());

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
	public List<UnidadMedida> ReadRow() throws SQLException {
		// TODO Auto-generated method stub
		db = cDB.conectarBD();
		UnidadMedida uMedidaRow = null;
		List<UnidadMedida> listaUnidadMedida = new ArrayList<UnidadMedida>();
		String sql = "SELECT * FROM public.\"UnidadMedida\"";
		PreparedStatement st = db.prepareStatement(sql);
		ResultSet rs = st.executeQuery();

//		if (rs.getFetchSize() > 0) {
		while (rs.next()) {
			uMedidaRow = new UnidadMedida();
			uMedidaRow.setUnidadMedidaID(rs.getInt("UnidadMedidaID"));
			uMedidaRow.setDescripcion(rs.getString("Descripcion"));
			listaUnidadMedida.add(uMedidaRow);
		}
//		} else {
		// Si deseamos, podemos cargar datos ficticios...
		// Ej.: INSTANCIAR ROLES Y AÑADIR A LA LISTA, para no devolver una lista vacía.

		/* OTRA OPCIÓN ES: Controlar en el método main (ConexionDB) */
//		}

		st.close();
		db.close();

		return listaUnidadMedida;
	}

	@Override
	public UnidadMedida ReadRow(int id) throws SQLException {
		// TODO Auto-generated method stub
		db = cDB.conectarBD();
		UnidadMedida uMedidaRow = null;
		String sql = "SELECT * FROM public.\"UnidadMedida\" WHERE \"UnidadMedidaID\"=" + id;
		PreparedStatement st = db.prepareStatement(sql);
		ResultSet rs = st.executeQuery();

//		if (rs.getFetchSize() > 0) {
		while (rs.next()) {
			uMedidaRow = new UnidadMedida();
			uMedidaRow.setUnidadMedidaID(rs.getInt("UnidadMedidaID"));
			uMedidaRow.setDescripcion(rs.getString("Descripcion"));
		}
//		} else {
		// Si deseamos, podemos cargar datos ficticios...
		// Ej.: INSTANCIAR ROLES Y AÑADIR A LA LISTA, para no devolver una lista vacía.

		/* OTRA OPCIÓN ES: Controlar en el método main (ConexionDB) */
//		}
		
		st.close();
		db.close();

		return uMedidaRow;
	}

	@Override
	public boolean UpdateRow(UnidadMedida unidadMedida) throws SQLException {
		// TODO Auto-generated method stub
		boolean resultado = false;
		db = cDB.conectarBD();
		String sql = "UPDATE public.\"UnidadMedida\" SET \"Descripcion\"=? WHERE \"UnidadMedidaID\"=?";
		PreparedStatement st = db.prepareStatement(sql);
		st.setString(1, unidadMedida.getDescripcion());
		st.setInt(2, unidadMedida.getUnidadMedidaID());
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
	public boolean DeleteRow(UnidadMedida unidadMedida) throws SQLException {
		// TODO Auto-generated method stub

		boolean resultado = false;
		db = cDB.conectarBD();
		String sql = "DELETE FROM public.\"UnidadMedida\" WHERE \"UnidadMedidaID\"=?";
		PreparedStatement st = db.prepareStatement(sql);
		st.setInt(1, unidadMedida.getUnidadMedidaID());
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
