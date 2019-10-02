package chess.client.networking;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Queue;
import java.util.concurrent.Semaphore;

import chess.client.controller.Controller;
import chess.client.model.Player;

public class Connection extends Thread {
	private String ip;
	private int port;
	private Semaphore connection_wait=null;
	private Socket server_client_socket=null;
	private BufferedReader breader = null;
	private BufferedWriter bwriter = null;
	private DataOutputStream out_stream=null;
	private DataInputStream in_stream = null;
	private Queue<String> messageQueue_out;
	private Controller controller=null;
	public Connection (Semaphore connection_wait,Controller controller,String ip, int port,Queue<String> messageQueue_out)
	{
		this.connection_wait=connection_wait;
		this.controller=controller;
		this.messageQueue_out=messageQueue_out;
		this.ip=ip;
		this.port=port;
	}
	
	@Override
	public void run() {
		try {
			server_client_socket=new Socket(ip,port);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			 System.out.println(e.getMessage());
			e.printStackTrace();
		}
		connection_wait.release();
				try {
					breader = new BufferedReader(new InputStreamReader(new DataInputStream(server_client_socket.getInputStream()),StandardCharsets.UTF_8));
					bwriter = new BufferedWriter(new OutputStreamWriter(new DataOutputStream(server_client_socket.getOutputStream()),StandardCharsets.UTF_8));
					
					while (true)
					{
							if(messageQueue_out.size() > 0)
								{
								String message = messageQueue_out.poll();
								message+="\n";
								bwriter.write(message);
								bwriter.flush();
								}
							
							if(breader.ready())
								{
								String message=breader.readLine();
								controller.processMessage(message);
								}
						
						
					}
				}
				 catch (Exception e) {
					// TODO Auto-generated catch block
					 System.out.println(e.getMessage());
					e.printStackTrace();
				}
	}

}
