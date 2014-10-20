package pathx.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.JPanel;
import mini_game.MiniGame;
import mini_game.Sprite;
import mini_game.SpriteType;
import mini_game.Viewport;
import properties_manager.PropertiesManager;
import pathx.data.pathXDataModel;
import static pathx.pathXConstants.*;
import pathx.PathX.pathXPropertyType;
import pathx.data.pathXRecord;
import pathx.data.*;


/**
 * This class performs all of the rendering for The Sorting Hat game application.
 * 
 * @author Richard McKenna
 */
public class pathXPanel extends JPanel
{
    // THIS IS ACTUALLY OUR Sorting Hat APP, WE NEED THIS
    // BECAUSE IT HAS THE GUI STUFF THAT WE NEED TO RENDER
    private MiniGame game;
    
    // AND HERE IS ALL THE GAME DATA THAT WE NEED TO RENDER
    private pathXDataModel data;
    
    // WE'LL USE THIS TO FORMAT SOME TEXT FOR DISPLAY PURPOSES
    private NumberFormat numberFormatter;
 
    // WE'LL USE THIS AS THE BASE IMAGE FOR RENDERING UNSELECTED TILES
    private BufferedImage blankTileImage;
    
    // WE'LL USE THIS AS THE BASE IMAGE FOR RENDERING SELECTED TILES
    private BufferedImage blankTileSelectedImage;
    
    // THIS IS FOR WHEN THE USE MOUSES OVER A TILE
    private BufferedImage blankTileMouseOverImage;
    
    private pathXMiniGame XMiniGame;
    
    Ellipse2D.Double recyclableCircle;
    Line2D.Double recyclableLine;
    HashMap<Integer, BasicStroke> recyclableStrokes;
    int triangleXPoints[] = {-ONE_WAY_TRIANGLE_WIDTH/2,  -ONE_WAY_TRIANGLE_WIDTH/2,  ONE_WAY_TRIANGLE_WIDTH/2};
    int triangleYPoints[] = {ONE_WAY_TRIANGLE_WIDTH/2, -ONE_WAY_TRIANGLE_WIDTH/2, 0};
     GeneralPath recyclableTriangle;
    pathXViewport viewport;
    /**
     * This constructor stores the game and data references,
     * which we'll need for rendering.
     * 
     * @param initGame The Sorting Hat game that is using
     * this panel for rendering.
     * 
     * @param initData The Sorting Hat game data.
     */
    public pathXPanel(MiniGame initGame, pathXDataModel initData)
    {
        game = initGame;
        data = (pathXDataModel)game.getDataModel();
        viewport = data.getPathXViewport();
        //viewport.x -= 217;
        numberFormatter = NumberFormat.getNumberInstance();
        numberFormatter.setMinimumFractionDigits(3);
        numberFormatter.setMaximumFractionDigits(3);
        
         recyclableCircle = new Ellipse2D.Double(0, 0, INTERSECTION_RADIUS * 2, INTERSECTION_RADIUS * 2);
        recyclableLine = new Line2D.Double(0,0,0,0);
        recyclableStrokes = new HashMap();
        for (int i = 1; i <= 10; i++)
        {
            recyclableStrokes.put(i, new BasicStroke(i*2));
        }
        
        recyclableTriangle =  new GeneralPath(   GeneralPath.WIND_EVEN_ODD,
                                                triangleXPoints.length);
        recyclableTriangle.moveTo(triangleXPoints[0], triangleYPoints[0]);
        for (int index = 1; index < triangleXPoints.length; index++) 
        {
            recyclableTriangle.lineTo(triangleXPoints[index], triangleYPoints[index]);
        };
        recyclableTriangle.closePath();
    }
    
    // MUTATOR METHODS
        // -setBlankTileImage
        // -setBlankTileSelectedImage
    
