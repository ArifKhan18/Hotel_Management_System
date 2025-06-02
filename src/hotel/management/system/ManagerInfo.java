// ManagerInfo.java
package hotel.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;

public class ManagerInfo extends JFrame implements ActionListener {
    private JTable t1;
    private JButton b1, b2;
    private Color primaryColor = new Color(0, 77, 153);

    public ManagerInfo() {
        // Frame setup
        setTitle("Manager Info");
        setBounds(250, 100, 1000, 650);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        // Table
        t1 = new JTable();
        t1.setBounds(0, 40, 1000, 500);
        add(t1);

        // Headers
        JLabel l1 = new JLabel("Name");    l1.setBounds(40, 10, 70, 20);  add(l1);
        JLabel l2 = new JLabel("Age");     l2.setBounds(170,10,70,20);     add(l2);
        JLabel l3 = new JLabel("Gender");  l3.setBounds(290,10,70,20);     add(l3);
        JLabel l4 = new JLabel("Job");     l4.setBounds(400,10,70,20);     add(l4);
        JLabel l5 = new JLabel("Salary");  l5.setBounds(540,10,70,20);     add(l5);
        JLabel l6 = new JLabel("Phone");   l6.setBounds(670,10,70,20);     add(l6);
        JLabel l7 = new JLabel("Email");   l7.setBounds(790,10,70,20);     add(l7);
        JLabel l8 = new JLabel("Id");  l8.setBounds(910,10,70,20);     add(l8);

        // Buttons
        b1 = createCustomButton("Load Data");
        b1.setBounds(350, 560, 120, 30);
        b1.addActionListener(this);
        add(b1);

        b2 = createCustomButton("Back");
        b2.setBounds(530, 560, 120, 30);
        b2.addActionListener(this);
        add(b2);

        setVisible(true);
    }

    // Styled-button factory (same as other classes)
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
                ResultSet rs = c.s.executeQuery(
                    "SELECT * FROM employee WHERE job='Manager'"
                );
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
        new ManagerInfo();
    }
}
