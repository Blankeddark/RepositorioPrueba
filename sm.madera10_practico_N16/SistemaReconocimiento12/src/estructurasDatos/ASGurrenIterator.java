package estructurasDatos;

import java.util.Iterator;

/**
 * Es un iterator con juegos de azar y mujerzuelas para el ASAVLTree. 
 * Diccionario -
 * "con juegos de azar" = es recursivo.
 * "mujerzuelas" = mujerzuelas.
 * 
 * @author sm.madera10
 *
 * @param <T> genericidad zensual
 */
public class ASGurrenIterator<T> implements Iterator<T>
{
	//Atributos
	private ASTreeNode raíz;
	private ASTreeNode nextNode;
	private ASGurrenIterator<T> nextIterator;

	public ASGurrenIterator(ASAVLTree loveAndLife)
	{
		raíz = loveAndLife.darRaíz();
		ASTreeNode derecho = raíz.darNodoDerecho();
		ASTreeNode izquierdo = raíz.darNodoIzquierdo();
		nextNode();

	}

	public boolean hasNext() 
	{
		return nextNode == null? false : true;
	}

	public T next() 
	{
		return null; //TODO
	}

	public ASTreeNode nextNode()
	{
		ASTreeNode derecho = raíz.darNodoDerecho();
		ASTreeNode izquierdo = raíz.darNodoIzquierdo();
		if (izquierdo != null)
		{
			nextNode = izquierdo;
		}

		else if (derecho != null)
		{
			nextNode = derecho;
		}
		
		else
		{
			nextNode = raíz;
		}
		
		return nextNode;
	}

}
