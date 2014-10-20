package pathx.ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import pathx.data.pathXDataModel;
import mini_game.MiniGame;
import mini_game.MiniGameState;
import static pathx.pathXConstants.*;
import mini_game.Sprite;
import mini_game.SpriteType;
import mini_game.Viewport;
import properties_manager.PropertiesManager;
import pathx.pathXConstants;
import pathx.PathX.pathXPropertyType;
import pathx.file.pathXFileManager;
import pathx.data.*;

/**
 * This is the actual mini game, as extended from the mini game framework. It
 * manages all the UI elements.
 * 
 * 
 * 
 * @author John Dilag
 */
public class pathXMiniGame extends MiniGame
{
    // THE PLAYER RECORD FOR EACH LEVEL, WHICH LIVES BEYOND ONE SESSION
    private pathXRecord record;

    // HANDLES GAME UI EVENTS
    private pathXEventHandler eventHandler;
    
    // HANDLES ERROR CONDITIONS
    private pathXErrorHandler errorHandler;
    
    // MANAGES LOADING OF LEVELS AND THE PLAYER RECORDS FILES
    private pathXFileManager fileManager = new pathXFileManager(this);
    
    // THE SCREEN CURRENTLY BEING PLAYED
    private String currentScreenState;
    
    public String getCurrentScreenState(){
        return currentScreenState;
    }
    
    private SpriteType st2 = new SpriteType(GAME_BACKGROUND_TYPE);
    private Sprite s2 = new Sprite(st2, 0, 0, 0, 0, GAME_SCREEN_STATE);
    
    public Sprite getGameBackgroundSprite(){
        return s2;
    }

    private pathXDataModel dataModel = new pathXDataModel(this);
    
    // ACCESSOR METHODS
        // - getPlayerRecord
        // - getErrorHandler
        // - getFileManager
        // - isCurrentScreenState
    
    /**
     * Accessor method for getting the player record object, which
     * summarizes the player's record on all levels.
     * 
     * @return The player's complete record.
     */
    public pathXRecord getPlayerRecord() 
    { 
        return record; 
    }

    /**
     * Accessor method for getting the application's error handler.
     * 
     * @return The error handler.
     */
    public pathXErrorHandler getErrorHandler()
    {
        return errorHandler;
    }

    /**
     * Accessor method for getting the app's file manager.
     * 
     * @return The file manager.
     */
    public pathXFileManager getFileManager()
    {
        return fileManager;
    }

    /**
     * Used for testing to see if the current screen state matches
     * the testScreenState argument. If it mates, true is returned,
     * else false.
     * 
     * @param testScreenState Screen state to test against the 
     * current state.
     * 
     * @return true if the current state is testScreenState, false otherwise.
     */
    public boolean isCurrentScreenState(String testScreenState)
    {
        return testScreenState.equals(currentScreenState);
    }
    
    // VIEWPORT UPDATE METHODS
        // - initViewport
        // - scroll
        // - moveViewport

     // SERVICE METHODS
        // - displayStats
        // - savePlayerRecord
        // - switchToGameScreen
        // - switchToSplashScreen
        // - updateBoundaries
   
    /**
     * This method displays makes the stats dialog display visible,
     * which includes the text inside.
     
    public void displayStats()
    {
        // MAKE SURE ONLY THE PROPER DIALOG IS VISIBLE
        guiDialogs.get(WIN_DIALOG_TYPE).setState(PathXTileState.INVISIBLE_STATE.toString());
        guiDialogs.get(STATS_DIALOG_TYPE).setState(PathXTileState.VISIBLE_STATE.toString());
    }
    *
    
    /**
     * This method forces the file manager to save the current player record.
     
    public void savePlayerRecord()
    {
        fileManager.saveRecord(record);
    }
    
    /**
     * This method switches the application to the game screen, making
     * all the appropriate UI controls visible & invisible.
     */
    
    public void switchToSettingScreen(){
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        guiDecor.get(BACKGROUND_TYPE).setState(SETTING_SCREEN_STATE);
        guiButtons.get(PLAY_BUTTON_TYPE).setState(pathXTileState.INVISIBLE_STATE.toString());
        guiButtons.get(PLAY_BUTTON_TYPE).setEnabled(false);
        guiButtons.get(RESET_BUTTON_TYPE).setState(pathXTileState.INVISIBLE_STATE.toString());
        guiButtons.get(RESET_BUTTON_TYPE).setEnabled(false);
        guiButtons.get(SETTING_BUTTON_TYPE).setState(pathXTileState.INVISIBLE_STATE.toString());
        guiButtons.get(SETTING_BUTTON_TYPE).setEnabled(false);
        guiButtons.get(HELP_BUTTON_TYPE).setState(pathXTileState.INVISIBLE_STATE.toString());
        guiButtons.get(HELP_BUTTON_TYPE).setEnabled(false);
        
        
        guiButtons.get(HOME_BUTTON_TYPE).setState(pathXTileState.VISIBLE_STATE.toString());
        guiButtons.get(HOME_BUTTON_TYPE).setEnabled(true);
        guiButtons.get(X_BUTTON_TYPE).setState(pathXTileState.VISIBLE_STATE.toString());
        guiButtons.get(X_BUTTON_TYPE).setEnabled(true);
         guiButtons.get(MUTE_BUTTON_TYPE).setState(pathXTileState.VISIBLE_STATE.toString());
        guiButtons.get(MUTE_BUTTON_TYPE).setEnabled(true);
        
        currentScreenState = SETTING_SCREEN_STATE;
    }
    
    public void switchToHelpScreen(){
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        guiDecor.get(BACKGROUND_TYPE).setState(HELP_SCREEN_STATE);
        guiButtons.get(PLAY_BUTTON_TYPE).setState(pathXTileState.INVISIBLE_STATE.toString());
        guiButtons.get(PLAY_BUTTON_TYPE).setEnabled(false);
        guiButtons.get(RESET_BUTTON_TYPE).setState(pathXTileState.INVISIBLE_STATE.toString());
        guiButtons.get(RESET_BUTTON_TYPE).setEnabled(false);
        guiButtons.get(SETTING_BUTTON_TYPE).setState(pathXTileState.INVISIBLE_STATE.toString());
        guiButtons.get(SETTING_BUTTON_TYPE).setEnabled(false);
        guiButtons.get(HELP_BUTTON_TYPE).setState(pathXTileState.INVISIBLE_STATE.toString());
        guiButtons.get(HELP_BUTTON_TYPE).setEnabled(false);
        
        
        guiButtons.get(HOME_BUTTON_TYPE).setState(pathXTileState.VISIBLE_STATE.toString());
        guiButtons.get(HOME_BUTTON_TYPE).setEnabled(true);
        guiButtons.get(X_BUTTON_TYPE).setState(pathXTileState.VISIBLE_STATE.toString());
        guiButtons.get(X_BUTTON_TYPE).setEnabled(true);
      
        currentScreenState = HELP_SCREEN_STATE;
    }
    
