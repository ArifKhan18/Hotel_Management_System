// SearchRoom.java
package hotel.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import net.proteanit.sql.*;

public class SearchRoom extends JFrame implements ActionListener {
    private JTable t1;
    private JButton b1, b2;
    private JComboBox<String> c1;
    private JCheckBox c2;
    private Color primaryColor = new Color(0, 77, 153);

    public SearchRoom() {
        setTitle("Search Room");
        setBounds(250, 100, 1000, 650);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel l1 = new JLabel("Search For Room");
        l1.setFont(new Font("Tahoma", Font.PLAIN, 20));
        l1.setBounds(400, 30, 200, 30);
        add(l1);

        JLabel l2 = new JLabel("Room Bed Type");
        l2.setBounds(50, 100, 100, 20);
        add(l2);

        c1 = new JComboBox<>(new String[] {"Single Bed", "Double Bed"});
        c1.setBounds(150, 100, 150, 25);
        c1.setBackground(Color.WHITE);
        add(c1);

        c2 = new JCheckBox("Only Display Available");
        c2.setBounds(650, 100, 180, 25);
        c2.setBackground(Color.WHITE);
        add(c2);

        JLabel l3 = new JLabel("Room Number");
        l3.setBounds(50, 160, 100, 20);
        add(l3);

        JLabel l4 = new JLabel("Availability");
        l4.setBounds(270, 160, 100, 20);
        add(l4);

        JLabel l5 = new JLabel("Cleaning Status");
        l5.setBounds(450, 160, 100, 20);
        add(l5);

        JLabel l6 = new JLabel("Price");
        l6.setBounds(670, 160, 100, 20);
        add(l6);

        JLabel l7 = new JLabel("Bed Type");
        l7.setBounds(870, 160, 100, 20);
        add(l7);

        t1 = new JTable();
        t1.setBounds(0, 200, 1000, 300);
        add(t1);

        b1 = createCustomButton("Submit");
        b1.setBounds(300, 520, 120, 30);
        b1.addActionListener(this);
        add(b1);

        b2 = createCustomButton("Back");
        b2.setBounds(500, 520, 120, 30);
        b2.addActionListener(this);
        add(b2);

        setVisible(true);
    }

    private JButton createCustomButton(String text) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color bg = getModel().isRollover() ? primaryColor.darker() : primaryColor;
                g2.setColor(bg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Tahoma", Font.BOLD, 14));
                FontMetrics fm = g2.getFontMetrics();
                int w = fm.stringWidth(text);
                int h = fm.getHeight();
                g2.drawString(text, (getWidth() - w) / 2, (getHeight() - h) / 2 + fm.getAscent());
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
            if (ae.getSource() == b1) {
                String query;
                if (c2.isSelected()) {
                    query = "SELECT * FROM room WHERE availability='Available' AND bed_type='" + c1.getSelectedItem() + "'";
                } else {
                    query = "SELECT * FROM room WHERE bed_type='" + c1.getSelectedItem() + "'";
                }
                ResultSet rs = c.s.executeQuery(query);
                t1.setModel(DbUtils.resultSetToTableModel(rs));
            } else if (ae.getSource() == b2) {
                new Reception().setVisible(true);
                dispose();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new SearchRoom();
    }
}