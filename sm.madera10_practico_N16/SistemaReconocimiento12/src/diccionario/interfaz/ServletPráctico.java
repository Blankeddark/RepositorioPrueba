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
import diccionario.mundo.PalabraPractico;
import estructurasDatos.ASList;

/**
 * Nombre: ServletPractico
 * url-pattern: /practico.html
 */
public class ServletPráctico extends HttpServlet {

	private ASList<Palabra> listaZensual;

	public void init()
	{

	}

	protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{
		String botonVerInformacion = request.getParameter("botonVerInformacion");
		String botonVolver = request.getParameter("botonVolver"); 
		String botonTop10 = request.getParameter("botonTop10");

		if (botonVolver != null)
		{
			ServletANS.mostrarMain(response);
		}

		else if (botonVerInformacion != null)
		{
			String nombreDoc = request.getParameter("nombreDoc");
			Documento doc = null;
			try {
				doc = AdministradorANS.getInstance().darDocumentos().get(new Documento(nombreDoc) );
			} catch (Exception e) 
			{
				ServletANS.mostrarMensaje("Pasó algo raro en ServletPráctico.doGet()");
				e.printStackTrace();
				return;
			}

			mostrarVentanaInformación(response, doc);
		}

		else if (botonTop10 != null)
		{
			String nombreDoc = request.getParameter("nombreDoc");

			Documento doc= null;
			try {
				doc = AdministradorANS.getInstance().darDocumentos().get(new Documento(nombreDoc) );
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (doc == null)
			{
				ServletANS.mostrarMensaje("El documento solicitado no fue encontrado.");
				ServletANS.mostrarVentanaPractico(response);
				return;
			}

			mostrarVentanaTop10(response, doc);
		}
	}

	protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{
		String botonVolverInfo = request.getParameter("botonVolverInfo");
		String botonVolverT10 = request.getParameter("botonVolverT10");

		if ( botonVolverInfo != null)
		{
			ServletANS.mostrarVentanaPractico(response);
		}

		else if (botonVolverT10 != null)
		{
			ServletANS.mostrarVentanaPractico(response);
		}
	}

	public void mostrarVentanaInformación(HttpServletResponse response, Documento doc) throws IOException
	{
		PrintWriter pw = response.getWriter();

		pw.println("<center>");
		pw.println("<form action=\"practico.html\" method=\"POST\" name=\"forumu\">");
		pw.println("<input type=\"submit\" name=\"botonVolverInfo\" value=\"Volver\">");
		pw.println("</form>");
		pw.println("<table border=\"1\" width=\"230px\">");

		pw.println("<col width=\"125px\" />");
		pw.println("<col width=\"105px\" />");
		pw.println("<tr>");
		pw.println("<th>Palabras");
		pw.println("<th>Frecuencia");
		pw.println("</tr>");

		ordenarAlfabEnLista(doc, response);

		for(int i = 0; i < listaZensual.size(); i++)
		{
			pw.println("<tr>");
			Palabra actual = listaZensual.get(i);
			pw.println("<td>" + actual.darPalabra());
			if(actual.darFrecuencia() == 0)
			{
				actual.aumentarFrecuencia();
			}
			pw.println("<td>" + actual.darFrecuencia());
			pw.println("</tr>");
		}

		pw.println("</table> </cemter> </body> </html>");
	}

	public void ordenarAlfabEnLista(Documento doc, HttpServletResponse response) throws IOException
	{
		if(doc.darPalabrasQueContiene() == null)
		{
			ServletANS.mostrarMensaje("El documento seleccionado no es un documento a ser reconocido. Puede que sea el resultado de un "
					+ "reconocimiento anterior (estos no tienen la lista de palabras contenidas)");
			ServletANS.mostrarVentanaPractico(response);
		}


		Iterator<Palabra> it = doc.darPalabrasQueContiene().iterator();
		listaZensual = new ASList<Palabra>(doc.darPalabrasQueContiene().darPeso());

		while(it.hasNext())
		{
			listaZensual.addSorted(it.next());
		}
	}

	public void mostrarVentanaTop10(HttpServletResponse response, Documento doc) throws IOException
	{
		PrintWriter pw = response.getWriter();

		ordenarAlfabEnLista(doc, response);

		ServletANS.imprimirEncabezado(response, pw);
		pw.println("<center>");
		pw.println("<form method=\"POST\" action=\"practico.html\" name=\"fere\">");
		pw.println("<input type=\"submit\" name=\"botonVolverT10\" value=\"volver\">");
		pw.println("</form>");

		ASList<PalabraPractico> lista = ordenarPorFrecuenciaLista();

		for(int i = 0; i < 10; i++)
		{
			PalabraPractico actual = lista.get(i);
			pw.println(i + ". " + actual.darPalabra() + " ---- Frecuencia: " + actual.darFrecuencia() );
			pw.println("<br><br>");
		}

		pw.println("</body> </html>");
	}

	/**
	 * pre: la lista ya tiene elementos
	 */
	public ASList<PalabraPractico> ordenarPorFrecuenciaLista()
	{
		ASList<PalabraPractico> rta = new ASList<PalabraPractico>();
		
		if(listaZensual.isEmpty())
		{
			return rta;
		}

		for(int i = 0; i < listaZensual.size(); i++)
		{
			Palabra actual = listaZensual.get(i);
			PalabraPractico pp = new PalabraPractico(actual);
			rta.addSorted(pp);
		}
		
		return rta;
	}

}