    public void switchToGameScreen()
    {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        ((pathXDataModel)data).setHasWon(false);
        ((pathXDataModel)data).setHasLost(false);
        
        // CHANGE THE BACKGROUND
        ArrayList<String> levels = props.getPropertyOptionsList(pathXPropertyType.LEVEL_OPTIONS);
        
        guiDecor.get(BACKGROUND_TYPE).setState(GAME_SCREEN_STATE);
        guiDecor.get(BACKGROUND2_TYPE).setState(GAME_SCREEN_2_STATE);
        guiDecor.get(BACKGROUND_TYPE).setX(0);
         guiDecor.get(BACKGROUND_TYPE).setY(0);
         guiButtons.get(levels.get(0)).setX(500);
        guiButtons.get(levels.get(0)).setY(400);
        guiButtons.get(levels.get(1)).setX(300);
        guiButtons.get(levels.get(1)).setY(300);
        guiButtons.get(levels.get(2)).setX(200);
        guiButtons.get(levels.get(2)).setY(200);
        guiButtons.get(levels.get(3)).setX(350);
        guiButtons.get(levels.get(3)).setY(200);
        guiButtons.get(levels.get(4)).setX(400);
        guiButtons.get(levels.get(4)).setY(300);
        guiButtons.get(levels.get(5)).setX(500);
        guiButtons.get(levels.get(5)).setY(300);
        guiButtons.get(levels.get(6)).setX(200);
        guiButtons.get(levels.get(6)).setY(300);
        guiButtons.get(levels.get(7)).setX(400);
        guiButtons.get(levels.get(7)).setY(500);
        
         guiButtons.get(UP_BUTTON_TYPE).setX(815);
         guiButtons.get(UP_BUTTON_TYPE).setY(120);
         guiButtons.get(LEFT_BUTTON_TYPE).setX(770);
         guiButtons.get(LEFT_BUTTON_TYPE).setY(164);
         guiButtons.get(RIGHT_BUTTON_TYPE).setX(860);
         guiButtons.get(RIGHT_BUTTON_TYPE).setY(160);
         guiButtons.get(DOWN_BUTTON_TYPE).setX(815);
         guiButtons.get(DOWN_BUTTON_TYPE).setY(200);
        
        
        
        
        // ACTIVATE THE TOOLBAR AND ITS CONTROLS
     
        guiButtons.get(PLAY_BUTTON_TYPE).setState(pathXTileState.INVISIBLE_STATE.toString());
        guiButtons.get(PLAY_BUTTON_TYPE).setEnabled(false);
        guiButtons.get(RESET_BUTTON_TYPE).setState(pathXTileState.INVISIBLE_STATE.toString());
        guiButtons.get(RESET_BUTTON_TYPE).setEnabled(false);
        guiButtons.get(SETTING_BUTTON_TYPE).setState(pathXTileState.INVISIBLE_STATE.toString());
        guiButtons.get(SETTING_BUTTON_TYPE).setEnabled(false);
        guiButtons.get(HELP_BUTTON_TYPE).setState(pathXTileState.INVISIBLE_STATE.toString());
        guiButtons.get(HELP_BUTTON_TYPE).setEnabled(false);
        guiDialogs.get(LEVEL_DIALOG_TYPE).setState(pathXTileState.INVISIBLE_STATE.toString());
        guiButtons.get(DIALOG_X_BUTTON_TYPE).setState(pathXTileState.INVISIBLE_STATE.toString());
        guiButtons.get(DIALOG_X_BUTTON_TYPE).setEnabled(false);
        guiButtons.get(DIALOG_BUTTON_TYPE).setState(pathXTileState.INVISIBLE_STATE.toString());
        guiButtons.get(DIALOG_BUTTON_TYPE).setEnabled(false);
        guiButtons.get(DIALOG2_BUTTON_TYPE).setState(pathXTileState.INVISIBLE_STATE.toString());
        guiButtons.get(DIALOG2_BUTTON_TYPE).setEnabled(false);
        
        guiButtons.get(HOME_BUTTON_TYPE).setState(pathXTileState.VISIBLE_STATE.toString());
        guiButtons.get(HOME_BUTTON_TYPE).setEnabled(true);
        guiButtons.get(X_BUTTON_TYPE).setState(pathXTileState.VISIBLE_STATE.toString());
        guiButtons.get(X_BUTTON_TYPE).setEnabled(true);
         guiButtons.get(UP_BUTTON_TYPE).setState(pathXTileState.VISIBLE_STATE.toString());
        guiButtons.get(UP_BUTTON_TYPE).setEnabled(true);
        guiButtons.get(DOWN_BUTTON_TYPE).setState(pathXTileState.VISIBLE_STATE.toString());
        guiButtons.get(DOWN_BUTTON_TYPE).setEnabled(true);
        guiButtons.get(LEFT_BUTTON_TYPE).setState(pathXTileState.VISIBLE_STATE.toString());
        guiButtons.get(LEFT_BUTTON_TYPE).setEnabled(true);
        guiButtons.get(RIGHT_BUTTON_TYPE).setState(pathXTileState.VISIBLE_STATE.toString());
        guiButtons.get(RIGHT_BUTTON_TYPE).setEnabled(true);
        
        int count = ((pathXDataModel)data).getLevelsCompleted();
        for (String level : levels)
        {
            if(count == 0){
                guiButtons.get(level).setState(pathXTileState.VISIBLE_STATE.toString());
                guiButtons.get(level).setEnabled(true);
            }
            else if(count > 0){
                guiButtons.get(level).setState(pathXTileState.COMPLETED_STATE.toString());
            guiButtons.get(level).setEnabled(true);
            }
            else{
                guiButtons.get(level).setState(pathXTileState.UNAVAILABLE_STATE.toString());
                guiButtons.get(level).setEnabled(false);
            }
            
            count--;
        } 
        
        ((pathXDataModel)data).getPlayer().setState(pathXTileState.INVISIBLE_STATE.toString());
        ((pathXDataModel)data).getPlayer().setEnabled(false);
        
         guiButtons.get(MUTE_BUTTON_TYPE).setState(pathXTileState.INVISIBLE_STATE.toString());
        guiButtons.get(MUTE_BUTTON_TYPE).setEnabled(false);
        

        // AND CHANGE THE SCREEN STATE
        currentScreenState = GAME_SCREEN_STATE;
        data.setGameState(MiniGameState.IN_PROGRESS);
        // PLAY THE GAMEPLAY SCREEN SONG
       // audio.stop(PathXPropertyType.SONG_CUE_MENU_SCREEN.toString()); 
       // audio.play(PathXPropertyType.SONG_CUE_GAME_SCREEN.toString(), true);        
    }
    
