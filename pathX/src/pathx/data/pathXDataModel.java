package pathx.data;

import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.Iterator;
import javax.swing.JPanel;
import mini_game.MiniGame;
import mini_game.MiniGameDataModel;
import mini_game.SpriteType;
import pathx.PathX;
import pathx.file.pathXLevel;
import pathx.data.pathXRoad;
import pathx.ui.*;
import static pathx.pathXConstants.*;
import properties_manager.PropertiesManager;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Joseph
 */
public class pathXDataModel extends MiniGameDataModel {
    
    private MiniGame miniGame;
    Image levelBackgroundImage;
    pathXLevel level;
    Image startingLocationImage;
    
   

    // DATA FOR RENDERING
    pathXViewport viewport;

    // WE ONLY NEED TO TURN THIS ON ONCE
    boolean levelBeingEdited;
    Image backgroundImage;
    Image destinationImage;

    // THE SELECTED INTERSECTION OR ROAD MIGHT BE EDITED OR DELETED
    // AND IS RENDERED DIFFERENTLY
    pathXIntersection selectedIntersection;
    pathXRoad selectedRoad;
    
    // WE'LL USE THIS WHEN WE'RE ADDING A NEW ROAD
    pathXIntersection startRoadIntersection;

    // IN CASE WE WANT TO TRACK MOVEMENTS
    int lastMouseX;
    int lastMouseY;    
    private int levelsCompleted;
    private int originalLevelsCompleted;
    
    
    // THESE BOOLEANS HELP US KEEP TRACK OF
    // @todo DO WE NEED THESE?
    boolean isMousePressed;
    boolean isDragging;
    boolean dataUpdatedSinceLastSave;
    boolean specialInUse;
    char special;
    
    private pathXTile player;
    
    private int totalMoney;
    
     private ArrayList<pathXTile> zombies;
     private ArrayList<pathXTile> cops;
     private ArrayList<pathXTile> bandits;
     
     private boolean hasLost;
     private boolean hasWon;
     private boolean levelsCheatActivated;
     private boolean moneyCheatActivated;
     private boolean hasCollidedWithZombie;

    // THIS IS THE UI, WE'LL NOTIFY IT WHENEVER THE DATA CHANGES SO
    // THAT THE UI RENDERING CAN BE UPDATED AT THAT TIME
    
     public Iterator intersectionsIterator()
    {
        ArrayList<pathXIntersection> intersections = level.getIntersections();
        return intersections.iterator();
    }
    public Iterator roadsIterator()
    {
        ArrayList<pathXRoad> roads = level.getRoads();
        return roads.iterator();
    }
    
    
    public Image            getBackgroundImage()        {   return backgroundImage;         }
    public Image            getStartingLocationImage()  {   return startingLocationImage;   }
    public Image            getDesinationImage()        {   return destinationImage;        }
    public pathXIntersection     getSelectedIntersection()   {   return selectedIntersection;    }
    public pathXRoad             getSelectedRoad()           {   return selectedRoad;            }
    public pathXIntersection     getStartRoadIntersection()  {   return startRoadIntersection;   }
    public int              getLastMouseX()             {   return lastMouseX;              }
    public int              getLastMouseY()             {   return lastMouseY;              }
    public pathXIntersection     getStartingLocation()       {   return level.getStartingLocation();  }
    public pathXIntersection     getDestination()            {   return level.getDestination();       }
    public boolean          isDataUpdatedSinceLastSave(){   return dataUpdatedSinceLastSave;}   
    public pathXTile  getPlayer() { return player; }
    public MiniGame getMiniGame(){  return miniGame; }
    public int getLevelsCompleted(){ return levelsCompleted;}
    public int getTotalMoney(){ return totalMoney; }
    public ArrayList<pathXTile> getCops() { return cops; }
    public ArrayList<pathXTile> getZombies() { return zombies; }
    public ArrayList<pathXTile> getBandits() { return bandits; }
    public boolean getLevelsCheatActivated(){ return levelsCheatActivated; }
    public boolean getMoneyCheatActivated(){ return moneyCheatActivated; }
    public int getOriginalLevelsCompleted() { return originalLevelsCompleted; }
    public boolean getSpecialInUse(){ return specialInUse;}
    public char getSpecial() { return special;}
    
     public boolean isStartingLocation(pathXIntersection testInt)  
    {   return testInt == level.getStartingLocation();           }
    public boolean isDestination(pathXIntersection testInt)
    {   return testInt == level.getDestination();                }
    public boolean isSelectedIntersection(pathXIntersection testIntersection)
    {   return testIntersection == selectedIntersection;    }
    public boolean isSelectedRoad(pathXRoad testRoad)
    {   return testRoad == selectedRoad;                    }
    
    
    public void setMoneyCheatActivated(boolean b){
        moneyCheatActivated = b;
    }
    
