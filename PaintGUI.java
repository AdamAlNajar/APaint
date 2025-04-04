import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class PaintGUI extends JFrame {
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
                chooseColorButton.setBackground(c);
                canvas.setColor(c);
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
        springLayout.putConstraint(SpringLayout.WEST, undoButton, 250, SpringLayout.WEST, canvasPanel);

        this.getContentPane().add(canvasPanel);
    }
}