    /**
     * This mutator method sets the base image to use for rendering tiles.
     * 
     * @param initBlankTileImage The image to use as the base for rendering tiles.
     */
    public void setBlankTileImage(BufferedImage initBlankTileImage)
    {
        blankTileImage = initBlankTileImage;
    }
    
    /**
     * This mutator method sets the base image to use for rendering selected tiles.
     * 
     * @param initBlankTileSelectedImage The image to use as the base for rendering
     * selected tiles.
     */
    public void setBlankTileSelectedImage(BufferedImage initBlankTileSelectedImage)
    {
        blankTileSelectedImage = initBlankTileSelectedImage;
    }
    
    public void setBlankTileMouseOverImage(BufferedImage initBlankTileMouseOverImage)
    {
        blankTileMouseOverImage = initBlankTileMouseOverImage;
    }

    /**
     * This is where rendering starts. This method is called each frame, and the
     * entire game application is rendered here with the help of a number of
     * helper methods.
     * 
     * @param g The Graphics context for this panel.
     */
    @Override
    public void paintComponent(Graphics g)
    {
        try
        {
            Graphics2D g2 = (Graphics2D) g;
            // MAKE SURE WE HAVE EXCLUSIVE ACCESS TO THE GAME DATA
            game.beginUsingData();
        
            // CLEAR THE PANEL
            super.paintComponent(g);
        
            // RENDER THE BACKGROUND, WHICHEVER SCREEN WE'RE ON
            renderBackground(g);
            
           
            renderGUIControls(g);

            // ONLY RENDER THIS STUFF IF WE'RE ACTUALLY IN-GAME
            if (!data.notStarted())
            {
                // RENDER THE SNAKE
               
                // AND THE TILES
               // renderTiles(g);
                if(!((pathXMiniGame)game).isCurrentScreenState(GAMEPLAY_SCREEN_STATE) )
                renderBackground2(g);
                // AND THE DIALOGS, IF THERE ARE ANY
                if(((pathXMiniGame)game).isCurrentScreenState(GAMEPLAY_SCREEN_STATE) ){
                    renderLevelBackground(g);
                    renderRoads(g2);
                    renderIntersections(g2, g);
                    renderBackground3(g);
                    renderTile(g, data.getPlayer());
                    renderDialogs(g);
                    
                    
                }
                
                    renderStats(g);
                
                
                renderGUIControls(g);
                
                
                                
                // RENDERING THE GRID WHERE ALL THE TILES GO CAN BE HELPFUL
                // DURING DEBUGGIN TO BETTER UNDERSTAND HOW THEY RE LAID OUT
               // renderGrid(g);
                
                // RENDER THE ALGORITHM NAME
                
                renderHeader(g);
               
                
            }

            // AND THE BUTTONS AND DECOR
            
            
            if (!data.notStarted())
            {
                // AND THE TIME AND TILES STATS
                //renderStats(g);
            }
        
            // AND FINALLY, TEXT FOR DEBUGGING
           // renderDebuggingText(g);
        }
        finally
        {
            // RELEASE THE LOCK
            game.endUsingData();    
        }
    }
    
    // RENDERING HELPER METHODS
        // - renderBackground
        // - renderGUIControls
        // - renderSnake
        // - renderTiles
        // - renderDialogs
        // - renderGrid
        // - renderDebuggingText
    
    /**`
     * Renders the background image, which is different depending on the screen. 
     * 
     * @param g the Graphics context of this panel.
     */
    public void renderBackground(Graphics g)
    {
        // THERE IS ONLY ONE CURRENTLY SET
        Sprite bg;
        
        bg = game.getGUIDecor().get(BACKGROUND_TYPE);
        renderSprite(g, bg);
    }
    
    public void renderBackground2(Graphics g)
    {
        // THERE IS ONLY ONE CURRENTLY SET
        Sprite bg;
        
        bg = game.getGUIDecor().get(BACKGROUND2_TYPE);
        renderSprite(g, bg);
            
    }
    
