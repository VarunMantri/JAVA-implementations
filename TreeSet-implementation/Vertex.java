/**
 * Java Program to implement TreeSet
 * 
 * @author Varun Mantri
 * 
 * Version 1.0
 *
 */
public class Vertex <T extends Comparable>
{
	T data;
	Vertex<T> left;
	Vertex<T> right;
	
	public Vertex(T data)
	{
		this.data=data;
		this.left=null;
		this.right=null;
	}

}
