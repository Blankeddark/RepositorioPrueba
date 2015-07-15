package estructurasDatos;

/**
* Interface which represents a hash table that stores elements from <code>V</code> type that
* that are associated with  <code>k</code> type keys. The elements of 
* <code>k</code> type must hace the methods <code> equals</code> and <code>hashCode</code> correctly defined
* @param <k> Type of the key associated with the elements
* @param <V> The type of the elements to be stored
*/

public interface IASHashTable <K,V>
{
	/**
	* Adds an entry to the table with the specified key and value. 
	* If there is another element with this value, it will be replaced.
	* @param key the key of the element
	* @param value the value of the element to be added.
	*/
	public void put( K key, V value );

	/**
	* Returns the element associated with an specific key
	* @param k the key from the element that we are looking for.
	* @return the element associated with the key  or <code>null</code> if there is not an association to this key.
	*/
	public V get( K k );

	/**
	* Delete the element that is associated with an specific key.
	 * @throws Exception 
	* @paramk the key from the element that we want to eliminate.
	*/
	public void remove( K llave ) throws Exception;

	/**
	* returns the number of elements that are contained by the hash table
	* @return the size of the hashtable
	*/
	public int size( );
	
	/**
	* Method that say if the table is empty or not.
	* @return true if the size of the array is 0 and true if not.
	*/
	public boolean isEmpty();

	
}
