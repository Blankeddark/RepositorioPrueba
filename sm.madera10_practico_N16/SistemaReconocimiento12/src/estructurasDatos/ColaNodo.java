package estructurasDatos;

public class ColaNodo <T>
{
   
	// -----------------------------------------------------------------
    // Atributes
    // -----------------------------------------------------------------
	
    private T dato;
    
	private double prioridad;
	
	private ColaNodo siguiente;
	
	public ColaNodo(T d, double x)
	{
		dato = d;
		prioridad = x;
	}
	
	//-----------------------------------------------------------------
	//Methods
	//-----------------------------------------------------------------
	/**
	 * returns the kind of data
	 **/
       public T getData() 
		{
			return dato;
		}
   
		/**
	     * change the value of data
	     * post: The data have been changed
	    **/
		public void setData(int d) 
		{
			dato = dato;
		}
		
		/**
	     * returns the next node os the list
	    **/
		public double getPrioridad() 
		{
			return prioridad;
		}
		
		/**
	     * change the value of the next node
	    **/
		public void setPrioridad(double n)
		{
			prioridad = n;
		}
		
		public ColaNodo getNext()
		{
			return siguiente;
		}
		
		public void setNext(ColaNodo d)
		{
			siguiente = d;
		}
		
		/**
	     * returns a string with the node's information
	    **/
		public String toString()
		{
			   return " " + prioridad + " " + dato;
	    }
}
