import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class Canvas extends JPanel {
    int x, y;
    Color color;
    int STROKE_SIZE = 8;
    List<List<ColorPoint>> strokes = new ArrayList<>();
    int canvasWidth, canvasHeight;

    public Canvas(int width, int height) {
        super();
        setPreferredSize(new Dimension(width, height));
        setOpaque(true);
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        canvasWidth = width;
        canvasHeight = height;

        MouseAdapter ma = new MouseAdapter() {
            private List<ColorPoint> currentDrawPath = new ArrayList<>();
            @Override
            public void mousePressed(MouseEvent e) {
                if(SwingUtilities.isLeftMouseButton(e)){
                    currentDrawPath.add(new ColorPoint(e.getX(), e.getY(), color));
                }
                else if (SwingUtilities.isRightMouseButton(e)){
                    undoRecentStroke();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (currentDrawPath != null && !currentDrawPath.isEmpty()) {
                    strokes.add(List.copyOf(currentDrawPath)); // add the most recent stroke to the list of all
                                                                   // strokes
                    System.out.println("Added New Stroke");
                    System.out.println(strokes);
                    currentDrawPath.clear();
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                // get current location
                x = e.getX();
                y = e.getY();

                // used to be able to draw a line 1)
                Graphics2D g2d = (Graphics2D) getGraphics();
                g2d.setColor(color);
                if (!currentDrawPath.isEmpty()) {
                    ColorPoint prevPoint = currentDrawPath.get(currentDrawPath.size() - 2);
                    g2d.setStroke(new BasicStroke(STROKE_SIZE));

                    // connect the current point to the previous point to draw a line
                    g2d.drawLine(prevPoint.getX(), prevPoint.getY(), x, y);
                }
                g2d.dispose();

                // add the new point to the path
                ColorPoint nextPoint = new ColorPoint(e.getX(), e.getY(), color);
                currentDrawPath.add(nextPoint);
            }

        };
        addMouseListener(ma);
        addMouseMotionListener(ma);
    }

    public void undoRecentStroke() {
        if (!strokes.isEmpty()) {
            strokes.remove(strokes.size() - 1);
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, getWidth(), getHeight());

        for (List<ColorPoint> stroke : strokes) {
            for (int i = 1; i < stroke.size(); i++) {
                ColorPoint start = stroke.get(i - 1);
                ColorPoint end = stroke.get(i);
                g2.setColor(start.getColor());
                g2.setStroke(new BasicStroke(STROKE_SIZE));
                g2.drawLine(start.getX(), start.getY(), end.getX(), end.getY());
            }
        }

        g2.dispose();
    }

    public void setStrokeSize(int newSize) {
        this.STROKE_SIZE = newSize; // Update stroke size
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
