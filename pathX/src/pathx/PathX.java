/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//edit

package pathx;

import static pathx.pathXConstants.*;
import properties_manager.PropertiesManager;
import xml_utilities.InvalidXMLFileFormatException;
import pathx.ui.pathXMiniGame;
/**
 *
 * @author John Dilag
 */
public class PathX {

     
    static pathXMiniGame miniGame = new pathXMiniGame();
   

    /**
     * This is where The Sorting Hat game application starts execution. We'll
     * load the application properties and then use them to build our
     * user interface and start the window in real-time mode.
     */
    public static void main(String[] args)
    {
        try
        {
            // LOAD THE SETTINGS FOR STARTING THE APP
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            props.addProperty(PropertiesManager.DATA_PATH_PROPERTY, PATH_DATA);
            props.loadProperties(PROPERTIES_FILE_NAME, PROPERTIES_SCHEMA_FILE_NAME);
            
            // THEN WE'LL LOAD THE GAME FLAVOR AS SPECIFIED BY THE PROPERTIES FILE
            String gameFlavorFile = props.getProperty(pathXPropertyType.FILE_GAME_PROPERTIES);
            props.loadProperties(gameFlavorFile, PROPERTIES_SCHEMA_FILE_NAME);

            // NOW WE CAN LOAD THE UI, WHICH WILL USE ALL THE FLAVORED CONTENT
            String appTitle = props.getProperty(pathXPropertyType.TEXT_TITLE_BAR_GAME);
            miniGame.initMiniGame(appTitle, FPS, WINDOW_WIDTH, WINDOW_HEIGHT);
            
            // GET THE PROPER WINDOW DIMENSIONS
            miniGame.startGame();
        }
        
        catch(InvalidXMLFileFormatException ixmlffe)
        {
            System.out.print("adsa");
           // pathXErrorHandler errorHandler = miniGame.getErrorHandler();
           // errorHandler.processError(SortingHatPropertyType.TEXT_ERROR_LOADING_XML_FILE);
        }
    }
    
    /**
     * SortingHatPropertyType represents the types of data that will need
     * to be extracted from XML files.
     */
    public enum pathXPropertyType
    {
        // LOADED FROM properties.xml
        
        /* SETUP FILE NAMES */
        FILE_GAME_PROPERTIES,
        FILE_PLAYER_RECORD,

        /* DIRECTORY PATHS FOR FILE LOADING */
        PATH_AUDIO,
        PATH_IMG,
        
        // LOADED FROM THE GAME FLAVOR PROPERTIES XML FILE
            // sorting_hat_properties.xml
                
        /* IMAGE FILE NAMES */
        IMAGE_BACKGROUND_GAME,
        IMAGE_BACKGROUND_GAME2,
        IMAGE_BACKGROUND_MENU,
        IMAGE_BACKGROUND_SETTING,
        IMAGE_BACKGROUND_HELP,
        IMAGE_BACKGROUND_GAMEPLAY,
        IMAGE_BACKGROUND_GAMEPLAY2,
        IMAGE_BUTTON_PLAY,
        IMAGE_BUTTON_PLAY_MOUSE_OVER,
        IMAGE_BUTTON_RESET,
        IMAGE_BUTTON_RESET_MOUSE_OVER,
        IMAGE_BUTTON_HELP,
        IMAGE_BUTTON_HELP_MOUSE_OVER,
        IMAGE_BUTTON_SETTING,
        IMAGE_BUTTON_SETTING_MOUSE_OVER,
        IMAGE_BUTTON_HOME,
        IMAGE_BUTTON_HOME_MOUSE_OVER,
        IMAGE_BUTTON_X,
        IMAGE_BUTTON_X_MOUSE_OVER,
        IMAGE_BUTTON_UP,
        IMAGE_BUTTON_DOWN,
        IMAGE_BUTTON_LEFT,
        IMAGE_BUTTON_RIGHT,
        IMAGE_BUTTON_LEVEL_RED,
        IMAGE_BUTTON_LEVEL_WHITE,
        IMAGE_BUTTON_LEVEL_GREEN,
        IMAGE_BUTTON_LEVEL_MOUSE_OVER,
        IMAGE_BUTTON_MUTE_OFF,
        IMAGE_BUTTON_MUTE_ON,
        IMAGE_BUTTON_DIALOG_X,
        IMAGE_BUTTON_PLAY_AGAIN,
        IMAGE_BUTTON_PLAY_AGAIN_MOUSE_OVER,
        IMAGE_BUTTON_LEAVE_TOWN,
        IMAGE_BUTTON_LEAVE_TOWN_MOUSE_OVER,
        IMAGE_DIALOG_LEVEL,
        IMAGE_DIALOG_WIN,
        IMAGE_DIALOG_LOST,
        IMAGE_PLAYER,
        IMAGE_COP,
        IMAGE_ZOMBIE,
        IMAGE_BANDIT,
        
        
        AUDIO_CUE_GAME_MUSIC,
        AUDIO_CUE_SPECIAL_USE,
       
        IMAGE_WINDOW_ICON,
        
        /* GAME TEXT */
        TEXT_ERROR_LOADING_AUDIO,
        TEXT_ERROR_LOADING_LEVEL,
        TEXT_ERROR_LOADING_RECORD,
        TEXT_ERROR_LOADING_XML_FILE,
        TEXT_ERROR_SAVING_RECORD,
       
        TEXT_PROMPT_EXIT,
        TEXT_TITLE_BAR_GAME,
        TEXT_TITLE_BAR_ERROR,
        
        /* AUDIO CUES */
        
        
        /* TILE LOADING STUFF */
        LEVEL_OPTIONS,
        LEVEL_IMAGE_OPTIONS,
        LEVEL_MOUSE_OVER_IMAGE_OPTIONS 
    
}

}