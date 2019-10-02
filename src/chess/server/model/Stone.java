package chess.server.model;

public class Stone {
	private Color color;
	private Type type;
	public Stone()
	{
		
	}
	public Stone (Type t,Color c)
	{
		type=t;
		color=c;
	}
	public Color getColor()
	{
		return color;
	}
	public Type getType()
	{
		return type;
	}
	public void setColor(Color c)
	{
		this.color=c;
	}
	public void setType(Type t)
	{
		this.type=t;
	}
	public String toString()
	{
		if (type==Type.King) return "King"+(color==Color.Black?" black":" white");
		else if (type==Type.Knight) return "Knight"+(color==Color.Black?" black":" white");
		else if (type==Type.Pawn) return "Pawn"+(color==Color.Black?" black":" white");
		else if (type==Type.Queen) return "Queen"+(color==Color.Black?" black":" white");
		else if (type==Type.Rook) return "Rook"+(color==Color.Black?" black":" white");
		else  return "Bishop"+(color==Color.Black?" black":" white");
	}
}
