package com.mbse.graphx.connectors;


import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import com.mbse.graphx.GraphCallOperation;
import com.mbse.graphx.MbseModel;
import com.mbse.graphx.layout.FunctionalBehaviorLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.model.mxICell;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEventObject;
import com.telelogic.rhapsody.core.IRPCollection;
import com.telelogic.rhapsody.core.IRPDependency;
import com.telelogic.rhapsody.core.IRPDiagram;
import com.telelogic.rhapsody.core.IRPGraphEdge;
import com.telelogic.rhapsody.core.IRPGraphElement;
import com.telelogic.rhapsody.core.IRPGraphNode;
import com.telelogic.rhapsody.core.IRPGraphicalProperty;
import com.telelogic.rhapsody.core.IRPModelElement;
import com.telelogic.rhapsody.core.IRPOperation;

public class RhapsodyMbseModel extends MbseModel {


	
	private HashMap<String, GraphCallOperation> map = new HashMap<String, GraphCallOperation>();
	
	protected mxCell frame = null;

	public RhapsodyMbseModel(String str) 
	{
		Object root = this.getDefaultParent();
		System.out.println("value parent:"+root);

		this.getModel().beginUpdate();

		try
		{
			mxICell frame=(mxICell)this.insertVertex(root, null, "TEST", 10, 10, 100, 100, "");
			
			
			mxICell extractParameter=(mxICell)this.insertVertex(frame, null, "Extract", 10, 10, 100, 100, "");
			extractParameter.getGeometry().setRelative(true);
			
			mxICell prm15=(mxICell)this.insertVertex(frame, null, "Prm 15", 200, 10, 100, 100, "");
			prm15.getGeometry().setRelative(true);
			
			mxICell adapterPrm=(mxICell)this.insertVertex(frame, null, "Adapted Power", 200, 10, 100, 100, "");
			adapterPrm.getGeometry().setRelative(true);
			
			mxICell prm16=(mxICell)this.insertVertex(frame, null, "Prm 16", 200, 10, 100, 100, "");
			prm16.getGeometry().setRelative(true);
			
			/*
			mxGeometry geo1 = new mxGeometry(direction, 0.5, textSize,40);
			// Because the origin is at upper left corner, need to translate to
			// position the center of port correctly
			geo1.setOffset(new mxPoint(-textSize/2, -20));
			geo1.setRelative(true);

			//mxCell port1 = new mxCell(Text, geo1,"");
			port1.setGeometry(geo1);
			port1.setId(GUID);
			port1.setVertex(true);
*/
			
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

		this.appliedLayout = new FunctionalBehaviorLayout(this, frame);

	}

	public RhapsodyMbseModel(IRPDiagram diagram, String str) {

		this.setAllowDanglingEdges(false);
		
		Object parent = this.getDefaultParent();
		System.out.println("herrre:"+parent);

		this.getModel().beginUpdate();

		try
		{
			@SuppressWarnings("unchecked")
			List<IRPGraphElement> elements = diagram.getGraphicalElements().toList();

			elements.sort(new Comparator<IRPGraphElement>() {
				@Override
				public int compare(IRPGraphElement o1, IRPGraphElement o2) {
					if( o1 instanceof IRPGraphEdge && o2 instanceof IRPGraphNode)
						return 1;
					if( o1 instanceof IRPGraphNode && o2 instanceof IRPGraphEdge)
						return -1;
					return 0;
				}
			});
			Object rootElement = parent;
			
			System.out.println(diagram.getName()+" - Diagrams elements: "+elements.size());
			// nodes first then edges
			for (IRPGraphElement graphElement : elements) 
			{
				
				String graphicalGUID = graphElement.getGraphicalProperty("GUID").getValue();

				//IRPGraphNode node = (IRPGraphNode) graphElement;



				switch (graphElement.getGraphicalProperty("Type").getValue()) {
				case "ActionPin":
					//addActionPinToGraph((IRPGraphNode) graphElement);						
					break;
				case "ActivityParameter":
					addActivityParameterToGraph((IRPGraphNode) graphElement);	
					//Object activityParameter = this.insertVertex(parent, graphicalGUID, displayText, 50, 50, 50, 30);
					//map.put(graphicalGUID, new GraphCallOperation((mxCell) rootElement));

					break;
				case "CallOperation":
					if (graphElement.getModelObject() instanceof IRPModelElement)
					{
						String displayName = graphElement.getModelObject().getDisplayName();

						int widthInPixels = (int) (displayName.length()*5.5+50);
						int heightInPixels = 30;
						
						String styleParent = mxConstants.STYLE_FILLCOLOR + "=#ff8484";

						System.out.println(displayName+" added vertex:" +graphicalGUID);
						Object vertex = this.insertVertex(rootElement, graphicalGUID, displayName, 0, 0, widthInPixels, heightInPixels,styleParent);
						
						//setConnectable(false);
						
						map.put(graphicalGUID, new GraphCallOperation((mxCell) vertex));
					}
					break;

				case "DiagramFrame":			
					//rootElement = this.insertVertex(parent, graphicalGUID, displayText, 50, 50, 400, 500);
					//frame =  (mxCell) rootElement;
					//map.put(graphicalGUID, new GraphCallOperation((mxCell) rootElement));
					
					break;

				default:
					if(graphElement instanceof IRPGraphEdge)
					{
						IRPGraphEdge edge = (IRPGraphEdge)graphElement;
						addObjectFlowToGraph(edge, rootElement);
						
						System.out.println("---------------------------");
						IRPCollection gps = graphElement.getAllGraphicalProperties();
						for (int i=0; i < gps.getCount(); i++) {
							IRPGraphicalProperty gp = (IRPGraphicalProperty) gps.getItem(i);
							if (gp != null)
							{// Null graphical properties may exist
								System.out.println(gp.getKey() + "\t" + gp.getValue());
							}

						}
					}
					break;
				};

			}

		}
		finally
		{
			this.getModel().endUpdate();
		}

		
		this.appliedLayout = new FunctionalBehaviorLayout(this, frame);
	}

	private void addObjectFlowToGraph(IRPGraphEdge edge, Object parent) {
		String GUID = edge.getGraphicalProperty("GUID").getValue();
		String rgbColor = edge.getGraphicalProperty("ForegroundColor").getValue();
	
		String hex = RhapsodyUtilities.convertRGBToHex(rgbColor);
		String styleParent = mxConstants.STYLE_STROKECOLOR + "="+hex;
		//String styleParent = mxConstants.STYLE_FILLCOLOR + "=#00FF00";
		
		
		String Text = edge.getSource().getGraphicalProperty("Text").getValue();

		String source = edge.getSource().getGraphicalProperty("Type").getValue();
		String target = edge.getTarget().getGraphicalProperty("Type").getValue();
		
		String sourceGUID = edge.getSource().getGraphicalProperty("GUID").getValue();
		String targetGUID = edge.getTarget().getGraphicalProperty("GUID").getValue();
		
		if (source.equals("ActionPin"))
		{
			sourceGUID = edge.getSource().getGraphicalParent().getGraphicalProperty("GUID").getValue();
			
		}
		
		if (target.equals("ActionPin"))
		{
			targetGUID = edge.getTarget().getGraphicalParent().getGraphicalProperty("GUID").getValue();
		}
		

		Object graphSource = findObject(sourceGUID);
		Object graphTarget = findObject(targetGUID);
		if (graphSource == null || graphTarget == null) 
		{
			System.out.println("ERRRRRROR");
		}

		this.insertEdge(parent, GUID, Text, graphSource, graphTarget,styleParent);

	}

	private void addActivityParameterToGraph(IRPGraphNode node) {
		String GUID = node.getGraphicalProperty("GUID").getValue();
		String Text = node.getGraphicalProperty("Text").getValue();

		int widthInPixels = (int) (Text.length()*5.5+50);
		int heightInPixels = 30;
		
		//String 
		String styleParent = mxConstants.STYLE_SHAPE + "="+mxConstants.SHAPE_ELLIPSE+";"; 
		styleParent = styleParent + mxConstants.STYLE_FILLCOLOR + "=#999999";
		
		this.insertVertex(this.getDefaultParent(), GUID, Text, 0, 0, widthInPixels, heightInPixels, styleParent);
		
	}

	public RhapsodyMbseModel(IRPDiagram diagram) {
		Object parent = this.getDefaultParent();

		this.getModel().beginUpdate();
		try {
			@SuppressWarnings("unchecked")
			List<IRPGraphElement> elements = diagram.getGraphicalElements().toList();

			// nodes first then edges
			for (IRPGraphElement graphElement : elements) 
			{
				String graphicalGUID = graphElement.getGraphicalProperty("GUID").getValue();

				if (graphElement instanceof IRPGraphNode) {
					IRPGraphNode node = (IRPGraphNode) graphElement;

					if (node.getModelObject() instanceof IRPOperation)
					{
						String displayName = node.getModelObject().getDisplayName();


						int widthInPixels = (int) (displayName.length()*5.5+50);
						int heightInPixels = 30;

						System.out.println(displayName+" added vertex:" +graphicalGUID);
						this.insertVertex(parent, graphicalGUID, displayName, 0, 0, widthInPixels, heightInPixels);
					}


				}
				else if (graphElement instanceof IRPGraphEdge)
				{
					IRPGraphEdge edge = (IRPGraphEdge) graphElement;
					if (edge.getModelObject() instanceof IRPDependency) {

						String sourceGUID = edge.getSource().getGraphicalProperty("GUID").getValue();
						String targetGUID = edge.getTarget().getGraphicalProperty("GUID").getValue();

						Object source = ((mxGraphModel)(this.getModel())).getCell(sourceGUID);
						Object target = ((mxGraphModel)(this.getModel())).getCell(targetGUID);
						System.out.println(source +" ---> "+ target);
						if (source != null && target != null)
						{
							this.insertEdge(parent, graphicalGUID, null, source, target);
						}
						else
						{
							System.out.println("error adding edge");
						}

					}
				}
			}

			RhapsodyMbseModel currentModel = this;
			this.addListener(mxEvent.FOLD_CELLS,  new mxIEventListener() {


				@Override
				public void invoke(Object sender, mxEventObject evt) {
					System.out.println("folding repositioning");

					appliedLayout.execute(currentModel.getDefaultParent());

				}
			});
		} finally {
			this.getModel().endUpdate();
		}
	}


	protected Object findObject(String GUID) {
		return ((mxGraphModel)(this.getModel())).getCell(GUID);
	}


}
