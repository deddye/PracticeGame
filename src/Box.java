import java.awt.Color;
import java.awt.Graphics;

public class Box extends GameObject {

    public Box(float x, float y, ID id) {
        super(x, y, id);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void tick() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.red);
        g.fillRect((int)x, (int)y, 64, 64);
    }

}
