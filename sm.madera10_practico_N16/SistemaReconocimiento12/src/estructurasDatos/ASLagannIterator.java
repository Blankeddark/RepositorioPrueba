package estructurasDatos;

import java.util.Iterator;

/**
 * Es un iterador cuya funcionalidad adicional es la de agregar los elementos que va recorriendo
 *  a un arreglo, para observarlos m�s facil luego
 *
 */
public class ASLagannIterator<T> implements Iterator<T>
{

	private final static int VACIO = -1;

	/**
	 * Elementos sobre los que se est� iterando
	 */
	private T[] elementos;

	/**
	 * Atributo que modela la posici�n actual en la que se encuentra el iterador
	 */
	private int actual;

	/**
	 * se usa para sacar el numero de elementos sobre el que se esta iterando
	 */
	private int sigPosLibre;

	/**
	 * constructor
	 * @param iniTam tama�o total del iterador
	 */
	public ASLagannIterator( int iniTam)
	{
		elementos = ( T[] ) new Object[iniTam];
		sigPosLibre = 0;
		actual = VACIO;
	}

	public boolean hasNext() 
	{
		if( elementos.length > 0 && ( actual + 1 ) < sigPosLibre)
		{
			return true;
		}

		else
		{
			return false;
		}

	}

	public T next() 
	{
		return hasNext( ) ? elementos[ ++actual ] : null;

	}

	public T getPrevious( )
	{
		return hasPrevious( ) ? elementos[ --actual ] : null;
	}


	public boolean hasPrevious( )
	{
		if(elementos.length > 0 && actual > 0)
		{
			return true;
		}

		else
		{
			return false;
		}

	}

	public void clear( )
	{
		actual = VACIO;
	}


	/**
	 * Agrega un nuevo elemento al final del iterador.
	 */
	public void add( T elem ) throws Exception
	{
		if( sigPosLibre <= elementos.length - 1 )
		{
			elementos[ sigPosLibre++ ] = (T) elem;
		}

		else
		{
			throw new Exception( "El iterador est� lleno. No se pueden a�adir m�s elementos" );
		}

	}


	/**
	 * Inserta un nuevo elemento en la primera posici�n del iterador.
	 */
	public void insertar( T elem ) throws Exception
	{
		if( sigPosLibre >= elementos.length )	
		{
			throw new Exception( "L�mite del iterador alcanzado" );
		}

		// Abre espacio para el nuevo elemento
		for( int i = sigPosLibre; i > 0; i-- )
		{
			elementos[ i ] = elementos[ i - 1 ];
		}

		sigPosLibre++;
		elementos[ 0 ] = elem;
	}


	/**
	 * Retorna la siguiente posici�n libre del iterador (n�mero de elementos sobre los que se est� iterando).
	 */
	public int darSigPosLibre( )
	{
		return sigPosLibre;
	}


	/**
	 * Retorna la posici�n del pr�ximo elemento a ser visitado.
	 */
	public int darPosActual( )
	{
		return actual;
	}


	/**
	 * Retorna el tama�o del iterador (n�mero m�ximo de elementos que puede recorrer).
	 */
	public int darLongitud( )
	{
		return elementos.length;
	}

	public T[] darElementos()
	{
		return elementos;
	}
	
	public String toString( )
	{  
        String resp = "";
		
		for( int i = 0; i < sigPosLibre; i++ )
		{   
			if( i == sigPosLibre - 1)
			{
				resp += elementos[ i ];
			}
			
			else
			{
				resp += elementos[ i ] + ", ";
			}
				
		}
	
		return resp;
	}

}


