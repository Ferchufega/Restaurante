package modelDB;

import java.sql.SQLException;
import java.util.List;

public interface MarcaDAO {

	boolean CreateRow(Marca marca) throws SQLException;
	List<Marca> ReadRow() throws SQLException;
	Marca ReadRow(int id) throws SQLException;
	boolean UpdateRow(Marca marca) throws SQLException;
	boolean DeleteRow(Marca marca) throws SQLException;
	
}
