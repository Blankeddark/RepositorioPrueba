package estructurasDatos;

import java.io.Serializable;
import java.util.Arrays;


/**
 * Class that represent a sorted dynamic array which can sort and store all his elements. 
 * The objects of  T  type must be implemented from the interface <b>Comparable</b>
 * @param <T> the type of data which is going to be stored.
 */
public class ASList <T extends Comparable<T>> implements IAS_LIST<T>, Serializable
{
	// -----------------------------------------------------------------
	// Atributes
	// -----------------------------------------------------------------
	/**
	 * Represents the array where are going to be stored the dates.
	 */
	private Object[] estructuraAS;

	/**
	 * Indicates the number of elements that are allocated inside the array.
	 */
	private int tamaño;


	// -----------------------------------------------------------------
	// Constructor
	// -----------------------------------------------------------------

	@SuppressWarnings("unchecked")
	/**
	 * Constructor of the array<br>
	 * <b>post: </b> A new array have been created.
	 */
	public ASList()
	{
		estructuraAS = new Object[50];
		tamaño = 0;

	}

	public ASList(int size)
	{
		estructuraAS = new Object[size];
		tamaño = 0;
	}

	//-----------------------------------------------------------------
	//Methods
	//-----------------------------------------------------------------

	/**
	 * adds a new object at the end of the array. <br>
	 * <b>post: </b> A new element of T type have been added.<br>
	 * @param op Element to be added<br>
	 */
	public  void addLast(T op)
	{
		if(estructuraAS.length - tamaño <= 0)
		{
			estructuraAS = Arrays.copyOf(estructuraAS, (estructuraAS.length) * 2);
			estructuraAS[tamaño] = op;
			tamaño++;
		}

		else
		{
			estructuraAS[tamaño] = op;
			tamaño++;
		}
	}

	/**
	 * adds a new object at a specific position of the array. <br>
	 * <b>post: </b> A new element of T type have been added.<br>
	 * @param o Element to be added, i Position where we want to add the element<br>
	 */
	public void addPos(int i, T o)
	{   
		if (i< 0 || i > size())
		{
			throw new IndexOutOfBoundsException("Index exceeds limits");
		}

		if (tamaño == estructuraAS.length)
		{
			Object[] temp = new Object[(estructuraAS.length) * 2];
			System.arraycopy(estructuraAS, 0, temp, 0, i);
			System.arraycopy(estructuraAS, i, temp, i + 1, tamaño - i);
			estructuraAS = temp;
		}

		else
		{
			for (int x = tamaño -1; x >= i; x--)
			{
				estructuraAS[x + 1] = estructuraAS[x];	
			}
		}

		estructuraAS[i] = o;
		tamaño++;
	}

	/**
	 * adds a new element to the array at the end of it. Complexity: O(1) most of the time, O(n) if it runs out of space. <br>
	 * <b>post: </b> a new element have been added to the array according to his relation with other elements of the T type.<br>
	 * @param o Element to add<br>
	 */
	public void add(T op)
	{   

		if(estructuraAS.length - tamaño <= 0)
		{
			estructuraAS = Arrays.copyOf(estructuraAS, (estructuraAS.length) * 2);
			estructuraAS[tamaño] = op;
			tamaño++;
		}

		else
		{
			estructuraAS[tamaño] = op;
			tamaño++;

		}

	}

	/**
	 * adds a new element to the array assuring it stays sorted. Complexity O(n) + O(n) if it runs out of space. <br>
	 * <b>post: </b> a new element have been added to the array according to his relation with other elements of the T type.<br>
	 * @param o Element to add<br>
	 */
	public void addSorted(T op)
	{   

		if(estructuraAS.length - tamaño <= 0)
		{
			estructuraAS = Arrays.copyOf(estructuraAS, (estructuraAS.length) * 2);
			estructuraAS[tamaño] = op;
			tamaño++;
		}

		else
		{
			estructuraAS[tamaño] = op;
			tamaño++;

		}

		for(int i = tamaño - 1; i > 0; i--)
		{
			T p = (T) estructuraAS[i - 1];

			if(op.compareTo(p) < 0)
			{
				set(i, p);
				set(i-1, op);
			}
		}

	}

