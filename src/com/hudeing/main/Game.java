package com.hudeing.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Game extends Canvas implements Runnable, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static int WIDTH = 480;
	public static final int HEIGHT = 480;
	public BufferedImage sprite1;
	public BufferedImage sprite2;
	public int x1 = 30, y1 = 90;
	public int x2 = 100, y2 = 100;
	public int[] pixels1;
	public int[] pixels2;
	
	public Game() {
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.addMouseListener(this);
		try {
			sprite1 = ImageIO.read(getClass().getResource("/sprite1.png"));
			sprite2 = ImageIO.read(getClass().getResource("/sprite2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		pixels1 = new int[sprite1.getWidth() * sprite1.getHeight()];
		sprite1.getRGB(0, 0, sprite1.getWidth(), sprite1.getHeight(), pixels1, 0, sprite1.getWidth());
		pixels2 = new int[sprite2.getWidth() * sprite2.getHeight()];
		sprite2.getRGB(0, 0, sprite2.getWidth(), sprite2.getHeight(), pixels2, 0, sprite2.getWidth());	
	}
	
	public void update() {
		x1++;
		if(this.isCollidingPerfect(x1, y1, x2, y2, pixels1, pixels2, sprite1, sprite2)) {
			System.out.println("Estão colidindo!");
		}
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.setColor(new Color(0, 0, 0));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.drawImage(sprite1, x1, y1, null);
		g.drawImage(sprite2, x2, y2, null);
		g.dispose();
		bs.show();
	}
	
	public boolean isCollidingPerfect(int x1, int y1, int x2, int y2, int[] pixels1, int[] pixels2, BufferedImage sprite1, BufferedImage sprite2) {
		for(int xx1 = 0; xx1 < sprite1.getWidth(); xx1++) {
			for(int yy1 = 0; yy1 < sprite1.getHeight(); yy1++) {
				for(int xx2 = 0; xx2 < sprite2.getWidth(); xx2++) {
					for(int yy2 = 0; yy2 < sprite2.getHeight(); yy2++) {
						int pixelAtual1 = pixels1[xx1 + yy1 * sprite1.getWidth()];
						int pixelAtual2 = pixels2[xx2 + yy2 * sprite2.getWidth()];
						if(pixelAtual1 == 0x00000000 || pixelAtual2 == 0x00000000) { // if(pixelAtual1 ==  || pixelAtual2 == 0x00FFFFFF) {
							continue;
						}
						if(xx1 + x1 == xx2 + x2 && yy1 + y1 == yy2 + y2) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		JFrame frame = new JFrame();
		frame.setTitle("Collision Pixel Perfect");
		frame.add(game);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setVisible(true);
		new Thread(game).start();
	}

	@Override
	public void run() {
		double fps = 60.0;
		while(true) {
			update();
			render();
			try {
				Thread.sleep((int)(1000 / fps));
			} catch (InterruptedException e) {}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
