package paquetePrueba;

import java.sql.SQLException;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

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
		
		try {
			OperacionRolDB servicio;
					
			Gson gson = new Gson();

			/**
			 * El tipo de contenido de respuesta para todos las solicitudes sera JSON
			 */
			after((request, response) -> {
				response.type("application/json");
			});

			get("/", (request, response) -> "Welcome\n"
					+ response.raw());
			
			servicio = new OperacionRolDB();

			/**
			 * retorna lista completa de Roles
			 */
			get("/roles", (request, response) -> {

				List<Rol> resultado;

				try {
					resultado = servicio.ReadRow();
					for (Rol rol : resultado) {
						System.out.println("\n" + rol.toString());
					}
					response.status(200);
					
					response.type("application/json");
					return gson.toJson(resultado);
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

					// Busca registro según el ID dado
					Rol rol = servicio.ReadRow(Integer.parseInt(request.params(":id")));

					if (rol != null) { // existe registro?

						// obtiene los datos a actualizar
						rol.setDescripcion(request.queryParams("descripcion"));

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

					} else {
						response.status(404); // Not found
						return new Respuesta("No se pudo actualizar el Rol: " + rol.toString());
					}

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

			/**
			 * Mensaje de respuesta para solicitudes que no existan en la API
			 */
			notFound((request, response) -> {
				response.status(404); // Not found
				return gson.toJson("Servicio no encontrado..."); //(rptaDB);
			});

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			Respuesta rptaDB = new Respuesta(e.getMessage());

			System.out.println(rptaDB);
			
		}

	}

}
