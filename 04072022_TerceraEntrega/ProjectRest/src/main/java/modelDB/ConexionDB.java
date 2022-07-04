package modelDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConexionDB {

	private String driver = "org.postgresql.Driver";
	//private String host = "jdbc:postgresql://localhost:5432/Restaurant";
	private String host = "jdbc:postgresql://ec2-3-217-113-25.compute-1.amazonaws.com:5432/d7o9jai4clos0v";
	//private String user = "postgres";
	private String user = "ustpfzkcetcyhh";
	//private String pass = "123ABC459";
	private String pass = "d3c69c2e0899a0295acf1317f1c3fcfbd108904188bf47195a65890e3c1420af";
	private Connection bd = null;

	public ConexionDB() throws SQLException {
		conectarBD();
	}

	public Connection conectarBD() throws SQLException {
		try {
			Class.forName(driver);
			bd = DriverManager.getConnection(host, user, pass);
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(ConexionDB.class.getName()).log(Level.SEVERE, null, ex);
		}
		return bd;
	}
	
	public void cerrarBD() throws SQLException {
        if (bd != null) {
            bd.close();
        }
    }
}
