// Department.java
package hotel.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;

public class Department extends JFrame implements ActionListener {
    private JTable t1;
    private JButton b1, b2;
    private Color primaryColor = new Color(0, 77, 153);

    public Department() {
        // Frame setup
        setTitle("Department");
        setBounds(400, 200, 700, 480);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        // Table for department data
        t1 = new JTable();
        t1.setBounds(0, 50, 700, 350);
        add(t1);

        // Table headers
        JLabel l1 = new JLabel("Department");
        l1.setBounds(180, 10, 100, 20);
        add(l1);

        JLabel l2 = new JLabel("Budget");
        l2.setBounds(420, 10, 100, 20);
        add(l2);

        // Buttons
        b1 = createCustomButton("Load Data");
        b1.setBounds(180, 400, 120, 30);
        b1.addActionListener(this);
        add(b1);

        b2 = createCustomButton("Back");
        b2.setBounds(380, 400, 120, 30);
        b2.addActionListener(this);
        add(b2);

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
                String str = "SELECT * FROM department";
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
        new Department();
    }
}