    public void setLevelsCheatActivated(boolean b){
        levelsCheatActivated = b;
    }
    
    public void setSpecialInUse(char x, boolean b){
        special = x;
        specialInUse = b;
    }
    
    public void setSelectedIntersection(pathXIntersection i){
        selectedIntersection = i;
        selectedRoad = null;
    }
     public void setSelectedRoad(pathXRoad r)
    {
        selectedRoad = r;
        selectedIntersection = null;
        miniGame.getCanvas().repaint();
    }
     
     public void setHasWon(boolean b){
         hasWon = b;
     }
     
     public void setHasLost(boolean b){
         hasLost = b;
     }
     public void setTotalMoney(int cash){
         totalMoney = cash;
     }
    
    public void setLevelsCompleted(int x){
        levelsCompleted = x;
    }
    
    public Image getLevelBackgroundImage(){
        return levelBackgroundImage;
    }
    
    
    public pathXViewport getPathXViewport(){
        return viewport;
    }
    public pathXLevel getLevel(){
        return level;                   
    }
    
    public pathXDataModel(MiniGame initMiniGame)
    {
        // KEEP THE GAME FOR LATER
        miniGame = initMiniGame;
        level = new pathXLevel();
        
        viewport = new pathXViewport();
        levelBeingEdited = false;
        startRoadIntersection = null;
        levelsCompleted = 0;
        originalLevelsCompleted = 0;
        totalMoney = 0;
        zombies = new ArrayList();
        cops = new ArrayList();
        bandits = new ArrayList();
        
        hasCollidedWithZombie = false;
        
        hasWon =false;
        // INIT THESE FOR HOLDING MATCHED AND MOVING TILES
        
     

        // NOTHING IS BEING DRAGGED YET
        
    }
    
     @Override
    public void checkMousePressOnSprites(MiniGame game, int x, int y)
    {
      pathXMiniGame miniGame = (pathXMiniGame)game;
        PropertiesManager props = PropertiesManager.getPropertiesManager();
       if(miniGame.isCurrentScreenState(GAMEPLAY_SCREEN_STATE)){
           pathXIntersection i = findIntersectionAtCanvasLocation(x, y);
            if (i != null)
            {
               
                if(specialInUse){
                    if(special == 'r' && i.isOpen()){
                        i.toggleOpen();
                        miniGame.getAudio().play(PathX.pathXPropertyType.AUDIO_CUE_SPECIAL_USE.toString(), true);
                        totalMoney -= 5;
                       
                    }
                    else if(special == 'g' && !i.isOpen()){
                        i.toggleOpen();
                        miniGame.getAudio().play(PathX.pathXPropertyType.AUDIO_CUE_SPECIAL_USE.toString(), true);
                        totalMoney -= 5;
                    }    
                    
                   specialInUse = false;
                   special = 'a';
                }
                else if(i.isOpen()){
                // MAKE THIS THE SELECTED INTERSECTION
                setSelectedIntersection(i);
               player.setTarget(selectedIntersection.x + WEST_PANEL_WIDTH - 33, selectedIntersection.y - 33);
               player.startMovingToTarget(PLAYER_SPEED);
               hasCollidedWithZombie = false;
                //model.switchEditMode(PXLE_EditMode.INTERSECTION_DRAGGED);
               return;
                }
            }   
       }
       
    }
    
    

    public void movePlayer(int canvasX, int canvasY)
    {
        player.setX(canvasX + viewport.x);
        player.setY(canvasY + viewport.y);
        miniGame.getCanvas().repaint();
    }
    /**
     * Called when the game is won, it will record the ending game time, update
     * the player record, display the win dialog, and play the win animation.
     */
    @Override
    public void endGameAsWin()
    {
        super.endGameAsWin();
        
        if(hasWon != true){
         totalMoney += level.getMoney();
          levelsCompleted++;
          originalLevelsCompleted++;  
        }
        hasWon = true;
        
      miniGame.getGUIDialogs().get(LEVEL_DIALOG_TYPE).setState(pathXTileState.WIN_STATE.toString());
      miniGame.getGUIButtons().get(DIALOG_BUTTON_TYPE).setState(pathXTileState.VISIBLE_STATE.toString());
       miniGame.getGUIButtons().get(DIALOG2_BUTTON_TYPE).setState(pathXTileState.VISIBLE_STATE.toString());
      miniGame.getGUIButtons().get(DIALOG_BUTTON_TYPE).setEnabled(true);
      miniGame.getGUIButtons().get(DIALOG2_BUTTON_TYPE).setEnabled(true);
      
    }
    
