import static org.junit.jupiter.api.Assertions.*;

import java.awt.event.KeyEvent;

import org.junit.jupiter.api.Test;

class PlayerTest {

	@Test
	void test() {
		Player player = new Player(1, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SHIFT, KeyEvent.VK_SPACE, KeyEvent.VK_1);
		
		System.out.println("1,1,1:" + player.calculateMovement(1, 1, 1) / Math.sqrt(2));
		System.out.println("2,1,1:" + player.calculateMovement(2, 1, 1) / Math.sqrt(2));
		System.out.println("3,1,1:" + player.calculateMovement(3, 1, 1) / Math.sqrt(2));
		System.out.println("4,1,1:" + player.calculateMovement(4, 1, 1) / Math.sqrt(2));
		System.out.println("5,1,1:" + player.calculateMovement(5, 1, 1) / Math.sqrt(2));   
		//fail("Not yet implemented");
	}

}
