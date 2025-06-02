package hotel.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AddRoom extends JFrame implements ActionListener {
    private JTextField t1, t2;
    private JComboBox<String> c1, c2, c3;
    private JButton b1, b2;
    private Color primaryColor = new Color(0, 77, 153);

    public AddRoom() {
        // Frame settings
        setTitle("Add Rooms");
        setBounds(330, 200, 940, 470);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        // Title label
        JLabel l1 = new JLabel("Add Rooms");
        l1.setFont(new Font("Tahoma", Font.BOLD, 18));
        l1.setBounds(150, 20, 150, 20);
        add(l1);

        // Room Number
        JLabel room = new JLabel("Room Number");
        room.setFont(new Font("Tahoma", Font.PLAIN, 16));
        room.setBounds(60, 80, 120, 30);
        add(room);

        t1 = new JTextField();
        t1.setBounds(200, 80, 150, 30);
        add(t1);

        // Available
        JLabel available = new JLabel("Available");
        available.setFont(new Font("Tahoma", Font.PLAIN, 16));
        available.setBounds(60, 130, 120, 30);
        add(available);

        c1 = new JComboBox<>(new String[]{"Available", "Occupied"});
        c1.setBounds(200, 130, 150, 30);
        c1.setBackground(Color.WHITE);
        add(c1);

        // Cleaning Status
        JLabel status = new JLabel("Cleaning Status");
        status.setFont(new Font("Tahoma", Font.PLAIN, 16));
        status.setBounds(60, 180, 120, 30);
        add(status);

        c2 = new JComboBox<>(new String[]{"Cleaned", "Dirty"});
        c2.setBounds(200, 180, 150, 30);
        c2.setBackground(Color.WHITE);
        add(c2);

        // Price
        JLabel price = new JLabel("Price");
        price.setFont(new Font("Tahoma", Font.PLAIN, 16));
        price.setBounds(60, 230, 120, 30);
        add(price);

        t2 = new JTextField();
        t2.setBounds(200, 230, 150, 30);
        add(t2);

        // Bed Type
        JLabel type = new JLabel("Bed Type");
        type.setFont(new Font("Tahoma", Font.PLAIN, 16));
        type.setBounds(60, 280, 120, 30);
        add(type);

        c3 = new JComboBox<>(new String[]{"Single Bed", "Double Bed"});
        c3.setBounds(200, 280, 150, 30);
        c3.setBackground(Color.WHITE);
        add(c3);

        // Custom styled buttons
        b1 = createCustomButton("Add Room");
        b1.setBounds(60, 350, 130, 30);
        b1.addActionListener(this);
        add(b1);

        b2 = createCustomButton("Cancel");
        b2.setBounds(220, 350, 130, 30);
        b2.addActionListener(this);
        add(b2);

        // Image panel
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/twelve.jpg"));
        JLabel l5 = new JLabel(i1);
        l5.setBounds(400, 30, 500, 350);
        add(l5);

        setVisible(true);
    }

    // Factory for styled buttons (unchanged backend logic preserved)
    private JButton createCustomButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color bg = getModel().isRollover() ? primaryColor.darker() : primaryColor;
                g2d.setColor(bg);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2d.setColor(Color.WHITE);
                g2d.setFont(new Font("Tahoma", Font.BOLD, 14));
                FontMetrics fm = g2d.getFontMetrics();
                int w = fm.stringWidth(text);
                int h = fm.getHeight();
                g2d.drawString(text, (getWidth()-w)/2, (getHeight()-h)/2 + fm.getAscent());
            }
        };
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    // Action handling with the same backend DB logic (database load)
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == b1) {
            String room = t1.getText();
            String available = (String) c1.getSelectedItem();
            String status = (String) c2.getSelectedItem();
            String price = t2.getText();
            String type = (String) c3.getSelectedItem();
            try {
                Conn c = new Conn();
                String str = "INSERT INTO room values('" + room + "','" + available + "','" + status + "','" + price + "','" + type + "')";
                c.s.executeUpdate(str);
                JOptionPane.showMessageDialog(null, "New Room Added");
                this.setVisible(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == b2) {
            this.setVisible(false);
        }
    }

    public static void main(String[] args) {
        new AddRoom();
    }
}