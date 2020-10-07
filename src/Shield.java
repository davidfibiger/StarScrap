import java.awt.Graphics;

public class Shield extends Drawable {
	public int order;
	public int direction;
	
	Shield(int order, int direction, Player player){
		update(order, direction, player);
	}
	public void update(int order, int direction, Player player) {
		this.order = order;
		this.direction = direction;
		y = player.y - player.height/2;		
		x = player.x;
		
		width = StarScrapMain.canvas.getWidth()/40;
		height = width/8;
	}
	public void paint(Graphics g, GameStatus gameStatus) {
		if(order == 1 && direction == 0) {
			g.drawImage(StarScrapMain.shieldR, (int)x, (int)y, (int)width, (int)height,null);
			//System.out.println("shield");
		}
	}
}
