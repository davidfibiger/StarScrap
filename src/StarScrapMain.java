import java.awt.Image;
import java.awt.*;
import java.io.File;

import javax.imageio.ImageIO;
public class StarScrapMain {
	static GameStatus gameStatus;
	static Image starShip1;
	static Image starShip2;
	static Image starShip3;
	static String shotSoundPath = "d:\\david\\programovani\\Eclipse\\eclipse\\workSpace\\StarScrap\\Sounds\\laserBullet.wav";
	
	public static void main(String[] args) {
		Frame  f = new Frame();		
		f.setExtendedState(Frame.MAXIMIZED_BOTH);
		f.setLayout(null);
		ProgrammeKiller pk = new ProgrammeKiller();
		f.addWindowListener(pk);
		f.setMinimumSize(new Dimension(600, 600));
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
