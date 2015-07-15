package estructurasDatos;

import java.io.Serializable;


/**
 * Clase que representa a un nodo de la cola ordenada
 */
public class ASQueuedNode<T> implements Serializable 
{

	// -----------------------------------------------------------------
	// Constantes
	// -----------------------------------------------------------------

	private static final long serialVersionUID = 1L;

	// -----------------------------------------------------------------
	// Atributos
	// -----------------------------------------------------------------

	/**
	 * información del nodo
	 */
	private T data;

	/**
	 * El nodo siguiente al actual
	 */
	private ASQueuedNode<T> next;


	// -----------------------------------------------------------------
	// Constructores
	// -----------------------------------------------------------------

	/**
	 * Constructor por parámetros del nodo. <br>
	 * @param d Información que va a ser almacenada en el nodo. 
	 */
	public ASQueuedNode( T d )
	{
		data = d;
		next = null;
	}


	// -----------------------------------------------------------------
	// Métodos
	// -----------------------------------------------------------------

	/**
	 * Retorna la información contenida por el nodo. <br>
	 * @return La información dentro del nodo. 
	 */
	public T darElemento( )
	{
		return data;
	}
	

	/**
	 * Desconecta el nodo de la cola suponiendo que es el primero. 
	 * @return Nodo con el cual comienza la cola ahora.<br>
	 */
	public ASQueuedNode<T> desconectarPrimero( )
	{
		ASQueuedNode<T> p = next;
		next = null;
		return p;
	}



	/**
	 * Inserta el nodo especificado después del nodo actual. <br>
	 * @param nodo El nodo a ser insertado<br>
	 * @return Nodo que se insertó después del nodo actual<br>
	 */
	public ASQueuedNode<T> insertar( ASQueuedNode<T> nodo )
	{
		next = nodo;
		return nodo;
	}

	/**
	 * Retorna el siguiente nodo. <br>
	 * <b>post: </b> Se retornó el siguiente nodo.<br>
	 * @return El nodo siguiente<br>
	 */
	public ASQueuedNode<T> darSiguiente( )
	{
		return next;
	}

}
