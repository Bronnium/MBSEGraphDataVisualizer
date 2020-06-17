package com.mbse.graphx;

import javax.swing.SwingConstants;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.util.mxPoint;

public class Port extends mxCell {

	protected Port port;
	
	public static final int IN    = 0;
	public static final int OUT    = 1;
	
	
	final int PORT_DIAMETER = 10;

	final int PORT_RADIUS = PORT_DIAMETER / 2;
	
	/**
	 * 
	 * @param name
	 * @param position - SwingConstants.LEFT or SwingConstants.RIGHT
	 */
	public Port(String name, int position) {
		// TODO Auto-generated constructor stub
		//setValue(name);
		setId(name);
		
		
		//mxCell port1 = (mxCell) graph.insertVertex(parent, "P1", "Port In 1", 0, 0, PORT_RADIUS, PORT_RADIUS, "labelPosition=left;align=right;verticalLabelPosition=middle;");
		// port position needs to depend on number of ports
		int portPosition=0;
		String portStyle = null;
		switch (position) {
		case IN:
			//portStyle = "labelPosition=left;align=right;verticalLabelPosition=middle;";
			break;
		case OUT:
			portPosition = 1;
			//portStyle = "labelPosition=left;align=right;verticalLabelPosition=middle;";

			break;
			
		default:
			break;
		}
		setStyle(portStyle);
		
		//new mxGeometry()
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
