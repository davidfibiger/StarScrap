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
	static String shotSoundPath = "d:\\david\\programovani\\Eclipse\\eclipse\\workSpace\\StarScrap\\Sounds\\pew.wav";
	static String deathSoundPath = "d:\\david\\programovani\\Eclipse\\eclipse\\workSpace\\StarScrap\\Sounds\\death.wav";
	static String laserSoundPath = "d:\\david\\programovani\\Eclipse\\eclipse\\workSpace\\StarScrap\\Sounds\\laser.wav";
	public static void main(String[] args) {
		JFrame  f = new JFrame();		
		f.setExtendedState(JFrame.MAXIMIZED_BOTH);
		f.setUndecorated(true);
		f.setLayout(null);
		ProgrammeKiller pk = new ProgrammeKiller();
		f.addWindowListener(pk);
		gameStatus = new GameStatus();
		f.setVisible(true);
		DrawingCanvas canvas = new DrawingCanvas(f);
		f.add(canvas);
		gameStatus.setCanvas(canvas);
		
		try {
            starShip1 = ImageIO.read(new File("d:\\david\\programovani\\Eclipse\\eclipse\\workSpace\\StarScrap\\textures\\starShip1.png"));
            starShip2 = ImageIO.read(new File("d:\\david\\programovani\\Eclipse\\eclipse\\workSpace\\StarScrap\\textures\\starShip2.png"));
            starShip3 = ImageIO.read(new File("d:\\david\\programovani\\Eclipse\\eclipse\\workSpace\\StarScrap\\textures\\starShip3.png"));
            
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