    /**
     * This method switches the application to the menu screen, making
     * all the appropriate UI controls visible & invisible.
     */  
    
    public void switchToGameplayScreen(){
         PropertiesManager props = PropertiesManager.getPropertiesManager();
        
         
         guiButtons.get(UP_BUTTON_TYPE).setX(75);
         guiButtons.get(UP_BUTTON_TYPE).setY(100);
         guiButtons.get(LEFT_BUTTON_TYPE).setX(30);
         guiButtons.get(LEFT_BUTTON_TYPE).setY(144);
         guiButtons.get(RIGHT_BUTTON_TYPE).setX(120);
         guiButtons.get(RIGHT_BUTTON_TYPE).setY(140);
         guiButtons.get(DOWN_BUTTON_TYPE).setX(75);
         guiButtons.get(DOWN_BUTTON_TYPE).setY(180);
         guiButtons.get(HOME_BUTTON_TYPE).setX(75);
         guiButtons.get(X_BUTTON_TYPE).setX(115);
         
        // CHANGE THE BACKGROUND
         guiDialogs.get(LEVEL_DIALOG_TYPE).setState(pathXTileState.VISIBLE_STATE.toString());
        guiDecor.get(BACKGROUND_TYPE).setState(GAMEPLAY_SCREEN_STATE);
        guiDecor.get(BACKGROUND2_TYPE).setState(GAMEPLAY_SCREEN_2_STATE);
         guiDecor.get(BACKGROUND_TYPE).setX(0);
         guiDecor.get(BACKGROUND_TYPE).setY(0);
         
         for(int i = 0; i < ((pathXDataModel)data).getCops().size(); i++){
         ((pathXDataModel)data).getCops().get(i).setState(pathXTileState.VISIBLE_STATE.toString());
          }
         
         for(int i = 0; i < ((pathXDataModel)data).getZombies().size(); i++){
         ((pathXDataModel)data).getZombies().get(i).setState(pathXTileState.VISIBLE_STATE.toString());
          }
        // ACTIVATE THE TOOLBAR AND ITS CONTROLS
     
        guiButtons.get(PLAY_BUTTON_TYPE).setState(pathXTileState.INVISIBLE_STATE.toString());
        guiButtons.get(PLAY_BUTTON_TYPE).setEnabled(false);
        guiButtons.get(RESET_BUTTON_TYPE).setState(pathXTileState.INVISIBLE_STATE.toString());
        guiButtons.get(RESET_BUTTON_TYPE).setEnabled(false);
        guiButtons.get(SETTING_BUTTON_TYPE).setState(pathXTileState.INVISIBLE_STATE.toString());
        guiButtons.get(SETTING_BUTTON_TYPE).setEnabled(false);
        guiButtons.get(HELP_BUTTON_TYPE).setState(pathXTileState.INVISIBLE_STATE.toString());
        guiButtons.get(HELP_BUTTON_TYPE).setEnabled(false);
        guiButtons.get(MUTE_BUTTON_TYPE).setState(pathXTileState.INVISIBLE_STATE.toString());
        guiButtons.get(MUTE_BUTTON_TYPE).setEnabled(false);
        
        guiButtons.get(HOME_BUTTON_TYPE).setState(pathXTileState.VISIBLE_STATE.toString());
        guiButtons.get(HOME_BUTTON_TYPE).setEnabled(true);
        guiButtons.get(X_BUTTON_TYPE).setState(pathXTileState.VISIBLE_STATE.toString());
        guiButtons.get(X_BUTTON_TYPE).setEnabled(true);
        
        
        
        ArrayList<String> levels = props.getPropertyOptionsList(pathXPropertyType.LEVEL_OPTIONS);
        for (String level : levels)
        {
            guiButtons.get(level).setState(pathXTileState.INVISIBLE_STATE.toString());
            guiButtons.get(level).setEnabled(false);
        }        

        
        
        
        
        

        // AND CHANGE THE SCREEN STATE
        currentScreenState = GAMEPLAY_SCREEN_STATE;
        
        guiButtons.get(DIALOG_X_BUTTON_TYPE).setState(pathXTileState.VISIBLE_STATE.toString());
        guiButtons.get(DIALOG_X_BUTTON_TYPE).setEnabled(true);
        
        
        
        int x = ((pathXDataModel)data).getLevel().getStartingLocation().getX() + WEST_PANEL_WIDTH + (PLAYER_WIDTH/2);
        int y = ((pathXDataModel)data).getLevel().getStartingLocation().getY() - (PLAYER_HEIGHT/2);
        ((pathXDataModel)data).getPlayer().setState(pathXTileState.VISIBLE_STATE.toString());
        ((pathXDataModel)data).getPlayer().setEnabled(true);
        ((pathXDataModel)data).getPlayer().setX(x);
        ((pathXDataModel)data).getPlayer().setY(y);
        //data.setGameState(MiniGameState.IN_PROGRESS);
        // PLAY THE GAMEPLAY SCREEN SONG
       // audio.stop(PathXPropertyType.SONG_CUE_MENU_SCREEN.toString()); 
       // audio.play(PathXPropertyType.SONG_CUE_GAME_SCREEN.toString(), true); 
    }
    public void switchToSplashScreen()
    {
        
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        ((pathXDataModel)data).setHasWon(false);
        
        guiDecor.get(BACKGROUND_TYPE).setX(0);
        guiDecor.get(BACKGROUND_TYPE).setY(0);
        // CHANGE THE BACKGROUND
        guiDecor.get(BACKGROUND_TYPE).setState(MENU_SCREEN_STATE);
        
        guiButtons.get(PLAY_BUTTON_TYPE).setState(pathXTileState.VISIBLE_STATE.toString());
        guiButtons.get(PLAY_BUTTON_TYPE).setEnabled(true);
        guiButtons.get(RESET_BUTTON_TYPE).setState(pathXTileState.VISIBLE_STATE.toString());
        guiButtons.get(RESET_BUTTON_TYPE).setEnabled(true);
        guiButtons.get(SETTING_BUTTON_TYPE).setState(pathXTileState.VISIBLE_STATE.toString());
        guiButtons.get(SETTING_BUTTON_TYPE).setEnabled(true);
        guiButtons.get(HELP_BUTTON_TYPE).setState(pathXTileState.VISIBLE_STATE.toString());
        guiButtons.get(HELP_BUTTON_TYPE).setEnabled(true);
        // DEACTIVATE ALL DIALOGS
        guiButtons.get(HOME_BUTTON_TYPE).setState(pathXTileState.INVISIBLE_STATE.toString());
        guiButtons.get(HOME_BUTTON_TYPE).setEnabled(false);
        guiButtons.get(X_BUTTON_TYPE).setState(pathXTileState.INVISIBLE_STATE.toString());
        guiButtons.get(X_BUTTON_TYPE).setEnabled(false);
        guiButtons.get(UP_BUTTON_TYPE).setState(pathXTileState.INVISIBLE_STATE.toString());
        guiButtons.get(UP_BUTTON_TYPE).setEnabled(false);
        guiButtons.get(DOWN_BUTTON_TYPE).setState(pathXTileState.INVISIBLE_STATE.toString());
        guiButtons.get(DOWN_BUTTON_TYPE).setEnabled(false);
        guiButtons.get(LEFT_BUTTON_TYPE).setState(pathXTileState.INVISIBLE_STATE.toString());
        guiButtons.get(LEFT_BUTTON_TYPE).setEnabled(false);
        guiButtons.get(RIGHT_BUTTON_TYPE).setState(pathXTileState.INVISIBLE_STATE.toString());
        guiButtons.get(RIGHT_BUTTON_TYPE).setEnabled(false);
        guiButtons.get(DIALOG_X_BUTTON_TYPE).setState(pathXTileState.INVISIBLE_STATE.toString());
        guiButtons.get(DIALOG_X_BUTTON_TYPE).setEnabled(false);
        guiButtons.get(MUTE_BUTTON_TYPE).setState(pathXTileState.INVISIBLE_STATE.toString());
        guiButtons.get(MUTE_BUTTON_TYPE).setEnabled(false);
        guiButtons.get(DIALOG_BUTTON_TYPE).setState(pathXTileState.INVISIBLE_STATE.toString());
        guiButtons.get(DIALOG_BUTTON_TYPE).setEnabled(false);
        guiButtons.get(DIALOG2_BUTTON_TYPE).setState(pathXTileState.INVISIBLE_STATE.toString());
        guiButtons.get(DIALOG2_BUTTON_TYPE).setEnabled(false);
        
        
        ArrayList<String> levels = props.getPropertyOptionsList(pathXPropertyType.LEVEL_OPTIONS);
        for (String level : levels)
        {
            guiButtons.get(level).setState(pathXTileState.INVISIBLE_STATE.toString());
            guiButtons.get(level).setEnabled(false);
        }     
        // HIDE THE TILES
        

        // MAKE THE CURRENT SCREEN THE MENU SCREEN
        currentScreenState = MENU_SCREEN_STATE;
        
        // AND UPDATE THE DATA GAME STATE
        data.setGameState(MiniGameState.NOT_STARTED);
        
        // PLAY THE WELCOME SCREEN SONG
        
    }
    