     public void renderBackground3(Graphics g)
    {
        // THERE IS ONLY ONE CURRENTLY SET
        Sprite bg;
        
        bg = game.getGUIDecor().get(BACKGROUND3_TYPE);
        renderSprite(g, bg);
            
    }
     
     private void renderRoads(Graphics2D g2)
    {
        // GO THROUGH THE ROADS AND RENDER ALL OF THEM
        pathXViewport viewport = data.getPathXViewport();
        Iterator<pathXRoad> it = data.roadsIterator();
        g2.setStroke(recyclableStrokes.get(INT_STROKE));
        while (it.hasNext())
        {
            pathXRoad road = it.next();
            if (!data.isSelectedRoad(road))
                renderRoad(g2, road, INT_OUTLINE_COLOR);
        }
        
        // NOW DRAW THE LINE BEING ADDED, IF THERE IS ONE
       

        // AND RENDER THE SELECTED ONE, IF THERE IS ONE
        pathXRoad selectedRoad = data.getSelectedRoad();
        if (selectedRoad != null)
        {
            renderRoad(g2, selectedRoad, HIGHLIGHTED_COLOR);
        }
    }
     
     private void renderRoad(Graphics2D g2, pathXRoad road, Color c)
    {
        g2.setColor(c);
        int strokeId = road.getSpeedLimit()/10;

        // CLAMP THE SPEED LIMIT STROKE
        if (strokeId < 1) strokeId = 1;
        if (strokeId > 10) strokeId = 10;
        g2.setStroke(recyclableStrokes.get(strokeId));

        // LOAD ALL THE DATA INTO THE RECYCLABLE LINE
        recyclableLine.x1 = road.getNode1().x-viewport.x + WEST_PANEL_WIDTH;
        recyclableLine.y1 = road.getNode1().y-viewport.y;
        recyclableLine.x2 = road.getNode2().x-viewport.x + WEST_PANEL_WIDTH;
        recyclableLine.y2 = road.getNode2().y-viewport.y;

        // AND DRAW IT
        g2.draw(recyclableLine);
        
        // AND IF IT'S A ONE WAY ROAD DRAW THE MARKER
        if (road.isOneWay())
        {
            this.renderOneWaySignalsOnRecyclableLine(g2);
        }
    }

    /**
     * Renders all the GUI decor and buttons.
     * 
     * @param g this panel's rendering context.
     */
    public void renderGUIControls(Graphics g)
    {
        // GET EACH DECOR IMAGE ONE AT A TIME
        Collection<Sprite> decorSprites = game.getGUIDecor().values();
        for (Sprite s : decorSprites)
        {
           if (s.getSpriteType().getSpriteTypeID() != BACKGROUND_TYPE)
               renderSprite(g, s);
        }
        
        // AND NOW RENDER THE BUTTONS
        Collection<Sprite> buttonSprites = game.getGUIButtons().values();
        for (Sprite s : buttonSprites)
        {
            renderSprite(g, s);
        }
    }
    
    public void renderHeader(Graphics g)
    {
       // g.setColor(COLOR_ALGORITHM_HEADER);
        
    }
    
   public void renderLevelBackground(Graphics g){
        
        Image img = ((pathXDataModel)data).getLevelBackgroundImage();
       
        viewport.setLevelDimensions(1100, 650);
        viewport.setViewportDimensions(960, 540);
        g.drawImage(img, 0, 0, viewport.width, viewport.height, viewport.x, viewport.y, viewport.x + viewport.width, viewport.y + viewport.height, null);
    }
   
