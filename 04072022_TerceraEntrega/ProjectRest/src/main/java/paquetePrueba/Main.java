package paquetePrueba;

import java.sql.SQLException;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import modelDB.Categoria;
import modelDB.Marca;
import modelDB.OperacionCategoriaDB;
import modelDB.OperacionMarcaDB;
import modelDB.OperacionRolDB;
import modelDB.OperacionTOperDB;
import modelDB.OperacionUMedidaDB;
import modelDB.Rol;
import modelDB.TipoOperacion;
import modelDB.UnidadMedida;

// Spark
import static spark.Spark.*;
import static spark.Spark.after;
import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.notFound;
import static spark.Spark.post;
import static spark.Spark.put;

public class Main {

	public static void main(String[] args) {
		
		port(getHerokuAssignedPort());
		Gson gson = new Gson();
		/**
		 * El tipo de contenido de respuesta para todos las solicitudes sera JSON
		 */
		after((request, response) -> {
			response.type("application/json");
		});

		//get("/", (request, response) -> "Welcome\n" + response.raw() + "\nHey there!");
		
	    get("/hello", (request, response) -> "Welcome. Hey there!");


		/**
		 * Mensaje de respuesta para solicitudes que no existan en la API
		 */
		notFound((request, response) -> {
			response.status(404); // Not found
			return gson.toJson("Servicio no encontrado..."); // (rptaDB);
		});

		// Servicio para Rol
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

					if (resultado != null) {
						response.status(200);

						response.type("application/json");
						return gson.toJson(resultado);
					} else {
						response.status(404);
						response.type("application/json");
						Respuesta mensaje = new Respuesta("No matching records found");
						return gson.toJson(mensaje);
					}
					/*
					 * for (Rol rol : resultado) { System.out.println("\n" + rol.toString()); }
					 */

				} catch (SQLException e) {
					// TODO: handle exception
					response.status(500); // bad request
					return new Respuesta(e.getMessage());
				}
			}, gson::toJson);

			get("/roles/:id", (request, response) -> {

				// List<Rol> resultado;
				Rol rol = null;
				int id = Integer.parseInt(request.params(":id"));
				try {
					rol = servicio.ReadRow(id);
					/*
					 * for (Rol rol : resultado) { System.out.println("\n" + rol.toString()); }
					 */
					if (rol != null) {
						response.status(200);
						response.type("application/json");
						return gson.toJson(rol);
					} else {
						response.status(404);
						response.type("application/json");
						Respuesta mensaje = new Respuesta("No matching record found");
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

					// if (rol != null) { // existe registro?

					/*
					 * obtiene los datos a actualizar
					 * rol.setDescripcion(request.queryParams("descripcion"));
					 */

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

					/*
					 * } else { response.status(404); // Not found return new
					 * Respuesta("No se pudo actualizar el Rol: " + rol.toString()); }
					 */

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

		// Servicio para MARCA
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

					if (resultado != null) {
						response.status(200);

						response.type("application/json");
						return gson.toJson(resultado);
					} else {
						response.status(404);
						response.type("application/json");
						Respuesta mensaje = new Respuesta("No matching records found");
						return gson.toJson(mensaje);
					}
					/*
					 * for (Rol rol : resultado) { System.out.println("\n" + rol.toString()); }
					 */

				} catch (SQLException e) {
					// TODO: handle exception
					response.status(500); // bad request
					return new Respuesta(e.getMessage());
				}
			}, gson::toJson);

			get("/marca/:id", (request, response) -> {

				// List<Rol> resultado;
				Marca mar = null;
				int id = Integer.parseInt(request.params(":id"));
				try {
					mar = servicioM.ReadRow(id);
					/*
					 * for (Rol rol : resultado) { System.out.println("\n" + rol.toString()); }
					 */
					if (mar != null) {
						response.status(200);
						response.type("application/json");
						return gson.toJson(mar);
					} else {
						response.status(404);
						response.type("application/json");
						Respuesta mensaje = new Respuesta("No matching record found");
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
					int marcaID = Integer.parseInt(request.params(":id"));
					marc.setMarcaID(marcaID);
					// Busca registro según el ID dado
					// Marca marc = servicioM.ReadRow(Integer.parseInt(request.params(":id")));

					// if (marc != null) { // existe registro?

					// obtiene los datos a actualizar
					// marc.setNombre(request.queryParams("Nombre"));

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

					/*
					 * } else { response.status(404); // Not found return new
					 * Respuesta("No se pudo actualizar la Marca: " + marc.toString()); }
					 */

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

		// Servicio para CATEGORIA
		try {
			OperacionCategoriaDB servicioCategoria;

			servicioCategoria = new OperacionCategoriaDB();

			/**
			 * retorna lista completa de Categoría
			 */
			get("/categorias", (request, response) -> {

				List<Categoria> resultado;

				try {
					resultado = servicioCategoria.ReadRow();

					if (resultado != null) {
						response.status(200);

						response.type("application/json");
						return gson.toJson(resultado);
					} else {
						response.status(404);
						response.type("application/json");
						Respuesta mensaje = new Respuesta("No matching records found");
						return gson.toJson(mensaje);
					}
					/*
					 * for (Rol rol : resultado) { System.out.println("\n" + rol.toString()); }
					 */

				} catch (SQLException e) {
					// TODO: handle exception
					response.status(500); // bad request
					return new Respuesta(e.getMessage());
				}
			}, gson::toJson);

			get("/categoria/:id", (request, response) -> {

				// List<Rol> resultado;
				Categoria cat = null;
				int id = Integer.parseInt(request.params(":id"));
				try {
					cat = servicioCategoria.ReadRow(id);
					/*
					 * for (Rol rol : resultado) { System.out.println("\n" + rol.toString()); }
					 */
					if (cat != null) {
						response.status(200);
						response.type("application/json");
						return gson.toJson(cat);
					} else {
						response.status(404);
						response.type("application/json");
						Respuesta mensaje = new Respuesta("No matching record found");
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
			post("/categoria/agregar", (request, response) -> {

				if (request.body() != null) { // existe contenido

					try {

						// Obtiene objeto JSON de tipo Rol
						Categoria categ = gson.fromJson(request.body(), Categoria.class);

						boolean banInsercion = servicioCategoria.CreateRow(categ);

						if (banInsercion) {
							response.status(201); // Codigo de respuesta
							return new Respuesta("Nueva categoría agregada: " + categ.toString());
						} else {
							response.status(500); // Error 500: pérdida de conexión con servidor
							return new Respuesta("No se pudo agregar la categoría: " + categ.toString());
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
			put("/categoria/actualizar/:id", (request, response) -> {

				try {

					Categoria categ = gson.fromJson(request.body(), Categoria.class);
					int categoriaID = Integer.parseInt(request.params(":id"));
					categ.setCategoriaID(categoriaID);
					// Busca registro según el ID dado
					// Marca marc = servicioM.ReadRow(Integer.parseInt(request.params(":id")));

					// if (marc != null) { // existe registro?

					// obtiene los datos a actualizar
					// marc.setNombre(request.queryParams("Nombre"));

					// actualiza
					boolean banActualizacion = servicioCategoria.UpdateRow(categ);

					if (banActualizacion) {
						// responde
						response.status(200);// Codigo de respuesta
						return new Respuesta("Registro actualizado");

					} else {
						response.status(500); // Error 500: pérdida de conexión con servidor
						return new Respuesta("No se pudo actualizar la Categoría: " + categ.toString());
					}

					/*
					 * } else { response.status(404); // Not found return new
					 * Respuesta("No se pudo actualizar la Marca: " + marc.toString()); }
					 */

				} catch (SQLException e) {
					response.status(400);// Bad Request
					return new Respuesta(e.getMessage());
				}

			}, gson::toJson);

			/**
			 * Elimina un registro dado su ID
			 */
			delete("/categoria/eliminar/:id", (request, response) -> {

				try {

					// Busca registro según el ID dado
					Categoria categ = servicioCategoria.ReadRow(Integer.parseInt(request.params(":id")));

					if (categ != null) { // existe registro?

						// Elimina
						boolean banEliminacion = servicioCategoria.DeleteRow(categ);

						if (banEliminacion) {
							// responde
							response.status(200);// Codigo de respuesta
							return new Respuesta("Registro eliminado");

						} else {
							response.status(500); // Error 500: pérdida de conexión con servidor
							return new Respuesta("No se pudo eliminar la Categoría: " + categ.toString());
						}

					} else {
						response.status(404); // Not found
						return new Respuesta("No se pudo actualizar la Categoría: " + categ.toString());
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

		// Servicio para UNIDAD DE MEDIDA
		try {
			OperacionUMedidaDB servicioUMed;

			servicioUMed = new OperacionUMedidaDB();

			/**
			 * retorna lista completa de Unidad de Medida
			 */
			get("/unidadesmedida", (request, response) -> {

				List<UnidadMedida> resultado;

				try {
					resultado = servicioUMed.ReadRow();

					if (resultado != null) {
						response.status(200);

						response.type("application/json");
						return gson.toJson(resultado);
					} else {
						response.status(404);
						response.type("application/json");
						Respuesta mensaje = new Respuesta("No matching records found");
						return gson.toJson(mensaje);
					}
					/*
					 * for (Rol rol : resultado) { System.out.println("\n" + rol.toString()); }
					 */

				} catch (SQLException e) {
					// TODO: handle exception
					response.status(500); // bad request
					return new Respuesta(e.getMessage());
				}
			}, gson::toJson);

			get("/unidadmedida/:id", (request, response) -> {

				// List<Rol> resultado;
				UnidadMedida uMed = null;
				int id = Integer.parseInt(request.params(":id"));
				try {
					uMed = servicioUMed.ReadRow(id);
					/*
					 * for (Rol rol : resultado) { System.out.println("\n" + rol.toString()); }
					 */
					if (uMed != null) {
						response.status(200);
						response.type("application/json");
						return gson.toJson(uMed);
					} else {
						response.status(404);
						response.type("application/json");
						Respuesta mensaje = new Respuesta("No matching record found");
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
			post("/unidadmedida/agregar", (request, response) -> {

				if (request.body() != null) { // existe contenido

					try {

						// Obtiene objeto JSON de tipo Rol
						UnidadMedida uMed = gson.fromJson(request.body(), UnidadMedida.class);

						boolean banInsercion = servicioUMed.CreateRow(uMed);

						if (banInsercion) {
							response.status(201); // Codigo de respuesta
							return new Respuesta("Nueva unidad de medida agregada: " + uMed.toString());
						} else {
							response.status(500); // Error 500: pérdida de conexión con servidor
							return new Respuesta("No se pudo agregar la unidad de medida: " + uMed.toString());
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
			put("/unidadmedida/actualizar/:id", (request, response) -> {

				try {

					UnidadMedida uMed = gson.fromJson(request.body(), UnidadMedida.class);
					int unidadMedidaID = Integer.parseInt(request.params(":id"));
					uMed.setUnidadMedidaID(unidadMedidaID);
					// Busca registro según el ID dado
					// Marca marc = servicioM.ReadRow(Integer.parseInt(request.params(":id")));

					// if (marc != null) { // existe registro?

					// obtiene los datos a actualizar
					// marc.setNombre(request.queryParams("Nombre"));

					// actualiza
					boolean banActualizacion = servicioUMed.UpdateRow(uMed);

					if (banActualizacion) {
						// responde
						response.status(200);// Codigo de respuesta
						return new Respuesta("Registro actualizado");

					} else {
						response.status(500); // Error 500: pérdida de conexión con servidor
						return new Respuesta("No se pudo actualizar la Unidad de Medida: " + uMed.toString());
					}

					/*
					 * } else { response.status(404); // Not found return new
					 * Respuesta("No se pudo actualizar la Marca: " + marc.toString()); }
					 */

				} catch (SQLException e) {
					response.status(400);// Bad Request
					return new Respuesta(e.getMessage());
				}

			}, gson::toJson);

			/**
			 * Elimina un registro dado su ID
			 */
			delete("/unidadmedida/eliminar/:id", (request, response) -> {

				try {

					// Busca registro según el ID dado
					UnidadMedida uMed = servicioUMed.ReadRow(Integer.parseInt(request.params(":id")));

					if (uMed != null) { // existe registro?

						// Elimina
						boolean banEliminacion = servicioUMed.DeleteRow(uMed);

						if (banEliminacion) {
							// responde
							response.status(200);// Codigo de respuesta
							return new Respuesta("Registro eliminado");

						} else {
							response.status(500); // Error 500: pérdida de conexión con servidor
							return new Respuesta("No se pudo eliminar la Unidad de Medida: " + uMed.toString());
						}

					} else {
						response.status(404); // Not found
						return new Respuesta("No se pudo actualizar la Unidad de Medida: " + uMed.toString());
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

		// Servicio para TIPO DE OPERACION
		try {
			OperacionTOperDB servicioTOper;

			servicioTOper = new OperacionTOperDB();

			/**
			 * retorna lista completa de Tipo de Operacion
			 */
			get("/tiposoperaciones", (request, response) -> {

				List<TipoOperacion> resultado;

				try {
					resultado = servicioTOper.ReadRow();

					if (resultado != null) {
						response.status(200);

						response.type("application/json");
						return gson.toJson(resultado);
					} else {
						response.status(404);
						response.type("application/json");
						Respuesta mensaje = new Respuesta("No matching records found");
						return gson.toJson(mensaje);
					}
					/*
					 * for (Rol rol : resultado) { System.out.println("\n" + rol.toString()); }
					 */

				} catch (SQLException e) {
					// TODO: handle exception
					response.status(500); // bad request
					return new Respuesta(e.getMessage());
				}
			}, gson::toJson);

			get("/tipooperacion/:id", (request, response) -> {

				// List<Rol> resultado;
				TipoOperacion tOper = null;
				int id = Integer.parseInt(request.params(":id"));
				try {
					tOper = servicioTOper.ReadRow(id);
					/*
					 * for (Rol rol : resultado) { System.out.println("\n" + rol.toString()); }
					 */
					if (tOper != null) {
						response.status(200);
						response.type("application/json");
						return gson.toJson(tOper);
					} else {
						response.status(404);
						response.type("application/json");
						Respuesta mensaje = new Respuesta("No matching record found");
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
			post("/tipooperacion/agregar", (request, response) -> {

				if (request.body() != null) { // existe contenido

					try {

						// Obtiene objeto JSON de tipo Rol
						TipoOperacion tOper = gson.fromJson(request.body(), TipoOperacion.class);

						boolean banInsercion = servicioTOper.CreateRow(tOper);

						if (banInsercion) {
							response.status(201); // Codigo de respuesta
							return new Respuesta("Nuevo Tipo de operación agregado: " + tOper.toString());
						} else {
							response.status(500); // Error 500: pérdida de conexión con servidor
							return new Respuesta("No se pudo agregar el Tipo de operación: " + tOper.toString());
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
			put("/tipooperacion/actualizar/:id", (request, response) -> {

				try {

					TipoOperacion tOper = gson.fromJson(request.body(), TipoOperacion.class);
					int tipoOperacionID = Integer.parseInt(request.params(":id"));
					tOper.setTipoOperacionID(tipoOperacionID);
					// Busca registro según el ID dado
					// Marca marc = servicioM.ReadRow(Integer.parseInt(request.params(":id")));

					// if (marc != null) { // existe registro?

					// obtiene los datos a actualizar
					// marc.setNombre(request.queryParams("Nombre"));

					// actualiza
					boolean banActualizacion = servicioTOper.UpdateRow(tOper);

					if (banActualizacion) {
						// responde
						response.status(200);// Codigo de respuesta
						return new Respuesta("Registro actualizado");

					} else {
						response.status(500); // Error 500: pérdida de conexión con servidor
						return new Respuesta("No se pudo actualizar el tipo de operación: " + tOper.toString());
					}

					/*
					 * } else { response.status(404); // Not found return new
					 * Respuesta("No se pudo actualizar la Marca: " + marc.toString()); }
					 */

				} catch (SQLException e) {
					response.status(400);// Bad Request
					return new Respuesta(e.getMessage());
				}

			}, gson::toJson);

			/**
			 * Elimina un registro dado su ID
			 */
			delete("/tipooperacion/eliminar/:id", (request, response) -> {

				try {

					// Busca registro según el ID dado
					TipoOperacion tOper = servicioTOper.ReadRow(Integer.parseInt(request.params(":id")));

					if (tOper != null) { // existe registro?

						// Elimina
						boolean banEliminacion = servicioTOper.DeleteRow(tOper);

						if (banEliminacion) {
							// responde
							response.status(200);// Codigo de respuesta
							return new Respuesta("Registro eliminado");

						} else {
							response.status(500); // Error 500: pérdida de conexión con servidor
							return new Respuesta("No se pudo eliminar el tipo de operación: " + tOper.toString());
						}

					} else {
						response.status(404); // Not found
						return new Respuesta("No se pudo actualizar el tipo de operación: " + tOper.toString());
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

	static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }

}
