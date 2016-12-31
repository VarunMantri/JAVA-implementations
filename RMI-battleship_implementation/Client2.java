import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.border.Border;
/** 
 * Server class whose insatnce is shared using RMI
 * 
 * @author      Varun Rajiv Mantri 
 * 
 */
public class Client2 extends Thread
{
	//Global declarations
	JFrame frame;
	JPanel top;
	JPanel south;
	JPanel center;
	JPanel radioContainer;
	JLabel information1;
	JLabel text;
	JLabel information2;
	JButton connect;
	JButton disconnect;
	JPanel buttonContainer;
	JRadioButton verticalB;
	JRadioButton horizontalB;
	ButtonGroup group;
	int looserFlag=0;
	static ServerMethods sm;
	static int play=0;
	static int state=0;
	int id=2;
	int gameOverFlag=0;
	int threadStartFlag=1;
	int connectionFlag=0;
	Border raisedlevel;
	static int orientation;
	static JButton [][]array;
	String name;
	public  Client2()
	{
		initializeUI();
	}
	/**
	 *method to initialize user interface
	 */
	public void initializeUI()
	{
		//Object creation
		//-------------------------------------------------------
		raisedlevel = BorderFactory.createRaisedBevelBorder();
		frame=new JFrame();
		verticalB=new JRadioButton("Vertical",true);
		horizontalB=new JRadioButton("Horizontal",false);
		group=new ButtonGroup();
		disconnect=new JButton("Disconnect");
		connect=new JButton("  Connect  ");
		//adding action listener
				disconnect.addActionListener(new ActionListener() 
				{
		            public void actionPerformed(ActionEvent e) 
		            {
		            	System.exit(0);
		            }
		         });
		connect=new JButton("  Connect  ");
		//adding action listener
		connect.addActionListener(new ActionListener() 
		{
			 public void actionPerformed(ActionEvent e) 
			 {
				 if(connectionFlag==1)
				 {
					JOptionPane.showMessageDialog(null,"Cannot connect: 1 connection already present");
				 }
				 else
				 {
					 JOptionPane.showMessageDialog(null,"Connection establihsed with server");
					 connectionFlag=1;
					 connectServer();
				 }
			 }
		});
		//adding to group;
		//-----------------------
		group.add(verticalB);
		group.add(horizontalB);
		//-----------------------
		top=new JPanel();
		south=new JPanel();
		center=new JPanel();
		radioContainer=new JPanel();
		buttonContainer=new JPanel();
		buttonContainer.add(connect);
		buttonContainer.add(disconnect);
		information1=new JLabel();
		information2=new JLabel();
		information1.setOpaque(true);
		information1.setBackground(Color.YELLOW);
		
		text=new JLabel();
		text.setBackground(Color.gray);
		text.setFont(new Font("Courier New", Font.ITALIC, 18));
		text.setHorizontalAlignment(JLabel.CENTER);
		text.setVerticalAlignment(JLabel.CENTER);
		information1.setHorizontalAlignment(JLabel.CENTER);
		information1.setVerticalAlignment(JLabel.CENTER);
		information1.setText("Information Console Client2");
		information1.setFont(new Font("Courier New", Font.ITALIC, 18));
		//-------------------------------------------------------
		
		//adding to panels
		GridLayout g=new GridLayout(2,2);
		top.setLayout(g);
		//information1.setSize(800,25);
		top.add(information1);
		top.add(text);
		top.setBorder(raisedlevel);
		GridLayout g1=new GridLayout(10,10);
		south.setLayout(g1);
		south.setBorder(raisedlevel);
		//adding action listners to radio buttons
		//setting up the default value
		orientation=1;
		information2.setFont(new Font("Courier New", Font.ITALIC, 16));
		information2.setText("Select Ships orientation");
		information2.setHorizontalAlignment(JLabel.CENTER);
		information2.setVerticalAlignment(JLabel.CENTER);
		GridLayout rG=new GridLayout(3,1);
		center.setLayout(rG);
		center.add(information2);
		radioContainer.add(verticalB,BorderLayout.EAST);
		radioContainer.add(horizontalB,BorderLayout.WEST);
		center.add(radioContainer);
		center.add(buttonContainer);
		verticalB.addActionListener(new ActionListener() 
				{
                    public void actionPerformed(ActionEvent e) 
                    {
                    	orientation=1;
                    }
                });
		horizontalB.addActionListener(new ActionListener() 
		{
            public void actionPerformed(ActionEvent e) 
            {
            	orientation=0;
            }
        });

		array=new JButton[10][10];
		for(int i=0;i<10;i++)
		{
			for(int j=0;j<10;j++)
			{
				array[i][j]=new JButton("("+j+","+i+")");
				array[i][j].setBackground(Color.white);
				array[i][j].setSize(35, 35);
				array[i][j].addActionListener(new ActionListener() 
				{
                    public void actionPerformed(ActionEvent e) 
                    {
                    	String data=e.getActionCommand();
                    	if(connectionFlag==0)
                    	{
                    		JOptionPane.showMessageDialog(null, "Server not connected");
                    	}
                    	else
                    	{
		                    	try 
		                    	{
			                    	if(state==0)
			                    	{
			                    		String dataPart=data.substring(1,4);
			                    		System.out.println(dataPart);
											if(sm.setCarrier(dataPart+","+orientation, id))
											{
												state=1;
												text.setText("");
												paintAgaian();
												askBattleship();
											}
											else
											{
												text.setText("Incorrect input.....");
												askCarrier();
											}
			                    	}
			                    	else if(state==1)
			                    	{
			                    		String dataPart=data.substring(1,4);
			                    		
											if(sm.setBattleship(dataPart+","+orientation, id))
											{
												state=2;
												text.setText("");
												paintAgaian();
												askCruiser();
											}
											else
											{
												text.setText("Incorrect input.....");
												askBattleship();
											}
			                    	}
			                    	else if(state==2)
			                    	{
			                    		String dataPart=data.substring(1,4);
			                    		
											if(sm.setCruiser(dataPart+","+orientation, id))
											{
												state=3;
												text.setText("");
												paintAgaian();
												askDestroyer();
											}
											else
											{
												text.setText("Incorrect input.....");
												askCruiser();
											}
			                    	}
			                    	else if(state==3)
			                    	{
			                    		String dataPart=data.substring(1,4);
			                    		
											if(sm.setDestroyer(dataPart+","+orientation, id))
											{
												center.remove(radioContainer);
												center.repaint();
												information2.setText("--------WAR--------");
												
												paintCompleteBoard();
												youCantPlay();
												state=5;
												
											}
											else
											{
												text.setText("Incorrect input.....");
												askDestroyer();
											}
			                    	}
			                    	else if(state==4)
			                    	{
			                    		String dataPart=data.substring(1,4);
			                    		int temp=sm.isHit(dataPart);
											if(temp==1)
											{
												text.setText("Its a hit.....Enter co-ordinates of shell.....");
												paintHit(dataPart,1);
												
											}
											else if(temp==2)
											{
												
												text.setText("It's a miss.....It is opponents turn to play.....");
												paintHit(dataPart,0);
												
												sm.relinQuishLock(id);
												threadStartFlag=1;
												state=5;
											}
											else if(temp==5)
											{
												refactorScreen();
											}
			                    	}
			                    	else if(state==5)
			                    	{
			                    		text.setText("Please wait.....");
			                    
			                    	}
			              
		                    	} 
		                    	catch (RemoteException e1) 
		                    	{
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
                    	}
                    }
                });
				south.add(array[i][j]);
			}
		}
		GridLayout gFrame=new GridLayout(3,1);
		frame.setLayout(gFrame);
		frame.add(top);
		frame.add(center);
		frame.add(south);
		frame.setSize(800, 500);
		frame.setMinimumSize(new Dimension(650,500));
		frame.setVisible(true);
	}
	/**
	 *method to re-fabricate screen after winning the game
	 */
	public void refactorScreen()
	{
		frame.remove(south);
		frame.repaint();
		text.setText("You have won the game");
		information2.setText("-------------"+name+" is WINNER-------------");
	}
	/**
	 *method to paint the entire board
	 */
	public void paintCompleteBoard()
	{
		for(int i=0;i<10;i++)
		{
			for(int j=0;j<10;j++)
			{
				array[i][j].setBackground(Color.WHITE);
			}
		}
	}
	/**
	 *method to connect to server
	 */
	public void connectServer()
	{
		initRMI();
		name=JOptionPane.showInputDialog("Enter your name");
		try {
			sm.setName(name, id);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 *method to set paint depending upon whichColor variable
	 *
	 * @param       data	 	coordinates
	 *
	 * @param       whichColor 	flag to determine color
	 */
	public void paintHit(String data,int whichColor)
	{
		int x=Character.getNumericValue(data.charAt(0));
		int y=Character.getNumericValue(data.charAt(2));
		//System.out.println(x);
		//System.out.println(y);
		for(int i=0;i<10;i++)
		{
			for (int j=0;j<10;j++)
			{
				if(i==y)
				{
					if(j==x)
					{
						if(whichColor==1)
						{
							array[i][j].setBackground(Color.GREEN);
							south.repaint();
						}
						else if(whichColor==0)
						{
							array[i][j].setBackground(Color.red);
							south.repaint();
						}
					}
				}
			}
		}
	}
	/**
	 *method to show ship placements to player
	 *
	 */
	public void paintAgaian()
	{
		int [][]temp=new int[10][10];
		try {
			temp=sm.peekSettingBoard(id);
			for(int i=0;i<10;i++)
			{
				for(int j=0;j<10;j++)
				{
					if(temp[i][j]==1)
					{
						array[i][j].setBackground(Color.GREEN);
						south.repaint();
					}
				}
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 *method to indicate end of game
	 *
	 */
	public void gameOver()
	{
		threadStartFlag=0;
		gameOverFlag=0;
		frame.remove(south);
		frame.repaint();
		text.setText("You have lost the game");
		information2.setText("-------------"+name+" is LOOSER-------------");
	}
	/**
	 *method to ask carrier coordinates
	 */
	public void askCarrier()
	{
		text.setText("Enter the co-ordinates of carrier:");
		
	}
	/**
	 *method to ask battleship coordinates
	 */
	public void askBattleship()
	{
		text.setText("Enter the co-ordinates of battleship:");
		
	}
	/**
	 *method to ask cruiser coordinates
	 */
	public void askCruiser()
	{
		text.setText("Enter the co-ordinates of cruiser:");
		
	}
	/**
	 *method to ask destroyer coordinates
	 */
	public void askDestroyer()
	{
		text.setText("Enter the co-ordinates of destroyer:");
		
	}
	/**
	 *method to ask if its this players turn to play
	 */
	public void canIPlay()
	{
		System.out.println("Its playing again");
		try {
			while(play!=1)			
			{
				int temp=sm.isMyTurn(id);
				if(temp==1)
				{
					play=1;
				}
				else if(temp==5)
				{
					play=1;
					looserFlag=1;
					gameOver();
				}
				try 
				{
					sleep(1000);
				} catch (InterruptedException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(looserFlag!=1)
		{
			threadStartFlag=0;
			play=0;
			youCanPlay();
		}
		
	}
	/**
	 *method to indicate servers consent to play
	 */
	public void youCanPlay()
	{
		text.setText("It's your turn to play.....Enter co-ordinates of shell.....");
		//information2.setText("Enter co-ordinates of shell.....");
		state=4;
	}
	/**
	 *method to reject request to play
	 */
	public void youCantPlay()
	{
		text.setText("PLease wait.....Server busy with opponent.....");
		//information2.setText("Server busy with opponent.....");
	}
	static Client2 uc;
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		//UIClient ui=new UIClient();
		//initialize RMI
		
		//initRMI();
		uc=new Client2();
		//DummyClass dc=new DummyClass();
		Thread t1=new Thread(uc,"t1");
		//Thread t2=new Thread(uc,"t2");
		t1.start();
		
		//UIClient uc2=new UIClient();
		//uc1.start();
	//	uc2.start();
		//uc.askCarrier();
	}
	/**
	 *method to initialize the RMI
	 */
	public static void initRMI()
	{
		try {
			sm=(ServerMethods)Naming.lookup("Server");
			Thread t2=new Thread(uc,"t2");
			t2.start();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 *method to run the Threads
	 */
	public void run()
	{
		if(Thread.currentThread().getName().equals("t1"))
		{
			askCarrier();
		}
		else if(Thread.currentThread().getName().equals("t2"))
		{
			while(gameOverFlag!=1)
			{
				if(threadStartFlag==1)
				{
					canIPlay();
					//System.out.println("called");
				}
				try {
					sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//System.out.println("Exited");
		}
	}
	

}