    /**
     * Updates the player record, adding a game without a win.
     */
    public void endGameAsLoss()
    {
        miniGame.getGUIDialogs().get(LEVEL_DIALOG_TYPE).setState(pathXTileState.LOSE_STATE.toString());
      miniGame.getGUIButtons().get(DIALOG_BUTTON_TYPE).setState(pathXTileState.VISIBLE_STATE.toString());
       miniGame.getGUIButtons().get(DIALOG2_BUTTON_TYPE).setState(pathXTileState.VISIBLE_STATE.toString());
      miniGame.getGUIButtons().get(DIALOG_BUTTON_TYPE).setEnabled(true);
      miniGame.getGUIButtons().get(DIALOG2_BUTTON_TYPE).setEnabled(true);
      
        if(hasLost!= true){
            totalMoney -= (int)(totalMoney * .1);
        }
    }

    /**
     * Called when a game is started, the game grid is reset.
     *
     * @param game
     */
    @Override
    public void reset(MiniGame game)
    {
        
    }

    /**
     * Called each frame, this method updates all the game objects.
     *
     * @param game The Sorting Hat game to be updated.
     */
    @Override
    public void updateAll(MiniGame game)
    {
        try
        {
            // MAKE SURE THIS THREAD HAS EXCLUSIVE ACCESS TO THE DATA
            game.beginUsingData();
            
            
            
            player.update(game);
            if(((pathXMiniGame)miniGame).isCurrentScreenState(GAMEPLAY_SCREEN_STATE)){
            pathXIntersection i = level.getDestination();
            double distance = calculateDistanceBetweenPoints(i.x + 217, i.y, (int)player.getX() + viewport.x, (int)player.getY() + viewport.y);
            if (distance < 66 &&hasWon != true)
            {
                // MAKE THIS THE SELECTED INTERSECTION
                endGameAsWin();
            }
            
            for(int j = 0; j < cops.size(); j++){
                distance = calculateDistanceBetweenPoints((int)cops.get(j).getX(), (int)cops.get(j).getY(), 
                        (int)player.getX() + viewport.x, (int)player.getY() + viewport.y);
               
                if (distance < 30 &&hasLost != true)
            {
                // MAKE THIS THE SELECTED INTERSECTION
               
                endGameAsLoss();
            }
            }
            
             for(int j = 0; j < zombies.size(); j++){
                distance = calculateDistanceBetweenPoints((int)zombies.get(j).getX(), (int)zombies.get(j).getY(), 
                        (int)player.getX() + viewport.x, (int)player.getY() + viewport.y);
               
                if (distance < 30 &&hasLost != true)
            {
                // MAKE THIS THE SELECTED INTERSECTION
                if(hasCollidedWithZombie == false){
                    hasCollidedWithZombie = true;
                    PLAYER_SPEED -= (int)(PLAYER_SPEED * .5);
                }
              
                 
            }
                
            }
            
           // player.startMovingToTarget(PLAYER_SPEED);
        }
            if(((pathXMiniGame)miniGame).isCurrentScreenState(GAME_SCREEN_STATE))
            cheatLevels();
            
           
      
            // WE ONLY NEED TO UPDATE AND MOVE THE MOVING TILES
            //for (int i = 0; i < movingTiles.size(); i++)
            //{
                // GET THE NEXT TILE
              //  SortingHatTile tile = movingTiles.get(i);

                // THIS WILL UPDATE IT'S POSITION USING ITS VELOCITY
             //   tile.update(game);

                // IF IT'S REACHED ITS DESTINATION, REMOVE IT
                // FROM THE LIST OF MOVING TILES
              //  if (!tile.isMovingToTarget())
              //  {
                 //   movingTiles.remove(tile);
               // }
           // }

            // IF THE GAME IS STILL ON, THE TIMER SHOULD CONTINUE
            if (inProgress())
            {
                // KEEP THE GAME TIMER GOING IF THE GAME STILL IS
                //endTime = new GregorianCalendar();
            }
        } finally
        {
            // MAKE SURE WE RELEASE THE LOCK WHETHER THERE IS
            // AN EXCEPTION THROWN OR NOT
            game.endUsingData();
        }
    }
    
    
     public void cheatLevels(){
         PropertiesManager props = PropertiesManager.getPropertiesManager();
         ArrayList<String> levels = props.getPropertyOptionsList(PathX.pathXPropertyType.LEVEL_OPTIONS);
         if(((pathXMiniGame)miniGame).isCurrentScreenState(GAME_SCREEN_STATE)){
         if(levelsCheatActivated){
        for (int i = 0; i < levels.size(); i++){  
            miniGame.getGUIButtons().get(levels.get(i)).setState(pathXTileState.COMPLETED_STATE.toString());
             miniGame.getGUIButtons().get(levels.get(i)).setEnabled(true);
       }
         }
         if(!levelsCheatActivated){
        for (int i = 0; i < levels.size(); i++){ 
            if(i == levelsCompleted){
            miniGame.getGUIButtons().get(levels.get(i)).setState(pathXTileState.VISIBLE_STATE.toString());
             miniGame.getGUIButtons().get(levels.get(i)).setEnabled(true);
            }
            else if (i > levelsCompleted){
                miniGame.getGUIButtons().get(levels.get(i)).setState(pathXTileState.UNAVAILABLE_STATE.toString());
             miniGame.getGUIButtons().get(levels.get(i)).setEnabled(false);
            }
       }
         }
         }
         else{
            for (int i = 0; i < levels.size(); i++){  
            miniGame.getGUIButtons().get(levels.get(i)).setState(pathXTileState.INVISIBLE_STATE.toString());
            miniGame.getGUIButtons().get(levels.get(i)).setEnabled(false);
            }
         }
     }

