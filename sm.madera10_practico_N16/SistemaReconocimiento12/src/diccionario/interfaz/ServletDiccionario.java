package diccionario.interfaz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import diccionario.mundo.AdministradorANS;
import diccionario.mundo.Diccionario;
import diccionario.mundo.Palabra;
import estructurasDatos.ASList;

/**
 * Servlet que se encarga de las solicitudes a partir de la ventana de diccionarios.
 * Nombre: ServletDiccionario
 * URL Pattern: /diccionario.html
 * @author AS Team
 */
public class ServletDiccionario extends HttpServlet {

	//------------------------------------------------------------------------------------------------------------
	//Constantes
	//------------------------------------------------------------------------------------------------------------

	public static final String OPCIÓN_INICIAL = "Seleccione un diccionario";
	
	/**
	 * Diccionario seleccionado actualmente.
	 */
	private Diccionario dicSeleccionado;

	//------------------------------------------------------------------------------------------------------------
	//Métodos
	//------------------------------------------------------------------------------------------------------------

	public void init()
	{
		dicSeleccionado = null;
	}

	public void destroy()
	{

	}

	protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{
		//Verifica qué botón fue presionado

		String botonAgregarDiccionario = request.getParameter("botonAgregarDiccionario");
		String botonVolverDiccionario = request.getParameter("botonVolverDiccionario");
		String urlArchivo = request.getParameter("urlArchivo");
		String botonCargar = request.getParameter("botonCargar");
		
		urlArchivo = "C:\\Users\\Sergio\\Documents\\APO\\SistemaReconocimiento12\\data\\diccionario.dic";
		
		if(botonAgregarDiccionario != null)
		{
			agregarDiccionario(request, response);
		}
		
		else if (botonCargar != null)
		{
			agregarDiccionarioConURL(urlArchivo, response);
		}

		else if(botonVolverDiccionario != null)
		{
			ServletANS.getInstance().mostrarVentanaDiccionario(response);
		}

	}

	protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{
//		System.err.println("En ServletDiccionario.doGet(): ");
		
		String diccionarioSeleccionado = request.getParameter("Diccionarios");
		String botonVerInfoDiccionarios = request.getParameter("botonVerInfoDiccionario");
		String botonVolver = request.getParameter("botonVolver");
		String botonVolverDiccionario = request.getParameter("botonVolverDiccionario");
		String botonEditarEdicion = request.getParameter("botonEditarEdicion");
		String botonEditar = request.getParameter("botonEditar");
		String botonAgregar = request.getParameter("botonAgregar");
		String botonVolverEdicion = request.getParameter("botonVolverEdicion");

		if(diccionarioSeleccionado == null || diccionarioSeleccionado.equals(OPCIÓN_INICIAL) || 
				diccionarioSeleccionado.equals("") )
		{
			dicSeleccionado = null;
		}
		
		if (botonAgregar != null)
		{
			mostrarVentanaAgregarDiccionario(response);
		}
		
		
		else if(botonEditar != null)
		{
			mostrarVentanaEditarDiccionario(response, dicSeleccionado);
		}
		
		if(botonVerInfoDiccionarios != null && diccionarioSeleccionado != null)
		{
			if( !diccionarioSeleccionado.equals(OPCIÓN_INICIAL) )
			{
				Diccionario dic = new Diccionario(diccionarioSeleccionado);
				
				try 
				{
					dicSeleccionado = ServletANS.getInstance().buscarDiccionario(dic);
					mostrarInfoDiccionario(dicSeleccionado, response);
				} 
				
				catch (Exception e) 
				{
					ServletANS.mostrarMensaje("No se encontró el diccionario solicitado.");
					return;
				}

			}

			else
			{
				ServletANS.mostrarMensaje("Seleccione un diccionario de la lista");
				ServletANS.getInstance().mostrarVentanaDiccionario(response);
			}
		}

		else if(botonVolver != null)
		{
			ServletANS.mostrarMain(response);
		}

		else if (botonVolverDiccionario != null)
		{
			ServletANS.getInstance().mostrarVentanaDiccionario(response);
		}
		
		else if(botonEditarEdicion != null)
		{
			if (dicSeleccionado != null)
			{
				String textoEntrante = request.getParameter("textoPalabras");
				String[] palabrasEntrantes = textoEntrante.split(" ");
				
				for (int i = 0; i < palabrasEntrantes.length; i++)
				{
					String palabra = palabrasEntrantes[i];
					
					try
					{
						dicSeleccionado.añadirPalabra(palabra);
					}
					catch (Exception e) 
					{	
						ServletANS.mostrarMensaje("La palabra " + palabra + " no pudo ser agregada. Es posible"
								+ " \n que fuese agregada anteriormente. Todas las palabras anteriores a esta fueron"
								+ " \nagregadas correctamente.");
						e.printStackTrace();
						return;
					}
					
				}
				
				ServletANS.mostrarMensaje("Todas las palabras fueron agregadas al diccionario correctamente."
						+ "\nPresione \"Volver\" para regresar a la ventana general de diccionarios.");
				
			}
			
			else
			{
				ServletANS.mostrarMensaje("No hay un diccionario seleccionado actualmente.");
			}
			
			mostrarVentanaEditarDiccionario(response, dicSeleccionado);
		}
		
		else if (botonVolverEdicion != null)
		{
			ServletANS.getInstance().mostrarVentanaDiccionario(response);
		}

	}