   private void renderIntersections(Graphics2D g2, Graphics g)
    {
        Iterator<pathXIntersection> it = data.intersectionsIterator();
        int i = 0;
        while (it.hasNext())
        {
            pathXIntersection intersection = it.next();

            // ONLY RENDER IT THIS WAY IF IT'S NOT THE START OR DESTINATION
            // AND IT IS IN THE VIEWPORT
            if ((!data.isStartingLocation(intersection))
                    && (!data.isDestination(intersection))
                    && viewport.isCircleBoundingBoxInsideViewport(intersection.x, intersection.y, INTERSECTION_RADIUS)
                    )
            {
                // FIRST FILL
                if (intersection.isOpen())
                {
                    g2.setColor(OPEN_INT_COLOR);
                } else
                {
                    g2.setColor(CLOSED_INT_COLOR);
                }
                recyclableCircle.x = intersection.x - viewport.x - INTERSECTION_RADIUS + WEST_PANEL_WIDTH;
                recyclableCircle.y = intersection.y - viewport.y - INTERSECTION_RADIUS;
                g2.fill(recyclableCircle);

                // AND NOW THE OUTLINE
                if (data.isSelectedIntersection(intersection))
                {
                    g2.setColor(HIGHLIGHTED_COLOR);
                } else
                {
                    g2.setColor(INT_OUTLINE_COLOR);
                }
                Stroke s = recyclableStrokes.get(INT_STROKE);
                g2.setStroke(s);
                g2.draw(recyclableCircle);
            }
            
            
            //render cops
            
              
        }

        // AND NOW RENDER THE START AND DESTINATION LOCATIONS
        Image startImage = data.getStartingLocationImage();
        pathXIntersection startInt = data.getStartingLocation();
        renderIntersectionImage(g2, startImage, startInt);

        Image destImage = data.getDesinationImage();
        pathXIntersection destInt = data.getDestination();
        renderIntersectionImage(g2, destImage, destInt);
    }
   
   private void renderIntersectionImage(Graphics2D g2, Image img, pathXIntersection i)
    {
        // CALCULATE WHERE TO RENDER IT
        int w = img.getWidth(null);
        int h = img.getHeight(null);
        int x1 = i.x-(w/2);
        int y1 = i.y-(h/2);
        int x2 = x1 + img.getWidth(null);
        int y2 = y1 + img.getHeight(null);
        
        // ONLY RENDER IF INSIDE THE VIEWPORT
       if (viewport.isRectInsideViewport(x1, y1, x2, y2))
        {
            g2.drawImage(img, x1 - viewport.getX() + WEST_PANEL_WIDTH, y1 - viewport.getY(), null);
        }        
    }
   
   private void renderOneWaySignalsOnRecyclableLine(Graphics2D g2)
    {
        // CALCULATE THE ROAD LINE SLOPE
        double diffX = recyclableLine.x2 - recyclableLine.x1;
        double diffY = recyclableLine.y2 - recyclableLine.y1;
        double slope = diffY/diffX;
        
        // AND THEN FIND THE LINE MIDPOINT
        double midX = (recyclableLine.x1 + recyclableLine.x2)/2.0;
        double midY = (recyclableLine.y1 + recyclableLine.y2)/2.0;
        
        // GET THE RENDERING TRANSFORM, WE'LL RETORE IT BACK
        // AT THE END
        AffineTransform oldAt = g2.getTransform();
        
        // CALCULATE THE ROTATION ANGLE
        double theta = Math.atan(slope);
        if (recyclableLine.x2 < recyclableLine.x1)
            theta = (theta + Math.PI);
        
        // MAKE A NEW TRANSFORM FOR THIS TRIANGLE AND SET IT
        // UP WITH WHERE WE WANT TO PLACE IT AND HOW MUCH WE
        // WANT TO ROTATE IT
        AffineTransform at = new AffineTransform();        
        at.setToIdentity();
        at.translate(midX, midY);
        at.rotate(theta);
        g2.setTransform(at);
        
        // AND RENDER AS A SOLID TRIANGLE
        g2.fill(recyclableTriangle);
        
        // RESTORE THE OLD TRANSFORM SO EVERYTHING DOESN'T END UP ROTATED 0
        g2.setTransform(oldAt);
    }
   
   
   public void renderTile(Graphics g, pathXTile tileToRender)
    {
        // ONLY RENDER VISIBLE TILES
        if (!tileToRender.getState().equals(pathXTileState.INVISIBLE_STATE.toString()))
        {
            viewport = data.getPathXViewport();
            int correctedTileX = (int)(tileToRender.getX());
            int correctedTileY = (int)(tileToRender.getY());

            // THEN THE TILE IMAGE
            SpriteType bgST = tileToRender.getSpriteType();
            Image img = bgST.getStateImage(tileToRender.getState());
            g.drawImage(img,    correctedTileX - viewport.x, 
                                correctedTileY - viewport.y, 
                                bgST.getWidth(), bgST.getHeight(), null); 
            
            Iterator<pathXTile> cops = data.getCops().iterator();
            while (cops.hasNext())
            {
            pathXTile tile = cops.next();
            renderTile2(g, tile);
            }
            
            Iterator<pathXTile> zombies = data.getZombies().iterator();
            while (zombies.hasNext())
            {
            pathXTile tile = zombies.next();
            renderTile2(g, tile);
            }
        }
    }
   
