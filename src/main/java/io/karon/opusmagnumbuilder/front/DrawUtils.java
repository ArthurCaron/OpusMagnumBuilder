package io.karon.opusmagnumbuilder.front;

import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.Point;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.Display;

import java.util.Collection;

public class DrawUtils {
	public Color darkBlue;
	public Color white;
	public Color darkGray;
	public Color yellow;
	public Color red;
	public Color green;

	private Font font;
	private int fontSize;

	public DrawUtils(Display display, int radius) {
		darkBlue = display.getSystemColor(SWT.COLOR_DARK_BLUE);
		white = display.getSystemColor(SWT.COLOR_WHITE);
		darkGray = display.getSystemColor(SWT.COLOR_DARK_GRAY);
		yellow = display.getSystemColor(SWT.COLOR_YELLOW);
		red = display.getSystemColor(SWT.COLOR_RED);
		green = display.getSystemColor(SWT.COLOR_GREEN);

		FontData fd = display.getSystemFont().getFontData()[0];
		fontSize = (int) (radius / 2.5);
		font = new Font(display, fd.getName(), fontSize, SWT.NONE);
	}

	public void drawDisplayRectangle(GC gc, int shellWidth, int shellHeight) {
		gc.setLineWidth(2);
		gc.setForeground(darkBlue);
		gc.setBackground(white);
		gc.fillRectangle(new Rectangle(0, 0, shellWidth, shellHeight));
	}

	public void drawEmptyHexagon(GC gc, Hexagon hexagon) {
		gc.setForeground(darkBlue);
		gc.setBackground(white);
		gc.drawPolygon(convertToPointsArr(hexagon.getPoints()));
	}

	public void drawTextCenterHexagon(GC gc, Hexagon hexagon, String textToDraw) {
		gc.setFont(font);
		gc.setForeground(red);

		gc.drawString(textToDraw, (int) hexagon.getCenterX() - fontSize, (int) hexagon.getCenterY() - fontSize, true);
	}

	public void drawVisibleHexagon(GC gc, Hexagon hexagon) {
		gc.setBackground(green);
		gc.fillPolygon(convertToPointsArr(hexagon.getPoints()));
		int previousLineWidth = gc.getLineWidth();
		gc.setLineWidth(3);
		gc.setForeground(red);
		gc.drawPolygon(convertToPointsArr(hexagon.getPoints()));
		gc.setLineWidth(previousLineWidth);
	}

	public void drawNotVisibleHexagon(GC gc, Hexagon hexagon) {
		gc.setBackground(red);
		gc.fillPolygon(convertToPointsArr(hexagon.getPoints()));
		int previousLineWidth = gc.getLineWidth();
		gc.setLineWidth(3);
		gc.setForeground(red);
		gc.drawPolygon(convertToPointsArr(hexagon.getPoints()));
		gc.setLineWidth(previousLineWidth);
	}

	public void drawNeighborHexagon(GC gc, Hexagon hexagon) {
		gc.setForeground(white);
		gc.setBackground(darkGray);
		gc.fillPolygon(convertToPointsArr(hexagon.getPoints()));
		gc.setForeground(darkBlue);
		gc.drawPolygon(convertToPointsArr(hexagon.getPoints()));
	}

	public void drawMovementRangeHexagon(GC gc, Hexagon hexagon) {
		gc.setForeground(darkBlue);
		gc.setBackground(yellow);
		gc.fillPolygon(convertToPointsArr(hexagon.getPoints()));
		gc.setForeground(darkBlue);
		gc.drawPolygon(convertToPointsArr(hexagon.getPoints()));
	}

	public void drawLineHexagon(GC gc, Hexagon hexagon) {
		int previousLineWidth = gc.getLineWidth();
		gc.setLineWidth(3);
		gc.setForeground(red);
		gc.drawPolygon(convertToPointsArr(hexagon.getPoints()));
		gc.setLineWidth(previousLineWidth);
	}

	public void drawFilledHexagon(GC gc, Hexagon hexagon) {
		gc.setForeground(white);
		gc.setBackground(darkBlue);
		gc.fillPolygon(convertToPointsArr(hexagon.getPoints()));
		gc.setForeground(darkBlue);
		gc.drawPolygon(convertToPointsArr(hexagon.getPoints()));
	}

	public void drawCoordinates(GC gc, Hexagon hexagon) {
		int gridX = hexagon.getGridX();
		int gridY = hexagon.getGridY();
		int gridZ = -(gridX + gridY);
		gc.setFont(font);
		gc.setForeground(red);
		gc.drawString("x:" + gridX, (int) hexagon.getCenterX() - fontSize, (int) (hexagon.getCenterY() - fontSize * 2.5), true);
		gc.drawString("y:" + gridY, (int) hexagon.getCenterX() - fontSize, (int) hexagon.getCenterY() - fontSize, true);
		gc.drawString("z:" + gridZ, (int) hexagon.getCenterX() - fontSize, (int) (hexagon.getCenterY() + fontSize / 3), true);
	}

	private int[] convertToPointsArr(Collection<Point> points) {
		int[] pointsArr = new int[12];
		int idx = 0;
		for (Point point : points) {
			pointsArr[idx] = (int) Math.round(point.getCoordinateX());
			pointsArr[idx + 1] = (int) Math.round(point.getCoordinateY());
			idx += 2;
		}
		return pointsArr;
	}
}
