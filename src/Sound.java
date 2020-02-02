import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;

public class Sound {
	Clip clip;
	String soundFileName;
	public Sound(String soundFileName){
		try {
			clip = AudioSystem.getClip();
			File file = new File(soundFileName);
			AudioInputStream sound = AudioSystem.getAudioInputStream(file);
			
			clip.open(sound);
			clip.setFramePosition(0);
		}catch(Exception e){
			System.out.println("Error: "+ e.getMessage());
			e.printStackTrace();
		}
		this.soundFileName = soundFileName;
		
	}
	public void play() {
		
		//try {
			
			
			clip.setFramePosition(0);
			clip.start();
			/*clip.addLineListener(new LineListener() {
				
				@Override
				public void update(LineEvent event) {
					// TODO Auto-generated method stub
					if(event.getType().equals(LineEvent.Type.STOP)) {
						clip.close();
						try {
							sound.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			});
		}
		catch(Exception e){
			System.out.println("Error: "+ e.getMessage());
			e.printStackTrace();
		}*/
		
		
	}

}
