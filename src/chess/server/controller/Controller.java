package chess.server.controller;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;

import javax.swing.JOptionPane;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import chess.interface_classes.JSONHandler;
import chess.interface_classes.Move;
import chess.model.Color;
import chess.model.Game;
import chess.model.Player;
import chess.server.networking.Connection;
import chess.server.view.ConnectionWaiting;
import chess.server.view.MainView;
import chess.server.view.StartView;

public class Controller {
	private static final ObjectMapper mapper=new ObjectMapper();
	private Connection connection;
	private Semaphore connection_wait=new Semaphore(0);
	private Semaphore start_view_wait=new Semaphore(0);
	private Game game;
	private Player me;
	private MainView mainView;
	private Queue<String> messageQueue_out;
	public Controller() throws InterruptedException
	{	
		messageQueue_out=new ConcurrentLinkedQueue<String>();
		connection=new Connection(connection_wait,this,messageQueue_out);
		connection.start();
		
	}
	public void processMessage (String message) throws JsonParseException, JsonMappingException, JSONException, IOException
	{
		JSONObject receivedMessage=new JSONObject (message);
		switch ((String)receivedMessage.get("type"))
		{
		case "GameJoin":
			Player remotePlayer=mapper.readValue((String)receivedMessage.get("data"), Player.class);
			StartView start_view=new StartView(start_view_wait,remotePlayer);
			try {
				start_view_wait.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Player serverPlayer=start_view.getPlayer();
			me = serverPlayer;
			if (serverPlayer.getColor()==Color.White)
				game=new Game(serverPlayer,remotePlayer);
			else game=new Game(remotePlayer,serverPlayer);
			mainView=new MainView(me,this);
			mainView.update(game);
			JSONObject toSendMessage=new JSONObject();
			toSendMessage.put("type", "GameStateNotification");
			toSendMessage.put("data", mapper.writeValueAsString(game));
			messageQueue_out.add(toSendMessage.toString());
			break;
		case "Move":
			Move move=mapper.readValue((String)receivedMessage.get("data"), Move.class);
			if (!isOnTurn(move))
			{
				toSendMessage=new JSONObject();
				toSendMessage.put("type", "IllegalMove");
				toSendMessage.put("data", "It is not your turn");
				messageQueue_out.add(toSendMessage.toString());
			}
			else if (!isLegalMove(move)) 
			{
				toSendMessage=new JSONObject();
				toSendMessage.put("type", "IllegalMove");
				toSendMessage.put("data", "This stone cannot move this way");
				messageQueue_out.add(toSendMessage.toString());
			}
			else
				{
				performMove(move);
				toSendMessage=new JSONObject();
				toSendMessage.put("type", "GameStateNotification");
				toSendMessage.put("data", mapper.writeValueAsString(game));
				messageQueue_out.add(toSendMessage.toString());
				}
			break;
		
		}
		
	}
	public void processMoveRequest (Move move) throws JSONException, JsonProcessingException
	{	
		move.setMover(me);
		if (!isOnTurn(move))
		{
			mainView.showWarningIllegalMove("It is not your turn");
		}
		else if (!isLegalMove(move)) 
		{
			mainView.showWarningIllegalMove("This stone cannot move this way");
		}
		else
			{
			performMove(move);
			JSONObject toSendMessage=new JSONObject();
			toSendMessage.put("type", "GameStateNotification");
			toSendMessage.put("data", mapper.writeValueAsString(game));
			messageQueue_out.add(toSendMessage.toString());
			}
	}
	private void performMove(Move move)
	{
		game.getBoard()[move.getTo().getX()][move.getTo().getY()]=game.getBoard()[move.getFrom().getX()][move.getFrom().getY()];
		game.getBoard()[move.getFrom().getX()][move.getFrom().getY()]=null;
		mainView.update(game);
		game.switchTurn();
	}
	private boolean isLegalMove (Move move)
	{
		// here checking if it is a legal move for this stone
		return true;
	}
	private boolean isOnTurn (Move move)
	{
		if (!move.getMover().equals(game.getTurn()))
			return false;
		return true;
	}
}
