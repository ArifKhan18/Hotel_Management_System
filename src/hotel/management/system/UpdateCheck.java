package hotel.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class UpdateCheck extends JFrame implements ActionListener {
    private Choice c1;
    private JTextField t1, t2, t3, t4, t5;
    private JButton b1, b2, b3;
    private Color primaryColor = new Color(0, 77, 153);

    public UpdateCheck() {
        setTitle("Update Check-In Status");
        setBounds(300, 200, 950, 450);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel l1 = new JLabel("Check-in Details");
        l1.setFont(new Font("Tahoma", Font.PLAIN, 20));
        l1.setBounds(90, 20, 200, 30);
        add(l1);

        JLabel l2 = new JLabel("Customer-ID");
        l2.setBounds(30, 80, 100, 20);
        add(l2);

        c1 = new Choice();
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("SELECT number FROM customer");
            while (rs.next()) c1.add(rs.getString(1));
        } catch (Exception e) { e.printStackTrace(); }
        c1.setBounds(200, 80, 150, 25);
        add(c1);

        JLabel l3 = new JLabel("Room Number");
        l3.setBounds(30, 120, 100, 20);
        add(l3);
        t1 = new JTextField(); t1.setBounds(200,120,150,25); add(t1);

        JLabel l4 = new JLabel("Name");
        l4.setBounds(30, 160, 100, 20);
        add(l4);
        t2 = new JTextField(); t2.setBounds(200,160,150,25); add(t2);

        JLabel l5 = new JLabel("Check-in Date");
        l5.setBounds(30, 200, 100, 20);
        add(l5);
        t3 = new JTextField(); t3.setBounds(200,200,150,25); add(t3);

        JLabel l6 = new JLabel("Amount Paid");
        l6.setBounds(30, 240, 100, 20);
        add(l6);
        t4 = new JTextField(); t4.setBounds(200,240,150,25); add(t4);

        JLabel l7 = new JLabel("Pending Amount");
        l7.setBounds(30, 280, 120, 20);
        add(l7);
        t5 = new JTextField(); t5.setBounds(200,280,150,25); add(t5);

        b1 = createCustomButton("Check");
        b1.setBounds(30, 340, 100, 30);
        b1.addActionListener(this);
        add(b1);

        b2 = createCustomButton("Update");
        b2.setBounds(150, 340, 100, 30);
        b2.addActionListener(this);
        add(b2);

        b3 = createCustomButton("Back");
        b3.setBounds(270, 340, 100, 30);
        b3.addActionListener(this);
        add(b3);

        JLabel img = new JLabel(new ImageIcon(
            new ImageIcon(ClassLoader.getSystemResource("icons/nine.jpg")).getImage()
            .getScaledInstance(500,300,Image.SCALE_DEFAULT)
        ));
        img.setBounds(400, 50, 500, 300);
        add(img);

        setVisible(true);
    }

    private JButton createCustomButton(String text) {
        JButton btn = new JButton(text) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D)g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color bg = getModel().isRollover() ? primaryColor.darker() : primaryColor;
                g2.setColor(bg);
                g2.fillRoundRect(0,0,getWidth(),getHeight(),10,10);
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Tahoma",Font.BOLD,14));
                FontMetrics fm = g2.getFontMetrics();
                int w = fm.stringWidth(text), h = fm.getHeight();
                g2.drawString(text, (getWidth()-w)/2, (getHeight()-h)/2+fm.getAscent());
            }
        };
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    @Override public void actionPerformed(ActionEvent ae) {
        try {
            Conn c = new Conn();
            String id = c1.getSelectedItem();
            if (ae.getSource() == b1) {
    try {
        ResultSet rs = c.s.executeQuery("SELECT * FROM customer WHERE number='" + id + "'");
        if (rs.next()) {
            String roomNumber = rs.getString("room_number");
            t1.setText(roomNumber);
            t2.setText(rs.getString("name"));
            t3.setText(rs.getString("status"));
            String deposit = rs.getString("deposit");
            t4.setText(deposit);
            
            // Get the room price with proper error handling
            try {
                String priceQuery = "SELECT price FROM room WHERE room_number='" + roomNumber + "'";
                System.out.println("Executing query: " + priceQuery); // Debug output
                
                ResultSet rs2 = c.s.executeQuery(priceQuery);
                if (rs2.next()) {
                    String roomPrice = rs2.getString("price");
                    System.out.println("Room price: " + roomPrice + ", Deposit: " + deposit); // Debug output
                    
                    // Calculate pending amount with careful parsing
                    int totalPrice = Integer.parseInt(roomPrice.trim());
                    int depositPaid = Integer.parseInt(deposit.trim());
                    int pendingAmount = totalPrice - depositPaid;
                    
                    System.out.println("Calculated pending amount: " + pendingAmount); // Debug output
                    t5.setText(String.valueOf(pendingAmount));
                } else {
                    t5.setText("0"); // Default if room not found
                    System.out.println("Room not found for room number: " + roomNumber);
                }
            } catch (NumberFormatException nfe) {
                t5.setText("0"); // Default for parsing errors
                System.out.println("Number format error: " + nfe.getMessage());
            } catch (Exception e) {
                t5.setText("0"); // Default for other errors
                System.out.println("Error calculating pending amount: " + e.getMessage());
                e.printStackTrace();
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
} else if (ae.getSource() == b2) {
                c.s.executeUpdate(
                    "UPDATE customer SET room_number='"+t1.getText()+"', name='"+t2.getText()+"', status='"+t3.getText()+"', deposit='"+t4.getText()+"' WHERE number='"+id+"'"
                );
                JOptionPane.showMessageDialog(null, "Data Updated Successfully");
                new Reception().setVisible(true);
                dispose();
            } else {
                new Reception().setVisible(true);
                dispose();
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static void main(String[] args) {
        new UpdateCheck();
    }
}
