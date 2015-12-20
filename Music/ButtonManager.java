import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.nio.channels.NonWritableChannelException;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.xml.crypto.Data;

public class ButtonManager extends JPanel{
	GamePanel gamePanel;
	JButton play1 = new JButton();
	JButton play2 = new JButton();
	JButton play3 = new JButton();
	JButton play4 = new JButton();
	
	public ButtonManager(GamePanel p) {
		// TODO Auto-generated constructor stub
//		setLayout(null);
		gamePanel = p;
		play1.setLayout(null);play1.setIcon(new ImageIcon("play.png"));
		play2.setLayout(null);play2.setIcon(new ImageIcon("play.png"));
		play3.setLayout(null);play3.setIcon(new ImageIcon("play.png"));
		play4.setLayout(null);play4.setIcon(new ImageIcon("play.png"));
		play1.setSize(40,40);play1.setLocation(20,20);
		play2.setSize(40,40);play2.setLocation(60, 20);
		play3.setSize(40,40);play3.setLocation(100,20);
		play4.setSize(40,40);play4.setLocation(140, 20);
		
		add(play1);
		add(play2);
		add(play3);
		add(play4);
		
		play1.addActionListener(new Play1Listener());
		play2.addActionListener(new Play2Listener());
	}
	class Play1Listener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			//open rhythm data
			openFile("data.dat");
			//music
			play("canonInD.mid");
		}
	}
	class Play2Listener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			//open rhythm data
			openFile("canon.txt");
			//play music
			play("1.wav");
		}
	}
	class Play3Listener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
	class Play4Listener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
	void openFile(String fileName){
		java.io.File file = new File(fileName);
		Scanner input = null;
		try
		{
			input = new Scanner(file);
		} catch (FileNotFoundException e1)
		{
			System.out.println("Can't open file");
		}
		int num=0, ni;
		gamePanel.resetEnd();
		gamePanel.stop();
		while (input.hasNext())
		{
			ni = input.nextInt();
			if (ni==-1)
			{
				num++; continue;
			}
			gamePanel.addNode(num, ni);
		}
		input.close();
	}
	void play(String file){
		//music
		File file1 = new File(file);
		AudioClip sound1 = null;
		try
		{
			sound1 = Applet.newAudioClip(file1.toURL());
		} catch (MalformedURLException ex)
		{
			ex.printStackTrace();
		}
		gamePanel.play();
		while (System.currentTimeMillis()-gamePanel.getStartTime()<=3000)
		{}
		sound1.play();
		
	}
}