    // METHODS OVERRIDDEN FROM MiniGame
        // - initAudioContent
        // - initData
        // - initGUIControls
        // - initGUIHandlers
        // - reset
        // - updateGUI

    @Override
    /**
     * Initializes the sound and music to be used by the application.
     */
    public void initAudioContent()
    {
        try
        {
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            String audioPath = props.getProperty(pathXPropertyType.PATH_AUDIO);

            // LOAD ALL THE AUDIO
            
            loadAudioCue(pathXPropertyType.AUDIO_CUE_GAME_MUSIC);
            loadAudioCue(pathXPropertyType.AUDIO_CUE_SPECIAL_USE);
            //loadAudioCue(PathXPropertyType.SONG_CUE_MENU_SCREEN);
            //loadAudioCue(PathXPropertyType.SONG_CUE_GAME_SCREEN);

            // PLAY THE WELCOME SCREEN SONG
           audio.play(pathXPropertyType.AUDIO_CUE_GAME_MUSIC.toString(), true);
        }
        catch(UnsupportedAudioFileException | IOException | LineUnavailableException | InvalidMidiDataException | MidiUnavailableException e)
       {
         //   errorHandler.processError(PathXPropertyType.TEXT_ERROR_LOADING_AUDIO);
        }        
    }

    /**
     * This helper method loads the audio file associated with audioCueType,
     * which should have been specified via an XML properties file.
     */
    private void loadAudioCue(pathXPropertyType audioCueType) 
            throws  UnsupportedAudioFileException, IOException, LineUnavailableException, 
                    InvalidMidiDataException, MidiUnavailableException
    {
   
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String audioPath = props.getProperty(pathXPropertyType.PATH_AUDIO);
        String cue = props.getProperty(audioCueType.toString());
        audio.loadAudio(audioCueType.toString(), audioPath + cue);        
    }
    