	public void agregarDiccionario(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String contenidoDiccionario = request.getParameter("contenidoDiccionario");
		String nombreDiccionario = request.getParameter("nombreDiccionario");
		boolean sinErrores = true;

		if (contenidoDiccionario == null || nombreDiccionario == null)
		{
			ASList<String> errores = new ASList<String>(2);

			if(contenidoDiccionario == null)
			{
				errores.add("Contenido diccionario");
			}

			if(nombreDiccionario == null)
			{
				errores.add("Nombre diccionario");
			}

			ServletANS.mostrarVentanaError(errores, response);
			return;
		}

		else if(contenidoDiccionario.equals("") || nombreDiccionario.equals(""))
		{
			ASList<String> errores = new ASList<String>(2);

			if(contenidoDiccionario.equals(""))
			{
				errores.add("Contenido diccionario");
			}

			if(nombreDiccionario.equals(""))
			{
				errores.add("Nombre diccionario");
			}
			ServletANS.mostrarVentanaError(errores, response);
			return;
		}

		Diccionario d = new Diccionario(nombreDiccionario);

		String[] palabras = contenidoDiccionario.split(" ");
		for(int i = 0; i < palabras.length; i++)
		{
			try 
			{
				if (!d.añadirPalabra(palabras[i]))
				{
					ServletANS.mostrarMensaje("La palabra \"" + palabras[i] + "\" no pudo ser agregada. Es"
							+ " posible que fuera agregada anteriormente al diccionario. Las \npalabras en la lista"
							+ " anteriores a esta fueron agregadas correctamente. No intente volver a agregarlas");
					
					sinErrores = false;
					
				}
			} 
			catch (Exception e) 
			{
				ServletANS.mostrarMensaje(e.getMessage());
				e.printStackTrace();
			}
		}
		
		if (ServletANS.getInstance().agregarDiccionario(d))
		{
			if(sinErrores)
			{
			ServletANS.mostrarMensaje("El diccionario \"" + nombreDiccionario + "\" fue creado éxitosamente.");
			}
			else
			{
				ServletANS.mostrarMensaje("El diccionario \"" + nombreDiccionario + "\" fue creado, pero hubo "
						+ "advertencias \nadicionales. Revise los mensajes con las advertencias que aparecieron "
						+ "durante el proceso.");
			}
		}
		
		else
		{
			ServletANS.mostrarMensaje("El diccionario no pudo ser agregado a la lista. \nEs posible que ya haya otro diccionario con el mismo nombre.");
		}
		
		ServletANS.getInstance().mostrarVentanaDiccionario(response);

	}

