package estructurasDatos;

import java.util.Iterator;

/**
 * Interfaz que se usará para la implementación de un árbol AVL
 * El cual se caracteriza por ser balanceado, lo último quiere decir que
 * posee igual cantidad de elementos en ambos lados del árbol
 */
public interface IASTree <T extends Comparable<T>>
{
	/**
	 * Añade un nodo que ingresa por parámetro como una hija del árbol.  
	 * Este debe de avanzar a través del árbol con el fin de encontrar su posición indicada.
	 * No se aceptan objetos repetidos, en caso de que se intente agregar un objeto que ya se
	 * encuentre dentro del árbol este no será agregado.
	 * No se agregarán objetos cuyo valor sea nulo.
	 * @param data el objeto que va a ser añadido.
	 * @throws Excepcion si el elemento a agregar es nulo
	 * @return retorna true en caso de poder agregar el nodo, false en caso contrario
	 */
	public boolean add(T data) throws Exception;

	/**
	 * Remueve un objeto del árbol.  
	 * Existen 3 casos a tener en consideración:
	 * Caso 1.)  El objeto a remover es una hoja.  En este caso simplemente se elimina del árbol.
	 * Caso 2.) El objeto a eliminar tiene un hijo.  En este caso se reemplaza al objeto a eliminar
	 * por su hijo y luego se remueve al hijo de este.
	 * Caso 3.) El objeto a eliminar tiene 2 hijos. Para este caso se utiliza
	 * la siguiente solución:  reemplazarlo con elemento más grande al objeto a eliminar
	 * que esté más cerca a este (su sucesor).
	 * @param data el objeto que va a ser eliminado
	 * @return el objeto que ha sido eliminado del árbol.
	 * @throws Exception si el objeto a eliminar es nulo
	 */
	public T remove(T data) throws Exception;

	/**
	 * Returna el objeto dentro del árbol que concuerde con el valor
	 * que entra como parámetro. En caso de no existir retorna nulo.
	 * @param data el objeto que vamos a buscar.
	 * @throws Exception si la información para buscar el nodo es nula.
	 * @return el objeto dentro del árbol que es igual al parámetro.
	 */
	public T get(T data) throws Exception;


	/**
	 * Método que retorna el tamaño del árbol.
	 * @return retorna el número de elementos contenidos dentro del árbol.
	 */
	public int darPeso();
	

	/**
	 * Método que retorna la altura del árbol. 
	 * En caso de que el árbol solo sea una hoja retorna 0.
	 * * En caso de que el árbol esté vacío retornará -1.
	 * @return retorna la altura del árbol.
	 */
	public int darAltura();
	

}
