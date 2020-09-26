import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
public class StarScrapMain {
	static GameStatus gameStatus;
	static Image starShip1;
	static Image starShip2;
	static Image starShip3;
	static Image logo;
	static Image oneVOne;
	static Image meteorite1;
	static Image meteorite2;
	static String shotSoundPath = "Sounds\\pew.wav";
	static String deathSoundPath = "Sounds\\death.wav";
	static String laserSoundPath = "Sounds\\laser.wav";
	static String colisionSoundPath = "Sounds\\colision.wav";
	static DrawingCanvas canvas;
	static List<String> stringControls;
	static Map<String, Integer> controls;
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
			meteorite1 = ImageIO.read(new File("textures\\meteorite.png"));
			meteorite2 = ImageIO.read(new File("textures\\meteorite2.png"));
            starShip1 = ImageIO.read(new File("textures\\starShip1.png"));
            starShip2 = ImageIO.read(new File("textures\\starShip2.png"));
            starShip3 = ImageIO.read(new File("textures\\starShip3.png"));
            logo = ImageIO.read(new File("textures\\logo20.png"));
            logo = logo.getScaledInstance((int)((canvas.getWidth()/4)*3), (int)((canvas.getHeight()/4)*3), Image.SCALE_SMOOTH);
            oneVOne = ImageIO.read(new File("textures\\1v1.png"));            
            stringControls = scanControlsFile();
            
            controls = converControls(stringControls);
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
	private static Map<String, Integer> converControls(List<String> stringControls2) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		for(String row : stringControls2) {	
			String[] split = row.split("=");
			if(split.length == 2) {
				map.put(split[0].trim().toUpperCase(), convertToInt(split[1].trim().toUpperCase()));
			}else {
				System.out.println("controlls are wrong at: "+row);
				System.exit(1);
			}
			
		}
		return map;
	}
	private static Integer convertToInt(String key) {
		int intKey = 0;
		if(key.length() == 1) {
			intKey = (int)key.charAt(0);
		}else {
			if("SPACE".equals(key))
				return KeyEvent.VK_SPACE;
			if("F1".equals(key))
				return KeyEvent.VK_F1;
			if("ENTER".equals(key))
				return KeyEvent.VK_ENTER;
			if("F2".equals(key))
				return KeyEvent.VK_F2;
			if("SHIFT".equals(key))
				return KeyEvent.VK_SHIFT;
			if("ESCAPE".equals(key))
				return KeyEvent.VK_ESCAPE;
			
		}
		return intKey;
	}
	public static List<String> scanControlsFile() throws FileNotFoundException, IOException {
		File file = new File("controls.txt"); 
		List<String> stringControls = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader(file)); 
		
		String row; 
		while ((row = br.readLine()) != null) 
			stringControls.add(row);
		return stringControls;
	}

}
