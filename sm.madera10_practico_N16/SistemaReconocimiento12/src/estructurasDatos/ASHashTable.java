package estructurasDatos;

import java.io.Serializable;
import java.util.Iterator;

public class ASHashTable<K extends Comparable <K>, V> implements IASHashTable<K,V>, Serializable, Iterable 
{  
	// -----------------------------------------------------------------
	// Atributes
	// -----------------------------------------------------------------

	//Represents the maximum value allowed to the load factor
	private final static double MAXIMUMLOADFACTOR = 0.75;

	//Represents the optional minimum size of the hashTable
	private final static int SIZE = 1553;

	//Represents the actual load factor of the hash table
	private double actualLoadFactor;

	//Represents the array of sorted lists that contains the entries of the hash table
	private ASortedList <ASEntry <K,V> >[] entries;

	//Represents the number of elements of the hash table
	private int elemCount;

	//Represents the capacity of the table
	private int tableSize;

	//Represents the number of collisions of the hash table
	private int collisions;

	//Represents the number of rehashes that the hash table have had
	private int reHashes;

	//Keys stored
	private ASList<K> keySet;

	// -----------------------------------------------------------------
	// Constructors
	// -----------------------------------------------------------------
	public ASHashTable(int size )
	{   
		entries = new ASortedList [size];
		tableSize = size;
		elemCount = 0;
		actualLoadFactor = 0;
		collisions = 0;
		reHashes = 0;
		keySet = new ASList<K>((int) (size * MAXIMUMLOADFACTOR));
	}



	// -----------------------------------------------------------------
	// Methods
	// -----------------------------------------------------------------

	/**
	 * Method that returns the index value within the hash table of an specific key
	 * @param key the key that is going to be converted into an index of the array of sorted lists
	 * @return the index value of the key
	 */
	public int getHashIndex( K key )
	{

		int size = entries.length;

		int hashCode = Math.abs(key.hashCode());

		int hashIndex = hashCode % size;

		if( hashIndex < 0 )
		{
			hashIndex += size;
		}

		return hashIndex;
	}

	/**
	 * Returns the list in a position with a key
	 */

	public ASortedList <ASEntry <K,V> > getListOf(K key)
	{
		return entries[getHashIndex(key)];
	}

	/**
	 * Sets the list in a position to the value in argument
	 */
	public void setListOf(K key, ASortedList< ASEntry<K, V> > value)
	{
		entries[getHashIndex(key)] = value;
	}

	/**
	 * Method that modify the actual load factor of the hash table
	 */
	protected void updateLoadFactor()
	{
		actualLoadFactor = elemCount/(double)tableSize;
	}

	/**
	 * Method that add an entry to the hash table
	 * @param key the key of the element to be added
	 * @param value the value of the element to be added
	 */
	public void put(K key, V value) 
	{
		int hashIndex = getHashIndex(key);
		ASEntry<K,V> newEntry = new ASEntry<K,V>(key,value);

		if(actualLoadFactor > MAXIMUMLOADFACTOR)
		{
			rehash();		
		}

		if(entries[hashIndex] != null)
		{
			collisions++;	
			//append to linked list	
			entries[hashIndex].add(newEntry);
		}
		else
		{
			entries[hashIndex] = new ASortedList(); //TODO
			entries[hashIndex].add(newEntry);
		}
		
//		if (!keySet.containsBinary(key))
//		{
//			keySet.addSorted(key); 	
//		}
		
		keySet.add(key);
		elemCount++;
		updateLoadFactor();

	}

	/**
	 * Method that add capacity to the table depending of the value of the actual load factor
	 */
	protected void rehash()
	{ 

		reHashes++;
		ASortedList<ASEntry <K,V>>[] oldHashTable =  entries;
		entries = new ASortedList[obtainPrimeNumber(tableSize*2)];
		tableSize = obtainPrimeNumber(tableSize * 2);

		for(int i=0; i<oldHashTable.length; i++)

		{
			ASortedList <ASEntry <K,V> > node = oldHashTable[i];
			entries[i] = node;

		}
	}

