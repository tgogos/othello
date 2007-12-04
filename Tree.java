import java.util.ArrayList;
import java.util.List;

public class Tree
{
	
	public int last_move_X;
	public int last_move_Y;
	
	public int search_depth;
	
	Node HEAD;
	
	
	
	public Tree(int[][] game, short[][] liberties, int cpu_color, int search_depth)
	{
		this.search_depth = search_depth;
		
		int player_color=0;
		if (cpu_color==1)
		{
			player_color=2;
		}
		else if (cpu_color==2)
		{
			player_color=1;
		}
		
		
		

		HEAD= new Node(cpu_color , player_color , game, liberties , 0 , 8 , 8 , Integer.MIN_VALUE , Integer.MAX_VALUE , true);
	}





	public void minimax_dfs_ab()
	{
		HEAD.v= max_value(HEAD , HEAD.a , HEAD.b);
		System.out.println(HEAD.v/*+ " "+HEAD.a+" "+HEAD.b*/);
		for (int i=0 ; i<HEAD.kids.size() ; i++)
		{
			if(HEAD.kids.get(i).v == HEAD.v)
			{
				last_move_X = HEAD.kids.get(i).last_move_X;
				last_move_Y = HEAD.kids.get(i).last_move_Y;
			}
		}
	}
	
	
	
	
	
	private int max_value(Node state, int a, int b)
	{
		//if ((state.depth==search_depth) || isOver(state.game))
		if (state.depth==search_depth)
		{
			return state.heuristic();
		}
			
		state.v= Integer.MIN_VALUE;
		
		if ((state.depth==1) && (state.isCorner())) return state.v;
		
		for (int i=0 ; i<8 ; i++)
		{
			for (int j=0 ; j<8 ; j++)
			{
				if(isLegal(state.game , i , j , state.cpu_color))
				{
					int[][] tmp= new int[8][8];
					tmp= copy_from(state.game);
					tmp[i][j]= state.cpu_color;
					calculate_changes(tmp , i , j , state.cpu_color);
					
					
					short[][] lib= new short[8][8];
					lib= copy_from(state.liberties);
					update_liberties(lib , i , j);
					
					//System.out.println(i + " "+ j + " " +state.cpu_color);
					//print(tmp);
					
					Node tmp_node= new Node(state.cpu_color , state.player_color , tmp , lib , state.depth+1 , i , j , state.a , state.b , false);
					state.kids.add(tmp_node);
					
					state.v= max(state.v , min_value(tmp_node, state.a ,state.b));
					if (state.v>=state.b)
					{
						if (state.depth>1) state.kids.clear();
						return state.v;
					}
					state.a= max( state.a , state.v );
				}
			}
		}
		if (state.depth>1) state.kids.clear();
		return state.v;
	}
	private int  min_value(Node state, int a, int b)
	{
		//if ((state.depth==search_depth) || isOver(state.game))
		if (state.depth==search_depth)
		{
			return state.heuristic();
		}
				
		state.v= Integer.MAX_VALUE;
		
		if ((state.depth==1) && (state.isCorner())) return state.v;
		
		for (int i=0 ; i<8 ; i++)
		{
			for (int j=0 ; j<8 ; j++)
			{
				if(isLegal(state.game , i , j , state.player_color))
				{
					int[][] tmp= new int[8][8];
					tmp= copy_from(state.game);
					tmp[i][j]= state.player_color;
					calculate_changes(tmp , i , j , state.player_color);
					
					
					short[][] lib= new short[8][8];
					lib= copy_from(state.liberties);
					update_liberties(lib , i , j);
					
					
					//System.out.println(i + " "+ j + " " +state.cpu_color);
					//print(tmp);
					
					Node tmp_node= new Node(state.cpu_color , state.player_color , tmp , lib , state.depth+1 , i , j , state.a , state.b , true);
					state.kids.add(tmp_node);
					
					state.v= min(state.v , max_value(tmp_node, state.a, state.b));
					if (state.v<=state.a)
					{
						if (state.depth>1) state.kids.clear();
						return state.v;
					}
					state.b= min( state.b , state.v );
				}
			}
		}
		if (state.depth>1) state.kids.clear();
		return state.v;
	}
	
