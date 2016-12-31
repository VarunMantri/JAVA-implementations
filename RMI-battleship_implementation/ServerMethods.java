import java.rmi.Remote;
import java.rmi.RemoteException;

/** 
 * Interface ServerMethods needed for ServerClient communication
 * 
 * @author      Varun Rajiv Mantri 
 * 
 */
public interface ServerMethods extends Remote
{
	public void setName(String name,int id) throws RemoteException;
	public boolean setCarrier(String input,int id)throws RemoteException;
	public boolean setBattleship(String input,int id)throws RemoteException;
	public boolean setCruiser(String input,int id)throws RemoteException;
	public boolean setDestroyer(String input,int id)throws RemoteException;
	public int isMyTurn(int id)throws RemoteException;
	public int isHit(String input)throws RemoteException;
	public void relinQuishLock(int id)throws RemoteException;
	public int[][] peekBoard(int id) throws RemoteException;
	public int[][] peekSettingBoard(int id) throws RemoteException;
}
