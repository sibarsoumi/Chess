package chess.server.view;

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
	private JComboBox<Color> color_chooser;
	private Semaphore start_view_wait=null;
	private JButton submit;

	public StartView (Semaphore start_view_wait,Player remotePlayer)
	{
		this.start_view_wait=start_view_wait;
		color_chooser=new JComboBox<Color>( new Color [] {Color.Black,Color.White}  );
		setTitle("Game start");
		setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
		
		add(new JLabel("Incoming connection from: "+remotePlayer.getName()));
		
		add(new JLabel(remotePlayer.getName()+((remotePlayer.getColor()==null)?" has not selected a color.":" has selected "+remotePlayer.getColor())));
		add(new JLabel("Enter your name:"));
		name_chooser=new JTextField();
		add(name_chooser);
		add(new JLabel((remotePlayer.getColor()==null)?" Pick a color: ":"Your color is "+((remotePlayer.getColor()==Color.Black)?"White":"Black")));
		if (remotePlayer.getColor()==null)
			add(color_chooser);
		else if (remotePlayer.getColor()==Color.White)
			color_chooser.setSelectedItem(Color.Black);
		else if (remotePlayer.getColor()==Color.Black)
			color_chooser.setSelectedItem(Color.White);
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
	
	
}
