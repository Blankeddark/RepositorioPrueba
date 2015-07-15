package estructurasDatosTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;



import co.edu.uniandes.estructuras.trie.ITrie;
import estructurasDatos.ASTrie;
import junit.framework.TestCase;

public class TrieTest extends TestCase
{
	private ITrie<String> trie; 
	private String nombreClase;
	private String rutaInfoArchivo;
	
	/**
	 * Escenario para evaluar m�todos de agregaci�n en el Trie
	 */
	private void setupEscenario1()
	{
		trie = (ITrie<String>) new ASTrie<String>();
		
	
	}
		
	
	/**
	 * Escenario para evaluar m�todos de b�squeda, conteo y eliminaci�n en el Trie
	 */
	private void setupEscenario2()
	{
		
			
			trie = (ITrie<String>) new ASTrie<String>();
			
			String e1 = "e1";
			String e2 = "e2";
			String e3 = "e3";
			String e4 = null;
			String e5 = null;
			String e6 = "e6";
			String e7 = "e7";
			String e8 = "e8";
			String e9 = "e9";
			String e10 = "e10";
			
			String p1 = "CASADO";
			String p2 = "CASA";
			String p3 = "COSA";
			String p4 = "ROSA";
			String p5 = "ROSARIO";
			String p6 = "CAOTICO";
			String p7 = "ARBOL";
			String p8 = "BARCO";
			String p9 = "BARRA";
			String p10 = "BARRISTA";
			
			trie.agregar(p1, e1);
			trie.agregar(p2, e2);
			trie.agregar(p3, e3);
			trie.agregar(p4, e4);
			trie.agregar(p5, e5);
			trie.agregar(p6, e6);
			trie.agregar(p7, e7);
			trie.agregar(p8, e8);
			trie.agregar(p9, e9);
			trie.agregar(p10, e10);
			
			
	}
	
	/**
	 * M�todo que eval�a el m�todo agregar, agregando una palabra nueva que no es prefijo de otras
	 */
	public void testAgregarPalabraNuevaQueNoEsPrefijo()
	{
		setupEscenario1();
		
		String nuevaPalabra = "NUEVA";
		String nuevoElemento = "E1";
		trie.agregar(nuevaPalabra, nuevoElemento);
		
		String elemBuscado = trie.buscar(nuevaPalabra);
		assertEquals("El elemento y la palabra no fueron agregados correctamente", nuevoElemento, elemBuscado);
		
		
		nuevaPalabra = "ORUGA";
		nuevoElemento = "E2";
		trie.agregar(nuevaPalabra, nuevoElemento);
		
		elemBuscado = trie.buscar(nuevaPalabra);
		assertEquals("El elemento y la palabra no fueron agregados correctamente", nuevoElemento, elemBuscado);
		
		nuevaPalabra = "BARCO";
		nuevoElemento = "E3";
		trie.agregar(nuevaPalabra, nuevoElemento);
		
		elemBuscado = trie.buscar(nuevaPalabra);
		assertEquals("El elemento y la palabra no fueron agregados correctamente", nuevoElemento, elemBuscado);
		
		int numPalabras = trie.darNumeroPalabras();
		assertEquals("El numero de palabras del Trie es incorrecto", 3, numPalabras);
		
	}
	
