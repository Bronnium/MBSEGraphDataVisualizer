package com.mbse.model;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.mbse.graphx.GraphCallOperation;
import com.mbse.graphx.connectors.RhapsodyMbseModel;
import com.mbse.graphx.connectors.RhapsodyUtilities;
import com.mbse.graphx.layout.FunctionalBehaviorLayout;
import com.mbse.graphx.layout.ProductBreakdownStructureLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.model.mxICell;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxEventSource.mxIEventListener;

public class StandardMbseGraphModel extends mxGraphModel{

	/** 
	 * default constructor will create dummy data
	 */
	public StandardMbseGraphModel()
	{
		
		Object root = this.getDefaultParent();
		System.out.println("value parent:"+root);

		this.getModel().beginUpdate();

		try
		{
			mxICell frame=(mxICell)this.insertVertex(root, null, "TEST", 10, 10, 100, 100, "");
			
			
			mxICell extractParameter=(mxICell)this.insertVertex(frame, null, "Extract", 10, 10, 100, 100, "");
			//extractParameter.getGeometry().setRelative(true);
			
			mxICell prm15=(mxICell)this.insertVertex(frame, null, "Prm 15", 200, 10, 100, 100, "");
			prm15.getGeometry().setRelative(true);
			
			mxICell adapterPrm=(mxICell)this.insertVertex(frame, null, "Adapted Power", 200, 10, 100, 100, "");
			adapterPrm.getGeometry().setRelative(true);
			
			mxICell prm16=(mxICell)this.insertVertex(frame, null, "Prm 16", 200, 10, 100, 100, "");
			prm16.getGeometry().setRelative(true);
						
			this.addCell(extractParameter, frame);
			
			this.addCell(prm15, frame);
			
			this.addCell(adapterPrm, frame);
			
			this.addCell(prm16, frame);
			
			


			mxICell f1=(mxICell)this.insertVertex(frame, null, "To adapt speed", 200, 10, 100, 100, "");
			mxICell f2=(mxICell)this.insertVertex(frame, null, "To evacuate heat rejections", 200, 10, 100, 100, "");
			mxICell f3=(mxICell)this.insertVertex(frame, null, "To monitor oil pressure", 200, 10, 100, 100, "");

			this.insertEdge(frame, null, null, extractParameter, f1);
			this.insertEdge(frame, null, null, prm15, f2);
			this.insertEdge(frame, null, null, f2, f3);	
			this.insertEdge(frame, null, null, f1, adapterPrm);
			this.insertEdge(frame, null, null, f1, f3);
			this.insertEdge(frame, null, null, f3, prm16);

		}
		finally
		{
			this.getModel().endUpdate();
		}

		this.appliedLayout = new ProductBreakdownStructureLayout(this);

	}


	
	protected Object findObject(String GUID) {
		return ((mxGraphModel)(this.getModel())).getCell(GUID);
	}


}
