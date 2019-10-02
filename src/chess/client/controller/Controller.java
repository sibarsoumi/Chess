package chess.client.controller;

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

import chess.model.Game;
import chess.model.Player;
import chess.client.networking.Connection;
import chess.client.view.StartView;
import chess.interface_classes.Move;
import chess.client.view.MainView;


public class Controller {
	private static final ObjectMapper mapper=new ObjectMapper();
	private Connection connection;
	private Semaphore connection_wait=new Semaphore(0);
	private Semaphore start_view_wait=new Semaphore(0);
	private Game game;
	private Player me;
	private MainView mainView;
	private Queue<String> messageQueue_out;
	public Controller() throws InterruptedException, JSONException, JsonProcessingException
	{
		messageQueue_out=new ConcurrentLinkedQueue<String>();
		StartView start_view=new StartView(start_view_wait);
		try {
			start_view_wait.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		me=start_view.getPlayer();
		connection=new Connection(connection_wait,this,start_view.getIP(),start_view.getPort(),messageQueue_out);
		connection.start();
		connection_wait.acquire();
		JSONObject toSendMessage=new JSONObject();
		toSendMessage.put("type", "GameJoin");
		toSendMessage.put("data", mapper.writeValueAsString(start_view.getPlayer()));
		messageQueue_out.add(toSendMessage.toString());
		mainView=new MainView(me,this);
		
	}
	public void processMessage (String message) throws JsonParseException, JsonMappingException, JSONException, IOException
	{
		JSONObject receivedMessage=new JSONObject (message);
		switch ((String)receivedMessage.get("type"))
		{
		case "GameStateNotification":
			Game game=mapper.readValue((String)receivedMessage.get("data"), Game.class);
			mainView.update(game);
			break;
			
		case "IllegalMove":
			String reason=((String)receivedMessage.get("data"));
			mainView.showWarningIllegalMove(reason);
			break;
		
		}
		
	}
	public void processMoveRequest (Move move) throws JSONException, JsonProcessingException
	{	
			move.setMover(me);
			JSONObject toSendMessage=new JSONObject();
			toSendMessage.put("type", "Move");
			toSendMessage.put("data", mapper.writeValueAsString(move));
			messageQueue_out.add(toSendMessage.toString());
	}
	
}
