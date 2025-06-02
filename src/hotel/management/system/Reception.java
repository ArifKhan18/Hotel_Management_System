package hotel.management.system;

import javax.swing.*;
import java.sql.*;    
import java.awt.*;
import java.awt.event.*;

public class Reception extends JFrame implements ActionListener {

    private JPanel contentPane;
    private JButton btnNewCustomerForm, btnRoom, btnDepartment, btnAllEmployeeInfo;
    private JButton btnCustomerInfo, btnManagerInfo, btnCheckOut, btnUpdateStatus;
    private JButton btnUpdateRoomStatus, btnSearchRoom, btnLogOut;
    private Color primaryColor = new Color(0, 77, 153);

    public static void main(String[] args) {
        new Reception();
    }
    
    public Reception() {
        setBounds(530, 200, 850, 570);
        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Background image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/fourth.jpg"));
        Image img = i1.getImage().getScaledInstance(500, 500, Image.SCALE_DEFAULT);
        JLabel l1 = new JLabel(new ImageIcon(img));
        l1.setBounds(250, 30, 500, 470);
        contentPane.add(l1);

        // Buttons with custom style
        btnNewCustomerForm = createCustomButton("New Customer Form");
        btnNewCustomerForm.setBounds(10, 30, 200, 30);
        btnNewCustomerForm.addActionListener(this);
        contentPane.add(btnNewCustomerForm);

        btnRoom = createCustomButton("Room");
        btnRoom.setBounds(10, 70, 200, 30);
        btnRoom.addActionListener(this);
        contentPane.add(btnRoom);

        btnDepartment = createCustomButton("Department");
        btnDepartment.setBounds(10, 110, 200, 30);
        btnDepartment.addActionListener(this);
        contentPane.add(btnDepartment);

        btnAllEmployeeInfo = createCustomButton("All Employee Info");
        btnAllEmployeeInfo.setBounds(10, 150, 200, 30);
        btnAllEmployeeInfo.addActionListener(this);
        contentPane.add(btnAllEmployeeInfo);

        btnCustomerInfo = createCustomButton("Customer Info");
        btnCustomerInfo.setBounds(10, 190, 200, 30);
        btnCustomerInfo.addActionListener(this);
        contentPane.add(btnCustomerInfo);

        btnManagerInfo = createCustomButton("Manager Info");
        btnManagerInfo.setBounds(10, 230, 200, 30);
        btnManagerInfo.addActionListener(this);
        contentPane.add(btnManagerInfo);

        btnCheckOut = createCustomButton("Check Out");
        btnCheckOut.setBounds(10, 270, 200, 30);
        btnCheckOut.addActionListener(this);
        contentPane.add(btnCheckOut);

        btnUpdateStatus = createCustomButton("Update Check Status");
        btnUpdateStatus.setBounds(10, 310, 200, 30);
        btnUpdateStatus.addActionListener(this);
        contentPane.add(btnUpdateStatus);

        btnUpdateRoomStatus = createCustomButton("Update Room Status");
        btnUpdateRoomStatus.setBounds(10, 350, 200, 30);
        btnUpdateRoomStatus.addActionListener(this);
        contentPane.add(btnUpdateRoomStatus);

        btnSearchRoom = createCustomButton("Search Room");
        btnSearchRoom.setBounds(10, 390, 200, 30);
        btnSearchRoom.addActionListener(this);
        contentPane.add(btnSearchRoom);

        btnLogOut = createCustomButton("Log Out");
        btnLogOut.setBounds(10, 430, 200, 30);
        btnLogOut.addActionListener(this);
        contentPane.add(btnLogOut);

        getContentPane().setBackground(Color.WHITE);
        setVisible(true);
    }
    
    // Factory for styled buttons
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

    @Override
    public void actionPerformed(ActionEvent ae) {
        try {
            if (ae.getSource() == btnNewCustomerForm) {
                new NewCustomer().setVisible(true);
                setVisible(false);
            } else if (ae.getSource() == btnRoom) {
                new Room().setVisible(true);
                setVisible(false);
            } else if (ae.getSource() == btnDepartment) {
                new Department().setVisible(true);
                setVisible(false);
            } else if (ae.getSource() == btnAllEmployeeInfo) {
                new Employee().setVisible(true);
                setVisible(false);
            } else if (ae.getSource() == btnCustomerInfo) {
                new CustomerInfo().setVisible(true);
                setVisible(false);
            } else if (ae.getSource() == btnManagerInfo) {
                new ManagerInfo().setVisible(true);
                setVisible(false);
            } else if (ae.getSource() == btnCheckOut) {
                new CheckOut().setVisible(true);
                setVisible(false);
            } else if (ae.getSource() == btnUpdateStatus) {
                new UpdateCheck().setVisible(true);
                setVisible(false);
            } else if (ae.getSource() == btnUpdateRoomStatus) {
                new UpdateRoom().setVisible(true);
                setVisible(false);
            } else if (ae.getSource() == btnSearchRoom) {
                new SearchRoom().setVisible(true);
                setVisible(false);
            } else if (ae.getSource() == btnLogOut) {
                new Login().setVisible(true);
                setVisible(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
}
