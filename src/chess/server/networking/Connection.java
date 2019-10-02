package chess.server.networking;

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

import chess.server.controller.Controller;

public class Connection extends Thread {
	private Semaphore connection_wait=null;
	private ServerSocket server_socket=null;
	private Socket server_client_socket=null;
	private BufferedReader breader = null;
	private BufferedWriter bwriter = null;
	private DataOutputStream out_stream=null;
	private DataInputStream in_stream = null;
	private Queue<String> messageQueue_out;
	private Controller controller=null;
	public Connection (Semaphore connection_wait,Controller controller,Queue<String> messageQueue_out)
	{
		this.connection_wait=connection_wait;
		this.controller=controller;
		this.messageQueue_out=messageQueue_out;
	}
	
	@Override
	public void run() {
				try {
					server_socket=new ServerSocket(33098);
					server_client_socket=server_socket.accept();
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
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					 System.out.println(e.getMessage());
					e.printStackTrace();
				}
				
				
			
	}

}
