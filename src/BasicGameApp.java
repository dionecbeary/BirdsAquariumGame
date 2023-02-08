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
    public Image chickenRPic;
    public Image chickenLPic;
    public Image chickRPic;
    public Image chickLPic;
    public Image eagleRPic;
    public Image eagleLPic;
    public Image fencePic;
    public Image seedsPic;
    public Image background;
    public Image endScreen;
    public Image winScreen;
    public Image ghostPic;

    //Declare the objects used in the program
    //These are things that are made up of more than one variable type
    private Bird chicken;
    private Bird chick;
    private Bird eagle;
    private int chickLives = 3;
    private int seedsEaten = 0;
    private Fence fence;
    private Seeds seeds;




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
        chickenRPic = Toolkit.getDefaultToolkit().getImage("chickenR.png"); //load the picture
        chickenLPic = Toolkit.getDefaultToolkit().getImage("chickenL.png");
        chickRPic = Toolkit.getDefaultToolkit().getImage("chickR.png");
        chickLPic = Toolkit.getDefaultToolkit().getImage("chickL.png");
        eagleRPic = Toolkit.getDefaultToolkit().getImage("eagleR.png");
        eagleLPic = Toolkit.getDefaultToolkit().getImage("eagleL.png");
        fencePic = Toolkit.getDefaultToolkit().getImage("fence.png");
        background = Toolkit.getDefaultToolkit().getImage("field.png");
        endScreen = Toolkit.getDefaultToolkit().getImage("gameover.jpeg");
        winScreen = Toolkit.getDefaultToolkit().getImage("winscreen.png");
        ghostPic = Toolkit.getDefaultToolkit().getImage("ghost.png");
        seedsPic = Toolkit.getDefaultToolkit().getImage("seeds.png");
        chicken = new Bird(700,300,-4,4);
        chick = new Bird(710,300,3,3);
        chick.height = 40;
        chick.width = 40;
        eagle = new Bird(200,300,-7,7);
        fence = new Fence(500, 200, 0, 3);
        seeds = new Seeds(50, 0, 1, 1);

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
            if (chickLives > 0&& seedsEaten<3) {
            chickLives = chickLives - 1;
            System.out.println("Eagle has attacked chick!");
            }
            if (chickLives == 0) {
                chick.dx = 5;
                chick.dy = 5;
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
            if (seedsEaten == 3){
                System.out.println("The chickens have eaten enough seeds and will no longer be bothered by the eagle!!!");
            }

        }
        if (chickLives > 0 && seedsEaten<3) {
            if ((seeds.rec.intersects(chicken.rec)||seeds.rec.intersects(chick.rec)||seeds.rec.intersects(eagle.rec))&&seeds.isCrashing==false){
                if (seeds.rec.intersects(chick.rec)){
                    System.out.println("Chick ate seeds and grew stronger!");
                    chick.width += 15;
                    chick.height += 15;
                    chick.dx = chick.dx + 9;
                    chick.dy = chick.dy + 3;
                    seedsEaten +=1;
                }
                if (seeds.rec.intersects(eagle.rec)){
                    System.out.println("Eagle ate seeds and grew stronger!");
                    eagle.dx += 1;
                    eagle.dy += 1;
                }
                if (seeds.rec.intersects(chicken.rec)){
                    System.out.println("Chicken ate seeds and is slowing down!");
                    chicken.dx -= 1;
                    chicken.dx -= 1;
                    seedsEaten +=1;
                }
                seeds.isCrashing = true;
            }
            if (seeds.rec.intersects(chicken.rec)==false && seeds.rec.intersects(chick.rec)==false && seeds.rec.intersects(eagle.rec)==false){
                seeds.isCrashing = false;
            }
            if (chicken.rec.intersects(eagle.rec) && chicken.isCrashingEagle == false) {
                eagle.xpos = 200;
                System.out.println("Chicken has scared off Eagle!");
                chicken.isCrashingEagle = true;
            }
            if (chicken.rec.intersects(fence.rec) && chicken.isCrashingFence == false) {
                chicken.isCrashingFence = true;
                chicken.dx = -chicken.dx;
//                System.out.println("Chicken: 'oof'");
            }
            if (chick.rec.intersects(fence.rec) && chick.isCrashingFence == false) {
                chick.isCrashingFence = true;
                chick.dx = -chick.dx;
//                System.out.println("Chick: 'oof'");
            }
            if (eagle.rec.intersects(fence.rec) && eagle.isCrashingFence == false) {
                eagle.isCrashingFence = true;
                eagle.dx = -eagle.dx;
//                System.out.println("Eagle: 'oof'");
            }

            if (chicken.rec.intersects(eagle.rec) == false) {
                chicken.isCrashingEagle = false;
            }
            if (eagle.rec.intersects(chick.rec) == false) {
                chick.isCrashingEagle = false;
            }
            if (chicken.rec.intersects(fence.rec) == false) {
                chicken.isCrashingFence = false;
            }
            if (chick.rec.intersects(fence.rec) == false) {
                chick.isCrashingFence = false;
            }
            if (eagle.rec.intersects(fence.rec) == false) {
                eagle.isCrashingFence = false;
            }
        }
    }


    public void moveThings() {
        //calls the move( ) code in the objects
        crash();
        chicken.bounce();
        chicken.move();
        if (chickLives > 0 && seedsEaten<3){
            chick.bounce();
        } else {
            chick.wrap();
        }
        chick.move();
        eagle.bounce();
        eagle.move();
        seeds.bounce();
        seeds.move();
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

        if (chickLives>0 && seedsEaten < 3){
            g.drawImage(background, 0, 0, 1000, 700, null);

            if (chicken.dx > 0){
                g.drawImage(chickenRPic, chicken.xpos, chicken.ypos, chicken.width, chicken.height, null);
            }else{
                g.drawImage(chickenLPic, chicken.xpos, chicken.ypos, chicken.width, chicken.height, null);
            }
            g.draw(new Rectangle(chicken.xpos, chicken.ypos, chicken.width, chicken.height));

            if (chick.dx > 0){
                g.drawImage(chickRPic, chick.xpos, chick.ypos,40 , 40, null);
            }else{
                g.drawImage(chickLPic, chick.xpos, chick.ypos,40 , 40, null);
            }
            g.draw(new Rectangle(chick.xpos, chick.ypos, 40, 40));

            if (eagle.dx > 0){
                g.drawImage(eagleRPic, eagle.xpos, eagle.ypos, eagle.width, eagle.height, null);
            }else{
                g.drawImage(eagleLPic, eagle.xpos, eagle.ypos, eagle.width, eagle.height, null);
            }
            g.draw(new Rectangle(eagle.xpos, eagle.ypos, eagle.width, eagle.height));

            g.drawImage(seedsPic, seeds.xpos, seeds.ypos, seeds.width, seeds.height, null);
            g.draw(new Rectangle(seeds.xpos, seeds.ypos, seeds.width, seeds.height));

            g.drawImage(fencePic, fence.xpos, fence.ypos, 30, 300, null);
            g.draw(new Rectangle(fence.xpos, fence.ypos, 30, 300));
        } else if (chickLives == 0) {
            System.out.println(seedsEaten);
            g.drawImage(endScreen, 0, 0, 1000, 700, null);
            g.drawImage(ghostPic, chick.xpos, chick.ypos,40 , 40, null);
        } else if(seedsEaten == 3){
            g.drawImage(winScreen, 0, 0, 1000, 700, null);
            if (chick.dx > 0){
                g.drawImage(chickRPic, chick.xpos, chick.ypos,40 , 40, null);
            }else{
                g.drawImage(chickLPic, chick.xpos, chick.ypos,40 , 40, null);
            }
            g.draw(new Rectangle(chick.xpos, chick.ypos, 40, 40));

            if (chicken.dx > 0){
                g.drawImage(chickenRPic, chicken.xpos, chicken.ypos, chicken.width, chicken.height, null);
            }else{
                g.drawImage(chickenLPic, chicken.xpos, chicken.ypos, chicken.width, chicken.height, null);
            }
            g.draw(new Rectangle(chicken.xpos, chicken.ypos, chicken.width, chicken.height));
        }


        g.dispose();

        bufferStrategy.show();
    }
}