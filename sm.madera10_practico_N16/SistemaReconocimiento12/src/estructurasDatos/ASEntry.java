package estructurasDatos;

import java.io.Serializable;

public class ASEntry <K, V> implements Comparable <ASEntry<K, V>>, Serializable
{
	// -----------------------------------------------------------------
	// Atributes
	// -----------------------------------------------------------------

	//Represents the key of the entry
	private K key;

	//Represents the value of the entry
	private V value;

	//    //Represents the next element of the table
	 private ASEntry next;

	// -----------------------------------------------------------------
	// Constructor
	// -----------------------------------------------------------------
	/**
	 * Initialize a new entry with an entry and a value 
	 * @param k key value for the entry
	 * @param v value for the entry
	 */
	public ASEntry(K key, V value)
	{		
		this.key = key;
		this.value = value;
		//		next = null;
	}

	public ASEntry()
	{

	}

	/**
	 * Returns the key value of an entry
	 * @return key the key of an entry
	 */
	public K getKey() 
	{
		return  key;
	}

	/**
	 * Modify the key of an entry with a k value
	 * @param k the new value of the key
	 */
	public void setKey(K k) 
	{
		key = k;
	}

	/**
	 * returns the value of an entry
	 * @return the value of an entry
	 */
	public V getValue() 
	{
		return value;
	}

	/**
	 * modify the value of an entry with a v parameter
	 * @param v the new value of the value of an entry
	 */
	public void setValue(V v) 
	{
		value = v;
	}

	//	//This method returns the entry next to the actual one
	public ASEntry getNext()
	{
		return next;
	}

	//	/**
	//	 * Modify the next entry to the actual one
	//	 * @param next the entry next to the actual one
	//	 */
	public void setNext(ASEntry<K,V> next)
	{
		this.next = next;
	}

	/**
	 * Compares this entry with the object in argument. 
	 * @return > 0 if the key of this is more than the key of the argument.
	 * 		   < 0 if the key of this is less than the key of the argument.
	 * 		   0 if the key of this is the same as the key of the argument.
	 */
	public int compareTo(ASEntry<K, V> entry) 
	{
		return (((Comparable<K>) key).compareTo((K) entry.getKey()));
	}
}
