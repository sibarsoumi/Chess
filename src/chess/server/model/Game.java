package chess.server.model;

import java.util.LinkedList;

public class Game {
	
	public static final int A=0;
	public static final int B=1;
	public static final int C=2;
	public static final int D=3;
	public static final int E=4;
	public static final int F=5;
	public static final int G=6;
	public static final int H=7;
	
	private Stone board [][] = new Stone [9][8];
//	private LinkedList<Stone> aside_white;
//	private LinkedList<Stone> aside_black;
	private Player player_white,player_black;
	private Player turn;
	public Game()
	{
		
	}
	public Game(Player player_white,Player player_black)
	{
		this.player_white=player_white;
		this.player_black=player_black;
		
		turn=player_white;
	//	aside_white=new LinkedList<Stone>();
	//	aside_black=new LinkedList<Stone>();
		
		/* initializing white stones */
		board[1][A]=new Stone(Type.Rook,Color.White);
		board[1][B]=new Stone(Type.Knight,Color.White);
		board[1][C]=new Stone(Type.Bishop,Color.White);
		board[1][D]=new Stone(Type.Queen,Color.White);
		board[1][E]=new Stone(Type.King,Color.White);
		board[1][F]=new Stone(Type.Bishop,Color.White);
		board[1][G]=new Stone(Type.Knight,Color.White);
		board[1][H]=new Stone(Type.Rook,Color.White);
		for (int i=0;i<=7;i++)
			board[2][i]=new Stone(Type.Pawn,Color.White);
		
		/* initializing black stones */
		board[8][A]=new Stone(Type.Rook,Color.Black);
		board[8][B]=new Stone(Type.Knight,Color.Black);
		board[8][C]=new Stone(Type.Bishop,Color.Black);
		board[8][D]=new Stone(Type.Queen,Color.Black);
		board[8][E]=new Stone(Type.King,Color.Black);
		board[8][F]=new Stone(Type.Bishop,Color.Black);
		board[8][G]=new Stone(Type.Knight,Color.Black);
		board[8][H]=new Stone(Type.Rook,Color.Black);
		for (int i=0;i<=7;i++)
			board[7][i]=new Stone(Type.Pawn,Color.Black);
	}
	
	public Stone [][] getBoard()
	{	return board; }
	
	public Player getPlayerWhite()
	{
		return player_white;
	}
	public Player getPlayerBlack()
	{
		return player_black;
	}
	public Player getTurn()
	{
		return turn;
	}
//	public LinkedList<Stone> getAsideWhite()
//	{
//		return aside_white;
//	}
//	public LinkedList<Stone> getAsideBlack()
//	{
//		return aside_black;
//	}
	public void setBoard(Stone [][] b)
	{ board=b; }
	
	public void setPlayerWhite(Player pw)
	{
		player_white=pw;
	}
	public void setPlayerBlack(Player pb)
	{
		player_black=pb;
	}
	public void setTurn(Player t)
	{
		 turn=t;
	}
//	public void setAsideWhite(LinkedList<Stone> aw)
//	{
//		aside_white=aw;
//	}
//	public void getAsideBlack(LinkedList<Stone> ab)
//	{
//		aside_black=ab;
//	}
	public String toString()
	{
		return "player white: "+player_white.getName()+" player black: "+player_black.getName()+" turn: "+turn.getName();
		
	}
	public void switchTurn()
	{
		turn=(turn.equals(player_white)?player_black:player_white);
	}
}
