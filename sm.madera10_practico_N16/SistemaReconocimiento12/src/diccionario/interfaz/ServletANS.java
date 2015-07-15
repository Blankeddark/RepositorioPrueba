package diccionario.interfaz;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import diccionario.mundo.AdministradorANS;
import diccionario.mundo.Carpeta;
import diccionario.mundo.Diccionario;
import diccionario.mundo.Documento;
import estructurasDatos.ASAVLTree;
import estructurasDatos.ASList;

/**
 * Servlet principal que recibe las solicitudes del servidor. La URL a la que responde es
 * SistemaReconocimiento. Así, en el browser deberá escribirse: localhost:8080/(nombreProyecto)
 * Nombre: ServletANS
 * URL pattern: /ANS.html
 * @author AS Team
 */
public class ServletANS extends HttpServlet { 
	//------------------------------------------------------------------------------------------------------------
	//Constantes
	//------------------------------------------------------------------------------------------------------------

	public final static String URL_SERIALIZACIÓN = "C:/Users/Sergio/Documents/APO/N16/sm.madera10_practico_N16/SistemaReconocimiento12/serialized/";
	public final static String URL_RECON = "C:/Users/Sergio/Documents/APO/N16/sm.madera10_practico_N16/SistemaReconocimiento12/data/";

	//------------------------------------------------------------------------------------------------------------
	//Atributos
	//------------------------------------------------------------------------------------------------------------

	/**
	 * Boolean que modela si el modo PRO está activado. True si lo está, false de lo contrario. DEPRECATED.
	 * -- Es de cuando creía que el modo pro se activaba/desactivaba.
	 * 
	 *  
	 *  ---> No hace nada, lol <--- 
	 */
	private static boolean modoPro = false;

	/**
	 * Conexión con el mundo. No se usa en los métodos estáticos.
	 */
	private AdministradorANS admin;

	/**
	 * Instancia del servletANS
	 */
	private static ServletANS instancia;

	//------------------------------------------------------------------------------------------------------------
	//Métodos
	//------------------------------------------------------------------------------------------------------------

	public void init()
	{
		System.out.println("Hace lo que es");
		instancia = this;
		modoPro = false; //No hace nada, lol
		admin = AdministradorANS.getInstance();
	}

	public void destroy()
	{

	}

	public static ServletANS getInstance()
	{
		if(instancia == null)
		{
			JOptionPane.showMessageDialog(null, "La instancia del ServletANS es null. Esto no debería pasar");
		}

		return instancia;
	}

	protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{
		String botonPractico = request.getParameter("botonPractico");

		if (botonPractico != null)
		{
			mostrarVentanaPractico(response);
		}
	}
	

	protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{
		try 
		{
			redireccionarBotónPresionado(request, response);
		} 

		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	public static void mostrarVentanaPractico(HttpServletResponse response) throws IOException
	{
		PrintWriter pw = response.getWriter();
		imprimirEncabezado(response, pw);
		pw.println("<center>Examen pr&aacute;ctico desu. Seleccione el documento cuya informaci&oacute;n quiere ver:<br>");
		pw.println("<form action=\"practico.html\" method=\"GET\" name=\"formDesu\">");
		pw.println("<select name=\"nombreDoc\">");
		
		Iterator<Documento> it = AdministradorANS.getInstance().darDocumentos().iterator();
		while(it.hasNext())
		{
			Documento actual = it.next();
			String nombreCort = actual.darNombre().substring(0, actual.darNombre().length() - 4);
			pw.println("<option value=\"" + actual.darNombre() + "\">" + nombreCort);
		}
		
		pw.println("</select> <br><br>");
		pw.println("<input type=\"submit\" name=\"botonVerInformacion\" value=\"Ver informaci&oacute;n\"><br><br>");
		pw.println("<input type=\"submit\" name=\"botonTop10\" value=\"Ver top 10\"> <br><br>");
		pw.println("<input type=\"submit\" name=\"botonVolver\" value=\"Volver\"><br><br>");
		pw.println("</form>");
		pw.println("</center> </body> </html>");
	}

	/**
	 * Verifica si algún botón fue presionado, y si lo fue, cuál. Según el botón presionado, 
	 * redirecciona al usuario a la página correspondiente.
	 * @param request solicitud por parte del usuario.
	 * @throws Exception 
	 */
	private void redireccionarBotónPresionado( HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		//El único de ellos que no sea null es el que fue presionado.
		String botonDiccionario = request.getParameter("botonDiccionario");
		String botonArchivos = request.getParameter("botonArchivos");
		String botonReconocimiento = request.getParameter("botonReconocimiento");
		String botonBúsqueda = request.getParameter("botonBusqueda");
		String botonVolverError = request.getParameter("botonVolverError");

		if(botonDiccionario != null)
		{
			mostrarVentanaDiccionario(response);
		}

		else if(botonArchivos != null)
		{
			mostrarVentanaArchivos(response);
		}

		else if(botonReconocimiento != null)
		{
			mostrarVentanaReconocimiento(response);
		}

		else if(botonBúsqueda != null)
		{
			mostrarVentanaBúsqueda(response);
		}

		else if (botonVolverError != null)
		{
			mostrarMain(response);
		}

	}

	/**
	 * @return true si el modo PRO está activado, false de lo contrario.
	 */
	public static boolean modoPro()
	{
		return modoPro;
	}

	/**
	 * Crea la respuesta de tal forma que envíe al browser a la página del diccionario. La versión
	 * base es la de ./html/diccionarios.html, pero se le concatena más código dependiendo de los
	 * diccionarios guardados actualmente. Esto sólo muestra la ventana "principal" (inicial) de
	 * diccionarios, pero en ella todas las solicitudes son manejadas por ServletDiccionario. Es
	 * decir, para ver el método que muestra la página para agregar un diccionario, se debe buscar
	 * en el ServletDiccionario (él maneja esa solicitud).
	 */
	public void mostrarVentanaDiccionario(HttpServletResponse response)
	{
		//recordar mostrar cte de opción inicial
		PrintWriter pw = null;
		try
		{
			pw = response.getWriter();

		} 
		catch (IOException e) 
		{
			JOptionPane.showMessageDialog(null, "Hubo un problema mostrando l ventana diccionario");
			e.printStackTrace();
		}

		imprimirEncabezado(response, pw);
		pw.println("<br> <br>");
		pw.println("<center>");
		pw.println(		"<font size=\"6\" color=\"red\"> Sistema de Reconocimiento </font> <br>");
		pw.println(		"<font size=\"5\" color=\"red\"> Diccionarios </font> <br> <br> <br> <br>");
		pw.println(	"<table>");
		pw.println(		"<tr>");
		pw.println("			<form action=\"diccionario.html\" method=\"GET\" value=\"getInfDic\">");
		pw.println(			"<td><select name=\"Diccionarios\">");
		pw.println(				"<option selected value=\"" + ServletDiccionario.OPCIÓN_INICIAL + "\">" + ServletDiccionario.OPCIÓN_INICIAL);

		//Agrego todos los diccionarios disponibles
		ASAVLTree<Diccionario> diccionarios = admin.darDiccionarios();
		Iterator<Diccionario> it = diccionarios.iterator();

		while(it.hasNext())
		{
			Diccionario actual = it.next();
			String nombreActual = actual.darNombre();
			pw.println("<option value=\"" + nombreActual + "\">" + nombreActual);
		}

		pw.println("</select> </td>");
		pw.println("			<br>");
		pw.println("		<td>");
		pw.println("				<input type=\"submit\" name=\"botonVerInfoDiccionario\"");
		pw.println("					value=\"Ver Informaci&oacute;n diccionario\">");
		pw.println("				</form>");
		pw.println("			</td>");
		pw.println("	</table>");
		pw.println("	<br> <br> <br>");

		pw.println("	<table>");
		pw.println("	<tr>");
		pw.println("	<td>");
		pw.println("				<form action=\"diccionario.html\" method=\"GET\" name=\"AgregarEditar\">");
		pw.println("					<input type=\"submit\" name=\"botonAgregar\" value=\"Agregar diccionario\">");
		pw.println("					<input type=\"submit\" name=\"botonEditar\" value=\"Editar diccionario\">");
		pw.println("				</form>");
		pw.println("		</tr>");
		pw.println("		<tr>");
		pw.println("			<td><br>");
		pw.println("				<form action=\"diccionario.html\" method=\"GET\" name=\"verDic\">");
		pw.println("					<center>");
		pw.println("						<input type=\"submit\" name=\"botonVolver\" value=\" Volver \">");
		pw.println("</center>");
		pw.println("</form></td>");
		pw.println("</table>");


		pw.println("</center>");
		pw.println("</body>");
		pw.println("</html>");
	}

	/**
	 * Retorna la información necesaria para mostrar la página principal.
	 */
	public static void mostrarMain(HttpServletResponse response)
	{
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
		} 
		catch (IOException e) 
		{
			JOptionPane.showMessageDialog(null, "Hubo un problema mostrando la respuesta del servlet");
			e.printStackTrace();
		}

		imprimirEncabezado(response, pw);

		pw.println("<br> <br>");
		pw.println("<center><font size=\"6\" color=\"red\">");
		pw.println("Sistema de Reconocimiento");
		pw.println("</font>");
		pw.println("<br>");
		pw.println("<font size =\"5\" color=\"red\">");
		pw.println("Administrador</font>");
		pw.println("<br> <br> <br> <br>");

		pw.println("<form action=\"ANS.html\" method=\"GET\" name=\"diccionario\" value=\"dic\">");
		pw.println("<input type=\"submit\" name=\"botonDiccionario\" value=\"Diccionarios\" style=\"font-size:1.5em\">");
		pw.println("</form>");
		pw.println("<br> <br> <br> <br>");

		pw.println("<form action=\"ANS.html\" method=\"GET\" name=\"archivos\" value=\"arch\">");
		pw.println("<input type=\"submit\" name=\"botonArchivos\" value=\"Archivos\" style=\"font-size:1.5em\">");
		pw.println("</form>");
		pw.println("<br> <br> <br> <br>");	

		pw.println("<form action=\"ANS.html\" method=\"GET\" name=\"reconocimiento\" value=\"recon\">");
		pw.println("<input type=\"submit\" name=\"botonReconocimiento\" value=\"Reconocimiento\" style=\"font-size:1.5em\">");
		pw.println("</form>");
		pw.println("<br> <br> <br> <br>");	

		pw.println("<form action=\"ANS.html\" method=\"GET\" name=\"busqueda\" value=\"busq\">");
		pw.println("<input type=\"submit\" name=\"botonBusqueda\" value=\"B&uacute;squeda\" style=\"font-size:1.5em\">");
		pw.println("</form>");
		pw.println("<br> <br> <br>");
		pw.println("<br>");

		pw.println("<form action=\"ANS.html\" method=\"POST\" name=\"búsqueda\" value=\"BasicoPRO\">");
		pw.println("<input align=\"left\" type=\"submit\"  name=\"botonPractico\" value=\"Practico\" style=\"font-size:1.2em; color:#B26B00\">");

		pw.println("</form>");

		pw.println("</center>");
		pw.println("</body>");
		pw.println("</html>");
	}

