package paquetePrueba;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;
import static spark.Spark.*;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class Main {

	
	
	/* CÓDIGO PARA TRABAJAR CON PLANTILLAS FREEMARKER
	 * 
	 * • RECORDATORIO!
	 * 		Falta leer documentación para aprender a crear plantillas, y tratar de solucionar
	 * 		error de instanciar la plantilla.
	 * 
	 * public static final Version VERSION_2_3_31 = new Version("2.3.26");
	
	public static void main(String[] args) {

		try {

			final Configuration configuration = new Configuration(VERSION_2_3_31);
			configuration.setClassForTemplateLoading(Main.class, "/");
			Spark.get("/nombre", new Route() {

				StringWriter writer = new StringWriter();
				private Object plantilla;

				@Override
				public Object handle(Request rqst, Response rspns) throws Exception {

					try {
						Template plantilla = configuration.getTemplate("nombre.html");
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("nombre", "Luis Chris Fernando 123456");
						plantilla.process(map, writer);
					} catch (Exception e) {
						// TODO: handle exception

						// Para detener inmediatamente una solicitud dentro de un filtro o ruta
						// Nro. 500 hace referencia a código de error porque servidor no respondió a
						// solicitud para el servicio indicado
						halt(500);
						e.printStackTrace();

					}

					return writer;

				}

			});
			
		} catch (IllegalArgumentException e) {
			// TODO: handle exception
			System.err.println(e.getMessage());
		}

	}*/

}
