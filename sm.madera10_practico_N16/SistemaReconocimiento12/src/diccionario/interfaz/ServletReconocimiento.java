package diccionario.interfaz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import diccionario.mundo.AdministradorANS;
import diccionario.mundo.Documento;

/**
 * Servlet que se encarga de recibir las solicitudes de la ventana para subir un archivo 
 * y reconocerlo.
 * Nombre: ServletReconocimiento
 * url-pattern: /reconocimiento.html
 */
public class ServletReconocimiento extends HttpServlet {


	//------------------------------------------------------------------------------------------------------------
	//M�todos
	//------------------------------------------------------------------------------------------------------------

	public void init()
	{

	}

	public void destroy()
	{

	}

	protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{
		System.err.println("En servletReconocimiento.doPost(): ");

		//Bot�n de cargar archivo
		String botonCargarArchivo = request.getParameter("botonCargarArchivo");

		//Bot�n para volver
		String botonVolverReconocimiento = request.getParameter("botonVolverReconocimiento");

		//Bot�n para reconocer un doc
		String botonReconocer = request.getParameter("botonReconocer");

		//Bot�n para ver un doc
		String botonVer = request.getParameter("botonVer");

		String botonVolverRecon = request.getParameter("botonVolverRecon");

		String botonVolverTexto = request.getParameter("botonVolverTexto");

		if(botonVolverReconocimiento != null)
		{
			ServletANS.mostrarMain(response);
		}

		else if (botonVolverTexto != null || botonVolverRecon != null)
		{
			ServletANS.mostrarVentanaReconocimiento(response);
		}

		else if (botonVer != null)
		{
			String nombreDocParaVer = request.getParameter("docReconocido");

			if(nombreDocParaVer == null || nombreDocParaVer.equals(""))
			{
				ServletANS.mostrarMensaje("Seleccione un documento de la lista de documentos reconocidos.");
				ServletANS.mostrarVentanaReconocimiento(response);
				return;
			}

			if(AdministradorANS.getInstance().darDiccionarioActual() == null)
			{
				ServletANS.mostrarMensaje("No hay un diccionario seleccionado para el reconocimiento.");
				ServletANS.mostrarVentanaReconocimiento(response);
				return;
			}

			Documento docActual = new Documento(nombreDocParaVer);

			try 
			{
				ServletANS.cambiarDocumentoActual(docActual);
			} 

			catch (Exception e) 
			{
				ServletANS.mostrarMensaje("No se encontr� el documento con nombre \"" + nombreDocParaVer + "\"");
				ServletANS.mostrarVentanaReconocimiento(response);
				e.printStackTrace();
				return;
			}

			try 
			{
				ServletANS.mostrarVentanaConTexto(response);
			} 
			catch (Exception e) 
			{
				ServletANS.mostrarMensaje("Hubo un problema mostrando el texto reconocido.");
				ServletANS.mostrarVentanaReconocimiento(response);
				e.printStackTrace();
				return;
			}
		}

		else if (botonReconocer != null)
		{
			String nombreDocSeleccionado = request.getParameter("docSeleccionado");

			try
			{
				reconocerCheat(nombreDocSeleccionado, response);
			}
			catch (Exception e)
			{

			}
		}

		else if (botonCargarArchivo != null)
		{
			String urlArchivo = request.getParameter("urlArchivo");
			String carpetaGuardar = request.getParameter("carpetaGuardar");
			
			try
			{
				cargarArchivo(urlArchivo, carpetaGuardar, response);
			}

			catch (Exception e)
			{
				e.printStackTrace();
				return;
			}
		}
	}

	protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{

	}

	public void cargarArchivo(String urlArchivo, String carpetaGuardar, HttpServletResponse response) throws Exception
	{
		BufferedReader br = null;
		try
		{
			System.out.println("ServletReconocimiento.cargarArchivo(): " + urlArchivo); //TODO
			File f = new File(urlArchivo);
			FileInputStream fis = new FileInputStream( f.getCanonicalPath() );
			InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
			br = new BufferedReader(isr);
		}
		catch (Exception e)
		{
			ServletANS.mostrarMensaje("Hubo un problema encontrando el archivo de texto en la direcci�n: " + urlArchivo
					+"\nVerifique que la direcci�n ingresada es correcta y vuelva a intentarlo.");
			ServletANS.mostrarVentanaReconocimiento(response);
			e.printStackTrace();
			return;
		}

		boolean a�adi� = false;

		try
		{
			if(carpetaGuardar == null || carpetaGuardar.equals("") )
			{
				a�adi� = AdministradorANS.getInstance().a�adirDocumentoConURL(urlArchivo, br);
			}
			else
			{
				a�adi� = AdministradorANS.getInstance().a�adirDocumentoConURL(urlArchivo, carpetaGuardar, br);
			}
		}

		catch (Exception e2)
		{
			ServletANS.mostrarMensaje("El archivo se encontr�, pero hubo un problema al leerlo");
			ServletANS.mostrarVentanaReconocimiento(response);
			e2.printStackTrace();
			return;
		}

		if (!a�adi�)
		{
			ServletANS.mostrarMensaje("El documento no pudo ser a�adido. Es posible que ya se haya a�adido otro con el mismo nombre.");
			ServletANS.mostrarVentanaReconocimiento(response);
			return;
		}

		ServletANS.mostrarMensaje("El archivo en la direcci�n: " + urlArchivo + " fue cargado �xitosamente.");
		ServletANS.mostrarVentanaReconocimiento(response);
	}

