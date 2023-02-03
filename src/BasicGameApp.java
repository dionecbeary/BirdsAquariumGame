//Basic Game Application
//Version 2
// Basic Object, Image, Movement
// Astronaut moves to the right.
// Threaded

//K. Chun 8/2018

//*******************************************************************************
//Import Section
//Add Java libraries needed for the game
//import java.awt.Canvas;

//Graphics Libraries
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;


//*******************************************************************************
// Class Definition Section

public class BasicGameApp implements Runnable {

    //Variable Definition Section
    //Declare the variables used in the program
    //You can set their initial values too

    //Sets the width and height of the program window
    final int WIDTH = 1000;
    final int HEIGHT = 700;

    //Declare the variables needed for the graphics
    public JFrame frame;
    public Canvas canvas;
    public JPanel panel;

    public BufferStrategy bufferStrategy;
    public Image chickenPic;
    public Image chickPic;
    public Image eaglePic;
    public Image fencePic;
    public Image background;
    public Image endScreen;
    public Image ghostPic;

    //Declare the objects used in the program
    //These are things that are made up of more than one variable type
    private Bird chicken;
    private Bird chick;
    private Bird eagle;
    private int chickLives = 3;
    private Fence fence;




    // Main method definition
    // This is the code that runs first and automatically
    public static void main(String[] args) {
        BasicGameApp ex = new BasicGameApp();   //creates a new instance of the game
        new Thread(ex).start();                 //creates a threads & starts up the code in the run( ) method
    }


    // Constructor Method
    // This has the same name as the class
    // This section is the setup portion of the program
    // Initialize your variables and construct your program objects here.
    public BasicGameApp() {

        setUpGraphics();

        //variable and objects
        //create (construct) the objects needed for the game and load up
        chickenPic = Toolkit.getDefaultToolkit().getImage("chicken.png"); //load the picture
        chickPic = Toolkit.getDefaultToolkit().getImage("chick.png");
        eaglePic = Toolkit.getDefaultToolkit().getImage("eagle.png");
        fencePic = Toolkit.getDefaultToolkit().getImage("fence.png");
        background = Toolkit.getDefaultToolkit().getImage("field.png");
        endScreen = Toolkit.getDefaultToolkit().getImage("gameover.jpeg");
        ghostPic = Toolkit.getDefaultToolkit().getImage("ghost.png");
        chicken = new Bird(700,300,-4,4);
        chick = new Bird(710,300,3,3);
        chick.height = 40;
        chick.width = 40;
        eagle = new Bird(200,300,-7,7);
        fence = new Fence(500, 200, 0, 3);

    }// BasicGameApp()


//*******************************************************************************
//User Method Section
//
// put your code to do things here.

    // main thread
    // this is the code that plays the game after you set things up
    public void run() {

        //for the moment we will loop things forever.
        while (true) {

            moveThings();  //move all the game objects
            render();  // paint the graphics
            pause(20); // sleep for 10 ms
        }
    }

    public void crash(){
        if(eagle.rec.intersects(chick.rec) && chick.isCrashingEagle == false)
        {
            chick.isCrashingEagle = true;
            System.out.println("Eagle attacked chick :(");
            if (chickLives > 0) {
            chickLives = chickLives - 1;
            }
            if (chickLives == 0) {
                System.out.println("Eagle has eaten chick ;-;");
                System.out.println(
                        "            _________________\n" +
                        "           /                \\\\\n" +
                        "          |  _     ___   _   ||\n" +
                        "          | | \\     |   | \\  ||\n" +
                        "          | |  |    |   |  | ||\n" +
                        "          | |_/     |   |_/  ||\n" +
                        "          | | \\     |   |    ||\n" +
                        "          | |  \\    |   |    ||\n" +
                        "          | |   \\. _|_. | .  ||\n" +
                        "          |                  ||\n" +
                        "          |      Chick       ||\n" +
                        "          |                  ||");
            }

        }
        if (chickLives > 0) {
            if (chicken.rec.intersects(eagle.rec)) {
                eagle.xpos = 200;
                System.out.println("The chicken has scared off the eagle.");
            }
            if (chicken.rec.intersects(fence.rec)) {
                chicken.dx = -chicken.dx;
            }
            if (chick.rec.intersects(fence.rec)) {
                chick.dx = -chick.dx;
            }
            if (eagle.rec.intersects(fence.rec)) {
                eagle.dx = -eagle.dx;
            }

            if (eagle.rec.intersects(chick.rec) == false) {
                chick.isCrashingEagle = false;
            }
        }
    }

    public void commentary(){
        if(chickLives> 0)
        {
            if(chicken.rec.intersects(eagle.rec))
            {
            System.out.println("Chicken has scared off Eagle!");
            }
            if(chicken.rec.intersects(fence.rec))
            {
                System.out.println("Chicken: 'oof'");
            }
            if(chick.rec.intersects(fence.rec))
            {
                System.out.println("Chick: 'oof'");
            }
            if(eagle.rec.intersects(fence.rec))
            {
                System.out.println("Eagle: 'oof'");
            }
        }
    }

    public void moveThings() {
        //calls the move( ) code in the objects
        crash();
        commentary();
        chicken.bounce();
        chicken.move();
        if (chickLives > 0){
            chick.bounce();
        } else {
            chick.wrap();
        }
        chick.move();
        eagle.bounce();
        eagle.move();
        fence.bounce();
        //fence.move();
    }

    //Pauses or sleeps the computer for the amount specified in milliseconds
    public void pause(int time ){
        //sleep
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {

        }
    }

    //Graphics setup method
    private void setUpGraphics() {
        frame = new JFrame("Application Template");   //Create the program window or frame.  Names it.

        panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
        panel.setLayout(null);   //set the layout

        // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
        // and trap input events (Mouse and Keyboard events)
        canvas = new Canvas();
        canvas.setBounds(0, 0, WIDTH, HEIGHT);
        canvas.setIgnoreRepaint(true);

        panel.add(canvas);  // adds the canvas to the panel.

        // frame operations
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
        frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
        frame.setResizable(false);   //makes it so the frame cannot be resized
        frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!

        // sets up things so the screen displays images nicely.
        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        canvas.requestFocus();
        System.out.println("DONE graphic setup");

    }


    //paints things on the screen using bufferStrategy
    private void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);

        //draw the image of the astronaut

        if (chickLives>0){
            g.drawImage(background, 0, 0, 1000, 700, null);

            g.drawImage(chickenPic, chicken.xpos, chicken.ypos, chicken.width, chicken.height, null);
            g.draw(new Rectangle(chicken.xpos, chicken.ypos, chicken.width, chicken.height));

            g.drawImage(chickPic, chick.xpos, chick.ypos,40 , 40, null);
            g.draw(new Rectangle(chick.xpos, chick.ypos, 40, 40));

            g.drawImage(eaglePic, eagle.xpos, eagle.ypos, eagle.width, eagle.height, null);
            g.draw(new Rectangle(eagle.xpos, eagle.ypos, eagle.width, eagle.height));

            g.drawImage(fencePic, fence.xpos, fence.ypos, 30, 300, null);
            g.draw(new Rectangle(fence.xpos, fence.ypos, 30, 300));
        }else{
            g.drawImage(endScreen, 0, 0, 1000, 700, null);
            g.drawImage(ghostPic, chick.xpos, chick.ypos,40 , 40, null);
            g.draw(new Rectangle(chick.xpos, chick.ypos, 40, 40));

        }


        g.dispose();

        bufferStrategy.show();
    }
}