import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.util.ArrayList;
import java.util.Random;

public class SierpinskiTriangle {

    private static int SIZE = 750;
    private static ArrayList<Color> colors = new ArrayList<>();
    private static Random rnd = new Random();

    JFrame frame;
    JPanel panel;

    @SuppressWarnings("serial")
    public void display() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                paintSierpinskiTriangle(g, getSize());
            }
        };
        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) { panel.repaint(); }
        });
        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        /* The width of the canvas is SIZE and the height of the canvas
        is now the height of the triangle that gets drawn, so that there is
        no free space between the canvas and the triangle.
         */
        int height = (int) (SIZE * Math.sqrt(3) / 2);
        frame.setSize(SIZE, height);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        setColors(4);
        SierpinskiTriangle triangle = new SierpinskiTriangle();
        triangle.display();
    }

    public void paintSierpinskiTriangle(Graphics g, Dimension size) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setBackground(Color.white);
        g2.clearRect(0, 0, size.width, size.height);

        /* Formula that gets the height of a equilateral triangle from the
        length.
         */
        int height = (int) (size.width * Math.sqrt(3) / 2);

        /* The points for the triangle */
        Point p1 = new Point(0, height);
        Point p2 = new Point(size.width/2, 0);
        Point p3 = new Point(size.width, height);

        // Creates a polygon object t and adds the points to it.
        Polygon t = new Polygon();
        t.addPoint(p1.x, p1.y);
        t.addPoint(p2.x, p2.y);
        t.addPoint(p3.x, p3.y);

        // Fills the polygon with a color (black) and draws it.
        g2.fillPolygon(t);
        g2.drawPolygon(t);

        // Calls the method to draw the subtriangles.
        drawSubTriangles(4,g, p1,p2,p3);


    }

    /**
     * Draws the subtriangles in a Sirpinski triangle.
     * @param rec Recursion level. The amount of times the subtriangles gets drawn.
     *            This is a placeholder ending condition and needs to be changed. The recursive
     *            termination condition should be, when the triangles that are drawn are smaller
     *            or equals of one pixel in length.
     * @param g Graphics component. Is needed to be able to do graphic related operations.
     * @param p1 The first point of the former triangle.
     * @param p2 The second point of the former triangle.
     * @param p3 The third point of the former triangle.
     */
    public void drawSubTriangles(int rec, Graphics g, Point p1, Point p2, Point p3){

        // If the recursion level is greater than zero, then draw the subtriangles
        if(rec > 0){

            /* Creates the midpoints of the former triangle. From these points
            the subtriangle will be drawn.
             */
            Point m1 = new Point((p1.x + p2.x) / 2, (p1.y + p2.y) / 2);
            Point m2 = new Point((p2.x + p3.x) / 2, (p2.y + p3.y) / 2);
            Point m3 = new Point((p1.x + p3.x) / 2, (p1.y + p3.y) / 2);

            Polygon st = new Polygon();
            st.addPoint(m1.x, m1.y);
            st.addPoint(m2.x, m2.y);
            st.addPoint(m3.x, m3.y);

            /* Sets the color of the triangle. Gets a color from a List of colors
            which was created by a method setColors(). "rec - 1" is the index e.g
            The triangle is at level 7, it will get the color at index 6, if it is
            at level 1, it will get the color at index 0.
             */
            g.setColor(colors.get(rec - 1));
//            g.setColor(Color.white);
            // Fills the polygon with the color and draws it.
            g.fillPolygon(st);
            g.drawPolygon(st);

            /* Recursively calls the subtriangle method, to draw subtriangles
            of which the drawn subtriangle will again draw three subtriangles
            and so on. The level of each call gets lower until it reaches null.
            At this point there will be no calls to this method and the triangle
            does not get drawn any further.
             */
            drawSubTriangles(rec - 1, g, p1,m1,m3);
            drawSubTriangles(rec - 1,g, m1,p2,m2);
            drawSubTriangles(rec - 1,g, m3,m2,p3);
        }
    }

    /**
     * This method creates random colors and saves them in a list of colors.
     * @param level Determines the amount of available colors. Should be equal to
     *              the level of the initial triangle. This is subject to change.
     */
    public static void setColors(int level){

        /* In a loop, creates random colors in HSB and adds it to the
        public field colors.
         */
        for(int i = 0; i < level; i++){
            int h = rnd.nextInt(360) + 1;
            int s = rnd.nextInt(100);
            int b = rnd.nextInt(100);
            Color c1 = Color.getHSBColor(h,s,b);
            colors.add(c1);
        }
    }
}