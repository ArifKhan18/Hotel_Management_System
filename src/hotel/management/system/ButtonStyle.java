package hotel.management.system;

import javax.swing.*;
import java.awt.*;

/**
 * Utility class to apply consistent button styling throughout the application
 */
public class ButtonStyle {
    
    // Standard button color
    public static final Color PRIMARY_COLOR = new Color(0, 77, 153);
    
    /**
     * Apply standard styling to a button
     * @param button The button to style
     */
    public static void applyStyle(JButton button) {
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Tahoma", Font.BOLD, 14));
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFocusPainted(false);
        
        // Make button keep its background color even with Look and Feel changes
        button.setContentAreaFilled(true);
        button.setOpaque(true);
    }
    
    /**
     * Set global UI properties to ensure consistent button styling
     */
    public static void setUIProperties() {
        try {
            // Override default button properties
            UIManager.put("Button.background", PRIMARY_COLOR);
            UIManager.put("Button.foreground", Color.WHITE);
            UIManager.put("Button.font", new Font("Tahoma", Font.BOLD, 14));
            UIManager.put("Button.border", BorderFactory.createRaisedBevelBorder());
            UIManager.put("Button.focus", PRIMARY_COLOR);
            UIManager.put("Button.select", new Color(0, 51, 102)); // Darker shade for selection
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
