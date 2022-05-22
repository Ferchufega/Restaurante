package paquetePrueba;

import static spark.Response;
import static spark.Request;
import static spark.Spark;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import modelDB.*;

public class RouteRol {

	private Rol rol;
	private OperacionRolDB operacionRol;

	public RouteRol() {
		try {
			operacionRol = new OperacionRolDB();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Logger.getLogger(RouteRol.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	public void InsertarRol(Rol rol) {
		try {
			operacionRol.CreateRow(rol);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
