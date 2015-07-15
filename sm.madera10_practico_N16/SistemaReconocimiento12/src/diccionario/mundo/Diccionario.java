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
	 * Cantidad de letras de la palabra m�s larga del diccionario
	 */
	
	private int m�ximoLetras;
	
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
	//M�todos
	//------------------------------------------------------------------------------------------------------------
	
	public int darN�meroPalabras()
	{
		return palabras.darNumeroPalabras();
	}
	
	public boolean a�adirPalabra(String palabra, String definici�n, String tipo) throws Exception
	{
		Palabra p = new Palabra(palabra, definici�n, tipo);
		return palabras.agregarCool(palabra, p);
	}
	
	public String eliminarPalabra(String palabra)
	{
		return palabras.eliminar(palabra).darPalabra();
	}
	
	/**
	 * Compara este diccionario con otro. 
	 * @return 1 si este diccionario tiene m�s elementos, -1 de lo contrario.
	 * 			Si tienen el mismo n�mero de elementos (palabras), las compara.
	 * 			Si hay dos palabras diferentes, retorna el comapreToIgnoreCase entre las primeras
	 * 			 (diferentes) que encuentre.
	 */
	
	public int compareTo(Diccionario otroDiccionario)
	{
		return nombre.compareToIgnoreCase(otroDiccionario.nombre);
//		int n�meroPalabras = darN�meroPalabras();
//		int otroN�meroPalabras =  otroDiccionario.darN�meroPalabras();
//		
//		if (n�meroPalabras > otroN�meroPalabras)
//			return 1;
//		
//		else if (n�meroPalabras < otroN�meroPalabras)
//			return -1;
//		
//		Iterator<Palabra> it = palabras.iterator();
//		Iterator<Palabra> otroIter = otroDiccionario.palabras.iterator();
//		
//		ASList<Palabra> actualLista = new ASList<Palabra>(n�meroPalabras);
//		ASList<Palabra> otroLista = new ASList<Palabra>(otroN�meroPalabras);
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
//		for (int i = 0; i < n�meroPalabras; i++)
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
	
	
	public boolean a�adirPalabra(String palabra) throws Exception
	{
		if (listaPalabras.isEmpty())
		{
			m�ximoLetras = palabra.length();
		}
		
		else if(palabra.length() > m�ximoLetras)
		{
			m�ximoLetras = palabra.length();
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
	
	public int darM�ximoLetras()
	{
		return m�ximoLetras;
	}
	
	public boolean a�adirPalabraConMayus(String palabra) throws Exception
	{
		String mayusPalabra = AdministradorANS.ponerMayus(palabra);
		listaPalabras.add(mayusPalabra);
		int tamAnt = palabras.darNumeroPalabras();
		palabras.agregar(mayusPalabra, new Palabra(mayusPalabra));
		int tamDesp = palabras.darNumeroPalabras();
		
		return tamDesp > tamAnt;
	}

}