	public void reconocer(String nombreDocSeleccionado, HttpServletResponse response) throws Exception
	{
		System.err.println("En ServletReconocimiento.reconocer(): ");
		
		if(nombreDocSeleccionado == null || nombreDocSeleccionado.equals(""))
		{
			ServletANS.mostrarMensaje("Seleccione un documento de la lista de documentos cargados.");
			ServletANS.mostrarVentanaReconocimiento(response);
			return;
		}

		if(AdministradorANS.getInstance().darDiccionarioActual() == null)
		{
			ServletANS.mostrarMensaje("No hay un diccionario seleccionado para el reconocimiento.");
			ServletANS.mostrarVentanaReconocimiento(response);
			return;
		}

		Documento docActual = new Documento(nombreDocSeleccionado);
		Documento d = null;
		
		try 
		{
			ServletANS.cambiarDocumentoActual(docActual);
			d = AdministradorANS.getInstance().darDocumentos().get(docActual);
			System.out.println(d);
			System.out.println(d.darTexto());
		} 

		catch (Exception e) 
		{
			ServletANS.mostrarMensaje("No se encontr� el documento con nombre \"" + nombreDocSeleccionado + "\"");
			ServletANS.mostrarVentanaReconocimiento(response);
			e.printStackTrace();
			return;
		}
		
//		if (d == null)
//		{
//			ServletANS.mostrarMensaje("El doc seleccionado no existe. Ver en ServletReconocimiento.reconocer().");
//		}
		try
		{
			ServletANS.mostrarMensajeConScroll( d.reconocer(ServletANS.darDiccionarioActual()), "Texto reconocido" );
			ServletANS.mostrarMensaje("El documento fue reconocido �xitosamente.");
		}
		catch (Exception e2)
		{
			ServletANS.mostrarMensaje("No es posible reconocer el archivo con el diccionario actual. No est�n las palabras necesarias.");
			e2.printStackTrace();
			ServletANS.mostrarVentanaReconocimiento(response);
		}
	}
	
	public void reconocerCheat(String nombreDocSeleccionado, HttpServletResponse response) throws Exception
	{
		if(nombreDocSeleccionado == null || nombreDocSeleccionado.equals(""))
		{
			ServletANS.mostrarMensaje("Seleccione un documento de la lista de documentos cargados.");
			ServletANS.mostrarVentanaReconocimiento(response);
			return;
		}

		if(AdministradorANS.getInstance().darDiccionarioActual() == null)
		{
			ServletANS.mostrarMensaje("No hay un diccionario seleccionado para el reconocimiento.");
			ServletANS.mostrarVentanaReconocimiento(response);
			return;
		}

		Documento docActual = new Documento(nombreDocSeleccionado);
		Documento d = null;
		
		try 
		{
			ServletANS.cambiarDocumentoActual(docActual);
			d = AdministradorANS.getInstance().darDocumentos().get(docActual);
		} 

		catch (Exception e) 
		{
			ServletANS.mostrarMensaje("No se encontr� el documento con nombre \"" + nombreDocSeleccionado + "\"");
			ServletANS.mostrarVentanaReconocimiento(response);
			e.printStackTrace();
			return;
		}
		
//		if (d == null)
//		{
//			ServletANS.mostrarMensaje("El doc seleccionado no existe. Ver en ServletReconocimiento.reconocer().");
//		}
		try
		{
			ServletANS.mostrarMensajeConScroll( d.cheat(), "Texto reconocido" );
			ServletANS.mostrarMensaje("El documento fue reconocido �xitosamente.");
			ServletANS.mostrarVentanaReconocimiento(response);
		}
		catch (Exception e2)
		{
			ServletANS.mostrarMensaje("No es posible reconocer el archivo con el diccionario actual. No est�n las palabras necesarias.");
			e2.printStackTrace();
			ServletANS.mostrarVentanaReconocimiento(response);
		}
	}

}
