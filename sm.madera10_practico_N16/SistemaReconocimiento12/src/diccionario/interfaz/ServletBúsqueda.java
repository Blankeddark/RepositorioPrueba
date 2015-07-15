package diccionario.interfaz;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import diccionario.mundo.AdministradorANS;
import diccionario.mundo.Documento;
import diccionario.mundo.Palabra;
import estructurasDatos.ASAVLTree;

/**
 * Servlet que se encarga de la parte de búsqueda
 * Nombre: ServletBusqueda
 * url-pattern: /busqueda.html
 */
public class ServletBúsqueda extends HttpServlet {


	//------------------------------------------------------------------------------------------------------------
	//Constantes
	//------------------------------------------------------------------------------------------------------------

	/**
	 * Si el usuario seleccionó esto en el panel, se hace la búsqueda por prefijos.
	 */
	public final static String OPCIÓN_POR_PREFIJO = "Por prefijos";

	/**
	 * Si el usuario seleccionó esto en el panel, se hace la búsqueda por palabras completas.
	 */
	public final static String OPCIÓN_PALABRA_COMPLETA = "Palabra completa";

	/**
	 * Si el usuario seleccionó esto en el panel, se hace la búsqueda con todas las palabras/prefijos
	 * ingresados.
	 */
	public final static String OPCIÓN_TODAS_PALABRAS = "Todas las palabras/prefijos";

	/**
	 * Si el usuario seleccionó esto en el panel, se hace la búsqueda con al menos una de las palabras/prefijos
	 * ingresados.
	 */
	public final static String OPCIÓN_UNA_PALABRA = "Al menos una palabra/prefijo";

	//------------------------------------------------------------------------------------------------------------
	//Atributos
	//------------------------------------------------------------------------------------------------------------


	//------------------------------------------------------------------------------------------------------------
	//Métodos
	//------------------------------------------------------------------------------------------------------------

	public void init()
	{

	}

	public void destroy()
	{

	}

	protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{
		String botonBuscar = request.getParameter("botonBuscar");
		String botonVolver = request.getParameter("botonVolver");
		String botonVolverResultados = request.getParameter("botonVolverResultados");

		if(botonVolverResultados != null)
		{
			try 
			{
				ServletANS.mostrarVentanaBúsqueda(response);
			} 

			catch (Exception e) 
			{
				ServletANS.mostrarMensaje("Hubo un problema mostrando la ventana búsqueda");
				ServletANS.mostrarMain(response);
				e.printStackTrace();
				return;
			}
		}

		else if(botonBuscar != null)
		{
			//Lista de todos los documentos almacenados.
			ASAVLTree<Documento> docs = AdministradorANS.getInstance().darDocumentos();

			//Si no hay documentos, me detengo.
			if(docs.isEmpty())
			{
				ServletANS.mostrarMensaje("No hay documentos sobre los que realizar la búsqueda. Cargue un\ndocumento en el sistema e inténtelo de nuevo.");
				ServletANS.mostrarMain(response);
				return;
			}

			//Árbol AVL con los documentos resultados de la búsqueda.
			ASAVLTree<Documento> rta = new ASAVLTree<Documento>();

			String contenidoBuscar = request.getParameter("contenidoBuscar");
			String tipoBusqueda = request.getParameter("tipoBusqueda");
			String tipoFiltro = request.getParameter("tipoFiltro");

			String[] contenidoSplit = contenidoBuscar.split(", ");
			Iterator<Documento> it = docs.iterator();

			if(tipoBusqueda.equals(OPCIÓN_PALABRA_COMPLETA))
			{
				//Si quiere que el documento contenga todas las palabras completas.
				if (tipoFiltro.equals(OPCIÓN_TODAS_PALABRAS))
				{
					while(it.hasNext())
					{
						Documento actual = it.next();
						boolean tieneTodas = true;

						for (int i = 0; i < contenidoSplit.length; i++)
						{
							Palabra p;

							try 
							{
								p = new Palabra(contenidoSplit[i]);
							}

							catch (Exception e)
							{
								ServletANS.mostrarMensaje("Una de las palabras ingresadas para la búsqueda no es válida. Es probable que sea"
										+ " una palabra vacía");
								ServletANS.mostrarMain(response);
								e.printStackTrace();
								return;
							}

							if(!actual.darPalabrasQueContiene().contains(p))
							{
								//Detengo el for si le falta una de las palabras, y continúo al siguiente
								//documento sin agregar el actual.
								tieneTodas = false;
								break;
							}
						}

						if (tieneTodas)
						{
							try 
							{
								rta.add(actual);
							}
							catch (Exception e) 
							{
								ServletANS.mostrarMensaje("Esto no debería suceder. Ubicación: ServletBúsqueda.doPost()");	
								e.printStackTrace();
							}
						}
					}
				}

				//Si quiere que el documento contenga al menos una palabra completa.
				else if(tipoFiltro.equals(OPCIÓN_UNA_PALABRA))
				{
					while(it.hasNext())
					{
						Documento actual = it.next();

						for(int i = 0; i < contenidoSplit.length; i++)
						{
							if(actual.darTextoReconocido().contains(contenidoSplit[i]))
							{
								try 
								{
									rta.add(actual);
								} 

								catch (Exception e)
								{
									ServletANS.mostrarMensaje("El texto no puede ser null");
									ServletANS.mostrarMain(response);
									e.printStackTrace();
									return;
								}
							}
						}
					}
				}

				else
				{
					ServletANS.mostrarMensaje("La opción de filtro (todas las palabras/prefijos o al menos uno) no es válida.");

					try
					{
						ServletANS.mostrarVentanaBúsqueda(response);
					} 

					catch (Exception e) 
					{
						e.printStackTrace();
					}
					return;
				}
			}

			else if(tipoBusqueda.equals(OPCIÓN_POR_PREFIJO))
			{

				if (tipoFiltro.equals(OPCIÓN_TODAS_PALABRAS))
				{
					while (it.hasNext())
					{
						boolean tieneTodas = true;
						Documento actual = it.next();

						for (int i = 0; i < contenidoSplit.length; i++)
						{
							String prefijo = contenidoSplit[i];
							Iterator<String> iter = actual.darPalabrasQueContieneTrie().buscarPalabrasConPrefijo(prefijo);

							//Si no tiene al menos una palabra con el prefijo actual, no lo agrego a la respuesta y continúo.
							//Esto es porque necesito que tenga TODOS.
							if (! (AdministradorANS.darTamañoIterator(iter) >= 1) )
							{
								tieneTodas = false;
								break;
							}
						}

						if(tieneTodas)
						{
							try 
							{
								rta.add(actual);
							} 
							catch (Exception e)
							{
								ServletANS.mostrarMensaje("Pasó algo raro generando una respuesta a la solicitud de búsqueda.");
								ServletANS.mostrarMain(response);
								e.printStackTrace();
								return;
							}
						}
					}
				}

				else if(tipoFiltro.equals(OPCIÓN_UNA_PALABRA))
				{
					Documento actual = it.next();
					for (int i = 0; i < contenidoSplit.length; i++)
					{
						String prefijo = contenidoSplit[i];
						Iterator<String> iter = actual.darPalabrasQueContieneTrie().buscarPalabrasConPrefijo(prefijo);

						//Si ya tiene al menos una, lo agrego a la respuesta y paso al siguiente.
						if ( AdministradorANS.darTamañoIterator(iter) >= 1 )
						{
							try 
							{
								rta.add(actual);
							} 
							catch (Exception e)
							{
								ServletANS.mostrarMensaje("Pasó algo raro generando una respuesta a la solicitud de búsqueda.");
								ServletANS.mostrarMain(response);
								e.printStackTrace();
								return;
							}

							break;
						}
					}
				}

				else
				{
					ServletANS.mostrarMensaje("La opción de filtro (todas las palabras/prefijos o al menos uno) no es válida.");

					try
					{
						ServletANS.mostrarVentanaBúsqueda(response);
					} 

					catch (Exception e) 
					{
						e.printStackTrace();
					}
					return;
				}
			}

			else
			{
				ServletANS.mostrarMensaje("La opción de búsqueda (por prefijo o palabra completa) no es válida.");

				try
				{
					ServletANS.mostrarVentanaBúsqueda(response);
				} 

				catch (Exception e) 
				{
					e.printStackTrace();
				}
				return;
			}

			ServletANS.mostrarMensaje("La búsqueda se realizó éxitosamente");
			mostrarVentanaResultadosBúsqueda(response, rta);
		}

		else if (botonVolver != null)
		{
			ServletANS.mostrarMain(response);
		}
	}

	protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{

	}

	public void mostrarVentanaResultadosBúsqueda(HttpServletResponse response, ASAVLTree<Documento> docs) throws IOException
	{
		PrintWriter pw = response.getWriter();
		ServletANS.imprimirEncabezado(response, pw);

		pw.println("<br> <br>");
		pw.println("<center>");
		if (!docs.isEmpty())
		{
			pw.println("Los resultados de la b&uacute;squeda son:<br><br> ");
			Iterator<Documento> it = docs.iterator();
			while (it.hasNext())
			{
				Documento actual = it.next();
				String nombre = actual.darNombre();
				pw.println("Nombre documento: " + nombre.substring(0, nombre.length() - 4) +"<br>");
				pw.println("	 	URL: " + actual.darUrlLocal() + "<br><br>");
			}
		}

		else
		{
			pw.println("<font color =\"red\"> La búsqueda no tuvo ningún resultado </font>");
		}

		pw.println("<br> <br>");
		pw.println("<form action=\"busqueda.html\" method=\"POST\" name=\"fr\">");
		pw.println("<input type=\"submit\" name=\"botonVolverResultados\" value=\"Volver\">");
		pw.println("</form> </center> </body> </html>");
	}

}