	/**
	 * M�todo que eval�a el m�todo agregar, agregando una palabra nueva que es prefijo de otra
	 */
	public void testAgregarPalabraNuevaQueEsPrefijo()
	{
		setupEscenario1();
		
		String nuevaPalabra1 = "CASADO";
		String nuevaPalabra2 = "CASA";
		String nuevoElemento1 = "E1";
		String nuevoElemento2 = "E2";
		
		//Agrego primero CASADO
		trie.agregar(nuevaPalabra1, nuevoElemento1);
		
		//Se agrega despu�s CASA, que es prefijo de CASADO
		trie.agregar(nuevaPalabra2, nuevoElemento2);
		
		String elemBuscado1 = trie.buscar(nuevaPalabra1);
		assertEquals("El elemento y la palabra no fueron agregados correctamente", nuevoElemento1, elemBuscado1);
		
		String elemBuscado2 = trie.buscar(nuevaPalabra2);
		assertEquals("El elemento y la palabra no fueron agregados correctamente", nuevoElemento2, elemBuscado2);
		
		//Se buscan las 2 palabras definidas con el prefijo de la palabra 2 (CASA)
		Iterator<String> palabras = trie.buscarPalabrasConPrefijo(nuevaPalabra2);
		int tamanioIterador = 0;
		while(palabras.hasNext())
		{
			palabras.next();
			tamanioIterador++;
		}
		assertEquals("El numero de palabras del con el Prefijo " + nuevaPalabra2 + " es incorrecto", 2, tamanioIterador);
		
		//Reviso la primera palabra del Iterador, la cual debe ser CASA
		palabras = trie.buscarPalabrasConPrefijo(nuevaPalabra2);
		String primeraPalabra = palabras.next();
		assertEquals("La primera palabra del iterador no es la correcta", nuevaPalabra2, primeraPalabra);
		
		nuevaPalabra1 = "ARROZAL";
		nuevaPalabra2 = "ARROZ";
		nuevoElemento1 = "E3";
		nuevoElemento2 = "E4";
		
		//Agrego ahora ARROZAL
		trie.agregar(nuevaPalabra1, nuevoElemento1);
		
		//Luego agrego ARROZ
		trie.agregar(nuevaPalabra2, nuevoElemento2);
		
		elemBuscado1 = trie.buscar(nuevaPalabra1);
		assertEquals("El elemento y la palabra no fueron agregados correctamente", nuevoElemento1, elemBuscado1);
		
		elemBuscado2 = trie.buscar(nuevaPalabra2);
		assertEquals("El elemento y la palabra no fueron agregados correctamente", nuevoElemento2, elemBuscado2);
		
		//Se buscan las 2 palabras definidas con el prefijo de la palabra 2 (CASA)
		palabras = trie.buscarPalabrasConPrefijo(nuevaPalabra2);
		tamanioIterador = 0;
		while(palabras.hasNext())
		{
			palabras.next();
			tamanioIterador++;
		}
		assertEquals("El numero de palabras del con el Prefijo " + nuevaPalabra2 + " es incorrecto", 2, tamanioIterador);
		
		//Reviso la primera palabra del Iterador, la cual debe ser CASA
		palabras = trie.buscarPalabrasConPrefijo(nuevaPalabra2);
		primeraPalabra = palabras.next();
		assertEquals("La primera palabra del iterador no es la correcta", nuevaPalabra2, primeraPalabra);
		
		int tamanioTrie = trie.darNumeroPalabras();
		assertEquals("No se agreg� la totalidad de palabras", 4, tamanioTrie);
		
	}
	
	/**
	 * M�todo que agrega una palabra existente al Trie, modificando el elemento que �sta contiene
	 */
	public void testAgregarPalabraExistente()
	{
		setupEscenario1();
		
		String nuevaPalabra = "CASA";
		String nuevoElemento1 = "E1";
		String nuevoElemento2 = "E2";
		
		trie.agregar(nuevaPalabra, nuevoElemento1);
		trie.agregar(nuevaPalabra, nuevoElemento2);
		
		String elemBuscado = trie.buscar(nuevaPalabra);
		assertEquals("No se actualiz� correctamente el elemento de la palabra " + nuevaPalabra, nuevoElemento2, elemBuscado);
		
		nuevaPalabra = "BARRA";
		nuevoElemento1 = "E1";
		nuevoElemento2 = "E2";
		
		trie.agregar(nuevaPalabra, nuevoElemento1);
		trie.agregar(nuevaPalabra, nuevoElemento2);
		
		elemBuscado = trie.buscar(nuevaPalabra);
		assertEquals("No se actualiz� correctamente el elemento de la palabra " + nuevaPalabra, nuevoElemento2, elemBuscado);
		
		nuevaPalabra = "BARRISTA";
		nuevoElemento1 = "E1";
		nuevoElemento2 = "E2";
		
		trie.agregar(nuevaPalabra, nuevoElemento1);
		trie.agregar(nuevaPalabra, nuevoElemento2);
		
		elemBuscado = trie.buscar(nuevaPalabra);
		assertEquals("No se actualiz� correctamente el elemento de la palabra " + nuevaPalabra, nuevoElemento2, elemBuscado);
		
		nuevaPalabra = "CASADO";
		nuevoElemento1 = "E1";
		nuevoElemento2 = "E2";
		
		trie.agregar(nuevaPalabra, nuevoElemento1);
		trie.agregar(nuevaPalabra, nuevoElemento2);
		
		elemBuscado = trie.buscar(nuevaPalabra);
		assertEquals("No se actualiz� correctamente el elemento de la palabra " + nuevaPalabra, nuevoElemento2, elemBuscado);
		
		
		int numPalabras = trie.darNumeroPalabras();
		assertEquals("El numero de palabras del Trie es incorrecto", 4, numPalabras);
		
	}
	