    /**
     * Initializes the game data used by the apxplication. Note
     * that it is this method's obligation to construct and set
     * this Game's custom GameDataModel object as well as any
     * other needed game objects.
     */
    @Override
    public void initData()
    {        
       // INIT OUR ERROR HANDLER
       // errorHandler = new pathXErrorHandler(window);
        
        // INIT OUR FILE MANAGER
        fileManager = new pathXFileManager(this);

        // LOAD THE PLAYER'S RECORD FROM A FILE
        //record = fileManager.loadRecord();
        
        // INIT OUR DATA MANAGER
        data = new pathXDataModel(this);
        canvas = new pathXPanel(this, (pathXDataModel) data);
        registerLevelController(((pathXDataModel)data));
    }
    
    
    private void registerLevelController(pathXDataModel model)
    {
        pathXLevelController LevelHandler = new pathXLevelController(model);
        canvas.addMouseListener(LevelHandler);
        canvas.addMouseMotionListener(LevelHandler);
    }
    /**
     * Initializes the game controls, like buttons, used by
     * the game application. Note that this includes the tiles,
     * which serve as buttons of sorts.
     */
    @Override
    public void initGUIControls()
    {
        // WE'LL USE AND REUSE THESE FOR LOADING STUFF
        BufferedImage img;
        float x, y;
        SpriteType sT, sT2, sT3;
        Sprite s, s2;
 
        // FIRST PUT THE ICON IN THE WINDOW
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String imgPath = props.getProperty(pathXPropertyType.PATH_IMG);        
        String windowIconFile = props.getProperty(pathXPropertyType.IMAGE_WINDOW_ICON);
        img = loadImage(imgPath + windowIconFile);
        window.setIconImage(img);

        // CONSTRUCT THE PANEL WHERE WE'LL DRAW EVERYTHING
       // 
        
        window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        canvas = new pathXPanel(this, (pathXDataModel) data);
        
        // LOAD THE BACKGROUNDS, WHICH ARE GUI DECOR
        currentScreenState = MENU_SCREEN_STATE;
        img = loadImage(imgPath + props.getProperty(pathXPropertyType.IMAGE_BACKGROUND_MENU));
        sT = new SpriteType(BACKGROUND_TYPE);
        sT.addState(MENU_SCREEN_STATE, img);
        img = loadImage(imgPath + props.getProperty(pathXPropertyType.IMAGE_BACKGROUND_GAME));
        sT.addState(GAME_SCREEN_STATE, img);
        img = loadImage(imgPath + props.getProperty(pathXPropertyType.IMAGE_BACKGROUND_SETTING));
        sT.addState(SETTING_SCREEN_STATE, img);
        img = loadImage(imgPath + props.getProperty(pathXPropertyType.IMAGE_BACKGROUND_HELP));
        sT.addState(HELP_SCREEN_STATE, img);
        img = loadImage(imgPath + props.getProperty(pathXPropertyType.IMAGE_BACKGROUND_GAMEPLAY));
        sT.addState(GAMEPLAY_SCREEN_STATE, img);
        s = new Sprite(sT, 0, 0, 0, 0, GAMEPLAY_SCREEN_STATE);
        
        guiDecor.put(BACKGROUND_TYPE, s);
        guiDecor.get(BACKGROUND_TYPE).setState(MENU_SCREEN_STATE);
        
        
   
        img = loadImage(imgPath + props.getProperty(pathXPropertyType.IMAGE_BACKGROUND_GAME2));
        sT.addState(GAME_SCREEN_2_STATE, img);
        s = new Sprite(sT, 0, 0, 0, 0, GAME_SCREEN_2_STATE);
        guiDecor.put(BACKGROUND2_TYPE, s);
        guiDecor.get(BACKGROUND2_TYPE).setState(GAME_SCREEN_2_STATE);
        
        
        img = loadImage(imgPath + props.getProperty(pathXPropertyType.IMAGE_BACKGROUND_GAMEPLAY2));
        sT.addState(GAMEPLAY_SCREEN_2_STATE, img);
        s = new Sprite(sT, 0, 0, 0, 0, GAMEPLAY_SCREEN_2_STATE);
        guiDecor.put(BACKGROUND3_TYPE, s);
        guiDecor.get(BACKGROUND3_TYPE).setState(GAMEPLAY_SCREEN_2_STATE);
        
        //sT = new SpriteType(BACKGROUND3_TYPE);
        
        //Viewport viewport = data.getViewport();
        //viewport.setGameWorldSize(1200, 904);
        //viewport.setScreenSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        //viewport.initViewportMargins();
       
        
        float totalWidth = 4 * (LEVEL_BUTTON_WIDTH + LEVEL_BUTTON_MARGIN) - LEVEL_BUTTON_MARGIN;
        
        x = 80;
        
        String playButton = props.getProperty(pathXPropertyType.IMAGE_BUTTON_PLAY);
        sT = new SpriteType(PLAY_BUTTON_TYPE);
	img = loadImage(imgPath + playButton);
        sT.addState(pathXTileState.VISIBLE_STATE.toString(), img);
        String newMouseOverButton = props.getProperty(pathXPropertyType.IMAGE_BUTTON_PLAY_MOUSE_OVER);
        img = loadImage(imgPath + newMouseOverButton);
        sT.addState(pathXTileState.MOUSE_OVER_STATE.toString(), img);
        s = new Sprite(sT, x, MENU_BUTTON_Y, 0, 0, pathXTileState.VISIBLE_STATE.toString());
        guiButtons.put(PLAY_BUTTON_TYPE, s);
        x += LEVEL_BUTTON_WIDTH + LEVEL_BUTTON_MARGIN;
        
        String resetButton = props.getProperty(pathXPropertyType.IMAGE_BUTTON_RESET);
        sT = new SpriteType(RESET_BUTTON_TYPE);
	img = loadImage(imgPath + resetButton);
        sT.addState(pathXTileState.VISIBLE_STATE.toString(), img);
        newMouseOverButton = props.getProperty(pathXPropertyType.IMAGE_BUTTON_RESET_MOUSE_OVER);
        img = loadImage(imgPath + newMouseOverButton);
        sT.addState(pathXTileState.MOUSE_OVER_STATE.toString(), img);
        s = new Sprite(sT, x, MENU_BUTTON_Y, 0, 0, pathXTileState.VISIBLE_STATE.toString());
        guiButtons.put(RESET_BUTTON_TYPE, s);
        x += LEVEL_BUTTON_WIDTH + LEVEL_BUTTON_MARGIN;
        
        String settingButton = props.getProperty(pathXPropertyType.IMAGE_BUTTON_SETTING);
        sT = new SpriteType(SETTING_BUTTON_TYPE);
	img = loadImage(imgPath + settingButton);
        sT.addState(pathXTileState.VISIBLE_STATE.toString(), img);
        newMouseOverButton = props.getProperty(pathXPropertyType.IMAGE_BUTTON_SETTING_MOUSE_OVER);
        img = loadImage(imgPath + newMouseOverButton);
        sT.addState(pathXTileState.MOUSE_OVER_STATE.toString(), img);
        s = new Sprite(sT, x - 30, MENU_BUTTON_Y, 0, 0, pathXTileState.VISIBLE_STATE.toString());
        guiButtons.put(SETTING_BUTTON_TYPE, s);
        x += LEVEL_BUTTON_WIDTH + LEVEL_BUTTON_MARGIN;
        
        String helpButton = props.getProperty(pathXPropertyType.IMAGE_BUTTON_HELP);
        sT = new SpriteType(HELP_BUTTON_TYPE);
	img = loadImage(imgPath + helpButton);
        sT.addState(pathXTileState.VISIBLE_STATE.toString(), img);
        newMouseOverButton = props.getProperty(pathXPropertyType.IMAGE_BUTTON_HELP_MOUSE_OVER);
        img = loadImage(imgPath + newMouseOverButton);
        sT.addState(pathXTileState.MOUSE_OVER_STATE.toString(), img);
        s = new Sprite(sT, x + 30, MENU_BUTTON_Y, 0, 0, pathXTileState.VISIBLE_STATE.toString());
        guiButtons.put(HELP_BUTTON_TYPE, s);
        x += LEVEL_BUTTON_WIDTH + LEVEL_BUTTON_MARGIN;
        // ADD THE CONTROLS ALONG THE NORTH OF THE GAME SCREEN
                
        String homeButton = props.getProperty(pathXPropertyType.IMAGE_BUTTON_HOME);
        sT = new SpriteType(HOME_BUTTON_TYPE);
	img = loadImage(imgPath + homeButton);
        sT.addState(pathXTileState.VISIBLE_STATE.toString(), img);
        newMouseOverButton = props.getProperty(pathXPropertyType.IMAGE_BUTTON_HOME_MOUSE_OVER);
        img = loadImage(imgPath + newMouseOverButton);
        sT.addState(pathXTileState.MOUSE_OVER_STATE.toString(), img);
        s = new Sprite(sT, 820, MENU_BUTTON_Y, 0, 0, pathXTileState.INVISIBLE_STATE.toString());
        guiButtons.put(HOME_BUTTON_TYPE, s);
        x += LEVEL_BUTTON_WIDTH + LEVEL_BUTTON_MARGIN;
        
       String XButton = props.getProperty(pathXPropertyType.IMAGE_BUTTON_X);
        sT = new SpriteType(X_BUTTON_TYPE);
	img = loadImage(imgPath + XButton);
        sT.addState(pathXTileState.VISIBLE_STATE.toString(), img);
        newMouseOverButton = props.getProperty(pathXPropertyType.IMAGE_BUTTON_X_MOUSE_OVER);
        img = loadImage(imgPath + newMouseOverButton);
        sT.addState(pathXTileState.MOUSE_OVER_STATE.toString(), img);
        s = new Sprite(sT, 880, MENU_BUTTON_Y, 0, 0, pathXTileState.INVISIBLE_STATE.toString());
        guiButtons.put(X_BUTTON_TYPE, s);
        x += LEVEL_BUTTON_WIDTH + LEVEL_BUTTON_MARGIN;

       String UpButton = props.getProperty(pathXPropertyType.IMAGE_BUTTON_UP);
        sT = new SpriteType(UP_BUTTON_TYPE);
	img = loadImage(imgPath + UpButton);
        sT.addState(pathXTileState.VISIBLE_STATE.toString(), img);
        newMouseOverButton = props.getProperty(pathXPropertyType.IMAGE_BUTTON_UP);
        img = loadImage(imgPath + newMouseOverButton);
        sT.addState(pathXTileState.MOUSE_OVER_STATE.toString(), img);
        s = new Sprite(sT, WINDOW_WIDTH/2, 110, 0, 0, pathXTileState.INVISIBLE_STATE.toString());
        guiButtons.put(UP_BUTTON_TYPE, s);
        
        String DownButton = props.getProperty(pathXPropertyType.IMAGE_BUTTON_DOWN);
        sT = new SpriteType(DOWN_BUTTON_TYPE);
	img = loadImage(imgPath +DownButton);
        sT.addState(pathXTileState.VISIBLE_STATE.toString(), img);
        newMouseOverButton = props.getProperty(pathXPropertyType.IMAGE_BUTTON_DOWN);
        img = loadImage(imgPath + newMouseOverButton);
        sT.addState(pathXTileState.MOUSE_OVER_STATE.toString(), img);
        s = new Sprite(sT, WINDOW_WIDTH/2, WINDOW_HEIGHT-80, 0, 0, pathXTileState.INVISIBLE_STATE.toString());
        guiButtons.put(DOWN_BUTTON_TYPE, s);
        
        String LeftButton = props.getProperty(pathXPropertyType.IMAGE_BUTTON_LEFT);
        sT = new SpriteType(LEFT_BUTTON_TYPE);
	img = loadImage(imgPath + LeftButton);
        sT.addState(pathXTileState.VISIBLE_STATE.toString(), img);
        newMouseOverButton = props.getProperty(pathXPropertyType.IMAGE_BUTTON_LEFT);
        img = loadImage(imgPath + newMouseOverButton);
        sT.addState(pathXTileState.MOUSE_OVER_STATE.toString(), img);
        s = new Sprite(sT, 15, WINDOW_HEIGHT/2 - 30, 0, 0, pathXTileState.INVISIBLE_STATE.toString());
        guiButtons.put(LEFT_BUTTON_TYPE, s);
        
        String RightButton = props.getProperty(pathXPropertyType.IMAGE_BUTTON_RIGHT);
        sT = new SpriteType(RIGHT_BUTTON_TYPE);
	img = loadImage(imgPath + RightButton);
        sT.addState(pathXTileState.VISIBLE_STATE.toString(), img);
        newMouseOverButton = props.getProperty(pathXPropertyType.IMAGE_BUTTON_RIGHT);
        img = loadImage(imgPath + newMouseOverButton);
        sT.addState(pathXTileState.MOUSE_OVER_STATE.toString(), img);
        s = new Sprite(sT, WINDOW_WIDTH -70, WINDOW_HEIGHT/2 -30, 0, 0, pathXTileState.INVISIBLE_STATE.toString());
        guiButtons.put(RIGHT_BUTTON_TYPE, s);
        
        
       
        
        ArrayList<String> levels = props.getPropertyOptionsList(pathXPropertyType.LEVEL_OPTIONS);
        for (int i = 0; i < levels.size(); i++)
        {
            sT = new SpriteType(LEVEL_BUTTON_TYPE);
            String redLevelButton = props.getProperty(pathXPropertyType.IMAGE_BUTTON_LEVEL_RED);
            img = loadImage(imgPath + redLevelButton);
            sT.addState(pathXTileState.VISIBLE_STATE.toString(), img);
            String yellowLevelButton = props.getProperty(pathXPropertyType.IMAGE_BUTTON_LEVEL_MOUSE_OVER);
            img = loadImage(imgPath  + yellowLevelButton);
            sT.addState(pathXTileState.MOUSE_OVER_STATE.toString(), img);
            String whiteLevelButton = props.getProperty(pathXPropertyType.IMAGE_BUTTON_LEVEL_WHITE);
            img = loadImage(imgPath + whiteLevelButton);
            sT.addState(pathXTileState.UNAVAILABLE_STATE.toString(), img);
            String greenLevelButton = props.getProperty(pathXPropertyType.IMAGE_BUTTON_LEVEL_GREEN);
            img = loadImage(imgPath + greenLevelButton);
            sT.addState(pathXTileState.COMPLETED_STATE.toString(), img);
            s = new Sprite(sT, 500, 500, 0, 0, pathXTileState.INVISIBLE_STATE.toString());
            guiButtons.put(levels.get(i), s);
            
        }
        
        String DialogXButton = props.getProperty(pathXPropertyType.IMAGE_BUTTON_DIALOG_X);
        sT = new SpriteType(DIALOG_X_BUTTON_TYPE);
	img = loadImage(imgPath + DialogXButton);
        sT.addState(pathXTileState.VISIBLE_STATE.toString(), img);
        newMouseOverButton = props.getProperty(pathXPropertyType.IMAGE_BUTTON_DIALOG_X);
        img = loadImage(imgPath + newMouseOverButton);
        sT.addState(pathXTileState.MOUSE_OVER_STATE.toString(), img);
        s = new Sprite(sT, 675, 90, 0, 0, pathXTileState.INVISIBLE_STATE.toString());
        guiButtons.put(DIALOG_X_BUTTON_TYPE, s);
        
        String playAgainButton = props.getProperty(pathXPropertyType.IMAGE_BUTTON_PLAY_AGAIN);
        sT = new SpriteType(DIALOG_BUTTON_TYPE);
	img = loadImage(imgPath + playAgainButton);
        sT.addState(pathXTileState.VISIBLE_STATE.toString(), img);
        newMouseOverButton = props.getProperty(pathXPropertyType.IMAGE_BUTTON_PLAY_AGAIN_MOUSE_OVER);
        img = loadImage(imgPath + newMouseOverButton);
        sT.addState(pathXTileState.MOUSE_OVER_STATE.toString(), img);
        s = new Sprite(sT, 340, 350, 0, 0, pathXTileState.INVISIBLE_STATE.toString());
        guiButtons.put(DIALOG_BUTTON_TYPE, s);
        
        String leaveTownButton = props.getProperty(pathXPropertyType.IMAGE_BUTTON_LEAVE_TOWN);
        sT = new SpriteType(DIALOG2_BUTTON_TYPE);
	img = loadImage(imgPath + leaveTownButton);
        sT.addState(pathXTileState.VISIBLE_STATE.toString(), img);
        newMouseOverButton = props.getProperty(pathXPropertyType.IMAGE_BUTTON_LEAVE_TOWN_MOUSE_OVER);
        img = loadImage(imgPath + newMouseOverButton);
        sT.addState(pathXTileState.MOUSE_OVER_STATE.toString(), img);
        s = new Sprite(sT, 550, 350, 0, 0, pathXTileState.INVISIBLE_STATE.toString());
        guiButtons.put(DIALOG2_BUTTON_TYPE, s);
        
        String MuteButton = props.getProperty(pathXPropertyType.IMAGE_BUTTON_MUTE_OFF);
        sT = new SpriteType(MUTE_BUTTON_TYPE);
	img = loadImage(imgPath + MuteButton);
        sT.addState(pathXTileState.VISIBLE_STATE.toString(), img);
        newMouseOverButton = props.getProperty(pathXPropertyType.IMAGE_BUTTON_MUTE_ON);
        img = loadImage(imgPath + newMouseOverButton);
        sT.addState(pathXTileState.SELECTED_STATE.toString(), img);
        s = new Sprite(sT, 340, 240, 0, 0, pathXTileState.INVISIBLE_STATE.toString());
        guiButtons.put(MUTE_BUTTON_TYPE, s);

        // NOW ADD THE DIALOGS
        pathXViewport viewport = ((pathXDataModel)data).getPathXViewport();
        
        
        String levelDialog = props.getProperty(pathXPropertyType.IMAGE_DIALOG_LEVEL);
        sT = new SpriteType(LEVEL_DIALOG_TYPE);
        img = loadImageWithColorKey(imgPath + levelDialog,new Color(255, 174, 201));
        sT.addState(pathXTileState.VISIBLE_STATE.toString(), img);
        levelDialog = props.getProperty(pathXPropertyType.IMAGE_DIALOG_WIN);
        img = loadImageWithColorKey(imgPath + levelDialog,new Color(255, 174, 201));
        sT.addState(pathXTileState.WIN_STATE.toString(), img);
        levelDialog = props.getProperty(pathXPropertyType.IMAGE_DIALOG_LOST);
        img = loadImageWithColorKey(imgPath + levelDialog,new Color(255, 174, 201));
        sT.addState(pathXTileState.LOSE_STATE.toString(), img);
        x = 330;
        y = 65;
        s = new Sprite(sT, x, y, 0, 0, pathXTileState.INVISIBLE_STATE.toString());
        guiDialogs.put(LEVEL_DIALOG_TYPE, s);
        
        //ADD TILES
        String player = props.getProperty(pathXPropertyType.IMAGE_PLAYER);
        sT = new SpriteType(PLAYER_TYPE);
        img = loadImage(imgPath + player);
        sT.addState(pathXTileState.VISIBLE_STATE.toString(), img);
        pathXTile playertile = new pathXTile(sT, 0, 0, 0, 0, pathXTileState.INVISIBLE_STATE.toString());
        ((pathXDataModel)data).setPlayer(playertile);
        
       
    }	
    
