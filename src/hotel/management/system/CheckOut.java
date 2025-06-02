// CheckOut.java
package hotel.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Date;

public class CheckOut extends JFrame implements ActionListener {
    private Choice c1;
    private JTextField t1;
    private JButton b1, b2, b3;
    private Color primaryColor = new Color(0, 77, 153);

    public CheckOut() {
        setTitle("Check Out");
        setBounds(300, 200, 800, 300);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel l1 = new JLabel("Check Out");
        l1.setFont(new Font("Tahoma", Font.PLAIN, 20));
        l1.setBounds(100, 20, 120, 30);
        add(l1);

        JLabel l2 = new JLabel("Customer ID");
        l2.setBounds(30, 80, 100, 30);
        add(l2);

        c1 = new Choice();
        try {
            ResultSet rs = new Conn().s.executeQuery("SELECT number FROM customer");
            while (rs.next()) c1.add(rs.getString(1));
        } catch (Exception e) { e.printStackTrace(); }
        c1.setBounds(150, 80, 150, 30);
        add(c1);

        JLabel l3 = new JLabel("Room Number");
        l3.setBounds(30, 130, 100, 30);
        add(l3);

        t1 = new JTextField();
        t1.setBounds(150, 130, 150, 30);
        add(t1);

        b1 = createCustomButton("Checkout");
        b1.setBounds(30, 200, 120, 30);
        b1.addActionListener(this);
        add(b1);

        b2 = createCustomButton("Back");
        b2.setBounds(170, 200, 120, 30);
        b2.addActionListener(this);
        add(b2);

        ImageIcon tick = new ImageIcon(ClassLoader.getSystemResource("icons/tick.png"));
        b3 = new JButton(new ImageIcon(tick.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT)));
        b3.setBounds(310, 80, 20, 20);
        b3.addActionListener(this);
        add(b3);

        JLabel bg = new JLabel(new ImageIcon(
            new ImageIcon(ClassLoader.getSystemResource("icons/sixth.jpg")).getImage()
            .getScaledInstance(400,250,Image.SCALE_DEFAULT)
        ));
        bg.setBounds(350, 0, 400, 250);
        add(bg);

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
                g2.drawString(text, (getWidth()-w)/2, (getHeight()-h)/2 + fm.getAscent());
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
            String id = c1.getSelectedItem();
            String room = t1.getText();
            try {
                Conn c = new Conn();
                c.s.executeUpdate("DELETE FROM customer WHERE number='"+id+"'");
                c.s.executeUpdate("UPDATE room SET availability='Available' WHERE room_number='"+room+"'");
                JOptionPane.showMessageDialog(null, "Checkout Done");
                new Reception().setVisible(true);
                dispose();
            } catch (Exception e) { e.printStackTrace(); }
        } else if (ae.getSource() == b2) {
            new Reception().setVisible(true);
            dispose();
        } else {
            try {
                ResultSet rs = new Conn().s.executeQuery(
                    "SELECT room_number FROM customer WHERE number='"+c1.getSelectedItem()+"'"
                );
                if (rs.next()) t1.setText(rs.getString(1));
            } catch (Exception e) { e.printStackTrace(); }
        }
    }

    public static void main(String[] args) {
        new CheckOut();
    }
}