	/**
	 * M�todo que Busca una palabra en un Trie vac�o
	 */
	public void testBuscarEnTrieVacio()
	{	
		setupEscenario1();
		String elemento = trie.buscar("CASA");
		
		assertNull("Deber�a haberse retornado null", elemento);
	}
	
	/**
	 * M�todo que Busca una palabra que no es Prefijo de ninguna otra
	 */
	public void testBuscarPalabraQueNoEsPrefijo()
	{
		setupEscenario2();
		String elemento = trie.buscar("CASADO");
		assertEquals("El elemento retornado no es el esperado", "e1",elemento);
		
		elemento = trie.buscar("BARRISTA");
		assertEquals("El elemento retornado no es el esperado", "e10",elemento);
		
		elemento = trie.buscar("CAOTICO");
		assertEquals("El elemento retornado no es el esperado", "e6",elemento);
		
	}
	
	/**
	 * M�todo que Busca una palabra que es prefijo de otras
	 */
	public void testBuscarPalabraQueEsPrefijo()
	{
		setupEscenario2();
		
		String elemento = trie.buscar("CASA");
		assertEquals("El elemento retornado no es el esperado", "e2",elemento);
		
		elemento = trie.buscar("BARRA");
		assertEquals("El elemento retornado no es el esperado", "e9",elemento);
		
		elemento = trie.buscar("ROSA");
		assertNull("El elemento retornado deber�a ser nulo",elemento);

	}
	
	/**
	 * M�todo que Busca una palabra no existente en el Trie
	 */
	public void testBuscarPalabraNoExistente()
	{	
		setupEscenario2();
		String elemento = trie.buscar("RANA");
		
		assertNull("Deber�a haberse retornado null", elemento);
	}
	
	/**
	 * M�todo que busca una palabra que posee un elemento nulo
	 */
	public void testBuscarPalabraConElementoNulo()
	{	
		setupEscenario2();
		String elemento = trie.buscar("ROSARIO");
		
		assertNull("Deber�a haberse retornado null", elemento);
		
		elemento = trie.buscar("ROSA");
		
		assertNull("Deber�a haberse retornado null", elemento);
	}
	
	/**
	 * M�todo que busca con Prefijo en un Trie vac�o
	 */
	public void testBuscarConPrejifoTrieVacio()
	{
		setupEscenario1();
		Iterator<String> palabras = trie.buscarPalabrasConPrefijo("CASA");
		
		assertNotNull("Se retorn� null como valor, cuando deb�a retornarse un iterador vac�o", palabras);
		
		int numElementos = 0;
		while(palabras.hasNext())
		{
			palabras.next();
			numElementos++;
		}
		
		assertEquals("Se debi� haber retornado un iterador vac�o", 0, numElementos);
	}
	
	/**
	 * M�todo que busca con Prefijo, utilizando Prefijo no existente en el Trie
	 */
	public void testBuscarConPrejifoNoExistenteEnTrie()
	{
		setupEscenario2();
		Iterator<String> palabras = trie.buscarPalabrasConPrefijo("ROCA");
		
		assertNotNull("Se retorn� null como valor, cuando deb�a retornarse un iterador vac�o", palabras);
		
		int numElementos = 0;
		while(palabras.hasNext())
		{
			palabras.next();
			numElementos++;
		}
		
		assertEquals("Se debi� haber retornado un iterador vac�o", 0, numElementos);
	}
	
	/**
	 * M�todo que busca con Prefijo, utilizando un Prefijo que posee una sola palabra 
	 */
	public void testBuscarConPrefijoDeUnaPalabra()
	{
		setupEscenario2();
		Iterator<String> palabras = trie.buscarPalabrasConPrefijo("CASAD");
		assertNotNull("Se retorn� null como valor, cuando deb�a retornarse un iterador", palabras);
		
		int numElementos = 0;
		while(palabras.hasNext())
		{
			palabras.next();
			numElementos++;
		}
		
		assertEquals("El iterador no est� constituido por una palabra", 1, numElementos);
		
		//-------Analizamos caso de palabra que no posee otras palabras como prefijo-----
		palabras = trie.buscarPalabrasConPrefijo("CAOTI");
		assertNotNull("Se retorn� null como valor, cuando deb�a retornarse un iterador", palabras);
		
		numElementos = 0;
		while(palabras.hasNext())
		{
			palabras.next();
			numElementos++;
		}
		
		assertEquals("El iterador no est� constituido por una palabra", 1, numElementos);
		
	}
	
