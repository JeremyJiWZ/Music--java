import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ButtonManager extends JPanel {
	GamePanel gamePanel;
	JButton play1 = new JButton();
	JButton play2 = new JButton();
	JButton play3 = new JButton();
	JButton play4 = new JButton();
	JButton play5 = new JButton();

	public ButtonManager(GamePanel p) {
		// TODO Auto-generated constructor stub
		// setLayout(null);
		gamePanel = p;
		play1.setLayout(null);
		play1.setIcon(new ImageIcon("play.png"));
		play2.setLayout(null);
		play2.setIcon(new ImageIcon("play.png"));
		play3.setLayout(null);
		play3.setIcon(new ImageIcon("play.png"));
		play4.setLayout(null);
		play4.setIcon(new ImageIcon("play.png"));
		play5.setLayout(null);
		play5.setIcon(new ImageIcon("play.png"));
		play1.setSize(40, 40);
		play1.setLocation(20, 20);
		play2.setSize(40, 40);
		play2.setLocation(60, 20);
		play3.setSize(40, 40);
		play3.setLocation(100, 20);
		play4.setSize(40, 40);
		play4.setLocation(140, 20);
		play5.setSize(40, 40);
		play5.setLocation(180, 20);

		add(play1);
		add(play2);
		add(play3);
		add(play4);
		add(play5);

		play1.addActionListener(new Play1Listener());
		play2.addActionListener(new Play2Listener());
		play3.addActionListener(new Play3Listener());
		play4.addActionListener(new Play4Listener());
		play5.addActionListener(new Play5Listener());
	}

	class Play1Listener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			// open rhythm data
			openFile("yede.dat");
			// begin to draw
			gamePanel.play();
			// music
			play("yede.mid");
		}
	}

	class Play2Listener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			// open rhythm data
			openFile("canonInD.dat");
			// begin to draw
			gamePanel.play();
			delay();
//			delay();
			// play music
			play("canonInD.mid");
		}
	}

	class Play3Listener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			openFile("wangle.dat");
			gamePanel.play();
			play("wangle.mid");
		}
	}

	class Play4Listener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			openFile("xiaoxinxin2.dat");
			gamePanel.play();
			play("xiaoxinxin.mid");
		}
	}
	class Play5Listener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			openFile("yongqi.dat");
			gamePanel.play();
			play("yongqi.mid");
		}
	}

	void openFile(String fileName) {
		java.io.File file = new File(fileName);
		Scanner input = null;
		try {
			input = new Scanner(file);
		} catch (FileNotFoundException e1) {
			System.out.println("Can't open file");
		}
		int num = 0, ni;
		gamePanel.resetEnd();
		gamePanel.stop();
		gamePanel.setNum(4);
		while (input.hasNext()) {
			ni = input.nextInt();
			if (ni == -1) {
				num++;
				continue;
			}
			gamePanel.addNode(num, ni);
		}
		input.close();
	}

    //no use
	void delay() {
		// delay 3s
		Timer timer = new Timer();// 实例化Timer类
		timer.schedule(new TimerTask() {
			public void run() {
				System.out.println("退出");
				this.cancel();
			}
		}, 1000);// 3秒
	}
	
	Sequencer sequencer = null;
	void play(String file) {
		// music
		File file1 = new File(file);
		Sequence sequence;
		try {
			if (sequencer == null) {
				sequencer = MidiSystem.getSequencer();
				sequencer.open();
			}
			else
				sequencer.stop();
			sequence = MidiSystem.getSequence(file1);
			sequencer.setSequence(sequence);
			sequencer.start();
		} catch (MidiUnavailableException ex) {
			ex.printStackTrace();
		} catch (InvalidMidiDataException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