	/**
	 * Remove an specific element from the array <br>
	 * <b>post: </b> An element have been deleted from the array.<br>
	 * @param o Element to delete<br>
	 * @throws Exception if the array is empty and the user wants to delete something  <br>
	 */
	public void remove(T o) throws Exception
	{   
		boolean termine = false;
		if(tamaño == 0)
		{
			throw new Exception ("No se puede eliminar");
		}

		else
		{
			for (int i = 0; i< estructuraAS.length && !termine; i ++)
			{
				Object p = estructuraAS[i];
				if( ((Comparable<T>) p).compareTo(o) == 0)
				{
					p = null;
					termine = true;
					tamaño--;
				}
			}  
		}

	}

	/**
	 * Remove an element from  a specific position of the array <br>
	 * <b>post: </b> An element have been deleted from the array.<br>
	 * @param i Position of the element to be removed<br>
	 * @throws Exception if the array is empty and the user wants to delete something  <br>
	 */
	public void removePos(int i) throws Exception
	{   
		if(tamaño == 0)
		{
			throw new Exception("The list is empty" );
		}

		else
		{
			if(i < tamaño)
			{

				estructuraAS[i]= null;
				int temp = i;
				while(temp < tamaño)
				{
					estructuraAS[temp] = estructuraAS[temp+1];
					estructuraAS[temp+1] = null;
					temp++;
				}
				tamaño--;

			} 

			else 
			{
				throw new IndexOutOfBoundsException("Index not included within the range");
			}
		}


	}

	/**
	 * Get an element from a specific position of the array. <br>
	 * <b>post: </b> An element from an specific position of the array have been removed, if it doesn´t exist then return null. <br>
	 * @param i Position of the element to get<br>
	 * @throws Exception if the position os the element is not included in the range of the array <br>
	 * @return Element of the list, null if it doesn´t exist<br>
	 */
	public T get(int i)
	{   
		T o = null;

		if(i > tamaño  ||  i < 0)
		{
			throw new IndexOutOfBoundsException();
		}

		else
		{   
			o = (T) estructuraAS[i];

		}

		return o;
	}

	/**
	 * Method that returns the number of elements of the aray. <br>
	 * @return returns the number of elements of the array, in other words, the size<br>
	 */
	public  int size()
	{
		return tamaño;
	}

	/**
	 * Method that say if the array is empty or not.
	 * @return true if the size of the array is 0 and true if not.
	 */
	public boolean isEmpty()
	{
		return tamaño == 0 ? true : false;
	}

	/**
	 * Method that say if an specific object is inside the array or not.
	 * @param o Element to be search.
	 * @return true if the array contains o , or false if not.
	 */
	public boolean contains(T  o)
	{   
		boolean rta = false;
		boolean termine = false;
		for(int i = 0; i< tamaño && !termine; i++)
		{
			T p = (T) estructuraAS[i];
			if(p.compareTo(o) == 0)
			{
				rta = true;
			}

		}

		return rta;
	}

	/**
	 * Method that copy the contain of an Array to our array.
	 * <b>post: </b> A collection of elements have been added to the array.<br>
	 * @param param The array that we are going to copy.
	 */
	public void addAll(ASList param)
	{
		for(int i = 0; i < param.size() ; i++)
		{
			add((T) param.get(i));

		}
	}

	/**
	 * Method that clear the array, in other words, resets the array.
	 * <b>post: </b> The array have been cleared.<br>
	 */
	public void clear()
	{
		tamaño = 0;
		estructuraAS = new Object[50];
	}
	
	public void clear(int size)
	{
		tamaño = 0;
		estructuraAS = new Object[size];
	}

