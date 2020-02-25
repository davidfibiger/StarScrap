import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.File;
public class DrawingCanvas extends Canvas {
	
	private BufferStrategy strategy;
	private Image dbImage;
	//private Graphics dbg;
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	DrawingCanvas(JFrame f){
		
		setIgnoreRepaint(true);
		JPanel panel = (JPanel) f.getContentPane();
		panel.add(this);
		panel.setPreferredSize(f.getSize());
		createBufferStrategy(2);
		strategy = getBufferStrategy();
		setBackground(new Color(0,0,0));
		
		
		setLocation(0,0);
		setSize(panel.getWidth(), panel.getHeight());
	}
	
	public BufferStrategy getStrategy() {
		return strategy;
	}
	public void setStrategy(BufferStrategy strategy) {
		this.strategy = strategy;
	}
	public void paint(Graphics g, int fps){
       	
        for(int i = 0; i < StarScrapMain.gameStatus.drawables.size(); i++) {
        	Drawable drawable = StarScrapMain.gameStatus.drawables.get(i);
        	drawable.paint(g, StarScrapMain.gameStatus);
        	 
        }
        drawText(g, String.valueOf(fps));
	}

	public void newFrame(int fps) {
		// TODO Auto-generated method stub
		Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
		
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());		
		g.setColor(getForeground());
		
		paint(g,fps);
		g.dispose();
		strategy.show();
	}
	private void drawText(Graphics g, String text) {
		g.setColor(new Color(0, 245, 0));
        g.setFont(new Font(Font.SANS_SERIF, 0, (getWidth()/80)));
        g.drawString("fps: " + text, getWidth() - getWidth()/2 - text.length()*(getWidth()/128), getHeight()/10);
	}
	
	
	
	
}
