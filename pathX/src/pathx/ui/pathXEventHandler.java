package pathx.ui;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import mini_game.Sprite;
import mini_game.SpriteType;
import mini_game.Viewport;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import static pathx.pathXConstants.GAME_SCREEN_STATE;
import static pathx.pathXConstants.MENU_SCREEN_STATE;
import static pathx.pathXConstants.WINDOW_WIDTH;
import static pathx.pathXConstants.WINDOW_HEIGHT;
import static pathx.pathXConstants.BACKGROUND_TYPE;
import static pathx.pathXConstants.*;
import pathx.file.pathXFileManager;
import pathx.data.*;

import pathx.PathX;
import pathx.PathX.pathXPropertyType;
import pathx.data.pathXDataModel;
import properties_manager.PropertiesManager;


//import sorting_hat.file.SortingHatFileManager;

/**
 *
 * @author Richard McKenna & _____________________
 */
public class pathXEventHandler
{
    // THE SORTING HAT GAME, IT PROVIDES ACCESS TO EVERYTHING
    private pathXMiniGame game;
    private pathXDataModel dataModel;
     
    PropertiesManager props = PropertiesManager.getPropertiesManager();
    
    /**
     * Constructor, it just keeps the game for when the events happen.
     */
    public pathXEventHandler(pathXMiniGame initGame)
    {
        game = initGame;
        dataModel = (pathXDataModel)game.getDataModel();
    }

    /**
     * Called when the user clicks the close window button.
     */    
    public void respondToExitRequest()
    {
        // IF THE GAME IS STILL GOING ON, END IT AS A LOSS
        if(game.getCurrentScreenState().equals("GAMEPLAY_SCREEN_STATE")){
            game.switchToGameScreen();
        }
            
        else
        {
            game.getDataModel().endGameAsLoss();
             System.exit(0);  
        }
        // AND CLOSE THE ALL
             
    }

    /**
     * Called when the user clicks the New button.
     */
    public void respondToNewGameRequest()
    {
        // IF THERE IS A GAME UNDERWAY, COUNT IT AS A LOSS
        if (game.getDataModel().inProgress())
        {
            game.getDataModel().endGameAsLoss();
            game.reset();   
        }
        // RESET THE GAME AND ITS DATA
           
    }

    /**
     * Called when the user clicks a button to select a level.
     */    
    public void respondToSettingRequest()
    {
        // WE ONLY LET THIS HAPPEN IF THE MENU SCREEN IS VISIBLE
        if (game.isCurrentScreenState(MENU_SCREEN_STATE))
        {
            
            game.switchToSettingScreen();
        }        
    }
    
     public void respondToCloseDialogRequest()
    {
        // WE ONLY LET THIS HAPPEN IF THE MENU SCREEN IS VISIBLE
        if (game.isCurrentScreenState(GAMEPLAY_SCREEN_STATE))
        {
            
            game.getGUIDialogs().get(LEVEL_DIALOG_TYPE).setState(pathXTileState.INVISIBLE_STATE.toString());
             game.getGUIButtons().get(DIALOG_X_BUTTON_TYPE).setState(pathXTileState.INVISIBLE_STATE.toString());
             game.getGUIButtons().get(DIALOG_X_BUTTON_TYPE).setEnabled(false);
        }        
    }
    
    public void respondToHelpRequest()
    {
        // WE ONLY LET THIS HAPPEN IF THE MENU SCREEN IS VISIBLE
        if (game.isCurrentScreenState(MENU_SCREEN_STATE))
        {
            
            game.switchToHelpScreen();
        }        
    }
    
