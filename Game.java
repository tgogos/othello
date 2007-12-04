public class Game
{
	
	
	int[][] game= new int[8][8];
	short[][] liberties= new short[8][8];
	
	
	public int numOfWHITE;
    public int numOfBLACK;
	public int last_movement_X;
	public int last_movement_Y;
	
	
	
	public Game()
	{
		last_movement_X= -1;
		last_movement_Y= -1;
		short liberties[][] = {
  						{ 3, 5, 5, 5, 5, 5, 5, 3 },
  						{ 5, 8, 8, 8, 8, 8, 8, 5 },
  						{ 5, 8, 7, 6, 6, 7, 8, 5 },
  						{ 5, 8, 6, 5, 5, 6, 8, 5 },
  						{ 5, 8, 6, 5, 5, 6, 8, 5 },
  						{ 5, 8, 7, 6, 6, 7, 8, 5 },
  						{ 5, 8, 8, 8, 8, 8, 8, 5 },
  						{ 3, 5, 5, 5, 5, 5, 5, 3 }
												 };
	}
	
	
	
	public void update_liberties()
	{
		int x= last_movement_X;
		int y= last_movement_Y;
		
		for (int i=x-1 ; i<=x+1; i++)
		{
  			for (int j=y-1 ; j<=y+1 ; j++)
  			{
  				if ((i<0) || (i>7) || (j<0) || (j>7)) continue;
  				
    			if (i != x || j != y)
    			{
      				liberties[i][j]--;
    			}
  			}
		}
	}	
	
	
	
	
	public void initBoard()
	{
		for (int i=0 ; i<8 ; i++)
		{
			for (int j=0 ; j<8 ; j++)
			{
				game[i][j]=0;
			}
		}
		game[3][3]=1;
		game[3][4]=2;
		game[4][3]=2;
		game[4][4]=1;
	}
	
	
	
	
	public void think(int cpu_color, int search_depth)
	{
		Tree tr= new Tree(game, liberties, cpu_color, search_depth);
		tr.minimax_dfs_ab();
		accept_movement(tr.last_move_X , tr.last_move_Y , cpu_color);
		//update_liberties();
		tr=null;
	}
	
	
	
	
	
	public void count(int cpu_color)
	{		
		numOfWHITE=0;
		numOfBLACK=0;
		
		for (int i=0 ; i<8 ; i++)
		{
			for(int j=0 ; j<8 ; j++)
			{
				if (game[i][j]==1) numOfWHITE++;
				if (game[i][j]==2) numOfBLACK++;
			}
		}
	}
	
	
	
	
	public boolean isOver()
	{
		for (int i=0 ; i<8 ; i++)
		{
			for(int j=0 ; j<8 ; j++)
			{
				if (isLegal(i , j , 1)) return false;
				if (isLegal(i , j , 2)) return false;
			}
		}
		return true;
	}
	
	
	
	
	
	
	public boolean has_next(int player)
	{
		for (int i=0 ; i<8 ; i++)
		{
			for(int j=0 ; j<8 ; j++)
			{
				if (isLegal(i , j , player)) return true;
			}
		}
		return false;
	}
	
	
	
	
	
	
	/*
	 *returns true if the (x,y) movement from 1st or 2nd player is legal
	 *
	 */
	 
	public boolean isLegal(int x, int y, int player)
	{
		if (x==-1) return false;
		if (game[x][y]!=0) return false;
		
		int opponent;
		if (player==1) opponent=2;
		else opponent=1;
		
		
		//Orizontia anazhthsh ->
		if ((y<6) && (game[x][y+1]==opponent))
		{
			for (int i=y+2 ; i<8 ; i++)
			{
				if (game[x][i]==0) break;
				if (game[x][i]==opponent) continue;
				if (game[x][i]==player)
				{
					return true;
				}
			}
		}
		
		//Orizontia anazhthsh <-
		
		if ((y>1) && (game[x][y-1]==opponent))
		{
			for (int i= y-2 ; i>=0 ; i--)
			{
				if (game[x][i]==0) break;
				if (game[x][i]==opponent) continue;
				if (game[x][i]==player)
				{
					return true;
				}
			}
		}
		
		//Katheti anazhthsh pros ta panw
		
		if ((x>1) && (game[x-1][y]==opponent))
		{
			for (int i= x-2 ; i>=0 ; i--)
			{
				if (game[i][y]==0) break;
				if (game[i][y]==opponent) continue;
				if (game[i][y]==player)
				{
					return true;
				}
			}
		}
		
		//Katheti anazhthsh pros ta katw
		
		
		if ((x<6) && (game[x+1][y]==opponent))
		{
			for (int i=x+2 ; i<8 ; i++)
			{
				if (game[i][y]==0) break;
				if (game[i][y]==opponent) continue;
				if (game[i][y]==player)
				{
					return true;
				}
			}
		}
		
		//Äéáãþíéá áíáæÞôçóç êÜôù-äåîéÜ \>
		
		if ((y<6) && (x<6) && (game[x+1][y+1]==opponent))
		{
			for (int i=x+2, j=y+2 ;  ((i<8) && (j<8)) ; i++, j++)
			{
				if (game[i][j]==0) break;
				if (game[i][j]==opponent) continue;
				if (game[i][j]==player)
				{
					return true;
				}
			}
		}
		
		//Äéáãþíéá áíáæÞôçóç ðÜíù-áñéóôåñÜ <\
		
		if ((y>1) && (x>1) && (game[x-1][y-1]==opponent))
		{
			for (int i=x-2, j=y-2 ; ((i>=0) && (j>=0)) ; i--, j--)
			{
				if (game[i][j]==0) break;
				if (game[i][j]==opponent) continue;
				if (game[i][j]==player)
				{
					return true;
				}
			}
		}
		
		
		//Äéáãþíéá áíáæÞôçóç ðÜíù-äåîéÜ />
		
		if ((y<6) && (x>1) && (game[x-1][y+1]==opponent))
		{
			for (int i=x-2, j=y+2 ; ((i>=0) && (j<8)) ; i--, j++)
			{
				if (game[i][j]==0) break;
				if (game[i][j]==opponent) continue;
				if (game[i][j]==player)
				{
					return true;
				}
			}
		}
		
		//Äéáãþíéá áíáæÞôçóç êÜôù-áñéóôåñÜ </
		
		if ((y>1) && (x<6) && (game[x+1][y-1]==opponent))
		{
			for (int i=x+2, j=y-2 ; ((i<8) && (j>=0)) ; i++, j--)
			{
				if (game[i][j]==0) break;
				if (game[i][j]==opponent) continue;
				if (game[i][j]==player)
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	
	
	
	
	public void setMovement(int x, int y)
	{
		last_movement_X= x;
		last_movement_Y= y;
	}
	
	
	
	
	
	public void accept_movement(int x, int y, int player)
	{
		last_movement_X= x;
		last_movement_Y= y;
		
		System.out.println("x: "+x+" y: "+y);
		
		game[x][y] = player;
		calculate_changes(player);
		update_liberties();
	}
	
	
	
	
	
	public int[][] get_game()
	{
		return game;
	}
	
	
	
	
	public void set_game(int[][] game)
	{
		this.game= game;
	}
	
	
	
	
	public void calculate_changes(int player)
	{
		int x= last_movement_X;
		int y= last_movement_Y;
		
		int opponent;
		if (player==1) opponent=2;
		else opponent=1;
		
		//Orizontia anazhthsh ->
		if (y<6)
		{
			for (int i=y+1 ; i<8 ; i++)
			{
				if (game[x][i]==0) break;
				if (game[x][i]==player)
				{
					for (int j=i ; j>y ; j--)
					{
						game[x][j]=player;
					}
					break;
				}
			}
		}
		
		//Orizontia anazhthsh <-
		
		if (y>1)
		{
			for (int i= y-1 ; i>=0 ; i--)
			{
				if (game[x][i]==0) break;
				if (game[x][i]==player)
				{
					for (int j=i ; j<y ; j++)
					{
						game[x][j]=player;
					}
					break;
				}
			}
		}
		
		//Katheti anazhthsh pros ta panw
		
		if (x>1)
		{
			for (int i= x-1 ; i>=0 ; i--)
			{
				if (game[i][y]==0) break;
				if (game[i][y]==player)
				{
					for (int j=i ; j<x ; j++)
					{
						game[j][y]=player;
					}
					break;
				}
			}
		}
		
		//Katheti anazhthsh pros ta katw
		
		
		if (x<6)
		{
			for (int i=x+1 ; i<8 ; i++)
			{
				if (game[i][y]==0) break;
				if (game[i][y]==player)
				{
					for (int j=i ; j>x ; j--)
					{
						game[j][y]=player;
					}
					break;
				}
			}
		}
		
		//Äéáãþíéá áíáæÞôçóç êÜôù-äåîéÜ \>
		
		if ((y<6) && (x<6))
		{
			for (int i=x+1, j=y+1 ;  ((i<8) && (j<8)) ; i++, j++)
			{
				if (game[i][j]==0) break;
				if (game[i][j]==player)
				{
					for (int m=i-1, n=j-1 ; ((m>x) && (n>y)) ; m--, n--)
					{
						game[m][n]=player;
					}
					break;
				}
			}
		}
		
		//Äéáãþíéá áíáæÞôçóç ðÜíù-áñéóôåñÜ <\
		
		if ((y>1) && (x>1))
		{
			for (int i=x-1, j=y-1 ; ((i>=0) && (j>=0)) ; i--, j--)
			{
				if (game[i][j]==0) break;
				if (game[i][j]==player)
				{
					for (int m=i+1, n=j+1 ; ((m<x) && (n<y)) ; m++, n++)
					{
						game[m][n]=player;
					}
					break;
				}
			}
		}
		
		
		//Äéáãþíéá áíáæÞôçóç ðÜíù-äåîéÜ />
		
		if ((y<6) && (x>1))
		{
			for (int i=x-1, j=y+1 ; ((i>=0) && (j<8)) ; i--, j++)
			{
				if (game[i][j]==0) break;
				if (game[i][j]==player)
				{
					for (int m=i+1, n=j-1 ; ((m<x) && (n>y)) ; m++, n--)
					{
						game[m][n]=player;
					}
					break;
				}
			}
		}
		
		//Äéáãþíéá áíáæÞôçóç êÜôù-áñéóôåñÜ </
		
		if ((y>1) && (x<6))
		{
			for (int i=x+1, j=y-1 ; ((i<8) && (j>=0)) ; i++, j--)
			{
				if (game[i][j]==0) break;
				if (game[i][j]==player)
				{
					for (int m=i-1, n=j+1 ; ((m>x) && (n<y)) ; m--, n++)
					{
						game[m][n]=player;
					}
					break;
				}
			}
		}
	}
}