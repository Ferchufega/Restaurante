package modelDB;

import java.sql.SQLException;
import java.util.List;

public interface UMedidaDAO {
	
	boolean CreateRow(UnidadMedida unidadMedida) throws SQLException;
	List<UnidadMedida> ReadRow() throws SQLException;
	UnidadMedida ReadRow(int id) throws SQLException;
	boolean UpdateRow(UnidadMedida unidadMedida) throws SQLException;
	boolean DeleteRow(UnidadMedida unidadMedida) throws SQLException;
	
}