   public void renderTile2(Graphics g, pathXTile tileToRender)
    {
        // ONLY RENDER VISIBLE TILES
        if (!tileToRender.getState().equals(pathXTileState.INVISIBLE_STATE.toString()))
        {
            viewport = data.getPathXViewport();
            int correctedTileX = (int)(tileToRender.getX());
            int correctedTileY = (int)(tileToRender.getY());

            // THEN THE TILE IMAGE
            SpriteType bgST = tileToRender.getSpriteType();
            Image img = bgST.getStateImage(tileToRender.getState());
            g.drawImage(img,    correctedTileX -viewport.x, 
                                correctedTileY - viewport.y , 
                                bgST.getWidth(), bgST.getHeight(), null); 
            
            
        }
    }
   
   public void renderStats(Graphics g)
    {
        // RENDER THE GAME TIME AND THE TILES LEFT FOR IN-GAME
        if (((pathXMiniGame)game).isCurrentScreenState(GAMEPLAY_SCREEN_STATE) )
        {
             if(game.getGUIDialogs().get(LEVEL_DIALOG_TYPE).getState().equals(pathXTileState.VISIBLE_STATE.toString())){
            // RENDER THE TILES LEFT
        g.setFont(FONT_TEXT_LEVEL_NAME);
        g.setColor(Color.BLACK);
//
            // RENDER THE TIME
          String levelName = data.getLevel().getLevelName();
          int x = 440;
          int y = 130;
          g.drawString(levelName, x, y);
          
           g.setFont(FONT_TEXT_LEVEL_INFO);
           x = 350;
           y = 200;
           String money = "Money: $" + data.getLevel().getMoney();
           g.drawString(money, x, y);
           
           y += 30;
             
        
           if(data.getLevel().getStartingLocationImageFileName().equals("./img/pathX/PiggyBank.png")){
           
           String info = "You just stole a piggy bank"; 
           g.drawString(info, x, y);
           info = "from that annoying little pest";
           g.drawString(info, x, y += 30);      
           info = "next door. Get to the hideout!";
           g.drawString(info, x, y += 30); 
           }
           }
             else if(game.getGUIDialogs().get(LEVEL_DIALOG_TYPE).getState().equals(pathXTileState.WIN_STATE.toString())){
                g.setFont(FONT_TEXT_LEVEL_INFO);
                int x = 350;
                int y = 200;
                
                String info = "You got away! You earned"; 
                g.drawString(info, x, y);
           info = "$" + data.getLevel().getMoney();
           g.drawString(info, x, y += 30);      
           info = "Play again or Leave Town?";
           g.drawString(info, x, y += 30); 
             }
           
        }  
    
         if (((pathXMiniGame)game).isCurrentScreenState(GAME_SCREEN_STATE)){
             g.setFont(FONT_TEXT_LEVEL_INFO);
             int x = 700;
             int y = 40;
             String money = "$" + data.getTotalMoney();
             g.drawString(money, x, y);
             g.drawString("$2000", 630, y+= 40);
             
             
         }
    }
   

