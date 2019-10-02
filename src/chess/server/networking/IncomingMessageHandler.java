package chess.server.networking;

import chess.interface_classes.JSONHandler;
import chess.server.controller.Controller;
import chess.server.controller.Employee;

public class IncomingMessageHandler {
	private Controller controller;
	protected IncomingMessageHandler (Controller c)
	{
		controller=c;
	}
	protected void handleMessage(String message)
	{	
		JSONHandler.deserialize(message);
	}
}
