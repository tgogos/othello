import java.awt.*;
import javax.swing.*;

public class MyPopup extends JFrame
{
	public ButtonGroup color_group;
	public ButtonGroup player_group;	
	
	public JButton ok;
	public JButton cancel;
    
	public JCheckBox player;
    public JCheckBox cpu;
    
    public JLabel title;
    public JLabel choose_player;
    public JLabel choose_color;
    
    public JTextField search_depth;
    
    
    
    public javax.swing.JRadioButton white;
    public javax.swing.JRadioButton black;
    
  
    public MyPopup(Point p)
    {
        initComponents(p);
    }
    
    
    private void initComponents(Point p)
    {
    	setSize(300,200);
		setLocation((int) p.getX()+100 , (int) p.getY()+100);
		setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		setResizable(false);
		
		
        color_group = new ButtonGroup();
        player_group = new ButtonGroup();
        	
        ok = new JButton();
        cancel = new JButton();
        
        player = new JCheckBox();
        cpu = new JCheckBox();
        
        title = new JLabel();
        choose_player = new JLabel();
        choose_color = new JLabel();
        
        white = new JRadioButton();
        black = new JRadioButton();
        
        search_depth= new JTextField();        

        
        //jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11));
        title.setText("  Settings:");

        choose_player.setText("  Who is playing first?");
	
		player.setText("You");
		player.setSelected(true);
        player.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        player.setMargin(new java.awt.Insets(0, 0, 0, 0));
        
        cpu.setText("CPU");
        cpu.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        cpu.setMargin(new java.awt.Insets(0, 0, 0, 0));

        player_group.add(player);
        player_group.add(cpu);
        
        white.setText("White");
        white.setSelected(true);
        white.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        white.setMargin(new java.awt.Insets(0, 0, 0, 0));
        
        black.setText("Black");
        black.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        black.setMargin(new java.awt.Insets(0, 0, 0, 0));

        color_group.add(white);
        color_group.add(black);
        
        choose_color.setText("  Choose your color:");

        ok.setText("OK");

        cancel.setText("Cancel");
        
        search_depth.setText("5");
        search_depth.setSize(1,1);
        	

		GridLayout g= new GridLayout(7,2,5,5);
		setLayout(g);
        add(title);
        add(new JPanel());
        	
        add(choose_player);
        add(new JPanel()); 
        	
        add(player);
        add(cpu);
        	
        add(choose_color);
        add(new JPanel());
        	
        add(white);
        add(black);
        	
        add(ok);
        add(cancel);
        
        add(new JLabel("Search depth: "));
        add(search_depth);
        
        //setVisible(true);
        	
    }
	
}