    /**
     * This method is for updating any debug text to present to the screen. In a
     * graphical application like this it's sometimes useful to display data in
     * the GUI.
     *
     * @param game The Sorting Hat game about which to display info.
     */
    @Override
    public void updateDebugText(MiniGame game)
    {
    }
    
    public void moveViewport(int incX, int incY)
    {
        // MOVE THE VIEWPORT
        viewport.move(incX, incY);

        // AND NOW FORCE A REDRAW
       
    }
    
    public void updateBackgroundImage(String imgName){
        
        level.backgroundImageFileName = imgName;
        levelBackgroundImage = miniGame.loadImage("./img/pathX/" + imgName);
        
        
        //Image img = miniGame.loadImage(levelBackgroundImageName);
        
    }
    
    public void updateStartingLocationImage(String newStartImage)
    {
        
        level.setStartingLocationImageFileName("./img/pathX/"+ newStartImage);
        startingLocationImage = miniGame.loadImage(level.getStartingLocationImageFileName());
        level.setStartingLocationImageWidth(startingLocationImage.getWidth(null));
        //view.getCanvas().repaint();
    }
    
     public void updateDestinationImage(String newDestImage)
    {
        level.setDestinationImageFileName("./img/pathX/"+ newDestImage);
        destinationImage = miniGame.loadImage(level.getDestinationImageFileName());
        //view.getCanvas().repaint();
    }
     
     public void setPlayer(pathXTile player)
    {
        this.player = player;
    }
     
     public pathXIntersection findIntersectionAtCanvasLocation(int canvasX, int canvasY)
    {
        // CHECK TO SEE IF THE USER IS SELECTING AN INTERSECTION
        for (pathXIntersection i : level.getIntersections())
        {
            double distance = calculateDistanceBetweenPoints(i.x, i.y, canvasX + viewport.x - WEST_PANEL_WIDTH, canvasY + viewport.y);
            if (distance < INTERSECTION_RADIUS)
            {
                // MAKE THIS THE SELECTED INTERSECTION
                return i;
            }
        }
        return null;
    }
     
     public pathXRoad selectRoadAtCanvasLocation(int canvasX, int canvasY)
    {
        Iterator<pathXRoad> it = level.getRoads().iterator();
        Line2D.Double tempLine = new Line2D.Double();
        while (it.hasNext())
        {
            pathXRoad r = it.next();
            tempLine.x1 = r.node1.x;
            tempLine.y1 = r.node1.y;
            tempLine.x2 = r.node2.x;
            tempLine.y2 = r.node2.y;
            double distance = tempLine.ptSegDist(canvasX+viewport.x - WEST_PANEL_WIDTH, canvasY+viewport.y);
            
            // IS IT CLOSE ENOUGH?
            if (distance <= INT_STROKE)
            {
                // SELECT IT
                this.selectedRoad = r;
                return selectedRoad;
            }
        }
        return null;
    }
     
     public double calculateDistanceBetweenPoints(int x1, int y1, int x2, int y2)
    {
        double diffXSquared = Math.pow(x1 - x2, 2);
        double diffYSquared = Math.pow(y1 - y2, 2);
        return Math.sqrt(diffXSquared + diffYSquared);
    }
     
     
     
     
    
}
