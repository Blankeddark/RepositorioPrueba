package estructurasDatos;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

public class IteratorHT<K extends Comparable<K>, V > implements Iterator 
{

	private ASNodo< ASEntry <K, V> > previous;
	private ASNodo< ASEntry<K,V> > next;
	private ASortedList listaActual;
	private ASHashTable<K, V> ht;
	private ASList<K> keySet;
	private ASHashTable<K, K> recorridos;
	private IteratorASortedList<V> it;
	int posicionEnKeySetSiguiente;

	public IteratorHT(ASHashTable ht)
	{
		this.ht = ht;
		previous = null;
		keySet = ht.getKeySet();
		recorridos = new ASHashTable((int) (keySet.size() * 1.5));
		posicionEnKeySetSiguiente = 0;

		if (keySet.size() > 0)
		{
			listaActual = ht.getListOf(keySet.get(0));
			next = listaActual.darPrimero();
			it = listaActual.iterator();
			posicionEnKeySetSiguiente++;
		}
		else
		{
			next = null;
		}
	}

	/**
	 * Asigna a la variable posicionEnKeySetSiguiente la siguiente posici�n donde no hay
	 * una llave repetida.
	 */
	public void siguientePosicionValidaEnKeySet()
	{
		boolean encontro = false;
		for (int i = 0; i < keySet.size() && !encontro; i++)
		{
			if (!recorridos.containsKey(keySet.get(i)))
			{
				posicionEnKeySetSiguiente = i;
				encontro = true;
			}
		}

		if (!encontro)
		{
			posicionEnKeySetSiguiente = -1;
		}

		//System.out.println(posicionEnKeySetSiguiente);
	}

	public boolean hasNext() 
	{
		if (posicionEnKeySetSiguiente == -1 || next == null)
			return false;

		else
			return true;
	}

	public V next() 
	{
		V rta = null;
		K key = keySet.get(posicionEnKeySetSiguiente);

		//Verifica si hay colisiones.
		if (it.hasNext())
		{
			//Si hay, verifica si la siguiente colisi�n tiene siguiente. Si hay, es la que
			//debo devolver la pr�xima vez (entrar� a este if, verificr� si hay colisiones
			//en el nuevo next; si no hay, regresar� el next y el siguiente ser� el primero
			//de la lista siguiente. Si no hay, entrar� a este preciso if, verificar� si hay
			//siguiente del siguiente y retornar� el siguiente.
			if (it.getNext().getNext() != null)
			{
				next = it.getNext().getNext();
			}

			else
			{
				//Si no hay colisiones, mi siguiente elemento ser� el primero de la siguiente lista,
				//siempre y cuando no haya sido recorrida. Aumentar la posici�n de la siguiente key
				//sin importar el caso.

				listaActual = ht.getListOf(key);
				it = listaActual.iterator();
				next = listaActual.darPrimero();
				//Si ya no hay m�s posiciones que recorrer en la lista, agrega la key a la lista de
				//recorridos para asegurarse de no volverlo a recorrer.
				recorridos.put(key, key);

				siguientePosicionValidaEnKeySet();
			}

			//Ya que el if es s�lo si el next es el primero, retorna el primero de la 
			//lista en cuesti�n (su existencia s�lo sirve para comprobar si hay siguiente
			//del siguiente).
			rta = (V) it.next();
		}

		//Si no hay colisiones, mi siguiente siguiente ser� el primero de la 
		//lista en la pr�xima posici�n, siempre y cuando no haya sido recorrida.
		else
		{
			if (key != null)
			{
				if (!recorridos.containsKey(key))
				{
					rta = (V) ( (ASEntry) next.getData() ).getValue();
					listaActual = ht.getListOf(keySet.get(posicionEnKeySetSiguiente));
					it = listaActual.iterator();
					next = listaActual.darPrimero();
					recorridos.put(key, key);
				}
			}

			siguientePosicionValidaEnKeySet();

		}
		
		return rta;
	}
}
