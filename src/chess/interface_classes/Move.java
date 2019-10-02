package chess.interface_classes;

import chess.model.Player;

public class Move {
	private Player mover;
	private Position from;
	private Position to;
	public Player getMover() {
		return mover;
	}
	public void setMover(Player mover) {
		this.mover = mover;
	}
	public Position getFrom() {
		return from;
	}
	public void setFrom(Position from) {
		this.from = from;
	}
	public Position getTo() {
		return to;
	}
	public void setTo(Position to) {
		this.to = to;
	}
	
}
