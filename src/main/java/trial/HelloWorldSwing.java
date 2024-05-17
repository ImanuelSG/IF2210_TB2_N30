package trial;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HelloWorldSwing {
    public static void main(String[] args) {
        // Create and set up the window
        JFrame frame = new JFrame("HelloWorldSwing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a JPanel to hold the components and center it
        JPanel panel = new JPanel(new GridBagLayout());

        // Add the "Hello, World!" label to the panel
        JLabel label = new JLabel("Hello, World!");
        label.setHorizontalAlignment(SwingConstants.CENTER); // Center text horizontally
        label.setVerticalAlignment(SwingConstants.CENTER); // Center text vertically
        panel.add(label);

        // Add a counter button to the panel
        JButton button = new JButton("Click Me!");
        button.addActionListener(new ActionListener() {
            private int count = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                count++;
                label.setText("Button clicked " + count + " times");
            }
        });
        panel.add(button);

        // Add the panel to the frame
        frame.getContentPane().add(panel);

        // Set window size
        int width = 300; // Width of the window
        int height = 200; // Height of the window
        frame.setSize(width, height);

        // Center the window on the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - width) / 2;
        int y = (screenSize.height - height) / 2;
        frame.setLocation(x, y);

        // Make the window visible
        frame.setVisible(true);
    }
}
