package chess.server;

import chess.server.controller.Controller;

public class StartServer {
	public static void main (String [] args)
	{
		try {
			new Controller();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
