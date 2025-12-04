import java.awt.Color;
import java.awt.Font;

public class PetAdoptionTheme {

    // --- 1. COLOR PALETTE ---
    
    // Primary/Accent Color (Soft Blue for Pet Adoption)
    public static final Color PRIMARY_BLUE = new Color(74, 134, 232); 

    // Secondary Colors (Used for background/text contrast)
    public static final Color BACKGROUND_WHITE = new Color(255, 255, 255); 
    public static final Color DARK_NAV_GRAY = new Color(50, 50, 50);
    public static final Color TEXT_HINT_GRAY = new Color(150, 150, 150);
    public static final Color LIGHT_BACKGROUND_GRAY = new Color(240, 240, 240);
    
    // Text Color
    public static final Color TEXT_WHITE = Color.WHITE;
    public static final Color TEXT_DARK = DARK_NAV_GRAY;

    // --- 2. FONT STYLES ---
    
    // Define standard font faces
    public static final String FONT_NAME = "Segoe UI";
    
    // Define specific Font objects for easy reuse
    public static final Font FONT_TITLE = new Font(FONT_NAME, Font.BOLD, 36);
    public static final Font FONT_HEADER = new Font(FONT_NAME, Font.BOLD, 24);
    public static final Font FONT_SUBTITLE = new Font(FONT_NAME, Font.PLAIN, 18);
    public static final Font FONT_BODY = new Font(FONT_NAME, Font.PLAIN, 16);
    public static final Font FONT_BUTTON = new Font(FONT_NAME, Font.BOLD, 14);

}