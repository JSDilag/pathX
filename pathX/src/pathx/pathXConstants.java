/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pathx;

import java.awt.Color;
import java.awt.Font;

/**
 *
 * @author John Dilag
 */
public class pathXConstants {
    public static String PROPERTY_TYPES_LIST = "property_types.txt";
    public static String PROPERTIES_FILE_NAME = "properties.xml";
    public static String PROPERTIES_SCHEMA_FILE_NAME = "properties_schema.xsd";    
     public static String PATH_DATA = "./data/";
     public static final String  LEVEL_SCHEMA = "PathXLevelSchema.xsd";
    
    public static final String BACKGROUND_TYPE = "BACKGROUND_TYPE";
    public static final String BACKGROUND2_TYPE = "BACKGROUND2_TYPE";
    public static final String BACKGROUND3_TYPE = "BACKGROUND3_TYPE";
    public static final String GAME_BACKGROUND_TYPE = "GAME_BACKGROUND_TYPE";
    
    public static final String LEVEL_SELECT_BUTTON_TYPE = "LEVEL_SELECT_BUTTON_TYPE";

    public static final String PLAY_BUTTON_TYPE = "PLAY_BUTTON_TYPE";
    public static final String HOME_BUTTON_TYPE = "HOME_BUTTON_TYPE";
    public static final String HELP_BUTTON_TYPE = "HELP_BUTTON_TYPE";
    public static final String SETTING_BUTTON_TYPE = "SETTING_BUTTON_TYPE";
    public static final String RESET_BUTTON_TYPE = "RESET_BUTTON_TYPE";
    public static final String X_BUTTON_TYPE = "X_BUTTON_TYPE";
    public static final String LEVEL_DIALOG_TYPE = "LEVEL_DIALOG_TYPE";
    public static final String DIALOG_X_BUTTON_TYPE = "DIALOG_X_BUTTON_TYPE";
    public static final String PLAYER_TYPE = "PLAYER_TYPE";
    public static final String COP_TYPE = "COP_TYPE";
    public static final String ZOMBIE_TYPE = "ZOMBIE_TYPE";
    public static final String BANDIT_TYPE = "BANDIT_TYPE";
   
    public static final String UP_BUTTON_TYPE = "UP_BUTTON_TYPE";
    public static final String DIALOG_BUTTON_TYPE = "DIALOG_BUTTON_TYPE";
    public static final String DIALOG2_BUTTON_TYPE = "DIALOG2_BUTTON_TYPE";
    public static final String DOWN_BUTTON_TYPE = "DOWN_BUTTON_TYPE";
    public static final String LEFT_BUTTON_TYPE = "LEFT_BUTTON_TYPE";
    public static final String RIGHT_BUTTON_TYPE = "RIGHT_BUTTON_TYPE";
    public static final String LEVEL_BUTTON_TYPE = "LEVEL_BUTTON_TYPE";
   
    public static final String MUTE_BUTTON_TYPE = "MUTE_BUTTON_TYPE";
    public static final String MENU_SCREEN_STATE = "MENU_SCREEN_STATE";
    public static final String GAME_SCREEN_STATE = "GAME_SCREEN_STATE";    
    public static final String GAMEPLAY_SCREEN_STATE = "GAMEPLAY_SCREEN_STATE";
    public static final String SETTING_SCREEN_STATE = "SETTING_SCREEN_STATE";
    public static final String HELP_SCREEN_STATE = "HELP_SCREEN_STATE";
    public static final String GAME_SCREEN_2_STATE = "GAME_SCREEN_2_STATE";
    public static final String GAMEPLAY_SCREEN_2_STATE = "GAMEPLAY_SCREEN_2_STATE";
    