	/**
	 * M�todo que busca con Prefijo, utilizando un Prefijo que posee muchas palabras
	 */
	public void testBuscarConPrefijoDeMuchasPalabra()
	{
		setupEscenario2();
		Iterator<String> palabras = trie.buscarPalabrasConPrefijo("CAS");
		assertNotNull("Se retorn� null como valor, cuando deb�a retornarse un iterador", palabras);
		
		int numElementos = 0;
		while(palabras.hasNext())
		{
			palabras.next();
			numElementos++;
		}
		
		assertEquals("El iterador no est� constituido por una palabra", 2, numElementos);
		
		palabras = trie.buscarPalabrasConPrefijo("CAS");
		String p1 = palabras.next();
		
		assertEquals("La primera palabra no es la esperada", "CASA", p1);
		
		//------ Segundo caso para Barra y Barrista-----
		
		palabras = trie.buscarPalabrasConPrefijo("BARR");
		assertNotNull("Se retorn� null como valor, cuando deb�a retornarse un iterador", palabras);
		
		numElementos = 0;
		while(palabras.hasNext())
		{
			palabras.next();
			numElementos++;
		}
		
		assertEquals("El iterador no est� constituido por una palabra", 2, numElementos);
		
		palabras = trie.buscarPalabrasConPrefijo("BARR");
		p1 = palabras.next();
		
		assertEquals("La primera palabra no es la esperada", "BARRA", p1);
		
	}
	
	/**
	 * M�todo que Elimina una palabra en un Trie Vac�o
	 */
	public void testEliminarPalabraTrieVacio()
	{
		setupEscenario1();
		String elemento = trie.eliminar("GATO");
		
		assertNull("Deber�a haberse retornado null", elemento);
	}
	
	/**
	 * M�todo que Elimina una palabra existente
	 */
	public void testEliminarPalabraExistente()
	{
		setupEscenario2();
		String elemento = trie.eliminar("BARRA");
		assertEquals("La palabra no se elimin� correctamente", "e9", elemento);
		
		elemento = trie.buscar("BARRA");
		assertNull("El elemento debi� haber sido eliminado", elemento);
		
		elemento = trie.buscar("BARRISTA");
		assertEquals("Se afect� erroneamente la palabra que ten�a como prefijo la palabra eliminada", "e10", elemento);
		
		trie.eliminar("BARRISTA");
		elemento = trie.buscar("BARRISTA");
		assertNull("El elemento debi� haber sido eliminado", elemento);
		
		elemento = trie.buscar("CASA");
		assertEquals("Se afect� erroneamente una palabra armada con nodos hermanos de la palabra eliminada", "e2", elemento);
	
		trie.eliminar("CASA");
		elemento = trie.buscar("CASA");
		assertNull("El elemento debi� haber sido eliminado", elemento);
	}
	
	/**
	 * M�todo que Elimina una palabra no existente en el Trie
	 */
	public void testEliminarPalabraNoExistente()
	{
		setupEscenario2();
		String elemento = trie.eliminar("GATO");
		
		assertNull("Deber�a haberse retornado null", elemento);
	}
	
	/**
	 * M�todo que Elimina una cadena que no es una palabra
	 */
	public void testEliminarCadenaQueNoEsPalabra()
	{
		setupEscenario2();
		String elemento = trie.eliminar("CA");
		
		assertNull("Deber�a haberse retornado null", elemento);
		
		elemento = trie.buscar("CASADO");
		assertEquals("Se afect� erroneamente la palabra que ten�a como prefijo la cadena eliminada", "e1", elemento);
	}
	
	/**
	 * M�todo que Elimina con Prefijo en un Trie vac�o
	 */
	public void testEliminarElementosConPrefijoTrieVacio()
	{
		setupEscenario1();
		Iterator<String> palabras = trie.eliminarPalabrasConPrefijo("CASA");
		
		assertNotNull("Se retorn� null como valor, cuando deb�a retornarse un iterador vac�o", palabras);
		
		int numElementos = 0;
		while(palabras.hasNext())
		{
			palabras.next();
			numElementos++;
		}
		
		assertEquals("Se debi� haber retornado un iterador vac�o", 0, numElementos);
	}
	