	public void mostrarVentanaAgregarDiccionario(HttpServletResponse response)
	{
		PrintWriter pw = null;
		try
		{
			pw = response.getWriter();
			ServletANS.imprimirEncabezado(response, pw);

			pw.println("<br> <br>");
			pw.println("<form method=\"POST\" action=\"diccionario.html\" name=\"agDic\">");
			pw.println("Ingrese nombre: <input type=text name=\"nombreDiccionario\">"); //TODO

			pw.println("<br> <br>Ingrese contenido:");
			pw.println("<textarea name=\"contenidoDiccionario\" size=\"5\">"); //TODO

			pw.println("</textarea>");

			pw.println("<input type=\"submit\" name=\"botonVolverDiccionario\" value=\"Volver\">");
			pw.println("<input type=\"submit\" name=\"botonAgregarDiccionario\" value=\"Agregar\">");

			pw.println("</form> <br> <br>");
			
			pw.println("<form action=\"diccionario.html\" method=\"POST\" name=\"k\">");
			
			pw.println("<input type=\"file\" name=\"urlArchivo\"> <br>");
			pw.println("<input type=\"submit\" name=\"botonCargar\" value=\"Cargar archivo\">");

			pw.println("</form>");
			pw.println("</body>");
			pw.println("</html>");
		}
		
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "Hubo un problema. Error: " + e.getMessage());
		}
	}

	public void mostrarVentanaEditarDiccionario(HttpServletResponse response, Diccionario dicSeleccionado)
	{
		PrintWriter pw = null;
		
		try
		{
			pw = response.getWriter();
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "Hubo un problema buscando el PrintWriter");
		}
		
		ServletANS.imprimirEncabezado(response, pw);
		pw.println("<br> <br>");
		pw.println("Introduzca las palabras para agregar al diccionario con un espacio entre ellas:");
		pw.println("<form action=\"diccionario.html\" method=\"GET\">");
		pw.println("<textarea name=\"textoPalabras\" size=\"5\"></textarea>");
		pw.println("<br> <br>");
		pw.println("<input type=\"submit\" name=\"botonVolverEdicion\" value=\"Volver\">");
		pw.println("<input type=\"submit\" name=\"botonEditarEdicion\" value=\"Editar\">");
		pw.println("</form> </body> </html>");
		
	}
	
	public void agregarDiccionarioConURL(String urlArchivo, HttpServletResponse response) throws IOException
	{
		System.err.println("En ServletDiccionario.agregarDiccionarioConURL(): ");
		
		if (urlArchivo == null || urlArchivo.equals("") )
		{
			ServletANS.mostrarMensaje("La URL ingresada no es válida.");
			ServletANS.getInstance().mostrarVentanaDiccionario(response);
		}
		
		else
		{
			BufferedReader br = null;
			try
			{
				File f = new File(urlArchivo);
				FileInputStream fis = new FileInputStream( f );
				InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
				br = new BufferedReader(isr);
			}
			
			catch (Exception e)
			{
				ServletANS.mostrarMensaje("Hubo un problema encontrando el archivo de texto en la dirección: " + urlArchivo
						+"\nVerifique que la dirección ingresada es correcta y vuelva a intentarlo.");
				mostrarVentanaAgregarDiccionario(response);
				e.printStackTrace();
				return;
			}
			
			String[] urlSplit = urlArchivo.split("\\\\");
			String nombreDic = urlSplit[urlSplit.length - 1];
			Diccionario nuevoDic = new Diccionario(nombreDic);
			String línea = br.readLine();
			
			while(línea != null)
			{
				try 
				{
					if((int) línea.charAt(0) == 65279)
					{
						línea = línea.substring(1);
					}
					
					if (!nuevoDic.añadirPalabra(línea))
					{
						ServletANS.mostrarMensaje("Hubo un problema (err1) añadiendo la palabra: \"" + línea + "\".\nEs posible que haya sido"
								+ "añadida anteriormente.");
						mostrarVentanaAgregarDiccionario(response);
						return;
					}

					boolean esNumero = false;
					
					try
					{
						Double.parseDouble(línea);
						esNumero = true;
					}
					catch(Exception e2)
					{
						
					}
					
					boolean empiezaRaro = (línea.charAt(0) == '¿');

					boolean empiezaRaro2 = (línea.charAt(0) == '(');
					
					boolean primeraLetraNumero = false;
					try
					{
						Integer.parseInt(String.valueOf(línea.charAt(0)));
						primeraLetraNumero = true;
					}
					
					catch(Exception e4)
					{
						
					}
					
					boolean empiezaRaro3 = (línea.charAt(0) == '-');
					
					boolean esDeUnaSolaLetra = (línea.length() == 1);
					
					if (línea.charAt(0) != '«' && !esNumero && !empiezaRaro && !empiezaRaro2 && 
							!primeraLetraNumero && !empiezaRaro3 && !esDeUnaSolaLetra)
					{
						if(!nuevoDic.añadirPalabraConMayus(línea))
						{
							ServletANS.mostrarMensaje("Hubo un problema (err3, mayus) añadiendo la palabra: \"" + línea + "\".\nEs posible que haya sido"
									+ "añadida anteriormente.");
							mostrarVentanaAgregarDiccionario(response);
							return;
						}
					}
					
					
				} 
				
				catch (Exception e)
				{
					ServletANS.mostrarMensaje("Hubo un problema (err2) añadiendo la palabra: " + línea + ".\nEs posible que haya sido"
							+ "añadida anteriormente.");
					e.printStackTrace();
					br.close();
					return;
				}
				
				línea = br.readLine();
			}
			
			ServletANS.getInstance().agregarDiccionario(nuevoDic);
			ServletANS.getInstance().asignarDiccionario(nuevoDic);
			ServletANS.mostrarMensaje("El diccionario y sus palabras fueron "
					+ "agregadas exitosamente.");
			mostrarVentanaAgregarDiccionario(response);
			br.close();
		}
	}

	/**
	 * Muestra la información del diccionario cuyo nombre es pasado como parámetro. Se basa
	 * en el archivo en ./WebContent/infoDiccionarios.html 
	 * @param nombreDiccionario nombre del diccionario cuya información se quiere mostrar
	 * @param response respuesta al servidor.
	 */
	public void mostrarInfoDiccionario(Diccionario dic, HttpServletResponse response)
	{
		try {

			PrintWriter pw = response.getWriter();
			ServletANS.imprimirEncabezado(response, pw);

			pw.println("<br> <br>");
			pw.println("<center>");
			pw.println("<form action=\"diccionario.html\" method=\"GET\" name=\"volverDiccionario\">");
			pw.println("<input type=\"submit\" name=\"botonVolverDiccionario\" value=\"Volver\">");
			pw.println("</form> <br>");

			pw.println("<table border=\"1\" width=\"230px\">");

			pw.println("<col width=\"125px\" />");
			pw.println("<col width=\"105px\" />");
			pw.println("<tr>");
			pw.println("<th>Palabras");
			pw.println("<th>Cantidad palabras");
			pw.println("</tr>");

			boolean primerRecorrido = true;
			ASList<String> listaPalabras = dic.darListaPalabras();
			for(int i = 0; i < listaPalabras.size(); i++)
			{
				String actual = listaPalabras.get(i);
				pw.println("<tr>");
				pw.println("<td>");
				pw.println(actual);
				pw.println("</td>");

				if (primerRecorrido)
				{
					pw.println("<td>");
					pw.println(dic.darNúmeroPalabras());
					pw.println("</td>");
					primerRecorrido = false;
				}

				pw.println("</tr>");
			}


			pw.println("</table>");

			pw.println("</center>");

			pw.println("</body>");
			pw.println("</html>");

		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			ServletANS.mostrarMensaje(e.getMessage());
		}

	}

}


