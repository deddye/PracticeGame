import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {

    public static int WIDTH = 800, HEIGHT = 608;
    public String title = "Zombie Game";

    private Thread thread;
    private boolean isRunning = false;
    
    private Handler handler;
    private KeyInput input;
    private MouseInput mInput;
    private Camera cam;

    public Game() {
        // constructor for the game
        new Window(WIDTH, HEIGHT, title, this);
        start();
        
        
        init();
        
        
    }
    
    private void init() {
        handler = new Handler();
        input = new KeyInput();
        cam = new Camera(0, 0, handler);
        mInput = new MouseInput(handler, cam);
        this.addKeyListener(input);
        this.addMouseListener(mInput);
        
        handler.addObject(new Player(100, 100, ID.Player, input, handler));
        handler.addObject(new Box(200, 200, ID.Block));
        handler.addObject(new Box(300, 300, ID.Block));

        mInput.findPlayer();
    }


    private synchronized void start() {
        if (isRunning)
            return;

        thread = new Thread(this);
        thread.start();
        isRunning = true;
    }


    private synchronized void stop() {
        if (!isRunning)
            return;

        try {
            thread.join();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        isRunning = false;
    }


    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int updates = 0;
        int frames = 0;
        while(isRunning){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1){
                tick();
                updates++;
                delta--;
            }
            render();
            frames++;
                    
            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                System.out.println("FPS: " + frames + " TICKS: " + updates);
                frames = 0;
                updates = 0;
            }
        }
        stop();
    }
    
    private void tick() {
        //updates the game
        handler.tick();
        cam.tick();
    }
    
    private void render() {
        //renders the game
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        
        Graphics g = bs.getDrawGraphics();
        Graphics2D g2d = (Graphics2D) g;
        
        //what makes the rendering happen
        g.setColor(Color.gray);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        
        g2d.translate(-cam.getX(), -cam.getY());
        
        handler.render(g);
        
        g2d.translate(cam.getX(), cam.getY());
        
        bs.show();
        g.dispose();
    }


    public static void main(String[] args) {
        new Game();
    }

}
