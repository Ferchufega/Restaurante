package paquetePrueba;

import spark.Request;
import spark.Response;
import spark.Route;

public class Rutas implements Route{
	/*
	public void rutas() {
	
		//val rolDao = RolDAO();
		
		path("/api", () -> {
			before("/*", (q, a) -> log.info("Received api call"));
			path("/roles", () -> {				
				get("/",       OperacionRolDB.ReadRow);
				post("/agregar",       OperacionRolDB.CreateRow);
				put("/actualizar",     OperacionRolDB.UpdateRow);
				delete("/eliminar",  OperacionRolDB.DeleteRow);
			});
			path("/username", () -> {
				post("/add",       UserApi.addUsername);
				put("/change",     UserApi.changeUsername);
				delete("/remove",  UserApi.deleteUsername);
			});
		});
		
	}*/

	@Override
	public Object handle(Request request, Response response) throws Exception {
		// TODO Auto-generated method stub
		String body = null;
		
		body = "<h1>Prueba página</h1><br>";
		
		response.body(body);
		return response;
	}
	
	
}
