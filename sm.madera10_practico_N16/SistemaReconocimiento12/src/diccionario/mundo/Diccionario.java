package diccionario.mundo;

import java.io.Serializable;

import estructurasDatos.ASList;
import estructurasDatos.ASTrie;

public class Diccionario implements Comparable<Diccionario>, Serializable {
	
	//------------------------------------------------------------------------------------------------------------
	//Atributos
	//------------------------------------------------------------------------------------------------------------

	/**
	 * Las palabras guardadas en el diccionario
	 */
	private ASTrie<Palabra> palabras;
	
	/**
	 * Nombre del diccionario.
	 */
	private String nombre;
	
	/**
	 * Lista de palabras
	 */
	private ASList<String> listaPalabras;
	
	/**
	 * Cantidad de letras de la palabra más larga del diccionario
	 */
	
	private int máximoLetras;
	
	//------------------------------------------------------------------------------------------------------------
	//Constructores
	//------------------------------------------------------------------------------------------------------------
	
	public Diccionario(String nombre)
	{
		this.nombre = nombre;
		palabras = new ASTrie<Palabra>();
		listaPalabras = new ASList<String>();
	}
	
	//------------------------------------------------------------------------------------------------------------
	//Métodos
	//------------------------------------------------------------------------------------------------------------
	
	public int darNúmeroPalabras()
	{
		return palabras.darNumeroPalabras();
	}
	
	public boolean añadirPalabra(String palabra, String definición, String tipo) throws Exception
	{
		Palabra p = new Palabra(palabra, definición, tipo);
		return palabras.agregarCool(palabra, p);
	}
	
	public String eliminarPalabra(String palabra)
	{
		return palabras.eliminar(palabra).darPalabra();
	}
	
	/**
	 * Compara este diccionario con otro. 
	 * @return 1 si este diccionario tiene más elementos, -1 de lo contrario.
	 * 			Si tienen el mismo número de elementos (palabras), las compara.
	 * 			Si hay dos palabras diferentes, retorna el comapreToIgnoreCase entre las primeras
	 * 			 (diferentes) que encuentre.
	 */
	
	public int compareTo(Diccionario otroDiccionario)
	{
		return nombre.compareToIgnoreCase(otroDiccionario.nombre);
//		int númeroPalabras = darNúmeroPalabras();
//		int otroNúmeroPalabras =  otroDiccionario.darNúmeroPalabras();
//		
//		if (númeroPalabras > otroNúmeroPalabras)
//			return 1;
//		
//		else if (númeroPalabras < otroNúmeroPalabras)
//			return -1;
//		
//		Iterator<Palabra> it = palabras.iterator();
//		Iterator<Palabra> otroIter = otroDiccionario.palabras.iterator();
//		
//		ASList<Palabra> actualLista = new ASList<Palabra>(númeroPalabras);
//		ASList<Palabra> otroLista = new ASList<Palabra>(otroNúmeroPalabras);
//		
//		while (it.hasNext() && otroIter.hasNext())
//		{
//			Palabra actual = it.next();
//			Palabra actualDelOtro = otroIter.next();
//			
//			actualLista.addSorted(actual);
//			otroLista.addSorted(actualDelOtro);
//		}
//		
//		for (int i = 0; i < númeroPalabras; i++)
//		{
//			int comp = actualLista.dar(i).compareTo(otroLista.dar(i));
//			
//			if (comp != 0)
//				return comp;
//		}
//		
//		return 0;
	}

	/**
	 * @return the palabras
	 */
	public ASTrie darPalabras() {
		return palabras;
	}
	
	/**
	 * @return el nombre
	 */
	public String darNombre()
	{
		return nombre;
	}
	
	
	public boolean añadirPalabra(String palabra) throws Exception
	{
		if (listaPalabras.isEmpty())
		{
			máximoLetras = palabra.length();
		}
		
		else if(palabra.length() > máximoLetras)
		{
			máximoLetras = palabra.length();
		}
		
		listaPalabras.add(palabra);
		int tamAnt = palabras.darNumeroPalabras();
		palabras.agregar(palabra, new Palabra(palabra));
		int tamDesp = palabras.darNumeroPalabras();
		
		return tamDesp > tamAnt;
		
	}
	
	public ASList<String> darListaPalabras()
	{
		return listaPalabras;
	}
	
	public int darMáximoLetras()
	{
		return máximoLetras;
	}
	
	public boolean añadirPalabraConMayus(String palabra) throws Exception
	{
		String mayusPalabra = AdministradorANS.ponerMayus(palabra);
		listaPalabras.add(mayusPalabra);
		int tamAnt = palabras.darNumeroPalabras();
		palabras.agregar(mayusPalabra, new Palabra(mayusPalabra));
		int tamDesp = palabras.darNumeroPalabras();
		
		return tamDesp > tamAnt;
	}

}
