package diccionario.mundo;

import java.io.BufferedReader;
import java.util.Iterator;

import estructurasDatos.ASAVLTree;
import estructurasDatos.ASList;

public class AdministradorANS implements IAdministradorANS {

	//------------------------------------------------------------------------------------------------------------
	//Constantes
	//------------------------------------------------------------------------------------------------------------

	/**
	 * El prefijo del nombre por defecto de los documentos. En su valor original, "Documento",
	 * los documentos cuyo nombre no sea especificado se comportarán de la siguiente manera:
	 * el primer documento tendrá nombre Documento1,
	 * el segundo Documento2,
	 * el tercero Documento3,
	 * y así.
	 */
	private final static String NOMBRE_POR_DEFECTO_DOC = "Documento";

	/**
	 * Tipo de búsqueda por palabras.
	 */
	public final static String BÚSQUEDA_PALABRA = "Palabra";

	/**
	 * Tipo de búsqueda por prefijos.
	 */
	public final static String BÚSQUEDA_PREFIJO = "Prefijo";

	/**
	 * Criterio de búsqueda en el que se solicita que el documento contenga todo el conjunto 
	 * de palabras.
	 */ 
	public final static String CRITERIO_CON_TODAS_PALABRAS = "Todas";

	/**
	 * Criterio de búsqueda en el que se solicita que el documento contenga al menos una de las
	 * palabras en el conjunto. 
	 */
	public final static String CRITERIO_CON_UNA_DE_LAS_PALABRAS = "Una";

	//------------------------------------------------------------------------------------------------------------
	//Atributos
	//------------------------------------------------------------------------------------------------------------

	private ASAVLTree<Diccionario> diccionarios;
	private ASAVLTree<Documento> documentos;
	private ASAVLTree<Carpeta> carpetas;
	private Diccionario diccionarioActual;
	private Documento documentoActual;
	private static AdministradorANS instancia;
	private ASAVLTree<Palabra> índicePalabras;

	//------------------------------------------------------------------------------------------------------------
	//Constructores
	//------------------------------------------------------------------------------------------------------------

	private AdministradorANS()
	{
		diccionarios = new ASAVLTree<Diccionario>();
		documentos = new ASAVLTree<Documento>();
		diccionarioActual = null;
		documentoActual = null;
		índicePalabras = new ASAVLTree<Palabra>();
		carpetas = new ASAVLTree<Carpeta>();
	}

	//------------------------------------------------------------------------------------------------------------
	//Métodos
	//------------------------------------------------------------------------------------------------------------

	public static AdministradorANS getInstance()
	{
		if (instancia == null)
		{
			instancia = new AdministradorANS();
		}

		return instancia;
	}

	public int darCantidadDocumentos()
	{
		return documentos.darPeso();
	}

	public boolean añadirCarpeta(Carpeta carpeta)
	{
		try {
			return carpetas.add(carpeta);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return false;
		}
	}
	
	public void cambiarDocumentoActual(Documento d)
	{
		documentoActual = d;
	}

	public int darCantidadDiccionarios()
	{
		return diccionarios.darPeso();
	}

	public void asignarDiccionarioActual(Diccionario dic)
	{
		diccionarioActual = dic;
	}
	
	public void cambiarDiccionarioActual(String nuevoNombreDiccionario) throws Exception
	{
		diccionarioActual = diccionarios.get(new Diccionario(nuevoNombreDiccionario));
		
		if (diccionarioActual == null)
			throw new Exception("No hay un diccionario almacenado con el nombre ingresado");
	}

	public int darTotalPalabras()
	{
		Iterator<Diccionario> it = diccionarios.iterator();
		int numPalabras = 0;

		while (it.hasNext())
		{
			numPalabras += it.next().darNúmeroPalabras();
		}

		return numPalabras;
	}

	public String reconocerTexto() throws Exception
	{
		if (diccionarioActual == null)
			throw new Exception("Seleccione un diccionario con el que reconocer el documento.");

		if (documentoActual == null)
			throw new Exception("Seleccione un documento que reconocer.");

		return null;
		//TODO
	}

	public int darNúmeroPalabrasActual()
	{
		return diccionarioActual.darNúmeroPalabras();
	}

	public void añadirDocumento(String nombre, String texto) throws Exception
	{
		if (nombre == "")
		{
			nombre = NOMBRE_POR_DEFECTO_DOC + (documentos.darPeso() + 1);
		}

		Documento d = new Documento(nombre, texto);
		documentos.add(d);
		documentoActual = d;
	}

