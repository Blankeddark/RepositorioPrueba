package estructurasDatos;

import java.io.Serializable;

public class ASNonSortedList <T> implements Serializable
{
	// -----------------------------------------------------------------
	// Constantes
	// -----------------------------------------------------------------

	/**
	 * Constante para la serialización
	 */
	private static final long serialVersionUID = 1L;

	// -----------------------------------------------------------------
	// Atributos
	// -----------------------------------------------------------------

	/**
	 * Cabeza de la lista encadenada
	 */
	private ASNodo<T> primero;

	/**
	 * Último elemento de la lista encadenada
	 */
	private ASNodo<T> ultimo;

	/**
	 * Número de elementos de la lista
	 */
	private int tamano;

	// -----------------------------------------------------------------
	// Constructores
	// -----------------------------------------------------------------

	/**
	 * Constructor de la lista vacía. <br>
	 * <b>post: </b> Se construyó una lista vacía.
	 */
	public ASNonSortedList( )
	{
		primero = null;
		ultimo = null;
		tamano = 0;
	}

	// -----------------------------------------------------------------
	// Métodos
	// -----------------------------------------------------------------


	public T get( T modelo )
	{
		for( ASNodo<T> p = primero; p != null; p = p.getNext( ) )
		{
			if( p.getData( ).equals( modelo ) )
			{
				return p.getData( );
			}
		}
		return null;
	}


	public int size( )
	{
		return tamano;
	}


	public ASNodo<T> darPrimero( )
	{
		return primero;
	}


	public ASNodo<T> darUltimo( )
	{
		return ultimo;
	}



	public void add( T elemento )
	{
		ASNodo<T> nodo = new ASNodo<T>( elemento );

		if( primero == null )
		{
			primero = nodo;
			ultimo = nodo;
		}


		else
		{
			ultimo.insertarDespues( nodo );
			ultimo = nodo;
		}

		tamano++;
	}


	public T getByIndex( int pos )
	{
		if( pos >= tamano || pos < 0 )
		{
			return null;
		}
		
		
		else
		{
			ASNodo<T> actual = primero;

			for( int cont = 0; cont < pos; cont++ )
			{
				actual = actual.getNext( );
			}

			return actual.getData( );
		}
		
	}

	public T eliminar( int pos )
	{
		T valor = null;

		if( ( pos >= tamano ) || pos < 0 )
		{
			return null;
		}
		
		else if( pos == 0 )
		{
			if( primero.equals( ultimo ) )
			{
				ultimo = null;
			}
			valor = primero.getData( );
			//primero = primero.desconectarPrimero( );
			tamano--;
			return valor;
		}
		else
		{

			ASNodo<T> p = primero.getNext( );
			for( int cont = 1; cont < pos; cont++ )
			{
				p = p.getNext( );
			}

			if( p.equals( ultimo ) )
			{
				ultimo = p.getPrevious( );
			}
			valor = p.getData( );
			//p.desconectarNodo( );
			tamano--;
			return valor;
		}
	}


	public boolean contains( T modelo )
	{
		return get( modelo ) != null;
	}


	public void clear( )
	{
		primero = null;
		ultimo = null;
		tamano = 0;
	}
}