    public void initEnemies(){
        int numCops = ((pathXDataModel)data).getLevel().getNumPolice();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
            SpriteType sT;
            BufferedImage img;
        
        ((pathXDataModel)data).getCops().clear();
        for(int c = 0; c < numCops; c++){
            
            
            String cops = props.getProperty(pathXPropertyType.IMAGE_COP);
            sT = new SpriteType(COP_TYPE);
            img = loadImage("./img/" + cops);
            sT.addState(pathXTileState.VISIBLE_STATE.toString(), img);
            
            pathXTile coptile = new pathXTile(sT, ((pathXDataModel)data).getLevel().getIntersections().get(c+2).x + WEST_PANEL_WIDTH - 33, 
                    ((pathXDataModel)data).getLevel().getIntersections().get(c+2).y - 33, 0, 0, pathXTileState.INVISIBLE_STATE.toString());
            
            ((pathXDataModel)data).getCops().add(coptile);
           
        }
        int numZombies = ((pathXDataModel)data).getLevel().getNumZombies();
        ((pathXDataModel)data).getZombies().clear();
        for(int c = 0; c < numZombies; c++){
            
            
            String zombies = props.getProperty(pathXPropertyType.IMAGE_ZOMBIE);
            sT = new SpriteType(ZOMBIE_TYPE);
            img = loadImage("./img/" + zombies);
            sT.addState(pathXTileState.VISIBLE_STATE.toString(), img);
            
            pathXTile zombietile = new pathXTile(sT, ((pathXDataModel)data).getLevel().getIntersections().get(c+numCops+ 2).x + WEST_PANEL_WIDTH - 33, 
                    ((pathXDataModel)data).getLevel().getIntersections().get(c+ numCops+ 2).y - 33, 0, 0, pathXTileState.INVISIBLE_STATE.toString());
            
            ((pathXDataModel)data).getZombies().add(zombietile);
           
        }
        
        
    }
    