    public void respondToMuteRequest()
    {
        // WE ONLY LET THIS HAPPEN IF THE MENU SCREEN IS VISIBLE
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        if (game.isCurrentScreenState(SETTING_SCREEN_STATE))
        {
            
            if(game.getGUIButtons().get(MUTE_BUTTON_TYPE).getState().equals(pathXTileState.VISIBLE_STATE.toString())){
                game.getGUIButtons().get(MUTE_BUTTON_TYPE).setState(pathXTileState.SELECTED_STATE.toString());
                game.getAudio().stop(pathXPropertyType.AUDIO_CUE_GAME_MUSIC.toString());
            }else{
                game.getGUIButtons().get(MUTE_BUTTON_TYPE).setState(pathXTileState.VISIBLE_STATE.toString());
                game.getAudio().play(pathXPropertyType.AUDIO_CUE_GAME_MUSIC.toString(),true);
            }
            
                
        }        
    }
    
    public void respondToPlayAgainRequest(){
        if(game.isCurrentScreenState(GAMEPLAY_SCREEN_STATE)){
            game.switchToGameScreen();
            game.switchToGameplayScreen();
        }
    }
    
    public void respondToPlayRequest()
    {
        // WE ONLY LET THIS HAPPEN IF THE MENU SCREEN IS VISIBLE
        if (game.isCurrentScreenState(MENU_SCREEN_STATE))
        {
            
            game.switchToGameScreen();
        }      
        
        
       // Viewport viewport = new Viewport();
        //viewport.setScreenSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        //viewport.setGameWorldSize(1052, 1024);
        
        
    }
    
    public void respondToLevelSelectRequest(String levelFile)
    {
        // WE ONLY LET THIS HAPPEN IF THE MENU SCREEN IS VISIBLE
        if (game.isCurrentScreenState(GAME_SCREEN_STATE))
        {
             pathXDataModel data = (pathXDataModel)game.getDataModel();
        
            // UPDATE THE DATA
            pathXFileManager fileManager = game.getFileManager();
            
            fileManager.loadLevel(levelFile, data);
            
            game.switchToGameplayScreen();
            //data.reset(game);
            

            // GO TO THE GAME
            
            
        }      
        
        
       // Viewport viewport = new Viewport();
        //viewport.setScreenSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        //viewport.setGameWorldSize(1052, 1024);
        
        
    }
    
    
    public void respondToScrollRequest(String direction){
        ArrayList<String> levels = props.getPropertyOptionsList(PathX.pathXPropertyType.LEVEL_OPTIONS);
        
        pathXDataModel data = (pathXDataModel)game.getDataModel();
        
        if(game.getCurrentScreenState().equals(GAMEPLAY_SCREEN_STATE)){
            if(direction.equals("UP_BUTTON_TYPE"))
                data.moveViewport(0,-6);
            
            
            if(direction.equals("RIGHT_BUTTON_TYPE"))
                data.moveViewport(6,0);
            
            if(direction.equals("DOWN_BUTTON_TYPE"))
                data.moveViewport(0,6);
            
            
            if(direction.equals("LEFT_BUTTON_TYPE"))
                data.moveViewport(-6,0);
           
        }
        if(direction.equals("UP_BUTTON_TYPE")){
            if(game.getGUIDecor().get(BACKGROUND_TYPE).getY() <= 0){
                game.getGUIDecor().get(BACKGROUND_TYPE).setY(game.getGUIDecor().get(BACKGROUND_TYPE).getY() + 6);
                
                for (int i = 0; i < levels.size(); i++)
                {
                    game.getGUIButtons().get(levels.get(i)).setY(game.getGUIButtons().get(levels.get(i)).getY() +6);
                }
            }
        }
        else if(direction.equals("DOWN_BUTTON_TYPE")){
                if(game.getGUIDecor().get(BACKGROUND_TYPE).getY() >= 540-878){
                   game.getGUIDecor().get(BACKGROUND_TYPE).setY(game.getGUIDecor().get(BACKGROUND_TYPE).getY() - 6);
                   
                     
                for (int i = 0; i < levels.size(); i++)
                {
                    game.getGUIButtons().get(levels.get(i)).setY(game.getGUIButtons().get(levels.get(i)).getY() - 6);
                }
                   
                }
        }
             else if(direction.equals("LEFT_BUTTON_TYPE")){
                if(game.getGUIDecor().get(BACKGROUND_TYPE).getX() <= 0) {
                    game.getGUIDecor().get(BACKGROUND_TYPE).setX(game.getGUIDecor().get(BACKGROUND_TYPE).getX() + 6);
                    
                    for (int i = 0; i < levels.size(); i++)
                {
                    game.getGUIButtons().get(levels.get(i)).setX(game.getGUIButtons().get(levels.get(i)).getX() +6);
                }
                }
                
                
        
             }  else if(direction.equals("RIGHT_BUTTON_TYPE")){
                 if(game.getGUIDecor().get(BACKGROUND_TYPE).getX() >= 960-1200) {
                game.getGUIDecor().get(BACKGROUND_TYPE).setX(game.getGUIDecor().get(BACKGROUND_TYPE).getX() - 6);
                 for (int i = 0; i < levels.size(); i++)
                {
                    game.getGUIButtons().get(levels.get(i)).setX(game.getGUIButtons().get(levels.get(i)).getX() - 6);
                }
                 }
             }
                 
    }
    