	public static void imprimirEncabezado(HttpServletResponse response, PrintWriter pw)
	{
		pw.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
		pw.println("<html>");

		pw.println("<head>");
		pw.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">");
		pw.println("<title>Sistema de Reconocimiento</title>");

		pw.println("</head>");

		pw.println("<body style=\"font-family: Verdana\">"); 
	}

	public static void mostrarTest(HttpServletResponse response) throws IOException
	{
		PrintWriter pw = response.getWriter();

		imprimirEncabezado(response, pw);
		pw.println("heyo");
		pw.println("</body>");
		pw.println("</html>");
	}

	public static void mostrarTest2(HttpServletResponse response) throws Exception
	{
		PrintWriter pw = response.getWriter();
		imprimirEncabezado(response, pw);
		pw.println("<form action=\"ANS.html\" method=\"POST\">");
		pw.println("<input type=\"file\" name=\"k\">");
		pw.println("<input type=\"submit\" name=\"s\" value=\"selec\">");
		pw.println("</form>");
		pw.println("</body> </html>");
	}

	/**
	 * Muestra la ventana error.
	 * @param errores lista de errores a mostrar en letras rojas en la ventana.
	 * @param response respuesta de la solicitud; retorna al usuario a la página principal.
	 * @throws IOException
	 */
	public static void mostrarVentanaError(ASList<String> errores, HttpServletResponse response) throws IOException
	{
		PrintWriter pw = response.getWriter();

		imprimirEncabezado(response, pw);
		pw.println("<br> <br>");
		pw.println("El contenido de los siguientes campos no es v&aacute;lido: <br> <br>");
		pw.println("<font style=color:red>");
		for (int i = 0; i < errores.size(); i++)
		{
			if (errores.get(i) != null)
			{
				pw.println(errores.get(i) + "<br>");
			}
		}
		pw.println("</font>");
		pw.println("<br>");
		pw.println("Recuerde que los campos no pueden estar vac&iacute;os. <br>"
				+ "Presiona \"Volver\" y ser&aacute;s regresado a la p&aacute;gina principal <br>");

		pw.println("<form method=\"GET\" action=\"ANS.html\" name=\"bVolver\">");

		pw.println("<input type=\"submit\" name=\"botonVolverError\" value=\"Volver\">");

		pw.println("</form>");

		pw.println("</body> </html>");

	}

	public static void mostrarMensaje(String mensaje)
	{
		ThreadMostrarMensaje tmm = new ThreadMostrarMensaje(mensaje);
		tmm.start();
	}

	public static void mostrarMensajeConScroll(String mensaje, String título)
	{
		ThreadMostrarMensajeConScroll tmmcs = new ThreadMostrarMensajeConScroll(mensaje, título);
		tmmcs.start();

	}

	public static void mostrarVentanaReconocimiento(HttpServletResponse response) throws IOException
	{
		PrintWriter pw = response.getWriter();
		imprimirEncabezado(response, pw);
		pw.println("<center> <br> <br> <br>");
		pw.println("<form action=\"reconocimiento.html\" method=\"POST\" name=\"fahm\">");
		pw.println("<input type=\"submit\" name=\"botonVolverReconocimiento\" value=\"Volver\">");
		pw.println("</form> <br>");
		pw.println("<form action=\"reconocimiento.html\" method=\"POST\" name=\"fohm\">");
		pw.println("<br>Elija una carpeta del sistema de carpetas virtual para guardar el documento: ");
		pw.println("<select name=\"carpetaGuardar\">");
		
		imprimirListaCarpetas(pw);
		
		pw.println("</select>");
		pw.println("<br>Nota: si no hay carpeta seleccionada, no se guardar&aacute; en ninguna.");
		pw.println("<input type=\"file\" name=\"urlArchivo\">");
		pw.println("<input type=\"submit\" name=\"botonCargarArchivo\" value=\"Cargar archivo\">");
		pw.println("</form> <br> <br>");
		pw.println("<form action=\"reconocimiento.html\" method=\"POST\" name=\"fehm\">");
		pw.println("<table border=\"0\"> <tr>");
		pw.println("<td>Seleccione el documento que desea reconocer: <select name=\"docSeleccionado\">");

		ASAVLTree<Documento> docs = AdministradorANS.getInstance().darDocumentos();

		Iterator<Documento> it = docs.iterator();

		while(it.hasNext())
		{
			Documento actual = it.next();
			pw.println("<option value=\"" + actual.darNombre() + "\"/>" + actual.darNombre());
		}

		pw.println("</select> <br>");
		pw.println("<td> <input type=\"submit\" name=\"botonReconocer\" value=\"Reconocer\">");
		pw.println("</table> <br> <br>");
		pw.println("<table border=\"0\"> <tr>");
		pw.println("<td>Seleccione el documento cuyo contenido reconocido quiere ver: <select name=\"docReconocido\">");

		docs = AdministradorANS.getInstance().darDocumentos();

		it = docs.iterator();

		while(it.hasNext())
		{
			Documento actual = it.next();
			pw.println("<option value=\"" + actual.darNombre() + "\"/>" + actual.darNombre());
		}
		pw.println("<td> <input type=\"submit\" name=\"botonVer\" value=\"Reconocer\">");
		pw.println("</table> </form> </body> </html>");
	}

	/**
	 * Vuelve al diccionario que entra por parámetro el diccionarioActual.
	 * @param dic diccionario que será el diccionarioActual.
	 */
	public void asignarDiccionario(Diccionario dic)
	{
		admin.asignarDiccionarioActual(dic);
	}

	/**
	 * Agrega un diccionario a la lista de diccionarios.
	 * @param d diccionario que se agregará
	 * @return true si el diccionario pudo ser añadido, false de lo contrario.
	 */
	public boolean agregarDiccionario(Diccionario d)
	{
		return admin.añadirDiccionario(d);
	}

	public static Diccionario darDiccionarioActual()
	{
		return AdministradorANS.getInstance().darDiccionarioActual();
	}

	/**
	 * Muestra la ventana de archivos.
	 * @param response
	 * @throws IOException
	 */
	public void mostrarVentanaArchivos(HttpServletResponse response) throws IOException
	{
		PrintWriter pw = response.getWriter();
		imprimirEncabezado(response, pw);

		pw.println("<form method=\"POST\" action=\"carpetas.html\" name=\"lel\">");
		pw.println("<br><br><input type=\"submit\" name=\"botonVolverCarpetas\" value=\"Volver\">");
		pw.println("<br><br><input type=\"submit\" name= \"botonCrear\" value=\"Crear carpeta\">");
		pw.println("<br><br><input type=\"submit\" name=\"botonBorrar\" value=\"Borrar carpeta\">");
		pw.println("<br><br><input type=\"submit\" name=\"botonExportar\" value=\"Exportar XML\">");
		pw.println("<input type=\"file\" name=\"urlImportar\">");
		pw.println("<input type=\"submit\" name= \"botonImportar\" value=\"Importar XML\">");
		pw.println("</form>");

		pw.println("Carpetas: <br><br>");

		imprimirCarpetas(pw);

		pw.println("</body> </html>");
	}

	/**
	 * Escribe las carpetas sobre el pw.
	 * @param pw
	 */
	public void imprimirCarpetas(PrintWriter pw)
	{
		pw.println("<font size=\"2.2em\">");
		Iterator<Carpeta> it = admin.darCarpetas().iterator();

		while(it.hasNext())
		{
			Carpeta actual = it.next();
			pw.println("-----" + actual.darNombre() + "<br>");

			if(actual.darDocumentos() != null)
			{

				Iterator<Documento> iter = actual.darDocumentos().iterator();
				while(iter.hasNext())
				{
					Documento actualDoc = iter.next();
					pw.println("--------" + actualDoc.darNombre() + "<br>");
				}
			}
			
			pw.println("<br><br>");
		}
		
		pw.println("</font>");
	}
	
	public static void imprimirListaCarpetas(PrintWriter pw)
	{
		Iterator<Carpeta> it = AdministradorANS.getInstance().darCarpetas().iterator();
		
		while(it.hasNext())
		{
			Carpeta actual = it.next();
			pw.println("<option value=\"" + actual.darNombre() + "\">" + actual.darNombre());
		}
	}

	public Diccionario buscarDiccionario(Diccionario dic) throws Exception
	{
		return admin.darDiccionarios().get(dic);
	}

	public static Documento darDocumentoActual()
	{
		return AdministradorANS.getInstance().darDocumentoActual();
	}

	public static void cambiarDocumentoActual(Documento docActual) throws Exception
	{
		AdministradorANS.getInstance().cambiarDocumentoActual(docActual);
	}

	//	/**
	//	 * pre: diccionarioActual != null
	//	 * @param response
	//	 * @throws Exception 
	//	 */
	//	public static void mostrarVentanaDocReconocido(Documento doc, HttpServletResponse response) throws Exception
	//	{
	//		System.out.println(doc); //TODO
	//		PrintWriter pw = response.getWriter();
	//
	//		imprimirEncabezado(response, pw);
	//		pw.println("<br> <br>");
	//		pw.println("Texto a reconocer: <br>");
	//		pw.println(doc.darTexto());
	//		pw.println("<br> <br> Texto reconocido: <br>");
	//		pw.println(doc.reconocer(AdministradorANS.getInstance().darDiccionarioActual()));
	//		pw.println("<br> <form action=\"reconocimiento.html\" method=\"POST\" name=\"b\">");
	//		pw.println("<input type=\"submit\" name=\"botonVolverRecon\" value\"Volver\">");
	//		pw.println("</form>");
	//		pw.println("</body> </html>");
	//	}
	//	
	public static void mostrarVentanaConTexto(HttpServletResponse response) throws Exception
	{
		Documento doc = AdministradorANS.getInstance().darDocumentoActual();

		PrintWriter pw = response.getWriter();

		imprimirEncabezado(response, pw);
		pw.println("<br><br> Texto reconocido: <br>");
		pw.println(doc.darTextoReconocido());
		pw.println("<br> <form action=\"reconocimiento.html\" method=\"POST\" name=\"b\">");
		pw.println("<input type=\"submit\" name=\"botonVolverTexto\" value\"Volver\">");
		pw.println("</form>");
		pw.println("</body> </html>");
	}

	public static void mostrarVentanaBúsqueda(HttpServletResponse response) throws Exception
	{
		PrintWriter pw = response.getWriter();
		imprimirEncabezado(response, pw);
		pw.println("<br> <br>");
		pw.println("Utilice las opciones a continuaci&oacute;n para realizar una b&uacute;squeda. Los nombres de los"
				+ " documentos que cumplan los requisitos especificados aparecer&aacute;n una vez la b&uacute;squeda finalice. <br>Recuerde que la b&acute;squeda"
				+ " se realiza sobre todos los documentos cargados anteriormente.");
		pw.println("<br> <br> <br>");

		//Inicio de form
		pw.println("<form action=\"busqueda.html\" method=\"POST\" name=\"giggity\">");

		pw.println("Ingrese las palabras o prefijos con los que quiere hacer la b&uacute;squeda separados por comas y un espacio. "
				+ "<br> Ej.: uno, dos, tres, cuatro"
				+ "<br> No agregue puntos finales o intermedios. <br>"
				+ "Tenga en cuenta que si ingresa prefijos pero decide buscar por palabras, los resultados no ser&aacute;n precisos. <br> <br>");
		pw.println("<textarea name=\"contenidoBuscar\" size=\"5\"></textarea><br> <br>");

		//Declaración de la lista de opción para buscar por palabra completa o por prefijos.
		pw.println("Selecciona la forma de b&uacute;squeda: <br> <br>");
		pw.println("<select name=\"tipoBusqueda\">");
		pw.println("<option selected value =\"" + ServletBúsqueda.OPCIÓN_PALABRA_COMPLETA +"\">" + ServletBúsqueda.OPCIÓN_PALABRA_COMPLETA);
		pw.println("<option value=\"" + ServletBúsqueda.OPCIÓN_POR_PREFIJO + "\">" + ServletBúsqueda.OPCIÓN_POR_PREFIJO);
		pw.println("</select> <br> <br>");

		//Declaración de la lista de filtro para buscar por al menos una ocurrencia de palabra/prefijo
		//o por al menos una ocurrencia de todas las palabras/prefijos ingresados.
		pw.println("Seleccione el filtro de la b&uacute;squeda:<br> <br>");
		pw.println("<select name=\"tipoFiltro\">");
		pw.println("<option selected value=\"" + ServletBúsqueda.OPCIÓN_TODAS_PALABRAS + "\">" + ServletBúsqueda.OPCIÓN_TODAS_PALABRAS + ">");
		pw.println("<option value=\"" + ServletBúsqueda.OPCIÓN_UNA_PALABRA + "\">" + ServletBúsqueda.OPCIÓN_UNA_PALABRA + ">");
		pw.println("</select>");

		//Tengo que agregar la URL en el sistema de carpetas virtual con los resultados de la
		//búsqueda.

		//Botones para buscar y volver
		pw.println("<input type=\"submit\" name=\"botonVolver\" value=\"Volver\">");
		pw.println("<input type=\"submit\" name=\"botonBuscar\" value=\"Realizar b&uacute;squeda\">");
		pw.println("</form> </body> </html>");

	}

	public static void imprimirEncabezadoString(String pw)
	{
		pw.concat("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
		pw.concat("<html>");

		pw.concat("<head>");
		pw.concat("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">");
		pw.concat("<title>Sistema de Reconocimiento</title>");

		pw.concat("</head>");

		pw.concat("<body style=\"font-family: Verdana\">"); 
	}

}
