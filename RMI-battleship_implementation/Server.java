import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
/** 
 * Server class whose insatnce is shared using RMI
 * 
 * @author      Varun Rajiv Mantri 
 */
public class Server extends UnicastRemoteObject implements ServerMethods
{

	static Player playerA;	//object for player A
	static Player playerB;//Object for player B
	static int precount=0;
	static int lock=1;//is 1 for playerA and -1 for the other
	static int game=0;
	
	protected Server() throws RemoteException 
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		playerA=new Player();
		playerB=new Player();
		try {
			Server server=new Server();
			Naming.rebind("Server", server);
			System.out.println("Server administrative console view.....\n");
			System.out.println("Server started\n");
			System.out.println("Server binding succeeded.....\n");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	/**
	 *method to set player name
	 *
	 * @param       name	 	player name
	 *
	 * @param       id 			player id
	 */
	@Override
	public void setName(String name,int id) throws RemoteException 
	{
		// TODO Auto-generated method stub
		if(id==1)
		{
			playerA.name=name;
		}
		else if(id==2)
		{
			playerB.name=name;
		}
		
	}
	/**
	 *method peek board
	 *
	 * @param       id 			player id
	 */
	@Override
	public int[][] peekBoard(int id)throws RemoteException
	{
		if(id==1)
		{
		  return playerB.hitBoard;
		}
		else if(id==2)
		{
		  return playerA.hitBoard;
		}
		return new int[10][10];
	}
	/**
	 *method to peek self board
	 *
	 * @param       name	 	player name
	 *
	 * @param       id 			player id
	 */
	@Override
	public int[][] peekSettingBoard(int id)throws RemoteException
	{
		if(id==1)
		{
		  return playerA.board;
		}
		else if(id==2)
		{
		  return playerB.board;
		}
		return new int[10][10];//default return 
	}
	/**
	 *method to set carrier ship position
	 *
	 * @param       input	 	string entered by user
	 *
	 * @param       id 			player id
	 */
	@Override
	public boolean setCarrier(String input,int id) throws RemoteException 
	{
		// TODO Auto-generated method stub
		int x,y,o;
		if(input.length()!=5)
		{
		  return false;
		}
		x=Character.getNumericValue(input.charAt(0));
		y=Character.getNumericValue(input.charAt(2));
		o=Character.getNumericValue(input.charAt(4));
		//System.out.println(o);
		Player temp;
		if(id==1)
		{
			temp=playerA;
		}
		else
		{
			temp=playerB;
		}
		if(o==1)
		{
			if(valVertical(x,y,5,temp))
			{
			}
			else
			{
				return false;
			}
		}
		else
		{
			if(valHorizontal(x,y,5,temp))
			{
			}
			else
			{
				return false;
			}
		}
		if(id==1)
		{
			if(o==1)
			{
					for(int j=y;j>y-5;j--)
					{
						playerA.board[j][x]=1;
						if(x!=0 && x!=9)
						{
							playerA.board[j][x+1]=2;
							playerA.board[j][x-1]=2;
						}
						else
						{
							if(x==0)
							{
								playerA.board[j][x+1]=2;
							}
							else if(x==9)
							{
								playerA.board[j][x-1]=2;
							}
						}
						//logic for this is to be written
					}
					if(y!=9 && (y-5)!=0)
					{
						playerA.board[y+1][x]=2;
						playerA.board[y-5][x]=2;
					}
					else
					{
						if(y==9)
						{
							playerA.board[y-5][x]=2;
						}
						else if((y-5)==0)
						{
							playerA.board[y+1][x]=2;
						}
					}
					//printBoard(id);
					return true;
			}
			else if(o==0)
			{
				for(int j=x;j<x+5;j++)
				{
					playerA.board[y][j]=1;
					//logic for this is to be written
					if(y!=0 && y!=9)
					{
						playerA.board[y+1][j]=2;
						playerA.board[y-1][j]=2;
					}
					else
					{
						if(y==0)
						{
							playerA.board[y+1][j]=2;
						}
						else if(y==9)
						{
							playerA.board[y-1][j]=2;
						}
					}
				}
				if(x!=0 && (x+5)!=9)
				{
					playerA.board[y][x-1]=2;
					playerA.board[y][x+5]=2;
				}
				else
				{
					if((x+5)==9)
					{
						playerA.board[y][x-1]=2;
					}
					else if(x==0)
					{
						playerA.board[y][x+5]=2;
					}
				}
				//printBoard(id);
				return true;
			}
		}
		else if(id==2)
		{
			if(o==1)
			{
					for(int j=y;j>y-5;j--)
					{
						playerB.board[j][x]=1;
						//logic for this is to be written
						if(x!=0 && x!=9)
						{
							playerB.board[j][x+1]=2;
							playerB.board[j][x-1]=2;
						}
						else
						{
							if(x==0)
							{
								playerB.board[j][x+1]=2;
							}
							else if(x==9)
							{
								playerB.board[j][x-1]=2;
							}
						}
					}
					if(y!=9 && (y-5)!=0)
					{
						playerB.board[y+1][x]=2;
						playerB.board[y-5][x]=2;
					}
					else
					{
						if(y==9)
						{
							playerB.board[y-5][x]=2;
						}
						else if((y-5)==0)
						{
							playerB.board[y+1][x]=2;
						}
					}
					//printBoard(id);
					return true;
			}
			else if(o==0)
			{
				for(int j=x;j<x+5;j++)
				{
					playerB.board[y][j]=1;
					//logic for this is to be written
					if(y!=0 && y!=9)
					{
						playerB.board[y+1][j]=2;
						playerB.board[y-1][j]=2;
					}
					else
					{
						if(y==0)
						{
							playerB.board[y+1][j]=2;
						}
						else if(y==9)
						{
							playerB.board[y-1][j]=2;
						}
					}
				}
				if(x!=0 && (x+5)!=9)
				{
					playerB.board[y][x-1]=2;
					playerB.board[y][x+5]=2;
				}
				else
				{
					if((x+5)==9)
					{
						playerB.board[y][x-1]=2;
					}
					else if(x==0)
					{
						playerB.board[y][x+5]=2;
					}
				}
				//printBoard(id);
				return true;
			}
		}
		return false;
	}
	/**
	 *method to set Battleship ship position
	 *
	 * @param       input	 	string entered by user
	 *
	 * @param       id 			player id
	 */
	@Override
	public boolean setBattleship(String input,int id) throws RemoteException
	{
		// TODO Auto-generated method stub
		int x,y,o;
		if(input.length()!=5)
		{
		  return false;
		}
		x=Character.getNumericValue(input.charAt(0));
		y=Character.getNumericValue(input.charAt(2));
		o=Character.getNumericValue(input.charAt(4));
		Player temp;
		if(id==1)
		{
			temp=playerA;
		}
		else
		{
			temp=playerB;
		}
		if(o==1)
		{
			if(valVertical(x,y,4,temp))
			{
			}
			else
			{
				return false;
			}
		}
		else
		{
			if(valHorizontal(x,y,4,temp))
			{
			}
			else
			{
				return false;
			}
		}
		if(id==1)
		{
			if(o==1)
			{
					for(int j=y;j>y-4;j--)
					{
						playerA.board[j][x]=1;
						//logic for this is to be written
						if(x!=0 && x!=9)
						{
							playerA.board[j][x+1]=2;
							playerA.board[j][x-1]=2;
						}
						else
						{
							if(x==0)
							{
								playerA.board[j][x+1]=2;
							}
							else if(x==9)
							{
								playerA.board[j][x-1]=2;
							}
						}}
						if(y!=9 && (y-4)!=0)
						{
							playerA.board[y+1][x]=2;
							playerA.board[y-4][x]=2;
						}
						else
						{
							if(y==9)
							{
								playerA.board[y-4][x]=2;
							}
							else if((y-4)==0)
							{
								playerA.board[y+1][x]=2;
							}
						}
					//printBoard(id);
					return true;
			}
			else if(o==0)
			{
				for(int j=x;j<x+4;j++)
				{
					playerA.board[y][j]=1;
					if(y!=0 && y!=9)
					{
						playerA.board[y+1][j]=2;
						playerA.board[y-1][j]=2;
					}
					else
					{
						if(y==0)
						{
							playerA.board[y+1][j]=2;
						}
						else if(y==9)
						{
							playerA.board[y-1][j]=2;
						}
					}
					//logic for this is to be written
				}
				if(x!=0 && (x+4)!=9)
				{
					playerA.board[y][x-1]=2;
					playerA.board[y][x+4]=2;
				}
				else
				{
					if((x+4)==9)
					{
						playerA.board[y][x-1]=2;
					}
					else if(x==0)
					{
						playerA.board[y][x+4]=2;
					}
				}
				//printBoard(id);
				return true;
			}
		}
		else if(id==2)
		{
			if(o==1)
			{
					for(int j=y;j>y-4;j--)
					{
						playerB.board[j][x]=1;
						//logic for this is to be written
						if(x!=0 && x!=9)
						{
							playerB.board[j][x+1]=2;
							playerB.board[j][x-1]=2;
						}
						else
						{
							if(x==0)
							{
								playerB.board[j][x+1]=2;
							}
							else if(x==9)
							{
								playerB.board[j][x-1]=2;
							}
						}
					}
					if(y!=9 && (y-4)!=0)
					{
						playerB.board[y+1][x]=2;
						playerB.board[y-4][x]=2;
					}
					else
					{
						if(y==9)
						{
							playerB.board[y-4][x]=2;
						}
						else if((y-4)==0)
						{
							playerB.board[y+1][x]=2;
						}
					}
					//printBoard(id);
					return true;
			}
			else if(o==0)
			{
				for(int j=x;j<x+4;j++)
				{
					playerB.board[y][j]=1;
					if(y!=0 && y!=9)
					{
						playerB.board[y+1][j]=2;
						playerB.board[y-1][j]=2;
					}
					else
					{
						if(y==0)
						{
							playerB.board[y+1][j]=2;
						}
						else if(y==9)
						{
							playerB.board[y-1][j]=2;
						}
					}
					//logic for this is to be written
				}
				if(x!=0 && (x+4)!=9)
				{
					playerB.board[y][x-1]=2;
					playerB.board[y][x+4]=2;
				}
				else
				{
					if((x+4)==9)
					{
						playerB.board[y][x-1]=2;
					}
					else if(x==0)
					{
						playerB.board[y][x+4]=2;
					}
				}
			//printBoard(id);
				return true;
			}
		}
		return false;
	}
	/**
	 *method to set Cruiser ship position
	 *
	 * @param       input	 	string entered by user
	 *
	 * @param       id 			player id
	 */
	@Override
	public boolean setCruiser(String input,int id) throws RemoteException 
	{
		// TODO Auto-generated method stub
		int x,y,o;
		if(input.length()!=5)
		{
			return false;
		}
		x=Character.getNumericValue(input.charAt(0));
		y=Character.getNumericValue(input.charAt(2));
		o=Character.getNumericValue(input.charAt(4));
		Player temp;
		if(id==1)
		{
			temp=playerA;
		}
		else
		{
			temp=playerB;
		}
		if(o==1)
		{
			if(valVertical(x,y,3,temp))
			{
			}
			else
			{
				return false;
			}
		}
		else
		{
			if(valHorizontal(x,y,3,temp))
			{
			}
			else
			{
				return false;
			}
		}
		if(id==1)
		{
			if(o==1)
			{
					for(int j=y;j>y-3;j--)
					{
						playerA.board[j][x]=1;
						if(x!=0 && x!=9)
						{
							playerA.board[j][x+1]=2;
							playerA.board[j][x-1]=2;
						}
						else
						{
							if(x==0)
							{
								playerA.board[j][x+1]=2;
							}
							else if(x==9)
							{
								playerA.board[j][x-1]=2;
							}
					}
						//logic for this is to be written
					}
					if(y!=9 && (y-3)!=0)
					{
						playerA.board[y+1][x]=2;
						playerA.board[y-3][x]=2;
					}
					else
					{
						if(y==9)
						{
							playerA.board[y-3][x]=2;
						}
						else if((y-3)==0)
						{
							playerA.board[y+1][x]=2;
						}
					}
					//printBoard(id);
					return true;
			}
			else if(o==0)
			{
				for(int j=x;j<x+3;j++)
				{
					playerA.board[y][j]=1;
					//logic for this is to be written
					if(y!=0 && y!=9)
					{
						playerA.board[y+1][j]=2;
						playerA.board[y-1][j]=2;
					}
					else
					{
						if(y==0)
						{
							playerA.board[y+1][j]=2;
						}
						else if(y==9)
						{
							playerA.board[y-1][j]=2;
						}
					}
				}
				if(x!=0 && (x+3)!=9)
				{
					playerA.board[y][x-1]=2;
					playerA.board[y][x+3]=2;
				}
				else
				{
					if((x+3)==9)
					{
						playerA.board[y][x-1]=2;
					}
					else if(x==0)
					{
						playerA.board[y][x+3]=2;
					}
				}
				//printBoard(id);
				return true;
			}
		}
		else if(id==2)
		{
			if(o==1)
			{
					for(int j=y;j>y-3;j--)
					{
						playerB.board[j][x]=1;
						//logic for this is to be written
						if(x!=0 && x!=9)
						{
							playerB.board[j][x+1]=2;
							playerB.board[j][x-1]=2;
						}
						else
						{
							if(x==0)
							{
								playerB.board[j][x+1]=2;
							}
							else if(x==9)
							{
								playerB.board[j][x-1]=2;
							}
					}}
						if(y!=9 && (y-3)!=0)
						{
							playerB.board[y+1][x]=2;
							playerB.board[y-3][x]=2;
						}
						else
						{
							if(y==9)
							{
								playerB.board[y-3][x]=2;
							}
							else if((y-3)==0)
							{
								playerB.board[y+1][x]=2;
							}
						}
					//printBoard(id);
					return true;
			}
			else if(o==0)
			{
				for(int j=x;j<x+3;j++)
				{
					playerB.board[y][j]=1;
					//logic for this is to be written
					if(y!=0 && y!=9)
					{
						playerB.board[y+1][j]=2;
						playerB.board[y-1][j]=2;
					}
					else
					{
						if(y==0)
						{
							playerB.board[y+1][j]=2;
						}
						else if(y==9)
						{
							playerB.board[y-1][j]=2;
						}
					}
				}
				if(x!=0 && (x+3)!=9)
				{
					playerB.board[y][x-1]=2;
					playerB.board[y][x+3]=2;
				}
				else
				{
					if((x+3)==9)
					{
						playerB.board[y][x-1]=2;
					}
					else if(x==0)
					{
						playerB.board[y][x+3]=2;
					}
				}
				//printBoard(id);
				return true;
			}
		}
		return false;
	}
	/**
	 *method to set Destroyer ship position
	 *
	 * @param       input	 	string entered by user
	 *
	 * @param       id 			player id
	 */
	@Override
	public boolean setDestroyer(String input,int id) throws RemoteException {
		// TODO Auto-generated method stub
		int x,y,o;
    if(input.length()!=5)
    {
      return false;
    }
		x=Character.getNumericValue(input.charAt(0));
		y=Character.getNumericValue(input.charAt(2));
		o=Character.getNumericValue(input.charAt(4));
		Player temp;
		if(id==1)
		{
			temp=playerA;
		}
		else
		{
			temp=playerB;
		}
		if(o==1)
		{
			if(valVertical(x,y,2,temp))
			{
			}
			else
			{
				return false;
			}
		}
		else
		{
			if(valHorizontal(x,y,2,temp))
			{
			}
			else
			{
				return false;
			}
		}
		if(id==1)
		{
			if(o==1)
			{
					for(int j=y;j>y-2;j--)
					{
						playerA.board[j][x]=1;
						//logic for this is to be written
						if(x!=0 && x!=9)
						{
							playerA.board[j][x+1]=2;
							playerA.board[j][x-1]=2;
						}
						else
						{
							if(x==0)
							{
								playerA.board[j][x+1]=2;
							}
							else if(x==9)
							{
								playerA.board[j][x-1]=2;
							}
						}
					}
					if(y!=9 && (y-2)!=0)
					{
						playerA.board[y+1][x]=2;
						playerA.board[y-2][x]=2;
					}
					else
					{
						if(y==9)
						{
							playerA.board[y-2][x]=2;
						}
						else if((y-2)==0)
						{
							playerA.board[y+1][x]=2;
						}
					}
					//printBoard(id);
					precount++;
					return true;
			}
			else if(o==0)
			{
				for(int j=x;j<x+2;j++)
				{
					playerA.board[y][j]=1;
					//logic for this is to be written
					if(y!=0 && y!=9)
					{
						playerA.board[y+1][j]=2;
						playerA.board[y-1][j]=2;
					}
					else
					{
						if(y==0)
						{
							playerA.board[y+1][j]=2;
						}
						else if(y==9)
						{
							playerA.board[y-1][j]=2;
						}
					}
				}
				if(x!=0 && (x+2)!=9)
				{
					playerA.board[y][x-1]=2;
					playerA.board[y][x+2]=2;
				}
				else
				{
					if((x+2)==9)
					{
						playerA.board[y][x-1]=2;
					}
					else if(x==0)
					{
						playerA.board[y][x+2]=2;
					}
				}
				//printBoard(id);
				precount++;
				return true;
			}
		}
		else if(id==2)
		{
			if(o==1)
			{
					for(int j=y;j>y-2;j--)
					{
						playerB.board[j][x]=1;
						//logic for this is to be written
						if(x!=0 && x!=9)
						{
							playerB.board[j][x+1]=2;
							playerB.board[j][x-1]=2;
						}
						else
						{
							if(x==0)
							{
								playerB.board[j][x+1]=2;
							}
							else if(x==9)
							{
								playerB.board[j][x-1]=2;
							}
						}
					}
					if(y!=9 && (y-2)!=0)
					{
						playerA.board[y+1][x]=2;
						playerA.board[y-2][x]=2;
					}
					else
					{
						if(y==9)
						{
							playerA.board[y-2][x]=2;
						}
						else if((y-2)==0)
						{
							playerA.board[y+1][x]=2;
						}
					}
					//printBoard(id);
					precount++;
					return true;
			}
			else if(o==0)
			{
				for(int j=x;j<x+2;j++)
				{
					playerB.board[y][j]=1;
					//logic for this is to be written
					if(y!=0 && y!=9)
					{
						playerB.board[y+1][j]=2;
						playerB.board[y-1][j]=2;
					}
					else
					{
						if(y==0)
						{
							playerB.board[y+1][j]=2;
						}
						else if(y==9)
						{
							playerB.board[y-1][j]=2;
						}
					}
				}
				if(x!=0 && (x+2)!=9)
				{
					playerB.board[y][x-1]=2;
					playerB.board[y][x+2]=2;
				}
				else
				{
					if((x+2)==9)
					{
						playerB.board[y][x-1]=2;
					}
					else if(x==0)
					{
						playerB.board[y][x+2]=2;
					}
				}
				//printBoard(id);
				precount++;
				return true;
			}
		}
		return false;
	}
	/**
	 *method that checks if its this id's turn to enter inputs
	 *
	 *
	 * @param       id 			player id
	 */
	@Override
	public int isMyTurn(int id) throws RemoteException 
	{
		// TODO Auto-generated method stub
		if(lock==1 && id==1&& precount==2)
		{
			System.out.println("Client1 polled.....");
			System.out.println("Lock status: acquired");
			return 1;
		}
		else if(lock==-1 && id==2 && precount==2)
		{
			System.out.println("Client2 polled.....");
			System.out.println("Lock Status: acquired");
			return 1;
		}
		if(game!=1)
		{
      
			return 2;
		}
		else
		{
			System.out.println("-----Game Over-----");
			return 5;
		}
	}
	/**
	 *method that checks if its hit
	 *
	 *
	 * @param       input 			string input entered by player
	 */
	@Override
	public int isHit(String input) throws RemoteException 
	{
		// TODO Auto-generated method stub
		int x,y;
		x=Character.getNumericValue(input.charAt(0));
		y=Character.getNumericValue(input.charAt(2));
		if(lock ==1)
		{
			//playerA is playing
			if(playerB.board[y][x]==1)
			{
				playerB.board[y][x]=5;
				playerB.hitBoard[y][x]=9;
				playerA.hitcounter=playerA.hitcounter+1;
				if (playerA.hitcounter==14)
				{
					game=1;
					return 5;
				}
				return 1;
			}
			else
			{
				return 2;
			}
		}
		else if(lock ==-1)
		{
			//playerB is playing
			if(playerA.board[y][x]==1)
			{
				playerA.board[y][x]=5;
				playerA.hitBoard[y][x]=9;
				playerB.hitcounter=playerB.hitcounter+1;
				if (playerB.hitcounter==14)
				{
					game=1;
					return 5;
				}
				return 1;
			}
			else
			{
				return 2;
			}
		}
		return 2;
	}
	/**
	 *method that checks if the entered input is correct
	 *
	 * @param       x 			x co-ordinate
	 *
	 * @param       y 			y co-ordinate
	 *
	 * @param       length 			length of ship
	 * @param       o 			object of the current player
	 */
	public boolean valVertical(int x,int y,int length,Player o)
	{
		int errFlag=0;
		boolean below,top,left,right;
		if(x<0 || x>9 || y<0 || y>9)
		{
			return false;
			//do nothing
		}
		else
		{
			if(y-length>=0)
			{
				for(int i=y;i>y-length;i--)
				{
					if(x!=0)
					{
						if(y-length==0)
						{
							top=true;
						}
						else
						{
							top=(o.board[i-1][x]!=1);
						}
						//checking bottom
						if(y==9)
						{
							below=true;
						}
						else
						{
							below=(o.board[y+1][x]!=1);
						}
						left=(o.board[i][x-1]!=1);
						right=true;
						if(x!=9)
						{
							right=(o.board[i][x+1]!=1);
						}
						if(top && below && left && right)
						{
							//System.out.println(top+""+below+""+right+""+left);
							if(o.board[i][x]!=1 && o.board[i][x]!=2)
							{
								//do nothing
								//System.out.println("shift");
							}
							else
							{
								errFlag=1;
							}
							//do nothing
						}
						else
						{
							errFlag=1;
						}
					}
					else if(x==0)
					{
						if(y-length==0)
						{
							top=true;
						}
						else
						{
							top=(o.board[i-1][x]!=1);
						}
						//checking bottom
						if(y==9)
						{
							below=true;
						}
						else
						{
							below=(o.board[y+1][x]!=1);
						}
						left=true;
						right=(o.board[i][x+1]!=1);
						//System.out.println("left "+left+" right "+right+" top "+top+" below"+below);
						if(top && below && left && right)
						{
							if(o.board[i][x]!=1 && o.board[i][x]!=2)
							{
								//do nothing
							}
							else
							{
								errFlag=1;
							}
						}
						else
						{
							errFlag=1;
						}
					}
					else if(x==9)
					{
						if(y-length==0)
						{
							top=true;
						}
						else
						{
							top=(o.board[i-1][x]==0);
						}
						//checking bottom
						if(y==9)
						{
							below=true;
						}
						else
						{
							below=(o.board[y+1][x]==0);
						}
						left=(o.board[i][x-1]==0);
						right=true;
						if(top && below && left && right)
						{
							if(o.board[i][x]!=1 && o.board[i][x]!=2)
							{
								//do nothing
							}
							else
							{
								errFlag=1;
							}
						}
						else
						{
							errFlag=1;
						}
					}
				}
				if(errFlag==0)
				{
					return true;
				}
				else
				{
					return false;
				}
			}
		}
		return false;
	}
	/**
	 *method that checks if the entered input is correct
	 *
	 * @param       x 			x co-ordinate
	 *
	 * @param       y 			y co-ordinate
	 *
	 * @param       length 			length of ship
	 * @param       o 			object of the current player
	 */
	public boolean valHorizontal(int x,int y,int length,Player o)
	{
		int errFlag=0;
		boolean top,below,left,right;
		if(x<0 || x>9 || y<0 || y>9)
		{
			return false;
			//do nothing
		}
		else
		{
			if(x+length<=9)
			{
				for(int i=x;i<x+length;i++)
				{
					if(y!=0)
					{
						if(x+length==9)
						{
							top=true;
						}
						else
						{
							top=(o.board[y][i+1]!=1);
						}
						//checking bottom
						if(x==0)
						{
							below=true;
						}
						else
						{
							below=(o.board[y][x-1]!=1);
						}
						left=(o.board[y-1][i]!=1);
						right=true;
						if(y!=9)
						{
							right=(o.board[y+1][i]!=1);
						}
						
						if(top && below && left && right)
						{
							if(o.board[y][i]!=1 && o.board[y][i]!=2)
							{
								//do nothing
							}
							else
							{
								errFlag=1;
							}
						}
						else
						{
							errFlag=1;
						}
					}
					else if(y==0)
					{
						if(x+length==9)
						{
							top=true;
						}
						else
						{
							top=(o.board[y][i+1]!=1);
						}
						//checking bottom
						if(x==0)
						{
							below=true;
						}
						else
						{
							below=(o.board[y][x-1]!=1);
						}
						left=true;
						right=(o.board[y+1][i]!=1);
						if(top && below && left && right)
						{
							if(o.board[y][i]!=1 && o.board[y][i]!=2)
							{
								//do nothing
							}
							else
							{
								errFlag=1;
							}
						}
						else
						{
							errFlag=1;
						}
					}
					else if(y==9)
					{
						if(x+length==9)
						{
							top=true;
						}
						else
						{
							top=(o.board[y][i+1]!=1);
						}
						//checking bottom
						if(x==0)
						{
							below=true;
						}
						else
						{
							below=(o.board[y][x-1]!=1);
						}
						left=(o.board[y-1][i]!=1);
						right=true;
						if(top && below && left && right)
						{
							if(o.board[y][i]!=1 && o.board[y][i]!=2)
							{
								//do nothing
							}
							else
							{
								errFlag=1;
							}
						}
						else
						{
							errFlag=1;
						}
					}
				}
				if(errFlag==0)
				{
					return true;
				}
			}
		}
		return false;
	}
	/**
	 *method that prints board depending upon incomming id
	 *
	 * @param       id 			users input id
	 *
	 */
	public void printBoard(int id)
	{
		if(id==1)
		{
			for(int i=0;i<10;i++)
			{
				for(int j=0;j<10;j++)
				{
					System.out.print(playerA.board[i][j]);
				}
				System.out.println("");
			}
		}
		else
		{
			for(int i=0;i<10;i++)
			{
				for(int j=0;j<10;j++)
				{
					System.out.print(playerB.board[i][j]);
				}
				System.out.println("");
			}
		}
	}
	/**
	 *method that lets go of the lock
	 *
	 * @param       id 			users input id
	 *
	 */
	@Override
	public void relinQuishLock(int id) throws RemoteException 
	{
		// TODO Auto-generated method stub
		if(id==1)
		{
			lock=-1;
		}
		else if(id==2)
		{
			lock=1;
		}
		
	}
}
