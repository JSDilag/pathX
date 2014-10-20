/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pathx.file;
import java.awt.Image;
import pathx.data.pathXDataModel;
import pathx.data.pathXIntersection;
import pathx.data.pathXRoad;
import java.util.ArrayList;
import pathx.ui.pathXMiniGame;
import java.awt.image.BufferedImage;

/**
 * This stores all the data for a level. We use a separate class from
 * the model for this because this object stores only the data that
 * needs to be written to level files.
 * 
 * @author Richard McKenna
 */
public class pathXLevel
{
    // EVERY LEVEL HAS A NAME
    String levelName;

    // THE LEVEL BACKGROUND
    String startingLocationImageFileName;

    // COMPLETE LIST OF INTERSECTIONS SORTED LEFT TO RIGHT
    ArrayList<pathXIntersection> intersections;

    // COMPLETE LIST OF ROADS SORTED BY STARTING INTERSECTION LOCATION LEFT TO RIGHT
    ArrayList<pathXRoad> roads;

    // THE STARTING LOCATION AND DESTINATION
    pathXIntersection startingLocation;
    public String backgroundImageFileName;
    pathXIntersection destination;
    String destinationImageFileName;

    // THE AMOUNT OF MONEY TO BE EARNED BY THE LEVEL
    int money;

    // THE NUMBER OF POLICE, BANDITS, AND ZOMBIES
    int numPolice;
    int numBandits;
    int numZombies;
    int startingLocationImageWidth;
    /**
     * Default constructor, it just constructs the graph data structures
     * but does not fill in any data.
     */
    public pathXLevel()
    {
        // INIT THE GRAPH DATA STRUCTURES
        intersections = new ArrayList();
        roads = new ArrayList();    
        
    }

    /**
     * Initializes this level to get it up and running.
     */
    public void init (  String initLevelName,
                        String initBackgroundImageFileName,
                        String initStartingLocationImageFileName,
                        int startingLocationX, 
                        int startingLocationY,
                        String initDestinationImageFileName,
                        int destinationX, 
                        int destinationY)
    {
        // THESE THINGS ARE KNOWN
        levelName = initLevelName;
        backgroundImageFileName = initBackgroundImageFileName;
        startingLocationImageFileName = initStartingLocationImageFileName;
        destinationImageFileName = initDestinationImageFileName;
        
        
        // AND THE STARTING LOCATION AND DESTINATION
        startingLocation = new pathXIntersection(startingLocationX, startingLocationY);
        intersections.add(startingLocation);
        destination = new pathXIntersection(destinationX, destinationY);
        intersections.add(destination);
        
        // THESE THINGS WILL BE PROVIDED DURING LEVEL EDITING
        money = 0;
        numPolice = 0;
        numBandits = 0;
        numZombies = 0;
    }
    
    // ACCESSOR METHODS
    public String                   getLevelName()                      {   return levelName;                       }
    public String                   getStartingLocationImageFileName()  {   return startingLocationImageFileName;   }
    public String                   getBackgroundImageFileName()        {   return backgroundImageFileName;         }
    public String                   getDestinationImageFileName()       {   return destinationImageFileName;        }
    public ArrayList<pathXIntersection>  getIntersections()                  {   return intersections;                   }
    public ArrayList<pathXRoad>          getRoads()                          {   return roads;                           }
    public pathXIntersection             getStartingLocation()               {   return startingLocation;                }
    public pathXIntersection             getDestination()                    {   return destination;                     }
    public int                      getMoney()                          {   return money;                           }
    public int                      getNumPolice()                      {   return numPolice;                       }
    public int                      getNumBandits()                     {   return numBandits;                      }
    public int                      getNumZombies()                     {   return numZombies;                      }
    public int getStartingLocationImageWidth(){ return startingLocationImageWidth;}
    
    // MUTATOR METHODS
    public void setLevelName(String levelName)    
    {   this.levelName = levelName;                                             }
    public void setNumBandits(int numBandits)
    {   this.numBandits = numBandits;                                           }
    public void setBackgroundImageFileName(String backgroundImageFileName)    
    {   this.backgroundImageFileName = backgroundImageFileName;                 }
    public void setStartingLocationImageFileName(String startingLocationImageFileName)    
    {   this.startingLocationImageFileName = startingLocationImageFileName;     }
    public void setDestinationImageFileName(String destinationImageFileName)    
    {   this.destinationImageFileName = destinationImageFileName;               }
    public void setMoney(int money)    
    {   this.money = money;                                                     }
    public void setNumPolice(int numPolice)    
    {   this.numPolice = numPolice;                                             }
    public void setNumZombies(int numZombies)
    {   this.numZombies = numZombies;                                           }
    public void setStartingLocation(pathXIntersection startingLocation)
    {   this.startingLocation = startingLocation;                               }
    public void setDestination(pathXIntersection destination)
    {   this.destination = destination;                                         }
    public void setStartingLocationImageWidth(int width)
    {   startingLocationImageWidth = width;                                         }
    
    /**
     * Clears the level graph and resets all level data.
     */
    public void reset()
    {
        levelName = "";
        startingLocationImageFileName = "";
        intersections.clear();
        roads.clear();
        startingLocation = null;
        backgroundImageFileName = "";
        destination = null;
        destinationImageFileName = "";
        money = 0;
        numPolice = 0;
        numBandits = 0;
        numZombies = 0;
    }
}