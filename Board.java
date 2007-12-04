import java.awt.*;
import javax.swing.*;


public class Board extends JPanel
{
	
	int[][] board= new int[8][8];
	public Point p;
	public int last_movement_X;
	public int last_movement_Y;
	
	
	public JLabel jpg;
	
	public Board()
	{
		last_movement_X= -1;
		last_movement_Y= -1;
		
		initComponents();
	}
	
	private void initComponents()
	{
		setSize(550,550);
		setBackground(new Color(0 , 146 , 64));
				
		
		/*Icon othello_icon= new ImageIcon("1.jpg");
		jpg= new JLabel(othello_icon , SwingConstants.RIGHT);
		jpg.setBounds(700, 50, 830 , 110);
		jpg.setOpaque(true);
		add(jpg);*/
		
		
		setVisible(true);
	}
	
	
	public void setV()
	{
		setVisible(true);
	}
	
	
	public void refresh()
	{
		repaint();
	}
	
	public void paint(Graphics g)
	{
		
		super.paint(g);
		
		
		/*Icon othello_icon= new ImageIcon("1.jpg");
		jpg= new JLabel(othello_icon , SwingConstants.CENTER);
		jpg.setBounds(530, 100, 660 , 160);
		jpg.setOpaque(true);
		add(jpg);*/
		
		
		Graphics2D g2= (Graphics2D) g;
		
		//g2.fillRect(560,30,100,100);
		
		int x,y,size;
		x= 10;
		y= 10;
		size= 60;
		
		for (int i=0 ; i<8 ; i++)
		{
			x=10;
			for (int j=0 ; j<8 ; j++)
			{
				g2.drawRect(x, y, size, size);
				x= x+60;
			}
			y= y+60;
		}
		
		x= 15;
		y= 15;
		size= 50;
		g2.setColor(new Color(2, 167, 70));
		
		for (int i=0 ; i<8 ; i++)
		{
			x=15;
			for (int j=0 ; j<8 ; j++)
			{
				g2.setColor(new Color(2, 167, 70));
				g2.fillRect(x, y, size, size);
				x= x+60;
				
			}
			y= y+60;
		}
		
		
		x= 15;
		y= 15;
		size= 40;
		
		for (int i=0 ; i<8 ; i++)
		{
			x=15;
			for (int j=0 ; j<8 ; j++)
			{
				if (board[i][j]==1)
				{
					//g2.setColor(new Color(255, 255, 255));
					//g2.fillOval((x+5), (y+5), size, size);
					g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING , RenderingHints.VALUE_ANTIALIAS_ON);
					g2.setPaint( new GradientPaint( x , y , Color.WHITE , x+40 , y+40, new Color(195,195,195),false));
					g2.fill(new java.awt.geom.Ellipse2D.Double((x+5), (y+5), size, size));
					g2.setPaint( new GradientPaint( x+40 , y+40 , Color.WHITE , x , y, new Color(150,150,150),true));
					g2.fill(new java.awt.geom.Ellipse2D.Double((x+10), (y+10), size-10, size-10));
				}
				else if (board[i][j]==2)
				{
					//g2.setColor(new Color(0, 0, 0));
					//g2.fillOval((x+5), (y+5), size, size);
					g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING , RenderingHints.VALUE_ANTIALIAS_ON);
					g2.setPaint( new GradientPaint( x , y , new Color(150,150,150) , x+40 , y+40, Color.BLACK ,false));
					g2.fill(new java.awt.geom.Ellipse2D.Double((x+5), (y+5), size, size));
					g2.setPaint( new GradientPaint( x+40 , y+40 , new Color(150,150,150), x , y,Color.BLACK , true));
					g2.fill(new java.awt.geom.Ellipse2D.Double((x+10), (y+10), size-10, size-10));
				}
				
				x= x+60;
				
			}
			y= y+60;
		}
		
		
	}
	
	public int getX()
	{
		return last_movement_X;
	}
	
	public int getY()
	{
		return last_movement_Y;
	}
	
	public void accept_last_movement(int player)
	{
		board[last_movement_X][last_movement_Y]= player;
		repaint();
	}
	
	public void accept_movement(int x , int y , int player)
	{
		board[x][y]= player;
		repaint();
	}
	
	public void lock_movements()
	{
		last_movement_X= -1;
		last_movement_Y= -1;
	}
	
	public void set_game(int[][] game)
	{
		board= game;
		repaint();
	}
	
	public int[][] get_game()
	{
		return board;
	}
	
	public void initBoard()
	{
		for (int i=0 ; i<8 ; i++)
		{
			for (int j=0 ; j<8 ; j++)
			{
				board[i][j]=0;
			}
		}
		board[3][3]=1;
		board[3][4]=2;
		board[4][3]=2;
		board[4][4]=1;
		repaint();
	}
	
}//GUI