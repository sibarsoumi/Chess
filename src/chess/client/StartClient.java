package chess.client;

import org.json.JSONException;

import com.fasterxml.jackson.core.JsonProcessingException;

import chess.client.controller.Controller;

public class StartClient {
	public static void main (String [] args)
	{
	
		try {
			new Controller();
		} catch (JSONException | JsonProcessingException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
}
