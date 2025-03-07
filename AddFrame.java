import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

class AddFrame extends JFrame {
    Container c;
    JLabel labRno, labName, labSub1, labSub2, labSub3;
    JTextField txtRno, txtName, txtSub1, txtSub2, txtSub3;
    JButton btnSave, btnBack;

    AddFrame() {
        c = getContentPane();
        c.setLayout(new BorderLayout());

        GradientPanel gradientPanel = new GradientPanel();
        gradientPanel.setLayout(new GridBagLayout());
        gradientPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        labRno = new JLabel("Enter Roll No : ");
        txtRno = new JTextField(12);
        labName = new JLabel("Enter Name : ");
        txtName = new JTextField(12);
        labSub1 = new JLabel("Enter Sub Marks 1 : ");
        txtSub1 = new JTextField(12);
        labSub2 = new JLabel("Enter Sub Marks 2 : ");
        txtSub2 = new JTextField(12);
        labSub3 = new JLabel("Enter Sub Marks 3 : ");
        txtSub3 = new JTextField(12);
        
        btnSave = new JButton("Save Student");
        btnBack = new JButton("Back to Main");

        Font f = new Font("Tahoma", Font.BOLD, 20);
        labRno.setFont(f);
        txtRno.setFont(f);
        labName.setFont(f);
        txtName.setFont(f);
        labSub1.setFont(f);
        txtSub1.setFont(f);
        labSub2.setFont(f);
        txtSub2.setFont(f);
        labSub3.setFont(f);
        txtSub3.setFont(f);
        btnSave.setFont(f);
        btnBack.setFont(f);

        // Set button background and foreground colors
        btnSave.setBackground(new Color(0, 128, 255)); // Blue
        btnSave.setForeground(Color.WHITE);
        btnBack.setBackground(new Color(255, 69, 0)); // Red
        btnBack.setForeground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.gridx = 0;

        gbc.gridy = 0;
        gradientPanel.add(labRno, gbc);
        gbc.gridy = 1;
        gradientPanel.add(txtRno, gbc);
        gbc.gridy = 2;
        gradientPanel.add(labName, gbc);
        gbc.gridy = 3;
        gradientPanel.add(txtName, gbc);
        gbc.gridy = 4;
        gradientPanel.add(labSub1, gbc);
        gbc.gridy = 5;
        gradientPanel.add(txtSub1, gbc);
        gbc.gridy = 6;
        gradientPanel.add(labSub2, gbc);
        gbc.gridy = 7;
        gradientPanel.add(txtSub2, gbc);
        gbc.gridy = 8;
        gradientPanel.add(labSub3, gbc);
        gbc.gridy = 9;
        gradientPanel.add(txtSub3, gbc);
        gbc.gridy = 10;
        gradientPanel.add(btnSave, gbc);
        gbc.gridy = 11;
        gradientPanel.add(btnBack, gbc);

        c.add(gradientPanel, BorderLayout.CENTER);

        btnBack.addActionListener(e -> {
            new MainFrame();
            dispose(); // Close AddFrame after opening MainFrame
        });

        btnSave.addActionListener(e -> {
            try {
                // Input validation
                String rollNoText = txtRno.getText().trim();
                String name = txtName.getText().trim();
                String sub1Text = txtSub1.getText().trim();
                String sub2Text = txtSub2.getText().trim();
                String sub3Text = txtSub3.getText().trim();

                if (rollNoText.isEmpty()) {
                    JOptionPane.showMessageDialog(c, "Roll No field cannot be empty.");
                    txtRno.requestFocus();
                    txtRno.setText("");
                    return;
                }

                int rollNo;
                try {
                    rollNo = Integer.parseInt(rollNoText);
                    if (rollNo <= 0) {
                        JOptionPane.showMessageDialog(c, "Roll No must be positive.");
                        txtRno.requestFocus();
                        txtRno.setText("");
                        return;
                    }
                } catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(c, "Roll No must be a valid integer.");
                    txtRno.requestFocus();
                    txtRno.setText("");
                    return;
                }

                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(c, "Name field cannot be empty.");
                    txtName.requestFocus();
                    txtName.setText("");
                    return;
                }

                if (!name.matches("[A-Za-z]+")) {
                    JOptionPane.showMessageDialog(c, "Name should contain only alphabets.");
                    txtName.requestFocus();
                    txtName.setText("");
                    return;
                }

                int sub1, sub2, sub3;
                try {
                    sub1 = Integer.parseInt(sub1Text);
                    if (sub1 < 0 || sub1 > 100) {
                        JOptionPane.showMessageDialog(c, "Sub Marks 1 must be between 0 and 100.");
                        txtSub1.requestFocus();
                        txtSub1.setText("");
                        return;
                    }
                } catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(c, "Sub Marks 1 must be a valid integer.");
                    txtSub1.requestFocus();
                    txtSub1.setText("");
                    return;
                }

                try {
                    sub2 = Integer.parseInt(sub2Text);
                    if (sub2 < 0 || sub2 > 100) {
                        JOptionPane.showMessageDialog(c, "Sub Marks 2 must be between 0 and 100.");
                        txtSub2.requestFocus();
                        txtSub2.setText("");
                        return;
                    }
                } catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(c, "Sub Marks 2 must be a valid integer.");
                    txtSub2.requestFocus();
                    txtSub2.setText("");
                    return;
                }

                try {
                    sub3 = Integer.parseInt(sub3Text);
                    if (sub3 < 0 || sub3 > 100) {
                        JOptionPane.showMessageDialog(c, "Sub Marks 3 must be between 0 and 100.");
                        txtSub3.requestFocus();
                        txtSub3.setText("");
                        return;
                    }
                } catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(c, "Sub Marks 3 must be a valid integer.");
                    txtSub3.requestFocus();
                    txtSub3.setText("");
                    return;
                }

                DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
                String url = "jdbc:mysql://localhost:3306/smsystem";
                Connection con = DriverManager.getConnection(url, "root", "abc456");
                String sql = "insert into student values(?, ?, ?, ?, ?)";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setInt(1, rollNo);
                pst.setString(2, name);
                pst.setInt(3, sub1);
                pst.setInt(4, sub2);
                pst.setInt(5, sub3);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(c, "Student information saved successfully.");
                txtRno.setText("");
                txtName.setText("");
                txtSub1.setText("");
                txtSub2.setText("");
                txtSub3.setText("");
                txtRno.requestFocus();
                con.close();
            } catch (SQLException e1) {
                JOptionPane.showMessageDialog(c, "Database issue: " + e1.getMessage());
            }
        });

        setTitle("ADD STUDENT");
        setSize(600, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false); // Make the window non-resizable
        setVisible(true);
    }

    public static void main(String[] args) {
        new AddFrame();
    }
}
