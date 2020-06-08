package com.mbse.graphx;

import javax.swing.SwingConstants;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.util.mxPoint;

public class Port extends mxCell {

	protected Port port;
	
	final int PORT_DIAMETER = 10;

	final int PORT_RADIUS = PORT_DIAMETER / 2;
	
	public Port(String name, int position) {
		// TODO Auto-generated constructor stub
		//setValue(name);
		setId(name);
		
		// port position needs to depend on number of ports
		int portPosition=0;
		switch (position) {
		case SwingConstants.LEFT:
			
			break;
		case SwingConstants.RIGHT:
			portPosition = 1;
			break;
			
		default:
			break;
		}
		
		mxGeometry geo1 = new mxGeometry(portPosition, 0.5, PORT_DIAMETER,	PORT_DIAMETER);
		// Because the origin is at upper left corner, need to translate to
		// position the center of port correctly
		geo1.setOffset(new mxPoint(-PORT_RADIUS, -PORT_RADIUS));
		geo1.setRelative(true);
		
		setGeometry(geo1);
		//setStyle("shape=ellipse;perimter=ellipsePerimeter");

		setVertex(true);
		
	}
}