	public boolean añadirDocumentoConURL(String URL, String carpetaGuardar, BufferedReader br) throws Exception
	{
		String nombreDoc = "";
		
		//Saco el nombre del doc, con su extensión.
		String[] urlSplit = URL.split("\\\\");
		nombreDoc = urlSplit[urlSplit.length - 1];

		//Saco el texto
		String texto = "";
		String línea = br.readLine();
		boolean primeraLínea = true;

		while(línea != null)
		{
			if (primeraLínea)
			{
				texto+= línea; 
				primeraLínea = false;
			}
			else
			{
				texto += "\n" + línea;
			}

			línea = br.readLine();
		}
		
		//Guardo el documento.
		Documento nDoc = new Documento(nombreDoc, texto);
		boolean rta = documentos.add(nDoc);
		
		if(rta)
		{
			Carpeta carpeta = carpetas.get( new Carpeta(carpetaGuardar) );
			
			if (carpeta == null)
			{
				throw new Exception("La carpeta solicitada no se encontró. Lo cual no es zensual.");
			}
			
			carpeta.añadirDocumento(nDoc);
			nDoc.setUrlLocal("/" + carpetaGuardar);
			documentoActual = nDoc;
		}
		
		documentoActual = documentos.get(new Documento(nombreDoc));
		
		return rta;
	}

	public boolean añadirDocumentoConURL(String URL, BufferedReader br) throws Exception
	{
		String nombreDoc = "";
		
		//Saco el nombre del doc, con su extensión.
		String[] urlSplit = URL.split("\\\\");
		nombreDoc = urlSplit[urlSplit.length - 1];

		//Saco el texto
		String texto = "";
		String línea = br.readLine();
		boolean primeraLínea = true;

		while(línea != null)
		{
			if (primeraLínea)
			{
				texto+= línea; 
				primeraLínea = false;
			}
			else
			{
				texto += "\n" + línea;
			}

			línea = br.readLine();
		}
		
		//Guardo el documento.
		Documento nDoc = new Documento(nombreDoc, texto);
		boolean rta = documentos.add(nDoc);
		
		if(rta)
		{
			documentoActual = nDoc;
		}
		
		documentoActual = documentos.get(new Documento(nombreDoc));
		
		return rta;
	}

	//	public void añadirDiccionario(String nombre) throws Exception
	//	{
	//		Diccionario d = new Diccionario(nombre);
	//		diccionarios.add(d);
	//	}

	/**
	 * @return the diccionarios
	 */
	public ASAVLTree<Diccionario> darDiccionarios() {
		return diccionarios;
	}

	/**
	 * @return the diccionarioActual
	 */
	public Diccionario darDiccionarioActual() {
		return diccionarioActual;
	}

	public ASAVLTree<Documento> buscarDocumentos(String tipoBúsqueda, ASList<String> palabrasOPrefijos, String tipoCriterio) throws Exception
	{
		if (!tipoBúsqueda.equals(BÚSQUEDA_PALABRA) && !tipoBúsqueda.equals(BÚSQUEDA_PREFIJO))
			throw new Exception("El tipo de búsqueda solicitado no es válido. Deben buscarse documentos"
					+ " por palabras o prefijos");

		if (!tipoCriterio.equals(CRITERIO_CON_TODAS_PALABRAS) && !tipoCriterio.equals(CRITERIO_CON_UNA_DE_LAS_PALABRAS))
			throw new Exception("El tipo de criterio solicitado no es válido. Deben buscarse documentos que contengan"
					+" todas las palabras requeridas o al menos una de ellas."); 

		if (palabrasOPrefijos.isEmpty())
			throw new Exception("Se requiere al menos una palabra o prefijo para realizar una búsqueda.");

		return null;
		//TODO :c
	}

	public void cambiarDiccionarioActual(Diccionario n)
	{
		diccionarioActual = n;
	}

	public boolean añadirDiccionario(Diccionario d)
	{
		try 
		{
			return diccionarios.add(d);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return false;
		}

	}

	public void serializar() throws Exception
	{
		//TODO
	}

	public void deserializar() throws Exception
	{
		//TODO
	}
	
	public ASAVLTree<Carpeta> darCarpetas()
	{
		return carpetas;
	}
	
	public ASAVLTree<Documento> darDocumentos()
	{
		return documentos;
	}
	
	public void setCarpetas(ASAVLTree<Carpeta> c )
	{
		carpetas = c;
	}

	/**
	 * @param diccionarioActual the diccionarioActual to set
	 */
	public void setDiccionarioActual(Diccionario diccionarioActual) {
		this.diccionarioActual = diccionarioActual;
	}

	/**
	 * @return the documentoActual
	 */
	public Documento darDocumentoActual() {
		return documentoActual;
	}

	/**
	 * @param documentoActual un objeto de tipo documento con el nombre del documento que se busca.
	 * @throws Exception si no se encuentra el documento actual en el árbol.
	 */
	public void setDocumentoActual(Documento documentoActual) throws Exception 
	{
		this.documentoActual = documentos.get(documentoActual);
	}
	
	public static int darTamañoIterator(Iterator it)
	{
		int contador = 0;
		
		while(it.hasNext())
		{
			contador++;
			it.next();
		}
		
		return contador;
	}

	/**
	 * @return el índicePalabras
	 */
	public ASAVLTree<Palabra> darÍndicePalabras() 
	{
		return índicePalabras;
	}
	
	public boolean agregarAÍndice(Palabra palabra)
	{
		try 
		{
			return índicePalabras.add(palabra);
		} 
		
		catch (Exception e) 
		{
			e.printStackTrace();
			return false;
		}
	}
	
	public static String ponerMayus(String palabra)
	{
		 return Character.toUpperCase(palabra.charAt(0)) + palabra.substring(1);
	}
	
	

}
