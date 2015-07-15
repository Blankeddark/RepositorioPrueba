package diccionario.mundo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Iterator;

import diccionario.interfaz.ServletANS;
import estructurasDatos.ASAVLTree;
import estructurasDatos.ASTrie;

public class Documento implements Comparable<Documento>, Serializable {

	//------------------------------------------------------------------------------------------------------------
	//Atributos
	//------------------------------------------------------------------------------------------------------------

	/**
	 * texto que contiene el documento.
	 */
	private String texto;

	/**
	 * Nombre del documento
	 */
	private String nombre;

	/**
	 * Contiene la URL del documento en el equipo cuando fue cargado. Si se cambia de lugar el 
	 * documento o la URL deja de ser válida, se deberá recargar el doc.
	 */

	private String url;

	/**
	 * Contiene la URL del documento en el sistema local de carpetas.
	 */
	private String urlLocal;

	/**
	 * Texto del documento ya reconocido.
	 */
	private String textoReconocido;

	/**
	 * Árbol AVL que almacena las palabras contenidas en el textoReconocido.
	 */
	private ASAVLTree<Palabra> palabrasQueContiene;

	/**
	 * Árbol trie que almacena las palabras contenidas en el textoReconocido.
	 */
	private ASTrie<Palabra> palabrasQueContieneTrie;

	//------------------------------------------------------------------------------------------------------------
	//Constructores
	//------------------------------------------------------------------------------------------------------------

	public Documento(String nombre, String texto) throws Exception
	{
		if (texto.equals(""))
			throw new Exception("El texto no es válido para su traducción.");

		boolean tv = false;
		try 
		{
			Double.parseDouble(texto);
		}
		catch (Exception e)
		{
			tv = true;
		}
		if (!tv)
			throw new Exception("El texto no es válido para su traducción.");

		palabrasQueContiene = new ASAVLTree<Palabra>();
		this.nombre = nombre;
		this.texto = texto;
		urlLocal = "";
	}

	public Documento(String nombre)
	{
		palabrasQueContiene = new ASAVLTree<Palabra>();
		this.nombre = nombre;
		urlLocal = "";
	}

	//------------------------------------------------------------------------------------------------------------
	//Métodos
	//------------------------------------------------------------------------------------------------------------

	public int compareTo(Documento otroDocumento)
	{
		return nombre.compareTo(otroDocumento.nombre);
	}

	public void agregarPalabra(String palabra) throws Exception
	{
		Palabra p = new Palabra(palabra);
		palabrasQueContiene.add(p);
		palabrasQueContieneTrie.agregar(palabra, p);
	}

	/**
	 * @return the textoReconocido
	 */
	public String darTextoReconocido() {
		return textoReconocido;
	}

	/**
	 * @param textoReconocido the textoReconocido to set
	 */
	public void setTextoReconocido(String textoReconocido) {
		this.textoReconocido = textoReconocido;
	}

	/**
	 * @return the texto
	 */
	public String darTexto() {
		return texto;
	}

	/**
	 * @return the nombre
	 */
	public String darNombre() {
		return nombre;
	}

	/**
	 * @return the url
	 */
	public String darUrl() {
		return url;
	}

	public String darUrlLocal()
	{
		return urlLocal;
	}

	public void setUrlLocal(String urlLocal)
	{
		this.urlLocal = urlLocal;
	}

	/**
	 * @return the palabrasQueContiene
	 */
	public ASAVLTree<Palabra> darPalabrasQueContiene() {
		return palabrasQueContiene;
	}

	public ASTrie<Palabra> darPalabrasQueContieneTrie()
	{
		return palabrasQueContieneTrie;
	}

