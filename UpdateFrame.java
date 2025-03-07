import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

class UpdateFrame extends JFrame {
    Container c;
    JLabel labRno, labName, labSub1, labSub2, labSub3;
    JTextField txtRno, txtName, txtSub1, txtSub2, txtSub3;
    JButton btnSearch, btnUpdate, btnBack;
    Connection con;

    UpdateFrame() {
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
        btnSearch = new JButton("Search");
        btnUpdate = new JButton("Update");
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
        btnSearch.setFont(f);
        btnUpdate.setFont(f);
        btnBack.setFont(f);

        // Set button background and foreground colors
        btnSearch.setBackground(new Color(0, 128, 255)); // Blue
        btnSearch.setForeground(Color.WHITE);
        btnUpdate.setBackground(new Color(0, 128, 0)); // Green
        btnUpdate.setForeground(Color.WHITE);
        btnBack.setBackground(new Color(255, 69, 0)); // Red
        btnBack.setForeground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL; // Ensure components fill their cell

        gbc.gridy = 0;
        gradientPanel.add(labRno, gbc);
        gbc.gridy = 1;
        gradientPanel.add(txtRno, gbc);
        gbc.gridy = 2;
        gradientPanel.add(btnSearch, gbc);
        gbc.gridy = 3;
        gradientPanel.add(labName, gbc);
        gbc.gridy = 4;
        gradientPanel.add(txtName, gbc);
        gbc.gridy = 5;
        gradientPanel.add(labSub1, gbc);
        gbc.gridy = 6;
        gradientPanel.add(txtSub1, gbc);
        gbc.gridy = 7;
        gradientPanel.add(labSub2, gbc);
        gbc.gridy = 8;
        gradientPanel.add(txtSub2, gbc);
        gbc.gridy = 9;
        gradientPanel.add(labSub3, gbc);
        gbc.gridy = 10;
        gradientPanel.add(txtSub3, gbc);
        gbc.gridy = 11;
        gradientPanel.add(btnUpdate, gbc);
        gbc.gridy = 12;
        gradientPanel.add(btnBack, gbc);

        c.add(gradientPanel, BorderLayout.CENTER);

        btnSearch.addActionListener(e -> searchStudent());

        btnUpdate.addActionListener(e -> updateStudent());

        btnBack.addActionListener(e -> {
            new MainFrame();
            dispose(); // Close UpdateFrame after opening MainFrame
        });

        setTitle("UPDATE STUDENT");
        setSize(600, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false); // Make the window non-resizable
        setVisible(true);
    }

    private void searchStudent() {
        String rollNoText = txtRno.getText();
        if (rollNoText.isEmpty()) {
            JOptionPane.showMessageDialog(c, "Roll No field cannot be empty.");
            txtRno.requestFocus();
            txtRno.setText("");
            return;
        }

        try {
            int rollNo = Integer.parseInt(rollNoText);
            if (rollNo <= 0) {
                JOptionPane.showMessageDialog(c, "Roll No must be a positive integer.");
                txtRno.requestFocus();
                txtRno.setText("");
                return;
            }

            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            String url = "jdbc:mysql://localhost:3306/smsystem";
            con = DriverManager.getConnection(url, "root", "abc456");
            String sql = "SELECT * FROM student WHERE rno = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, rollNo);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                txtName.setText(rs.getString("name"));
                txtSub1.setText(String.valueOf(rs.getInt("sub1")));
                txtSub2.setText(String.valueOf(rs.getInt("sub2")));
                txtSub3.setText(String.valueOf(rs.getInt("sub3")));
            } else {
                JOptionPane.showMessageDialog(c, "No student found with Roll No: " + rollNo);
                clearFields();
            }

            rs.close();
            pst.close();
            con.close(); // Close the connection after the operation
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(c, "Issue: " + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(c, "Roll No must be a valid integer.");
        }
    }

    private void updateStudent() {
        String rollNoText = txtRno.getText();
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
        if (sub1Text.isEmpty()) {
            JOptionPane.showMessageDialog(c, "Sub Marks 1 field cannot be empty.");
            txtSub1.requestFocus();
            txtSub1.setText("");
            return;
        }
        if (sub2Text.isEmpty()) {
            JOptionPane.showMessageDialog(c, "Sub Marks 2 field cannot be empty.");
            txtSub2.requestFocus();
            txtSub2.setText("");
            return;
        }
        if (sub3Text.isEmpty()) {
            JOptionPane.showMessageDialog(c, "Sub Marks 3 field cannot be empty.");
            txtSub3.requestFocus();
            txtSub3.setText("");
            return;
        }

        int rollNo, sub1, sub2, sub3;
        try {
            rollNo = Integer.parseInt(rollNoText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(c, "Roll No must be a valid integer.");
            txtRno.requestFocus();
            txtRno.setText("");
            return;
        }
        try {
            sub1 = Integer.parseInt(sub1Text);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(c, "Sub Marks 1 must be a valid integer.");
            txtSub1.requestFocus();
            txtSub1.setText("");
            return;
        }
        try {
            sub2 = Integer.parseInt(sub2Text);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(c, "Sub Marks 2 must be a valid integer.");
            txtSub2.requestFocus();
            txtSub2.setText("");
            return;
        }
        try {
            sub3 = Integer.parseInt(sub3Text);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(c, "Sub Marks 3 must be a valid integer.");
            txtSub3.requestFocus();
            txtSub3.setText("");
            return;
        }

        if (rollNo <= 0) {
            JOptionPane.showMessageDialog(c, "Roll No must be positive.");
            txtRno.requestFocus();
            txtRno.setText("");
            return;
        }
        if (sub1 < 0 || sub1 > 100) {
            JOptionPane.showMessageDialog(c, "Sub Marks 1 must be between 0 and 100.");
            txtSub1.requestFocus();
            txtSub1.setText("");
            return;
        }
        if (sub2 < 0 || sub2 > 100) {
            JOptionPane.showMessageDialog(c, "Sub Marks 2 must be between 0 and 100.");
            txtSub2.requestFocus();
            txtSub2.setText("");
            return;
        }
        if (sub3 < 0 || sub3 > 100) {
            JOptionPane.showMessageDialog(c, "Sub Marks 3 must be between 0 and 100.");
            txtSub3.requestFocus();
            txtSub3.setText("");
            return;
        }

        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            String url = "jdbc:mysql://localhost:3306/smsystem";
            con = DriverManager.getConnection(url, "root", "abc456");
            String sql = "UPDATE student SET name = ?, sub1 = ?, sub2 = ?, sub3 = ? WHERE rno = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, name);
            pst.setInt(2, sub1);
            pst.setInt(3, sub2);
            pst.setInt(4, sub3);
            pst.setInt(5, rollNo);
            int result = pst.executeUpdate();

            if (result > 0) {
                JOptionPane.showMessageDialog(c, "Record updated successfully.");
		clearFields(); // Clear the fields after a successful update
            } else {
                JOptionPane.showMessageDialog(c, "No student found with Roll No: " + rollNo);
            }

            pst.close();
            con.close(); // Close the connection after the operation
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(c, "Issue: " + e.getMessage());
        }
    }

    private void clearFields() {
       	txtRno.setText("");
        txtName.setText("");
        txtSub1.setText("");
        txtSub2.setText("");
        txtSub3.setText("");
        txtRno.requestFocus();
    }

    public static void main(String[] args) {
        new UpdateFrame();
    }
}
