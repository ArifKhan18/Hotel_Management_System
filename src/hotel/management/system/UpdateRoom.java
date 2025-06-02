package hotel.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class UpdateRoom extends JFrame implements ActionListener {
    private Choice c1; // Guest ID এর জন্য আগের মতোই রাখা হয়েছে
    private JTextField t1; // Room Number দেখানোর জন্য TextField
    private JComboBox<String> c2, c3; // Availability ও Cleaning Status এর জন্য JComboBox
    private JButton b1, b2, b3;
    private Color primaryColor = new Color(0, 77, 153);

    public UpdateRoom() {
        setTitle("Update Room Status");
        setBounds(300, 200, 950, 450);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel l1 = new JLabel("Update Room Status");
        l1.setFont(new Font("Tahoma", Font.PLAIN, 25));
        l1.setBounds(30, 20, 250, 30);
        l1.setForeground(Color.BLUE);
        add(l1);

        // Guest ID
        JLabel l2 = new JLabel("Guest ID");
        l2.setBounds(30, 80, 120, 20);
        add(l2);

        c1 = new Choice();
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("SELECT number FROM customer");
            while (rs.next()) {
                c1.add(rs.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        c1.setBounds(200, 80, 150, 25);
        add(c1);

        // Room Number
        JLabel l3 = new JLabel("Room Number");
        l3.setBounds(30, 130, 120, 20);
        add(l3);

        t1 = new JTextField();
        t1.setBounds(200, 130, 150, 25);
        t1.setEditable(false); // শুধু দেখানোর জন্য
        add(t1);

        // Availability (JComboBox)
        JLabel l4 = new JLabel("Availability");
        l4.setBounds(30, 180, 120, 20);
        add(l4);

        c2 = new JComboBox<>(new String[]{"Available", "Occupied"});
        c2.setBounds(200, 180, 150, 25);
        c2.setBackground(Color.WHITE);
        add(c2);

        // Clean Status (JComboBox)
        JLabel l5 = new JLabel("Clean Status");
        l5.setBounds(30, 230, 120, 20);
        add(l5);

        c3 = new JComboBox<>(new String[]{"Cleaned", "Dirty"});
        c3.setBounds(200, 230, 150, 25);
        c3.setBackground(Color.WHITE);
        add(c3);

        // Buttons
        b1 = createCustomButton("Check");
        b1.setBounds(30, 300, 120, 30);
        b1.addActionListener(this);
        add(b1);

        b2 = createCustomButton("Update");
        b2.setBounds(170, 300, 120, 30);
        b2.addActionListener(this);
        add(b2);

        b3 = createCustomButton("Back");
        b3.setBounds(310, 300, 120, 30);
        b3.addActionListener(this);
        add(b3);

        // Image
        JLabel img = new JLabel(new ImageIcon(
            new ImageIcon(ClassLoader.getSystemResource("icons/seventh.jpg"))
                .getImage().getScaledInstance(500, 300, Image.SCALE_DEFAULT)
        ));
        img.setBounds(400, 10, 500, 400);
        add(img);

        setVisible(true);
    }

    private JButton createCustomButton(String text) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
                );
                Color bg = getModel().isRollover() ? primaryColor.darker() : primaryColor;
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
        try {
            Conn c = new Conn();
            String id = c1.getSelectedItem();
            if (ae.getSource() == b1) {
                // Check বাটনে ক্লিক করলে প্রথমে Customer থেকে রুম নাম্বার নিয়ে আসবে
                ResultSet rs1 = c.s.executeQuery(
                    "SELECT room_number FROM customer WHERE number='" + id + "'"
                );
                if (rs1.next()) {
                    String room = rs1.getString(1);
                    t1.setText(room);

                    // তারপর Room টেবিল থেকে Availability ও Cleaning Status সেট করবে
                    ResultSet rs2 = c.s.executeQuery(
                        "SELECT availability, cleaning_status FROM room WHERE room_number='" + room + "'"
                    );
                    if (rs2.next()) {
                        c2.setSelectedItem(rs2.getString("availability"));
                        c3.setSelectedItem(rs2.getString("cleaning_status"));
                    }
                }
            } else if (ae.getSource() == b2) {
                // Update এ ক্লিক করলে JComboBox- থেকে মান নিয়ে আপডেট
                String avail = (String) c2.getSelectedItem();
                String clean = (String) c3.getSelectedItem();
                c.s.executeUpdate(
                    "UPDATE room SET availability='" + avail + 
                    "', cleaning_status='" + clean + 
                    "' WHERE room_number='" + t1.getText() + "'"
                );
                JOptionPane.showMessageDialog(null, "Room Updated Successfully");
                new Reception().setVisible(true);
                dispose();
            } else {
                // Back এ ক্লিক করলে ফিরে যাবে Reception এ
                new Reception().setVisible(true);
                dispose();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new UpdateRoom();
    }
}
