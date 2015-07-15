package estructurasDatos;

/**
 * Clase que representa un nodo del árbol 
 */

public class ASTreeNode < T extends Comparable <T> >  extends ASAVLTree<T>
{
	//------------------------------------------------------------------------------------------------------------
	//Atributos
	//------------------------------------------------------------------------------------------------------------

	/**
	 * Atributo que modela la información contenida por el nodo
	 */
	private T data;

	/**
	 * Atributo que modela el hijo menor del nodo
	 */
	private ASTreeNode<T> izquierda;

	/**
	 * Atributo que modela el hijo mayor del nodo
	 */
	private ASTreeNode<T> derecha;

	/**
	 * Atributo que modela la altura del nodo
	 */
	private int altura;

	/**
	 * Atributo que modela el factor de balanceo del nodo
	 */
	private int factorBalanceo;

	//------------------------------------------------------------------------------------------------------------
	//Constructores
	//------------------------------------------------------------------------------------------------------------

	/**
	 * Constructor de un nodo que no tiene hijos
	 * @param d la información que va a contener el nodo
	 */
	public ASTreeNode(T d)
	{
		data = d;
		altura = -1;
	}

	/**
	 * Constructor  de un nodo que posee hijos.
	 * @param d  información que va a contener el nodo
	 * @param pIzquierda hijo menor del nodo
	 * @param pDerecha hijo mayor del nodo
	 */
	public ASTreeNode(T d, ASTreeNode<T> pIzquierda, ASTreeNode<T> pDerecha) 
	{
		data = d;
		izquierda = pIzquierda;
		derecha = pDerecha;
		altura = -1;
	}


	//------------------------------------------------------------------------------------------------------------
	//Métodos
	//------------------------------------------------------------------------------------------------------------

	/**
	 * Método que retorna la información del nodo
	 * @return la información contenida por el nodo
	 */
	public T darData() 
	{
		return data;
	}                                                           

	/**	
	 * Método que modifica la información que contiene el nodo del árbol
	 */
	public void modificarData(T d) 
	{
		data = d;
	}

	/**
	 * Método que retorna el hijo menor del nodod
	 * @return retorna el nodod izquierdo (hijo menor) del nodo actual
	 */
	public ASTreeNode<T> darNodoIzquierdo() 
	{
		return izquierda;
	}

	/**
	 * Método cambia al hijo menor del nodo actual
	 * @param i nuevo hijo menor del nodo actual
	 */
	public void modificarNodoIzquierdo(ASTreeNode<T> i) 
	{
		izquierda = i;
	}

	/**
	 * Método que retorna al hijo mayor del nodo
	 * @return retorna el nodo derecho (el hijo mayor) del nodo actual
	 */
	public ASTreeNode<T> darNodoDerecho() 
	{
		return derecha;
	}

	/**
	 * Método que cambia al hijo mayor del nodo actual
	 * @param d nuevo hijo mayor del nodo actual
	 */
	public void modificarNodoDerecho(ASTreeNode<T> d) 
	{
		derecha = d;
	}

	/**
	 * Método que retorna la altura del nodo
	 * @return retorna la altura del nodo
	 */
	public int darAltura() 
	{
		return altura;
	}

	public void modificarAltura(int h) 
	{
		altura = h;
	}

	public int darFactorBalanceo() 
	{
		return factorBalanceo;
	}

	public void modificarFactorBalanceo(int balance) 
	{
		factorBalanceo = balance;
	}

	/**
	 * Retorna el nodo con el mayor elemento de un árbol AVL. <br>
	 * este método utiliza recursividad.
	 * <b>post: </b> Se retornó el nodo con el mayor elemento de un árbol AVL.
	 * @return Nodo Nodo con el mayor elemento de un árbol AVL
	 */
	public ASTreeNode<T> mayorElemento( )
	{
		return (derecha == null ) ? this : derecha.mayorElemento( );
	}

	/**
	 * Retorna el nodo con el menor elemento de un árbol AVL.<br>
	 * Este método utiliza recursividad.
	 * <b>post: </b> Se retornó el nodo con el menor elemento de un árbol AVL.
	 * @return Nodo Nodo con el menor elemento de un árbol AVL
	 */
	public ASTreeNode<T> menorElemento( )
	{
		return ( izquierda == null ) ? this : izquierda.menorElemento( );
	}


}
