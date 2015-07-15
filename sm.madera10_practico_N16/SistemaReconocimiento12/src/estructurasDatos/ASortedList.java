package estructurasDatos;

import java.io.Serializable;
import java.util.Iterator;

/**
 * Class that represents a sorted linked container. This class is generic and comparable. This class 
 * implements from IAS_LIST
**/
public class ASortedList <T extends Comparable<T> > implements IAS_LIST<T>, Serializable, Iterable
{   
	
	// -----------------------------------------------------------------
    // Atributes
    // -----------------------------------------------------------------
	/**
     * atribute that represents the first node o the list
    **/
	private ASNodo<T> primero;
	
	/**
     * atribute that model the last node of the list
    **/
	private ASNodo<T> ultimo;
	
	/**
     * atribute that represents the size of the list
    **/
	private int tamano;
	
	  // -----------------------------------------------------------------
	  // Constructor
	  // ----------------------------------------------------------------
	/**
	* Constructor of the list<br>
	* <b>post: </b> A new list have been created.
	*/
	public ASortedList()
	{
		int tamaño = 0;
		primero = null;
		ultimo = null;
	}
	
	public boolean isEmpty()
	{   
		boolean rta = false;
		
		if(primero == null)
		{
			rta = true;
		}
		
		return rta;
	}
	
	public int  size()
	{
		return tamano;
	}
	
	public void clear()
	{
		primero = null;
		ultimo = null;
		tamano = 0;
	}
	
	public void add(T item)
	{
		
		ASNodo n = new ASNodo(item);
		
		if (primero == null)
		{
			primero = n;
			ultimo = n;
			tamano++;
		}
		
		else
		{
			//ASNodo siguiente = primero.getNext();
//			ASNodo anterior = ultimo.getPrevious();
//			while (siguiente != null)
//			{
//				if (((Comparable<T>) item).compareTo((T) siguiente.getData()) < 0)
//				{
//					break;
//					
//				}
//				
//				anterior = siguiente;
//				siguiente = siguiente.getNext();
//				
//			}
			
			n.setPrevious(ultimo);
			ultimo.setNext(n);
			ultimo = n;
			tamano++;
		}

	}
	
	
	public void remove(T dato) throws Exception
	{
		T n = get(dato);
		ASNodo op = new ASNodo(n);
		
		if(n != null)
		{   
			if(tamano == 0)
			{
				throw new Exception ("No se puede eliminar");
			}
		
			else
			{
				if(( primero.getData()).compareTo(n) == 0)
				{
					op = primero.getNext();
					primero = null;
					primero = op;
					tamano--;
				}
				
				else
				{
					ASNodo actual = primero.getNext();
					ASNodo anterior = primero;
					while(actual != null)
					{  
						if( ( (Comparable<T>) actual.getData() ).compareTo(n) == 0)
						{   
							ASNodo x = actual;
							x = null;
							anterior.setNext(actual.getNext());
							tamano--;
							
						}
						
						actual = actual.getNext();
						anterior = anterior.getNext();	
					}
				}
			}
			
		}
	}
	
	/**
	 * Method that returns the first node of the list
	 * @return the first node of the list
	 */
	public ASNodo darPrimero()
	{
		return primero;
	}
	
	/**
	 * Method that returns the last node of the list
	 * @return the last node of the list
	 */
	public ASNodo darUltimo()
	{
		return ultimo;
	}
	
	/**
	 * Method that returns a node who contains an specific data.
	 * @param d Data that is contained by the node that we are looking for.
	 * @return the node that contains the d Data
	 */
	public T get(T d)
	{
		T n = null;
		if(primero == null)
		{
			n = null;
		}
		
		else
		{
			ASNodo p = primero;
			while(p != null)
			{
				
				if(((Comparable<T>) p.getData()).compareTo(d) == 0)
				{
					n = (T) p.getData();
				}
				
				p = p.getNext();
			}
		}
		
		return n;
	}
	
	
	@SuppressWarnings("unchecked")
	public T getByIndex(int i)
	{
		if( i >= tamano || i < 0 )
        {
            return null;
        }
		
        else
        {
            ASNodo<T> aux = primero;

            for( int cont = 0; cont < i; cont++ )
            {
                aux = aux.getNext( );
            }

            return aux.getData();
        }
	}
	
	public String toString()
	{
		String rta =  " " + primero.dato;
		ASNodo n =  primero.getNext();
		while(n != null)
		{  
			rta += " " +  n.getData();
			n = n.getNext();
		}
		
		return rta;
	}

	
	public boolean contains(T o) 
	{
        boolean rta = false;
        ASNodo n = new ASNodo(o);
        ASNodo actual = primero;
        while(actual != null)
        {   
        	
        	if(((Comparable<T>) actual.getData()).compareTo((T) n.getData()) == 0)
        	{
        		rta = true;
        	}
        	actual = actual.getNext();
        	
        }
        
		return rta;
	}
	
	public T getFirst()
	{
		return primero.getData();
	}
	
	public T getLast()
	{
		return ultimo.getData();
	}

	public IteratorASortedList<T> iterator()
	{
		return new IteratorASortedList<T>(this);
	}

}
