package hotel.management.system;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;

public class Dashboard extends JFrame implements ActionListener {
    JMenuBar mb;
    JMenu m1, m2;
    JMenuItem i1, i2, i3;  // Keeping original menu items

    public Dashboard() {
        try {
            // Set properties before creating components
            setTitle("Hotel Management System - Dashboard");
            setBounds(100, 50, 1550, 1000);
            setLayout(null);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            // Create a base panel with a solid color as fallback
            JPanel basePanel = new JPanel();
            basePanel.setLayout(null);
            basePanel.setBackground(new Color(32, 32, 32));
            basePanel.setBounds(0, 0, 1550, 1000);
            add(basePanel);
            
            // Try multiple methods to load the image
            JLabel backgroundLabel = null;
            boolean imageLoaded = false;
            
            // Method 1: Try ClassLoader
            try {
                ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("icons/h1.png"));
                if (icon.getIconWidth() > 0) {
                    Image img = icon.getImage().getScaledInstance(1550, 1000, Image.SCALE_DEFAULT);
                    backgroundLabel = new JLabel(new ImageIcon(img));
                    backgroundLabel.setBounds(0, 0, 1550, 1000);
                    imageLoaded = true;
                    System.out.println("Image loaded via ClassLoader");
                }
            } catch (Exception e) {
                System.out.println("Failed to load image via ClassLoader: " + e.getMessage());
            }
            
            // Method 2: Try direct file path if Method 1 failed
            if (!imageLoaded) {
                try {
                    File file = new File("src/icons/third.jpg");
                    if (file.exists()) {
                        ImageIcon icon = new ImageIcon(file.getAbsolutePath());
                        if (icon.getIconWidth() > 0) {
                            Image img = icon.getImage().getScaledInstance(1550, 1000, Image.SCALE_DEFAULT);
                            backgroundLabel = new JLabel(new ImageIcon(img));
                            backgroundLabel.setBounds(0, 0, 1550, 1000);
                            imageLoaded = true;
                            System.out.println("Image loaded via direct file path");
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Failed to load image via direct file path: " + e.getMessage());
                }
            }
            
            // Method 3: Try alternate paths if Methods 1 and 2 failed
            if (!imageLoaded) {
                String[] possiblePaths = {
                    "icons/third.jpg",
                    "./icons/third.jpg",
                    "../icons/third.jpg",
                    "resources/icons/third.jpg",
                    "src/resources/icons/third.jpg",
                    "src/hotel/management/system/icons/third.jpg"
                };
                
                for (String path : possiblePaths) {
                    try {
                        File file = new File(path);
                        if (file.exists()) {
                            ImageIcon icon = new ImageIcon(file.getAbsolutePath());
                            if (icon.getIconWidth() > 0) {
                                Image img = icon.getImage().getScaledInstance(1550, 1000, Image.SCALE_DEFAULT);
                                backgroundLabel = new JLabel(new ImageIcon(img));
                                backgroundLabel.setBounds(0, 0, 1550, 1000);
                                imageLoaded = true;
                                System.out.println("Image loaded via path: " + path);
                                break;
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Failed to load image via path " + path + ": " + e.getMessage());
                    }
                }
            }
            
            // If all methods failed, create a plain label
            if (!imageLoaded) {
                System.out.println("All image loading methods failed, using plain label");
                backgroundLabel = new JLabel();
                backgroundLabel.setOpaque(true);
                backgroundLabel.setBackground(new Color(32, 32, 32));
                backgroundLabel.setBounds(0, 0, 1550, 1000);
            }
            
            basePanel.add(backgroundLabel);
            
            // Create welcome text with improved styling
            JLabel welcomeLabel = new JLabel("Hotel of Programmers");
            welcomeLabel.setBounds(500, 100, 1000, 100);
            welcomeLabel.setFont(new Font("Tahoma", Font.BOLD, 48));
            welcomeLabel.setForeground(Color.WHITE);
            backgroundLabel.add(welcomeLabel);
            
            // Create menu bar with improved styling
            mb = new JMenuBar();
            mb.setBounds(0, 0, 1550, 40);
            mb.setBackground(new Color(0, 77, 153)); // Darker blue for better contrast
            mb.setBorder(BorderFactory.createEmptyBorder());
            backgroundLabel.add(mb);

            // Create menus with improved styling
            m1 = new JMenu("HOTEL MANAGEMENT");
            m1.setForeground(Color.WHITE);
            m1.setFont(new Font("Tahoma", Font.BOLD, 16));
            mb.add(m1);

            m2 = new JMenu("ADMIN");
            m2.setForeground(Color.WHITE);
            m2.setFont(new Font("Tahoma", Font.BOLD, 16));
            mb.add(m2);

            // Create menu items with improved styling
            Font menuItemFont = new Font("Tahoma", Font.PLAIN, 14);

            i1 = new JMenuItem("RECEPTION");
            i1.setFont(menuItemFont);
            i1.setBackground(new Color(240, 240, 240));
            i1.setForeground(new Color(0, 51, 102));
            i1.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            i1.addActionListener(this);
            m1.add(i1);

            i2 = new JMenuItem("ADD EMPLOYEE");
            i2.setFont(menuItemFont);
            i2.setBackground(new Color(240, 240, 240));
            i2.setForeground(new Color(0, 51, 102));
            i2.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            i2.addActionListener(this);
            m2.add(i2);

            i3 = new JMenuItem("ADD ROOMS");
            i3.setFont(menuItemFont);
            i3.setBackground(new Color(240, 240, 240));
            i3.setForeground(new Color(0, 51, 102));
            i3.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            i3.addActionListener(this);
            m2.add(i3);
            
            // Add separators for visual appeal
            m1.addSeparator();
            m2.addSeparator();
            
            // Make frame visible
            setVisible(true);
            
            System.out.println("Dashboard initialized successfully");
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error initializing Dashboard: " + e.getMessage());
        }
    }
    
    public void actionPerformed(ActionEvent ae) {
        try {
            String cmd = ae.getActionCommand();
            System.out.println("Menu item clicked: " + cmd);
            
            if (cmd.equals("RECEPTION")) {
                new Reception().setVisible(true);
            } else if (cmd.equals("ADD EMPLOYEE")) {
                new AddEmployee().setVisible(true);
            } else if (cmd.equals("ADD ROOMS")) {
                new AddRoom().setVisible(true);
            }
            // Keeping original functionality
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error processing action: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        try {
            // Set system look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            
            // Print system properties for debugging
            System.out.println("Java version: " + System.getProperty("java.version"));
            System.out.println("Java home: " + System.getProperty("java.home"));
            System.out.println("Current directory: " + System.getProperty("user.dir"));
            
            // Launch on the Event Dispatch Thread
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    try {
                        new Dashboard();
                    } catch (Exception e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error starting Dashboard: " + e.getMessage());
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error setting up application: " + e.getMessage());
        }
    }
}
