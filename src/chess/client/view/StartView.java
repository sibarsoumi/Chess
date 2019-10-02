package chess.client.view;

import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.util.concurrent.Semaphore;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import chess.model.Color;
import chess.model.Player;

public class StartView extends JFrame {
	private JTextField name_chooser;
	private JTextField ip_chooser;
	private JTextField port_chooser;
	private JComboBox<Color> color_chooser;
	private Semaphore start_view_wait=null;
	private JButton submit;

	public StartView (Semaphore start_view_wait)
	{
		this.start_view_wait=start_view_wait;
		
		setTitle("Game start");
		setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
		
		
		add (new JLabel("Your name:"));
		name_chooser=new JTextField();
		add(name_chooser);
		add (new JLabel("Your color:"));
		color_chooser=new JComboBox<Color>( new Color [] {Color.Black,Color.White}  );
		add(color_chooser);
		add (new JLabel("IP of the server:"));
		ip_chooser=new JTextField();
		add(ip_chooser);
		port_chooser=new JTextField();
		// add (new JLabel("Port:"));
		// add(port_chooser);
		port_chooser.setText("33098");
		
		submit=new JButton("Start game");
		add(submit);
		submit.addActionListener((e)->{
			this.dispose();
			start_view_wait.release();
		});
		
		pack();
		setLocation(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getWidth()/2-(this.getWidth()/2),GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getHeight()/2-(this.getHeight()/2));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	public Player getPlayer()
	{
		return new Player (name_chooser.getText(),(Color)color_chooser.getSelectedItem());
	}
	public String getIP()
	{
		return ip_chooser.getText();
	}
	public int getPort()
	{
		return Integer.parseInt(port_chooser.getText());
	}
	
}
