import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ViewFrame extends JFrame {
    Container c;
    JTextArea taData; // Changed from TextArea to JTextArea
    JButton btnBack;

    public ViewFrame() {
        // Set up the frame
        setTitle("VIEW STUDENT");
        setSize(600, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false); // Make the window non-resizable

        // Set up the content pane
        c = getContentPane();
        c.setLayout(new BorderLayout());

        GradientPanel gradientPanel = new GradientPanel();
        gradientPanel.setLayout(new GridBagLayout());
        gradientPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        taData = new JTextArea(10, 30); // Adjusted dimensions for better visibility
        taData.setEditable(false); // Make the text area read-only
        btnBack = new JButton("Back to Main");

        Font f = new Font("Tahoma", Font.BOLD, 16); // Adjusted font size
        taData.setFont(f);
        btnBack.setFont(f);

        // Set button background and foreground colors
        btnBack.setBackground(new Color(0, 128, 255)); // Blue
        btnBack.setForeground(Color.WHITE);

        // Layout constraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Adjusted padding
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gradientPanel.add(new JScrollPane(taData), gbc); // Added JScrollPane for scrollability
        gbc.gridy = 1;
        gbc.weighty = 0.0;
        gradientPanel.add(btnBack, gbc);

        c.add(gradientPanel, BorderLayout.CENTER);

        // Fetch and display data
        fetchData();

        btnBack.addActionListener(e -> {
            new MainFrame();
            dispose();
        });

        setVisible(true);
    }

    private void fetchData() {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            String url = "jdbc:mysql://localhost:3306/smsystem";
            con = DriverManager.getConnection(url, "root", "abc456");
            String sql = "SELECT * FROM student";
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            StringBuilder sb = new StringBuilder();
            // Header with fixed column widths
            sb.append(String.format("%-10s %-20s %-10s %-10s %-10s\n", "Roll No.", "Name", "Sub1", "Sub2", "Sub3"));
            sb.append("=".repeat(40)).append("\n"); // A more consistent separator

            if (!rs.isBeforeFirst()) {
                sb.append("No data found.");
            } else {
                while (rs.next()) {
                    sb.append(String.format("%-10d %-20s %-10d %-10d %-10d\n",
                      rs.getInt("rno"),
                      rs.getString("name"),
                      rs.getInt("sub1"),
                      rs.getInt("sub2"),
                      rs.getInt("sub3")));
                }
            }

            taData.setText(sb.toString());
        } catch (SQLException e) {
            System.out.println("Issue: " + e);
            taData.setText("Issue: " + e.getMessage()); // Display the error message in the text area
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
    }
}
