import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class PaintGUI extends JFrame {
    Font font = new Font("Arial", Font.BOLD, 20);
    public PaintGUI(){
        super("APaint");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1500, 1000));
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        AddGuiComponents();
    }

    void AddGuiComponents(){
        JPanel canvasPanel = new JPanel();
        SpringLayout springLayout = new SpringLayout();
        canvasPanel.setLayout(springLayout);

        Canvas canvas = new Canvas(1500, 950);
        canvasPanel.add(canvas);
        springLayout.putConstraint(springLayout.NORTH, canvas, 50, springLayout.NORTH, canvasPanel);

        // 2. Color Chooser
        JButton chooseColorButton = new JButton("Choose Color");
        chooseColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color c = JColorChooser.showDialog(null, "Select a color", Color.BLACK);
                if(c != null) {
                    chooseColorButton.setBackground(c);
                    canvas.setColor(c);
                }
            }
        });
        canvasPanel.add(chooseColorButton);
        springLayout.putConstraint(SpringLayout.NORTH, chooseColorButton, 10, SpringLayout.NORTH, canvasPanel);
        springLayout.putConstraint(SpringLayout.WEST, chooseColorButton, 25, SpringLayout.WEST, canvasPanel);


        // 3. Undo Recent Stroke
        JButton undoButton = new JButton("Undo");
        undoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.undoRecentStroke();
            }
        });
        canvasPanel.add(undoButton);
        springLayout.putConstraint(SpringLayout.NORTH, undoButton, 10, SpringLayout.NORTH, canvasPanel);
        springLayout.putConstraint(SpringLayout.WEST, undoButton, 150, SpringLayout.WEST, canvasPanel);


        // 4. Stroke Size Slider with JLabel
        JPanel strokeSizePanel = new JPanel();  // Create a panel to hold the slider and label
        strokeSizePanel.setLayout(new BoxLayout(strokeSizePanel, BoxLayout.X_AXIS)); // Horizontal layout

        JLabel strokeSizeLabel = new JLabel("Stroke Size -> 0 ------>"); // Initial label text
        strokeSizeLabel.setFont(font);
        JSlider strokeSizeSlider = new JSlider(1, 100, 1); // Min = 1, Max = 100, Initial = 1
        strokeSizeSlider.setMajorTickSpacing(0);
        strokeSizeSlider.setMinorTickSpacing(0);
        strokeSizeSlider.setPaintTicks(true);
        strokeSizeSlider.setPaintLabels(true);
        strokeSizeSlider.addChangeListener(e -> {
            // Update stroke size on canvas when the slider is adjusted
            int newStrokeSize = strokeSizeSlider.getValue();
            canvas.setStrokeSize(newStrokeSize);
            strokeSizeLabel.setText("Stroke Size -> " + newStrokeSize + " ------>"); // Update label text
        });

        // Add the label and slider to the panel
        strokeSizePanel.add(strokeSizeLabel);
        strokeSizePanel.add(strokeSizeSlider);

        // Add stroke size panel to canvasPanel
        canvasPanel.add(strokeSizePanel);
        springLayout.putConstraint(SpringLayout.NORTH, strokeSizePanel, 10, SpringLayout.NORTH, canvasPanel);
        springLayout.putConstraint(SpringLayout.WEST, strokeSizePanel, 400, SpringLayout.WEST, canvasPanel);
        this.getContentPane().add(canvasPanel);
    }
}
