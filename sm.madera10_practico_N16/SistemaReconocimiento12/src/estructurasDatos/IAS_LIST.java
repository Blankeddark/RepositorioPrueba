package estructurasDatos;

public interface IAS_LIST <T extends Comparable>
{   
	 /**
	  * adds a new element to the array in his respectively position. <br>
	  * <b>post: </b> a new element have been added to the array according to his relation with other elements of the T type.<br>
	  * @param o Element to add<br>
	  */
	public void add(T op);
	
	/**
    * Method that clear the array, in other words, resets the array.
	* <b>post: </b> The array have been cleared.<br>
	*/
	public void clear();
	
	/**
	* Method that say if an specific object is inside the array or not.
	* @param o Element to be search.
	* @return true if the array contains o , or false if not.
	*/
	public boolean contains(T o);
	
	/**
	* Method that say if the array is empty or not.
	* @return true if the size of the array is 0 and true if not.
	*/
	public boolean isEmpty();
	
	/**
	* Remove an specific element from the array <br>
    * <b>post: </b> An element have been deleted from the array.<br>
	* @param o Element to delete<br>
	* @throws Exception if the array is empty and the user wants to delete something  <br>
	*/
	public void remove(T o) throws Exception;
	
	/**
	* Method that returns the number of elements of the aray. <br>
	* @return returns the number of elements of the array, in other words, the size<br>
	*/
	public int size();
	
	/**
	*  Returns a string with the value of the array.
	*  @return returns a string that contains the value of each element of the array
	*/
	public String toString();
}
