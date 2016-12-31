import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/** 
 * Maze class determines if a path exists through the given maze and prints out the same 
 * 
 * @author      Varun Rajiv Mantri 
 */
public class Maze 
{
	/**
	 *traverseMaze traverses the maze and eliminates incorrect paths.
	 *
	 * @param       [][]array   it holds the incoming array of elements
	 * 
	 * @param		counterC    holds the number of columns
	 * 
	 * @param		counterR    holds the number of rows
	 * 
	 * @return              returns a binary value
	 *
	 */
	public char[][] traverseMaze(char [][] array,int counterC,int counterR)
	{
		//iteration started from 1 on purpose
		int flag=0;					//flag that supplements 'markCompletion'
		int markCompletion=0;		//flag that acts as a termination condition for while loop
		boolean marker1;
		boolean marker2;
		boolean marker3;
		boolean marker4;
		boolean marker5;
		while(markCompletion!=1)
		{
			flag=0;
			for(int i=1;i<counterR-1;i++)
			{
				for(int j=1;j<counterC-1;j++)
				{
					//All the conditions to be checked
					//---------------------------------------------------------------
					marker2=((array[i][j+1]=='#' || array[i][j+1]==' ') && (array[i+1][j]=='#' || array[i+1][j]==' ') && (array[i-1][j]=='#' || array[i-1][j]==' '));
					marker3=((array[i][j-1]=='#' || array[i][j-1]==' ') && (array[i+1][j]=='#' || array[i+1][j]==' ') && (array[i-1][j]=='#' || array[i-1][j]==' '));
					marker4=((array[i+1][j]=='#' || array[i+1][j]==' ') && (array[i][j+1]=='#' || array[i][j+1]==' ') && (array[i][j-1]=='#' || array[i][j-1]==' '));
					marker5=((array[i-1][j]=='#' || array[i-1][j]==' ') && (array[i][j+1]=='#' || array[i][j+1]==' ') && (array[i][j-1]=='#' || array[i][j-1]==' '));
					marker1=( marker2 || marker3 || marker4 || marker5 );
					//---------------------------------------------------------------
					if(array[i][j]=='.' && marker1==true)
					{
						array[i][j]=' ';
						flag=1;
					}
				}
			}
			if(flag==0)
			{
				markCompletion=1;
			}
		}
		return array;		//returns the array containg the solution
	}

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		Maze obj=new Maze();			//Creating an object of Maze class
		int counterC=0;					//holds the 2D array height
		int counterR=0;					//holds the 2D array width
		int iterator=0;					//Its an iterator
		int flagSoln=0;					//flag that marks successful exit from maze
		String filePath="mymaze_new.txt";
		try 
		{
			Scanner iReader1=new Scanner(new File(filePath));
			Scanner iReader2=new Scanner(new File(filePath));
			String line="";
			while(iReader1.hasNextLine())
			{
				line=iReader1.nextLine();
				counterR=counterR+1;
			}
			counterC=line.length();
			char [][]array=new char[counterR][counterC];//Making an array that holds the given maze
			while(iReader2.hasNextLine())
			{
				line=iReader2.nextLine();
				for(int j=0;j<counterC;j++)
				{
					if(line.charAt(j)==' ')
					{
						array[iterator][j]='.';
					}
					else
					{
						array[iterator][j]=line.charAt(j);
					}
				}
				iterator=iterator+1;//Iterates 
			}
			//calling the function traverse
			array=obj.traverseMaze(array, counterC, counterR);
			//Checking if the maze has been completely traversed
			
			for(int i=0;i<counterR;i++)
			{
				if(array[i][counterC-1]=='.')
				{
					if(array[i][counterC-2]==' ')
					{
						array[i][counterC-1]=' ';
					}
					else if(array[i][counterC-2]=='#')
					{
						array[i][counterC-1]='#';
					}
					else
					{
						flagSoln=1;
					}
				}
			}
			for(int i=0;i<counterC;i++)
			{
				if(array[counterR-1][i]=='.')
				{
					if(array[counterR-2][i]==' ')
					{
						array[counterR-1][i]=' ';
					}
					else if(array[counterR-2][i]=='#')
					{
						array[counterR-1][i]='#';
					}
					else
					{
						flagSoln=1;
					}
				}
			}
			//Printing out results
			//---------------------------------------------------------
			for(int i=0;i<counterR;i++)
			{
				for(int j=0;j<counterC;j++)
				{
					System.out.print(array[i][j]);
				}
				System.out.println("");
			}
			//---------------------------------------------------------
			if(flagSoln==1)
			{
				System.out.println("Maze Solved!!");
			}
			else
			{
				System.out.println("I am trapped!! No solution to this maze.");
			}
		}
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	

	}

}
