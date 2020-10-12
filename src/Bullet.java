import java.awt.Color;
import java.awt.Graphics;

public class Bullet extends GameObject {
    
    private Handler handler;

    public Bullet(float x, float y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;
        
        if (x > Game.WIDTH/2 + 50 || x < Game.WIDTH/2-600) handler.object.remove(this);
        if (y > Game.HEIGHT/2 + 50 || y < Game.HEIGHT/2-400) handler.object.remove(this);
        
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.yellow);
        g.fillRect((int)x, (int)y, 8, 8);
    }

}
