/** 
 * Player class for creating diffrenet player instances for different clients
 * 
 * @author      Varun Rajiv Mantri 
 * 
 */
public class Player 
{
	String name;
	int [][]board=new int[10][10];
	int [][]hitBoard=new int[10][10];
	int hitcounter=0;
	
	public Player()
	{
		//initializing the board
		for(int i=0;i<10;i++)
		{
			for(int j=0;j<10;j++)
			{
				board[i][j]=0;
			}
		}
	}
}
