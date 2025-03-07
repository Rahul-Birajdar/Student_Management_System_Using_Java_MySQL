import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class MainFrame extends JFrame {
    Container c;
    JButton btnAdd, btnView, btnUpdate, btnDelete;

    MainFrame() {
        c = getContentPane();
        c.setLayout(new BorderLayout());

        GradientPanel gradientPanel = new GradientPanel();
        gradientPanel.setLayout(new GridBagLayout());
        gradientPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        btnAdd = new JButton("Add Student");
        btnView = new JButton("View Student");
        btnUpdate = new JButton("Update Student");
        btnDelete = new JButton("Delete Student");

        Font f = new Font("Tahoma", Font.BOLD, 20);
        btnAdd.setFont(f);
        btnView.setFont(f);
        btnUpdate.setFont(f);
        btnDelete.setFont(f);

        Dimension buttonSize = new Dimension(200, 50);
        btnAdd.setPreferredSize(buttonSize);
        btnView.setPreferredSize(buttonSize);
        btnUpdate.setPreferredSize(buttonSize);
        btnDelete.setPreferredSize(buttonSize);

        // Set button background and foreground colors
        btnAdd.setBackground(new Color(0, 128, 255)); // Blue
        btnAdd.setForeground(Color.WHITE);
        btnView.setBackground(new Color(0, 204, 0)); // Green
        btnView.setForeground(Color.WHITE);
        btnUpdate.setBackground(new Color(255, 165, 0)); // Orange
        btnUpdate.setForeground(Color.WHITE);
        btnDelete.setBackground(new Color(255, 69, 0)); // Red
        btnDelete.setForeground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.gridx = 0;

        gbc.gridy = 0;
        gradientPanel.add(btnAdd, gbc);
        gbc.gridy = 1;
        gradientPanel.add(btnView, gbc);
        gbc.gridy = 2;
        gradientPanel.add(btnUpdate, gbc);
        gbc.gridy = 3;
        gradientPanel.add(btnDelete, gbc);

        c.add(gradientPanel, BorderLayout.CENTER);

        ActionListener a1 = (ae) -> {
            new AddFrame();
            dispose(); // Close MainFrame after opening AddFrame
        };
        btnAdd.addActionListener(a1);

        ActionListener a2 = (ae) -> {
            new ViewFrame();
            dispose(); // Close MainFrame after opening ViewFrame
        };
        btnView.addActionListener(a2);

	 ActionListener a3 = (ae) -> {
            new UpdateFrame();
            dispose(); // Close MainFrame after opening UpdateFrame
        };
        btnUpdate.addActionListener(a3);

	 ActionListener a4 = (ae) -> {
            new DeleteFrame();
            dispose(); // Close MainFrame after opening DeleteFrame
        };
        btnDelete.addActionListener(a4);

        setTitle("STUDENT MANAGEMENT SYSTEM BY RSB");
        setSize(600, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false); // Make the window non-resizable
        setVisible(true);
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}
