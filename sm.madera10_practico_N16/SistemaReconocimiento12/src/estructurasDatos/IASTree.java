package estructurasDatos;

import java.util.Iterator;

/**
 * Interfaz que se usar� para la implementaci�n de un �rbol AVL
 * El cual se caracteriza por ser balanceado, lo �ltimo quiere decir que
 * posee igual cantidad de elementos en ambos lados del �rbol
 */
public interface IASTree <T extends Comparable<T>>
{
	/**
	 * A�ade un nodo que ingresa por par�metro como una hija del �rbol.  
	 * Este debe de avanzar a trav�s del �rbol con el fin de encontrar su posici�n indicada.
	 * No se aceptan objetos repetidos, en caso de que se intente agregar un objeto que ya se
	 * encuentre dentro del �rbol este no ser� agregado.
	 * No se agregar�n objetos cuyo valor sea nulo.
	 * @param data el objeto que va a ser a�adido.
	 * @throws Excepcion si el elemento a agregar es nulo
	 * @return retorna true en caso de poder agregar el nodo, false en caso contrario
	 */
	public boolean add(T data) throws Exception;

	/**
	 * Remueve un objeto del �rbol.  
	 * Existen 3 casos a tener en consideraci�n:
	 * Caso 1.)  El objeto a remover es una hoja.  En este caso simplemente se elimina del �rbol.
	 * Caso 2.) El objeto a eliminar tiene un hijo.  En este caso se reemplaza al objeto a eliminar
	 * por su hijo y luego se remueve al hijo de este.
	 * Caso 3.) El objeto a eliminar tiene 2 hijos. Para este caso se utiliza
	 * la siguiente soluci�n:  reemplazarlo con elemento m�s grande al objeto a eliminar
	 * que est� m�s cerca a este (su sucesor).
	 * @param data el objeto que va a ser eliminado
	 * @return el objeto que ha sido eliminado del �rbol.
	 * @throws Exception si el objeto a eliminar es nulo
	 */
	public T remove(T data) throws Exception;

	/**
	 * Returna el objeto dentro del �rbol que concuerde con el valor
	 * que entra como par�metro. En caso de no existir retorna nulo.
	 * @param data el objeto que vamos a buscar.
	 * @throws Exception si la informaci�n para buscar el nodo es nula.
	 * @return el objeto dentro del �rbol que es igual al par�metro.
	 */
	public T get(T data) throws Exception;


	/**
	 * M�todo que retorna el tama�o del �rbol.
	 * @return retorna el n�mero de elementos contenidos dentro del �rbol.
	 */
	public int darPeso();
	

	/**
	 * M�todo que retorna la altura del �rbol. 
	 * En caso de que el �rbol solo sea una hoja retorna 0.
	 * * En caso de que el �rbol est� vac�o retornar� -1.
	 * @return retorna la altura del �rbol.
	 */
	public int darAltura();
	

}
