package paquetePrueba;

import java.sql.SQLException;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import modelDB.Marca;
import modelDB.OperacionMarcaDB;
import modelDB.OperacionRolDB;
import modelDB.Rol;

// Spark
import static spark.Spark.after;
import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.notFound;
import static spark.Spark.post;
import static spark.Spark.put;

public class Main {

	public static void main(String[] args) {
		
		Gson gson = new Gson();
		
		/**
		 * El tipo de contenido de respuesta para todos las solicitudes sera JSON
		 */
		after((request, response) -> {
			response.type("application/json");
		});
		
		get("/", (request, response) -> "Welcome\n"
				+ response.raw()+"\nHey there!");
		
		/**
		 * Mensaje de respuesta para solicitudes que no existan en la API
		 */
		notFound((request, response) -> {
			response.status(404); // Not found
			return gson.toJson("Servicio no encontrado..."); //(rptaDB);
		});
		
		
		//Servicio para Rol
		try {
			OperacionRolDB servicio;
			
			servicio = new OperacionRolDB();

			/**
			 * retorna lista completa de Roles
			 */
			get("/roles", (request, response) -> {

				List<Rol> resultado;

				try {
					resultado = servicio.ReadRow();
					
					if(resultado!=null) {
						response.status(200);
						
						response.type("application/json");
						return gson.toJson(resultado);
					}else {
						response.status(404);
						response.type("application/json");
						Respuesta mensaje= new Respuesta("No matching records found");
						return gson.toJson(mensaje);
					}
					/*for (Rol rol : resultado) {
						System.out.println("\n" + rol.toString());
					}*/
					
				} catch (SQLException e) {
					// TODO: handle exception
					response.status(500); // bad request
					return new Respuesta(e.getMessage());
				}
			}, gson::toJson);

			
			get("/roles/:id", (request, response) -> {

				//List<Rol> resultado;
				Rol rol = null;
				int id=Integer.parseInt(request.params(":id"));
				try {
					rol = servicio.ReadRow(id);
					/*for (Rol rol : resultado) {
						System.out.println("\n" + rol.toString());
					}*/
					if(rol!=null) {
						response.status(200);	
						response.type("application/json");
						return gson.toJson(rol);
					}else {
						response.status(404);
						response.type("application/json");
						Respuesta mensaje= new Respuesta("No matching record found");
						return gson.toJson(mensaje);
					}
					
					
				} catch (SQLException e) {
					// TODO: handle exception
					response.status(500); // bad request
					return new Respuesta(e.getMessage());
				}
			}, gson::toJson);

			/**
			 * Agrega un nuevo registro Los datos de entrada corresponden a un objeto JSON
			 */
			post("/roles/agregar", (request, response) -> {

				if (request.body() != null) { // existe contenido

					try {

						// Obtiene objeto JSON de tipo Rol
						Rol rol = gson.fromJson(request.body(), Rol.class);

						boolean banInsercion = servicio.CreateRow(rol);

						if (banInsercion) {
							response.status(201); // Codigo de respuesta
							return new Respuesta("Nuevo Rol agregado: " + rol.toString());
						} else {
							response.status(500); // Error 500: pérdida de conexión con servidor
							return new Respuesta("No se pudo agregar el Rol: " + rol.toString());
						}

					} catch (JsonParseException e) {
						// TODO: handle exception
						response.status(400);// Bad Request
						return new Respuesta("El objeto JSON no es válido: <br/>" + e.getMessage());
					} catch (SQLException e) {
						// TODO: handle exception
						response.status(404); // Not found
						return new Respuesta(e.getMessage());
					}
				} else {
					response.status(400); // Bad Request
					return new Respuesta("El objeto JSON es obligatorio");
				}
			}, gson::toJson);

			/**
			 * Actualiza un recurso
			 */
			put("/roles/actualizar/:id", (request, response) -> {

				try {
					
					Rol rol = gson.fromJson(request.body(), Rol.class);
					
					// Busca registro según el ID dado
					Integer rolID = Integer.parseInt(request.params(":id"));
					rol.setRolID(rolID);

					//if (rol != null) { // existe registro?

						/* obtiene los datos a actualizar
						rol.setDescripcion(request.queryParams("descripcion"));*/

						// actualiza
						boolean banActualizacion = servicio.UpdateRow(rol);

						if (banActualizacion) {
							// responde
							response.status(200);// Codigo de respuesta
							return new Respuesta("Registro actualizado");

						} else {
							response.status(500); // Error 500: pérdida de conexión con servidor
							return new Respuesta("No se pudo actualizar el Rol: " + rol.toString());
						}

					/*} else {
						response.status(404); // Not found
						return new Respuesta("No se pudo actualizar el Rol: " + rol.toString());
					}*/

				} catch (SQLException e) {
					response.status(400);// Bad Request
					return new Respuesta(e.getMessage());
				}

			}, gson::toJson);

			/**
			 * Elimina un registro dado su ID
			 */
			delete("/roles/eliminar/:id", (request, response) -> {

				try {

					// Busca registro según el ID dado
					Rol rol = servicio.ReadRow(Integer.parseInt(request.params(":id")));

					if (rol != null) { // existe registro?

						// Elimina
						boolean banEliminacion = servicio.DeleteRow(rol);

						if (banEliminacion) {
							// responde
							response.status(200);// Codigo de respuesta
							return new Respuesta("Registro eliminado");

						} else {
							response.status(500); // Error 500: pérdida de conexión con servidor
							return new Respuesta("No se pudo eliminar el Rol: " + rol.toString());
						}

					} else {
						response.status(404); // Not found
						return new Respuesta("No se pudo actualizar el Rol: " + rol.toString());
					}

				} catch (SQLException e) {
					// TODO: handle exception
					response.status(400);// Bad Request
					return new Respuesta(e.getMessage());
				}

			}, gson::toJson);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			Respuesta rptaDB = new Respuesta(e.getMessage());

			System.out.println(rptaDB);
			
		}
		
		//Servicio para MARCA
		try {
			OperacionMarcaDB servicioM;
			
			servicioM = new OperacionMarcaDB();

			/**
			 * retorna lista completa de Roles
			 */
			get("/marcas", (request, response) -> {

				List<Marca> resultado;

				try {
					resultado = servicioM.ReadRow();
					
					if(resultado!=null) {
						response.status(200);
						
						response.type("application/json");
						return gson.toJson(resultado);
					}else {
						response.status(404);
						response.type("application/json");
						Respuesta mensaje= new Respuesta("No matching records found");
						return gson.toJson(mensaje);
					}
					/*for (Rol rol : resultado) {
						System.out.println("\n" + rol.toString());
					}*/
					
				} catch (SQLException e) {
					// TODO: handle exception
					response.status(500); // bad request
					return new Respuesta(e.getMessage());
				}
			}, gson::toJson);

			
			get("/marca/:id", (request, response) -> {

				//List<Rol> resultado;
				Marca mar = null;
				int id=Integer.parseInt(request.params(":id"));
				try {
					mar = servicioM.ReadRow(id);
					/*for (Rol rol : resultado) {
						System.out.println("\n" + rol.toString());
					}*/
					if(mar!=null) {
						response.status(200);	
						response.type("application/json");
						return gson.toJson(mar);
					}else {
						response.status(404);
						response.type("application/json");
						Respuesta mensaje= new Respuesta("No matching record found");
						return gson.toJson(mensaje);
					}
					
					
				} catch (SQLException e) {
					// TODO: handle exception
					response.status(500); // bad request
					return new Respuesta(e.getMessage());
				}
			}, gson::toJson);

			/**
			 * Agrega un nuevo registro Los datos de entrada corresponden a un objeto JSON
			 */
			post("/marca/agregar", (request, response) -> {

				if (request.body() != null) { // existe contenido

					try {

						// Obtiene objeto JSON de tipo Rol
						Marca marc = gson.fromJson(request.body(), Marca.class);

						boolean banInsercion = servicioM.CreateRow(marc);

						if (banInsercion) {
							response.status(201); // Codigo de respuesta
							return new Respuesta("Nueva marca agregada: " + marc.toString());
						} else {
							response.status(500); // Error 500: pérdida de conexión con servidor
							return new Respuesta("No se pudo agregar la marca: " + marc.toString());
						}

					} catch (JsonParseException e) {
						// TODO: handle exception
						response.status(400);// Bad Request
						return new Respuesta("El objeto JSON no es válido: \n" + e.getMessage());
					} catch (SQLException e) {
						// TODO: handle exception
						response.status(404); // Not found
						return new Respuesta(e.getMessage());
					}
				} else {
					response.status(400); // Bad Request
					return new Respuesta("El objeto JSON es obligatorio");
				}
			}, gson::toJson);

			/**
			 * Actualiza un recurso
			 */
			put("/marca/actualizar/:id", (request, response) -> {

				try {
					
					Marca marc = gson.fromJson(request.body(), Marca.class);
					int marcaID= Integer.parseInt(request.params(":id"));
					marc.setMarcaID(marcaID);
					// Busca registro según el ID dado
					//Marca marc = servicioM.ReadRow(Integer.parseInt(request.params(":id")));
					

					//if (marc != null) { // existe registro?

						// obtiene los datos a actualizar
						//marc.setNombre(request.queryParams("Nombre"));
						
						// actualiza
						boolean banActualizacion = servicioM.UpdateRow(marc);

						if (banActualizacion) {
							// responde
							response.status(200);// Codigo de respuesta
							return new Respuesta("Registro actualizado");

						} else {
							response.status(500); // Error 500: pérdida de conexión con servidor
							return new Respuesta("No se pudo actualizar la Marca: " + marc.toString());
						}

					/*} else {
						response.status(404); // Not found
						return new Respuesta("No se pudo actualizar la Marca: " + marc.toString());
					}*/

				} catch (SQLException e) {
					response.status(400);// Bad Request
					return new Respuesta(e.getMessage());
				}

			}, gson::toJson);

			/**
			 * Elimina un registro dado su ID
			 */
			delete("/marca/eliminar/:id", (request, response) -> {

				try {

					// Busca registro según el ID dado
					Marca mar = servicioM.ReadRow(Integer.parseInt(request.params(":id")));

					if (mar != null) { // existe registro?

						// Elimina
						boolean banEliminacion = servicioM.DeleteRow(mar);

						if (banEliminacion) {
							// responde
							response.status(200);// Codigo de respuesta
							return new Respuesta("Registro eliminado");

						} else {
							response.status(500); // Error 500: pérdida de conexión con servidor
							return new Respuesta("No se pudo eliminar la Marca: " + mar.toString());
						}

					} else {
						response.status(404); // Not found
						return new Respuesta("No se pudo actualizar la Marca: " + mar.toString());
					}

				} catch (SQLException e) {
					// TODO: handle exception
					response.status(400);// Bad Request
					return new Respuesta(e.getMessage());
				}

			}, gson::toJson);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			Respuesta rptaDB = new Respuesta(e.getMessage());

			System.out.println(rptaDB);
			
		}
		

	}

}
