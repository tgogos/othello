


import javax.swing.JOptionPane;



public class Processor
{
	private MyFrame window;
	private Game thisGame;
	public MyPopup my;
	
	public int player_color;
    public int cpu_color;
    
    public final int WHITE=1;
    public final int BLACK=2;
    
	private boolean PLAYERs_TURN=true;
	private boolean CPUs_TURN=false;
	public int search_depth;
	
	int[][] game= new int[8][8];
	
	
	public Processor()
	{
		window= new MyFrame();
		thisGame= new Game();
		
			
		
		window.newGame.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt){
                newGame_buttonClicked(evt);
			}
		});
		
			
		window.othello.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                board_mousePressed(evt);
			}
		});
		
		window.othello.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                board_mouseReleased(evt);
			}
		});
	
	}
	
	private void board_mouseReleased(java.awt.event.MouseEvent evt)
	{
		if (CPUs_TURN) cpu_play();
	}
	
	private void board_mousePressed(java.awt.event.MouseEvent evt)
	{
		window.othello.p= window.othello.getMousePosition();
		int x= (int) window.othello.p.getX();
		int y= (int) window.othello.p.getY();
		
		int x1= (y-10)/60;
		int y1= (x-10)/60;
		
		if ((x1>7)||(y1>7)) return;
		
		window.othello.last_movement_X= x1;
		window.othello.last_movement_Y= y1;
	
		//System.out.println("mouse: "+x1+" "+y1);
		
		if (thisGame.isOver())
		{
			game_Over();
			return;
		}
		
		window.othello.repaint();
		
		if (thisGame.has_next(player_color))
		{
			if (!thisGame.isLegal(x1 , y1 , player_color)) {	window.message("illegal move!"); /*System.out.println((char) 7);*/ return;}
			
				
			thisGame.accept_movement(x1 , y1 , player_color);
			window.set_game(thisGame.get_game());
			window.othello.repaint();
			CPUs_TURN=true;		
				
		}else
		{
			JOptionPane.showMessageDialog(null,
											"You have no choice. \n It's CPU's turn again. ",
											"Oups!",
											JOptionPane.INFORMATION_MESSAGE);
			CPUs_TURN=true;
			cpu_play();
			return;
		}
		
		thisGame.count(cpu_color);
		window.how_many_message(thisGame.numOfWHITE, thisGame.numOfBLACK, (player_color==WHITE)?true:false);
		window.message("CPU is thinking...");
	}

	
    
	private void newGame_buttonClicked(java.awt.event.MouseEvent evt)
	{
		window.setEnabled(false);
		window.othello.setEnabled(false);
		
		
		my= new MyPopup(window.getLocation());
			
		
		my.ok.addMouseListener(new java.awt.event.MouseAdapter() {
        	   public void mouseClicked(java.awt.event.MouseEvent evt) {
        	       OK_MouseClicked(evt);
        	   }
        });
        
        
        my.cancel.addMouseListener(new java.awt.event.MouseAdapter() {
        	   public void mouseClicked(java.awt.event.MouseEvent evt) {
        	       CancelMouseClicked(evt);
        	   }
        });
		my.setVisible(true);
    }
    
    private void CancelMouseClicked(java.awt.event.MouseEvent evt) {
    	    my.dispose();
    	    window.setEnabled(true);
    }
    
    
    private void OK_MouseClicked(java.awt.event.MouseEvent evt)
	{
	    	if (  (my.white.isSelected() || my.black.isSelected()) && (my.player.isSelected() || my.cpu.isSelected()))
	    	{
	    		if (my.white.isSelected())
	    		{
	    			player_color= WHITE;
	    			cpu_color= BLACK;
	    		}
	    		else
	    		{
	    			player_color= BLACK;
	    			cpu_color= WHITE;
	    		}
				
				
				window.setEnabled(true);
	    		window.initBoard();
				thisGame.initBoard();
				
				if (my.cpu.isSelected())
	    		{
	    			CPUs_TURN=true;
	    			cpu_play();
	    		}
	    		
	    		search_depth= Integer.parseInt(my.search_depth.getText());
			}
			my.dispose();
			window.repaint();
			return;	
	    	
	}
	    
	private void cpu_play()
	{
		if (thisGame.isOver())
		{
			game_Over();
			return;
		}
		
		if (thisGame.has_next(cpu_color) && CPUs_TURN)
		{
			thisGame.think(cpu_color , search_depth);
			window.set_game(thisGame.get_game());
		}else
		{
			JOptionPane.showMessageDialog(null,
											"CPU has no choice. \n Continue, it's your turn again.",
											"You must be a very good player!!",
											JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		CPUs_TURN=false;
		window.othello.repaint();
				
		System.gc();
		thisGame.count(cpu_color);
		window.how_many_message(thisGame.numOfWHITE, thisGame.numOfBLACK, (cpu_color==WHITE)?false:true);
		window.message("Your turn");
		
		if (thisGame.isOver())
		{
			game_Over();
			return;
		}
		
		if (!thisGame.has_next(player_color))
		{
			JOptionPane.showMessageDialog(null,
											"You have no choice. \n It's CPU's turn again. ",
											"Oups!",
											JOptionPane.INFORMATION_MESSAGE);
			CPUs_TURN=true;
			cpu_play();
			window.othello.repaint();
		}
	}
	
	private void game_Over()
	{
		if((player_color==WHITE) && (thisGame.numOfWHITE>thisGame.numOfBLACK))
		{
			JOptionPane.showMessageDialog(null, "You WIN!!\n"
												+ "White: "+thisGame.numOfWHITE
												+ " Black: "+thisGame.numOfBLACK,
												"The game is over", JOptionPane.INFORMATION_MESSAGE);
		}
		else if ((player_color==WHITE) && (thisGame.numOfWHITE<thisGame.numOfBLACK))
		{
			JOptionPane.showMessageDialog(null, "Computer WINS.\n"
												+ "White: "+thisGame.numOfWHITE
												+ " Black: "+thisGame.numOfBLACK,
												"The game is over", JOptionPane.INFORMATION_MESSAGE);
		}
		else if ((player_color==BLACK) && (thisGame.numOfWHITE<thisGame.numOfBLACK))
		{
			JOptionPane.showMessageDialog(null, "You WIN!!\n"
											+ "White: "+thisGame.numOfWHITE
											+ " Black: "+thisGame.numOfBLACK,
											"The game is over", JOptionPane.INFORMATION_MESSAGE);
		}
		else if ((player_color==BLACK) && (thisGame.numOfWHITE>thisGame.numOfBLACK))
		{
			JOptionPane.showMessageDialog(null, "Computer WINS.\n"
											+ "White: "+thisGame.numOfWHITE
											+ " Black: "+thisGame.numOfBLACK,
											"The game is over", JOptionPane.INFORMATION_MESSAGE);
		}
		
		if (thisGame.numOfWHITE==thisGame.numOfBLACK)
		{
			JOptionPane.showMessageDialog(null, "We have NO winner!!\n"
												+ "White: "+thisGame.numOfWHITE
												+ " Black: "+thisGame.numOfBLACK,
												"The game is over", JOptionPane.INFORMATION_MESSAGE);
		}
		window.repaint();
	}
	
	
	public static void main(String[] args)
	{
		Processor p= new Processor();
	}
}