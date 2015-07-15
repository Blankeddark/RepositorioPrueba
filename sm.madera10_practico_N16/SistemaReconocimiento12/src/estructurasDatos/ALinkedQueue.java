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
	 * N�mero de elementos de la cola
	 */
	protected int tama�o;

	// -----------------------------------------------------------------
	// Constructores
	// -----------------------------------------------------------------

	/**
	 * Constructor de la cola encadenada vac�a. 
	 */
	public ALinkedQueue( )
	{
		primero = null;
		ultimo = null;
		tama�o  = 0;
	}

	// -----------------------------------------------------------------
	// M�todos
	// -----------------------------------------------------------------
	/**
	 * M�todo que retorna el tama�o de la cola.
	 * @return El n�mero de elementos de la cola.

	 */
	public int size( )
	{
		return tama�o;
	}

	/**
	 * Retorna el primer elemento de la cola y lo elimina. 
	 * @return El primer elemento de la cola. Diferente de null
	 * @throws Exception si la cola est� vac�a.

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

			tama�o--;
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

			tama�o++;
		}
		
	}

	/**
	 * M�todo que retorna el primer nodo de la cola. 
	 * @return El primer nodo de la cola
	 */
	public ASQueuedNode<T> darPrimero( )
	{
		return primero;
	}

	/**
	 * M�todo que retorna el �ltimo nodo de la cola. 
	 * @return El �ltimo nodo de la cola
	 */
	public ASQueuedNode<T> darUltimo( )
	{
		return ultimo;
	}

	/**
	 * Indica si la cola se encuentra vac�a. 
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