    public void respondToHomeRequest()
    {
        // WE ONLY LET THIS HAPPEN IF THE MENU SCREEN IS VISIBLE
        if (!game.isCurrentScreenState(MENU_SCREEN_STATE))
        {
            
            game.switchToSplashScreen();
        }        
    }
    
     public void respondToXRequest()
    {
        // WE ONLY LET THIS HAPPEN IF THE MENU SCREEN IS VISIBLE
        if (!game.isCurrentScreenState(MENU_SCREEN_STATE))
        {
            
            game.switchToSplashScreen();
        }        
    }

    /**
     * Called when the user clicks the Stats button.
     */
    /**
     * Called when the user presses a key on the keyboard.
     */    
    public void respondToKeyPress(int keyCode)
    {
        //SortingHatDataModel data = (SortingHatDataModel)game.getDataModel();

        // CHEAT BY ONE MOVE. NOTE THAT IF WE HOLD THE C
        // KEY DOWN IT WILL CONTINUALLY CHEAT
       
        pathXDataModel data = (pathXDataModel)game.getDataModel();
        
        
            if (keyCode == KeyEvent.VK_DOWN)
            {            
                if(game.getCurrentScreenState().equals("GAME_SCREEN_STATE")){
            // ONLY DO THIS IF THE GAME IS NO OVER
                
               if(game.getGUIDecor().get(BACKGROUND_TYPE).getY() >= 540-878){
                   game.getGUIDecor().get(BACKGROUND_TYPE).setY(game.getGUIDecor().get(BACKGROUND_TYPE).getY() - 3);
                   ArrayList<String> levels = props.getPropertyOptionsList(PathX.pathXPropertyType.LEVEL_OPTIONS);
                for (int i = 0; i < levels.size(); i++)
                {
                    game.getGUIButtons().get(levels.get(i)).setY(game.getGUIButtons().get(levels.get(i)).getY() - 3);
                }
               }
                
                if(game.getCurrentScreenState().equals(GAMEPLAY_SCREEN_STATE))
                data.moveViewport(0,6);
                   
                }
            }
            if (keyCode == KeyEvent.VK_UP)
            {         
                if(game.getCurrentScreenState().equals("GAME_SCREEN_STATE")){
                pathXViewport vp = ((pathXDataModel)game.getDataModel()).getPathXViewport();
            // ONLY DO THIS IF THE GAME IS NO OVER
               if(game.getGUIDecor().get(BACKGROUND_TYPE).getY() <= 0){
                game.getGUIDecor().get(BACKGROUND_TYPE).setY(game.getGUIDecor().get(BACKGROUND_TYPE).getY() + 3);
                 ArrayList<String> levels = props.getPropertyOptionsList(PathX.pathXPropertyType.LEVEL_OPTIONS);
                for (int i = 0; i < levels.size(); i++)
                {
                    game.getGUIButtons().get(levels.get(i)).setY(game.getGUIButtons().get(levels.get(i)).getY() + 3);
                }
                
               }
                }
            if(game.getCurrentScreenState().equals(GAMEPLAY_SCREEN_STATE))
                data.moveViewport(0,-6);
            
           
                 
            
               
            }
            if (keyCode == KeyEvent.VK_LEFT)
            {        
                if(game.getCurrentScreenState().equals("GAME_SCREEN_STATE")){
            // ONLY DO THIS IF THE GAME IS NO OVER
                if(game.getGUIDecor().get(BACKGROUND_TYPE).getX() <= 0) {
                    game.getGUIDecor().get(BACKGROUND_TYPE).setX(game.getGUIDecor().get(BACKGROUND_TYPE).getX() + 3);
                    ArrayList<String> levels = props.getPropertyOptionsList(PathX.pathXPropertyType.LEVEL_OPTIONS);
                for (int i = 0; i < levels.size(); i++)
                {
                    game.getGUIButtons().get(levels.get(i)).setX(game.getGUIButtons().get(levels.get(i)).getX() + 3);
                }
                }
                
                }
                if(game.getCurrentScreenState().equals(GAMEPLAY_SCREEN_STATE))
                data.moveViewport(-6,0);
            }
            if (keyCode == KeyEvent.VK_RIGHT)
            {     
                if(game.getCurrentScreenState().equals("GAME_SCREEN_STATE")){
            // ONLY DO THIS IF THE GAME IS NO OVER
                
               if(game.getGUIDecor().get(BACKGROUND_TYPE).getX() >= 960-1200) {
                game.getGUIDecor().get(BACKGROUND_TYPE).setX(game.getGUIDecor().get(BACKGROUND_TYPE).getX() - 3);
                ArrayList<String> levels = props.getPropertyOptionsList(PathX.pathXPropertyType.LEVEL_OPTIONS);
                for (int i = 0; i < levels.size(); i++)
                {
                    game.getGUIButtons().get(levels.get(i)).setX(game.getGUIButtons().get(levels.get(i)).getX() - 3);
                }
               }
               
                }
               if(game.getCurrentScreenState().equals(GAMEPLAY_SCREEN_STATE))
                    data.moveViewport(6,0);
            }
            
            if(keyCode == KeyEvent.VK_L){
                if(game.isCurrentScreenState(GAME_SCREEN_STATE)){
                if(!data.getLevelsCheatActivated()){
                data.setLevelsCheatActivated(true);
                }
                else{
                data.setLevelsCheatActivated(false);   
                    
                }
                game.getAudio().play(pathXPropertyType.AUDIO_CUE_SPECIAL_USE.toString(), true);
                }
            }
            
             if(keyCode == KeyEvent.VK_M){
                if(game.isCurrentScreenState(GAME_SCREEN_STATE)){
                    game.getAudio().play(pathXPropertyType.AUDIO_CUE_SPECIAL_USE.toString(), true);
                    data.setTotalMoney(data.getTotalMoney() + 100);
                }
            }
             
            if(keyCode == KeyEvent.VK_R){
                
                data.setSpecialInUse('r', true);
            }
            
            if(keyCode == KeyEvent.VK_V){
                data.setSpecialInUse('v', true);
                game.getAudio().play(pathXPropertyType.AUDIO_CUE_SPECIAL_USE.toString(), true);
            }
            
            if(keyCode == KeyEvent.VK_V){
                PLAYER_SPEED += PLAYER_SPEED * .5;
                game.getAudio().play(pathXPropertyType.AUDIO_CUE_SPECIAL_USE.toString(), true);
            }
        
    }
}