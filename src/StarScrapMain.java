import java.awt.Image;
import java.awt.*;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
public class StarScrapMain {
	static GameStatus gameStatus;
	static Image starShip1;
	static Image starShip2;
	static Image starShip3;
	static Image logo;
	static Image oneVOne;
	static String shotSoundPath = "Sounds\\pew.wav";
	static String deathSoundPath = "Sounds\\death.wav";
	static String laserSoundPath = "Sounds\\laser.wav";
	static DrawingCanvas canvas;
	public static void main(String[] args) {
		JFrame  f = new JFrame();		
		f.setExtendedState(JFrame.MAXIMIZED_BOTH);
		f.setUndecorated(true);
		f.setLayout(null);
		ProgrammeKiller pk = new ProgrammeKiller();
		f.addWindowListener(pk);
		f.setVisible(true);
		canvas = new DrawingCanvas(f);
		gameStatus = new GameStatus();
		
		f.add(canvas);
		gameStatus.setCanvas(canvas);
		
		try {
            starShip1 = ImageIO.read(new File("textures\\starShip1.png"));
            starShip2 = ImageIO.read(new File("textures\\starShip2.png"));
            starShip3 = ImageIO.read(new File("textures\\starShip3.png"));
            logo = ImageIO.read(new File("textures\\logo10.png"));
            oneVOne = ImageIO.read(new File("textures\\1v1.png"));
            
        }catch( java.io.IOException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }		
		  
		GameController controller = new GameController(gameStatus);
		canvas.addMouseListener(controller);
		canvas.addKeyListener(controller);
		controller.run(canvas);
		
		
	}

}
