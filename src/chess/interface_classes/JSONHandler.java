package chess.interface_classes;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;

import javax.annotation.Resource;

import org.json.JSONArray;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import com.fasterxml.jackson.databind.SerializationFeature;

import chess.server.controller.Employee;
import chess.server.model.Color;
import chess.server.model.Game;
import chess.server.model.Player;
import chess.server.model.Stone;

public class JSONHandler {
	private static final ObjectMapper mapper=new ObjectMapper();

	public static String serialize (Object o)
	{
		mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
		String x="";
			try {
				x=mapper.writeValueAsString(o);
				
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return x;
	}
	public static Object deserialize (String s)
	{
		Object eliyo=new Object();
		try {
		
			eliyo=mapper.readValue(s,Object.class);
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(eliyo.getClass());
	
		return null;
	}
	
	// Prepares finished notification
	public static String prepareNotification (String winner)
	{
		
		return null;
	}
	// Prepares illegal move notification
	public static String prepareNotification ()
	{
			
		return null;
	}
	// Prepares game state notification
	public static String prepareNotification (Game g)
	{
		try {
			return mapper.writeValueAsString(g);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// Prepares move
	public static String prepareNotification (Player player, int from, int to)
	{
			
		return null;
	}
	// Prepares game join
	public static String prepareNotification (String name, Color color)
	{
			
		return null;
	}
}