    /**
     * This method renders the on-screen stats that change as
     * the game progresses. This means things like the game time
     * and the number of tiles remaining.
     * 
     * @param g the Graphics context for this panel
     
    public void renderStats(Graphics g)
    {
        // RENDER THE GAME TIME AND THE TILES LEFT FOR IN-GAME
        if (((SortingHatMiniGame)game).isCurrentScreenState(GAME_SCREEN_STATE) 
                && data.inProgress() || data.isPaused())
        {
            // RENDER THE TILES LEFT
            g.setFont(FONT_TEXT_DISPLAY);
            g.setColor(Color.BLACK);

            // RENDER THE TIME
            String time = data.gameTimeToText();
            int x = TIME_X + TIME_OFFSET;
            int y = TIME_Y + TIME_TEXT_OFFSET;
            g.drawString(time, x, y);
        }        
        
        // IF THE STATS DIALOG IS VISIBLE, ADD THE TEXTUAL STATS
        if (game.getGUIDialogs().get(STATS_DIALOG_TYPE).getState().equals(SortingHatTileState.VISIBLE_STATE.toString()))
        {
            g.setFont(FONT_STATS);
            g.setColor(COLOR_STATS);
            String currentLevel = data.getCurrentLevel();
            int lastSlash = currentLevel.lastIndexOf("/");
            String levelName = currentLevel.substring(lastSlash+1);
            SortingHatRecord record = ((SortingHatMiniGame)game).getPlayerRecord();

            // GET ALL THE STATS
            String algorithm = record.getAlgorithm(currentLevel);
            int games = record.getGamesPlayed(currentLevel);
            int wins = record.getWins(currentLevel);

            // GET ALL THE STATS PROMPTS
            PropertiesManager props = PropertiesManager.getPropertiesManager();            
            String algorithmPrompt = props.getProperty(SortingHatPropertyType.TEXT_LABEL_STATS_ALGORITHM);
            String gamesPrompt = props.getProperty(SortingHatPropertyType.TEXT_LABEL_STATS_GAMES);
            String winsPrompt = props.getProperty(SortingHatPropertyType.TEXT_LABEL_STATS_WINS);

            // NOW DRAW ALL THE STATS WITH THEIR LABELS
            int dot = levelName.indexOf(".");
            levelName = levelName.substring(0, dot);
            g.drawString(levelName,                                     STATS_LEVEL_X, STATS_LEVEL_Y);
            g.drawString(algorithmPrompt + algorithm,                   STATS_LEVEL_X, STATS_ALGORITHM_Y);
            g.drawString(gamesPrompt + games,                           STATS_LEVEL_X, STATS_GAMES_Y);
            g.drawString(winsPrompt + wins,                             STATS_LEVEL_X, STATS_WINS_Y);
        }
    }
     */   
    /**
     * Renders all the game tiles, doing so carefully such
     * that they are rendered in the proper order.
     * 
     * @param g the Graphics context of this panel.
     
    public void renderTiles(Graphics g)
    {
        // DRAW THE GRID
        ArrayList<SortingHatTile> tilesToSort = data.getTilesToSort();
        for (int i = 0; i < tilesToSort.size(); i++)
        {
            SortingHatTile tile = tilesToSort.get(i);
            if (tile != null)
                renderTile(g, tile);
        }
        
        // THEN DRAW ALL THE MOVING TILES
        Iterator<SortingHatTile> movingTiles = data.getMovingTiles();
        while (movingTiles.hasNext())
        {
            SortingHatTile tile = movingTiles.next();
            renderTile(g, tile);
        }
        
        // AND THE SELECTED TILE, IF THERE IS ONE
        SortingHatTile selectedTile = data.getSelectedTile();
        if (selectedTile != null)
            renderTile(g, selectedTile);
    }
    */
    /**
     * Helper method for rendering the tiles that are currently moving.
     * 
     * @param g Rendering context for this panel.
     * 
     * @param tileToRender Tile to render to this panel.
     
    public void renderTile(Graphics g, SortingHatTile tileToRender)
    {
        // ONLY RENDER VISIBLE TILES
        if (!tileToRender.getState().equals(SortingHatTileState.INVISIBLE_STATE.toString()))
        {
            Viewport viewport = data.getViewport();
            int correctedTileX = (int)(tileToRender.getX());
            int correctedTileY = (int)(tileToRender.getY());

            // THEN THE TILE IMAGE
            SpriteType bgST = tileToRender.getSpriteType();
            Image img = bgST.getStateImage(tileToRender.getState());
            g.drawImage(img,    correctedTileX, 
                                correctedTileY, 
                                bgST.getWidth(), bgST.getHeight(), null); 
        }
    }
    
    /**
     * Renders the game dialog boxes.
     * 
     * @param g This panel's graphics context.
     */
    public void renderDialogs(Graphics g)
    {
        // GET EACH DECOR IMAGE ONE AT A TIME
        Collection<Sprite> dialogSprites = game.getGUIDialogs().values();
        for (Sprite s : dialogSprites)
        {
            // RENDER THE DIALOG, NOTE IT WILL ONLY DO IT IF IT'S VISIBLE
            renderSprite(g, s);
        }
    }
    
