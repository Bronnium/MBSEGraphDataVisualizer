package com.mbse.graphx.connectors;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

class StringMetrics {

	Font font;
	FontRenderContext context;

	public StringMetrics(Graphics2D g2) {

		font = g2.getFont();
		context = g2.getFontRenderContext();
	}

	Rectangle2D getBounds(String message) {

		return font.getStringBounds(message, context);
	}

	double getWidth(String message) {

		Rectangle2D bounds = getBounds(message);
		return bounds.getWidth();
	}

	double getHeight(String message) {

		Rectangle2D bounds = getBounds(message);
		return bounds.getHeight();
	}

}