	/**
	 *  Returns a string with the value of the array.
	 *  @return returns a string that contains the value of each element of the array
	 */
	public String toString()
	{
		String rta = "";
		for(int i = 0; i < estructuraAS.length; i++)
		{
			if(estructuraAS[i] != null)
			{
				rta += " " +  estructuraAS[i];
			}
		}

		return rta;
	}


	/**
	 * Set an element in a specific position of the array
	 * <b>post: </b> An object have been added to the position i.<br>
	 * @param i Position where we are going to add the element, o Element to the setted.<br>
	 */
	public void set ( int i, T o)
	{
		estructuraAS[i] = o;
	}

	/**
	 * Searches for an element with binary search. Only possible if the list is ordered.
	 * @return true if the element in argument is in the list. False if it is not.
	 */
	public boolean containsBinary(T o)
	{
		int inicio = 0;
		int fin = tamaño - 1;
		boolean encontro = false;
		while (inicio <= fin && !encontro)
		{
			int mitad = (inicio + fin)/2;
			if (o.compareTo((T) estructuraAS[mitad]) > 0)
			{
				inicio = mitad + 1;
			}
			else if (o.compareTo((T) estructuraAS[mitad]) < 0)
			{
				fin = mitad - 1;
			}
			else
			{
				encontro = true;
			}
		}

		return encontro;
	}

	/**
	 * Sorts an Array using the merge sort algorithm
	 * <b>post: </b> The array have been sorted.<br>
	 * @param arregloOrdenar Array to sort.<br>
	 * @return the sorted version of the array.
	 */
	public ASList<T> mergeSort(ASList<T> arregloOrdenar)
	{
		ASList<T> izquierda = new ASList<T>();
		ASList<T> derecha = new ASList<T>();
		int center;

		if(arregloOrdenar.size()==1)   
		{
			return arregloOrdenar;
		}

		else
		{   

			center = arregloOrdenar.size()/2;

			for(int i=0; i<center; i++)
			{
				izquierda.add((T) arregloOrdenar.get(i));
			}

			for(int i=center; i<arregloOrdenar.size(); i++)
			{
				derecha.add((T) arregloOrdenar.get(i));
			} 

			izquierda  = mergeSort(izquierda);

			derecha = mergeSort(derecha);

			join(izquierda,derecha,arregloOrdenar);

		}
		return arregloOrdenar;
	}

	/**
	 * Complementary method to mergeSort method. It join the different parts of the original array that we use to sort it.
	 * @param izquierda Array that represents the left side of the original array
	 * @param derecha Array that represents the right side of the original array
	 * @param arregloOrdenar Array to sort.
	 */
	private void join(ASList<T> izquierda, ASList<T> derecha, 
			ASList<T> arregloOrdenar) 
	{

		int indiceIzqui = 0;
		int indiceDere = 0;
		int indiceOrdenar = 0;

		while (indiceIzqui < izquierda.size() && indiceDere < derecha.size())
		{
			if ((( izquierda.get(indiceIzqui)).compareTo((T) derecha.get(indiceDere)))<0) 
			{
				arregloOrdenar.set(indiceOrdenar,(T) izquierda.get(indiceIzqui));
				indiceIzqui++;
			}
			else
			{
				arregloOrdenar.set(indiceOrdenar, (T) derecha.get(indiceDere));
				indiceDere++;
			}
			indiceOrdenar++;
		}

		ASList<T>rest;
		int restIndex;
		if (indiceIzqui >= izquierda.size()) 
		{

			rest = derecha;
			restIndex = indiceDere;
		}
		else 
		{

			rest = izquierda;
			restIndex = indiceIzqui;
		}


		for (int i=restIndex; i<rest.size(); i++) 
		{
			arregloOrdenar.set(indiceOrdenar, (T) rest.get(i));
			indiceOrdenar++;
		}
	}

}
