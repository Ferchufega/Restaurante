package modelDB;

import java.sql.SQLException;
import java.util.List;

public interface TipoOperacionDAO {
	
	boolean CreateRow(TipoOperacion tipoOperacion) throws SQLException;
	List<TipoOperacion> ReadRow() throws SQLException;
	TipoOperacion ReadRow(int id) throws SQLException;
	boolean UpdateRow(TipoOperacion tipoOperacion) throws SQLException;
	boolean DeleteRow(TipoOperacion tipoOperacion) throws SQLException;
	
}
