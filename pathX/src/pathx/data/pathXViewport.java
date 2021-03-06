/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pathx.data;



/**
 * This class keeps track of the portion of the level the user
 * is viewing during editing.
 * 
 * @author Richard McKenna
 */
public class pathXViewport
{
    // VIEWPORT POSITION
    public int x;
    public int y;
    
    // WIDTH AND HEIGHT OF THE VIEWPORT WINDOW
    public int width;
    public int height;
    
    // WIDTH AND HEIGHT OF THE LEVEL, WHICH SHOULD
    // CORRESPOND TO THE BACKGROUND IMAGE SIZE
    public int levelWidth;
    public int levelHeight;
    
    public int getY(){ return y;}
    public int getX(){ return x;}
    public int getWidth(){ return width;}
    public int getHeight(){ return height;}

    /**
     * Default constructor, it initializes the viewport at the origin
     * and sets its and the level's size to 0.
     */
    public pathXViewport()
    {
        width = 0;
        height = 0;
        x = 0;
        y = 0;
        levelWidth = width;
        levelHeight = height;
    }
    
    

    /**
     * Resets the viewport back to the top-left hand corner.
     */
    public void reset()
    {
        x = 0;
        y = 0;
    }

    /**
     * Sets the viewport's dimensions, making sure it is smaller
     * than the level's dimensions.
     */
    public void setViewportDimensions(int initWidth, int initHeight)
    {
        width = initWidth;
        height = initHeight;
        
        if (width > levelWidth)
            levelWidth = width;
        if (height > levelHeight)
            levelHeight = height;
    }

    /**
     * Sets the level's dimensions, which shoudl be larger than the viewport.
     */
    public void setLevelDimensions(int initLevelWidth, int initLevelHeight)
    {
        if ((initLevelWidth >= width) && (initLevelHeight >= height))
        {
           levelWidth = initLevelWidth;
           levelHeight = initLevelHeight;
        }
    }

    /**
     * Scrolls the viewport by (incX, incY), clamping at
     * the level borders so that we don't scroll off the level.
     */
    public void move(int incX, int incY)
    {
        x += incX;
        y += incY;
        if (x < 0) x = 0;
        if (y < 0) y = 0;
        if (x > (levelWidth - width))
            x = levelWidth - width;
        if (y > (levelHeight - height))
            y = levelHeight - height;
    }

    /**
     * Returns true if the test rect is inside the viewport,
     * returns false otherwise.
     */
    public boolean isRectInsideViewport(int x1, int y1, int x2, int y2)
    {
        if (x2 < x) return false;
        if (x1 > (x + width)) return false;
        if (y2 < y) return false;
        if (y1 > (y + height)) return false;
        return true;
    }

    /**
     * Returns true if the circle argument's bounding box is inside the
     * viewport, false otherwise.
     */
    public boolean isCircleBoundingBoxInsideViewport(int centerX, int centerY, int radius)
    {
        boolean bool = isRectInsideViewport(centerX-radius, centerY-radius, centerX+radius, centerY+radius);
        return bool;
    }
}