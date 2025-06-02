package hotel.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AddEmployee extends JFrame implements ActionListener {
    private JTextField t1, t2, t3, t4, t5, t6;
    private JRadioButton r1, r2;
    private JComboBox<String> c1;
    private JButton b1;
    private Color primaryColor = new Color(0, 77, 153);

    public AddEmployee() {
        // Frame settings
        setTitle("Add Employee");
        setBounds(350, 200, 850, 540);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        // Name
        JLabel name = new JLabel("NAME");
        name.setFont(new Font("Tahoma", Font.PLAIN, 17));
        name.setBounds(60, 30, 120, 30);
        add(name);

        t1 = new JTextField();
        t1.setBounds(200, 30, 150, 30);
        add(t1);

        // Age
        JLabel age = new JLabel("AGE");
        age.setFont(new Font("Tahoma", Font.PLAIN, 17));
        age.setBounds(60, 80, 120, 30);
        add(age);

        t2 = new JTextField();
        t2.setBounds(200, 80, 150, 30);
        add(t2);

        // Gender
        JLabel gender = new JLabel("GENDER");
        gender.setFont(new Font("Tahoma", Font.PLAIN, 17));
        gender.setBounds(60, 130, 120, 30);
        add(gender);

        r1 = new JRadioButton("Male");
        r1.setFont(new Font("Tahoma", Font.PLAIN, 14));
        r1.setBounds(200, 130, 70, 30);
        r1.setBackground(Color.WHITE);
        add(r1);

        r2 = new JRadioButton("Female");
        r2.setFont(new Font("Tahoma", Font.PLAIN, 14));
        r2.setBounds(280, 130, 70, 30);
        r2.setBackground(Color.WHITE);
        add(r2);

        ButtonGroup bg = new ButtonGroup();
        bg.add(r1);
        bg.add(r2);

        // Job
        JLabel job = new JLabel("JOB");
        job.setFont(new Font("Tahoma", Font.PLAIN, 17));
        job.setBounds(60, 180, 120, 30);
        add(job);

        String[] roles = {"Front Desk Clerks", "Porters", "Housekeeping", "Kitchen Staff", "Room Service", "Waiter/Waitress", "Manager", "Accountant", "Chef"};
        c1 = new JComboBox<>(roles);
        c1.setBounds(200, 180, 150, 30);
        c1.setBackground(Color.WHITE);
        add(c1);

        // Salary
        JLabel salary = new JLabel("SALARY");
        salary.setFont(new Font("Tahoma", Font.PLAIN, 17));
        salary.setBounds(60, 230, 120, 30);
        add(salary);

        t3 = new JTextField();
        t3.setBounds(200, 230, 150, 30);
        add(t3);

        // Phone
        JLabel phone = new JLabel("PHONE");
        phone.setFont(new Font("Tahoma", Font.PLAIN, 17));
        phone.setBounds(60, 280, 120, 30);
        add(phone);

        t4 = new JTextField();
        t4.setBounds(200, 280, 150, 30);
        add(t4);

        // Email
        JLabel email = new JLabel("EMAIL");
        email.setFont(new Font("Tahoma", Font.PLAIN, 17));
        email.setBounds(60, 330, 120, 30);
        add(email);

        t5 = new JTextField();
        t5.setBounds(200, 330, 150, 30);
        add(t5);

        // Aadhar
        JLabel nid = new JLabel("NID");
        nid.setFont(new Font("Tahoma", Font.PLAIN, 17));
        nid.setBounds(60, 380, 120, 30);
        add(nid);

        t6 = new JTextField();
        t6.setBounds(200, 380, 150, 30);
        add(t6);

        // Submit button with custom style
        b1 = createCustomButton("Submit");
        b1.setBounds(200, 430, 150, 30);
        b1.addActionListener(this);
        add(b1);

        // Image
//        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/tenth.jpg"));
//        Image img = i1.getImage().getScaledInstance(450, 450, Image.SCALE_DEFAULT);
//        JLabel l1 = new JLabel(new ImageIcon(img));
//        l1.setBounds(380, 60, 450, 450);
//        add(l1);
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/tenth.jpg"));
        Image i2 = i1.getImage().getScaledInstance(500, 500, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l1 = new JLabel(i3);
        l1.setBounds(380, 60, 450, 450);
        add(l1);

        // Header
        JLabel l2 = new JLabel("ADD EMPLOYEE DETAILS");
        l2.setForeground(primaryColor);
        l2.setBounds(420, 30, 400, 30);
        l2.setFont(new Font("Tahoma", Font.PLAIN, 30));
        add(l2);

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

    public void actionPerformed(ActionEvent ae) {
        String name = t1.getText();
        String age = t2.getText();
        String salary = t3.getText();
        String phone = t4.getText();
        String email = t5.getText();
        String nid = t6.getText();
        String gender = r1.isSelected() ? "Male" : r2.isSelected() ? "Female" : null;
        String job = (String) c1.getSelectedItem();
        try {
            Conn c = new Conn();
            String query = "INSERT INTO employee values('" + name + "','" + age + "','" + gender + "','" + job + "','" + salary + "','" + phone + "','" + email + "','" + nid + "')";
            c.s.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "Employee Added Successfully");
            setVisible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new AddEmployee();
    }
}
