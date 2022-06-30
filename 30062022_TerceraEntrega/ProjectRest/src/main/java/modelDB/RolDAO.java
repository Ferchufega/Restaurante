package modelDB;

import java.sql.SQLException;
import java.util.List;

public interface RolDAO {
	
	boolean CreateRow(Rol rol) throws SQLException;
	List<Rol> ReadRow() throws SQLException;
	Rol ReadRow(int id) throws SQLException;
	boolean UpdateRow(Rol rol) throws SQLException;
	boolean DeleteRow(Rol rol) throws SQLException;
	
}
