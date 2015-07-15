package estructurasDatos;

import java.io.Serializable;
import java.util.Iterator;

public class ASTrie<T> implements Serializable
{

	/**
	 * Atributo que modela la raiz del trie
	 */
	private ASTrieNode<T> raiz;


	/**
	 * Atributo que modela el peso del trie
	 */
	private int peso;

	/**
	 * Atributo que modela la cantidad total de palabras 
	 */
	private int numeroPalabras;


	private int buscados = 0;

	private int eliminados = 0;

	// -----------------------------------------------------------------
	// Constructores
	// -----------------------------------------------------------------

	/**
	 * Método constructor que construye un árbol trie vacío.
	 */
	public ASTrie( )
	{
		raiz = new ASTrieNode<T>(' ');
	}


	/**
	 * Construye un tree con la raíz especificada
	 * @param r La raíz del trie
	 * @param p El peso del trie (número de nodos que tiene el trie)
	 */
	public ASTrie( ASTrieNode<T> r, int p )
	{
		raiz = r;
		peso = p;
	}


	public void agregar(String palabra, T elemento) 
	{
		if(palabra != null && palabra.length() != 0 )
		{
			raiz.agregar( palabra, elemento );
		}

	}

	public boolean agregarCool(String palabra, T elemento) 
	{
		int tamAnt = peso;
		
		if(palabra != null && palabra.length() != 0 )
		{
			raiz.agregar( palabra, elemento );
		}
		
		int tamDesp = peso;
		
		return tamDesp > tamAnt;
	}


	public T buscar(String palabra) 
	{   
		return raiz.buscar(palabra);

	}


	public Iterator<String> buscarPalabrasConPrefijo(String palabraPrefijo) 
	{   
		ASIteradorSimple<String> iteradorBusqueda = new ASIteradorSimple<String>();

		iteradorBusqueda = raiz.buscarElementosPrefijos(palabraPrefijo, iteradorBusqueda);

		darElementosIteradorBuscar(iteradorBusqueda);

		return iteradorBusqueda;
	}

	public int darNumeroPalabras() 
	{
		int contador = 0;

		ASTrieNode<T> nodoActual = raiz;

		if(nodoActual.darData() != null)
		{
			contador++;
		}

		if(nodoActual.darHermano() != null)
		{
			contador+= nodoActual.darHermano().darNumeroPalabras();
		}

		if(nodoActual.darHijo() != null)
		{
			contador+= nodoActual.darHijo().darNumeroPalabras();
		}

		return contador;
	}


	public  ASTrieNode<T> darRaiz() 
	{
		return raiz;
	}

	public T eliminar(String palabra) 
	{   
		T retornar = null;

		if( palabra.length( ) == 0 )
		{
			return null;
		}

		else if(raiz.darHijo() == null)
		{
			return null;
		}

		else
		{
			retornar = raiz.eliminar(palabra);

			if(retornar != null)
			{
				numeroPalabras--;
			}
		}

		return retornar;
	}


	public Iterator<String> eliminarPalabrasConPrefijo(String palabraPrefijo) 
	{
		ASIteradorSimple<String> iteradorEliminacion = new ASIteradorSimple<String>();


		iteradorEliminacion =  raiz.eliminarElementosConPrefijo(palabraPrefijo, iteradorEliminacion);

		darElementosIteradorEliminar(iteradorEliminacion);

		return iteradorEliminacion;
	}

	public void  darElementosIteradorBuscar(ASIteradorSimple<String> iteradorBusqueda)
	{
		buscados = iteradorBusqueda.size();
	}

	public void darElementosIteradorEliminar( ASIteradorSimple<String> iteradorEliminacion)
	{
		eliminados =  iteradorEliminacion.size();
	}

	/**
	 * Retorna la cantidad de elementos eliminados por el iterador
	 * @return la cantidad de elementos eliminados del iterador
	 */
	public int darEliminados()
	{
		return eliminados;
	}

	/**
	 * Retorna los elementos buscados por el iterador
	 * @return la cantidad de elementos dentro del iterador de buscados
	 */
	public int darBuscados()
	{
		return buscados;
	}

}
