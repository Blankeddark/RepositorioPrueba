package estructurasDatos;

import java.util.Iterator;

public class IteratorASortedList<T> implements Iterator<T> {

	private ASNodo next;
	private ASNodo previous;
	private ASortedList list;
	private ASEntry ae;

	public IteratorASortedList(ASortedList list)
	{
		//this.previous = previous;
		this.list = list;
		next = list.darPrimero();
		previous = null;
		ae = new ASEntry();
	}

	public boolean hasNext() {

		if (next != null)
		{
			return true;
		}

		return false;
	}

	public T next() 
	{
		previous = next;

		if (next != null)
		{
			next = next.getNext();
		}

		if(previous.getData().getClass().equals(ae.getClass()))
		{
			return (T) ((ASEntry) previous.getData()).getValue();
		}
		else
			return (T) previous.getData();

	}

	public T previous()
	{
		next = previous;

		if (previous != null)
		{
			previous = previous.getPrevious();
		}

		return (T) next.getData();
	}

	/**
	 * @return the next
	 */
	public ASNodo getNext() {
		return next;
	}

	/**
	 * @return the previous
	 */
	public ASNodo getPrevious() {
		return previous;
	}

}
