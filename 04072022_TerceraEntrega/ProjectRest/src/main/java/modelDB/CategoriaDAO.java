package modelDB;

import java.sql.SQLException;
import java.util.List;

public interface CategoriaDAO {

	boolean CreateRow(Categoria categoria) throws SQLException;
	List<Categoria> ReadRow() throws SQLException;
	Categoria ReadRow(int id) throws SQLException;
	boolean UpdateRow(Categoria categoria) throws SQLException;
	boolean DeleteRow(Categoria categoria) throws SQLException;
	
}
