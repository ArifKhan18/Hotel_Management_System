package hotel.management.system;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener {
    
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, cancelButton;
    private Color primaryColor = new Color(0, 102, 204); // Blue color
    
    public Login() {
        // Basic frame setup
        setTitle("Hotel Management System");
        setBounds(350, 200, 700, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        
        // Left panel with blue background
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(primaryColor);
        leftPanel.setBounds(0, 0, 300, 350);
        leftPanel.setLayout(null);
        add(leftPanel);
        
        // Hotel icon
        JLabel iconLabel = new JLabel("🏨");
        iconLabel.setBounds(100, 80, 100, 100);
        iconLabel.setFont(new Font("SansSerif", Font.PLAIN, 80));
        iconLabel.setForeground(Color.WHITE);
        leftPanel.add(iconLabel);
        
        // Hotel name
        JLabel hotelLabel = new JLabel("HOTEL SYSTEM");
        hotelLabel.setBounds(65, 180, 200, 30);
        hotelLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        hotelLabel.setForeground(Color.WHITE);
        leftPanel.add(hotelLabel);
        
        // Main title
        JLabel titleLabel = new JLabel("Login");
        titleLabel.setBounds(400, 30, 200, 30);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 25));
        titleLabel.setForeground(primaryColor);
        add(titleLabel);
        
        // Username label
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setBounds(350, 80, 100, 30);
        usernameLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        add(usernameLabel);
        
        // Username field
        usernameField = new JTextField();
        usernameField.setBounds(350, 110, 280, 35);
        usernameField.setFont(new Font("SansSerif", Font.PLAIN, 15));
        usernameField.setMargin(new Insets(2, 10, 2, 10));
        usernameField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200), 1, true),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        add(usernameField);
        
        // Password label
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(350, 160, 100, 30);
        passwordLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        add(passwordLabel);
        
        // Password field
        passwordField = new JPasswordField();
        passwordField.setBounds(350, 190, 280, 35);
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 15));
        passwordField.setMargin(new Insets(2, 10, 2, 10));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200), 1, true),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        add(passwordField);
        
        // Custom styled Login button
        loginButton = createCustomButton("Login");
        loginButton.setBounds(350, 250, 130, 40);
        loginButton.addActionListener(this);
        add(loginButton);
        
        // Custom styled Cancel button
        cancelButton = createCustomButton("Cancel");
        cancelButton.setBounds(500, 250, 130, 40);
        cancelButton.addActionListener(this);
        add(cancelButton);
        
        setVisible(true);
    }
    
     // Factory to create a custom rounded button with hover effect 
    private JButton createCustomButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Background color based on hover
                if (getModel().isRollover()) {
                    g2d.setColor(primaryColor.darker());
                } else {
                    g2d.setColor(primaryColor);
                }
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                // Text
                g2d.setColor(Color.WHITE);
                g2d.setFont(new Font("SansSerif", Font.BOLD, 16));
                FontMetrics fm = g2d.getFontMetrics();
                int textWidth = fm.stringWidth(text);
                int textHeight = fm.getHeight();
                g2d.drawString(text,
                    (getWidth() - textWidth) / 2,
                    (getHeight() - textHeight) / 2 + fm.getAscent());
            }
        };
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
    
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == loginButton) {
            // ... existing login logic ...
            try {
                String username = usernameField.getText().trim();
                String password = new String(passwordField.getPassword()).trim();
                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                        "Please enter both username and password",
                        "Input Error",
                        JOptionPane.WARNING_MESSAGE);
                    return;
                }
                Conn c = new Conn();
                String query = "select * from login where username='" + username + "' and password='" + password + "'";
                ResultSet rs = c.s.executeQuery(query);
                if (rs.next()) {
                    JOptionPane.showMessageDialog(this, "Login Successful");
                    new Dashboard().setVisible(true);
                    setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Invalid username or password",
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this,
                    "Error: " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        } else if (ae.getSource() == cancelButton) {
            System.exit(0);
        }
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.put("Button.background", Color.WHITE);
            UIManager.put("Button.select", new Color(0, 102, 204));
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> new Login());
    }
}
