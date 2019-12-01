import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ProgrammeKiller extends WindowAdapter{
	public void windowClosing(WindowEvent windowEvent) {
		System.exit(0);
	}
}
