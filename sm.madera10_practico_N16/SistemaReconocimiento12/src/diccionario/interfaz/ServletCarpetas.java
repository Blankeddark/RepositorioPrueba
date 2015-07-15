package diccionario.interfaz;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import diccionario.mundo.AdministradorANS;
import diccionario.mundo.Carpeta;
import estructurasDatos.ASAVLTree;

/**
 * Servlet que se encarga de manejar las carpetas.
 * Nombre: ServletCarpetas
 * url-pattern: /carpetas.html
 *
 */
public class ServletCarpetas extends HttpServlet {

	public void init()
	{

	}

	public void destroy()
	{

	}

	protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{
		String botonVolverCarpetas = request.getParameter("botonVolverCarpetas");
		String botonCrear = request.getParameter("botonCrear");
		String botonBorrar = request.getParameter("botonBorrar");
		String botonExportar = request.getParameter("botonExportar");
		String botonImportar = request.getParameter("botonImportar");

		PrintWriter pw = response.getWriter();

		if (botonVolverCarpetas != null)
		{
			ServletANS.mostrarMain(response);
		}

		else if (botonCrear != null)
		{
			mostrarVentanaCrearCarpetas(response, pw);
		}

		else if (botonBorrar != null)
		{
			mostrarVentanaBorrar(response, pw);
		}

		else if (botonExportar != null)
		{
			try 
			{
				exportarCarpetas();
			} 
			catch (IOException e) 
			{
				ServletANS.mostrarMensaje("No se encontró la dirección para exportar el sistema de carpetas.");
				e.printStackTrace();
			}
			
			catch(Exception e2)
			{
				ServletANS.mostrarMensaje("Hubo un problema no esperado para exportar las carpetas. Problema:"
						+ "\n" + e2.getMessage());
				e2.printStackTrace();
			}
			
			ServletANS.getInstance().mostrarVentanaArchivos(response);
		}

		else if (botonImportar != null)
		{
			String urlImportar = request.getParameter("urlImportar");
			try
			{
				importarHTML(urlImportar);
			}
			
			catch(Exception e)
			{
				ServletANS.mostrarMensaje("Hubo un problema leyendo el archivo en la dirección: " + urlImportar +
						"\nPuede que no corresponda a un sistema de carpetas XML.");
				e.printStackTrace();
			}
			
			ServletANS.getInstance().mostrarVentanaArchivos(response);
		}
	}

	protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{
		String botonCrear = request.getParameter("botonCrear");
		String botonVolverCrear = request.getParameter("botonVolverCrear");
		String botonBorrar = request.getParameter("botonBorrar");
		String botonVolverBorrar = request.getParameter("botonVolverBorrar");
		
		PrintWriter pw = response.getWriter();

		if (botonCrear != null)
		{
			String nombreCarpeta = request.getParameter("nombreCarpeta");

			if(!crearCarpeta(nombreCarpeta))
			{
				ServletANS.mostrarMensaje("La carpeta no pudo ser creada, puede que ya exista otra con el mismo nombre");
				mostrarVentanaCrearCarpetas(response, pw);
				return;
			}

			ServletANS.mostrarMensaje("La carpeta fue creada exitosamente");
			mostrarVentanaCrearCarpetas(response, pw);
		}

		else if (botonVolverCrear != null || botonVolverBorrar != null)
		{
			ServletANS.getInstance().mostrarVentanaArchivos(response);
			return;
		}
		
		else if(botonBorrar != null) 
		{
			String nombreCarpetaSelec = request.getParameter("nombreCarpetaSelec");
			try 
			{
				borrarCarpeta(nombreCarpetaSelec);
				ServletANS.mostrarMensaje("La carpeta con nombre \"" + nombreCarpetaSelec + "\" fue borrada exitosamente.");
			} 
			catch (Exception e) 
			{
				ServletANS.mostrarMensaje("No se pudo borrar la carpeta de nombre: " + nombreCarpetaSelec);
				mostrarVentanaBorrar(response, pw);
				e.printStackTrace();
			}
			mostrarVentanaBorrar(response, pw);
		}
	}
	
	public static void mostrarVentanaBorrar(HttpServletResponse response, PrintWriter pw)
	{
		ServletANS.imprimirEncabezado(response, pw);
		pw.println("<form action=\"carpetas.html\" method=\"GET\" name=\"ff2\">");
		pw.println("Elija la carpeta a eliminar: " + "<select name=\"nombreCarpetaSelec\">");
		
		Iterator<Carpeta> it = AdministradorANS.getInstance().darCarpetas().iterator();
		
		while(it.hasNext())
		{
			Carpeta actual = it.next();
			pw.println("<option value=\"" + actual.darNombre() + "\">" + actual.darNombre());
		}
		
		pw.println("</select>");
		pw.println("<br><br><input type=\"submit\" name=\"botonBorrar\" value=\"Borrar\">");
		pw.println("<br><br><input type=\"submit\" name=\"botonVolverBorrar\" value=\"Volver\">");
		pw.println("</form> </body> </html>");
	}

	public static void mostrarVentanaCrearCarpetas(HttpServletResponse response, PrintWriter pw)
	{
		ServletANS.imprimirEncabezado(response, pw);
		pw.println("<form action=\"carpetas.html\" method=\"GET\" name=\"ff\">");
		pw.println("<br>Nombre: " + "<input type=\"text\" name=\"nombreCarpeta\">");
		pw.println("<br><br><input type=\"submit\" name=\"botonCrear\" value=\"Crear carpeta\">");
		pw.println("<br><input type=\"submit\" name=\"botonVolverCrear\" value=\"Volver\">");
		pw.println("</form>");
		pw.println("</body> </html>");
	}

	public boolean crearCarpeta(String nombreCarpeta)
	{
		Carpeta carpeta = new Carpeta(nombreCarpeta);
		boolean seAñadió = AdministradorANS.getInstance().añadirCarpeta(carpeta);

		return seAñadió;
	}

	public void exportarCarpetas() throws Exception
	{
		File f = new File(ServletANS.URL_SERIALIZACIÓN + "carpetas.info");

		try
		{
			new ObjectOutputStream( new FileOutputStream(f)).writeObject( (ASAVLTree<Carpeta>) AdministradorANS.getInstance().darCarpetas() );
			ServletANS.mostrarMensaje("El sistema de carpetas fue exportado correctamente.");
		}

		catch(Exception e)
		{
			System.out.println("En ServletCarpetas.exportarCarpetas(), la url absol es: " + f.getAbsolutePath());
			System.out.println("Y la url canon es: " + f.getCanonicalPath());
			e.printStackTrace();
		}
	}
	
	public void borrarCarpeta(String nombreCarpeta) throws Exception
	{
		AdministradorANS.getInstance().darCarpetas().remove(new Carpeta(nombreCarpeta));
	}

	public void importarHTML(String urlImportar) throws Exception
	{
		File f = new File(urlImportar);
		FileInputStream fis = new FileInputStream(f);
		ObjectInputStream objectInputStream = new ObjectInputStream(fis);
		AdministradorANS.getInstance().setCarpetas( (ASAVLTree<Carpeta>) objectInputStream.readObject() );
	}
}
