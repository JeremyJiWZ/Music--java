import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import javax.swing.*;

public class Game extends JFrame
{
	GamePanel gamePanel;
	public Game()
	{
		gamePanel = new GamePanel(4);//�������Ŀǰֻ֧��4��
		gamePanel.setFre(5);//ʱ��Ƶ��
		gamePanel.setFallTime(3000);//��������ʱ��
		gamePanel.setTimeWidth(100);//���еļ��
		gamePanel.setTimePerfectWidth(50);//�߼����еļ��
		gamePanel.setBottom(30);
		gamePanel.setFocusable(true);
		add(gamePanel);
		
		ButtonManager buttonManager = new ButtonManager(gamePanel);
		Container content = getContentPane();
		content.add(buttonManager,BorderLayout.NORTH);

	}

	public static void main(String[] args)
	{
		Game frame = new Game();
		frame.setTitle("AnimationDemo");
		frame.setSize(600, 800);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setVisible(true);
	}
}


