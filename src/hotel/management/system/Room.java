// Room.java
package hotel.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;

public class Room extends JFrame implements ActionListener {
    private JTable t1;
    private JButton b1, b2;
    private Color primaryColor = new Color(0, 77, 153);

    public Room() {
        // Frame setup
        setTitle("Room");
        setBounds(300, 200, 1050, 600);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        // Table for room data
        t1 = new JTable();
        t1.setBounds(0, 40, 500, 400);
        add(t1);

        // Table headers
        JLabel l1 = new JLabel("Room Number");
        l1.setBounds(10, 10, 100, 20);
        add(l1);

        JLabel l2 = new JLabel("Availability");
        l2.setBounds(120, 10, 80, 20);
        add(l2);

        JLabel l3 = new JLabel("Status");
        l3.setBounds(230, 10, 80, 20);
        add(l3);

        JLabel l4 = new JLabel("Price");
        l4.setBounds(330, 10, 60, 20);
        add(l4);

        JLabel l5 = new JLabel("Bed Type");
        l5.setBounds(410, 10, 100, 20);
        add(l5);

        // Buttons
        b1 = createCustomButton("Load Data");
        b1.setBounds(100, 460, 120, 30);
        b1.addActionListener(this);
        add(b1);

        b2 = createCustomButton("Back");
        b2.setBounds(250, 460, 120, 30);
        b2.addActionListener(this);
        add(b2);

        // Side image
        ImageIcon i1 = new ImageIcon(
            ClassLoader.getSystemResource("icons/eight.jpg")
        );
        Image img = i1.getImage().getScaledInstance(600, 600, Image.SCALE_DEFAULT);
        JLabel l6 = new JLabel(new ImageIcon(img));
        l6.setBounds(500, 0, 600, 600);
        add(l6);

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
            try {
                Conn c = new Conn();
                String str = "SELECT * FROM room";
                ResultSet rs = c.s.executeQuery(str);
                t1.setModel(DbUtils.resultSetToTableModel(rs));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == b2) {
            new Reception().setVisible(true);
            dispose();
        }
    }

    public static void main(String[] args) {
        new Room();
    }
}
