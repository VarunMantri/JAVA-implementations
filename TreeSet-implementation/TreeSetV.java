/**
 * Java Program to implement TreeSet
 * 
 * @author Varun Mantri
 * 
 * Version 1.0
 *
 */
import java.util.Iterator;
import java.util.TreeSet;

public class TreeSetV <T extends Comparable > extends TreeSet
{
	public Vertex<T> v;
	public Vertex<T> root;
	public Vertex<T> pointer;
	public Object []traversedarray;
	public T smallest;
	public T biggest;
	public T localData;
	Vertex<T> resultLeft;
	Vertex<T> resultRight;
	int szeCounter=0;
	
	public TreeSetV()
	{
		v=null;//initializing code
		root=null;
		pointer=null;
		smallest=null;
		biggest=null;
	}
	/**
	 *add method adds objects to treeSet
	 *
	 * @param       data   object to be inserted
	 * 
	 * @return      boolean
	 *
	 */
	public boolean add(Object data)
	{
		T typeChangedData=(T)data;
		v=new Vertex(typeChangedData);//Creating vertex object
		int temp=0;
		if (root==null)
		{
			root=v;
			szeCounter=szeCounter+1;
			smallest=typeChangedData;
			biggest=typeChangedData;
			return true;
			
		}
		else
		{
			pointer=root;
			if(iterativeInsert(pointer)==1)
			{
				szeCounter=szeCounter+1;
				if (typeChangedData.compareTo(smallest)<0)
				{
					smallest=typeChangedData;
				}
				else if(typeChangedData.compareTo(biggest)>0)
				{
					biggest=typeChangedData;
				}
				return true;
			}
		}
		return false;
	}
	/**
	 *iterator method return iterator object
	 *
	 * @return      iterator
	 *
	 */
	public Iterator iterator()
	{
		traversedarray=new Object[szeCounter];
		inorderTraversalCounterIter(root,0);
		return new IteratorV(szeCounter,traversedarray);
	}
	/**
	 *helper insert method for adding objects to treeset
	 *
	 * @param       pointer   pointer to the root node and later carries other objects 
	 * 
	 * @return      1 or 0
	 *
	 */
	public int iterativeInsert(Vertex<T> pointer)
	{
		int check=0;
		localData=v.data;
		int result;
		while(check!=1)
		{
			result=localData.compareTo(pointer.data);
			if(result>0)
			{
				resultRight=pointer.right;
				if(resultRight==null)
				{
					pointer.right=v;
					check=1;
				}
				pointer=resultRight;
			}
			else if(result<0)
			{
				resultLeft=pointer.left;
				if(resultLeft==null)
				{
					pointer.left=v;
					check=1;
				}
				pointer=resultLeft;
			}
			else if(result==0)
			{
				return 0;
			}
		}
		return 1;
		
	}
	/**
	 *contains method checks if the object is present in the Treeset
	 *
	 * @param       data   object to be inserted
	 * 
	 * @return      boolean
	 *
	 */
	public boolean contains(Object Chdata)
	{
		T typeChdata=(T)Chdata;
		if(root==null)
		{
			return false;
		}
		else if(typeChdata.compareTo(smallest)<0 || typeChdata.compareTo(biggest)>0)
		{
			return false;
		}
		else
		{
			if(helpperSearch(typeChdata,root)==1)
			{
				return true;
			}
			return false;
		}
		
	}
	/**
	 *this method is a helper funtion for the contains method
	 *
	 * @param       Chdata   object to be inserted
	 *
	 * @param       pointer   pointer to rot node and later holds the required value
	 * 
	 * @return      1 or 0
	 *
	 */
	public int helpperSearch(T Chdata, Vertex<T> pointer)
	{
		T resultData=pointer.data;
		resultRight=pointer.right;
		resultLeft=pointer.left;
		int result=Chdata.compareTo(resultData);
		if (result==0)
		{
			return 1;
		}
		else if(result>0)
		{
			if(resultRight==null)
			{
				return 0;
			}
			return helpperSearch(Chdata,resultRight);
		}
		else if(result<0)
		{
			if(resultLeft==null)
			{
				return 0;
			}
			return helpperSearch(Chdata,resultLeft);
		}
		return 0;
	}
	/**
	 *checks if the tree set is empty
	 * 
	 * @return      boolean
	 *
	 */
	public boolean isEmpty()
	{
		if(root==null)
		{
			return true;
		}
		return false;
		
	}
	/**
	 *this method return size
	 *
	 * @return      size value
	 *
	 */
	public int size()
	{
		return szeCounter;
	}
	/**
	 *method for inorder traversal to maintain natural order
	 *
	 * @param       pointer   pointer to rot node and later holds the required value
	 *
	 * @param       pointer   counter that counts the number of elements
	 * 
	 * @return      1 or 0
	 *
	 */
	public int inorderTraversalCounterIter(Vertex <T> pointer,int counter)
	{
		if(pointer==null)
		{
			return counter;
		}
		counter=inorderTraversalCounterIter(pointer.left,counter);
		traversedarray[counter]=pointer.data;
		counter++;
		counter=inorderTraversalCounterIter(pointer.right,counter);
		return counter;
	}
	/**
	 * method to clear the tree set oof al the values
	 *
	 */
	public void clear()
	{
		this.v=null;//initializing code
		this.root=null;
		this.pointer=null;
		this.szeCounter=0;
		traversedarray=null;
	}

}
