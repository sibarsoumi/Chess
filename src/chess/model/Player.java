package chess.model;



public class Player{
	private String name;
	private Color color;
	public Player ()
	{
		
	}
	public Player(String n, Color c)
	{
		name=n;
		color=c;
	}
	public String getName()
	{
		return name;
	}
	public Color getColor()
	{
		return color;
	}
	public void setName(String n)
	{
		name=n;
	}
	public void setColor(Color c)
	{
		color=c;
	}

	public boolean equals (Player p) {
		// TODO Auto-generated method stub
		if (this.name.equals(p.getName()) && this.color.equals(p.getColor()))
			 return true;
		return false;
	}
}
