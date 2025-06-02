package hotel.management.system;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HotelManagementSystem extends JFrame implements ActionListener {
    
    private JLabel timeLabel, dateLabel;
    private Timer timer;
    private Color primaryColor = new Color(25, 118, 210);
    private Image backgroundImage;
    
    public HotelManagementSystem() {
        // Basic frame setup
        setTitle("Hotel Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 50, 1000, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        // Load background image once
        try {
            InputStream is = getClass().getResourceAsStream("/icons/h2.png");
            if (is == null) throw new Exception("Resource not found");
            BufferedImage img = ImageIO.read(is);
            backgroundImage = img.getScaledInstance(1000, 600, Image.SCALE_SMOOTH);
        } catch (Exception e) {
            System.err.println("Error loading background image: " + e.getMessage());
            backgroundImage = null;
        }
        
        // Create main panel with background image
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, this);
                    g.setColor(new Color(0, 0, 0, 150));
                    g.fillRect(0, 0, getWidth(), getHeight());
                } else {
                    g.setColor(new Color(45, 45, 45));
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        mainPanel.setLayout(null);
        add(mainPanel);
        
        // Add hotel name with professional typography
        JLabel hotelNameLabel = new JLabel("HOTEL");
        hotelNameLabel.setFont(new Font("Montserrat", Font.BOLD, 48));
        hotelNameLabel.setForeground(Color.WHITE);
        hotelNameLabel.setBounds(0, 100, 1000, 60);
        hotelNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(hotelNameLabel);
        
        JLabel taglineLabel = new JLabel("MANAGEMENT SYSTEM");
        taglineLabel.setFont(new Font("Montserrat", Font.PLAIN, 20));
        taglineLabel.setForeground(new Color(220, 220, 220));
        taglineLabel.setBounds(0, 160, 1000, 30);
        taglineLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(taglineLabel);
        
        // Add date and time panel with glass effect
        JPanel dateTimePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(255, 255, 255, 40));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2d.setColor(new Color(255, 255, 255, 60));
                g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
            }
        };
        dateTimePanel.setOpaque(false);
        dateTimePanel.setLayout(new GridLayout(2, 1, 0, 5));
        dateTimePanel.setBounds(350, 230, 300, 80);
        mainPanel.add(dateTimePanel);
        
        // Add time label - using 12-hour format
        timeLabel = new JLabel();
        timeLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        dateTimePanel.add(timeLabel);
        
        // Add date label
        dateLabel = new JLabel();
        dateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        dateLabel.setForeground(Color.WHITE);
        dateLabel.setHorizontalAlignment(SwingConstants.CENTER);
        dateTimePanel.add(dateLabel);
        
        // Update time and date
        updateDateTime();
        
        // Create timer to update time every second
        timer = new Timer(1000, e -> updateDateTime());
        timer.start();
        
        // Create a custom button that properly handles hover effects
        JButton getStartedButton = new JButton("GET STARTED") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (getModel().isRollover()) {
                    g2d.setColor(new Color(21, 101, 192)); // Hover color
                } else {
                    g2d.setColor(primaryColor); // Normal color
                }
                
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2d.setColor(Color.WHITE);
                g2d.setFont(new Font("Segoe UI", Font.BOLD, 16));
                FontMetrics fm = g2d.getFontMetrics();
                int textWidth = fm.stringWidth("GET STARTED");
                int textHeight = fm.getHeight();
                g2d.drawString("GET STARTED", 
                    (getWidth() - textWidth) / 2, 
                    (getHeight() - textHeight) / 2 + fm.getAscent());
            }
        };
        getStartedButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        getStartedButton.setBounds(400, 350, 200, 50);
        getStartedButton.addActionListener(this);
        mainPanel.add(getStartedButton);
        
        setVisible(true);
    }
    
    private void updateDateTime() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss a");
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM d, yyyy");
        Date now = new Date();
        timeLabel.setText(timeFormat.format(now));
        dateLabel.setText(dateFormat.format(now));
    }
    
    public void actionPerformed(ActionEvent ae) {
        timer.stop();
        new Login().setVisible(true);
        this.setVisible(false);
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            System.setProperty("awt.useSystemAAFontSettings", "on");
            System.setProperty("swing.aatext", "true");
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> new HotelManagementSystem());
    }
}