    /**
     * Renders the s Sprite into the Graphics context g. Note
     * that each Sprite knows its own x,y coordinate location.
     * 
     * @param g the Graphics context of this panel
     * 
     * @param s the Sprite to be rendered
     */
    public void renderSprite(Graphics g, Sprite s)
    {
        // ONLY RENDER THE VISIBLE ONES
        if (!s.getState().equals(pathXTileState.INVISIBLE_STATE.toString()))
        {
            SpriteType bgST = s.getSpriteType();
            Image img = bgST.getStateImage(s.getState());
            g.drawImage(img, (int)s.getX(), (int)s.getY(), null); 
        }
    }
}

    /**
     * This method renders grid lines in the game tile grid to help
     * during debugging.
     * 
     * @param g Graphics context for this panel.
     
    public void renderGrid(Graphics g)
    {
        // ONLY RENDER THE GRID IF WE'RE DEBUGGING
        if (data.isDebugTextRenderingActive())
        {
            for (int i = 0; i < data.getNumGameGridColumns(); i++)
            {
                for (int j = 0; j < data.getNumGameGridRows(); j++)
                {
                    int x = data.calculateGridTileX(i);
                    int y = data.calculateGridTileY(j);
                    g.drawRect(x, y, TILE_WIDTH, TILE_HEIGHT);
                }
            }
        }
    }
    
    /**
     * Renders the debugging text to the panel. Note
     * that the rendering will only actually be done
     * if data has activated debug text rendering.
     * 
     * @param g the Graphics context for this panel
     
    public void renderDebuggingText(Graphics g)
    {
        // IF IT'S ACTIVATED
        if (data.isDebugTextRenderingActive())
        {
            // ENABLE PROPER RENDER SETTINGS
            g.setFont(FONT_DEBUG_TEXT);
            g.setColor(COLOR_DEBUG_TEXT);
            
            // GO THROUGH ALL THE DEBUG TEXT
            Iterator<String> it = data.getDebugText().iterator();
            int x = data.getDebugTextX();
            int y = data.getDebugTextY();
            while (it.hasNext())
            {
                // RENDER THE TEXT
                String text = it.next();
                g.drawString(text, x, y);
                y += 20;
            }   
        } 
    }
}*/