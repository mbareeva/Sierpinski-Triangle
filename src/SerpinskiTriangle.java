import java.util.Random;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class SerpinskiTriangle {
	public static int SIZE = 800;

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
			public void componentResized(ComponentEvent e) {
				panel.repaint();
			}
		});
		frame.setLayout(new BorderLayout());
		frame.add(panel, BorderLayout.CENTER);
		frame.pack();
		
		// set the size of the frame (height), the width = SIZE.
		//The height of frame is calculated acc. to formula: (plane * sqrt(3))/2).
		int height = (int) ((SIZE * Math.sqrt(3)) / 2);
		frame.setSize(SIZE, height);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		SerpinskiTriangle triangle = new SerpinskiTriangle();
		triangle.display();
	}

	public void paintSierpinskiTriangle(Graphics g, Dimension size) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setBackground(Color.white);
		g2.clearRect(0, 0, size.width, size.height);
		//g2.draw3DRect(20, 20, size.width - 40, size.height - 40, true);
		
		//set the height of triangle that is equal to the frame's height;
		//y value  of left and right bottom points = height.
		int height = (int) ((size.getWidth() * Math.sqrt(3)) / 2);
		
		//create Points for triangle.
		
		Point a = new Point(0, height);
		Point b = new Point(size.width, height);
		Point c = new Point(size.width/2, 0);
		
		//create Polygon and append 3 points.
		Polygon triangle= new Polygon();
		triangle.addPoint(a.x, a.y);
		triangle.addPoint(b.x, b.y);
		triangle.addPoint(c.x, c.y);
		
		g2.drawPolygon(triangle);
		paintSubTriangles(g, 5, a, b, c);
	}
	/*
	 * Draws recursively triangles inside the big triangle till it reaches the level.
	 * @param level The level of recursion.When the level is reached, the cycle is finished.
	 * @param x1 The new edge of new smaller triangle.
	 * @param x2 The new edge of new smaller triangle.
	 * @param x3 The new edge of new smaller triangle.
	 * @param g 
	 */
	public void paintSubTriangles(Graphics g, int level, Point x1, Point x2, Point x3) {
		if(level >= 1) {
		//creating new points for new triangle.
		Point newP1 = new Point(midpoint(x1, x2));
		Point newP2 = new Point(midpoint(x2, x3));
		Point newP3 = new Point(midpoint(x3, x1));
		
		//drawing polygon.
		Polygon triangle= new Polygon();
		// recursive case.
		//create new points for smaller triangle.
		triangle.addPoint(newP1.x, newP1.y);
		triangle.addPoint(newP2.x, newP2.y);
		triangle.addPoint(newP3.x, newP3.y);
		
		
		//g.drawPolygon(triangle);
		
		 g.setColor(generateColor());
		 g.fillPolygon(triangle);
		 g.drawPolygon(triangle);
		paintSubTriangles(g, level-1, newP1, newP2, newP3);
//		 g.setColor(Color.red);
//		g.fillPolygon(triangle);
		paintSubTriangles(g, level-1, newP1, newP2, newP3);
		// g.setColor(Color.yellow);
		paintSubTriangles(g, level-1, newP1, newP2, newP3);
		}
		
		
	}
	/*
	 * Returns the midpoint of two endpoints.
	 * @param x1 The one point of a line.
	 * @param x2 The other point of a line.
	 * @return p The middle point of x1 and x2.
	 */
	public Point midpoint(Point x1, Point x2) {
		Point p = new Point((x1.x+x2.x)/2, (x1.y + x2.y)/2);
		return p;
	}
	/*
	 * Generate the the random rgb color.
	 * @retun The color in rgb format.
	 */
	public Color generateColor() {
		int r = (int) (Math.random()*256);
		int g = (int) (Math.random()*256);
		int b = (int) (Math.random()*256);
		
		return new Color(r, g, b);
		
	}

}