	/**
	 * Method that get an entry of the table depending of his key value
	 * @param key the key of the value that we are going to get
	 */
	public V get(K k) 
	{  
		int hashIndex = getHashIndex(k);
		V rta = null;		

		ASortedList <ASEntry<K,V>> hashNode = entries[hashIndex];

		if(hashNode != null)
		{  
			boolean flag = false;
			ASNodo actual = hashNode.darPrimero();
			for(int i = 0; i < hashNode.size() && !flag; i++)
			{
				K key = ((ASEntry<K, V>) actual.getData()).getKey();
				if(key.compareTo(k) == 0)
				{
					rta = (V) ((ASEntry<K, V>) actual.getData()).getValue();
					flag = true;
				}
				else
				{
					actual = actual.getNext();
				}

			}
		}

		return rta;
	}


	/**
	 * Method that remove an entry of the table according to his key value
	 * @ key k key of the value that we are going to remove
	 */
	public void remove(K k) throws Exception 
	{
		int hashIndex =  getHashIndex(k);
		ASortedList <ASEntry<K,V>> hashNode = entries[hashIndex];

		if(hashNode.size() == 0)
		{  
			throw new Exception ("No se puede eliminar porque no contiene ningún elemento");
		}

		else
		{
			V v = get(k);
			ASEntry<K,V> deleted = new ASEntry<K,V>(k,v);
			hashNode.remove(deleted);
			elemCount--;
			keySet.remove(k);
			updateLoadFactor();
		}
	}


	//Method that return the capacity of the table
	public int size() 
	{
		return tableSize;
	}

	//Method that returns the number of elements of the table
	public int getElementCount()
	{
		return elemCount;
	}

	//Method that resets the table
	public void clear()
	{
		elemCount = 0;
	}

	/**
	 * Method that returns true if the table contains an entry with an specific key
	 * @param key the key of the element that we are looking for
	 * @return true if the table contains the key, false if not
	 */
	public boolean containsKey(K key)
	{
		int hashIndex = getHashIndex(key);
		boolean rta = false;
		ASortedList <ASEntry<K,V>> hashNode = entries[hashIndex];
		
		if (hashNode != null)
		{
			if(hashNode.size() > 0)
			{  
				ASNodo actual = hashNode.darPrimero();
				boolean flag = false;
				for(int i = 0; i < hashNode.size() && !flag; i++)
				{
					K key2 = ((ASEntry<K, V>) actual.getData()).getKey();
					if(key2.compareTo(key) == 0)
					{
						rta = true;
						flag = true;
					}
					else
					{
						actual = actual.getNext();
					}
				}
			}
		}

		else
		{
			rta = false;
		}
		return rta;

	}
		
	/**
	 * @return keySet of the ASHashTable.
	 */
	public ASList<K> getKeySet()
	{
		return keySet;
	}
	
	

	/**
	 * Method that says if the table is empty or not
	 * @return true if the table is empty, false if not
	 */
	public boolean isEmpty() 
	{   
		boolean rta = false;

		if(elemCount == 0)
		{
			rta = true;	
		}

		return rta;
	}



	public IteratorHT iterator()
	{
		return new IteratorHT(this);
	}

   
	/**
	 * Method that obtains a prime number in order to reduce the
	 * number of collisions at the moment of doing a rehash
	 */
	public int obtainPrimeNumber(int size)
	{   
		int rta = size;
		boolean termino = false;
		
		for(int i = rta; !termino; i++ )
		{  
		   String test = Integer.toString(rta);
		   int testSize = test.length();
		   char lastDigit = test.charAt(testSize - 1);
		   
		   if(lastDigit == 7)
		   {
			   break;
		   }
		   
		   rta++; 
		}
		
		
		for(int i = rta; !termino; i++ )
		{ 
		  
			if(size% 2 == 0 || size % 3 == 0 || size % 5 == 0 || size % 7 == 0 || size % 9 == 0)
			{
				   termino = false;
		    }
			   
		    else
			{
				   termino = true;
			}
			
			rta+= 10;
		}
		
		return rta;
	}
//	public Iterator iterator() {
//		
//	}
}

