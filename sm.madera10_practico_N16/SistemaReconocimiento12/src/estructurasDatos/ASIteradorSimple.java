package estructurasDatos;

import java.util.Iterator;



public class ASIteradorSimple<T> extends ASNonSortedList<T> implements Iterator<T>
{


	//Constante que representa que el iterador se encuentra vacío
	private final static int VACIO = -1;

	/**
	 * Atributo que modela la posición actual en la que se encuentra el iterador
	 */
	private int actual;



	public ASIteradorSimple( )
	{
		actual = VACIO;
	}

	public void agregar(T info) 
	{
		add(info);
	}

	public boolean hasNext() 
	{   

		boolean retornar = false;

		if(( actual + 1 ) < size( ))
		{
			retornar = true;
		}

		return retornar;
	}

	public T next() 
	{
		return hasNext( ) ? getByIndex( ++actual ) : null;
	}

	public void remove() 
	{   // TODO Auto-generated method stub

	}

	public String toString( )
	{
		String resp = "[" + size( ) + "]:";

		for( int i = 0; i < size( ); i++ )
		{
			resp += getByIndex( i ) + "-";
		}

		return resp;
	}
	
	public int darTamanño()
	{
		return size();
	}



}
