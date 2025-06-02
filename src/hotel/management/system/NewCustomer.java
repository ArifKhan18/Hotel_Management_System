package hotel.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Date;

public class NewCustomer extends JFrame implements ActionListener {
    
    private JTextField t1, t2, t3, t4, t5;
    private JComboBox<String> c1;
    private Choice c2;
    private JRadioButton r1, r2;
    private JButton b1, b2;
    private Color primaryColor = new Color(0, 77, 153);
    
    public NewCustomer() {
        // Frame setup
        setTitle("New Customer Form");
        setBounds(350, 200, 800, 500);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        
        // Header
        JLabel l1 = new JLabel("NEW CUSTOMER FORM");
        l1.setBounds(100, 20, 300, 30);
        l1.setFont(new Font("Tahoma", Font.PLAIN, 20));
        add(l1);
        
        // ID type
        JLabel l2 = new JLabel("ID");
        l2.setBounds(35, 80, 100, 20);
        add(l2);
        c1 = new JComboBox<>(new String[] {
            "Passport", "Voter-id Card", "Driving License", "Nid"
        });
        c1.setBounds(200, 80, 150, 25);
        c1.setBackground(Color.WHITE);
        add(c1);
        
        // Number
        JLabel l3 = new JLabel("Number");
        l3.setBounds(35, 120, 100, 20);
        add(l3);
        t1 = new JTextField();
        t1.setBounds(200, 120, 150, 25);
        add(t1);
        
        // Name
        JLabel l4 = new JLabel("Name");
        l4.setBounds(35, 160, 100, 20);
        add(l4);
        t2 = new JTextField();
        t2.setBounds(200, 160, 150, 25);
        add(t2);
        
        // Gender
        JLabel l5 = new JLabel("Gender");
        l5.setBounds(35, 200, 100, 20);
        add(l5);
        r1 = new JRadioButton("Male");
        r1.setBounds(200, 200, 60, 25);
        r1.setBackground(Color.WHITE);
        add(r1);
        r2 = new JRadioButton("Female");
        r2.setBounds(270, 200, 80, 25);
        r2.setBackground(Color.WHITE);
        add(r2);
        ButtonGroup bg = new ButtonGroup();
        bg.add(r1);
        bg.add(r2);
        
        // Country
        JLabel l6 = new JLabel("Country");
        l6.setBounds(35, 240, 100, 20);
        add(l6);
        t3 = new JTextField();
        t3.setBounds(200, 240, 150, 25);
        add(t3);
        
        // Allocated room
        JLabel l7 = new JLabel("Allocated Room Number");
        l7.setBounds(35, 280, 150, 20);
        add(l7);
        c2 = new Choice();
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery(
                "SELECT room_number FROM room WHERE availability='Available'"
            );
            while (rs.next()) {
                c2.add(rs.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        c2.setBounds(200, 280, 150, 25);
        add(c2);
        
        // Checked in date
        JLabel l8 = new JLabel("Checked In");
        l8.setBounds(35, 320, 100, 20);
        add(l8);
        t4 = new JTextField(
            new java.text.SimpleDateFormat("yyyy-MM-dd").format(new Date())
        );
        t4.setBounds(200, 320, 150, 25);
        add(t4);
        
        // Deposit
        JLabel l9 = new JLabel("Deposit");
        l9.setBounds(35, 360, 100, 20);
        add(l9);
        t5 = new JTextField();
        t5.setBounds(200, 360, 150, 25);
        add(t5);
        
        // Buttons
        b1 = createCustomButton("Add Customer");
        b1.setBounds(50, 410, 140, 30);
        b1.addActionListener(this);
        add(b1);
        
        b2 = createCustomButton("Back");
        b2.setBounds(200, 410, 140, 30);
        b2.addActionListener(this);
        add(b2);
        
        // Side image
        ImageIcon i1 = new ImageIcon(
            ClassLoader.getSystemResource("icons/fifth.png")
        );
        Image img = i1.getImage().getScaledInstance(300, 400, Image.SCALE_DEFAULT);
        JLabel l10 = new JLabel(new ImageIcon(img));
        l10.setBounds(400, 50, 300, 400);
        add(l10);
        
        setVisible(true);
    }
    
    // Styled-button factory
    private JButton createCustomButton(String text) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
                );
                Color bg = getModel().isRollover()
                    ? primaryColor.darker()
                    : primaryColor;
                g2.setColor(bg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Tahoma", Font.BOLD, 14));
                FontMetrics fm = g2.getFontMetrics();
                int w = fm.stringWidth(text);
                int h = fm.getHeight();
                g2.drawString(
                    text,
                    (getWidth() - w) / 2,
                    (getHeight() - h) / 2 + fm.getAscent()
                );
            }
        };
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == b1) {
            String id = (String) c1.getSelectedItem();
            String number = t1.getText();
            String name = t2.getText();
            String gender = r1.isSelected() ? "Male" : "Female";
            String country = t3.getText();
            String room = c2.getSelectedItem();
            String status = t4.getText();
            String deposit = t5.getText();
            
            String insertCust = "INSERT INTO customer VALUES('"
                + id + "','" + number + "','" + name + "','" + gender
                + "','" + country + "','" + room + "','" + status + "','" + deposit + "')";
            String updRoom = "UPDATE room SET availability='Occupied' "
                + "WHERE room_number='" + room + "'";
            
            try {
                Conn c = new Conn();
                c.s.executeUpdate(insertCust);
                c.s.executeUpdate(updRoom);
                JOptionPane.showMessageDialog(null, "New Customer Added");
                new Reception().setVisible(true);
                dispose();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == b2) {
            new Reception().setVisible(true);
            dispose();
        }
    }
    
    public static void main(String[] args) {
        new NewCustomer();
    }
}
