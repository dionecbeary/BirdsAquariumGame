import java.awt.*;

public class Seeds {
    //VARIABLE DECLARATION SECTION
    //Here's where you state which variables you are going to use.
    public String name;                //holds the name of the hero
    public int xpos;                //the x position
    public int ypos;                //the y position
    public int dx;                    //the speed of the hero in the x direction
    public int dy;                    //the speed of the hero in the y direction
    public int width;
    public int height;
    public boolean isAlive;            //a boolean to denote if the hero is alive or dead.
    public Rectangle rec;
    public boolean isCrashing;


    // METHOD DEFINITION SECTION

    // Constructor Definition
    // A constructor builds the object when called and sets variable values.


    //This is a SECOND constructor that takes 3 parameters.  This allows us to specify the hero's name and position when we build it.
    // if you put in a String, an int and an int the program will use this constructor instead of the one above.
    public Seeds(int pXpos, int pYpos, int pDx, int pDy) {
        xpos = pXpos;
        ypos = pYpos;
        dx =pDx;
        dy =pDy;
        width = 50;
        height = 50;
        isAlive = true;
        rec = new Rectangle(xpos, ypos, width, height);

    } // constructor

    //The move method.  Everytime this is run (or "called") the hero's x position and y position change by dx and dy
    public void move() {
        xpos = xpos + dx;
        ypos = ypos + dy;
        rec = new Rectangle(xpos, ypos, width, height);
    }

    public void wrap(){
        if (ypos > 650){
            ypos = 0;
        }
    }

    public void bounce(){
        if (ypos>650 || ypos<0){// ypos > 700-hieght
            dy=-dy;
        }
        if (xpos> 950|| xpos < 0){
            dx=-dx;
        }
    }
}

