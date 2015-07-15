package estructurasDatos;

public class ColaPrioridad <T extends Comparable<T> > implements IColaPrioridad<T>
{  
	// -----------------------------------------------------------------
    // Atributes
    // -----------------------------------------------------------------
	/**
     * atribute that represents the first node o the list
    **/
	private ColaNodo<T> primero;
	
	/**
     * atribute that model the last node of the list
    **/
	private ColaNodo<T> ultimo;
	
	/**
     * atribute that represents the size of the list
    **/
	private int tamano;
	

	/**
	 * Este método presenta una complejidad temporal de O(n), lo anterior debido a que en la parte coloreada con verde,
	 * podemos notar que en el peor de los casos el objeto a encolar deberá compararse con n elementos hasta que su prioridad
	 * coincida con la posición en la cual queremos agregarlo
	 */
	public boolean encolar(T pNuevo, double pPrioridad) throws Exception
	{   
        T n = (T) new ColaNodo<T>(pNuevo, pPrioridad);
        boolean rta = false;
		
		if (tamano == 0)
		{
			primero = (ColaNodo<T>) n;
			tamano++;
		}
		
		else if (((ColaNodo<T>) n).getPrioridad() - primero.getPrioridad() > 0 )
		{
			((ColaNodo<T>) n).setNext(primero); 
			primero = (ColaNodo<T>) n;
			tamano++;
		}
        
		// Agregamos el elemento a una cola de n elementos
		else
		{
			T siguiente = (T) primero.getNext();
			T anterior = (T) primero;
			while (siguiente != null)
			{
				if (((ColaNodo<T>) n).getPrioridad() - ((ColaNodo<T>) siguiente).getPrioridad() >  0)
				{
					break;
					
				}
				
				else if(((ColaNodo<T>) n).getPrioridad() - ((ColaNodo<T>) siguiente).getPrioridad() ==  0)
				{
					throw new Exception("No se pueden repetir elementos");
				}
				
				anterior = siguiente;
				siguiente = (T) ((ColaNodo<T>) siguiente).getNext();
				
			}
			
			((ColaNodo<T>) n).setNext(((ColaNodo<T>) anterior).getNext());
			((ColaNodo<T>) anterior).setNext((ColaNodo) n); 
			rta = true;
			tamano++;
		}
		// TODO Auto-generated method stub
		return rta;
	}

	/**
	 * Esta operación es de tipo O(1) dado a que indiferentemente de la cantidad de elementos, esto no influenciará a la operación quien
	 * siempre será constante al eliminar siempre al primero de la cola (el de mayor prioridad) y luego convertir al que le seguía en el
	 * nuevo primero
	 */
	public 	T atender() 
	{   
		
		T n = null;
		if(tamano == 0)
		{
			n = null;
		}
		
		else if(tamano == 1)
		{       
			    n = (T) primero;
				primero = null;
				tamano--;
	    }
		
		else
		{
			    T actual = (T) primero.getNext();
				T anterior = (T) primero;
				
				primero = null;
				primero = (ColaNodo<T>) actual;	
				primero.setNext(((ColaNodo<T>) actual).getNext());
				tamano--;
			
		}
		
		// TODO Auto-generated method stub
		
		return n;
	}

	/**
	 * Esta operación es de complejidad O(1) pues simplemente tiene que coger al primero de la cola y retornarlo
	 */
	public T consultarElementoMaximo() 
	{
		// TODO Auto-generated method stub
		return (T) primero;
	}

   
	/**
	 * Esta operación es de complejidad O(n) puesto que en el peor de los casos tendrá que recorrer los n elementos de la lista para lograr
	 * encontrar al último, el cual es el menor.
	 */
	public T consultarElementoMinimo() 
	{   
		T actual = (T) primero;
		T n = null;
		
		if(tamano == 0)
		{
			n = null;
		}
		
		else if ( tamano == 1)
		{
			n = (T) primero;
		}
		
		else
		{
			while(((ColaNodo<T>) actual).getNext() != null)
			{
				if(((ColaNodo<T>) actual).getNext() == null)
				{
					n = actual;
				}
				
				actual = (T) ((ColaNodo<T>) actual).getNext();
			}
		}
		
		
		// TODO Auto-generated method stub
		return n;
	}

	
	/**
	 * Este método es O(1) puesto que simplemente retorna la variable que representa el tamaño de la cola.
	 */
	public int darLongitud() 
	{
		// TODO Auto-generated method stub
		return tamano;
	}
    
	
	
	/**
	 * O(n) ya que debe agregar n elementos y posteriormente retornarlos. O(n) + O(1)
	 */
	public T[] darElementos() 
	{   
		Object[] rta = new Object[tamano];
		@SuppressWarnings("unchecked")
		T actual = (T) primero;
		
		if(tamano == 0)
		{
			rta = null;
		}
		
		else
		{
			for(int i = 0; i<rta.length; i++)
			{
				rta[i] = actual;
				actual = (T) ((ColaNodo<T>) actual).getNext();
			}
		}
		
		// TODO Auto-generated method stub
		return (T[]) rta;
	}
   
}
