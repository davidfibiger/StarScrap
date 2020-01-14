import javax.imageio.ImageIO;

import java.awt.*;
import java.io.File;
public class DrawingCanvas extends Canvas {
	private Image dbImage;
	private Graphics dbg;
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	DrawingCanvas(Frame f){
		
		
		setBackground(new Color(0,0,0));
		setLocation(f.getInsets().left,f.getInsets().top);
		setSize(f.getWidth() - (f.getInsets().left + f.getInsets().right), f.getHeight() - (f.getInsets().top + f.getInsets().bottom));
		
	}
	public void update(Graphics g) {
		if(dbImage ==null) {
			dbImage = createImage(getWidth(), getHeight());
			dbg = dbImage.getGraphics();
		}
		dbg.setColor(getBackground());
		dbg.fillRect(0, 0, getWidth(), getHeight());		
		dbg.setColor(getForeground());
		
		paint(dbg);
		g.drawImage(dbImage, 0, 0, this);
	}
	public void paint(Graphics g){
        //super.paint(g);
		
        for(int i = 0; i < StarScrapMain.gameStatus.drawables.size(); i++) {
        	Drawable drawable = StarScrapMain.gameStatus.drawables.get(i);
        	drawable.paint(g, StarScrapMain.gameStatus);
        	
        }
        
	}
	/*public void paint() {
		
	}*/
	
	
	
	
	
}
