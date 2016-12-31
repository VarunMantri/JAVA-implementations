
/**
 * Java Program to implement TreeSet
 * 
 * @author Varun Mantri
 * 
 * Version 1.0
 *
 */
import java.util.Iterator;

public class IteratorV implements Iterator
{
	int counter=-1;
	int sze;
	Object []array;
	public IteratorV(int sze,Object []array)
	{
		this.sze=sze;
		this.array=array;
	}
	/**
	 * method that returns true if next object is present and false otherwise
	 * 
	 * @return      boolean
	 *
	 */
	public boolean hasNext()
	{
		if(counter==(sze-1))
		{
			return false;
		}
		counter++;
		return true;
	}
	/**
	 * method that returns the next object present
	 *
	 * @return      object
	 *
	 */
	public Object next() {
		// TODO Auto-generated method stub
		
		return array[counter];
	}
}
