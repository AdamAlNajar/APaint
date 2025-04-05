import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class Canvas extends JPanel {
    int x, y;
    Color color;
    int STROKE_SIZE = 1;
    List<List<ColorPoint>> strokes = new ArrayList<>();
    int canvasWidth, canvasHeight;
    boolean isColorSelected = false;

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
                if(!isColorSelected)
                    return;
                if (SwingUtilities.isLeftMouseButton(e)) {
                    currentDrawPath.add(new ColorPoint(e.getX(), e.getY(), color));
                } else if (SwingUtilities.isRightMouseButton(e)) {
                    undoRecentStroke();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if(!isColorSelected)
                    return;
                if(SwingUtilities.isLeftMouseButton(e) && !currentDrawPath.isEmpty()){
                    strokes.add(List.copyOf(currentDrawPath));
                    currentDrawPath.clear();
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if(!isColorSelected)
                    return;
                if (!SwingUtilities.isLeftMouseButton(e)) {
                    return;
                }

                if (!currentDrawPath.isEmpty()) {
                    ColorPoint prevPoint = currentDrawPath.get(currentDrawPath.size() - 1 );

                    Graphics2D g2 = (Graphics2D) getGraphics();
                    g2.setColor(color);
                    g2.setStroke(new BasicStroke(STROKE_SIZE));
                    g2.drawLine(prevPoint.getX(), prevPoint.getY(), e.getX(), e.getY());
                    g2.dispose();
                }

                currentDrawPath.add(new ColorPoint(e.getX(), e.getY(), color));
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
        this.isColorSelected = true;
    }
}
