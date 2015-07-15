package estructurasDatos;

public interface IColaPrioridad <T extends Comparable<T>>

{
   /**
    * agrega un elemento seg�n su prioridad
    * @return true si se agreg� el nuevo elemento y se garantiza que solo existe un elemento pNuevo en la cola
 * @throws Exception 
    */
	public boolean encolar(T pNuevo, double pPrioridad) throws Exception;
	
	/**
	 * Obtener el elemento de mayor prioridad. Este elemento sale de la cola.
	 */
	public T atender();
	
	/**
	 * Retorna el elemento de ayor prioridad. Este elemento se mantiene en la cola de prioridad.
	 */
	public T consultarElementoMaximo();
	
	/**
	 * Retorna el elemento de menor prioridad. Este elemento se mantiene en la cola de prioridad.
	 */
	public T consultarElementoMinimo();
	
	/**
	 * @Return el n�mero de lementos T en la cola. Valor 0 si la cola est� vac�a.
	 */
	public int darLongitud();
	
	/**
	 * @Return el conjunto de lementos de la cola de prioridad (de mayor a menor)
	 */
	T[] darElementos();
	
	
	
}
