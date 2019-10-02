package chess.server.view;

import java.awt.BorderLayout;
import java.awt.GraphicsEnvironment;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ConnectionWaiting extends JFrame {
	private ConnectionWaiting ()
	{
		setTitle("Chess");
		add(new JLabel("Waiting for conection"));
		setSize(400,200);
		setLocation(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getWidth()/2-(this.getWidth()/2),GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getHeight()/2-(this.getHeight()/2));
		setVisible(true);
	}
	public static void main (String [] a)
	{
		new ConnectionWaiting();
	}
}
