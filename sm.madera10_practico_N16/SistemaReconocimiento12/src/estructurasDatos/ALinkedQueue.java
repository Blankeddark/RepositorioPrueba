package estructurasDatos;

import java.io.Serializable;

public class ALinkedQueue <T> implements Serializable
{
	// -----------------------------------------------------------------
	// Constantes
	// -----------------------------------------------------------------

	private static final long serialVersionUID = 1L;

	// -----------------------------------------------------------------
	// Atributos
	// -----------------------------------------------------------------

	/**
	 * Primer elemento de la cola encadenada
	 */
	protected ASQueuedNode<T> primero;

	/**
	 * Ultimo elemento de la cola encadenada
	 */
	protected ASQueuedNode<T> ultimo;

	/**
	 * Número de elementos de la cola
	 */
	protected int tamaño;

	// -----------------------------------------------------------------
	// Constructores
	// -----------------------------------------------------------------

	/**
	 * Constructor de la cola encadenada vacía. 
	 */
	public ALinkedQueue( )
	{
		primero = null;
		ultimo = null;
		tamaño  = 0;
	}

	// -----------------------------------------------------------------
	// Métodos
	// -----------------------------------------------------------------
	/**
	 * Método que retorna el tamaño de la cola.
	 * @return El número de elementos de la cola.

	 */
	public int size( )
	{
		return tamaño;
	}

	/**
	 * Retorna el primer elemento de la cola y lo elimina. 
	 * @return El primer elemento de la cola. Diferente de null
	 * @throws Exception si la cola está vacía.

	 */
	public T atender( ) throws Exception
	{
		if( isEmpty() )
		{
			throw new Exception( "No hay elementos en la cola" );
		}

		else
		{
			ASQueuedNode<T> p = primero;
			primero = primero.desconectarPrimero( );

			if( primero == null )
			{
				ultimo = null;	
			}

			tamaño--;
			return p.darElemento( );
		}
	}

	/**
	 * Inserta un elemento al final de la cola. 
	 * @param elemento El elemento a ser insertado..
	 */
	public void add( T d ) throws Exception
	{   
		if(d == null)
		{
			throw new Exception("No se puede agregar un elemento nulo");
		}
		
		else
		{
			ASQueuedNode <T> nodo = new ASQueuedNode<T>( d );

			if( primero == null )
			{
				primero = nodo;
				ultimo = nodo;
			}
			else
			{
				ultimo = ultimo.insertar( nodo );
			}

			tamaño++;
		}
		
	}

	/**
	 * Método que retorna el primer nodo de la cola. 
	 * @return El primer nodo de la cola
	 */
	public ASQueuedNode<T> darPrimero( )
	{
		return primero;
	}

	/**
	 * Método que retorna el último nodo de la cola. 
	 * @return El último nodo de la cola
	 */
	public ASQueuedNode<T> darUltimo( )
	{
		return ultimo;
	}

	/**
	 * Indica si la cola se encuentra vacía. 
	 * @return True si el primer elemento es nulo o false en caso contrario
	 */
	public boolean isEmpty( )
	{
		if(primero == null)
		{
			return true;
		}

		return false;
	}

}