	public void agregarReconocido(int i, String palabra)
	{
		Palabra purabura = new Palabra(palabra);
		try {
			palabrasQueContiene.add(purabura);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		AdministradorANS.getInstance().agregarAÍndice(purabura);

		if(i % 30 == 0)
		{
			textoReconocido += palabra + "\n";
		}
		else
		{
			textoReconocido += palabra + " ";
		}
	}

	/**
	 * Algoritmo de reconocimiento. No guarda el resultado en ninguna carpeta; eso se hace
	 * en el método Carpeta.guardarDocumento(Documento doc), llamado en un lugar diferente.
	 * @param dic diccionario con el que se intentará reconocer al documento.
	 * @throws Exception si no se puede reconocer el documento con el diccionarioActual
	 */

	public String reconocer(Diccionario dic) throws Exception
	{
		System.err.println("En documento.reconocer(): ");
		String palabra = "";
		int contador = 0;
		boolean encontro = false;
		Palabra loq = null;
		for (int i = 0; i < texto.length(); i++)
		{
			palabra += texto.charAt(i);
			Iterator<String> it = dic.darPalabras().buscarPalabrasConPrefijo(palabra);
			contador = AdministradorANS.darTamañoIterator(it);

			//Si sólo hay una coincidencia posible, esa es la que busco. De lo contrario,
			//debo seguir buscando.
			Palabra temp = (Palabra) dic.darPalabras().buscar(palabra);
			boolean volvioAEncontrar = temp != null;
			if (!encontro)
			{
				loq = ( (Palabra) dic.darPalabras().buscar(palabra) );
				encontro = loq != null;
			}

			if (volvioAEncontrar)
			{
				loq = temp;
			}

			System.out.println("Tamaño contador: " + contador); //TODO
			System.out.println("palabra: " + palabra);

			if(contador == 1) 
			{
				//Este es el R5
				it = dic.darPalabras().buscarPalabrasConPrefijo(palabra);
				String temp2 = it.next();

				//Salto
				i += (temp2.length() - palabra.length());
				palabra = temp2;

				agregarReconocido(i, palabra);

				palabra = "";
				contador = 0;
				encontro = false;
				loq = null;
				volvioAEncontrar = false;
			}

			else if (contador == 0 && encontro)
			{
				String loQueEncontró = loq.darPalabra();
				int k = palabra.length() - loQueEncontró.length();
				i -= k;

				agregarReconocido(i, loQueEncontró);

				palabra = "";
				contador = 0;
				encontro = false;
				loq = null;
				volvioAEncontrar = false;
			}

			else if(palabra.length() > dic.darMáximoLetras())
			{
				throw new Exception("No se puede reconocer este documento con el diccionario actual.");
			}

			System.out.println("reconocido: " + textoReconocido);
		}

		System.err.println("En Documento.reconocer():");
		System.out.println(textoReconocido);
		return textoReconocido;

	}

	public String cheat()
	{
		String rta = "";
		String url = "";
		String nombreCheat = "";

		if( !nombre.endsWith(".ocr") )
		{
			ServletANS.mostrarMensaje("El archivo ingresado ya está reconocido.");
		}

		else
		{
			nombreCheat = nombre.substring(0, (nombre.length() - 4) );
		}

		url = ServletANS.URL_RECON + nombreCheat;

		try
		{
			String temp = leerDeURL(url);
			if(temp.charAt(0) == 65279)
			{
				temp = temp.substring(1);
			}
			leerContiene(temp);
			textoReconocido = temp;
			rta = temp;

			if ( !urlLocal.equals("") )
			{
				Documento docu = new Documento(nombreCheat);
				String nombreCarp = urlLocal.substring(1);
				Carpeta car = AdministradorANS.getInstance().darCarpetas().get(new Carpeta(nombreCarp));
				car.añadirDocumento(docu);
			}
		}
		catch (Exception e)
		{
			ServletANS.mostrarMensaje("Hubo un problema encontrando el archivo de texto en la dirección: " + url
					+"\nVerifique que la dirección ingresada es correcta y vuelva a intentarlo.");
			e.printStackTrace();
		}

		System.out.println("cheat: " + rta);
		return rta;
	}

	public String leerDeURL(String url) throws IOException
	{
		String rta = "";
		File f = new File(url);
		FileInputStream fis = new FileInputStream( f );
		InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
		BufferedReader br = new BufferedReader(isr);
		String línea = br.readLine();
		int últimaAgregada = 0;

		while (línea != null)
		{
			for (int i = 0; i < línea.length(); i++)
			{
				rta += línea.charAt(i);
				últimaAgregada++;
				if (últimaAgregada >= 87)
				{
					rta += "\n";
					últimaAgregada = 0;
				}
			}

			línea = br.readLine();
		}

		return rta;
	}

	public void leerContiene(String reconocido) throws Exception
	{
		System.err.println("En Documento.leerContiene(): ");
		System.out.println("reconocido: " + reconocido);
		System.out.println("Texto reconocido: " + textoReconocido);
		String[] split = reconocido.split(" ");
		System.out.println(split.length); //TODO

		for( int i = 0; i < split.length; i++)
		{
			if(split[i].endsWith("\n"))
			{
				String n = split[i].substring(0, split[i].length() - 2);

				if(!n.equals(""))
				{
					split[i] = n;
				}

				else
				{
					continue;
				}

			}

			else if(split[i].startsWith("\n"))
			{
				String n = split[i].substring(2, split[i].length());

				if(!n.equals(""))
				{
					split[i] = n;
				}

				else
				{
					continue;
				}
			}

			Palabra p = new Palabra(split[i]);
			if(!palabrasQueContiene.add(p))
			{
				Palabra pReal = palabrasQueContiene.get(p);
				pReal.aumentarFrecuencia();
			}
			
			System.out.println("En Documento.leerContiene(), palabra: " + split[i]);
		}

	}

}
