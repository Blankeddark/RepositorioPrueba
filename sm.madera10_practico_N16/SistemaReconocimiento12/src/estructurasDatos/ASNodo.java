package estructurasDatos;

import java.io.Serializable;

/**
 * Class that represents a node in the list, it's a generic class
**/
public class ASNodo <T> implements Serializable
{   
	// -----------------------------------------------------------------
    // Atributes
    // -----------------------------------------------------------------
	/**
	 * Kind of data of the node
	**/
    T dato;
    
    /**
     * atribute that represents the next node of the list
    **/
	private ASNodo siguiente;
	
	/**
	 * The previous node.
	 */
	private ASNodo anterior;
	
	  // -----------------------------------------------------------------
	  // Constructor
	  // ----------------------------------------------------------------
	/**
	* Constructor of the node<br>
	* <b>post: </b> A new node have been created.
	*/
	public ASNodo(T d)
	{
		dato = d;
		siguiente = null;
		anterior = null;
	}
	
	/**
	 * Creates a node with another (a clone)
	 */
	public ASNodo(ASNodo nodo)
	{
		this.siguiente = nodo.getNext();
		this.dato = (T) nodo.getData();
		this.anterior = nodo.getPrevious();
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
	 * @return the anterior
	 */
	public ASNodo getPrevious() {
		return anterior;
	}

	/**
	 * @param anterior the anterior to set
	 */
	public void setPrevious(ASNodo anterior) {
		this.anterior = anterior;
	}

	/**
     * returns the next node os the list
    **/
	public ASNodo getNext() 
	{
		return siguiente;
	}
	
	/**
     * change the value of the next node
    **/
	public void setNext(ASNodo next)
	{
		siguiente = next;
	}
	
	public void insertarDespues( ASNodo<T> nodo )
    {
        nodo.siguiente = siguiente;
        nodo.anterior = this;
        if( siguiente != null )
            siguiente.anterior = nodo;
        siguiente = nodo;
    }
	
	
	/**
     * returns a string with the node's information
    **/
	public String toString()
	{
		   return " " + dato;
    }
}