    /**
     * Initializes the game event handlers for things like
     * game gui buttons.
     */
    @Override
    public void initGUIHandlers()
    {
        eventHandler = new pathXEventHandler(this);
                
        // WE'LL HAVE A CUSTOM RESPONSE FOR WHEN THE USER CLOSES THE WINDOW
        //window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
       // window.addWindowListener(new WindowAdapter(){
            //public void windowClosing(WindowEvent we) 
           // { eventHandler.respondToExitRequest(); }
       // });

        // SEND ALL LEVEL SELECTION HANDLING OFF TO THE EVENT HANDLER
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        ArrayList<String> levels = props.getPropertyOptionsList(pathXPropertyType.LEVEL_OPTIONS);
        for (String levelFile : levels)
        {
            Sprite levelButton = guiButtons.get(levelFile);
            levelButton.setActionCommand(PATH_DATA + levelFile);
            levelButton.setActionListener(new ActionListener(){
                Sprite s;
                public ActionListener init(Sprite initS) 
                {   s = initS; 
                    return this;    }
                public void actionPerformed(ActionEvent ae)
                {   eventHandler.respondToLevelSelectRequest(s.getActionCommand());    }
            }.init(levelButton));
        }   
        

        // NEW GAME EVENT HANDLER
        guiButtons.get(PLAY_BUTTON_TYPE).setActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae)
            {   eventHandler.respondToPlayRequest();     }
        });
        
        guiButtons.get(SETTING_BUTTON_TYPE).setActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae)
            {   eventHandler.respondToSettingRequest();     }
        });
        
        guiButtons.get(HOME_BUTTON_TYPE).setActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae)
            {   eventHandler.respondToHomeRequest();     }
        });
        
         guiButtons.get(X_BUTTON_TYPE).setActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae)
            {   eventHandler.respondToExitRequest();     }
        });
         
         guiButtons.get(HELP_BUTTON_TYPE).setActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae)
            {   eventHandler.respondToHelpRequest();     }
        });
         
         guiButtons.get(HELP_BUTTON_TYPE).setActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae)
            {   eventHandler.respondToHelpRequest();     }
        });
         
         guiButtons.get(UP_BUTTON_TYPE).setActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae)
            {   eventHandler.respondToScrollRequest(UP_BUTTON_TYPE);     }
        });
         
         guiButtons.get(DOWN_BUTTON_TYPE).setActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae)
            {   eventHandler.respondToScrollRequest(DOWN_BUTTON_TYPE);     }
        });
         
         guiButtons.get(LEFT_BUTTON_TYPE).setActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae)
            {   eventHandler.respondToScrollRequest(LEFT_BUTTON_TYPE);     }
        });
         guiButtons.get(RIGHT_BUTTON_TYPE).setActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae)
            {   eventHandler.respondToScrollRequest(RIGHT_BUTTON_TYPE);     }
        });
         
         guiButtons.get(DIALOG_BUTTON_TYPE).setActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae)
            {   eventHandler.respondToPlayAgainRequest();     }
        });
         
         guiButtons.get(DIALOG2_BUTTON_TYPE).setActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae)
            {   eventHandler.respondToExitRequest();     }
        });
         
         //guiButtons.get(LEVEL_BUTTON_TYPE).setActionListener(new ActionListener(){
           // public void actionPerformed(ActionEvent ae)
            //{   eventHandler.respondToLevelSelectRequest();     }
      //  });
         
         guiButtons.get(DIALOG_X_BUTTON_TYPE).setActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae)
            {   eventHandler.respondToCloseDialogRequest();     }
        });
         
         guiButtons.get(MUTE_BUTTON_TYPE).setActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae)
            {   eventHandler.respondToMuteRequest();     }
        });

        // STATS BUTTON EVENT HANDLER
       // guiButtons.get(STATS_BUTTON_TYPE).setActionListener(new ActionListener(){
            //public void actionPerformed(ActionEvent ae)
            //{   eventHandler.respondToDisplayStatsRequest();    }
      //  });
        
        // KEY LISTENER - LET'S US PROVIDE CUSTOM RESPONSES
        this.setKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent ke)
            {   
                eventHandler.respondToKeyPress(ke.getKeyCode());    
            }
        });
    }
    
    /**
     * Invoked when a new game is started, it resets all relevant
     * game data and gui control states. 
     */
    @Override
    public void reset()
    {
        data.reset(this);
    }
    
    /**
     * Updates the state of all gui controls according to the 
     * current game conditions.
     */
    @Override
    public void updateGUI()
    {
        // GO THROUGH THE VISIBLE BUTTONS TO TRIGGER MOUSE OVERS
        Iterator<Sprite> buttonsIt = guiButtons.values().iterator();
        while (buttonsIt.hasNext())
        {
            Sprite button = buttonsIt.next();
            
            // ARE WE ENTERING A BUTTON?
            if (button.getState().equals(pathXTileState.VISIBLE_STATE.toString()))
            {
                if (button.containsPoint(data.getLastMouseX(), data.getLastMouseY()))
                {
                    button.setState(pathXTileState.MOUSE_OVER_STATE.toString());
                }
            }
            // ARE WE EXITING A BUTTON?
            else if (button.getState().equals(pathXTileState.MOUSE_OVER_STATE.toString()))
            {
                 if (!button.containsPoint(data.getLastMouseX(), data.getLastMouseY()))
                {
                    button.setState(pathXTileState.VISIBLE_STATE.toString());
                }
            }
        }
    }    
}
