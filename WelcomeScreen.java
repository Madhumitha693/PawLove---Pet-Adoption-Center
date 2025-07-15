import javax.swing.*;
import java.awt.*;

public class WelcomeScreen extends JFrame {
    public WelcomeScreen() {
        setTitle("PAWLOVE - Pet Adoption Center");
        setSize(600, 400);
        setLocationRelativeTo(null); // Center the window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Title Label
        JLabel titleLabel = new JLabel("Welcome to PawLove Pet Adoption Center", JLabel.CENTER);
        titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 22));
        titleLabel.setForeground(new Color(255, 105, 180));

        // Pet Image (You must have jumping-dog.gif in your folder)
        ImageIcon dogIcon = new ImageIcon("jumping-dog.gif");
        JLabel imageLabel = new JLabel(dogIcon, JLabel.CENTER);

        // Start Button
        JButton startButton = new JButton("Start");
        startButton.setFont(new Font("Arial", Font.BOLD, 16));
        startButton.setBackground(new Color(173, 216, 230));
        startButton.setFocusPainted(false);

        // Action for button
        startButton.addActionListener(e -> {
            dispose(); // Close welcome screen
            new AdoptionGUI(new AdoptionCenter()); // Open main GUI
        });

        // Add to layout
        add(titleLabel, BorderLayout.NORTH);
        add(imageLabel, BorderLayout.CENTER);
        add(startButton, BorderLayout.SOUTH);

        setVisible(true);
    }
}
