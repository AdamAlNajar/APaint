
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class Canvas extends JPanel{
    int x,y;
    Color color;
    final static int STROKE_SIZE = 8;
    List<ColorPoint> currentDrawPath = new ArrayList<>();
    int canvasWidth, canvasHeight;

    public Canvas(int width, int height){
        super();
        setPreferredSize(new Dimension(width,height));
        setOpaque(true);
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        canvasWidth = width;
        canvasHeight = height;

        MouseAdapter ma = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // get current mouse location
                x = e.getX();
                y = e.getY();

                // draw in current mouse location
                Graphics g = getGraphics();
                g.setColor(color);
                g.fillRect(x, y, STROKE_SIZE, STROKE_SIZE);
                g.dispose();

                // start current path
                currentDrawPath = new ArrayList<>(25);
                currentDrawPath.add(new ColorPoint(x, y, color));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                currentDrawPath = null;
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                // get current location
                x = e.getX();
                y = e.getY();

                // used to be able to draw a line
                Graphics2D g2d = (Graphics2D) getGraphics();
                g2d.setColor(color);
                if(!currentDrawPath.isEmpty()){
                    ColorPoint prevPoint = currentDrawPath.get(currentDrawPath.size() - 1);
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

    public void setColor(Color color){
        this.color = color;
    }

    void resetCanvas() {
        // Removes everything from canvas
        Graphics g = getGraphics();
        g.clearRect(0,0,canvasWidth,canvasHeight);
        g.dispose();
        currentDrawPath = null;

        repaint(); 
        revalidate();
    }
}