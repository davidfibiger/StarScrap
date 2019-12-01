import javax.imageio.ImageIO;

import java.awt.*;
import java.io.File;
public class DrawingCanvas extends Canvas {
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	DrawingCanvas(Frame f){
		
		
		setBackground(new Color(0,0,0));
		setLocation(f.getInsets().left,f.getInsets().top);
		setSize(f.getWidth() - (f.getInsets().left + f.getInsets().right), f.getHeight() - (f.getInsets().top + f.getInsets().bottom));
		
	}
	public void paint(Graphics g){
        super.paint(g);
       
        for(int i = 0; i < StarScrapMain.gameStatus.drawables.size(); i++) {
        	Drawable drawable = StarScrapMain.gameStatus.drawables.get(i);
        	drawable.paint(g, StarScrapMain.gameStatus);
        	
        }
       
	}
	
	
	
	
	
}