	/**
	 * M�todo que Elimina con Prefijo, utilizando un Prefijo no existente
	 */
	public void testEliminarElementosConPrefijoNoExistente()
	{
		setupEscenario2();
		Iterator<String> palabras = trie.eliminarPalabrasConPrefijo("BOGOTA");
		
		assertNotNull("Se retorn� null como valor, cuando deb�a retornarse un iterador vac�o", palabras);
		
		int numElementos = 0;
		while(palabras.hasNext())
		{
			palabras.next();
			numElementos++;
		}
		
		assertEquals("Se debi� haber retornado un iterador vac�o", 0, numElementos);
	}
	
	/**
	 * M�todo que Elimina con Prefijo, utilizando un Prefijo que clasifica una sola palabra
	 */
	public void testEliminarElementosConPrefijoUnicaPalabra()
	{
		setupEscenario2();
		Iterator<String> palabras = trie.eliminarPalabrasConPrefijo("CASAD");
		
		assertNotNull("Se retorn� null como valor, cuando deb�a retornarse un iterador", palabras);
		
		int numElementos = 0;
		while(palabras.hasNext())
		{
			palabras.next();
			numElementos++;
		}
		
		assertEquals("Se debi� haber retornado un iterador con una palabra", 1, numElementos);
		String elemento = trie.buscar("CASA");
		assertNotNull("La palabra CASA deber�a tener un elemento asociado", elemento);
		assertEquals("El elemento de esta palabra se modific� equivocadamente", "e2", elemento);
		
		
		//------Segundo caso para palabra que no tiene otras como prefijo-----------
		palabras = trie.eliminarPalabrasConPrefijo("CAOTI");
		
		assertNotNull("Se retorn� null como valor, cuando deb�a retornarse un iterador", palabras);
		
		numElementos = 0;
		while(palabras.hasNext())
		{
			palabras.next();
			numElementos++;
		}
		
		assertEquals("Se debi� haber retornado un iterador con una palabra", 1, numElementos);
		elemento = trie.buscar("CAOTICO");
		assertNull("La palabra CAOTICO no deber�a existir", elemento);
	}
	
	/**
	 * M�todo que Elimina con Prefijo, utilizando un Prefijo que clasifica varias palabras
	 */
	public void testEliminarElementosConPrefijoMultiplesPalabras()
	{
		setupEscenario2();
		Iterator<String> palabras = trie.eliminarPalabrasConPrefijo("CAS");
		
		assertNotNull("Se retorn� null como valor, cuando deb�a retornarse un iterador", palabras);
		
		int numElementos = 0;
//		while(palabras.hasNext())
//		{
//			palabras.next();
//			numElementos++;
//		}
		
		//assertEquals("Se debi� haber retornado un iterador con una palabra", 2, numElementos);
		
		//palabras = trie.eliminarPalabrasConPrefijo("CAS");
		
		String palabra = palabras.next();
		
		assertEquals("La primera palabra del iterador no es la esperada", "CASA", palabra);	
		String elemento = trie.buscar("CASADO");
		assertNull("La palabra CASADO no deber�a existir", elemento);
		
	}
	
	/**
	 * M�todo que da la Longitud de un Trie vac�o
	 */
	public void testDarLongitudTrieVacio()
	{
		setupEscenario1();
		int longitud = trie.darNumeroPalabras();
		assertEquals("Se esperaba un n�mero de elementos igual a 0", 0, longitud);
	}
	
	/**
	 * M�todo que da la Longitud de un Trie cuyas palabras, todas son hojas
	 */
	public void testDarLongitudTriePalabrasHojas()
	{
		setupEscenario1();
		
		trie.agregar("CASA", "e1");
		trie.agregar("ROCA", "e2");
		trie.agregar("ARBOL", null);
		
		int longitud = trie.darNumeroPalabras();
		assertEquals("Se esperaba un n�mero de elementos igual a 3", 3, longitud);
	}

	/**
	 * M�todo que da la Longitud de un Trie cuyas palabras son hojas o nodos intermedios
	 */
	public void testDarLongitudTriePalabrasHojasNodosIntermedios()
	{
		setupEscenario2();
		int longitud = trie.darNumeroPalabras();
		assertEquals("Se esperaba un n�mero de elementos igual a 10", 10, longitud);
	}
}
