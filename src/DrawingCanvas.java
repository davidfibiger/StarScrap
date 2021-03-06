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
       	
        for(int d = 0; d < StarScrapMain.gameStatus.drawables.size(); d++) {
        	Drawable drawable = StarScrapMain.gameStatus.drawables.get(d);
        	drawable.paint(g, StarScrapMain.gameStatus);
        	 
        }
        for(int p = 0; p < StarScrapMain.gameStatus.players.size(); p++) {
        	Player player = StarScrapMain.gameStatus.players.get(p);
        	player.drawHud(g, StarScrapMain.gameStatus);
        }
        drawText(g, "fps: "+String.valueOf(fps));
        
        
        
        if(GameStatus.menu) {
        	//g.drawImage(StarScrapMain.logo, (int)getWidth()/8, -(int)getHeight()/8, (int)((getWidth()/4)*3), (int)((getHeight()/4)*3), null);
        	g.drawImage(StarScrapMain.logo,(int)getWidth()/8, -(int)getHeight()/8, null);
        	g.drawImage(StarScrapMain.oneVOne, (int)(getWidth()/5*2), (int)getHeight()/5*3, (int)(getWidth()/5), (int)(getHeight()/5), null);
        }
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
		Font font = new Font(Font.SANS_SERIF, 0, (getWidth()/80));
		g.setColor(new Color(0, 245, 0));
        g.setFont(font);
        FontMetrics metrics = g.getFontMetrics(font);
        int width = metrics.stringWidth(text);
        g.drawString(text, getWidth() - getWidth()/2 - width/2, getHeight()/10);
	}
	
	
	
	
}
