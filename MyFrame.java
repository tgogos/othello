import java.awt.*;
import javax.swing.*;
import java.io.*;


public class MyFrame extends JFrame
{
	public Board othello;
	
	public JPanel panel;
	public JButton newGame;

	public JLabel jla;
	public JLabel how_many;
	
	
	
    public boolean user_has_pressed_OK;
	
	
	
	JFrame jf;
	ButtonGroup color_group;
	ButtonGroup player_group;	
	
	JButton ok;
	JButton cancel;
    
	JCheckBox player;
	JCheckBox cpu;
    
    JLabel title;
    JLabel choose_player;
    JLabel choose_color;
    
    
    JRadioButton white;
    JRadioButton black;
	
	
	
	
	
	public MyFrame()
	{
		
			
		panel= new JPanel();
		GridLayout gr= new GridLayout(0,5, 5,5);
		panel.setLayout(gr);
		
		newGame= new JButton("New Game");
		newGame.setToolTipText("Start a new game");
		
		
		jla= new JLabel("(messages here...)");
		
		how_many= new JLabel();
		
		panel.add(newGame);
		panel.add(jla);
		panel.add(how_many);
		
		othello= new Board();

		initComponents();
	}
	
	
	
	public void refresh()
	{
		othello.refresh();
		repaint();
	}
	
	
	
	private void initComponents()
	{
		setTitle("Othello");
		setSize(700,550);
		setLocation(100,100);
		setResizable(false);
		
		
		getContentPane().add(panel, BorderLayout.SOUTH);
		
	
		
		//board_game.setBounds(0,0, 500,500);
		getContentPane().add(othello, BorderLayout.CENTER);
		
		
		
		//getContentPane().add(my, BorderLayout.EAST);
		
						
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		
		setVisible(true);
		
	}
	
	public int get_movement_X()
	{
		return othello.getX();
	}
	
	
	
	
	public int get_movement_Y()
	{
		return othello.getY();
	}
	
	
	
	
	
	public void accept_last_movement(int player)
	{
		othello.accept_last_movement(player);
	}
	
	
	
	
	public void accept_movement(int x , int y , int player)
	{
		othello.accept_movement(x,y,player);
		repaint();
	}
	
	
	
	
	
	public void set_game(int[][] game)
	{
		othello.set_game(game);
	}
	
	
	
	
	
	public int[][] get_game()
	{
		return othello.get_game();
	}
	
	
	
	
	
	public void message(String s)
	{
		String g=s;
		jla.setText(g);
	}
	
	
	
	
	public void how_many_message(int white, int black, boolean player_hasWhite)
	{
		String g;
		if (player_hasWhite)
		{
			g= "You: "+white+"     Computer: "+black;
		}
		else
		{
			g= "You: "+black+"     Computer: "+white;
		}
		how_many.setText(g);
	}
	
	
	
	
	public void initBoard()
	{
		othello.initBoard();
	}
	
	
}//MyFrame