package estructurasDatos;

import java.util.Iterator;

/**
 * Es un iterador cuya funcionalidad adicional es la de agregar los elementos que va recorriendo
 *  a un arreglo, para observarlos más facil luego
 *
 */
public class ASLagannIterator<T> implements Iterator<T>
{

	private final static int VACIO = -1;

	/**
	 * Elementos sobre los que se está iterando
	 */
	private T[] elementos;

	/**
	 * Atributo que modela la posición actual en la que se encuentra el iterador
	 */
	private int actual;

	/**
	 * se usa para sacar el numero de elementos sobre el que se esta iterando
	 */
	private int sigPosLibre;

	/**
	 * constructor
	 * @param iniTam tamaño total del iterador
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
			throw new Exception( "El iterador está lleno. No se pueden añadir más elementos" );
		}

	}


	/**
	 * Inserta un nuevo elemento en la primera posición del iterador.
	 */
	public void insertar( T elem ) throws Exception
	{
		if( sigPosLibre >= elementos.length )	
		{
			throw new Exception( "Límite del iterador alcanzado" );
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
	 * Retorna la siguiente posición libre del iterador (número de elementos sobre los que se está iterando).
	 */
	public int darSigPosLibre( )
	{
		return sigPosLibre;
	}


	/**
	 * Retorna la posición del próximo elemento a ser visitado.
	 */
	public int darPosActual( )
	{
		return actual;
	}


	/**
	 * Retorna el tamaño del iterador (número máximo de elementos que puede recorrer).
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