    public static final int FPS = 30;

    
    public static final int WINDOW_WIDTH = 960;
    public static final int WINDOW_HEIGHT = 540;
    public static final int VIEWPORT_MARGIN_LEFT = 20;
    public static final int VIEWPORT_MARGIN_RIGHT = 20;
    public static final int VIEWPORT_MARGIN_TOP = 20;
    public static final int VIEWPORT_MARGIN_BOTTOM = 20;
    public static final int LEVEL_BUTTON_WIDTH = 200;
    public static final int LEVEL_BUTTON_MARGIN = 5;
    public static final int MENU_BUTTON_Y = 445;
    public static final int VIEWPORT_INC = 5;
    public static final int LEVEL_DIMENSION_WIDTH = 1100;
    public static final int LEVEL_DIMENSION_HEIGHT = 700;
    public static final int WEST_PANEL_WIDTH = 217;
    public static final int PLAYER_WIDTH = 65;
    public static final int PLAYER_HEIGHT = 66;
    
     public static final int INTERSECTION_RADIUS = 15;
    public static final int INT_STROKE = 3;
    public static final int ONE_WAY_TRIANGLE_HEIGHT = 40;
    public static final int ONE_WAY_TRIANGLE_WIDTH = 60;
        
    
     public static final String LEVEL_NODE = "level";
    public static final String INTERSECTIONS_NODE = "intersections";
    public static final String INTERSECTION_NODE = "intersection";
    public static final String ROADS_NODE = "roads";
    public static final String ROAD_NODE = "road";
    public static final String START_INTERSECTION_NODE = "start_intersection";
    public static final String DESTINATION_INTERSECTION_NODE = "destination_intersection";
    public static final String MONEY_NODE = "money";
    public static final String POLICE_NODE = "police";
    public static final String BANDITS_NODE = "bandits";
    public static final String ZOMBIES_NODE = "zombies";

    // AND THE ATTRIBUTES FOR THOSE NODES
    public static final String NAME_ATT = "name";
    public static final String IMAGE_ATT = "image";
    public static final String ID_ATT = "id";
    public static final String X_ATT = "x";
    public static final String Y_ATT = "y";
    public static final String OPEN_ATT = "open";
    public static final String INT_ID1_ATT = "int_id1";
    public static final String INT_ID2_ATT = "int_id2";
    public static final String SPEED_LIMIT_ATT = "speed_limit";
    public static final String ONE_WAY_ATT = "one_way";
    public static final String AMOUNT_ATT = "amount";
    public static final String NUM_ATT = "num";
    
    public static final int MAX_TILE_VELOCITY = 20;
    public static int PLAYER_SPEED = 10;
   
    
   
    public static final int NORTH_PANEL_HEIGHT = 130;
    public static final int CONTROLS_MARGIN = 0;
   
    
    public static final Color   INT_OUTLINE_COLOR   = Color.BLACK;
    public static final Color   HIGHLIGHTED_COLOR = Color.YELLOW;
    public static final Color   OPEN_INT_COLOR      = Color.GREEN;
    public static final Color   CLOSED_INT_COLOR    = Color.RED;
  

    
    
    public static final long MILLIS_IN_A_SECOND = 1000;
    public static final long MILLIS_IN_A_MINUTE = 1000 * 60;
    public static final long MILLIS_IN_AN_HOUR  = 1000 * 60 * 60;
    
    public static final Font FONT_TEXT_LEVEL_NAME = new Font(Font.SANS_SERIF, Font.BOLD, 48);
    public static final Font FONT_TEXT_LEVEL_INFO = new Font(Font.SANS_SERIF, Font.PLAIN, 28);

    
  

    
   /* public static final Color COLOR_KEY = new Color(255, 174, 201);
    public static final Color COLOR_DEBUG_TEXT = Color.BLACK;
    public static final Color COLOR_TEXT_DISPLAY = new Color (10, 160, 10);
    public static final Color COLOR_STATS = new Color(0, 60, 0);
    public static final Color COLOR_ALGORITHM_HEADER = Color.WHITE;

    
    public static final Font FONT_TEXT_DISPLAY = new Font(Font.SANS_SERIF, Font.BOLD, 48);
    public static final Font FONT_DEBUG_TEXT = new Font(Font.MONOSPACED, Font.BOLD, 14);
    public static final Font FONT_STATS = new Font(Font.MONOSPACED, Font.BOLD, 20);
    */
}
