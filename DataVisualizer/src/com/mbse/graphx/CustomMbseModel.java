package com.mbse.graphx;

import com.mbse.model.MbseModel;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.model.mxICell;

public class CustomMbseModel extends MbseModel {

public CustomMbseModel() {

	Object parent = this.getDefaultParent();

	this.getModel().beginUpdate();
	
	try
	{

		mxICell containerNode=(mxICell)this.insertVertex(parent, null, "a", 10, 10, 100, 100, "");
		mxICell sampleNode=(mxICell)this.insertVertex(parent, null, "s", 200, 10, 100, 100, "");
		this.insertEdge(parent, null, null, containerNode, sampleNode);
		

		mxCell titleCell = new mxCell("Title",new mxGeometry(20,60,60,30),"");
		titleCell.setVertex(true);
		
		mxCell title2Cell = new mxCell("Title2",new mxGeometry(20,60,60,30),"");
		title2Cell.setVertex(true);

		
		this.addCell(titleCell, containerNode);
		this.addCell(title2Cell, sampleNode);
       
	}
	finally
	{
		this.getModel().endUpdate();
	}

	
}
}
