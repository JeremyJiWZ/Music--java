import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

class GamePanel extends JPanel {
	Image[] image;
	private String message = "Welcome to Java";
	private int num = 4;
	private int timerFre = 50;// timer frequency
	private int timeFall = 3000;
	private int timeWidth = 10;
	private int timePerfectWidth = 5;
	private int bottomHeight = 30;
	private int time = 0;
	private int start;
	private int end = 0;
	private int blockWidth = 40;
	private int blockWidthHalf = blockWidth / 2;
	private int blockHeight = 16;
	private int blockHeightHalf = blockHeight / 2;
	private int score;
	private int maxScore = 0;
	List<List<Integer>> track;
	List<List<Integer>> scored;
	Hashtable key;
	private Timer timer;

	public GamePanel() {
		construct();
	}

	public GamePanel(int num) {
		construct();

		setNum(num);
	}

	private void construct() {
		image = new Image[num + 2];
		for (int i = 0; i < num; i++)
			image[i] = this.getToolkit().createImage(i + ".jpg");
		image[num] = this.getToolkit().createImage("hit.jpg");
		image[num + 1] = this.getToolkit().createImage("perfect.jpg");

		addKeyListener(new MyKeyListener());

		key = new Hashtable();
		key.put('d', 0);
		key.put('f', 1);
		key.put('j', 2);
		key.put('k', 3);

		time = 0;
		score = 0;
		maxScore = 0;
	}

	public void restart() {
		time = 0;
		score = 0;
		for (int i = 0; i < num; i++)
			for (int j = 0; j < scored.get(i).size(); j++)
				scored.get(i).set(j, 0);
	}

	public void resetEnd() {
		end = 0;
	}

	public void setNum(int num) {
		javax.swing.Timer timer = new javax.swing.Timer(500, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				repaint();
			}
		});
		track = new LinkedList<List<Integer>>();
		scored = new LinkedList<List<Integer>>();
		for (int i = 0; i < num; i++) {
			track.add(new LinkedList<Integer>());
			scored.add(new LinkedList<Integer>());
		}
	}

	public void setFre(int timerFre)// set timer frequency
	{
		this.timerFre = timerFre;
	}

	public void setFallTime(int timeFall)// set fall time in millisecond
	{
		this.timeFall = timeFall;
	}

	public void setTimeWidth(int timeWidth)// set tolerance
	{
		this.timeWidth = timeWidth;
	}

	public void setTimePerfectWidth(int timePerfectWidth)// set perfect
															// tolerance
	{
		this.timePerfectWidth = timePerfectWidth;
	}

	public void setBottom(int bottomHeight) {
		this.bottomHeight = bottomHeight;
	}

	public void addNode(int num, int time)// num: which track; time: millisecond
	{
		track.get(num).add(time);
		if (time > end)
			end = time;
		scored.get(num).add(0);
		maxScore += 2;
	}

	public int getStartTime() {
		return start;
	}

	public void play() {
		restart();
		start = (int) System.currentTimeMillis() - timeFall;
		timer = new Timer(timerFre, new TimerListener());
		timer.start();
		// System.out.println("start()");
		// System.out.println("timeFall = " + timeFall);
		// System.out.println("end = " + end);
		//
		// System.out.println("blockWidth" + blockWidth);
		// System.out.println("blockWidthHalf" + blockWidthHalf);
		// System.out.println("blockHeight" + blockHeight);
		// System.out.println("blockHeightHalf" + blockHeightHalf);
		// System.out.println("timePerfectWidth" + timePerfectWidth);
		// System.out.println("timeWidth" + timeWidth);

		requestFocus();
	}

	public void stop() {
		if (timer != null && timer.isRunning())
			timer.stop();
		time = 0;
	}

	ImageIcon img = new ImageIcon("bg.jpg");

	@Override
	protected void paintComponent(Graphics g) {
		// System.out.println("paintComponent");
		super.paintComponent(g);
		g.drawImage(img.getImage(), 0, 0, null);

		int width = getWidth();
		int realHeight = getHeight();
		int height = realHeight / 4 * 3;
		int widthLength = width / (num + 1);

		int x0, y0;
		int note;

		for (int i = 0; i < num; i++) {
			x0 = widthLength * (i + 1) - blockWidthHalf;
			for (int j = 0; j < track.get(i).size(); j++) {
				note = track.get(i).get(j);
				if (!(note <= time && time <= note + timeFall + 1000))
					continue;
				y0 = (int) (-blockHeightHalf + (0.0 + time - note) / timeFall * height);
				if (scored.get(i).get(j) == 2)
					g.drawImage(image[num + 1], x0, y0, blockWidth, blockHeight, this);
				if (scored.get(i).get(j) == 1)
					g.drawImage(image[num], x0, y0, blockWidth, blockHeight, this);
				if (scored.get(i).get(j) == 0)
					g.drawImage(image[i], x0, y0, blockWidth, blockHeight, this);// draw
																					// another
																					// image
																					// by
																					// judging
																					// scored
			}
		}

		if (timer != null && timer.isRunning()) {
			g.drawLine(0, height - blockHeightHalf, width, height - blockHeightHalf);
			g.drawLine(0, height + blockHeightHalf, width, height + blockHeightHalf);
		}
		Font f = new Font("", 0, 18);
		g.setFont(f);
		g.drawString("score:" + score + "/" + maxScore, 0, realHeight);
		g.drawString("time:" + (end - time + timeFall + 1000) / 1000, width-150, realHeight);
	}

	class TimerListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// System.out.println("time="+time);
			time = (int) (System.currentTimeMillis() - start);
			// int x = (int) (System.currentTimeMillis()/1000);
			// System.out.println(""+x);
			repaint();
			if (time > end + timeFall + 1000) {
				timer.stop();
				JOptionPane.showMessageDialog(getParent(), "恭喜你！\n共获得了" + score + "分", "得分",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	class MyKeyListener extends KeyAdapter {
		@Override
		public void keyTyped(KeyEvent e) {
			super.keyTyped(e);
			int t = 0;
			try {
				t = (int) key.get(e.getKeyChar());
			} catch (Exception ex) {
				return;
			}
			System.out.println("" + t);
			for (int i = 0; i < track.get(t).size(); i++) {
				// System.out.println(""+scored.get(t).get(i));
				if (scored.get(t).get(i) == 0 && track.get(t).get(i) + timeFall - timePerfectWidth <= time
						&& time <= track.get(t).get(i) + timeFall + timePerfectWidth) {
					score += 2;
					scored.get(t).set(i, 2);
				}
				if (scored.get(t).get(i) == 0 && track.get(t).get(i) + timeFall - timeWidth <= time
						&& time <= track.get(t).get(i) + timeFall + timeWidth) {
					score += 1;
					scored.get(t).set(i, 1);
				}
			}
		}
	}
}
