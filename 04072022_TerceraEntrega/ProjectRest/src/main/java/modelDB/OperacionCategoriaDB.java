package modelDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OperacionCategoriaDB implements CategoriaDAO {

	public ConexionDB cDB = null;
	public Connection db = null;

	// Constructor
	public OperacionCategoriaDB() throws SQLException {
		cDB = new ConexionDB();
	}

	// Implementaciones
	@Override
	public boolean CreateRow(Categoria categoria) throws SQLException {
		db = cDB.conectarBD();
		String sql = "INSERT INTO public.\"Categoria\"(\"Descripcion\", \"Activo\") VALUES (?,?)";
		PreparedStatement st = db.prepareStatement(sql);
		st.setString(1, categoria.getDescripcion());
		st.setString(2, categoria.getActivo());

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
	public List<Categoria> ReadRow() throws SQLException {
		db = cDB.conectarBD();
		Categoria catRow = null;
		List<Categoria> listaCate = new ArrayList<Categoria>();
		String sql = "SELECT * FROM public.\"Categoria\"";
		PreparedStatement st = db.prepareStatement(sql);
		ResultSet rs = st.executeQuery();

			while (rs.next()) {
				catRow = new Categoria();
				catRow.setCategoriaID(rs.getInt("CategoriaID"));
				catRow.setDescripcion(rs.getString("Descripcion"));
				catRow.setActivo(rs.getString("Activo"));
				listaCate.add(catRow);
			}
		st.close();
        db.close();

		return listaCate;
	}

	@Override
	public Categoria ReadRow(int id) throws SQLException {
		db = cDB.conectarBD();
		Categoria catRow = null;
		String sql = "SELECT * FROM public.\"Categoria\" WHERE \"CategoriaID\"=" + id;
		PreparedStatement st = db.prepareStatement(sql);
		ResultSet rs = st.executeQuery();

			while (rs.next()) {
				catRow = new Categoria();
				catRow.setCategoriaID(rs.getInt("CategoriaID"));
				catRow.setDescripcion(rs.getString("Descripcion"));
				catRow.setActivo(rs.getString("Activo"));
			}
		st.close();
        db.close();

		return catRow;
	}

	@Override
	public boolean UpdateRow(Categoria categoria) throws SQLException {
		boolean resultado = false;
		db = cDB.conectarBD();
		String sql = "UPDATE public.\"Categoria\" SET \"Descripcion\"=?, \"Activo\" =? WHERE \"CategoriaID\"=?";
		PreparedStatement st = db.prepareStatement(sql);
		st.setString(1, categoria.getDescripcion());
		st.setString(2, categoria.getActivo());
		st.setInt(3, categoria.getCategoriaID());
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
	public boolean DeleteRow(Categoria categoria) throws SQLException {
		boolean resultado = false;
		db = cDB.conectarBD();
		String sql = "DELETE FROM public.\"Categoria\" WHERE \"CategoriaID\"=?";
		PreparedStatement st = db.prepareStatement(sql);
		st.setInt(1, categoria.getCategoriaID());
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
