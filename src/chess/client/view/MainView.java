package chess.client.view;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

import org.json.JSONException;

import com.fasterxml.jackson.core.JsonProcessingException;

import chess.interface_classes.Move;
import chess.interface_classes.Position;
import chess.client.controller.Controller;
import chess.model.Game;
import chess.model.Player;

public class MainView extends JFrame {
	private class Square extends JPanel
	{
		private Position position;
		private Square (Position position)
		{
			this.position=position;
			super.addMouseListener(new MouseAdapter() {
				
						@Override
						public void mousePressed (MouseEvent e)
						{
							move.setFrom(position);
							pressed=true;
						}
						@Override
						public void  mouseEntered (MouseEvent e)
						{
							if (pressed) move.setTo(position);
						}
						@Override
						public void  mouseReleased (MouseEvent e)
						{
							try {
								controller.processMoveRequest(move);
								pressed=false;
							} catch (JSONException | JsonProcessingException e1) {
						//		 TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					});
		}
		
	}
	private boolean pressed=false;
	private Move move=new Move();
	private Square board [][]=new Square [9][8];
	private Player me;
	private Controller controller;
	public MainView(Player me, Controller controller)
	{	
		setTitle("Chess game");
		this.me=me;
		this.controller=controller;
		setLayout(new GridLayout(8,8));
		
		if (me.getColor()==chess.model.Color.White)
			for (int i=8;i>=1;i--)
				for (int j=0;j<=7;j++)
				{
					board[i][j]=new Square(new Position(i,j));
					board[i][j].setBackground((i+j)%2==0?Color.WHITE:Color.GRAY);
					add(board[i][j]);
				}
		else if (me.getColor()==chess.model.Color.Black)
			for (int i=1;i<=8;i++)
				for (int j=7;j>=0;j--)
				{
					board[i][j]=new Square(new Position(i,j));
					board[i][j].setBackground((i+j)%2==0?Color.WHITE:Color.GRAY);
					add(board[i][j]);
				}
		setSize(800,800);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	public void update (Game game)
	{
		for (int i=8;i>=1;i--)
			for (int j=0;j<=7;j++)
			{
				board[i][j].removeAll();
				if (game.getBoard()[i][j]!=null)
					{
					BufferedImage myPicture;
					try {
						myPicture = ImageIO.read(new File("resources/"+game.getBoard()[i][j].getColor()+"/"+game.getBoard()[i][j].getType()+".png"));
						JLabel picLabel = new JLabel(new ImageIcon(myPicture));
						board[i][j].add(picLabel);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					}
			}
		revalidate();
		repaint();
		
	}
	public void showWarningIllegalMove (String reason)
	{
		JOptionPane.showMessageDialog(this, reason);
	}
	
}
