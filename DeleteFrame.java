import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

class DeleteFrame extends JFrame {
    Container c;
    JLabel labRno;
    JTextField txtRno;
    JButton btnDelete, btnBack;
    Connection con;

    DeleteFrame() {
        c = getContentPane();
        c.setLayout(new BorderLayout());

        GradientPanel gradientPanel = new GradientPanel();
        gradientPanel.setLayout(new GridBagLayout());
        gradientPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        labRno = new JLabel("Enter Roll No : ");
        txtRno = new JTextField(10); // Adjust width as needed

        btnDelete = new JButton("Delete");
        btnBack = new JButton("Back to Main");

        Font f = new Font("Tahoma", Font.BOLD, 20);
        labRno.setFont(f);
        txtRno.setFont(f);
        btnDelete.setFont(f);
        btnBack.setFont(f);

        // Set button background and foreground colors
        btnDelete.setBackground(new Color(255, 69, 0)); // Red
        btnDelete.setForeground(Color.WHITE);
        btnBack.setBackground(new Color(0, 128, 255)); // Blue
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
        gradientPanel.add(btnDelete, gbc);
        gbc.gridy = 3;
        gradientPanel.add(btnBack, gbc);

        c.add(gradientPanel, BorderLayout.CENTER);

        btnDelete.addActionListener(e -> deleteStudent());

        btnBack.addActionListener(e -> {
            new MainFrame();
            dispose(); // Close DeleteFrame after opening MainFrame
        });

        setTitle("DELETE STUDENT");
        setSize(600, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false); // Make the window non-resizable
        setVisible(true);
    }

    private void deleteStudent() {
        String rollNoText = txtRno.getText();
        if (rollNoText.isEmpty()) {
            JOptionPane.showMessageDialog(c, "Roll No must be provided.");
            return;
        }

        int rollNo;
        try {
            rollNo = Integer.parseInt(rollNoText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(c, "Roll No must be a valid integer.");
            return;
        }

        if (rollNo <= 0) {
            JOptionPane.showMessageDialog(c, "Roll No must be positive.");
            return;
        }

        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            String url = "jdbc:mysql://localhost:3306/smsystem";
            con = DriverManager.getConnection(url, "root", "abc456");
            String sql = "DELETE FROM student WHERE rno = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, rollNo);
            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(c, "Student record deleted successfully.");
                txtRno.setText(""); // Clear input field
                txtRno.requestFocus();
            } else {
                JOptionPane.showMessageDialog(c, "No student found with Roll No: " + rollNo);
            }

            pst.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(c, "Issue: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new DeleteFrame();
    }
}