	private void print(int[][] a)
	{
		for (int i=0 ; i<8 ; i++)
		{
			for (int j=0 ; j<8 ; j++)
			{
				System.out.print(a[i][j]+" ");
			}
			System.out.println();
		}
		System.out.println();
		System.out.println();
	}
	
	private int max(int a , int b)
	{
		if (a>b) return a;
		return b;
	}
	
	private int min(int a , int b)
	{
		if (a<b) return a;
		return b;
	}	
	
	public boolean isLegal(int[][] game_ , int x, int y, int player)
	{
		if (game_[x][y]!=0) return false;
		
		int opponent;
		if (player==1) opponent=2;
		else opponent=1;
		
		
		//Orizontia anazhthsh ->
		if ((y<6) && (game_[x][y+1]==opponent))
		{
			for (int i=y+2 ; i<8 ; i++)
			{
				if (game_[x][i]==0) break;
				if (game_[x][i]==opponent) continue;
				if (game_[x][i]==player)
				{
					return true;
				}
			}
		}
		
		//Orizontia anazhthsh <-
		
		if ((y>1) && (game_[x][y-1]==opponent))
		{
			for (int i= y-2 ; i>=0 ; i--)
			{
				if (game_[x][i]==0) break;
				if (game_[x][i]==opponent) continue;
				if (game_[x][i]==player)
				{
					return true;
				}
			}
		}
		
		//Katheti anazhthsh pros ta panw
		
		if ((x>1) && (game_[x-1][y]==opponent))
		{
			for (int i= x-2 ; i>=0 ; i--)
			{
				if (game_[i][y]==0) break;
				if (game_[i][y]==opponent) continue;
				if (game_[i][y]==player)
				{
					return true;
				}
			}
		}
		
		//Katheti anazhthsh pros ta katw
		
		
		if ((x<6) && (game_[x+1][y]==opponent))
		{
			for (int i=x+2 ; i<8 ; i++)
			{
				if (game_[i][y]==0) break;
				if (game_[i][y]==opponent) continue;
				if (game_[i][y]==player)
				{
					return true;
				}
			}
		}
		
		//Äéáãþíéá áíáæÞôçóç êÜôù-äåîéÜ \>
		
		if ((y<6) && (x<6) && (game_[x+1][y+1]==opponent))
		{
			for (int i=x+2, j=y+2 ;  ((i<8) && (j<8)) ; i++, j++)
			{
				if (game_[i][j]==0) break;
				if (game_[i][j]==opponent) continue;
				if (game_[i][j]==player)
				{
					return true;
				}
			}
		}
		
		//Äéáãþíéá áíáæÞôçóç ðÜíù-áñéóôåñÜ <\
		
		if ((y>1) && (x>1) && (game_[x-1][y-1]==opponent))
		{
			for (int i=x-2, j=y-2 ; ((i>=0) && (j>=0)) ; i--, j--)
			{
				if (game_[i][j]==0) break;
				if (game_[i][j]==opponent) continue;
				if (game_[i][j]==player)
				{
					return true;
				}
			}
		}
		
		
		//Äéáãþíéá áíáæÞôçóç ðÜíù-äåîéÜ />
		
		if ((y<6) && (x>1) && (game_[x-1][y+1]==opponent))
		{
			for (int i=x-2, j=y+2 ; ((i>=0) && (j<8)) ; i--, j++)
			{
				if (game_[i][j]==0) break;
				if (game_[i][j]==opponent) continue;
				if (game_[i][j]==player)
				{
					return true;
				}
			}
		}
		
		//Äéáãþíéá áíáæÞôçóç êÜôù-áñéóôåñÜ </
		
		if ((y>1) && (x<6) && (game_[x+1][y-1]==opponent))
		{
			for (int i=x+2, j=y-2 ; ((i<8) && (j>=0)) ; i++, j--)
			{
				if (game_[i][j]==0) break;
				if (game_[i][j]==opponent) continue;
				if (game_[i][j]==player)
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	public static void calculate_changes(int[][] _game , int last_movement_X, int last_movement_Y , int player)
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
				if (_game[x][i]==0) break;
				if (_game[x][i]==player)
				{
					for (int j=i ; j>y ; j--)
					{
						_game[x][j]=player;
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
				if (_game[x][i]==0) break;
				if (_game[x][i]==player)
				{
					for (int j=i ; j<y ; j++)
					{
						_game[x][j]=player;
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
				if (_game[i][y]==0) break;
				if (_game[i][y]==player)
				{
					for (int j=i ; j<x ; j++)
					{
						_game[j][y]=player;
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
				if (_game[i][y]==0) break;
				if (_game[i][y]==player)
				{
					for (int j=i ; j>x ; j--)
					{
						_game[j][y]=player;
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
				if (_game[i][j]==0) break;
				if (_game[i][j]==player)
				{
					for (int m=i-1, n=j-1 ; ((m>x) && (n>y)) ; m--, n--)
					{
						_game[m][n]=player;
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
				if (_game[i][j]==0) break;
				if (_game[i][j]==player)
				{
					for (int m=i+1, n=j+1 ; ((m<x) && (n<y)) ; m++, n++)
					{
						_game[m][n]=player;
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
				if (_game[i][j]==0) break;
				if (_game[i][j]==player)
				{
					for (int m=i+1, n=j-1 ; ((m<x) && (n>y)) ; m++, n--)
					{
						_game[m][n]=player;
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
				if (_game[i][j]==0) break;
				if (_game[i][j]==player)
				{
					for (int m=i-1, n=j+1 ; ((m>x) && (n<y)) ; m--, n++)
					{
						_game[m][n]=player;
					}
					break;
				}
			}
		}
	}
	
	public boolean isOver(int[][] a)
	{
		for (int i=0 ; i<8 ; i++)
		{
			for(int j=0 ; j<8 ; j++)
			{
				if (isLegal(a , i , j , 1)) return false;
				if (isLegal(a , i , j , 2)) return false;
			}
		}
		return true;
	}	
	
	
	public static int[][] copy_from(int[][] a)
	{
		int[][] b= new int[8][8];
		for (int i=0 ; i<8 ; i++)
		{
			for (int j=0 ; j<8 ; j++)
			{
				b[i][j] = a[i][j];
			}
		}
		return b;
	}
	
	
	public static short[][] copy_from(short[][] a)
	{
		short[][] b= new short[8][8];
		for (int i=0 ; i<8 ; i++)
		{
			for (int j=0 ; j<8 ; j++)
			{
				b[i][j] = a[i][j];
			}
		}
		return b;
	}
	
	
	public void update_liberties(short[][] liberties, int x, int y)
	{
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
}

class Node
{
	public int[][] game;
	public short[][] liberties;
	
	public int v;
	public int a;
	public int b;
	
	public int player_color;
	public int cpu_color;
	
	public int last_move_X;
	public int last_move_Y;
	public int depth;
	
	public boolean kind_of_node;	// true for MAX - false for MIN
	public static final boolean MAX= true;
	public static final boolean MIN= false;
	
	List <Node> kids;
	public Movement move;
	
	public Node(int cpu_color , int player_color , int[][] game , short[][] liberties ,  int depth , int x , int y, int a, int b , boolean kind)
	{
		this.game = copy_from(game);
		this.liberties = copy_from(liberties);
		this.depth = depth;
		this.cpu_color = cpu_color;
		this.player_color = player_color;
		
		last_move_X= x;
		last_move_Y= y;
		
		this.a= a;
		this.b= b;
		
		kind_of_node= kind;
		
		move= new Movement(x,y);
		kids = new ArrayList< Node >();
	}
	
	
	public static int[][] copy_from(int[][] a)
	{
		int[][] b= new int[8][8];
		for (int i=0 ; i<8 ; i++)
		{
			for (int j=0 ; j<8 ; j++)
			{
				b[i][j] = a[i][j];
			}
		}
		return b;
	}
	
	public static short[][] copy_from(short[][] a)
	{
		short[][] b= new short[8][8];
		for (int i=0 ; i<8 ; i++)
		{
			for (int j=0 ; j<8 ; j++)
			{
				b[i][j] = a[i][j];
			}
		}
		return b;
	}
	
	public boolean isCorner()
	{
		if ( ((last_move_X==0) && ((last_move_Y==0) || (last_move_Y==7))) || ((last_move_X==7) && ((last_move_Y==0) ||  (last_move_Y==7))))
		{
			return true;
		}
		return false;
	}
	
	
	
	public int heuristic()
	{
		int h=0;
		
		int phase= this.phase();
		
		if (phase==1)
		{
			int h0= 5  * h_simpleCount();
			int h1= 50 * h_dangerous_corners(); //30
			int h2= -5  * h_number_of_next_moves();//10
			int h3= 20 * h_dangerous_zone();//20
			int h4= 30 * h_acmes2();
			int h5= 50 * h_count_corners(); //20
			int h6= 50 * h_get_acme_first();
			h= h0+h1+h2+h3+h4+h5+h6;
		}
		else if (phase==2)
		{
			int h0= 25 * h_simpleCount();
			int h1= 50 * h_dangerous_corners();
			int h2= 40 * h_number_of_next_moves();
			int h3= 40 * h_dangerous_zone();
			int h4= 40 * h_acmes2();
			int h5= 50 * h_count_corners();
			int h6= 50 * h_get_acme_first();
			h= h0+h1+h2+h3+h4+h5+h6;
		}
		else if (phase==3)
		{
			int h1= 70 * h_dangerous_corners();
			int h2= 70 * h_number_of_next_moves();
			int h3= 10 * h_dangerous_zone();
			int h4= 20 * h_acmes();
			int h5= 200 * h_count_corners();
			int h6= 100 * h_simpleCount();
			h= h1+h2+h3+h4+h5+h6;
		}
		
		return h;
	}
	
	
	
	
	
	public int h_simpleCount()
	{
		int sum=0;
		
		for (int i=0 ; i<8 ; i++)
		{
			for (int j=0 ; j<8 ; j++)
			{
				if (game[i][j]==cpu_color) sum++;
				if (game[i][j]==player_color) sum--;
			}
		}
		return sum;
	}
	
	public int h_count_corners()
	{
		int sum=0;
		
		if (game[0][0]==cpu_color)
		{
			sum++;
		}else if (game[0][0]==player_color)
		{
			sum--;
		}
		
		
		if (game[0][7]==cpu_color)
		{
			sum++;
		}else if (game[0][7]==player_color)
		{
			sum--;
		}
		
		
		if (game[7][0]==cpu_color)
		{
			sum++;
		}else if (game[7][0]==player_color)
		{
			sum--;
		}
		
		
		if (game[7][7]==cpu_color)
		{
			sum++;
		}else if (game[7][7]==player_color)
		{
			sum--;
		}
		return sum;
	}
	
	public int h_dangerous_zone()
	{
		int sum=0;
		
		int x=1;
		for (int i=2 ; i<6 ; i++)
		{
			if (game[x][i]==cpu_color) sum= sum - liberties[x][i];
			else if (game[x][i]==player_color) sum= sum + liberties[x][i];
		}
		
		
		x=6;
		for (int i=2 ; i<6 ; i++)
		{
			if (game[x][i]==cpu_color) sum= sum - liberties[x][i];
			else if (game[x][i]==player_color) sum= sum + liberties[x][i];
		}
		
		
		int y=1;
		for (int i=2 ; i<6 ; i++)
		{
			if (game[i][y]==cpu_color) sum= sum - liberties[i][y];
			else if (game[i][y]==player_color) sum= sum + liberties[i][y];
		}
		
		
		y=6;
		for (int i=2 ; i<6 ; i++)
		{
			if (game[i][y]==cpu_color) sum= sum - liberties[i][y];
			else if (game[i][y]==player_color) sum= sum + liberties[i][y];
		}
		
		return sum;
	}
	
	
	
	public int h_simple_Count_2()
	{
		int sum=0;
		
		for (int i=0 ; i<8 ; i++)
		{
			for (int j=0 ; j<8 ; j++)
			{
				if (game[i][j]==cpu_color)
				{
					if (((i==0) && ((j==0) ||  (j==7))) || ((i==7) && ((j==0) ||  (j==7))))
					{
						sum+=20;
					}
					if ((i==0) || (j==0) || (i==7) || (j==7))
					{
						sum+=5;
					}
					if ((i==1) || (i==6) || (j==1) || (j==6))	sum-=10;
					sum++;
				}
					
				if (game[i][j]==player_color)
				{
					if (((i==0) && ((j==0) ||  (j==7))) || ((i==7) && ((j==0) ||  (j==7))))
					{
						sum-=20;
					}
					if ((i==0) || (j==0) || (i==7) || (j==7))
					{
						sum-=5;
					}
					if ((i==1) || (i==6) || (j==1) || (j==6))	sum+=10;
					sum--;
				}
			}
		}
		
		return sum;
	}
	
	
	public int phase()
	{
		int sum=0;
		
		for (int i=0 ; i<8 ; i++)
		{
			for (int j=0 ; j<8 ; j++)
			{
				if (game[i][j]!=0) sum++;
			}
		}
		
		if (sum<=16) return 1;
		else if ((sum>16) && (sum<56)) return 2;
		return 3;
	}
	
	public int h_dangerous_corners()
	{
		int sum=0;
		
		
		if ((game[0][0]==0) && (game[0][1]==player_color)) sum++;
		if ((game[0][0]==0) && (game[1][0]==player_color)) sum++;
		if ((game[0][0]==0) && (game[1][1]==player_color)) sum++;
		
		if ((game[0][7]==0) && (game[0][6]==player_color)) sum++;
		if ((game[0][7]==0) && (game[1][7]==player_color)) sum++;
		if ((game[0][7]==0) && (game[1][6]==player_color)) sum++;
		
		if ((game[7][0]==0) && (game[7][1]==player_color)) sum++;
		if ((game[7][0]==0) && (game[6][0]==player_color)) sum++;
		if ((game[7][0]==0) && (game[6][1]==player_color)) sum++;
		
		if ((game[7][7]==0) && (game[7][6]==player_color)) sum++;
		if ((game[7][7]==0) && (game[6][7]==player_color)) sum++;
		if ((game[7][7]==0) && (game[6][6]==player_color)) sum++;
		
		
		
		if ((game[0][0]==0) && (game[0][1]==cpu_color)) sum--;
		if ((game[0][0]==0) && (game[1][0]==cpu_color)) sum--;
		if ((game[0][0]==0) && (game[1][1]==cpu_color)) sum--;
		
		if ((game[0][7]==0) && (game[0][6]==cpu_color)) sum--;
		if ((game[0][7]==0) && (game[1][7]==cpu_color)) sum--;
		if ((game[0][7]==0) && (game[1][6]==cpu_color)) sum--;
		
		if ((game[7][0]==0) && (game[7][1]==cpu_color)) sum--;
		if ((game[7][0]==0) && (game[6][0]==cpu_color)) sum--;
		if ((game[7][0]==0) && (game[6][1]==cpu_color)) sum--;
		
		if ((game[7][7]==0) && (game[7][6]==cpu_color)) sum--;
		if ((game[7][7]==0) && (game[6][7]==cpu_color)) sum--;
		if ((game[7][7]==0) && (game[6][6]==cpu_color)) sum--;
		
		return sum;
	}
	
	
	
	public int h_number_of_next_moves()
	{
		int sum= 0;
		
		if (kind_of_node==MIN)
		{
			for (int i=0 ; i<8 ; i++)
			{
				for (int j=0 ; j<8 ; j++)
				{
					if (isLegal(i , j , player_color)) sum--;
				}
			}
		}
		else if (kind_of_node==MAX)
		{
			for (int i=0 ; i<8 ; i++)
			{
				for (int j=0 ; j<8 ; j++)
				{
					if (isLegal(i , j , cpu_color)) sum++;
				}
			}
		}
		return sum;
	}
	
	
	public int h_liberties()
	{
		int sum= 0;
		
		for (int i=0 ; i<8 ; i++)
		{
			for (int j=0 ; j<8 ; j++)
			{
				if (game[i][j]==cpu_color)
				{
					sum= sum - liberties[i][j];
				}
				else if (game[i][j]==player_color)
				{
					sum= sum + liberties[i][j];
				}
			}
		}
		return sum;
	}
	
	public int h_acmes()
	{
		int sum= 0;
		
		for (int i=0 ; i<8 ; i=i+7)
		{
			for (int j=0 ; j<8 ; j++)
			{
				
				if (game[i][j]==cpu_color)
				{
					if (liberties[i][j]==0) sum++;
				}
				else if (game[i][j]==player_color)
				{
					if (liberties[i][j]==0) sum--;
				}
				
				
				
				if (game[j][i]==cpu_color)
				{
					if (liberties[j][i]==0) sum++;
				}
				else if (game[j][i]==player_color)
				{
					if (liberties[j][i]==0) sum--;
				}
				
			}
		}
		
		
		return sum;
	}								
	
	public int h_get_acme_first()
	{
		boolean first= false;
		
		if (last_move_X==0)
		{
			for (int i=0 ; i<8 ; i++)
			{
				if (game[0][i]==cpu_color)
				{
					for (int j=i+1 ; j<8 ; j++)
					{
						if (game[0][j]!=0) 
						{
							first = false;
							break;
						}
					}
					if (first) return 1;
				}
				else if (game[0][i]==player_color)
				{
					for (int j=i+1 ; j<8 ; j++)
					{
						if (game[0][j]!=0) 
						{
							first = false;
							break;
						}
					}
					if (first) return -1;
				}
			}
		}				
		else if (last_move_X==7)
		{
			for (int i=0 ; i<8 ; i++)
			{
				if (game[7][i]==cpu_color)
				{
					for (int j=i+1 ; j<8 ; j++)
					{
						if (game[7][j]!=0) 
						{
							first = false;
							break;
						}
					}
					if (first) return 1;
				}
				else if (game[7][i]==player_color)
				{
					for (int j=i+1 ; j<8 ; j++)
					{
						if (game[7][j]!=0) 
						{
							first = false;
							break;
						}
					}
					if (first) return -1;
				}
			}
		}
		
		
		if (last_move_Y==0)
		{
			for (int i=0 ; i<8 ; i++)
			{
				if (game[i][0]==cpu_color)
				{
					for (int j=i+1 ; j<8 ; j++)
					{
						if (game[j][0]!=0) 
						{
							first = false;
							break;
						}
					}
					if (first) return 1;
				}
				else if (game[i][0]==player_color)
				{
					for (int j=i+1 ; j<8 ; j++)
					{
						if (game[j][0]!=0) 
						{
							first = false;
							break;
						}
					}
					if (first) return -1;
				}
			}
		}	
		else if (last_move_Y==7)
		{
			for (int i=0 ; i<8 ; i++)
			{
				if (game[i][7]==cpu_color)
				{
					for (int j=i+1 ; j<8 ; j++)
					{
						if (game[j][7]!=0) 
						{
							first = false;
							break;
						}
					}
					if (first) return 1;
				}
				else if (game[i][7]==player_color)
				{
					for (int j=i+1 ; j<8 ; j++)
					{
						if (game[j][7]!=0) 
						{
							first = false;
							break;
						}
					}
					if (first) return -1;
				}
			}
		}	
		return 0;
	}
	
	public int h_acmes2()
	{
		int sum= 0;
		
		boolean found_cpu_color = false;
		boolean found_player_color = false;
		
		int x=0;
		for (int i=0 ; i<8 ; i++)
		{
			if (game[x][i]==cpu_color)
			{
				found_cpu_color = true;
			}
			else if (game[x][i]==player_color)
			{
				found_player_color = true;
			}
			else if ((game[x][i]==0) && (i>0))
			{
				if ((game[x][i-1]==cpu_color) && (found_player_color))
				{
					for (int j=i-1 ; j>=0 ; j--)
					{
						if (game[x][j]==cpu_color)
						{
							sum--;
						}
						else if (game[x][j]==player_color) break;
					}
				}
				else if ((game[x][i-1]==player_color) && (found_cpu_color))
				{
					for (int j=i-1 ; j>=0 ; j--)
					{
						if (game[x][j]==player_color)
						{
							sum++;
						}
						else if (game[x][j]==cpu_color) break;
					}
				}
			}
		}
		
		
		found_cpu_color = false;
		found_player_color = false;
		x=7;
		for (int i=0 ; i<8 ; i++)
		{
			if (game[x][i]==cpu_color)
			{
				found_cpu_color = true;
			}
			else if (game[x][i]==player_color)
			{
				found_player_color = true;
			}
			else if ((game[x][i]==0) && (i>0))
			{
				if ((game[x][i-1]==cpu_color) && (found_player_color))
				{
					for (int j=i-1 ; j>=0 ; j--)
					{
						if (game[x][j]==cpu_color)
						{
							sum--;
						}
						else if (game[x][j]==player_color) break;
					}
				}
				else if ((game[x][i-1]==player_color) && (found_cpu_color))
				{
					for (int j=i-1 ; j>=0 ; j--)
					{
						if (game[x][j]==player_color)
						{
							sum++;
						}
						else if (game[x][j]==cpu_color) break;
					}
				}
			}
		}			
	
		
		
		found_cpu_color = false;
		found_player_color = false;
		int y=0;
		for (int i=0 ; i<8 ; i++)
		{
			if (game[i][y]==cpu_color)
			{
				found_cpu_color = true;
			}
			else if (game[i][y]==player_color)
			{
				found_player_color = true;
			}
			else if ((game[i][y]==0) && (i>0))
			{
				if ((game[i-1][y]==cpu_color) && (found_player_color))
				{
					for (int j=i-1 ; j>=0 ; j--)
					{
						if (game[j][y]==cpu_color)
						{
							sum--;
						}
						else if (game[j][y]==player_color) break;
					}
				}
				else if ((game[i-1][y]==player_color) && (found_cpu_color))
				{
					for (int j=i-1 ; j>=0 ; j--)
					{
						if (game[j][y]==player_color)
						{
							sum++;
						}
						else if (game[j][y]==cpu_color) break;
					}
				}
			}
		}
		
		
		
		found_cpu_color = false;
		found_player_color = false;
		y=7;
		for (int i=0 ; i<8 ; i++)
		{
			if (game[i][y]==cpu_color)
			{
				found_cpu_color = true;
			}
			else if (game[i][y]==player_color)
			{
				found_player_color = true;
			}
			else if ((game[i][y]==0) && (i>0))
			{
				if ((game[i-1][y]==cpu_color) && (found_player_color))
				{
					for (int j=i-1 ; j>=0 ; j--)
					{
						if (game[j][y]==cpu_color)
						{
							sum--;
						}
						else if (game[j][y]==player_color) break;
					}
				}
				else if ((game[i-1][y]==player_color) && (found_cpu_color))
				{
					for (int j=i-1 ; j>=0 ; j--)
					{
						if (game[j][y]==player_color)
						{
							sum++;
						}
						else if (game[j][y]==cpu_color) break;
					}
				}
			}
		}
		
		
		x=0;
		for (int i=7 ; i>=0 ; i--)
		{
			if (game[x][i]==cpu_color)
			{
				found_cpu_color = true;
			}
			else if (game[x][i]==player_color)
			{
				found_player_color = true;
			}
			else if ((game[x][i]==0) && (i<7))
			{
				if ((game[x][i+1]==cpu_color) && (found_player_color))
				{
					for (int j=i+1 ; j<8 ; j++)
					{
						if (game[x][j]==cpu_color)
						{
							sum--;
						}
						else if (game[x][j]==player_color) break;
					}
				}
				else if ((game[x][i+1]==player_color) && (found_cpu_color))
				{
					for (int j=i+1 ; j<8 ; j++)
					{
						if (game[x][j]==player_color)
						{
							sum++;
						}
						else if (game[x][j]==cpu_color) break;
					}
				}
			}
		}
		
		
		x=7;
		for (int i=7 ; i>=0 ; i--)
		{
			if (game[x][i]==cpu_color)
			{
				found_cpu_color = true;
			}
			else if (game[x][i]==player_color)
			{
				found_player_color = true;
			}
			else if ((game[x][i]==0) && (i<7))
			{
				if ((game[x][i+1]==cpu_color) && (found_player_color))
				{
					for (int j=i+1 ; j<8 ; j++)
					{
						if (game[x][j]==cpu_color)
						{
							sum--;
						}
						else if (game[x][j]==player_color) break;
					}
				}
				else if ((game[x][i+1]==player_color) && (found_cpu_color))
				{
					for (int j=i+1 ; j<8 ; j++)
					{
						if (game[x][j]==player_color)
						{
							sum++;
						}
						else if (game[x][j]==cpu_color) break;
					}
				}
			}
		}
		
		y=0;
		for (int i=7 ; i>=0 ; i--)
		{
			if (game[i][y]==cpu_color)
			{
				found_cpu_color = true;
			}
			else if (game[i][y]==player_color)
			{
				found_player_color = true;
			}
			else if ((game[i][y]==0) && (i<7))
			{
				if ((game[i+1][y]==cpu_color) && (found_player_color))
				{
					for (int j=i+1 ; j<8 ; j++)
					{
						if (game[j][y]==cpu_color)
						{
							sum--;
						}
						else if (game[j][y]==player_color) break;
					}
				}
				else if ((game[i+1][y]==player_color) && (found_cpu_color))
				{
					for (int j=i+1 ; j<8 ; j++)
					{
						if (game[j][y]==player_color)
						{
							sum++;
						}
						else if (game[j][y]==cpu_color) break;
					}
				}
			}
		}
		
		
		y=7;
		for (int i=7 ; i>=0 ; i--)
		{
			if (game[i][y]==cpu_color)
			{
				found_cpu_color = true;
			}
			else if (game[i][y]==player_color)
			{
				found_player_color = true;
			}
			else if ((game[i][y]==0) && (i<7))
			{
				if ((game[i+1][y]==cpu_color) && (found_player_color))
				{
					for (int j=i+1 ; j<8 ; j++)
					{
						if (game[j][y]==cpu_color)
						{
							sum--;
						}
						else if (game[j][y]==player_color) break;
					}
				}
				else if ((game[i+1][y]==player_color) && (found_cpu_color))
				{
					for (int j=i+1 ; j<8 ; j++)
					{
						if (game[j][y]==player_color)
						{
							sum++;
						}
						else if (game[j][y]==cpu_color) break;
					}
				}
			}
		}
		//System.out.println(sum);
		return sum;
	}			
			




	public boolean isLegal(int x, int y, int player)
	{
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
						
			
	
	
}

class Movement
{
	public int last_move_X;
	public int last_move_Y;
	
	public Movement()
	{
		last_move_X=-1;
		last_move_Y=-1;
	}
	public Movement(int x , int y)
	{
		last_move_X= x;
		last_move_Y= y;
	}
}